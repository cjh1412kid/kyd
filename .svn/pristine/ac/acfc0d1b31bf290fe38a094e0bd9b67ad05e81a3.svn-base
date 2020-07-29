package com.nuite.modules.erp.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.nuite.common.utils.DateUtils;
import com.nuite.datasources.DataSourceNames;
import com.nuite.datasources.annotation.DataSource;
import com.nuite.modules.erp.bserp.dao.*;
import com.nuite.modules.erp.bserp.entity.*;
import com.nuite.modules.erp.config.ErpConfig;
import com.nuite.modules.erp.entity.vo.GoodsDataVo;
import com.nuite.modules.erp.entity.vo.GoodsSXVo;
import com.nuite.modules.erp.entity.vo.OrderGoodsVo;
import com.nuite.modules.erp.entity.vo.OrderVo;
import com.nuite.modules.erp.entity.yiting.dao.YTKeHuDao;
import com.nuite.modules.erp.entity.yiting.entity.YTKeHu;
import com.nuite.modules.erp.service.CommonErpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: yangchuang
 * @Date: 2018/8/20 10:26
 * @Version: 1.0
 * @Description:
 */

@Slf4j
@Service("YitingBsErpService")
public class YiTingBsErpServiceImpl implements CommonErpService {

    @Autowired
    ErpConfig erpConfig;

    @Autowired
    SizesDao sizesDao;

    @Autowired
    ColorsDao colorsDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    CangKuDao cangKuDao;

    @Autowired
    GoodsStockDao goodsStockDao;

    @Autowired
    ShangPinDao shangPinDao;

    @Autowired
    FJSX1Dao fjsx1Dao;

    @Autowired
    FJSX2Dao fjsx2Dao;

    @Autowired
    FJSX3Dao fjsx3Dao;

    @Autowired
    FJSX4Dao fjsx4Dao;

    @Autowired
    FJSX5Dao fjsx5Dao;

    @Autowired
    FJSX6Dao fjsx6Dao;

    @Autowired
    FJSX7Dao fjsx7Dao;

    @Autowired
    FJSX8Dao fjsx8Dao;

    @Autowired
    FJSX9Dao fjsx9Dao;

    @Autowired
    FJSX10Dao fjsx10Dao;

    @Autowired
    FJSX11Dao fjsx11Dao;

    @Autowired
    FJSX12Dao fjsx12Dao;

    @Autowired
    FJSX13Dao fjsx13Dao;

    @Autowired
    FJSX14Dao fjsx14Dao;

    @Autowired
    FJSX15Dao fjsx15Dao;

    @Autowired
    FJSX16Dao fjsx16Dao;

    @Autowired
    DingdanDao dingdanDao;

    @Autowired
    DingdanDBDao dingdanDBDao;

    @Autowired
    DingdanPFDao dingdanPFDao;

    @Autowired
    DingdanMXDao dingdanMXDao;

    @Autowired
    DingdanDBMXDao dingdanDBMXDao;

    @Autowired
    DingdanPFMXDao dingdanPFMXDao;

    @Autowired
    KeHuDao keHuDao;

    @Autowired
    YTKeHuDao ytKeHuDao;

    @DataSource(name = DataSourceNames.YITING)
    @Override
    public List<GoodsSizeEntity> getAllGoodsSizes() {
        return sizesDao.selectList(null);
    }

    @DataSource(name = DataSourceNames.YITING)
    @Override
    public List<GoodsColorEntity> getAllGoodsColor() {
        return colorsDao.selectList(null);
    }

    @DataSource(name = DataSourceNames.YITING)
    @Override
    public List<GoodsCategoryEntity> getAllGoodsCategory() {
        return categoryDao.selectList(null);
    }

    @DataSource(name = DataSourceNames.YITING)
    @Override
    public List getAllSXandOption() {
        GoodsSXVo sx1 = new GoodsSXVo();
        sx1.setSxId("SX1");
        sx1.setSxName("属性1");
        sx1.setOptions(fjsx1Dao.selectList(null));

        GoodsSXVo sx2 = new GoodsSXVo();
        sx2.setSxId("SX2");
        sx2.setSxName("属性2");
        sx2.setOptions(fjsx2Dao.selectList(null));

        GoodsSXVo sx3 = new GoodsSXVo();
        sx3.setSxId("SX3");
        sx3.setSxName("属性3");
        sx3.setOptions(fjsx3Dao.selectList(null));

        GoodsSXVo sx4 = new GoodsSXVo();
        sx4.setSxId("SX4");
        sx4.setSxName("属性4");
        sx4.setOptions(fjsx4Dao.selectList(null));

        GoodsSXVo sx5 = new GoodsSXVo();
        sx5.setSxId("SX5");
        sx5.setSxName("属性5");
        sx5.setOptions(fjsx5Dao.selectList(null));

        GoodsSXVo sx6 = new GoodsSXVo();
        sx6.setSxId("SX6");
        sx6.setSxName("属性6");
        sx6.setOptions(fjsx6Dao.selectList(null));

        GoodsSXVo sx7 = new GoodsSXVo();
        sx7.setSxId("SX7");
        sx7.setSxName("属性7");
        sx7.setOptions(fjsx7Dao.selectList(null));

        GoodsSXVo sx8 = new GoodsSXVo();
        sx8.setSxId("SX8");
        sx8.setSxName("属性8");
        sx8.setOptions(fjsx8Dao.selectList(null));

        GoodsSXVo sx9 = new GoodsSXVo();
        sx9.setSxId("SX9");
        sx9.setSxName("属性9");
        sx9.setOptions(fjsx9Dao.selectList(null));

        GoodsSXVo sx10 = new GoodsSXVo();
        sx10.setSxId("SX10");
        sx10.setSxName("属性10");
        sx10.setOptions(fjsx10Dao.selectList(null));

        GoodsSXVo sx11 = new GoodsSXVo();
        sx11.setSxId("SX11");
        sx11.setSxName("属性11");
        sx11.setOptions(fjsx11Dao.selectList(null));

        GoodsSXVo sx12 = new GoodsSXVo();
        sx12.setSxId("SX12");
        sx12.setSxName("属性12");
        sx12.setOptions(fjsx12Dao.selectList(null));

        GoodsSXVo sx13 = new GoodsSXVo();
        sx13.setSxId("SX13");
        sx13.setSxName("属性13");
        sx13.setOptions(fjsx13Dao.selectList(null));

        GoodsSXVo sx14 = new GoodsSXVo();
        sx14.setSxId("SX14");
        sx14.setSxName("属性14");
        sx14.setOptions(fjsx14Dao.selectList(null));

        GoodsSXVo sx15 = new GoodsSXVo();
        sx15.setSxId("SX15");
        sx15.setSxName("属性15");
        sx15.setOptions(fjsx15Dao.selectList(null));

        GoodsSXVo sx16 = new GoodsSXVo();
        sx16.setSxId("SX16");
        sx16.setSxName("属性16");
        sx16.setOptions(fjsx16Dao.selectList(null));

        List<GoodsSXVo> result = new ArrayList();
        result.add(sx1);
        result.add(sx2);
        result.add(sx3);
        result.add(sx4);
        result.add(sx5);
        result.add(sx6);
        result.add(sx7);
        result.add(sx8);
        result.add(sx9);
        result.add(sx10);
        result.add(sx11);
        result.add(sx12);
        result.add(sx13);
        result.add(sx14);
        result.add(sx15);
        result.add(sx16);

        return result;
    }

    @DataSource(name = DataSourceNames.YITING)
    @Override
    public List<YTKeHu> getAllKeHu() {

        List<YTKeHu> ytKeHus = ytKeHuDao.selectList();
        for (YTKeHu keHu : ytKeHus) {
            if (keHu.getUserType().equals("0")) {  //代理
                if (keHu.getAccountName().startsWith("ZD")) {
                    keHu.setAttachType(3);
                    keHu.setSaleType(2);
                } else { //供应商  //不以ZD、HS、D开头
                    keHu.setAttachType(1);
                    keHu.setSaleType(3);
                }

            } else if (keHu.getUserType().equals("2")) { //门店
                if (keHu.getAccountName().startsWith("A")) {   //总部直营
                    keHu.setAttachType(1);
                    keHu.setSaleType(4);
                } else {   //不以D、FH、A开头
                    keHu.setAttachType(2);          //分公司直营
                    keHu.setSaleType(4);
                }
            } else if (keHu.getUserType().equals("3")) { //代理
                keHu.setAttachType(3);
                keHu.setSaleType(2);

            } else {
                log.info("依婷客户查询异常。。。");
            }
        }
        return ytKeHus;
    }

    @DataSource(name = DataSourceNames.YITING)
    @Override
    public List<GoodsDataVo> getStockInfo(List<String> goodIds) {
        log.info("查询库存的商品数量: {}, {}", goodIds.size(), goodIds);

        List<GoodsDataVo> result = new ArrayList<>();
        for (String goodId : goodIds) {
            goodId = goodId.trim();
            List<GoodsStock> goodsStocks = goodsStockDao.selectList(new EntityWrapper<GoodsStock>().eq("SPDM", goodId).eq("CKDM", "000"));

            if (goodsStocks == null || goodsStocks.isEmpty()) {
                continue;
            }

            List<GoodsStock> newStocks = new ArrayList<>();

            for (GoodsStock stock : goodsStocks) {
                boolean flag = true; //添加到新集合开关
                //判断每个库存对象是否在新集合里
                for (GoodsStock newStock : newStocks) {
                    //判断，同商品id，同尺码，同颜色， 即为同一个
                    if (stock.getColorCode().equals(newStock.getColorCode()) && stock.getSizeCode().equals(newStock.getSizeCode())) {
                        //数量累加
                        newStock.setCount(newStock.getCount() + stock.getCount());
                        flag = false;  //表示新集合存在此元素，关闭添加
                        break;
                    }
                }
                //新集合中不存在则添加
                if (flag) {
                    newStocks.add(stock);
                }
            }

            GoodsDataVo goodsDataVo = new GoodsDataVo();
            goodsDataVo.setGoodsCode(goodId);

            goodsDataVo.setStocks(newStocks);

            result.add(goodsDataVo);

//            log.info("goodId ={} 库存记录： {} 整合后记录数：{}", goodId, goodsStocks.size(), newStocks.size());

        }
        return result;
    }

    @DataSource(name = DataSourceNames.YITING)
    @Override
    public List<Dingdan> getAllDingdan(Date startDate) {

        return null;
    }

    @DataSource(name = DataSourceNames.YITING)
    @Override
    public boolean saveOrderData(String data) {
        //解析成订单对象
        OrderVo orderVo = JSON.parseObject(data, OrderVo.class);

        //快易订订单号
        JRD jrd;

        int orderType = orderVo.getERPOrderType();
        if (orderType == 1) {
            jrd = new DingdanDB();
        } else if (orderType == 2) {
            jrd = new DingdanPF();
        } else {
            jrd = new Dingdan();
        }

        String orderNum = erpConfig.getOrderPrefix() + orderVo.getOrderNum();
        jrd.setOrderNum(orderNum);
        jrd.setYdjh(orderVo.getOrderNum());
        //查询是否存在
        JRD dingdan2;
        if (orderType == 1) {
            dingdan2 = dingdanDBDao.selectOne((DingdanDB) jrd);
        } else if (orderType == 2) {
            dingdan2 = dingdanPFDao.selectOne((DingdanPF) jrd);
        } else {
            dingdan2 = dingdanDao.selectOne((Dingdan) jrd);
        }

        if (dingdan2 != null) {
            return false; //存在则不插入
        }
        //插入订单数据
        jrd.setInputTime(orderVo.getInputTime());
        jrd.setCount(orderVo.getCount());
        jrd.setOrderPrice(orderVo.getAmount());
        jrd.setPaid(orderVo.getPaid());
        jrd.setAccountName(orderVo.getUserSeq());
        String qddm = keHuDao.selectById(orderVo.getUserSeq()).getQudaoCode();
        jrd.setQddm(qddm);

        jrd.setYgdm("000");

        jrd.setZdrq(orderVo.getInputTime());
        jrd.setDm1_1("000");
        jrd.setDm2("000");
        jrd.setDm2_1("000");
        jrd.setRemark("000");
        jrd.setZdr("IT-系统管理员");
        jrd.setJgxd("3");
        jrd.setZhekou(1.00);
        jrd.setYxrq(DateUtils.addDateDays(orderVo.getInputTime(), 10));

        if (orderType == 1) {
            dingdanDBDao.insert((DingdanDB) jrd);
        } else if (orderType == 2) {
            dingdanPFDao.insert((DingdanPF) jrd);
        } else {
            dingdanDao.insert((Dingdan) jrd);
        }

        //插入订单明细数据
        int num = 0;
        for (OrderGoodsVo goodsVo : orderVo.getOrderGoods()) {
            JRDMX dingdanMX;
            if (orderType == 1) {
                dingdanMX = new DingdanDBMX();
            } else if (orderType == 2) {
                dingdanMX = new DingdanPFMX();
            } else {
                dingdanMX = new DingdanMX();
            }
            dingdanMX.setOrderNum(orderNum);
            dingdanMX.setGoodsSeq(goodsVo.getGoodsCode());
            dingdanMX.setColorSeq(goodsVo.getColorCode());
            dingdanMX.setSizeSeq(goodsVo.getSizeCode());
            dingdanMX.setBuyCount(goodsVo.getCount());
            dingdanMX.setNbBH(++num + "");
            dingdanMX.setProductPrice(goodsVo.getPrice());
            dingdanMX.setCkj(goodsVo.getPrice());
            dingdanMX.setAmount(goodsVo.getCount() * goodsVo.getPrice());
            dingdanMX.setBzje(goodsVo.getCount() * goodsVo.getPrice());

            if (orderType == 1) {
                dingdanDBMXDao.insert((DingdanDBMX) dingdanMX);
            } else if (orderType == 2) {
                dingdanPFMXDao.insert((DingdanPFMX) dingdanMX);
            } else {
                dingdanMXDao.insert((DingdanMX) dingdanMX);
            }
        }
        return true;
    }

    @DataSource(name = DataSourceNames.YITING)
    @Override
    public List<ShangPin> getAllGoods() {
        //查询本年的商品
        List<ShangPin> shangPins = shangPinDao.selectOnCurrYear();
        //查询当前之前的商品库存
        List<String> codes = shangPinDao.selectGoodsCodeBeforeCurrYear();
        List<GoodsDataVo> goodsDataVos = getStockInfo(codes);
        //获得库存不为0的商品code
        List<String> newCodes = new ArrayList<>();
        for (GoodsDataVo goodsDataVo : goodsDataVos) {
            int count = 0;
            for (GoodsStock goodsStock : goodsDataVo.getStocks()) {
                count += goodsStock.getCount();
            }
            if (count>0) {
                newCodes.add(goodsDataVo.getGoodsCode());
            }
        }

        //如果newCodes不为空，查询商品
        if (!newCodes.isEmpty()) {
            List<ShangPin> shangPins2 = shangPinDao.selectByGoodsCodes(newCodes);
            //追加商品集合
            shangPins.addAll(shangPins2);
        }


        for (ShangPin shangPin : shangPins) {
            switch (shangPin.getSeasonCode()) {
                case "Q1":
                    shangPin.setSeasonName("春季");
                    break;
                case "Q2":
                    shangPin.setSeasonName("夏季");
                    break;
                case "Q3":
                    shangPin.setSeasonName("秋季");
                    break;
                case "Q4":
                    shangPin.setSeasonName("冬季");
                    break;
            }
        }
        return shangPins;
    }

    @DataSource(name = DataSourceNames.YITING)
    @Override
    public void changeOrderStock(String data) {
        //goodsStockDao
    }

    @Override
    public List<Brand> getAllBrands() {
        return null;
    }

    @Override
    public boolean saveGoods(String goods) {
        return false;
    }

}
