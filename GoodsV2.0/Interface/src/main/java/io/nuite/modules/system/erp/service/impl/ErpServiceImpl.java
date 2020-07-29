package io.nuite.modules.system.erp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import io.nuite.common.utils.DateUtils;
import io.nuite.modules.job.task.ErpTask;
import io.nuite.modules.order_platform_app.dao.*;
import io.nuite.modules.order_platform_app.entity.*;
import io.nuite.modules.order_platform_app.utils.RongCloudUtils;
import io.nuite.modules.sr_base.dao.BaseBrandDao;
import io.nuite.modules.sr_base.dao.GoodsShoesDao;
import io.nuite.modules.sr_base.entity.*;
import io.nuite.modules.sr_base.service.*;
import io.nuite.modules.sr_base.utils.ConfigUtils;
import io.nuite.modules.sr_base.utils.ESmartConfig;
import io.nuite.modules.system.dao.order_platform.OrderManageDao;
import io.nuite.modules.system.dao.order_platform.OrderProductManagementDao;
import io.nuite.modules.system.dao.order_platform.OrderUserJurisdictionDao;
import io.nuite.modules.system.entity.order_platform.OrderUserJurisdictionEntity;
import io.nuite.modules.system.erp.entity.GoodsSXVo;
import io.nuite.modules.system.erp.entity.GoodsStock;
import io.nuite.modules.system.erp.entity.GoodsVo;
import io.nuite.modules.system.erp.entity.KeHu;
import io.nuite.modules.system.erp.service.ErpService;
import io.nuite.modules.system.erp.utils.HttpRequestUtils;
import io.nuite.modules.system.erp.utils.MapUtils;
import io.nuite.modules.system.service.SystemGoodsCategoryService;
import io.nuite.modules.system.service.SystemGoodsColorService;
import io.nuite.modules.system.service.SystemGoodsSizeService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: yangchuang
 * @Date: 2018/8/6 13:35
 * @Version: 1.0
 * @Description:
 */

@Service
public class ErpServiceImpl implements ErpService {

    private static final Logger log = LoggerFactory.getLogger(ErpTask.class);

    @Autowired
    SystemGoodsSizeService goodsSizeService;

    @Autowired
    SystemGoodsColorService goodsColorService;

    @Autowired
    SystemGoodsCategoryService goodsCategoryService;

    @Autowired
    GoodsBrandService goodsBrandService;

    @Autowired
    GoodsSXService goodsSXService;

    @Autowired
    GoodsSXOptionService goodsSXOptionService;

    @Autowired
    BaseUserService baseUserService;

    @Autowired
    OrderUserJurisdictionDao userJurisdictionDao;

    @Autowired
    GoodsShoesDao goodsShoesDao;

    @Autowired
    ShoesDataDao shoesDataDao;

    @Autowired
    ShoesInfoDao shoesInfoDao;

    @Autowired
    ShoesCompanyTypeDao shoesCompanyTypeDao;

    @Autowired
    private OrderManageDao orderManageDao;

    @Autowired
    private OrderProductManagementDao orderProductManagementDao;

    @Autowired
    BaseESmartService baseESmartService;

    @Autowired
    ESmartConfig eSmartConfig;

    @Autowired
    GoodsPeriodService goodsPeriodService;

    @Autowired
    BaseBrandDao baseBrandDao;

    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderProductsDao orderProductsDao;

    @Autowired
    ReceiverInfoDao receiverInfoDao;

    @Autowired
    protected ConfigUtils configUtils;

    @Autowired
    BaseAgentService baseAgentService;

    @Autowired
    BaseAreaService baseAreaService;

    @Autowired
    private RongCloudUtils rongCloudUtils;

    /**
     * 尺码同步，存在且名称不同则更新，不存在则新建
     *
     * @param baseESmartEntity
     * @param url
     */
    @Override
    public void selectSizes(BaseESmartEntity baseESmartEntity, String url) {
        Integer company_seq = baseESmartEntity.getCompanySeq();
        String key = baseESmartEntity.getSmartKey();
        String secret = baseESmartEntity.getSmartSecret();

        //发送post请求
        JSONObject data = HttpRequestUtils.getResponseResult(url, key, secret);

        if (data == null) {
            log.info("esmart异常，未获取到尺码数据，请检查");
            return;
        }

        //响应结果状态码
        Integer status = data.getInteger("code");
        String msg = data.getString("msg");
        if (status != 1) {
            log.info("尺码查询-响应结果状态码:{},{}", status, msg);
            return;
        }

        //解析json数据
        JSONArray result = data.getJSONArray("result");

        for (int i = 0; i < result.size(); i++) {
            JSONObject obj = result.getJSONObject(i);

            String sizeName = obj.getString("sizeName");
            String sizeCode = obj.getString("sizeCode");

            if (StringUtils.isEmpty(sizeName) || StringUtils.isEmpty(sizeCode)) {
                log.info("尺码同步-异常尺码：sizeCode:{};sizeName:{}", sizeCode, sizeName);
                continue;
            }
            sizeName = sizeName.trim();
            sizeCode = sizeCode.trim();

            List<GoodsSizeEntity> goodsSizeEntities = goodsSizeService.selectList(new EntityWrapper<GoodsSizeEntity>()
                    .eq("SizeCode", sizeCode)
                    .eq("Company_Seq", company_seq));

            if (goodsSizeEntities.size() > 0) {

                for (GoodsSizeEntity goodsSizeEntity : goodsSizeEntities) {

                    if (!sizeName.equals(goodsSizeEntity.getSizeName())) {
                        goodsSizeEntity.setSizeName(sizeName);
                        goodsSizeEntity.setInputTime(new Date());

                        goodsSizeService.updateById(goodsSizeEntity);
                    }
                }

                if (goodsSizeEntities.size() > 1) {
                    log.info("尺码同步-存在重复尺码:company_seq:{};sizeCode:{}", company_seq, goodsSizeEntities.get(0).getSizeCode());
                }
            } else {
                //不存在则创建对象并插入
                GoodsSizeEntity goodsSize = new GoodsSizeEntity();
                goodsSize.setSizeCode(sizeCode);
                goodsSize.setSizeName(sizeName);
                goodsSize.setInputTime(new Date());
                goodsSize.setCompanySeq(company_seq);
                goodsSize.setDel(0);
                goodsSizeService.insert(goodsSize);
            }
        }

    }

    /**
     * 颜色同步，存在且名称不同则更新，不存在则插入
     *
     * @param baseESmartEntity
     * @param url
     */
    @Override
    public void selectColors(BaseESmartEntity baseESmartEntity, String url) {
        Integer company_seq = baseESmartEntity.getCompanySeq();
        String key = baseESmartEntity.getSmartKey();
        String secret = baseESmartEntity.getSmartSecret();

        //发送post请求
        JSONObject data = HttpRequestUtils.getResponseResult(url, key, secret);

        if (data == null) {
            log.info("esmart异常，未获取到颜色数据，请检查");
            return;
        }

        //响应结果状态码
        Integer status = data.getInteger("code");
        if (status != 1) {
            log.info("颜色查询-响应结果状态码:{}", status);
            return;
        }

        //解析json数据
        JSONArray result = data.getJSONArray("result");

        for (int i = 0; i < result.size(); i++) {
            JSONObject obj = result.getJSONObject(i);

            String colorCode = obj.getString("code");
            String colorName = obj.getString("name");

            if (StringUtils.isEmpty(colorCode) || StringUtils.isEmpty(colorName)) {
                log.info("颜色同步-异常颜色：colorCode:{};colorName:{};company_seq:{}", colorCode, colorName, company_seq);
                continue;
            }

            colorCode = colorCode.trim();
            colorName = colorName.trim();

            List<GoodsColorEntity> goodsColors = goodsColorService.selectList(new EntityWrapper<GoodsColorEntity>()
                    .eq("Code", colorCode).
                            eq("Company_Seq", company_seq));

            if (goodsColors.size() > 0) {

                for (GoodsColorEntity goodsColor : goodsColors) {
                    if (!colorName.equals(goodsColor.getName())) {
                        //颜色名不相同则更新
                        goodsColor.setName(colorName);
                        goodsColor.setInputTime(new Date());
                        goodsColorService.updateById(goodsColor);
                    }
                }

                if (goodsColors.size() > 1) {
                    log.info("颜色同步-存在重复颜色:company_seq:{};colorCode:{}", company_seq, goodsColors.get(0).getCode());
                }
            } else {
                //不存在则创建对象并插入
                GoodsColorEntity goodsColorEntity = new GoodsColorEntity();
                goodsColorEntity.setInputTime(new Date());
                goodsColorEntity.setName(colorName);
                goodsColorEntity.setCode(colorCode);
                goodsColorEntity.setCompanySeq(company_seq);
                goodsColorEntity.setDel(0);

                goodsColorService.insert(goodsColorEntity);

            }

        }

    }

    /**
     * 同步erp的分类，不存在则插入，存在则不处理
     *
     * @param baseESmartEntity
     * @param url
     */
    @Override
    public void selectCategorys(BaseESmartEntity baseESmartEntity, String url) {
        Integer company_seq = baseESmartEntity.getCompanySeq();
        String key = baseESmartEntity.getSmartKey();
        String secret = baseESmartEntity.getSmartSecret();

        //发送post请求，获得响应结果
        JSONObject data = HttpRequestUtils.getResponseResult(url, key, secret);

        if (data == null) {
            log.info("esmart异常，未获取到分类数据，请检查");
            return;
        }

        //响应结果状态码
        Integer status = data.getInteger("code");
        if (status != 1) {
            log.info("分类查询-响应结果状态码:{}", status);
            return;
        }

        //解析json数据
        JSONArray result = data.getJSONArray("result");

        for (int i = 0; i < result.size(); i++) {

            String name = result.getJSONObject(i).getString("name");
            String code = result.getJSONObject(i).getString("seq");

            if (StringUtils.isEmpty(name) || StringUtils.isEmpty(code)) {
                log.info("分类查询-异常分类：company_seq:{}, name:{}", company_seq, name);
                continue;
            }

            name = name.trim();//分类名称
            code = code.trim();

            GoodsCategoryEntity categoryEntity = goodsCategoryService.selectOne(new EntityWrapper<GoodsCategoryEntity>()
                    .eq("Category_Code", code)
                    .eq("Company_Seq", company_seq));

            if (categoryEntity == null) {
                //不存在则创建对象并插入
                categoryEntity = new GoodsCategoryEntity();
                categoryEntity.setName(name);
                categoryEntity.setCatetoryCode(code);
                categoryEntity.setCompanySeq(company_seq);
                categoryEntity.setDel(0);
                categoryEntity.setParentSeq(0);

                goodsCategoryService.insert(categoryEntity);

            } else {
                //分类存在且名称改变则更新
                if (!name.equals(categoryEntity.getName())) {
                    categoryEntity.setInputTime(new Date());
                    categoryEntity.setName(name);
                    goodsCategoryService.updateById(categoryEntity);
                }

            }
        }
    }

    /**
     * 同步erp属性，本地不存在则插入，存在不更新，erp不存在的属性则删除（逻辑删除）
     *
     * @param baseESmartEntity
     * @param url
     */
    @Override
    public void selectGoodsSX(BaseESmartEntity baseESmartEntity, String url) {
        Integer company_seq = baseESmartEntity.getCompanySeq();
        String key = baseESmartEntity.getSmartKey();
        String secret = baseESmartEntity.getSmartSecret();

        JSONObject data = HttpRequestUtils.getResponseResult(url, key, secret);

        if (data == null) {
            log.info("esmart异常，未获取到属性数据，请检查");
            return;
        }

        //响应结果状态码
        Integer status = data.getInteger("code");
        String msg = data.getString("msg");

        if (status != 1) {
            log.info("属性查询-数据异常> status:{} msg:{}", status, msg);
            return;
        }

        //解析json数据
        JSONArray result = data.getJSONArray("result");

        //存储 属性-选项
//        Map<String, List<GoodsSXOptionEntity>> sxmap = new HashMap<>();

        //【插入或更新部分】
        for (int i = 0; i < result.size(); i++) {
            GoodsSXVo vo = result.getObject(i, GoodsSXVo.class);

            String sxId = vo.getSxId(); //列名
            String sxName = vo.getSxName(); //列名含义
            List<JSONObject> options = vo.getOptions();

            GoodsSXEntity goodsSXEntity = goodsSXService.selectOne(new EntityWrapper<GoodsSXEntity>()
                    .eq("SXID", sxId)
                    .eq("Company_Seq", company_seq));
            //判断属性对象是否存在,不存在则创建
            if (goodsSXEntity == null) {
                goodsSXEntity = new GoodsSXEntity();
                goodsSXEntity.setSXId(sxId);
                goodsSXEntity.setSXName(sxName);
                goodsSXEntity.setCompanySeq(company_seq);

                goodsSXService.insert(goodsSXEntity);
            }

//            List<GoodsSXOptionEntity> opts = new ArrayList();
            for (int j = 0; j < options.size(); j++) {
                JSONObject obj = options.get(j);
                String optionSeq = obj.getString("seq");
                String optionName = obj.getString("name");

                GoodsSXOptionEntity goodsSXOptionEntity = goodsSXOptionService.selectOne(new EntityWrapper<GoodsSXOptionEntity>()
                        .eq("SX_Seq", goodsSXEntity.getSeq())
                        .eq("Code", optionSeq));

                if (goodsSXOptionEntity == null) {
                    goodsSXOptionEntity = new GoodsSXOptionEntity();
                    goodsSXOptionEntity.setCode(optionSeq);
                    goodsSXOptionEntity.setSXSeq(goodsSXEntity.getSeq());
                    goodsSXOptionEntity.setValue(optionName);
                    goodsSXOptionService.insert(goodsSXOptionEntity);

                } else {

                    //当选项值存在，且选项值与查询到的选项值不相等，则更新
                    if (optionName != null && !optionName.equals(goodsSXOptionEntity.getValue())) {
                        goodsSXOptionEntity.setValue(optionName);
                        goodsSXOptionService.updateById(goodsSXOptionEntity);
                    }

                }

//                opts.add(goodsSXOptionEntity);
            }

//            sxmap.put(sxId, opts);

        }

      /*  //【删除不存在的记录】
        //查询该公司的所有属性
        List<GoodsSXEntity> goodsSXs = goodsSXService.selectList(new EntityWrapper<GoodsSXEntity>()
                .eq("Company_Seq", company_seq));
        //去除表中查询不到的属性
        for (GoodsSXEntity goodsSX : goodsSXs) {

            boolean flag = true;//删除标记

            String sxid = goodsSX.getSXId();
            //循环，判断该属性是否在新集合里
            for (String sx : sxmap.keySet()) {
                //判断是否存在
                if (sx.equals(sxid)) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                //属性不存在则删除
                goodsSXService.deleteById(goodsSX.getSeq());
                //并删除所关联的所有选项
                goodsSXOptionService.delete(new EntityWrapper<GoodsSXOptionEntity>()
                        .eq("SX_Seq", goodsSX.getSeq()));

                continue;
            }

            //属性存在
            //获取属性对应的所有选项遍历
            List<GoodsSXOptionEntity> goodsSXOpts = goodsSXOptionService.selectList(new EntityWrapper<GoodsSXOptionEntity>()
                    .eq("SX_Seq", goodsSX.getSeq()));

            for (GoodsSXOptionEntity goodsSXOpt : goodsSXOpts) {
                boolean flag2 = true; //删除标记

                for (GoodsSXOptionEntity optionEntity : sxmap.get(goodsSX.getSXId())) {
                    //判断是否存在
                    if (optionEntity.getCode().equals(goodsSXOpt.getCode())) {
                        flag2 = false;
                        break;
                    }
                }

                if (flag2) {
                    goodsSXOptionService.deleteById(goodsSXOpt);
                }

            }
        }
*/
    }

    /**
     * 同步erp用户，不存在则创建，本地可删除用户（逻辑删除），存在且信息改变则更新（会取消删除状态）
     * 增改查（忽略del，查询所有）
     * 用户地址存在，则查询用户的地址信息，插入到收件人信息表。已存在且地址变动则更新，不存在则插入
     *
     * @param baseESmartEntity
     * @param url
     */
    @Override
    public void selectKEHUs(BaseESmartEntity baseESmartEntity, String url) {
        Integer company_seq = baseESmartEntity.getCompanySeq();
        String key = baseESmartEntity.getSmartKey();
        String secret = baseESmartEntity.getSmartSecret();
        String userPrefix = baseESmartEntity.getUserPrefix();

        //获取工厂对象
        BaseUserEntity company = baseUserService.selectOne(new EntityWrapper<BaseUserEntity>()
                .eq("Company_Seq", company_seq)
                .eq("AttachType", 1)
                .eq("SaleType", 1));

        if (company == null) {
            log.info("批发商查询任务-无法查询到工厂信息，无法执行查询");
            return;
        }

        //获取数据
        JSONObject data = HttpRequestUtils.getResponseResult(url, key, secret);

        if (data == null) {
            log.info("esmart异常，未获取到客户数据，请检查");
            return;
        }

        //响应结果状态码
        Integer status = data.getInteger("code");
        String msg = data.getString("msg");

        if (status != 1) {
            log.info("批发商查询-响应结果状态码:{} {}", status, msg);
            return;
        }

        //解析json数据
        JSONArray result = data.getJSONArray("result");

        //遍历客户对象
        for (int i = 0; i < result.size(); i++) {

            KeHu keHu = result.getObject(i, KeHu.class);

            //联系人
            String linkman = keHu.getLinkman();
            //手机号
            String telephone = keHu.getTelephone();
            //电话
            String tel = keHu.getTel();
            //客户地址
            String keHuAddress = keHu.getAddress();

            //客户名添加前缀，位置不要改动
            String newAccountName = userPrefix + keHu.getAccountName();

            //查询用户是否存在
            BaseUserEntity userEntity = baseUserService.queryUserByCompanyAndAccountName(newAccountName, company_seq);

            //查询不到则创建新用户及用户权限
            if (userEntity == null) {
                userEntity = new BaseUserEntity();
                userEntity.setAccountName(newAccountName);
                userEntity.setUserName(keHu.getUserName());
                userEntity.setPassword(DigestUtils.sha256Hex("123"));
                userEntity.setCompanySeq(company_seq);
                userEntity.setBrandSeq(company.getBrandSeq());

                if ("yiting".equals(keHu.getCompanyFalg())) {
                    userEntity.setAttachType(keHu.getAttachType());
                    userEntity.setSaleType(keHu.getSaleType());

                    if (keHu.getAttachType() == 3 && keHu.getSaleType() == 2) {   //依婷代理
                        //获得代理商seq
                        //查询代理商是否存在，不存在则创建
                        BaseAgentEntity agentEntity = baseAgentService.selectOne(new EntityWrapper<BaseAgentEntity>()
                                .eq("AgentName", keHu.getQudaoName())
                                .eq("Remark", keHu.getQudaoCode()));
                        if (agentEntity == null) {
                            agentEntity = new BaseAgentEntity();
                            agentEntity.setAgentName(keHu.getQudaoName());
                            agentEntity.setRemark(keHu.getQudaoCode());
                            agentEntity.setBrandSeq(company.getBrandSeq());
                            baseAgentService.insert(agentEntity);
                        }
                        userEntity.setAttachSeq(agentEntity.getSeq());
                    } else if (keHu.getAttachType() == 2 && keHu.getSaleType() == 4) {  //依婷门店  //分公司直营
                        //获取区域对象
                        BaseAreaEntity baseAreaEntity = baseAreaService.selectOne(new EntityWrapper<BaseAreaEntity>()
                                .eq("ParentSeq", 0)
                                .eq("Name", keHu.getQuyuName())
                                .eq("Bound", keHu.getQuyuCode()));
                        //如果区域对象不存在，则创建
                        if (baseAreaEntity == null) {
                            baseAreaEntity = new BaseAreaEntity();
                            baseAreaEntity.setBrandSeq(company.getBrandSeq());
                            baseAreaEntity.setParentSeq(0);
                            baseAreaEntity.setName(keHu.getQuyuName());
                            baseAreaEntity.setBound(keHu.getQuyuCode());
                            baseAreaService.insert(baseAreaEntity);
                        }
                        //子公司对象
                        BaseAreaEntity subAreaEntity = baseAreaService.selectOne(new EntityWrapper<BaseAreaEntity>()
                                .ne("ParentSeq", 0)
                                .eq("Name", keHu.getQudaoName())
                                .eq("Bound", keHu.getQudaoCode()));
                        //如果区域对象不存在，则创建
                        if (subAreaEntity == null) {
                            subAreaEntity = new BaseAreaEntity();
                            subAreaEntity.setBrandSeq(company.getBrandSeq());
                            subAreaEntity.setParentSeq(baseAreaEntity.getSeq());
                            subAreaEntity.setName(keHu.getQudaoName());
                            subAreaEntity.setBound(keHu.getQudaoCode());
                            baseAreaService.insert(subAreaEntity);
                        }
                        userEntity.setAttachSeq(subAreaEntity.getSeq());
                    }

                } else {  //除依婷外
                    userEntity.setAttachType(1);  //账号类型 1工厂
                    userEntity.setSaleType(3);   //销售类型 3 批发商
                }

                //手机号为空则查询电话号码，都为空则不输入
                if (StringUtils.isNotEmpty(telephone)) {
                    userEntity.setTelephone(telephone);
                } else if (StringUtils.isNotEmpty(tel)) {
                    userEntity.setTelephone(tel);
                }
                MapUtils.Address address = null;
                if (StringUtils.isNotEmpty(keHuAddress)) {
                    userEntity.setAddress(keHuAddress);
                    address = MapUtils.getPosition(keHuAddress);//获取用户地址信息
                }

                baseUserService.insert(userEntity);

                //创建用户权限
                OrderUserJurisdictionEntity jurisdictionEntity = new OrderUserJurisdictionEntity();
                jurisdictionEntity.setUserSeq(userEntity.getSeq());
                jurisdictionEntity.setCreateUserSeq(company.getSeq());//创建人
                jurisdictionEntity.setEffectiveDate(DateUtils.addDateYears(new Date(), 3)); //有效期
                jurisdictionEntity.setIsDisable(0);
                jurisdictionEntity.setIntOfCreateUsers(0);
                jurisdictionEntity.setAlreadyCreateUsers(0);
                jurisdictionEntity.setIsAdministrator(0);
                jurisdictionEntity.setDel(0);

                userJurisdictionDao.insert(jurisdictionEntity);

                //收件人信息
                if (address != null
                        && StringUtils.isNotEmpty(linkman)
                        && linkman.length() <= 15
                        && (StringUtils.isNotEmpty(telephone) && telephone.length() <= 15
                        || StringUtils.isNotEmpty(tel) && tel.length() <= 15)) {

                    ReceiverInfoEntity receiverInfoEntity = new ReceiverInfoEntity();
                    receiverInfoEntity.setUserSeq(userEntity.getSeq());
                    receiverInfoEntity.setReceiverName(linkman);
                    if (StringUtils.isNotEmpty(userEntity.getTelephone())) {
                        receiverInfoEntity.setTelephone(userEntity.getTelephone());
                    } else if (StringUtils.isNotEmpty(tel)) {
                        receiverInfoEntity.setTelephone(tel);
                    }
                    receiverInfoEntity.setProvince(address.getProvince());
                    receiverInfoEntity.setProvinceCode(Integer.parseInt(address.getProvinceCode()));
                    receiverInfoEntity.setCity(address.getCity());
                    receiverInfoEntity.setCityCode(Integer.parseInt(address.getCityCode()));
                    receiverInfoEntity.setDistrict(address.getDistrict());
                    receiverInfoEntity.setDistrictCode(Integer.parseInt(address.getDistrictCode()));
                    receiverInfoEntity.setDetailAddress(keHuAddress);
                    receiverInfoEntity.setInputTime(new Date());

                    receiverInfoDao.insert(receiverInfoEntity);
                }

            } else {
                //存在则更新,需判断数据是否改变

                boolean updateFlag = false; //更新开关
                boolean addrFlag = false; //收件地址信息更新开关

                if (!StringUtils.isEmpty(telephone) && !telephone.equals(userEntity.getTelephone())) {
                    userEntity.setTelephone(telephone);
                    updateFlag = true;
                }
                if (!StringUtils.isEmpty(keHuAddress) && !keHuAddress.equals(userEntity.getAddress())) {
                    userEntity.setAddress(keHuAddress);
                    updateFlag = true;
                    addrFlag = true;
                }
                if (!StringUtils.isEmpty(keHu.getUserName()) && !keHu.getUserName().equals(userEntity.getUserName())) {
                    userEntity.setUserName(keHu.getUserName());
                    updateFlag = true;
                }

                if ("yiting".equals(keHu.getCompanyFalg())) {
                    log.info("更新依婷信息");

                    if (userEntity.getAttachType() != keHu.getAttachType()) {
                        userEntity.setAttachType(keHu.getAttachType());
                        updateFlag = true;
                    }
                    if (userEntity.getSaleType() != keHu.getSaleType()) {
                        userEntity.setSaleType(keHu.getSaleType());
                        updateFlag = true;
                    }
                    if (userEntity.getAttachType() == 3 && userEntity.getSaleType() == 2) {   //依婷代理
                        //获得代理商seq
                        //查询代理商是否存在，不存在则创建
                        BaseAgentEntity agentEntity = baseAgentService.selectOne(new EntityWrapper<BaseAgentEntity>()
                                .eq("AgentName", keHu.getQudaoName())
                                .eq("Remark", keHu.getQudaoCode()));
                        if (agentEntity == null) {
                            agentEntity = new BaseAgentEntity();
                            agentEntity.setAgentName(keHu.getQudaoName());
                            agentEntity.setRemark(keHu.getQudaoCode());
                            agentEntity.setBrandSeq(company.getBrandSeq());
                            baseAgentService.insert(agentEntity);
                        }

                        if (!agentEntity.getSeq().equals(userEntity.getAttachSeq())) {
                            userEntity.setAttachSeq(agentEntity.getSeq());
                            updateFlag = true;
                        }

                    } else if (userEntity.getAttachType() == 2 && userEntity.getSaleType() == 4) {  //依婷 分公司门店
                        //子公司对象
                        BaseAreaEntity subAreaEntity = baseAreaService.selectOne(new EntityWrapper<BaseAreaEntity>()
                                .ne("ParentSeq", 0)
                                .eq("Name", keHu.getQudaoName())
                                .eq("Bound", keHu.getQudaoCode()));
                        //如果区域对象不存在，则创建
                        if (subAreaEntity == null) {
                            //获取区域对象
                            BaseAreaEntity baseAreaEntity = baseAreaService.selectOne(new EntityWrapper<BaseAreaEntity>()
                                    .eq("ParentSeq", 0)
                                    .eq("Name", keHu.getQuyuName())
                                    .eq("Bound", keHu.getQuyuCode()));
                            //如果区域对象不存在，则创建
                            if (baseAreaEntity == null) {
                                baseAreaEntity = new BaseAreaEntity();
                                baseAreaEntity.setBrandSeq(company.getBrandSeq());
                                baseAreaEntity.setParentSeq(0);
                                baseAreaEntity.setName(keHu.getQuyuName());
                                baseAreaEntity.setBound(keHu.getQuyuCode());
                                baseAreaService.insert(baseAreaEntity);
                            }

                            subAreaEntity = new BaseAreaEntity();
                            subAreaEntity.setBrandSeq(company.getBrandSeq());
                            subAreaEntity.setParentSeq(baseAreaEntity.getSeq());
                            subAreaEntity.setName(keHu.getQudaoName());
                            subAreaEntity.setBound(keHu.getQudaoCode());
                            baseAreaService.insert(subAreaEntity);
                        }

                        if (!subAreaEntity.getSeq().equals(userEntity.getAttachSeq())) {
                            userEntity.setAttachSeq(subAreaEntity.getSeq());
                            updateFlag = true;
                        }
                    }
                }//yiTing if end;

                //数据有变化则更新
                if (updateFlag) {
                    userEntity.setInputTime(new Date());
                    if (userEntity.getDel() == 0) {

                        baseUserService.updateUserBySeq(userEntity);
                    } else {
                        //erp用户信息修改，若我方对应用户删除，则取消用户删除状态
                        userEntity.setDel(0);
                        baseUserService.updateUserBySeq(userEntity);
                        //【用户权限表处理】
                        OrderUserJurisdictionEntity jurisdiction = userJurisdictionDao.selectByUserSeq(userEntity.getSeq());
                        if (jurisdiction == null) {
                            //创建用户权限
                            OrderUserJurisdictionEntity jurisdictionEntity = new OrderUserJurisdictionEntity();
                            jurisdictionEntity.setUserSeq(userEntity.getSeq());
                            jurisdictionEntity.setCreateUserSeq(company.getSeq());//创建人
                            jurisdictionEntity.setEffectiveDate(DateUtils.addDateYears(new Date(), 3)); //有效期
                            jurisdictionEntity.setIsDisable(0);
                            jurisdictionEntity.setIntOfCreateUsers(0);
                            jurisdictionEntity.setAlreadyCreateUsers(0);
                            jurisdictionEntity.setIsAdministrator(0);
                            jurisdictionEntity.setDel(0);

                            userJurisdictionDao.insert(jurisdictionEntity);

                        } else {
                            if (jurisdiction.getDel() == 1) {
                                //取消删除
                                userJurisdictionDao.updateDel(userEntity.getSeq(), 0);
                            }
                        }
                    }
                }

                // 获取融云token
                Integer seq = userEntity.getSeq();
                String userName = userEntity.getUserName();
                String rongCloudToken = userEntity.getRongCloudToken();
                if (userName == null) userName = "default";
                String newRongCloudToken;
                if (org.apache.commons.lang.StringUtils.isBlank(rongCloudToken)) {
                    newRongCloudToken = rongCloudUtils.registerUser(seq, userName, "123");
                    if (newRongCloudToken != null) {
                        BaseUserEntity rongCloudUpdate = new BaseUserEntity();
                        rongCloudUpdate.setSeq(seq);
                        rongCloudUpdate.setRongCloudToken(newRongCloudToken);
                        baseUserService.updateById(rongCloudUpdate);
                    }
                } else {
                    rongCloudUtils.refreshUserInfo(userEntity.getSeq(), userName, "123");
                }

                //更新用户地址信息 , 地址和联系人不能为空, 用户地址变动 addrFlag为true
                if (addrFlag
                        && StringUtils.isNotEmpty(linkman)
                        && linkman.length() <= 15
                        && (StringUtils.isNotEmpty(telephone) && telephone.length() <= 15
                        || StringUtils.isNotEmpty(tel) && tel.length() <= 15)) {

                    MapUtils.Address address = MapUtils.getPosition(keHuAddress);
                    //用户收件信息
                    if (address != null) {
                        ReceiverInfoEntity receiverInfoEntity = new ReceiverInfoEntity();
                        receiverInfoEntity.setUserSeq(userEntity.getSeq());
                        receiverInfoEntity.setReceiverName(linkman);
                        if (StringUtils.isNotEmpty(userEntity.getTelephone())) {
                            receiverInfoEntity.setTelephone(userEntity.getTelephone());
                        } else if (StringUtils.isNotEmpty(tel)) {
                            receiverInfoEntity.setTelephone(tel);
                        }
                        receiverInfoEntity.setProvince(address.getProvince());
                        receiverInfoEntity.setProvinceCode(Integer.parseInt(address.getProvinceCode()));
                        receiverInfoEntity.setCity(address.getCity());
                        receiverInfoEntity.setCityCode(Integer.parseInt(address.getCityCode()));
                        receiverInfoEntity.setDistrict(address.getDistrict());
                        receiverInfoEntity.setDistrictCode(Integer.parseInt(address.getDistrictCode()));
                        receiverInfoEntity.setDetailAddress(keHuAddress);
                        receiverInfoEntity.setInputTime(new Date());
                        List<ReceiverInfoEntity> receiverInfoEntityList = receiverInfoDao.selectList(new EntityWrapper<ReceiverInfoEntity>()
                                .eq("User_Seq", userEntity.getSeq()));

                        if (receiverInfoEntityList != null && receiverInfoEntityList.size() > 0) {
                            receiverInfoDao.update(receiverInfoEntity, new EntityWrapper<ReceiverInfoEntity>()
                                    .eq("User_Seq", userEntity.getSeq()));
                        } else {
                            receiverInfoDao.insert(receiverInfoEntity);
                        }
                    }

                }

            }

        }//for循环结束

    }

    /**
     * 根据工厂id同步所有商品erp库存，颜色、尺码数据本地必须存在
     *
     * @param baseESmartEntity
     * @param url
     */
    @Override
    public void selectGoodsStock(BaseESmartEntity baseESmartEntity, String url) {
        Integer companySeq = baseESmartEntity.getCompanySeq();
        String key = baseESmartEntity.getSmartKey();
        String secret = baseESmartEntity.getSmartSecret();

        //获取要查询的商品id集合
        List<String> goodsIDs = goodsShoesDao.selectGoodsIDsByCompanySeq(companySeq);

        JSONObject data = HttpRequestUtils.getResponseResult(url, key, secret, goodsIDs);

        if (data == null) {
            log.info("esmart异常，未获取到数据，请检查");
            return;
        }

        //响应结果状态码
        Integer status = data.getInteger("code");
        String msg = data.getString("msg");

        if (status != 1) {
            log.info("商品库存查询-数据异常:{} {}", status, msg);
            return;
        }

        //解析json数据
        JSONArray result = data.getJSONArray("result");

        for (int i = 0; i < result.size(); i++) {
            JSONObject goodsDataVo = result.getJSONObject(i);
            String goodsId = goodsDataVo.getString("goodsCode");
            JSONArray stocks = goodsDataVo.getJSONArray("stocks");

            //库存记录为空则跳过
            if (stocks.isEmpty()) {
                continue;
            }

            //根据商品代码查询商品seq
            Integer goodsSeq = goodsShoesDao.selectGoodsSeqByGoodsID(companySeq, goodsId);
            if (goodsSeq == null) {
                log.info("商品库存查询—商品查询异常: (goodsCode:{})", goodsId);
                continue;
            }

            for (int j = 0; j < stocks.size(); j++) {
                GoodsStock goodsStock = stocks.getObject(j, GoodsStock.class);
                String colorCode = goodsStock.getColorCode().trim();
                String sizeCode = goodsStock.getSizeCode().trim();

                //根据颜色代码查询颜色seq（del=0）
                Integer colorSeq = goodsColorService.selectColorSeqByCode(companySeq, colorCode);
                //根据尺码代码查询尺码seq（del=0）
                Integer sizeSeq = goodsSizeService.getSizeSeqByCodeAndCompanySeq(companySeq, sizeCode);

                if (colorSeq == null) {
                    log.info("商品库存查询—颜色查询异常(goodsCode:{}, colorCode:{} )", goodsId, colorCode);
                    continue;
                }
                if (sizeSeq == null) {
                    log.info("商品库存查询—尺码查询异常(goodsCode:{}, SizeCode:{} )", goodsId, sizeCode);
                    continue;
                }

                //根据商品、颜色、尺码 判断数据是否存在
                ShoesDataEntity shoesDataEntity = shoesDataDao.selectByGoodsseqAndColorseqAndSizeseq(goodsSeq, colorSeq, sizeSeq);

                if (shoesDataEntity == null) {
                    shoesDataEntity = new ShoesDataEntity();
                    shoesDataEntity.setShoesSeq(goodsSeq);
                    shoesDataEntity.setColorSeq(colorSeq);
                    shoesDataEntity.setSizeSeq(sizeSeq);
                    shoesDataEntity.setNum(goodsStock.getCount());
                    shoesDataEntity.setStock(goodsStock.getCount());
                    shoesDataDao.insert(shoesDataEntity);
                } else {
                    //库存改变则更新
                    if (shoesDataEntity.getStock() != goodsStock.getCount()) {
                        shoesDataEntity.setStock(goodsStock.getCount());
                        shoesDataEntity.setNum(goodsStock.getCount());
                        shoesDataEntity.setStockDate(new Date());

                        shoesDataDao.updateById(shoesDataEntity);
                    }

                }

            }

        }//result for循环结束

    }

    @Override
    public void selectOrder(BaseESmartEntity baseESmartEntity, String url) {
        Integer company_seq = baseESmartEntity.getCompanySeq();
        String key = baseESmartEntity.getSmartKey();
        String secret = baseESmartEntity.getSmartSecret();
        JSONObject resultJson = HttpRequestUtils.getResponseResult(url, key, secret);
        if (resultJson == null) {
            log.error("order result is null");
            return;
        }
        int code = resultJson.getIntValue("code");
        if (code != 1) {
            log.error("select order from:" + company_seq + ", key:" + key + "\n"
                    + resultJson.getString("msg"));
        } else {
            JSONArray orderArray = resultJson.getJSONArray("result");
            int length = orderArray.size();
            for (int i = 0; i < length; i++) {
                JSONObject orderObj = orderArray.getJSONObject(i);
                String orderNum = orderObj.getString("orderNum");
                List<OrderEntity> orderList = orderManageDao.selectList(new EntityWrapper<OrderEntity>().eq("OrderNum", orderNum));
                OrderEntity orderEntity;
                if (orderList.isEmpty()) {
                    orderEntity = new OrderEntity();
                    orderEntity.setOrderNum(orderNum);
                    orderEntity.setCompanySeq(company_seq);

                    String accountName = baseESmartEntity.getUserPrefix() + orderObj.getString("accountName");
                    List<BaseUserEntity> userList = baseUserService.selectList(new EntityWrapper<BaseUserEntity>().eq("AccountName", accountName));
                    if (!userList.isEmpty()) {
                        Integer userSeq = userList.get(0).getSeq();
                        orderEntity.setUserSeq(userSeq);
                    } else {
                        log.error("error 账号不存在:" + accountName);
                        continue;
                    }
                    String inputTimeString = orderObj.getString("inputTime");
                    orderEntity.setInputTime(DateUtils.parse(inputTimeString, DateUtils.DATE_TIME_PATTERN));
                } else {
                    orderEntity = orderList.get(0);
                }
                orderEntity.setOrderPrice(new BigDecimal(orderObj.getFloatValue("orderPrice")));
                orderEntity.setPaid(new BigDecimal(orderObj.getFloatValue("paid")));
                orderEntity.setRemark(orderObj.getString("remark"));
                orderEntity.setOrderStatus(orderObj.getIntValue("orderStatus"));

                if (orderEntity.getSeq() != null) {
                    orderManageDao.updateById(orderEntity);
                } else {
                    orderManageDao.insert(orderEntity);
                }

                Integer orderSeq = orderEntity.getSeq();
                JSONArray orderProducts = orderObj.getJSONArray("mxList");
                int productsLength = orderProducts.size();
                for (int productIndex = 0; productIndex < productsLength; productIndex++) {
                    JSONObject orderProductJson = orderProducts.getJSONObject(productIndex);
                    String goodsNum = orderProductJson.getString("goodsSeq");
                    String colorCode = orderProductJson.getString("colorSeq");
                    String sizeCode = orderProductJson.getString("sizeSeq");
                    int buyCount = orderProductJson.getIntValue("buyCount");
                    int deliverNum = orderProductJson.getIntValue("deliverNum");
                    float productPrice = orderProductJson.getFloatValue("productPrice");

                    Integer shoesSeq = null, colorSeq = null, sizeSeq = null;
                    List<GoodsShoesEntity> shoesEntities = goodsShoesDao.selectList(new EntityWrapper<GoodsShoesEntity>().eq("GoodID", goodsNum));
                    if (!shoesEntities.isEmpty()) {
                        shoesSeq = shoesEntities.get(0).getSeq();
                    }
                    List<GoodsColorEntity> colorList = goodsColorService.selectList(new EntityWrapper<GoodsColorEntity>().eq("Company_Seq", company_seq).eq("Code", colorCode));
                    if (!colorList.isEmpty()) {
                        colorSeq = colorList.get(0).getSeq();
                    }

                    List<GoodsSizeEntity> sizeList = goodsSizeService.selectList(new EntityWrapper<GoodsSizeEntity>().eq("Company_Seq", company_seq).eq("SizeCode", sizeCode));
                    if (!sizeList.isEmpty()) {
                        sizeSeq = sizeList.get(0).getSeq();
                    }

                    if (shoesSeq != null && colorSeq != null && sizeSeq != null) {
                        List<ShoesDataEntity> shoesDataList = shoesDataDao.selectList(new EntityWrapper<ShoesDataEntity>().eq("Shoes_Seq", shoesSeq).eq("Color_Seq", colorSeq).eq("Size_Seq", sizeSeq));
                        if (!shoesDataList.isEmpty()) {
                            Integer shoesDataSeq = shoesDataList.get(0).getSeq();
                            OrderProductsEntity orderProductsEntity;
                            List<OrderProductsEntity> orderProductsEntities = orderProductManagementDao.selectList(new EntityWrapper<OrderProductsEntity>().eq("Order_Seq", orderSeq).eq("ShoesData_Seq", shoesDataSeq));
                            if (orderProductsEntities.isEmpty()) {
                                orderProductsEntity = new OrderProductsEntity();
                            } else {
                                orderProductsEntity = orderProductsEntities.get(0);
                                orderProductsEntity.setOrderSeq(orderSeq);
                                orderProductsEntity.setShoesDataSeq(shoesDataSeq);
                            }

                            orderProductsEntity.setBuyCount(buyCount);
                            orderProductsEntity.setDeliverNum(deliverNum);
                            orderProductsEntity.setProductPrice(new BigDecimal(productPrice));

                            if (orderProductsEntity.getSeq() != null) {
                                orderProductManagementDao.updateById(orderProductsEntity);
                            } else {
                                orderProductManagementDao.insert(orderProductsEntity);
                            }
                        } else {
                            log.error("shoes data in empty. shoesSeq:" + shoesSeq + ",colorSeq:" + colorSeq + ",sizeSeq:" + sizeSeq);
                        }
                    } else {
                        log.error("shoesSeq:" + shoesSeq + ",colorSeq:" + colorSeq + ",sizeSeq:" + sizeSeq);
                    }
                }

                // TODO 已发货数据同步
            }
        }
    }

    /**
     * @param companySeq
     * @param orderSeq
     * @return -1： 无需导入或已导入 0：导入成功 1：导入失败
     */
    @Override
    public int importOrder(Integer companySeq, Integer orderSeq) {
        try {
            BaseESmartEntity eSmartEntity = baseESmartService.selectOne(new EntityWrapper<BaseESmartEntity>().eq("Company_Seq", companySeq));
            Map<String, Object> map = orderManageDao.getErpOrder(orderSeq);

            //判断订单的ERP导入状态是否为待导入（在生成订单时(提交订单、拆单)，会根据提交人类型、所属公司类型设置导入ERP状态初始值）
            if (map.get("importErpState") == null || ((Integer) map.get("importErpState")) != 0) {
                return -1;
            }
            //判断ERP的订单类型（目前分为0: 门店配货单(默认) 1:渠道调拨单 2:批发销货单）
            int eRPOrderType = 0;
            //如果是依婷公主的订单，单独判断
            if (configUtils.getYtgzCompanyseq().equals((Integer) map.get("companySeq"))) {
                if ((Integer) map.get("attachType") == 1 && (Integer) map.get("saleType") == 4) {  //工厂 + 直营店
                    eRPOrderType = 0;
                } else if ((Integer) map.get("attachType") == 2 && (Integer) map.get("saleType") == 4) {  //分公司 + 直营店
                    eRPOrderType = 1;
                } else if ((Integer) map.get("attachType") == 3) {  //代理商
                    String userSeq = (String) map.get("userSeq");
                    // ZD开头的为总代
                    if (userSeq.startsWith("ytZD")) {
                        eRPOrderType = 2;
                    } else {
                        eRPOrderType = 1;
                    }
                } else if ((Integer) map.get("attachType") == 1 && (Integer) map.get("saleType") == 3) {  //工厂+ 批发
                    eRPOrderType = 2;
                }
            }
            map.put("eRPOrderType", eRPOrderType);

            String userAccount = (String) map.get("userSeq");
            userAccount = userAccount.replaceFirst(eSmartEntity.getUserPrefix(), "");
            map.put("userSeq", userAccount);
            System.out.println(JSON.toJSONString(map));

            JSONObject jsonObject = HttpRequestUtils.getResponseResult(eSmartConfig.getUrl() + "dingdan.import/v1.0", eSmartEntity.getSmartKey(), eSmartEntity.getSmartSecret(), map);
            log.info(jsonObject.toJSONString());
            if (jsonObject != null && jsonObject.getInteger("code") == 1) {  //{"msg":"操作成功","code":1}
                //修改订单导入ERP状态为1
                OrderEntity orderEntity = new OrderEntity();
                orderEntity.setSeq(orderSeq);
                orderEntity.setImportErpState(1);
                orderManageDao.updateById(orderEntity);
                return 0;
            } else {
                return 1;
            }
        } catch (Exception e) {
            log.error("查询工厂对应的ESmart平台key失败！", e);
            return 1;
        }
    }

    /**
     * @param baseESmartEntity
     * @param url
     */
    @Override
    public void selectGoods(BaseESmartEntity baseESmartEntity, String url) {
        Integer company_seq = baseESmartEntity.getCompanySeq();
        String key = baseESmartEntity.getSmartKey();
        String secret = baseESmartEntity.getSmartSecret();
        //获取品牌序号
        BaseBrandEntity baseBrandEntity = new BaseBrandEntity();
        baseBrandEntity.setCompanySeq(company_seq);
        BaseBrandEntity brandEntity = baseBrandDao.selectOne(baseBrandEntity);

        if (brandEntity == null) {
            log.info("商品同步异常——未查询到品牌信息");
            return;
        }

        JSONObject data = HttpRequestUtils.getResponseResult(url, key, secret);

        if (data == null) {
            log.info("esmart异常，未获取到数据，请检查");
            return;
        }

        //响应结果状态码
        Integer status = data.getInteger("code");
        String msg = data.getString("msg");

        if (status != 1) {
            log.info("商品查询-数据异常:{} {}", status, msg);
            return;
        }

        JSONArray goodsList = data.getJSONArray("result");

        for (int i = 0; i < goodsList.size(); i++) {
            GoodsVo goodsVo = goodsList.getObject(i, GoodsVo.class);

            Integer year = goodsVo.getYear();
            String goodsName = goodsVo.getName();
            String seasonName = goodsVo.getSeasonName();

            if (year == null || year == 0 || StringUtils.isEmpty(seasonName) || StringUtils.isEmpty(goodsName)) {
                log.info("商品同步——异常数据: year:{},seasonName:{},goodsName:{}", year, seasonName, goodsName);
                continue;
            }

            goodsName = goodsName.trim();
            seasonName = seasonName.trim();

            GoodsShoesEntity goodsShoesEntity = new GoodsShoesEntity();
            goodsShoesEntity.setCompanySeq(company_seq);
            goodsShoesEntity.setGoodID(goodsVo.getCode());
            //判断该商品是否存在
            GoodsShoesEntity shoesEntity = goodsShoesDao.selectOne(goodsShoesEntity);
            //获取分类序号
            GoodsCategoryEntity categoryEntity = goodsCategoryService.selectOne(new EntityWrapper<GoodsCategoryEntity>()
                    .eq("Company_Seq", company_seq)
                    .eq("Category_Code", goodsVo.getCategoryCode()));

            //获取波次序号
            GoodsPeriodEntity goodsPeriodEntity = goodsPeriodService.selectOne(new EntityWrapper<GoodsPeriodEntity>()
                    .eq("Brand_Seq", brandEntity.getSeq())
                    .eq("Year", year)
                    .eq("Name", year + seasonName));

            if (goodsPeriodEntity == null) {
                goodsPeriodEntity = new GoodsPeriodEntity();
                goodsPeriodEntity.setBrandSeq(brandEntity.getSeq());
                goodsPeriodEntity.setYear(year);
                goodsPeriodEntity.setName(year + seasonName);
                goodsPeriodEntity.setSaleDate(new Date());
                goodsPeriodService.insert(goodsPeriodEntity);
            }

            Integer shoesSeq;
            if (shoesEntity == null) {
                if (categoryEntity != null) {
                    goodsShoesEntity.setCategorySeq(categoryEntity.getSeq());
                }

                goodsShoesEntity.setPeriodSeq(goodsPeriodEntity.getSeq());
                goodsShoesEntity.setGoodName(goodsName);
                goodsShoesEntity.setSX1(goodsVo.getSx1());
                goodsShoesEntity.setSX2(goodsVo.getSx2());
                goodsShoesEntity.setSX3(goodsVo.getSx3());
                goodsShoesEntity.setSX4(goodsVo.getSx4());
                goodsShoesEntity.setSX5(goodsVo.getSx5());
                goodsShoesEntity.setSX6(goodsVo.getSx6());
                goodsShoesEntity.setSX7(goodsVo.getSx7());
                goodsShoesEntity.setSX8(goodsVo.getSx8());
                goodsShoesEntity.setSX9(goodsVo.getSx9());
                goodsShoesEntity.setSX10(goodsVo.getSx10());
                goodsShoesEntity.setSX11(goodsVo.getSx11());
                goodsShoesEntity.setSX12(goodsVo.getSx12());
                goodsShoesEntity.setSX13(goodsVo.getSx13());
                goodsShoesEntity.setSX14(goodsVo.getSx14());
                goodsShoesEntity.setSX15(goodsVo.getSx15());
                goodsShoesEntity.setSX16(goodsVo.getSx16());
                goodsShoesDao.insert(goodsShoesEntity);
                shoesSeq = goodsShoesEntity.getSeq();
            } else {
                boolean updateFlag = false;
                if (goodsPeriodEntity.getSeq() != shoesEntity.getPeriodSeq()) {
                    shoesEntity.setPeriodSeq(goodsPeriodEntity.getSeq());
                    updateFlag = true;
                }
                if (categoryEntity.getSeq() != null && !categoryEntity.getSeq().equals(shoesEntity.getCategorySeq())) {
                    shoesEntity.setCategorySeq(categoryEntity.getSeq());
                    updateFlag = true;
                }

                if (StringUtils.isNotEmpty(goodsName) && !goodsName.equals(shoesEntity.getGoodName())) {
                    shoesEntity.setGoodName(goodsName);
                    updateFlag = true;
                }
                if (StringUtils.isNotEmpty(goodsVo.getSx1()) && !goodsVo.getSx1().equals(shoesEntity.getSX1())) {
                    shoesEntity.setSX1(goodsVo.getSx1());
                    updateFlag = true;
                }
                if (StringUtils.isNotEmpty(goodsVo.getSx2()) && !goodsVo.getSx2().equals(shoesEntity.getSX2())) {
                    shoesEntity.setSX2(goodsVo.getSx2());
                    updateFlag = true;
                }
                if (StringUtils.isNotEmpty(goodsVo.getSx3()) && !goodsVo.getSx3().equals(shoesEntity.getSX3())) {
                    shoesEntity.setSX3(goodsVo.getSx3());
                    updateFlag = true;
                }
                if (StringUtils.isNotEmpty(goodsVo.getSx4()) && !goodsVo.getSx4().equals(shoesEntity.getSX4())) {
                    shoesEntity.setSX4(goodsVo.getSx4());
                    updateFlag = true;
                }
                if (StringUtils.isNotEmpty(goodsVo.getSx5()) && !goodsVo.getSx5().equals(shoesEntity.getSX5())) {
                    shoesEntity.setSX5(goodsVo.getSx5());
                    updateFlag = true;
                }
                if (StringUtils.isNotEmpty(goodsVo.getSx6()) && !goodsVo.getSx6().equals(shoesEntity.getSX6())) {
                    shoesEntity.setSX6(goodsVo.getSx6());
                    updateFlag = true;
                }
                if (StringUtils.isNotEmpty(goodsVo.getSx7()) && !goodsVo.getSx7().equals(shoesEntity.getSX7())) {
                    shoesEntity.setSX7(goodsVo.getSx7());
                    updateFlag = true;
                }
                if (StringUtils.isNotEmpty(goodsVo.getSx8()) && !goodsVo.getSx8().equals(shoesEntity.getSX8())) {
                    shoesEntity.setSX8(goodsVo.getSx8());
                    updateFlag = true;
                }
                if (StringUtils.isNotEmpty(goodsVo.getSx9()) && !goodsVo.getSx9().equals(shoesEntity.getSX9())) {
                    shoesEntity.setSX9(goodsVo.getSx9());
                    updateFlag = true;
                }
                if (StringUtils.isNotEmpty(goodsVo.getSx10()) && !goodsVo.getSx10().equals(shoesEntity.getSX10())) {
                    shoesEntity.setSX10(goodsVo.getSx10());
                    updateFlag = true;
                }
                if (StringUtils.isNotEmpty(goodsVo.getSx11()) && !goodsVo.getSx11().equals(shoesEntity.getSX11())) {
                    shoesEntity.setSX11(goodsVo.getSx11());
                    updateFlag = true;
                }
                if (StringUtils.isNotEmpty(goodsVo.getSx12()) && !goodsVo.getSx12().equals(shoesEntity.getSX12())) {
                    shoesEntity.setSX12(goodsVo.getSx12());
                    updateFlag = true;
                }
                if (StringUtils.isNotEmpty(goodsVo.getSx13()) && !goodsVo.getSx13().equals(shoesEntity.getSX13())) {
                    shoesEntity.setSX13(goodsVo.getSx13());
                    updateFlag = true;
                }
                if (StringUtils.isNotEmpty(goodsVo.getSx14()) && !goodsVo.getSx14().equals(shoesEntity.getSX14())) {
                    shoesEntity.setSX14(goodsVo.getSx14());
                    updateFlag = true;
                }
                if (StringUtils.isNotEmpty(goodsVo.getSx15()) && !goodsVo.getSx15().equals(shoesEntity.getSX15())) {
                    shoesEntity.setSX15(goodsVo.getSx15());
                    updateFlag = true;
                }
                if (StringUtils.isNotEmpty(goodsVo.getSx16()) && !goodsVo.getSx16().equals(shoesEntity.getSX16())) {
                    shoesEntity.setSX16(goodsVo.getSx16());
                    updateFlag = true;
                }

                if (updateFlag) {
                    shoesEntity.setInputTime(new Date());
                    goodsShoesDao.updateById(shoesEntity);
                }
                shoesSeq = shoesEntity.getSeq();
            }

            //同步价格
            List<ShoesInfoEntity> infoEntities = shoesInfoDao.selectList(new EntityWrapper<ShoesInfoEntity>().eq("Shoes_Seq", shoesSeq));
            ShoesInfoEntity shoesInfoEntity;
            if (infoEntities == null || infoEntities.isEmpty()) {
                shoesInfoEntity = new ShoesInfoEntity();
                shoesInfoEntity.setShoesSeq(shoesSeq);
            } else {
                shoesInfoEntity = infoEntities.get(0);
            }
            shoesInfoEntity.setWholesalerPrice(goodsVo.getSj2());
            shoesInfoEntity.setStorePrice(goodsVo.getSalePrice());
            shoesInfoEntity.setOemPrice(goodsVo.getSj4());
            shoesInfoEntity.setSalePrice(new BigDecimal(0));
//            shoesInfoEntity.setIsHotSale(0);
//            shoesInfoEntity.setIsNewest(0);
//            shoesInfoEntity.setOnSale(1);

            if (shoesInfoEntity.getSeq() != null) {
                shoesInfoDao.updateById(shoesInfoEntity);
            } else {
                shoesInfoDao.insert(shoesInfoEntity);
            }

            //增加鞋子对批发、贴牌、总代可见
            List<ShoesCompanyTypeEntity> typeEntities = shoesCompanyTypeDao.selectList(new EntityWrapper<ShoesCompanyTypeEntity>().eq("Shoes_Seq", shoesSeq));
            List<Integer> list = new ArrayList<Integer>() {{
                add(1);
                add(2);
                add(3);
                add(4);
            }};
            if (typeEntities == null || typeEntities.isEmpty()) {
                shoesCompanyTypeDao.insertBatch(shoesSeq, list);
            } else {
                for (ShoesCompanyTypeEntity typeEntity : typeEntities) {
                    list.remove(typeEntity.getCompanyTypeSeq());
                }
                if (!list.isEmpty()) {
                    shoesCompanyTypeDao.insertBatch(shoesSeq, list);
                }
            }
        }

    }

    /**
     * 鞋子品牌同步
     *
     * @param baseESmartEntity
     * @param url
     */
    @Override
    public void selectBrands(BaseESmartEntity baseESmartEntity, String url) {
        Integer company_seq = baseESmartEntity.getCompanySeq();
        String key = baseESmartEntity.getSmartKey();
        String secret = baseESmartEntity.getSmartSecret();

        //发送post请求
        JSONObject data = HttpRequestUtils.getResponseResult(url, key, secret);

        if (data == null) {
            log.info("esmart异常，未获取到品牌，请检查");
            return;
        }

        //响应结果状态码
        Integer status = data.getInteger("code");
        if (status != 1) {
            log.info("品牌查询-响应结果状态码:{}", status);
            return;
        }

        //解析json数据
        JSONArray result = data.getJSONArray("result");

        for (int i = 0; i < result.size(); i++) {
            JSONObject obj = result.getJSONObject(i);

            String brandCode = obj.getString("brandCode");
            String brandName = obj.getString("brandName");

            if (StringUtils.isEmpty(brandCode) || StringUtils.isEmpty(brandName)) {
                log.info("品牌同步-异常颜色：brandCode:{};brandName:{};company_seq:{}", brandCode, brandName, company_seq);
                continue;
            }

            brandCode = brandCode.trim();
            brandName = brandName.trim();

            List<GoodsBrandEntity> goodsBrands = goodsBrandService.selectList(new EntityWrapper<GoodsBrandEntity>()
                    .eq("BrandCode", brandCode).
                            eq("Company_Seq", company_seq));

            if (goodsBrands.size() > 0) {

                for (GoodsBrandEntity goodsBrand : goodsBrands) {
                    if (!brandName.equals(goodsBrand.getBrandName())) {
                        //品牌名不相同则更新
                        goodsBrand.setBrandName(brandName);
                        goodsBrand.setInputTime(new Date());
                        goodsBrandService.updateById(goodsBrand);
                    }
                }

                if (goodsBrands.size() > 1) {
                    log.info("品牌同步-存在重复品牌:company_seq:{};colorCode:{}", company_seq, goodsBrands.get(0).getBrandCode());
                }
            } else {
                //不存在则创建对象并插入
                GoodsBrandEntity goodsBrandEntity = new GoodsBrandEntity();
                goodsBrandEntity.setCompanySeq(company_seq);
                goodsBrandEntity.setBrandName(brandName);
                goodsBrandEntity.setBrandCode(brandCode);
                goodsBrandEntity.setDel(0);

                goodsBrandService.insert(goodsBrandEntity);

            }

        }

    }

    /**
     * 获取当天的订单商品同步到ERP
     *
     * @param baseESmartEntity
     * @param url
     */
    @Override
    public void importGoodsToERP(BaseESmartEntity baseESmartEntity, String url) {
        Integer company_seq = baseESmartEntity.getCompanySeq();
        String key = baseESmartEntity.getSmartKey();
        String secret = baseESmartEntity.getSmartSecret();

        //查询订单需要同步到erp的商品
        //根据工厂号获取当天所有订单序号
        List<Integer> orderSeqs = orderDao.getOrderSeqsByCompanySeqAndToday(company_seq);
        if (orderSeqs == null || orderSeqs.isEmpty()) {
            log.info("erp商品同步任务异常，未查询到订单号，无法继续执行");
            return;
        }
        //根据订单序号获取鞋子数据序号
        List<Integer> shoeDataSeqs = orderProductsDao.getShoeDataSeqsByOrderseqs(orderSeqs);
        if (shoeDataSeqs == null || shoeDataSeqs.isEmpty()) {
            log.info("erp商品同步任务异常，未查询到订单商品数据，无法继续执行");
            return;
        }
        //根据鞋子数据序号获取鞋子序号
        List<Integer> shoesSeqs = shoesDataDao.getShoesSeqsByShoesDataSeqs(shoeDataSeqs);
        if (shoesSeqs == null || shoesSeqs.isEmpty()) {
            log.info("erp商品同步任务异常，未查询到订单鞋子，无法继续执行");
            return;
        }
        //根据鞋子序号获取鞋子数据
        List<GoodsVo> goodsVos = goodsShoesDao.getShoesDataToERP(shoesSeqs);

        if (goodsVos == null || goodsVos.isEmpty()) {
            log.info("erp商品同步任务异常，未查询到订单鞋子数据，无法继续执行");
            return;
        }

        //查询鞋子数据信息
        for (GoodsVo goodsVo : goodsVos) {
            List<GoodsStock> shoesDatas = shoesDataDao.getShoeDataByShoeSeq(goodsVo.getSeq());
            goodsVo.setStocks(shoesDatas);
        }

//        goodsVos.stream().forEach(System.out::print);//控制台打印 测试

        JSONObject data = HttpRequestUtils.getResponseResult(url, key, secret, goodsVos);
        log.info("同步商品信息到ERP: {}", data.toJSONString());

    }

}
