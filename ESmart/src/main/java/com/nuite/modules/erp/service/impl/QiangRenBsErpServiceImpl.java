package com.nuite.modules.erp.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.nuite.common.utils.DateUtils;
import com.nuite.datasources.DataSourceNames;
import com.nuite.datasources.annotation.DataSource;
import com.nuite.modules.erp.bserp.dao.*;
import com.nuite.modules.erp.bserp.entity.*;
import com.nuite.modules.erp.config.ErpConfig;
import com.nuite.modules.erp.entity.qiangren.dao.QRShangPinDao;
import com.nuite.modules.erp.entity.qiangren.entity.QRShangPin;
import com.nuite.modules.erp.entity.vo.GoodsDataVo;
import com.nuite.modules.erp.entity.vo.GoodsSXVo;
import com.nuite.modules.erp.entity.vo.OrderGoodsVo;
import com.nuite.modules.erp.entity.vo.OrderVo;
import com.nuite.modules.erp.service.CommonErpService;
import com.nuite.modules.erp.utils.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: yangchuang
 * @Date: 2018/8/2 16:53
 * @Version: 1.0
 * @Description:
 */

@Slf4j
@Service("QiangrenBsErpService")
public class QiangRenBsErpServiceImpl implements CommonErpService {

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
    DingdanMXDao dingdanMXDao;

    @Autowired
    BrandDao brandDao;

    @Autowired
    ShangPinDao shangPinDao;

    @Autowired
    QRShangPinDao qrShangPinDao;

    @DataSource(name = DataSourceNames.QIANGREN)
    @Override
    public List<GoodsSizeEntity> getAllGoodsSizes() {

        return sizesDao.selectList(null);
    }

    @DataSource(name = DataSourceNames.QIANGREN)
    @Override
    public List<GoodsColorEntity> getAllGoodsColor() {

        return colorsDao.selectList(null);
    }

    @DataSource(name = DataSourceNames.QIANGREN)
    @Override
    public List<GoodsCategoryEntity> getAllGoodsCategory() {

        return categoryDao.selectList(null);
    }

    @DataSource(name = DataSourceNames.QIANGREN)
    @Override
    public List getAllSXandOption() {

        GoodsSXVo sx1 = new GoodsSXVo();
        sx1.setSxId("SX1");
        sx1.setSxName("分类");
        sx1.setOptions(fjsx1Dao.selectList(null));

        GoodsSXVo sx2 = new GoodsSXVo();
        sx2.setSxId("SX2");
        sx2.setSxName("品类");
        sx2.setOptions(fjsx2Dao.selectList(null));

        GoodsSXVo sx3 = new GoodsSXVo();
        sx3.setSxId("SX3");
        sx3.setSxName("风格");
        sx3.setOptions(fjsx3Dao.selectList(null));

        GoodsSXVo sx4 = new GoodsSXVo();
        sx4.setSxId("SX4");
        sx4.setSxName("风格");
        sx4.setOptions(fjsx4Dao.selectList(null));

        GoodsSXVo sx5 = new GoodsSXVo();
        sx5.setSxId("SX5");
        sx5.setSxName("面料");
        sx5.setOptions(fjsx5Dao.selectList(null));

        GoodsSXVo sx6 = new GoodsSXVo();
        sx6.setSxId("SX6");
        sx6.setSxName("跟型");
        sx6.setOptions(fjsx6Dao.selectList(null));

        GoodsSXVo sx7 = new GoodsSXVo();
        sx7.setSxId("SX7");
        sx7.setSxName("鞋底");
        sx7.setOptions(fjsx7Dao.selectList(null));

        GoodsSXVo sx8 = new GoodsSXVo();
        sx8.setSxId("SX8");
        sx8.setSxName("垫脚");
        sx8.setOptions(fjsx8Dao.selectList(null));

        GoodsSXVo sx9 = new GoodsSXVo();
        sx9.setSxId("SX9");
        sx9.setSxName("供应商");
        sx9.setOptions(fjsx9Dao.selectList(null));

        GoodsSXVo sx10 = new GoodsSXVo();
        sx10.setSxId("SX10");
        sx10.setSxName("线上线下");
        sx10.setOptions(fjsx10Dao.selectList(null));

        GoodsSXVo sx11 = new GoodsSXVo();
        sx11.setSxId("SX11");
        sx11.setSxName("里料");
        sx11.setOptions(fjsx11Dao.selectList(null));

        GoodsSXVo sx12 = new GoodsSXVo();
        sx12.setSxId("SX12");
        sx12.setSxName("功能");
        sx12.setOptions(fjsx12Dao.selectList(null));

        GoodsSXVo sx13 = new GoodsSXVo();
        sx13.setSxId("SX13");
        sx13.setSxName("跟高");
        sx13.setOptions(fjsx13Dao.selectList(null));

        GoodsSXVo sx14 = new GoodsSXVo();
        sx14.setSxId("SX14");
        sx14.setSxName("系统");
        sx14.setOptions(fjsx14Dao.selectList(null));

        GoodsSXVo sx15 = new GoodsSXVo();
        sx15.setSxId("SX15");
        sx15.setSxName("首订/后期版");
        sx15.setOptions(fjsx15Dao.selectList(null));

        GoodsSXVo sx16 = new GoodsSXVo();
        sx16.setSxId("SX16");
        sx16.setSxName("靴尺寸");
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

    @DataSource(name = DataSourceNames.QIANGREN)
    @Override
    public List<CangKu> getAllKeHu() {
        List<CangKu> kehuses = cangKuDao.selectList(null);

        //过滤停用账号
/*
        for (int i = kehuses.size() - 1; i >= 0; i--) {
            CangKu kehu=kehuses.get(i);
            if ( kehu.getUserName().contains("停用") || kehu.getUserName().contains("（停）")  ){
                kehuses.remove(i);
            }
        }
*/
        return kehuses;
    }

    @DataSource(name = DataSourceNames.QIANGREN)
    @Override
    public List<GoodsDataVo> getStockInfo(List<String> goodIds) {

        log.info("查询库存的商品数量: {}, {}", goodIds.size(), goodIds);
        List<GoodsDataVo> result = new ArrayList<>();
        for (String goodId : goodIds) {
            goodId = goodId.trim();
            List<GoodsStock> goodsStocks = goodsStockDao.selectList(new EntityWrapper<GoodsStock>().eq("SPDM", goodId));

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

    @DataSource(name = DataSourceNames.QIANGREN)
    @Override
    public List<Dingdan> getAllDingdan(Date startDate) {
        Date date = DateUtils.stringToDate("2018-08-01", DateUtils.DATE_PATTERN);
        if (startDate == null) {
            startDate = date;
        }

        List<Dingdan> dingdans = dingdanDao.getDingdan(startDate);
        for (Dingdan dingdan : dingdans) {
            String orderNum = dingdan.getOrderNum();
            if (orderNum.startsWith(erpConfig.getOrderPrefix())) {
                orderNum = orderNum.replaceFirst(erpConfig.getOrderPrefix(), "");
                dingdan.setOrderNum(orderNum);
            }
            if (dingdan.getStoreTime() != null) {
                dingdan.setOrderStatus(OrderStatus.ORDSTATUS_THREE.getValue());
                if (!dingdan.getMxList().isEmpty()) {
                    int totalNum = 0, deliverNum = 0;
                    for (DingdanMX dingdanMX : dingdan.getMxList()) {
                        totalNum += dingdanMX.getBuyCount();
                        deliverNum += dingdanMX.getDeliverNum();
                    }
                    // 部分发货已发货区分状态
                    if (deliverNum != 0 && deliverNum == totalNum) {
                        dingdan.setOrderStatus(OrderStatus.ORDSTATUS_FIVE.getValue());
                    } else if (deliverNum != 0) {
                        dingdan.setOrderStatus(OrderStatus.ORDSTATUS_FOUR.getValue());
                    }
                }

            } else {
                dingdan.setOrderStatus(OrderStatus.ORDSTATUS_TWO.getValue());
            }
        }
        return dingdans;
    }

    @DataSource(name = DataSourceNames.QIANGREN)
    @Override
    public boolean saveOrderData(String data) {

        //解析成订单对象
        OrderVo orderVo = JSON.parseObject(data, OrderVo.class);
        //快易订订单号
        Dingdan dingdan = new Dingdan();
        String orderNum = erpConfig.getOrderPrefix() + orderVo.getOrderNum();
        dingdan.setOrderNum(orderNum);
        dingdan.setYdjh(orderVo.getOrderNum());
        //查询是否存在
        Dingdan dingdan2 = dingdanDao.selectOne(dingdan);

        if (dingdan2 != null) {
            return false; //存在则不插入
        }
        //插入订单数据
        dingdan.setInputTime(orderVo.getInputTime());
        dingdan.setCount(orderVo.getCount());
        dingdan.setOrderPrice(orderVo.getAmount());
        dingdan.setPaid(orderVo.getPaid());
        dingdan.setQddm("000");
        dingdan.setAccountName(orderVo.getUserSeq());

        String ygdm = dingdanDao.selectDingdanYGDM(orderVo.getUserSeq());
        dingdan.setYgdm(ygdm);

        dingdan.setZdrq(orderVo.getInputTime());
        dingdan.setDm1_1("000");
        dingdan.setDm2("001");
        dingdan.setDm2_1("000");
        dingdan.setRemark("004");
        dingdan.setZdr("IT-系统管理员");
        dingdan.setJgxd("3");
        dingdan.setZhekou(1.00);
        dingdan.setYxrq(DateUtils.addDateDays(orderVo.getInputTime(), 10));
        dingdanDao.insert(dingdan);

        //插入订单明细数据
        int num = 0;
        for (OrderGoodsVo goodsVo : orderVo.getOrderGoods()) {
            DingdanMX dingdanMX = new DingdanMX();
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

            dingdanMXDao.insert(dingdanMX);
        }
        return true;

    }

    @Override
    public List<ShangPin> getAllGoods() {
        return null;
    }

    @DataSource(name = DataSourceNames.QIANGREN)
    @Override
    public boolean saveGoods(String data) {
//        log.info("商品数据传来了: {}", data);
        List<QRShangPin> shangPins = JSON.parseArray(data, QRShangPin.class);

        for (QRShangPin good : shangPins) {
            //  判断商品是否存在
            Integer count = qrShangPinDao.selectCount(new EntityWrapper<QRShangPin>().eq("SPDM", good.getCode()));
            if (count == null || count == 0) {
                //判断大类是否存在
                Integer daleiCount = categoryDao.selectCount(new EntityWrapper<GoodsCategoryEntity>()
                        .eq("DLDM", good.getCategoryCode()));
                //类别存在，可插入
                if (daleiCount != 0) {
                    qrShangPinDao.insert(good);
                } else {
                    log.info("商品{}类别不存在，不能插入", good.getCode());
                    continue;
                }
            } else {
//                log.info("商品已存在-----------");
                qrShangPinDao.update(good, new EntityWrapper<QRShangPin>().eq("SPDM", good.getCode()));
            }

            List<GoodsStock> stocks = good.getStocks();
            for (GoodsStock stock : stocks) {

                //判断商品库存表中是否存在
                List<GoodsStock> goodsStocks = goodsStockDao.selectList(new EntityWrapper<GoodsStock>()
                        .eq("SPDM", good.getCode())
                        .eq("GG1DM", stock.getColorCode())
                        .eq("GG2DM", stock.getSizeCode()));

                if (goodsStocks == null || goodsStocks.isEmpty()) {

                    //尺码判断（外键关联）
                    List<GoodsSizeEntity> sizes = sizesDao.selectList(new EntityWrapper<GoodsSizeEntity>()
                            .eq("GGDM", stock.getSizeCode()));
                    if (sizes == null || sizes.isEmpty()) {
                        GoodsSizeEntity size = new GoodsSizeEntity();
                        size.setSizeCode(stock.getSizeCode());
                        size.setSizeName(stock.getSizeName());
                        sizesDao.insert(size);
                    }

                    //颜色判断（外键关联）
                    List<GoodsColorEntity> colors = colorsDao.selectList(new EntityWrapper<GoodsColorEntity>()
                            .eq("GGDM", stock.getColorCode()));
                    if (colors == null || colors.isEmpty()) {
                        GoodsColorEntity color = new GoodsColorEntity();
                        color.setCode(stock.getColorCode());
                        color.setName(stock.getColorName());
                        colorsDao.insert(color);
                    }
                    stock.setCKCode("001"); // 仓库编号 总仓
                    stock.setKWCode("000"); // 库位编号
                    goodsStockDao.insert(stock);
                }
            }

        }
        return true;
    }

    @Override
    public void changeOrderStock(String data) {
        //强人无需同步
    }

    @DataSource(name = DataSourceNames.QIANGREN)
    @Override
    public List<Brand> getAllBrands() {
        return brandDao.selectList(null);
    }

    @DataSource(name = DataSourceNames.QIANGREN)
    public void savaStock(GoodsStock goodsStock) {
        goodsStockDao.insert(goodsStock);
    }
}
