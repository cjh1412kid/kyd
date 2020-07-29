package io.nuite.modules.system.controller.order_platform;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.binarywang.utils.qrcode.QrcodeUtils;
import com.mchange.io.FileUtils;
import io.nuite.common.exception.RRException;
import io.nuite.common.utils.PageUtils;
import io.nuite.common.utils.R;
import io.nuite.common.utils.SpringContextUtils;
import io.nuite.common.utils.ZipUtils;
import io.nuite.modules.sr_base.entity.*;
import io.nuite.modules.sr_base.service.GoodsBrandService;
import io.nuite.modules.sr_base.service.GoodsSXService;
import io.nuite.modules.sr_base.service.GoodsShoesService;
import io.nuite.modules.sr_base.utils.ConfigUtils;
import io.nuite.modules.system.controller.AbstractController;
import io.nuite.modules.system.controller.SystemGoodsCategoryController;
import io.nuite.modules.system.model.GoodsShoesForm;
import io.nuite.modules.system.service.ExcelGoodsService;
import io.nuite.modules.system.service.order_platform.OrderProductManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 订货平台货品管理
 *
 * @author fl
 * @date 2018-04-25
 */

@RestController
@RequestMapping("/order/goods")
@Api(tags = "后台  - 货品管理相关接口", description = "货品管理")
public class OrderGoodsController extends AbstractController {
    @Autowired
    private OrderProductManagementService orderProductManagementService;
    @Autowired
    private GoodsShoesService goodsShoesService;
    @Autowired
    private GoodsSXService goodsSXService;
    @Autowired
    protected ConfigUtils configUtils;

    @Autowired
    private GoodsBrandService goodsBrandService;


    /**
     * 获取公司的自定义属性名称和对应字段json（如： {"鞋面材质":"SX1Value", "鞋跟高度":"SX3Value"}   ）
     */
    @GetMapping("/getGoodsSXLabel")
    public R getGoodsSXLabel(@RequestParam Map<String, Object> params) {
        try {
            // 公司所有自定义属性list
            List<GoodsSXEntity> goodsSXList = goodsSXService.getGoodsSXListByCompanySeqAndVisibled(getUser().getCompanySeq());
            Map<String, String> SXLabelMap = new LinkedHashMap<>();
            for (GoodsSXEntity goodsSXEntity : goodsSXList) {
                SXLabelMap.put(goodsSXEntity.getSXId().toLowerCase() + "Value", goodsSXEntity.getSXName());
            }
            if("温州鞋世界".equals(getUser().getUserName())) {
                SXLabelMap.put("setStock","预扣");
            }
            return R.ok().put("result", SXLabelMap);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }

    /**
     * 获取公司的自定义属性名称、对应字段以及选项值json（如： [{"name":"鞋面材质","key":"SX1",values:[]}]   ）
     */
    @GetMapping("/getGoodsSXOption")
    public R getGoodsSX(@RequestParam Map<String, Object> params) {
        try {
            // 公司所有自定义属性list
            List<GoodsSXEntity> goodsSXList = goodsSXService.getGoodsSXContainOptionByCompanySeq(getUser().getCompanySeq());
            return R.ok().put("result", goodsSXList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }


    /**
     * 货品的列表显示 @RequestMapping("list") public R list() { PageUtils companyBrandPage
     * = systemBrandService.queryBrandByUser(getUserSeq()); return
     * R.ok().put("page", companyBrandPage); }
     */
    @PostMapping("/getGoodsList")
    public R productList(@RequestParam Map<String, Object> params) {
        try {
            // 货品列表
            PageUtils shoesPage = orderProductManagementService.getGoodsList(getUserSeq(), params);
            // 循环拼接图片路径
            return R.ok().put("page", shoesPage);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }

    /**
     * 商品的删除操作
     */
    @GetMapping("/delete")
    @ApiOperation("获评的列表显示")
    public R delete(@ApiParam("商品的seq") @RequestParam("seq") Integer seq) {
        return R.ok(orderProductManagementService.delete(seq));
    }

    /**
     * 根据对应分类的seq获得子分类列表
     */
    @PostMapping("/getCategoryByParentSeq")
    @ApiOperation("根据对应分类的seq获得子分类列表") // 用户前端分类的列表
    public R getCategoryByParentSeq(@ApiParam("类型的seq") @RequestParam("seq") Integer seq) {
        try {
            List<GoodsCategoryEntity> list = orderProductManagementService.getCategoryByParentSeq(seq);
            return R.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }

    /**
     * 添加鞋子信息
     */
    @PostMapping("/addGoods")
    @ApiOperation("添加鞋子信息")
    public R addGoods(GoodsShoesForm goodsShoesForm) {
        try {
            orderProductManagementService.addGoods(goodsShoesForm, getUser().getCompanySeq());
            return R.ok();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error("添加失败！");
        }
    }

    /**
     * 修改鞋子信息
     */
    @PostMapping("/updateGoods")
    @ApiOperation("修改鞋子信息")
    public R updateGoods(GoodsShoesForm goodsShoesForm) {
        try {
            List<String> needDel = orderProductManagementService.updateGoods(goodsShoesForm);
            // 存储成功之后删除 旧图片
            for (String filePath : needDel) {
                new File(filePath).deleteOnExit();
            }
            return R.ok();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error("修改失败！");
        }
    }

    /**
     * 根据鞋子的seq返回鞋子的基本信息
     */
    @GetMapping("/edit")
    @ApiOperation("根据Seq获取鞋子信息")
    public R edit(@ApiParam("商品的seq") @RequestParam("seq") Integer seq) {
        GoodsShoesForm goodsShoesForm = orderProductManagementService.edit(seq);
        return R.ok().put("goods", goodsShoesForm);
    }

    /**
     * 添加波次
     */
    @PostMapping("/addPeriod")
    @ApiOperation("添加波次")
    public R addPeriod(@RequestBody GoodsPeriodEntity period) {
        try {
            orderProductManagementService.savePeriod(getUser(), period);
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    /**
     * 获取当前用户波次列表 按年分类
     */
    @GetMapping("/periods")
    @ApiOperation("获取当前用户波次列表、按年分类")
    public R getUserPeriods() {
        Map result = orderProductManagementService.getUserPeriods(getUser());
        return R.ok().put("periods", result);
    }

    /**
     * 获取当前用户的所有鞋子分类
     *
     * @see SystemGoodsCategoryController#list()
     */
    @Deprecated
    @GetMapping("/category")
    @ApiOperation("获取当前用户的所有鞋子分类")
    public R getUserCategory() {
        List result = orderProductManagementService.getUserCategory(getUser());
        return R.ok().put("categorys", result);
    }

    /**
     * 货品上下架接口
     *
     * @param platform 0订货，1分销
     * @param updown   0下架，1上架
     */
    @PostMapping("/updown")
    public R upAndDown(Integer seq, int platform, int updown) {
        if (seq == null || (platform != 0 && platform != 1) || (updown != 0 && updown != 1)) {
            return R.error("参数错误");
        }
        orderProductManagementService.upAndDown(getUserSeq(), seq, platform, updown);
        return R.ok();
    }


    /**
     * 导出订货平台商品二维码
     *
     * @param response
     */
    @GetMapping("exportQRCode")
    public void exportQRCode(HttpServletResponse response) {
        ServletOutputStream out = null;
        HSSFWorkbook wb = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("货号二维码.xls", "UTF-8"));
            // 创建excel
            wb = new HSSFWorkbook();

            //列标题单元格样式
            HSSFCellStyle headCellStyle = wb.createCellStyle();
            //设置居中:
            headCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
            headCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
            //设置字体:
            HSSFFont font = wb.createFont();
            font.setFontName("仿宋_GB2312");
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
            font.setFontHeightInPoints((short) 12);
            headCellStyle.setFont(font);//选择需要用到的字体格式

            //内容单元格样式
            HSSFCellStyle contentCellStyle = wb.createCellStyle();
            //设置居中:
            contentCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
            contentCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中

            // 创建sheet页
            HSSFSheet sheet = wb.createSheet("sheet1");
            //默认宽度和高度
            sheet.setDefaultColumnWidth(15);
            sheet.setDefaultRowHeightInPoints(18);
            //不同列的宽度
            sheet.setColumnWidth(0, 18 * 256);
            sheet.setColumnWidth(1, 18 * 256);
            sheet.setColumnWidth(2, 40 * 256);


            String[] title = {"货品名称", "货号", "二维码"};
            HSSFRow row = sheet.createRow(0);
            row.setHeightInPoints(24);
            for (int i = 0; i < title.length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(headCellStyle);
                cell.setCellValue(title[i]);
            }


            // 订货平台全部已上架货品列表
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("periodSeq", "");
            params.put("categorySeq", "");
            params.put("onSale", "3");
            params.put("page", "1");
            params.put("limit", "5000");
            PageUtils shoesPage = orderProductManagementService.getGoodsList(getUserSeq(), params);
            List<GoodsViewEntity> shoesList = (List<GoodsViewEntity>) shoesPage.getList();


            // 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            // 循环多少次
            int h = 1;
            HSSFCell cell;
            for (int i = 0; i < shoesList.size(); i++) {
                row = sheet.createRow(h++);// 创建行
                row.setHeightInPoints(210);//行高度
                GoodsViewEntity goodsViewEntity = shoesList.get(i);// 获得行对象

                cell = row.createCell(0);
                cell.setCellStyle(contentCellStyle);
                cell.setCellValue(goodsViewEntity.getGoodName());

                cell = row.createCell(1);
                cell.setCellStyle(contentCellStyle);
                cell.setCellValue(goodsViewEntity.getGoodID());

                // anchor主要用于设置图片的属性
                HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) 2, h - 1, (short) 2, h - 1);
                // 插入图片
                patriarch.createPicture(anchor, wb.addPicture(QrcodeUtils.createQrcode(goodsViewEntity.getGoodID(), 280, null), HSSFWorkbook.PICTURE_TYPE_JPEG));

                sheet.createRow(h++);
            }
            // 获取输出流，写入excel并关闭
            out = response.getOutputStream();
            wb.write(out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("货品二维码导出失败: " + e.getMessage(), e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (wb != null) {
                    wb.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    
    /**
     * 生成小程序商品的货号excel和小程序码图片压缩包
     */
    @GetMapping("miniAppCode")
    public void miniAppCode(@RequestParam Map<String, Object> params,
    		HttpServletRequest request, HttpServletResponse response) {
        ZipOutputStream zipOut = null;
        try {
        	Integer userSeq = getUser().getSeq();
            Integer companySeq = getUser().getCompanySeq();
            
            
            String fileName = "货号小程序码.zip";
            String agent = (String) request.getHeader("USER-AGENT");
            if (agent != null && agent.contains("Firefox")) {
                // firefox
                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            } else {
                // others
                fileName = URLEncoder.encode(fileName, "UTF-8");
            }
            response.setHeader("Content-Type","application/octect-stream");
            response.setHeader("Content-Disposition","attachment;filename=" + fileName);
            
            
            zipOut = new ZipOutputStream(response.getOutputStream()); 
            
            byte[] buffer = new byte[5*1024];
            int length = 0;
            
            String excelFileName = "货号小程序码.xls";
            zipOut.putNextEntry(new ZipEntry(excelFileName));
            InputStream excelInputStream = createMiniAppCodeExcel(userSeq, companySeq, params);  //创建excel的流
            while ((length = excelInputStream.read(buffer)) != -1) {
            	zipOut.write(buffer, 0, length);
            }
            excelInputStream.close();

            
            String path = "图片";
            Map<String, File> fileMap = orderProductManagementService.getGoodsWxQRCode(userSeq, companySeq, params);
            for (Map.Entry<String, File> entry : fileMap.entrySet()) {
                String goodId = entry.getKey();
                File jpgFile = entry.getValue();
                
                String jpgFileName = goodId + ".jpg";
                zipOut.putNextEntry(new ZipEntry(path + File.separator + jpgFileName));
                InputStream jpgInputStream = new FileInputStream(jpgFile);
                while ((length = jpgInputStream.read(buffer)) != -1) {
                	zipOut.write(buffer, 0, length);
                }
                jpgInputStream.close();
            }
            
            zipOut.flush();
            
        } catch (Exception e) {
            logger.error("货品小程序码导出失败: " + e.getMessage(), e);
        } finally {
            try {
            	if (zipOut != null) {
                    zipOut.close();
            	}
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    
    //创建小程序码excel，返回输入流
    private InputStream createMiniAppCodeExcel(Integer userSeq, Integer companySeq, Map<String, Object> params) throws Exception{
        List<Map<String, Object>> shoesList = orderProductManagementService.getMiniAppCodeExcelShoesList(userSeq, companySeq, params);
        
        //创建excel
        HSSFWorkbook wb = new HSSFWorkbook();
        
        //列标题单元格样式
        HSSFCellStyle headCellStyle = wb.createCellStyle();
        //设置居中:
        headCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        headCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        //设置字体:
        HSSFFont font = wb.createFont();
        font.setFontName("仿宋_GB2312");
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        font.setFontHeightInPoints((short) 12);
        headCellStyle.setFont(font);//选择需要用到的字体格式

        //内容单元格样式
        HSSFCellStyle contentCellStyle = wb.createCellStyle();
        //设置居中:
        contentCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        contentCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中

        // 创建sheet页
        HSSFSheet sheet = wb.createSheet("sheet1");
        //默认宽度和高度
        sheet.setDefaultColumnWidth(15);
        sheet.setDefaultRowHeightInPoints(18);
        //不同列的宽度
        sheet.setColumnWidth(0, 18 * 256);
        sheet.setColumnWidth(1, 40 * 256);


        String[] title = {"工厂", "分类", "货号", "颜色", "小程序码图片"};
        HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(24);
        for (int i = 0; i < title.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(headCellStyle);
            cell.setCellValue(title[i]);
        }

        // 循环多少次
        int h = 1;
        HSSFCell cell;
        for (Map<String, Object> map : shoesList) {
            row = sheet.createRow(h++);// 创建行
            row.setHeightInPoints(18);//行高度

            cell = row.createCell(0);
            cell.setCellStyle(contentCellStyle);
            cell.setCellValue((String) map.get("factory"));
            
            cell = row.createCell(1);
            cell.setCellStyle(contentCellStyle);
            cell.setCellValue((String) map.get("categoryName"));
            
            cell = row.createCell(2);
            cell.setCellStyle(contentCellStyle);
            cell.setCellValue((String) map.get("goodId"));
            
            cell = row.createCell(3);
            cell.setCellStyle(contentCellStyle);
            cell.setCellValue((String) map.get("colorName"));
            
            cell = row.createCell(4);
            cell.setCellStyle(contentCellStyle);
            cell.setCellValue((String) map.get("goodId") + ".jpg");
        }
        
        //将生成excel转化为输入流返回
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        wb.write(outputStream);
        ByteArrayInputStream swapStream = new ByteArrayInputStream(outputStream.toByteArray());
        
        if (wb != null) {
            wb.close();
        }
        return swapStream;
    }
    
    
    
    /**
     * 在服务器上WxQRCodeTemp文件夹创建一个临时压缩包（没做下载功能，相当于没用）
     * @return
     */
    @GetMapping("miniAppCodeZip")
    public R miniAppCodeZip() {
        Integer companySeq = getUser().getCompanySeq();
        Integer brandSeq = getUser().getBrandSeq();

        try {
            String zipName = orderProductManagementService.getGoodsWxQRCodeZip(companySeq, brandSeq);
            return R.ok(zipName);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
    
    
    
    
    /**
     * 导入素材压缩包
     */
    @PostMapping("uploadSourceZip")
    @ApiOperation("导入素材压缩包")
    public R uploadSourceZip(@ApiParam("压缩包文件") @RequestParam(value = "sourceZipFile", required = false) MultipartFile sourceZipFile) {
        try {
            if (sourceZipFile.isEmpty()) {
                return R.error("文件不存在！");
            }

            Integer companySeq = getUser().getCompanySeq();
            //解压路径
            String decpPath = io.nuite.common.utils.FileUtils.getWebAppsPath() + configUtils.getPictureBaseUploadProject() + "/" + configUtils.getBaseDir() + "/" + configUtils.getGoodsShoes() + "/";
            //解压素材文件
            Map<String, Map<String, Integer>> map = ZipUtils.unZipFiles(decpPath, sourceZipFile);
            //根据解压结果，修改商品
            if (map != null && map.size() > 0) {
                Map<String, Integer> Dmap = map.get("Dmap");   //商品详细图片个数
                Map<String, Integer> Mmap = map.get("Mmap");    //商品轮播图图片个数
                Map<String, Integer> Vmap = map.get("Vmap");    //视频是否存在
                //遍历轮播图Mmap, 获取并修改商品
                for (Entry<String, Integer> entry : Mmap.entrySet()) {
                    String gooId = entry.getKey(); //货号
                    Integer Mnum = entry.getValue(); //轮播图个数
                    if (Mnum == 0) {
                        return R.error("导入失败，货号：" + gooId + " 没有找到主图片");
                    }

                    //根据货号获取商品
                    GoodsShoesEntity goodsEntity = goodsShoesService.getGoodsByGoodId(companySeq, gooId);
                    if (goodsEntity == null) {
                        continue;
                        //return R.error("导入失败，货号：" + gooId + " 不存在，请先新增货品");
                    }
                    //1.设置商品轮播图图片个数
                    SetUpMnum(goodsEntity, Mnum);

                    //2.设置商品详情图片个数
                    if (Dmap.containsKey(gooId)) {
                        Integer Dnum = Dmap.get(gooId);
                        if (Dnum == 0) {
                            SetUpDnum(goodsEntity, 1,"M");
                          //  return R.error("导入失败，货号：" + gooId + " 没有找到详细图片");
                        }else {
                            SetUpDnum(goodsEntity, Dnum, "D");
                        }
                    } else {
                        return R.error("导入失败，货号：" + gooId + " 没有找到详细图片");
                    }

                    //3.设置商品视频
                    if (Vmap.containsKey(gooId)) {
                        goodsEntity.setVideo("video.mp4");
                    } else {
                        goodsEntity.setVideo(null);
                    }

                    goodsShoesService.updateAllColumnById(goodsEntity);
                }

                return R.ok("导入成功");
            } else {
                return R.error("导入失败！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("导入失败:" + e.getMessage());
        }

    }

    //设置商品轮播图个数
    private void SetUpMnum(GoodsShoesEntity goodsShoesEntity, Integer mnum) {
        goodsShoesEntity.setImg1(null);
        goodsShoesEntity.setImg2(null);
        goodsShoesEntity.setImg3(null);
        goodsShoesEntity.setImg4(null);
        goodsShoesEntity.setImg5(null);
        if (mnum >= 1) {
            goodsShoesEntity.setImg1("M1.jpg");
        }
        if (mnum >= 2) {
            goodsShoesEntity.setImg2("M2.jpg");
        }
        if (mnum >= 3) {
            goodsShoesEntity.setImg3("M3.jpg");
        }
        if (mnum >= 4) {
            goodsShoesEntity.setImg4("M4.jpg");
        }
        if (mnum >= 5) {
            goodsShoesEntity.setImg5("M5.jpg");
        }
    }

    //设置商品详细个数
    private void SetUpDnum(GoodsShoesEntity goodsShoesEntity, Integer dnum,String  firstName) {
        StringBuilder des = new StringBuilder();
        for (int i = 1; i <= dnum; i++) {
            des.append(firstName + i + ".jpg").append(",");
        }
        des.deleteCharAt(des.length() - 1);
        goodsShoesEntity.setDescription(des.toString());
    }

    @PostMapping("uploadGoodsExcel")
    public R uploadExcelFile(MultipartFile goodsExcelFile) {
        if (goodsExcelFile.isEmpty()) {
            return R.error("文件不存在！");
        }
        Integer companySeq = getUser().getCompanySeq();
        Integer brandSeq = getUser().getBrandSeq();

        try {
            ExcelGoodsService excelGoodsService = SpringContextUtils.getBean("qiangrenExcelGoods", ExcelGoodsService.class);
            excelGoodsService.importExcel(companySeq, brandSeq, goodsExcelFile);
            return R.ok();
        } catch (RRException e) {
            return R.error(e.getMsg());
        } catch (Exception e) {
            logger.error("解析文件失败", e);
            return R.error("文件解析失败");
        }
    }

    @RequestMapping("shoesBrand")
    public R getShoesBrand() {
        Integer companySeq = getUser().getCompanySeq();
        List<GoodsBrandEntity> brands = goodsBrandService.selectList(new EntityWrapper<GoodsBrandEntity>().eq("Company_Seq", companySeq));
        return R.ok().put("brands", brands);
    }

    @PostMapping("uploadOnlineGoodsExcel")
    public R uploadOnlineGoodsExcel(MultipartFile goodsExcelFile) {
        if (goodsExcelFile.isEmpty()) {
            return R.error("文件不存在！");
        }
        Integer companySeq = getUser().getCompanySeq();
        Integer brandSeq = getUser().getBrandSeq();

        try {
            ExcelGoodsService excelGoodsService = SpringContextUtils.getBean("qiangrenExcelGoods", ExcelGoodsService.class);
            excelGoodsService.importOnlineGoodsExcel(companySeq, brandSeq, goodsExcelFile);
            return R.ok();
        } catch (RRException e) {
            return R.error(e.getMsg());
        } catch (Exception e) {
            logger.error("解析文件失败", e);
            return R.error("文件解析失败");
        }
    }
}
