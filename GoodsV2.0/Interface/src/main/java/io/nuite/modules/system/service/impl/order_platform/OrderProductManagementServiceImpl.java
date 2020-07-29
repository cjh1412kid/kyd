package io.nuite.modules.system.service.impl.order_platform;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.base.Joiner;

import io.nuite.common.exception.RRException;
import io.nuite.common.utils.FileUtils;
import io.nuite.common.utils.PageUtils;
import io.nuite.common.utils.Query;
import io.nuite.common.utils.ZipUtils;
import io.nuite.modules.online_sales_app.entity.MiniAppEntity;
import io.nuite.modules.online_sales_app.service.OlsMiniAppService;
import io.nuite.modules.online_sales_app.wx.WxMyService;
import io.nuite.modules.online_sales_app.wx.WxServiceUtils;
import io.nuite.modules.order_platform_app.service.ShoesValuateService;
import io.nuite.modules.sr_base.dao.GoodsCategoryDao;
import io.nuite.modules.sr_base.dao.GoodsColorDao;
import io.nuite.modules.sr_base.dao.GoodsPeriodDao;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.sr_base.entity.GoodsCategoryEntity;
import io.nuite.modules.sr_base.entity.GoodsColorEntity;
import io.nuite.modules.sr_base.entity.GoodsPeriodEntity;
import io.nuite.modules.sr_base.entity.GoodsShoesEntity;
import io.nuite.modules.sr_base.entity.GoodsViewEntity;
import io.nuite.modules.sr_base.service.GoodsShoesService;
import io.nuite.modules.sr_base.utils.ConfigUtils;
import io.nuite.modules.system.dao.order_platform.OrderProductManagementDao;
import io.nuite.modules.system.model.GoodsShoesForm;
import io.nuite.modules.system.model.GoodsShoesOnlineForm;
import io.nuite.modules.system.model.GoodsShoesPlatformForm;
import io.nuite.modules.system.model.GoodsShoesStockForm;
import io.nuite.modules.system.service.order_platform.OrderProductManagementService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

@Service
public class OrderProductManagementServiceImpl implements OrderProductManagementService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private OrderProductManagementDao orderProductManagementDao;

    @Autowired
    private GoodsPeriodDao goodsPeriodDao;
    
    @Autowired
    private GoodsColorDao goodsColorDao;

    @Autowired
    private GoodsCategoryDao goodsCategoryDao;

    @Autowired
    private GoodsShoesService goodsShoesService;

    @Autowired
    private io.nuite.modules.order_platform_app.dao.ShoesDataDao orderShoesDataDao;

    @Autowired
    private io.nuite.modules.order_platform_app.dao.ShoesInfoDao orderShoesInfoDao;

    @Autowired
    private io.nuite.modules.order_platform_app.dao.ShoesCompanyTypeDao orderShoesCompanyTypeDao;

    @Autowired
    private io.nuite.modules.online_sales_app.dao.OnlineSalesShoesDataDao onlineShoesDataDao;

    @Autowired
    private io.nuite.modules.online_sales_app.dao.OnlineSalesShoesInfoDao onlineShoesInfoDao;

    @Autowired
    private ConfigUtils configUtils;

    @Autowired
    private OlsMiniAppService olsMiniAppService;

    @Autowired
    private ShoesValuateService shoesValuateService;

    @Override
    public PageUtils getGoodsList(Long userSeq, Map<String, Object> params) {
        List<Integer> categoryList = new ArrayList<>();
        List<Integer> periodList = new ArrayList<>();
        Object categorySeqObj = params.get("categorySeq");
        int categorySeq;
        if(categorySeqObj == null || StringUtils.isEmpty(categorySeqObj.toString()) || categorySeqObj.toString().equals("-1")) {
        	categorySeq = 0;
        } else {
        	categorySeq = Integer.valueOf(categorySeqObj.toString());
        }
        // 不为空则判断是否有子类
        getCategorySeqList(categoryList, Collections.singletonList(categorySeq));

        Object periodSeqObj = params.get("periodSeq");
        // 如果波次为空 则查找该用户所有的波次
        if (periodSeqObj == null || StringUtils.isEmpty(periodSeqObj.toString()) || periodSeqObj.toString().equals("-1")) {
            periodList = orderProductManagementDao.getPeriodSeqList(userSeq);
        } else {
            periodList.add(Integer.valueOf(periodSeqObj.toString()));
        }

        String keywords = (String) params.get("keywords");
        if (keywords != null) {
            keywords = keywords.trim();
        }

        /*过滤条件，是否上架*/
        Integer onSale = Integer.valueOf((String) params.get("onSale"));

        Page<GoodsViewEntity> page = new Query<GoodsViewEntity>(params).getPage();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("periodSeqs", Joiner.on(",").join(periodList));
        //map.put("categorySeqs", Joiner.on(",").join(categoryList));
        map.put("goodNameId", keywords);
        map.put("onSaleType", onSale);
        List<GoodsViewEntity> list = orderProductManagementDao.selectGoodsList(page, map);

        for (GoodsViewEntity goodsViewEntity : list) {
            //分类名称
            GoodsCategoryEntity goodsCategoryEntity = goodsCategoryDao.selectById(goodsViewEntity.getCategorySeq());
            if(goodsCategoryEntity != null) {
                goodsViewEntity.setCategoryName(goodsCategoryEntity.getName());
            }

            //图片路径
            String goodId = goodsViewEntity.getGoodID();
            String img1 = goodsViewEntity.getImg1();
            String imageUrl = configUtils.getPictureBaseUrl() + "/" + configUtils.getBaseDir() + "/" + configUtils.getGoodsShoes()
                    + "/" + goodId + "/" + img1;
            goodsViewEntity.setImg1(imageUrl);
        }
        return new PageUtils(list, page.getTotal(), page.getSize(), page.getCurrent());
    }

    // 递归查询子类型
    private void getCategorySeqList(List<Integer> categoryList, List<Integer> list) {
        for (Integer integer : list) {
            categoryList.add(integer);
            List<Integer> listson = orderProductManagementDao.getCategorySeqList(integer);
            if (listson != null && listson.size() > 0) {
                getCategorySeqList(categoryList, listson);
            }
        }
    }

    // 根据上级seq查询子类别列表
    public List<GoodsCategoryEntity> getCategoryByParentSeq(Integer seq) {
        return orderProductManagementDao.getCategoryByParentSeq(seq);
    }

    // 根据上级seq删除信息
    @Transactional
    public int delete(Integer seq) {
        orderShoesInfoDao.deleteByShoesSeq(seq);
        orderShoesDataDao.deleteByShoesSeq(seq);
        orderShoesCompanyTypeDao.deleteByShoesSeq(seq);
        onlineShoesInfoDao.deleteByShoesSeq(seq);
        onlineShoesDataDao.deleteByShoesSeq(seq);
        shoesValuateService.deleteShoesValuateByShoesSeq(seq);
        return orderProductManagementDao.delete(seq);
    }

    // 添加商品的信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addGoods(GoodsShoesForm goodsShoesForm, Integer companySeq) throws Exception {
        GoodsShoesEntity oldEntity = goodsShoesService.getGoodsByGoodId(companySeq, goodsShoesForm.getGoodId().trim());
        if (oldEntity != null) {
            throw new RRException("当前货号已存在！");
        }
        GoodsShoesEntity goodsShoesEntity = new GoodsShoesEntity();
        goodsShoesEntity.setCompanySeq(companySeq);
        goodsShoesEntity.setPeriodSeq(goodsShoesForm.getPeriodSeq().intValue());
        goodsShoesEntity.setGoodName(goodsShoesForm.getGoodName());
        goodsShoesEntity.setGoodID(goodsShoesForm.getGoodId());
        goodsShoesEntity.setCategorySeq(goodsShoesForm.getCategorySeq().intValue());

        setGoodsSxValue(goodsShoesForm.getSxArray(), goodsShoesEntity);

        goodsShoesEntity.setIntroduce(goodsShoesForm.getIntroduce());
        goodsShoesEntity.setCompanySeq(companySeq);

        String imageGoodsDir = FileUtils.getWebAppsPath() + configUtils.getPictureBaseUploadProject() + File.separator
                + configUtils.getBaseDir() + File.separator + configUtils.getGoodsShoes() + File.separator
                + goodsShoesForm.getGoodId() + File.separator;
        // 处理轮播图
        List<MultipartFile> bannerFiles = goodsShoesForm.getBannerFiles();
        for (int i = 0; i < bannerFiles.size(); i++) {
            if (i >= 5)
                break;
            MultipartFile image = bannerFiles.get(i);
            if (image == null || image.getSize() <= 0)
                continue;
            String fileName = FileUtils.upLoadFile(imageGoodsDir, null, image);

            setIndexImage(i, fileName, goodsShoesEntity);
        }

        // 处理详细描述
        List<MultipartFile> descriptionFiles = goodsShoesForm.getDescriptionFiles();
        List<String> descriptionStringList = new ArrayList<String>();
        for (MultipartFile multipartFile : descriptionFiles) {
            if (multipartFile == null || multipartFile.getSize() <= 0)
                continue;
            String fileName = FileUtils.upLoadFile(imageGoodsDir, null, multipartFile);
            descriptionStringList.add(fileName);
        }
        goodsShoesEntity.setDescription(String.join(",", descriptionStringList));

        // 处理video
        MultipartFile videoFile = goodsShoesForm.getVideoFile();
        if (videoFile != null && videoFile.getSize() > 0) {
            String videoName = FileUtils.upLoadFile(imageGoodsDir, null, videoFile);
            goodsShoesEntity.setVideo(videoName);
        }
        goodsShoesEntity.setInputTime(new Date());
        goodsShoesEntity.setDel(0);

        goodsShoesService.insert(goodsShoesEntity);

        Integer goodsSeq = goodsShoesEntity.getSeq();

        GoodsShoesPlatformForm goodsShoesPlatformForm = goodsShoesForm.getOrderPlatform();
        GoodsShoesOnlineForm goodsShoesOnlineForm = goodsShoesForm.getOnlineSale();
        if (goodsShoesPlatformForm == null && goodsShoesOnlineForm == null) {
            throw new RRException("选择平台编辑库存！");
        }

        //订货平台数据不是空
        if (goodsShoesPlatformForm != null) {
            // 插入查看类型
            List<Integer> authors = goodsShoesPlatformForm.getAuthor();
            authors.add(1);
            orderShoesCompanyTypeDao.insertBatch(goodsSeq, authors);

            io.nuite.modules.order_platform_app.entity.ShoesInfoEntity shoesInfoEntity = new io.nuite.modules.order_platform_app.entity.ShoesInfoEntity();
            shoesInfoEntity.setShoesSeq(goodsSeq);
            shoesInfoEntity.setIsNewest(goodsShoesForm.getIsNewest() != null && goodsShoesForm.getIsNewest() ? 1 : 0);
            shoesInfoEntity.setIsHotSale(goodsShoesForm.getIsHotSale() != null && goodsShoesForm.getIsHotSale() ? 1 : 0);

            shoesInfoEntity.setOemPrice(goodsShoesPlatformForm.getOemPrice());
            shoesInfoEntity.setWholesalerPrice(goodsShoesPlatformForm.getWholesalerPrice());
            shoesInfoEntity.setStorePrice(goodsShoesPlatformForm.getStorePrice());
            shoesInfoEntity.setSalePrice(goodsShoesPlatformForm.getSalePrice());
            shoesInfoEntity.setOnSale(goodsShoesPlatformForm.getOnSale() != null && goodsShoesPlatformForm.getOnSale() ? 1 : 0);
            shoesInfoEntity.setOnSaleTime(goodsShoesPlatformForm.getOnSaleTime());
            shoesInfoEntity.setOffSaleTime(goodsShoesPlatformForm.getOffSaleTime());
            orderShoesInfoDao.insert(shoesInfoEntity);

            List<GoodsShoesStockForm> stockForms = goodsShoesPlatformForm.getStock();
            for (GoodsShoesStockForm stockForm : stockForms) {
                insertOrUpdateOrderPlatformShoesData(goodsSeq, stockForm, null);
            }
        }

        //分销平台数据不是空
        if (goodsShoesOnlineForm != null) {
            io.nuite.modules.online_sales_app.entity.ShoesInfoEntity shoesInfoEntity = new io.nuite.modules.online_sales_app.entity.ShoesInfoEntity();
            shoesInfoEntity.setShoesSeq(goodsSeq);
            shoesInfoEntity.setIsNewest(goodsShoesForm.getIsNewest() != null && goodsShoesForm.getIsNewest() ? 1 : 0);
            shoesInfoEntity.setIsHotSale(goodsShoesForm.getIsHotSale() != null && goodsShoesForm.getIsHotSale() ? 1 : 0);

            shoesInfoEntity.setTagPrice(goodsShoesOnlineForm.getTagPrice());
            shoesInfoEntity.setSalePrice(goodsShoesOnlineForm.getSalePrice());
            shoesInfoEntity.setOnSale(goodsShoesOnlineForm.getOnSale() != null && goodsShoesOnlineForm.getOnSale() ? 1 : 0);

            MiniAppEntity miniAppEntity = olsMiniAppService.queryOneByCompanySeq(companySeq);
            WxMyService wxMyService = WxServiceUtils.getWxMyService(miniAppEntity);
            if (wxMyService != null) {
                File wxCodeFile = wxMyService.getWxMaService().getQrcodeService().createWxCodeLimit("seq=" + goodsSeq, "pages/goods/detail/detail", 280, false, null);
                FileUtils.copyFile(imageGoodsDir, null, wxCodeFile);
                shoesInfoEntity.setWxQRCode(wxCodeFile.getName());
            }
            onlineShoesInfoDao.insert(shoesInfoEntity);

            List<GoodsShoesStockForm> stockForms = goodsShoesOnlineForm.getStock();
            for (GoodsShoesStockForm stockForm : stockForms) {
                insertOrUpdateOnlineShoesData(goodsSeq, stockForm, null);
            }
        }
    }

    // 根据Seq返回鞋子的详细信息
    @Override
    public GoodsShoesForm edit(Integer seq) {
        GoodsShoesForm goodsShoesForm = orderProductManagementDao.edit(seq);
        JSONObject sxJson = new JSONObject();
        for (int i = 1; i <= 20; i++) {
            try {
                Field sxField = GoodsShoesForm.class.getDeclaredField("sx" + i);
                sxField.setAccessible(true);
                String result = (String) sxField.get(goodsShoesForm);
                if (StringUtils.isNotBlank(result)) {
                    sxJson.put("sx" + i, result);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                logger.error("", e);
            }
        }
        goodsShoesForm.setSxArray(sxJson.toJSONString());

        String image1 = goodsShoesForm.getImg1();
        String image2 = goodsShoesForm.getImg2();
        String image3 = goodsShoesForm.getImg3();
        String image4 = goodsShoesForm.getImg4();
        String image5 = goodsShoesForm.getImg5();

        String description = goodsShoesForm.getDescription();

        String goodSrcBase = configUtils.getPictureBaseUrl() + "/" + configUtils.getBaseDir() + "/"
                + configUtils.getGoodsShoes() + "/" + goodsShoesForm.getGoodId() + "/";
        // 轮播图路径处理
        List<String> banners = goodsShoesForm.getBanners();
        if (banners == null) {
            banners = new ArrayList<String>();
            goodsShoesForm.setBanners(banners);
        }
        banners.clear();
        if (StringUtils.isNotBlank(image1))
            banners.add(goodSrcBase + image1);
        if (StringUtils.isNotBlank(image2))
            banners.add(goodSrcBase + image2);
        if (StringUtils.isNotBlank(image3))
            banners.add(goodSrcBase + image3);
        if (StringUtils.isNotBlank(image4))
            banners.add(goodSrcBase + image4);
        if (StringUtils.isNotBlank(image5))
            banners.add(goodSrcBase + image5);

        // 详情描述处理
        List<String> descriptions = goodsShoesForm.getDescriptions();
        if (descriptions == null) {
            descriptions = new ArrayList<String>();
            goodsShoesForm.setDescriptions(descriptions);
        }
        descriptions.clear();
        if (description != null) {
            String[] descSrcArray = description.split(",");
            for (String descSrc : descSrcArray) {
                if (!descSrc.isEmpty()) {
                    descriptions.add(goodSrcBase + descSrc);
                }
            }
        }
        // 视频路径处理
        String video = goodsShoesForm.getVideo();
        if (video != null && !video.trim().isEmpty()) {
            goodsShoesForm.setVideo(goodSrcBase + video);
        }
        int goodsSeq = goodsShoesForm.getSeq().intValue();

        // 查看订货平台是否存在
        EntityWrapper<io.nuite.modules.order_platform_app.entity.ShoesInfoEntity> orderEw =
                new EntityWrapper<>();
        orderEw.eq("Shoes_Seq", goodsSeq);
        List<io.nuite.modules.order_platform_app.entity.ShoesInfoEntity> orderShoesInfoEntities = orderShoesInfoDao.selectList(orderEw);
        if (!orderShoesInfoEntities.isEmpty()) {
            GoodsShoesPlatformForm goodsShoesPlatformForm = new GoodsShoesPlatformForm();
            io.nuite.modules.order_platform_app.entity.ShoesInfoEntity shoesInfoEntity = orderShoesInfoEntities.get(0);
            // 设置热销和新品
            goodsShoesForm.setIsNewest(shoesInfoEntity.getIsNewest() != null && shoesInfoEntity.getIsNewest() == 1);
            goodsShoesForm.setIsHotSale(shoesInfoEntity.getIsHotSale() != null && shoesInfoEntity.getIsHotSale() == 1);
            // 价格设置
            goodsShoesPlatformForm.setOemPrice(shoesInfoEntity.getOemPrice());
            goodsShoesPlatformForm.setWholesalerPrice(shoesInfoEntity.getWholesalerPrice());
            goodsShoesPlatformForm.setStorePrice(shoesInfoEntity.getStorePrice());
            goodsShoesPlatformForm.setSalePrice(shoesInfoEntity.getSalePrice());
            goodsShoesPlatformForm.setOnSale(shoesInfoEntity.getOnSale() != null && shoesInfoEntity.getOnSale() == 1);
            goodsShoesPlatformForm.setOnSaleTime(shoesInfoEntity.getOnSaleTime());
            goodsShoesPlatformForm.setOffSaleTime(shoesInfoEntity.getOffSaleTime());
            // 查看权限处理
            EntityWrapper<io.nuite.modules.order_platform_app.entity.ShoesCompanyTypeEntity> ctEw =
                    new EntityWrapper<>();
            ctEw.eq("Shoes_Seq", goodsSeq).eq("Del", 0);
            List<io.nuite.modules.order_platform_app.entity.ShoesCompanyTypeEntity> authorList = orderShoesCompanyTypeDao.selectList(ctEw);
            List<Integer> authors = new ArrayList<>();
            for (io.nuite.modules.order_platform_app.entity.ShoesCompanyTypeEntity companyTypeEntity : authorList) {
                if (companyTypeEntity.getCompanyTypeSeq() != 1) {
                    authors.add(companyTypeEntity.getCompanyTypeSeq());
                }
            }
            goodsShoesPlatformForm.setAuthor(authors);
            // 库存处理
            List<io.nuite.modules.order_platform_app.entity.ShoesDataEntity> orderDataList = orderShoesDataDao.queryDataAndColorName(goodsSeq);
            List<GoodsShoesStockForm> stockForms = new ArrayList<>();
            for (io.nuite.modules.order_platform_app.entity.ShoesDataEntity shoesDataEntity : orderDataList) {
                GoodsShoesStockForm stockForm = new GoodsShoesStockForm();
                stockForm.setName(shoesDataEntity.getColorName());
                stockForm.setCode(shoesDataEntity.getCode());
                stockForm.setSeq(shoesDataEntity.getColorSeq());
                stockForm.setSizeSeq(shoesDataEntity.getSizeSeq());
                stockForm.setSizeCode(shoesDataEntity.getSizeCode());
                stockForm.setSizeName(shoesDataEntity.getSizeName());
                stockForm.setStock(shoesDataEntity.getStock());
                stockForm.setRemark(shoesDataEntity.getRemark());
                stockForms.add(stockForm);
            }
            goodsShoesPlatformForm.setStock(stockForms);

            goodsShoesForm.setOrderPlatform(goodsShoesPlatformForm);
        }

        EntityWrapper<io.nuite.modules.online_sales_app.entity.ShoesInfoEntity> onlineEw =
                new EntityWrapper<>();
        onlineEw.eq("Shoes_Seq", goodsShoesForm.getSeq()).eq("Del", 0);
        List<io.nuite.modules.online_sales_app.entity.ShoesInfoEntity> onlineShoesInfoEntities = onlineShoesInfoDao.selectList(onlineEw);
        if (!onlineShoesInfoEntities.isEmpty()) {
            GoodsShoesOnlineForm goodsShoesOnlineForm = new GoodsShoesOnlineForm();
            io.nuite.modules.online_sales_app.entity.ShoesInfoEntity shoesInfoEntity = onlineShoesInfoEntities.get(0);
            // 设置热销和新品
            goodsShoesForm.setIsNewest(shoesInfoEntity.getIsNewest() != null && shoesInfoEntity.getIsNewest() == 1);
            goodsShoesForm.setIsHotSale(shoesInfoEntity.getIsHotSale() != null && shoesInfoEntity.getIsHotSale() == 1);
            // 价格设置
            goodsShoesOnlineForm.setTagPrice(shoesInfoEntity.getTagPrice());
            goodsShoesOnlineForm.setSalePrice(shoesInfoEntity.getSalePrice());
            goodsShoesOnlineForm.setOnSale(shoesInfoEntity.getOnSale() != null && shoesInfoEntity.getOnSale() == 1);

            // 库存处理
            List<io.nuite.modules.online_sales_app.entity.ShoesDataEntity> orderDataList = onlineShoesDataDao.queryDataAndColorName(goodsSeq);
            List<GoodsShoesStockForm> stockForms = new ArrayList<>();
            for (io.nuite.modules.online_sales_app.entity.ShoesDataEntity shoesDataEntity : orderDataList) {
                GoodsShoesStockForm stockForm = new GoodsShoesStockForm();
                stockForm.setName(shoesDataEntity.getColorName());
                stockForm.setCode(shoesDataEntity.getCode());
                stockForm.setSeq(shoesDataEntity.getColorSeq());
                stockForm.setSizeSeq(shoesDataEntity.getSizeSeq());
                stockForm.setSizeCode(shoesDataEntity.getSizeCode());
                stockForm.setSizeName(shoesDataEntity.getSizeName());
                stockForm.setStock(shoesDataEntity.getStock());
                stockForm.setSetStock(shoesDataEntity.getSetStock());
                stockForms.add(stockForm);
            }
            goodsShoesOnlineForm.setStock(stockForms);

            goodsShoesForm.setOnlineSale(goodsShoesOnlineForm);
        }
        return goodsShoesForm;
    }

    // 修改鞋子信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> updateGoods(GoodsShoesForm goodsShoesForm) throws Exception {
        List<String> needDelFile = new ArrayList<String>();
        GoodsShoesEntity goodsShoesEntity = new GoodsShoesEntity();
        goodsShoesEntity.setSeq(goodsShoesForm.getSeq().intValue());
        goodsShoesEntity.setPeriodSeq(goodsShoesForm.getPeriodSeq().intValue());
        goodsShoesEntity.setGoodName(goodsShoesForm.getGoodName());
        goodsShoesEntity.setGoodID(goodsShoesForm.getGoodId());
        goodsShoesEntity.setCategorySeq(goodsShoesForm.getCategorySeq().intValue());

        // 反射设置SX1-20
        setGoodsSxValue(goodsShoesForm.getSxArray(), goodsShoesEntity);

        goodsShoesEntity.setIntroduce(goodsShoesForm.getIntroduce());

        String imageGoodsDir = FileUtils.getWebAppsPath() + configUtils.getPictureBaseUploadProject() + File.separator
                + configUtils.getBaseDir() + File.separator + configUtils.getGoodsShoes() + File.separator
                + goodsShoesForm.getGoodId() + File.separator;
        // 处理轮播图
        List<MultipartFile> bannerFiles = goodsShoesForm.getBannerFiles();
        List<String> bannerStrings = goodsShoesForm.getBanners();
        if(bannerFiles != null && bannerFiles.size() > 0 && bannerStrings != null && bannerStrings.size() > 0) {
	        for (int i = 0; i < 5; i++) {
	            // 如果上传图片不足5个，需要原有内容清空
	            if (i >= bannerFiles.size()) {
	                setIndexImage(i, "", goodsShoesEntity);
	                continue;
	            }
	            MultipartFile image = bannerFiles.get(i);
	            String oldName = null;
	            if (i < bannerStrings.size()) {
	                String oldImage = bannerStrings.get(i);
	                oldName = oldImage.substring(oldImage.lastIndexOf("/") + 1, oldImage.length());
	            }
	            String fileName;
	            if (image == null || image.getSize() <= 0) {
	                fileName = oldName == null ? "" : oldName;
	            } else {
	                fileName = FileUtils.upLoadFile(imageGoodsDir, null, image);
	                needDelFile.add(imageGoodsDir + oldName);
	            }
	            setIndexImage(i, fileName, goodsShoesEntity);
	        }
        }
        // 处理详细描述
        List<MultipartFile> descriptionFiles = goodsShoesForm.getDescriptionFiles();
        List<String> descriptionStrings = goodsShoesForm.getDescriptions();
        List<String> descriptionStringList = new ArrayList<String>();
        if(descriptionFiles != null && descriptionFiles.size() > 0 && descriptionStrings != null && descriptionStrings.size() > 0) {
	        for (int i = 0; i < descriptionFiles.size(); i++) {
	            MultipartFile multipartFile = descriptionFiles.get(i);
	
	            String oldDesName = null;
	            if (i < descriptionStrings.size()) {
	                String oldDes = descriptionStrings.get(i);
	                oldDesName = oldDes.substring(oldDes.lastIndexOf("/") + 1, oldDes.length());
	            }
	            String fileName;
	            if (multipartFile == null || multipartFile.getSize() <= 0) {
	                fileName = oldDesName;
	            } else {
	                fileName = FileUtils.upLoadFile(imageGoodsDir, null, multipartFile);
	                if (oldDesName != null) {
	                    needDelFile.add(imageGoodsDir + oldDesName);
	                }
	            }
	            if (fileName != null) {
	                descriptionStringList.add(fileName);
	            }
	        }
        }
        goodsShoesEntity.setDescription(String.join(",", descriptionStringList));

        // 处理video
        MultipartFile videoFile = goodsShoesForm.getVideoFile();
        String video = goodsShoesForm.getVideo();
        String oldVideoName = null;
        if (video != null && !video.trim().isEmpty()) {
            oldVideoName = video.substring(video.lastIndexOf("/") + 1, video.length());
        }

        if (videoFile != null && videoFile.getSize() > 0) {
            String videoName = FileUtils.upLoadFile(imageGoodsDir, null, videoFile);
            goodsShoesEntity.setVideo(videoName);
            if (oldVideoName != null)
                needDelFile.add(imageGoodsDir + oldVideoName);
        } else {
            goodsShoesEntity.setVideo(oldVideoName);
        }

        goodsShoesService.updateById(goodsShoesEntity);

        Integer goodsSeq = goodsShoesEntity.getSeq();

        GoodsShoesPlatformForm goodsShoesPlatformForm = goodsShoesForm.getOrderPlatform();
        GoodsShoesOnlineForm goodsShoesOnlineForm = goodsShoesForm.getOnlineSale();
        if (goodsShoesPlatformForm == null && goodsShoesOnlineForm == null) {
            throw new RRException("选择平台编辑库存！");
        }

        //订货平台数据不是空
        if (goodsShoesPlatformForm != null) {
            // 插入查看类型
            EntityWrapper<io.nuite.modules.order_platform_app.entity.ShoesCompanyTypeEntity> entityEntityWrapper =
                    new EntityWrapper<>();
            entityEntityWrapper.eq("Shoes_Seq", goodsSeq);
            orderShoesCompanyTypeDao.delete(entityEntityWrapper);
            List<Integer> authors = goodsShoesPlatformForm.getAuthor();
            authors.add(1);
            orderShoesCompanyTypeDao.insertBatch(goodsSeq, authors);

            EntityWrapper<io.nuite.modules.order_platform_app.entity.ShoesInfoEntity> entityWrapper =
                    new EntityWrapper<>();
            entityWrapper.eq("Shoes_Seq", goodsSeq);
            List<io.nuite.modules.order_platform_app.entity.ShoesInfoEntity> shoesInfoEntities = orderShoesInfoDao.selectList(entityWrapper);
            io.nuite.modules.order_platform_app.entity.ShoesInfoEntity shoesInfoEntity;

            if (shoesInfoEntities.size() > 0) {
                shoesInfoEntity = shoesInfoEntities.get(0);
            } else {
                shoesInfoEntity = new io.nuite.modules.order_platform_app.entity.ShoesInfoEntity();
            }
            shoesInfoEntity.setShoesSeq(goodsSeq);
            shoesInfoEntity.setIsNewest(goodsShoesForm.getIsNewest() != null && goodsShoesForm.getIsNewest() ? 1 : 0);
            shoesInfoEntity.setIsHotSale(goodsShoesForm.getIsHotSale() != null && goodsShoesForm.getIsHotSale() ? 1 : 0);

            shoesInfoEntity.setOemPrice(goodsShoesPlatformForm.getOemPrice());
            shoesInfoEntity.setWholesalerPrice(goodsShoesPlatformForm.getWholesalerPrice());
            shoesInfoEntity.setStorePrice(goodsShoesPlatformForm.getStorePrice());
            shoesInfoEntity.setSalePrice(goodsShoesPlatformForm.getSalePrice());
            shoesInfoEntity.setOnSale(goodsShoesPlatformForm.getOnSale() != null && goodsShoesPlatformForm.getOnSale() ? 1 : 0);
            shoesInfoEntity.setOnSaleTime(goodsShoesPlatformForm.getOnSaleTime());
            shoesInfoEntity.setOffSaleTime(goodsShoesPlatformForm.getOffSaleTime());
            if (shoesInfoEntity.getSeq() != null) {
                orderShoesInfoDao.updateAllColumnById(shoesInfoEntity);
            } else {
                orderShoesInfoDao.insert(shoesInfoEntity);
            }

            EntityWrapper<io.nuite.modules.order_platform_app.entity.ShoesDataEntity> ew = new EntityWrapper<>();
            ew.eq("Shoes_Seq", goodsSeq).eq("Del", 0);
            List<io.nuite.modules.order_platform_app.entity.ShoesDataEntity> orderDataList = orderShoesDataDao.selectList(ew);
            List<GoodsShoesStockForm> stockForms = goodsShoesPlatformForm.getStock();
            // 查找新增\修改的颜色尺码
            for (GoodsShoesStockForm stockForm : stockForms) {
                io.nuite.modules.order_platform_app.entity.ShoesDataEntity oldShoesDataEntity = null;
                for (io.nuite.modules.order_platform_app.entity.ShoesDataEntity shoesDataEntity : orderDataList) {
                    if (stockForm.getSeq().equals(shoesDataEntity.getColorSeq()) && stockForm.getSizeSeq().equals(shoesDataEntity.getSizeSeq())) {
                        oldShoesDataEntity = shoesDataEntity;
                        break;
                    }
                }

                insertOrUpdateOrderPlatformShoesData(goodsSeq, stockForm, oldShoesDataEntity);
            }
            // 查找删除的颜色尺码
            for (io.nuite.modules.order_platform_app.entity.ShoesDataEntity shoesDataEntity : orderDataList) {
                boolean isDel = true;
                for (GoodsShoesStockForm stockForm : stockForms) {
                    if (stockForm.getSeq().equals(shoesDataEntity.getColorSeq()) && stockForm.getSizeSeq().equals(shoesDataEntity.getSizeSeq())) {
                        isDel = false;
                        break;
                    }
                }
                if (isDel) {
                    orderShoesDataDao.deleteById(shoesDataEntity.getSeq());
                }
            }
        }

        //分销平台数据不是空
        if (goodsShoesOnlineForm != null) {
            EntityWrapper<io.nuite.modules.online_sales_app.entity.ShoesInfoEntity> entityWrapper =
                    new EntityWrapper<>();
            entityWrapper.eq("Shoes_Seq", goodsSeq).eq("Del", 0);
            List<io.nuite.modules.online_sales_app.entity.ShoesInfoEntity> shoesInfoEntities = onlineShoesInfoDao.selectList(entityWrapper);
            io.nuite.modules.online_sales_app.entity.ShoesInfoEntity shoesInfoEntity;
            if (shoesInfoEntities.size() > 0) {
                shoesInfoEntity = shoesInfoEntities.get(0);
            } else {
                shoesInfoEntity = new io.nuite.modules.online_sales_app.entity.ShoesInfoEntity();
            }
            shoesInfoEntity.setShoesSeq(goodsSeq);
            shoesInfoEntity.setIsNewest(goodsShoesForm.getIsNewest() != null && goodsShoesForm.getIsNewest() ? 1 : 0);
            shoesInfoEntity.setIsHotSale(goodsShoesForm.getIsHotSale() != null && goodsShoesForm.getIsHotSale() ? 1 : 0);

            shoesInfoEntity.setTagPrice(goodsShoesOnlineForm.getTagPrice());
            shoesInfoEntity.setSalePrice(goodsShoesOnlineForm.getSalePrice());
            if (shoesInfoEntity.getSeq() != null) {
                onlineShoesInfoDao.updateById(shoesInfoEntity);
            } else {
                onlineShoesInfoDao.insert(shoesInfoEntity);
            }

            EntityWrapper<io.nuite.modules.online_sales_app.entity.ShoesDataEntity> ew = new EntityWrapper<>();
            ew.eq("Shoes_Seq", goodsSeq).eq("Del", 0);
            List<io.nuite.modules.online_sales_app.entity.ShoesDataEntity> orderDataList = onlineShoesDataDao.selectList(ew);
            List<GoodsShoesStockForm> stockForms = goodsShoesOnlineForm.getStock();
            // 查找新增\修改的颜色尺码
            for (GoodsShoesStockForm stockForm : stockForms) {
                io.nuite.modules.online_sales_app.entity.ShoesDataEntity oldShoesDataEntity = null;
                for (io.nuite.modules.online_sales_app.entity.ShoesDataEntity shoesDataEntity : orderDataList) {
                    if (stockForm.getSeq().equals(shoesDataEntity.getColorSeq()) && stockForm.getSizeSeq().equals(shoesDataEntity.getSizeSeq())) {
                        oldShoesDataEntity = shoesDataEntity;
                        break;
                    }
                }

                insertOrUpdateOnlineShoesData(goodsSeq, stockForm, oldShoesDataEntity);
            }
            // 查找删除的颜色尺码
            for (io.nuite.modules.online_sales_app.entity.ShoesDataEntity shoesDataEntity : orderDataList) {
                boolean isDel = true;
                for (GoodsShoesStockForm stockForm : stockForms) {
                    if (stockForm.getSeq().equals(shoesDataEntity.getColorSeq()) && stockForm.getSizeSeq().equals(shoesDataEntity.getSizeSeq())) {
                        isDel = false;
                        break;
                    }
                }
                if (isDel) {
                    onlineShoesDataDao.deleteById(shoesDataEntity.getSeq());
                }
            }
        }
        return needDelFile;
    }

    private void setGoodsSxValue(String sxArray, GoodsShoesEntity goodsShoesEntity) throws Exception {
        // 反射设置SX1-20
        JSONObject sxJson = JSONObject.parseObject(sxArray);
        for (String key : sxJson.keySet()) {
            Field keyField = GoodsShoesEntity.class.getDeclaredField(key.toUpperCase());
            keyField.setAccessible(true);
            String value = sxJson.getString(key);
            if (StringUtils.isNotBlank(value)) {
                keyField.set(goodsShoesEntity, value);
            }
        }
    }

    private void insertOrUpdateOrderPlatformShoesData(Integer goodsSeq, GoodsShoesStockForm stockForm, io.nuite.modules.order_platform_app.entity.ShoesDataEntity shoesDataEntity) {
        int stockNum = stockForm.getStock() != null ? stockForm.getStock() : 0;
        if (shoesDataEntity == null) {
            shoesDataEntity = new io.nuite.modules.order_platform_app.entity.ShoesDataEntity();
            shoesDataEntity.setNum(stockNum);
        } else {
            int newNumber = 0;
            if(shoesDataEntity.getNum() != null) {
                newNumber = stockNum - shoesDataEntity.getStock() + shoesDataEntity.getNum();
            }else {
                newNumber = stockNum - shoesDataEntity.getStock();
            }

            shoesDataEntity.setNum(newNumber);
        }
        shoesDataEntity.setShoesSeq(goodsSeq);
        shoesDataEntity.setColorSeq(stockForm.getSeq());
        shoesDataEntity.setSizeSeq(stockForm.getSizeSeq());
        shoesDataEntity.setRemark(stockForm.getRemark());
        shoesDataEntity.setStock(stockNum);
        shoesDataEntity.setStockDate(new Date());
        if (shoesDataEntity.getSeq() != null) {
            orderShoesDataDao.updateById(shoesDataEntity);
        } else {
            orderShoesDataDao.insert(shoesDataEntity);
        }
    }

    private void insertOrUpdateOnlineShoesData(Integer goodsSeq, GoodsShoesStockForm stockForm, io.nuite.modules.online_sales_app.entity.ShoesDataEntity shoesDataEntity) {
        int stockNum = stockForm.getStock() != null ? stockForm.getStock() : 0;
        if (shoesDataEntity == null) {
            shoesDataEntity = new io.nuite.modules.online_sales_app.entity.ShoesDataEntity();
            shoesDataEntity.setNum(stockNum);
        } else {
            int newNumber = stockNum - shoesDataEntity.getStock() + shoesDataEntity.getNum();
            shoesDataEntity.setNum(newNumber);
        }
        shoesDataEntity.setShoesSeq(goodsSeq);
        shoesDataEntity.setColorSeq(stockForm.getSeq());
        shoesDataEntity.setSizeSeq(stockForm.getSizeSeq());
        shoesDataEntity.setStock(stockNum);
        shoesDataEntity.setSetStock(stockForm.getSetStock() != null ? stockForm.getSetStock() : 0);
        shoesDataEntity.setStockDate(new Date());
        if (shoesDataEntity.getSeq() != null) {
            onlineShoesDataDao.updateById(shoesDataEntity);
        } else {
            onlineShoesDataDao.insert(shoesDataEntity);
        }
    }

    private void setIndexImage(int index, String fileName, GoodsShoesEntity goodsShoesEntity) {
        switch (index) {
            case 0:
                goodsShoesEntity.setImg1(fileName);
                break;
            case 1:
                goodsShoesEntity.setImg2(fileName);
                break;
            case 2:
                goodsShoesEntity.setImg3(fileName);
                break;
            case 3:
                goodsShoesEntity.setImg4(fileName);
                break;
            case 4:
                goodsShoesEntity.setImg5(fileName);
                break;
        }
    }

    @Override
    public Map<String, List<GoodsPeriodEntity>> getUserPeriods(BaseUserEntity userEntity) {
        EntityWrapper<GoodsPeriodEntity> ew = new EntityWrapper<GoodsPeriodEntity>();
        ew.eq("Brand_Seq", userEntity.getBrandSeq()).eq("Del", 0).orderDesc(Collections.singletonList("InputTime"));
        List<GoodsPeriodEntity> periods = goodsPeriodDao.selectList(ew);

        Map<String, List<GoodsPeriodEntity>> periodMap = new HashMap<String, List<GoodsPeriodEntity>>();
        for (GoodsPeriodEntity goodsPeriodEntity : periods) {
            String year = goodsPeriodEntity.getYear().toString();
            List<GoodsPeriodEntity> list = periodMap.computeIfAbsent(year, k -> new ArrayList<>());
            list.add(goodsPeriodEntity);
        }
        return periodMap;
    }

    @Override
    public List<GoodsCategoryEntity> getUserCategory(BaseUserEntity userEntity) {

    	 EntityWrapper<GoodsCategoryEntity> ew = new EntityWrapper<>();
         ew.eq("Company_Seq", userEntity.getCompanySeq()).eq("Del", 0);
         return goodsCategoryDao.selectList(ew);
    }

    /**
     * 新增波次
     */
    @Override
    @Transactional
    public void savePeriod(BaseUserEntity userEntity, GoodsPeriodEntity goodsPeriodEntity) {
        goodsPeriodEntity.setInputTime(new Date());
        goodsPeriodEntity.setBrandSeq(userEntity.getBrandSeq());
        goodsPeriodEntity.setDel(0);
        goodsPeriodDao.insert(goodsPeriodEntity);
    }

    /**
     * 上下架方法
     */
    @Override
    @Transactional
    public void upAndDown(Long userSeq, Integer goodsSeq, int platform, int updown) {
        //TODO 判断商品是否属于当前用户
        switch (platform) {
            case 0:
                orderShoesInfoDao.updateOnSale(goodsSeq, updown);
                break;
            case 1:
                onlineShoesInfoDao.updateOnSale(goodsSeq, updown);
                break;
        }
    }

    @Override
    public Map<String, File> getGoodsWxQRCode(Integer userSeq, Integer companySeq, Map<String, Object> params) {
        MiniAppEntity miniAppEntity = olsMiniAppService.queryOneByCompanySeq(companySeq);
        WxMyService wxMyService = WxServiceUtils.getWxMyService(miniAppEntity);
        if (wxMyService == null) {
            throw new RRException("小程序信息不完整");
        }
        
		/*解析筛选条件params*/
        List<Integer> categoryList = new ArrayList<>();
        List<Integer> periodList = new ArrayList<>();
        Object categorySeqObj = params.get("categorySeq");
        int categorySeq;
        if(categorySeqObj == null || StringUtils.isEmpty(categorySeqObj.toString()) || categorySeqObj.toString().equals("-1")) {
        	categorySeq = 0;
        } else {
        	categorySeq = Integer.valueOf(categorySeqObj.toString());
        }
        // 不为空则判断是否有子类
        getCategorySeqList(categoryList, Collections.singletonList(categorySeq));

        Object periodSeqObj = params.get("periodSeq");
        // 如果波次为空 则查找该用户所有的波次
        if (periodSeqObj == null || StringUtils.isEmpty(periodSeqObj.toString()) || periodSeqObj.toString().equals("-1")) {
            periodList = orderProductManagementDao.getPeriodSeqList(userSeq.longValue());
        } else {
            periodList.add(Integer.valueOf(periodSeqObj.toString()));
        }

        String keywords = (String) params.get("keywords");
        if (keywords != null) {
            keywords = keywords.trim();
        }

        /*过滤条件，是否上架*/
        Integer onSale = Integer.valueOf((String) params.get("onSale"));

        Page<GoodsViewEntity> page = new Query<GoodsViewEntity>(params).getPage();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("periodSeqs", Joiner.on(",").join(periodList));
        map.put("categorySeqs", Joiner.on(",").join(categoryList));
        map.put("goodNameId", keywords);
        map.put("onSaleType", onSale);
        
        map.put("companySeq", companySeq);
        /*查询货品，生成或直接获取二维码文件*/
        List<Map<String, Object>> allShoes = onlineShoesInfoDao.queryCurrentAllShoes(map);
        
        
        Map<String, File> wxQRCodeMap = new HashMap<>();
        String imageGoodsDir = FileUtils.getWebAppsPath() + configUtils.getPictureBaseUploadProject() + File.separator
                + configUtils.getBaseDir() + File.separator + configUtils.getGoodsShoes() + File.separator;
        
        
        for (Map<String, Object> shoesMap : allShoes) {
            Integer goodsSeq = Integer.valueOf(shoesMap.get("seq").toString());
            String wxQRCode = (String) shoesMap.get("wxQRCode");
            String goodsId = (String) shoesMap.get("goodId");
            Integer olsInfoSeq = Integer.valueOf(shoesMap.get("olsInfoSeq").toString());
            if (wxQRCode == null || wxQRCode.isEmpty()) {
                try {
                    File wxCodeFile = wxMyService.getWxMaService().getQrcodeService().createWxCodeLimit("seq=" + goodsSeq, "pages/goods/detail/detail", 280, false, null);
                    FileUtils.copyFile(imageGoodsDir + goodsId + File.separator, null, wxCodeFile);

                    io.nuite.modules.online_sales_app.entity.ShoesInfoEntity onlineInfo = new io.nuite.modules.online_sales_app.entity.ShoesInfoEntity();
                    onlineInfo.setSeq(olsInfoSeq);
                    onlineInfo.setWxQRCode(wxCodeFile.getName());
                    onlineShoesInfoDao.updateById(onlineInfo);

                    wxQRCodeMap.put(goodsId, wxCodeFile);
                } catch (Exception e) {
                    wxQRCodeMap.put(goodsId, null);
                    logger.error(e.getMessage(), e);
                }
            } else {
                wxQRCodeMap.put(goodsId, new File(imageGoodsDir + goodsId + File.separator + wxQRCode));
            }
        }

        //wxQRCodeMap写入excel文件

        //返回写入的文件路径
        return wxQRCodeMap;
    }

    
    /**
     * 在服务器上WxQRCodeTemp文件夹创建一个临时压缩包（没做下载功能，相当于没用）
     */
    @Override
    public String getGoodsWxQRCodeZip(Integer companySeq, Integer brandSeq) {
		return null;
//        Map<String, File> fileMap = getGoodsWxQRCode(companySeq, brandSeq);
//
//        String wxQRCodeTempDir = FileUtils.getWebAppsPath() + configUtils.getPictureBaseUploadProject() + File.separator
//                + configUtils.getBaseDir() + File.separator + "WxQRCodeTemp" + File.separator;
//        String tempTime = String.valueOf(System.currentTimeMillis());
//        String wxQRCodeTempTimeDir = wxQRCodeTempDir + tempTime + File.separator;
//        File wxQRCodeDir = new File(wxQRCodeTempTimeDir);
//        if (!wxQRCodeDir.exists()) wxQRCodeDir.mkdirs();
//
//        for (Object obj : fileMap.entrySet()) {
//            Map.Entry entry = (Map.Entry) obj;
//            String key = (String) entry.getKey();
//            File value = (File) entry.getValue();
//            try {
//                FileUtils.copyFile(wxQRCodeTempTimeDir, key + ".jpg", value);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        try {
//            ZipUtils.zip(wxQRCodeTempTimeDir, wxQRCodeTempDir, tempTime + ".zip");
//        } catch (Exception e) {
//            throw new RRException("压缩失败");
//        }
//
//        return tempTime;
    }

    
    /**
     * 获取小程序码Excel中货品List
     */
	@Override
	public List<Map<String, Object>> getMiniAppCodeExcelShoesList(Integer userSeq, Integer companySeq, Map<String, Object> params) {
		/*解析筛选条件params*/
        List<Integer> categoryList = new ArrayList<>();
        List<Integer> periodList = new ArrayList<>();
        Object categorySeqObj = params.get("categorySeq");
        int categorySeq;
        if(categorySeqObj == null || StringUtils.isEmpty(categorySeqObj.toString()) || categorySeqObj.toString().equals("-1")) {
        	categorySeq = 0;
        } else {
        	categorySeq = Integer.valueOf(categorySeqObj.toString());
        }
        // 不为空则判断是否有子类
        getCategorySeqList(categoryList, Collections.singletonList(categorySeq));

        Object periodSeqObj = params.get("periodSeq");
        // 如果波次为空 则查找该用户所有的波次
        if (periodSeqObj == null || StringUtils.isEmpty(periodSeqObj.toString()) || periodSeqObj.toString().equals("-1")) {
            periodList = orderProductManagementDao.getPeriodSeqList(userSeq.longValue());
        } else {
            periodList.add(Integer.valueOf(periodSeqObj.toString()));
        }

        String keywords = (String) params.get("keywords");
        if (keywords != null) {
            keywords = keywords.trim();
        }

        /*过滤条件，是否上架*/
        Integer onSale = Integer.valueOf((String) params.get("onSale"));

        Page<GoodsViewEntity> page = new Query<GoodsViewEntity>(params).getPage();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("periodSeqs", Joiner.on(",").join(periodList));
        map.put("categorySeqs", Joiner.on(",").join(categoryList));
        map.put("goodNameId", keywords);
        map.put("onSaleType", onSale);
        
        map.put("companySeq", companySeq);
        //查询货品颜色list供excel使用
        List<Map<String, Object>> shoesList = onlineShoesInfoDao.queryMiniAppCodeExcelShoes(map);
		
        //添加层级分类、颜色名称
		for(Map<String, Object> shoesMap : shoesList) {
			Integer colorSeq = (Integer) shoesMap.get("colorSeq");
			GoodsColorEntity goodsColorEntity = goodsColorDao.selectById(colorSeq);
			shoesMap.put("colorName", goodsColorEntity.getName());
			
			Integer shoesCategorySeq = (Integer) shoesMap.get("categorySeq");
			String categoryName = recurstGetCategoryName("", shoesCategorySeq);
			
			if(categoryName != null && categoryName.length() > 0) {
				categoryName = categoryName.substring(0, categoryName.length() - 1);
			}
			shoesMap.put("categoryName", categoryName);
		}
		
		return shoesList;
	}
	
	//递归获取层级分类名称 （- 分隔）
	private String recurstGetCategoryName(String categoryName, Integer categorySeq) {
		GoodsCategoryEntity goodsCategoryEntity = goodsCategoryDao.selectById(categorySeq);
		if(goodsCategoryEntity.getParentSeq() != 0) {
			return recurstGetCategoryName(goodsCategoryEntity.getName() + "-" + categoryName, goodsCategoryEntity.getParentSeq());
		} else {
			return goodsCategoryEntity.getName() + "-" + categoryName;
		}
	}

}
