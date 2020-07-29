package io.nuite.modules.system.controller.online_sale;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.nuite.common.utils.DateUtils;
import io.nuite.common.utils.PageUtils;
import io.nuite.common.utils.R;
import io.nuite.modules.online_sales_app.entity.*;
import io.nuite.modules.online_sales_app.service.*;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.sr_base.entity.GoodsCategoryEntity;
import io.nuite.modules.sr_base.entity.GoodsColorEntity;
import io.nuite.modules.sr_base.entity.GoodsPeriodEntity;
import io.nuite.modules.sr_base.service.GoodsCategoryService;
import io.nuite.modules.sr_base.service.GoodsColorService;
import io.nuite.modules.sr_base.service.GoodsPeriodService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 分销平台订单Controller类
 * @author: jxj
 * @create: 2019-06-27 15:16
 */
@RestController
@RequestMapping("/online/order")
public class OLOrderController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OnlineOrderService onlineOrderService;

    @Autowired
    private OrderCartService orderCartService;

    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @Autowired
    private GoodsPeriodService goodsPeriodService;

    @Autowired
    private CustomerUserInfoService customerUserInfoService;

    @Autowired
    private OrderCartDistributeBoxService orderCartDistributeBoxService;

    @Autowired
    private OrderCartDetailService orderCartDetailService;

    @Autowired
    private GoodsColorService goodsColorService;

    /**
     * 登录用户
     */
    protected BaseUserEntity getUser() {
        return (BaseUserEntity) SecurityUtils.getSubject().getPrincipal();
    }

    @PostMapping("/selectOrder")
    @ApiOperation("获取定货方订单列表")
    public R selectOrder(@ApiParam("第几页") Integer page, @ApiParam("每页数量") Integer limit,
                         @ApiParam("年份") @RequestParam(required = false) Integer year,
                         @ApiParam("波次") @RequestParam(required = false) Integer periodSeq,
                         @ApiParam("订货方") @RequestParam(required = false) Integer customSeq) {
        try {
            Page<OrderEntity> orderPage = new Page<>(page,limit);
            Page result = onlineOrderService.selectOrder(orderPage,getUser().getCompanySeq(),year,periodSeq,customSeq);
            return R.ok().put("page",new PageUtils(result));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return R.error();
    }

    @GetMapping("/selectYear")
    @ApiOperation("获取波次年份")
    public R selectYear() {
        try {
            return R.ok().put("result",onlineOrderService.selectYear(getUser().getBrandSeq()));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return R.error();
    }

    @GetMapping("/selectPeriod/{year}")
    @ApiOperation("获取波次")
    public R selectPeriod(@ApiParam("年份") @PathVariable Integer year) {
        try {
            return R.ok().put("result",onlineOrderService.selectPeriod(getUser().getBrandSeq(),year));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return R.error();
    }

    @GetMapping("/selectCustom/{periodSeq}")
    @ApiOperation("获取定货方")
    public R selectCustom(@ApiParam("波次序号") @PathVariable Integer periodSeq) {
        try {
            return R.ok().put("result",onlineOrderService.selectCustom(getUser().getBrandSeq(),periodSeq));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return R.error();
    }

    @GetMapping("/selectOrderCart/{orderSeq}")
    @ApiOperation("获取订单详情")
    public R selectOrderCart(@ApiParam("订单序号") @PathVariable Integer orderSeq) {
        try {
            return R.ok().put("result",orderCartService.selectOrderCart(orderSeq));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return R.error();
    }

    @GetMapping("/selectOrderByGood/{periodSeq}")
    @ApiOperation("根据货号获取订单详情")
    public R selectOrderByGood(@ApiParam("波次序号") @PathVariable Integer periodSeq) {
        try {
            return R.ok().put("result",orderCartService.selectOrderByGood(getUser().getCompanySeq(),periodSeq));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return R.error();
    }

    @GetMapping("/selectOrderByFactory/{periodSeq}")
    @ApiOperation("根据生产厂家获取订单详情")
    public R selectOrderByFactory(@ApiParam("波次序号") @PathVariable Integer periodSeq) {
        try {
            return R.ok().put("result",orderCartService.selectOrderByFactory(getUser().getCompanySeq(),periodSeq));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return R.error();
    }

    @GetMapping("/selectCategory/{parentSeq}")
    @ApiOperation("获取类别列表")
    public R selectCategory(@ApiParam("父级序号") @PathVariable Integer parentSeq) {
        try {
            Wrapper<GoodsCategoryEntity> wrapper = new EntityWrapper<>();
            wrapper.eq("Company_Seq",getUser().getCompanySeq()).eq("ParentSeq",parentSeq);
            return R.ok().put("result",goodsCategoryService.selectList(wrapper));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return R.error();
    }

    @GetMapping("/selectOrderByCategory/{periodSeq}/{categorySeq}")
    @ApiOperation("根据类别获取订单详情")
    public R selectOrderByCategory(@ApiParam("波次序号") @PathVariable Integer periodSeq ,@ApiParam("类别序号") @PathVariable Integer categorySeq) {
        try {
            return R.ok().put("result",orderCartService.selectOrderByCategory(getUser().getCompanySeq(),periodSeq,categorySeq));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return R.error();
    }

    @PostMapping("/exportCustomExcel")
    @ApiOperation("导出定货方订单Excel")
    public void exportCustomExcel(@ApiParam("波次序号") @RequestParam Integer periodSeq,
                                         @ApiParam("订货方序号") @RequestParam(required = false) Integer customSeq, HttpServletResponse response) {

        try {
            List<OrderEntity> orderEntities = onlineOrderService.selectOrderDetail(getUser().getCompanySeq(),customSeq,periodSeq);

            if (orderEntities == null || orderEntities.size() == 0) {
                logger.warn("定货方统计订货单下载-查询内容为空");
                return;
            }
            //查询总的订单量
            Integer totalBuy = onlineOrderService.selectTotalOrderNum(getUser().getCompanySeq(),customSeq, periodSeq);
            BigDecimal totalMoney = new BigDecimal(0);
            // 获取订货会
            String filePrefix = orderEntities.get(0).getPeriodName() + "-";

            // 2.创建Excel
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(filePrefix + "定货方统计订货单.xlsx", "UTF-8"));

            // 创建excel2007工作簿
            XSSFWorkbook wb = new XSSFWorkbook();

            // 创建sheet页
            XSSFSheet sheet = wb.createSheet("sheet1");
            // 默认单元格宽度和高度
            sheet.setDefaultColumnWidth(8);
            sheet.setDefaultRowHeightInPoints(16);

            // 不同列的宽度
            sheet.setColumnWidth(0, 18 * 256);
            // sheet.setColumnWidth(1, 40 * 256);

            // 单元格样式1
            XSSFCellStyle style1 = wb.createCellStyle();
            style1.setAlignment(HorizontalAlignment.CENTER); // 水平居中
            style1.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
            style1.setBorderLeft(BorderStyle.THIN);
            style1.setLeftBorderColor(new XSSFColor(Color.black));
            style1.setBorderRight(BorderStyle.THIN);
            style1.setRightBorderColor(new XSSFColor(Color.black));
            style1.setBorderTop(BorderStyle.THIN);
            style1.setTopBorderColor(new XSSFColor(Color.black));
            style1.setBorderBottom(BorderStyle.THIN);
            style1.setBottomBorderColor(new XSSFColor(Color.black));

            // 单元格样式2
            XSSFCellStyle style2 = wb.createCellStyle();
            style2.setAlignment(HorizontalAlignment.CENTER); // 水平居中
            style2.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
            style2.setBorderLeft(BorderStyle.THIN);
            style2.setLeftBorderColor(new XSSFColor(Color.black));
            style2.setBorderRight(BorderStyle.THIN);
            style2.setRightBorderColor(new XSSFColor(Color.black));
            style2.setBorderTop(BorderStyle.THIN);
            style2.setTopBorderColor(new XSSFColor(Color.black));
            style2.setBorderBottom(BorderStyle.THIN);
            style2.setBottomBorderColor(new XSSFColor(Color.black));

            // 字体样式
            XSSFFont font = wb.createFont();
            font.setFontName("仿宋_GB2312");
            font.setBold(true); // 粗体
            font.setFontHeightInPoints((short) 12);

            style1.setFont(font); // 字体

            XSSFRow newRow;
            XSSFCell newCell;
            // 当前操作行,创建新行后+1
            int currRowIndex = 0;
            CellRangeAddress cra1 = new CellRangeAddress(currRowIndex, currRowIndex, 0, 12); // 合并单元格
            sheet.addMergedRegion(cra1);
            newRow = sheet.createRow(currRowIndex++);
            newRow.setHeightInPoints(24);
            newCell = newRow.createCell(0);
            newCell.setCellStyle(style1);
            for (OrderEntity orderEntity : orderEntities) {
                List<Map<String, Object>> orderGoods = orderCartService.selectOrderCart(orderEntity.getSeq());
                for (Map<String, Object> orderGood : orderGoods) {
                    BigDecimal totalPrice = new BigDecimal(orderGood.get("goodTotal").toString());
                    totalMoney = totalMoney.add(totalPrice);
                }
            }
            newCell.setCellValue("总订货量：" + totalBuy + "     总订货金额：" + totalMoney);

            int CountSeq=1;
            for (OrderEntity orderEntity : orderEntities) {
                List<Map<String, Object>> orderGoods = orderCartService.selectOrderCart(orderEntity.getSeq());

                // 创建订单标题
                CellRangeAddress cra = new CellRangeAddress(currRowIndex, currRowIndex, 0, 12); // 合并单元格
                sheet.addMergedRegion(cra);
                newRow = sheet.createRow(currRowIndex++);
                newRow.setHeightInPoints(30);

                // 标题内容
                StringBuilder sb = new StringBuilder().append("    ").append(orderEntity.getCustomUser())// 定货方
                        .append("    订单号：").append(orderEntity.getOrderNum()).append("    下单日期：")
                        .append(DateUtils.format(orderEntity.getInputTime(), DateUtils.DATE_TIME_PATTERN))
                        .append(" 收货地址:").append(orderEntity.getFullAddress()).append("  收货人："+orderEntity.getReceiverName()).append(" 收货电话："+orderEntity.getTelephone()).append("    订单量序号:").append(CountSeq++).append("  物流信息：").append(orderEntity.getExpressName())
                        .append("  快递单号:"+orderEntity.getExpressNo());
                if(orderEntity.getIsOem() == 1) {
                    sb.append("    有贴图LOGO");
                }else if(orderEntity.getIsOem() == 0) {
                    sb.append("    没有贴图LOGO");
                }
                newCell = newRow.createCell(0);
                newCell.setCellValue(sb.toString());
                int seq = 1;
                for (Map<String, Object> orderGood : orderGoods) {
                    // 每个货号尺码范围不同，需要创建1行标题行


                    // 数据解析
                    List<Integer> sizeTitles = (List<Integer>) orderGood.get("title");
                    Integer total = (Integer) orderGood.get("total");
                    String goodID = (String) orderGood.get("goodID");
                    BigDecimal price = new BigDecimal(orderGood.get("tagPrice").toString());
                    BigDecimal totalPrice = new BigDecimal(orderGood.get("goodTotal").toString());
                    List<Map<String, Object>> goodDetail = (List<Map<String, Object>>) orderGood.get("details");

                    // 标题列表
                    List<String> titles = new ArrayList<>();
                    titles.add("序号");
                    titles.add("货号");
                    titles.add("配件数");
                    titles.add("订货量");
                    titles.add("单价");
                    titles.add("总价");
                    titles.add("期望到货日期");
                    titles.add("备注");
                    titles.add("颜色");
                    titles.add("总数");
                    for (Integer sizeTitle : sizeTitles) {
                        titles.add(sizeTitle.toString());
                    }
                    //titles.add("建议");
                    // 创建标题行
                    newRow = sheet.createRow(currRowIndex++);
                    for (int k = 0; k < titles.size(); k++) {
                        newCell = newRow.createCell(k);
                        newCell.setCellStyle(style1);
                        String title = titles.get(k);
                        newCell.setCellValue(title);
                    }

                    // 循环创建数据行
                    for (int n = 0; n < goodDetail.size(); n++) {
                        Map<String, Object> detail = goodDetail.get(n);
                        Map<Integer, Integer> sizeMap = (Map<Integer, Integer>) detail.get("size");
                        newRow = sheet.createRow(currRowIndex++);
                        if (n == 0) {
                            newCell = newRow.createCell(0);
                            newCell.setCellStyle(style2);
                            newCell.setCellValue(seq++);

                            newCell = newRow.createCell(1);
                            newCell.setCellStyle(style2);
                            newCell.setCellValue(goodID);

                            newCell = newRow.createCell(2);
                            newCell.setCellStyle(style2);
                            if(orderEntity.getPerBoxNum() != null) {
                                newCell.setCellValue(orderEntity.getPerBoxNum());
                            }

                            newCell = newRow.createCell(3);
                            newCell.setCellStyle(style2);
                            newCell.setCellValue(total);
                            // 第四列 单价
                            newCell = newRow.createCell(4);
                            newCell.setCellStyle(style2);
                            newCell.setCellValue(price.toString());
                            // 第五列 总价
                            newCell = newRow.createCell(5);
                            newCell.setCellStyle(style2);
                            newCell.setCellValue(totalPrice.toString());
                            // 第六列 单价
                            newCell = newRow.createCell(6);
                            newCell.setCellStyle(style2);
                            newCell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(orderEntity.getExpectedTime()));
                            // 第七列 总价
                            newCell = newRow.createCell(7);
                            newCell.setCellStyle(style2);
                            newCell.setCellValue(orderEntity.getRemark());
                        }

                        // 第八列 颜色
                        newCell = newRow.createCell(8);
                        newCell.setCellStyle(style2);
                        newCell.setCellValue(detail.get("color").toString());
                        // 第九列 颜色-尺码 订货量总数
                        newCell = newRow.createCell(9);
                        newCell.setCellStyle(style2);
                        newCell.setCellValue(detail.get("colorTotal").toString());
                        // 第十列开始 尺码列
                        int currCellIndex = 10;
                        for (Integer sizeTitle : sizeTitles) {
                            Integer sizeCount = sizeMap.get(sizeTitle + "");
                            newCell = newRow.createCell(currCellIndex);
                            newCell.setCellStyle(style2);
                            if (sizeCount != null) {
                                newCell.setCellValue(sizeCount);
                            }
                            currCellIndex++;
                        }
                        /*if (n == 0) {
                            // 最后1列 所有建议
                            newCell = newRow.createCell(currCellIndex);
                            newCell.setCellStyle(style2);
                            newCell.setCellValue(detail.get("valuate").toString());
                        }*/

                    }

                }

                // 每个订单空两行
                currRowIndex += 2;

            } // 订单 for end

            // 获取输出流，写入excel并关闭
            ServletOutputStream out = response.getOutputStream();
            wb.write(out);
            out.flush();
            out.close();
            wb.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error("定货方订货单: 文件名编码异常");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("定货方订货单: " + e.getMessage(), e);
        }
    }

    @PostMapping("exportGoodsExcel")
    @ApiOperation(value = "下载订货单-按货号统计", hidden = true)
    public void exportGoodsExcel(@ApiParam(value = "波次序号", required = true) @RequestParam Integer periodSeq,
                                   HttpServletResponse response) {

        try {

            List<Map<String, Object>> goodsList = orderCartService.selectOrderByGood(getUser().getCompanySeq(), periodSeq);

            if (goodsList == null || goodsList.size() == 0) {
                logger.warn("货号统计订货单下载-查询内容为空");
                return;
            }
            //查询总的订单量
            Integer totalBuy = onlineOrderService.selectTotalOrderNum(getUser().getCompanySeq(),null,periodSeq);
            BigDecimal totalMoney = new BigDecimal(0);
            GoodsPeriodEntity periodEntity = goodsPeriodService.selectById(periodSeq);

            // 文件名前缀
            String filePrefix = "";
            if (periodEntity != null) {
                filePrefix += periodEntity.getName() + "-";
            }

            // 2.创建Excel
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(filePrefix + "货品统计订货单.xlsx", "UTF-8"));

            // 创建excel2007工作簿
            XSSFWorkbook wb = new XSSFWorkbook();

            // 创建sheet页
            XSSFSheet sheet = wb.createSheet("sheet1");
            // 默认单元格宽度和高度
            sheet.setDefaultColumnWidth(8);
            sheet.setDefaultRowHeightInPoints(16);

            // 不同列的宽度
            sheet.setColumnWidth(0, 18 * 256);
            // sheet.setColumnWidth(1, 40 * 256);

            // 单元格样式1
            XSSFCellStyle style1 = wb.createCellStyle();
            style1.setAlignment(HorizontalAlignment.CENTER); // 水平居中
            style1.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
            style1.setBorderLeft(BorderStyle.THIN);
            style1.setLeftBorderColor(new XSSFColor(Color.black));
            style1.setBorderRight(BorderStyle.THIN);
            style1.setRightBorderColor(new XSSFColor(Color.black));
            style1.setBorderTop(BorderStyle.THIN);
            style1.setTopBorderColor(new XSSFColor(Color.black));
            style1.setBorderBottom(BorderStyle.THIN);
            style1.setBottomBorderColor(new XSSFColor(Color.black));

            // 单元格样式2
            XSSFCellStyle style2 = wb.createCellStyle();
            style2.setAlignment(HorizontalAlignment.CENTER); // 水平居中
            style2.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
            style2.setBorderLeft(BorderStyle.THIN);
            style2.setLeftBorderColor(new XSSFColor(Color.black));
            style2.setBorderRight(BorderStyle.THIN);
            style2.setRightBorderColor(new XSSFColor(Color.black));
            style2.setBorderTop(BorderStyle.THIN);
            style2.setTopBorderColor(new XSSFColor(Color.black));
            style2.setBorderBottom(BorderStyle.THIN);
            style2.setBottomBorderColor(new XSSFColor(Color.black));

            // 字体样式
            XSSFFont font = wb.createFont();
            font.setFontName("仿宋_GB2312");
            font.setBold(true); // 粗体
            font.setFontHeightInPoints((short) 12);

            style1.setFont(font); // 字体

			/*
			 * 数据结构 [ { goodID: 'A1111', details: [ {color: '米白', size: {34: 12, 35: 13, 36:
			 * 14}}, {color: '黑色', size: {34: 18, 35: 17, 36: 14}} ], title: [34, 35, 36],
			 * imgSrc:'http://', total:101 } ]
			 */

            XSSFRow newRow;
            XSSFCell newCell;
            // 当前操作行,创建新行后+1
            int currRowIndex = 0;
            CellRangeAddress cra1 = new CellRangeAddress(currRowIndex, currRowIndex, 0, 12); // 合并单元格
            sheet.addMergedRegion(cra1);
            newRow = sheet.createRow(currRowIndex++);
            newRow.setHeightInPoints(24);
            newCell = newRow.createCell(0);
            newCell.setCellStyle(style1);

            for (Map<String, Object> orderGood : goodsList) {
                BigDecimal totalPrice = new BigDecimal(orderGood.get("goodTotal").toString());
                totalMoney = totalMoney.add(totalPrice);
            }
            newCell.setCellValue("总订货量："+totalBuy + "    总订货金额：" + totalMoney);

            int seq = 1;
            for (Map<String, Object> orderGood : goodsList) {
                // 每个货号尺码范围不同，需要创建1行标题行
                // 数据解析
                List<Integer> sizeTitles = (List<Integer>) orderGood.get("title");
                Integer total = (Integer) orderGood.get("total");
                String goodID = (String) orderGood.get("goodID");
                BigDecimal price = new BigDecimal(orderGood.get("tagPrice").toString());
                BigDecimal totalPrice = new BigDecimal(orderGood.get("goodTotal").toString());
                List<Map<String, Object>> goodDetail = (List<Map<String, Object>>) orderGood.get("details");

                // 标题列表
                List<String> titles = new ArrayList<>();
                titles.add("序号");
                titles.add("货号");
                titles.add("订货量");
                titles.add("单价");
                titles.add("总价");
                titles.add("颜色");
                titles.add("总数");
                for (Integer sizeTitle : sizeTitles) {
                    titles.add(sizeTitle.toString());
                }
                //titles.add("建议");
                // 创建标题行
                newRow = sheet.createRow(currRowIndex++);
                for (int k = 0; k < titles.size(); k++) {
                    newCell = newRow.createCell(k);
                    newCell.setCellStyle(style1);
                    String title = titles.get(k);
                    newCell.setCellValue(title);
                }

                // 循环创建数据行
                for (int n = 0; n < goodDetail.size(); n++) {
                    Map<String, Object> detail = goodDetail.get(n);
                    Map<Integer, Integer> sizeMap = (Map<Integer, Integer>) detail.get("size");
                    newRow = sheet.createRow(currRowIndex++);
                    if (n == 0) {
                        newCell = newRow.createCell(0);
                        newCell.setCellStyle(style2);
                        newCell.setCellValue(seq++);

                        newCell = newRow.createCell(1);
                        newCell.setCellStyle(style2);
                        newCell.setCellValue(goodID);

                        newCell = newRow.createCell(2);
                        newCell.setCellStyle(style2);
                        newCell.setCellValue(total);

                        // 第三列 单价
                        newCell = newRow.createCell(3);
                        newCell.setCellStyle(style2);
                        newCell.setCellValue(price.toString());
                        // 第四列 总价
                        newCell = newRow.createCell(4);
                        newCell.setCellStyle(style2);
                        newCell.setCellValue(totalPrice.toString());
                    }
                    // 第五列 颜色
                    newCell = newRow.createCell(5);
                    newCell.setCellStyle(style2);
                    newCell.setCellValue(detail.get("color").toString());
                    // 第六列 颜色-尺码 订货量总数
                    newCell = newRow.createCell(6);
                    newCell.setCellStyle(style2);
                    newCell.setCellValue(detail.get("colorTotal").toString());
                    // 第四列开始 尺码列
                    int currCellIndex = 7;
                    for (Integer sizeTitle : sizeTitles) {
                        Integer sizeCount = sizeMap.get(sizeTitle + "");
                        newCell = newRow.createCell(currCellIndex);
                        newCell.setCellStyle(style2);
                        if (sizeCount != null) {
                            newCell.setCellValue(sizeCount);
                        }
                        currCellIndex++;
                    }
                    /*if (n == 0) {
                        // 最后1列 所有建议
                        newCell = newRow.createCell(currCellIndex);
                        newCell.setCellStyle(style2);
                        newCell.setCellValue(detail.get("valuate").toString());
                    }*/

                }

                // 每个货号之间空一行
                currRowIndex++;
            }
            // 获取输出流，写入excel并关闭
            ServletOutputStream out = response.getOutputStream();
            wb.write(out);
            out.flush();
            out.close();
            wb.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error("货号统计订货单: 文件名编码异常");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("货号统计订货单: " + e.getMessage(), e);
        }
    }

    @PostMapping("exportFactoryExcel")
    @ApiOperation(value = "下载订货单-按生产厂家统计", hidden = true)
    public void exportFactoryExcel(@ApiParam(value = "订货会序号", required = true) Integer periodSeq,
                                  HttpServletResponse response) {

        try {

            List<Map<String, Object>> factoryOrderProducts = orderCartService.selectOrderByFactory(getUser().getCompanySeq(),periodSeq);
            if (factoryOrderProducts == null || factoryOrderProducts.size() == 0) {
                logger.warn("生产厂家统计订货单下载-查询内容为空");
                return;
            }
            //查询总的订单量
            Integer totalBuy = onlineOrderService.selectTotalOrderNum(getUser().getCompanySeq(),null,periodSeq);
            BigDecimal totalMoney = new BigDecimal(0);
            // 获取订货会
            GoodsPeriodEntity goodsPeriodEntity = goodsPeriodService.selectById(periodSeq);
            String filePrefix = "";
            if (goodsPeriodEntity != null) {
                filePrefix += goodsPeriodEntity.getName() + "-";
            }

            // 2.创建Excel
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(filePrefix + "生产厂家统计订货单.xlsx", "UTF-8"));

            // 创建excel2007工作簿
            XSSFWorkbook wb = new XSSFWorkbook();

            // 创建sheet页
            XSSFSheet sheet = wb.createSheet("sheet1");
            // 默认单元格宽度和高度
            sheet.setDefaultColumnWidth(17);
            sheet.setDefaultRowHeightInPoints(16);

            // 默认单元格宽度和高度
            sheet.setDefaultColumnWidth(8);
            sheet.setDefaultRowHeightInPoints(16);

            // 不同列的宽度
            sheet.setColumnWidth(0, 18 * 256);
            // sheet.setColumnWidth(1, 40 * 256);

            // 单元格样式1
            XSSFCellStyle style1 = wb.createCellStyle();
            style1.setAlignment(HorizontalAlignment.CENTER); // 水平居中
            style1.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
            style1.setBorderLeft(BorderStyle.THIN);
            style1.setLeftBorderColor(new XSSFColor(Color.black));
            style1.setBorderRight(BorderStyle.THIN);
            style1.setRightBorderColor(new XSSFColor(Color.black));
            style1.setBorderTop(BorderStyle.THIN);
            style1.setTopBorderColor(new XSSFColor(Color.black));
            style1.setBorderBottom(BorderStyle.THIN);
            style1.setBottomBorderColor(new XSSFColor(Color.black));

            // 单元格样式2
            XSSFCellStyle style2 = wb.createCellStyle();
            style2.setAlignment(HorizontalAlignment.CENTER); // 水平居中
            style2.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
            style2.setBorderLeft(BorderStyle.THIN);
            style2.setLeftBorderColor(new XSSFColor(Color.black));
            style2.setBorderRight(BorderStyle.THIN);
            style2.setRightBorderColor(new XSSFColor(Color.black));
            style2.setBorderTop(BorderStyle.THIN);
            style2.setTopBorderColor(new XSSFColor(Color.black));
            style2.setBorderBottom(BorderStyle.THIN);
            style2.setBottomBorderColor(new XSSFColor(Color.black));

            // 字体样式
            XSSFFont font = wb.createFont();
            font.setFontName("仿宋_GB2312");
            font.setBold(true); // 粗体
            font.setFontHeightInPoints((short) 12);

            style1.setFont(font); // 字体
            XSSFRow newRow;
            XSSFCell newCell;
            // 当前操作行,创建新行后+1
            int currRowIndex = 0;
            CellRangeAddress cra1 = new CellRangeAddress(currRowIndex, currRowIndex, 0, 12); // 合并单元格
            sheet.addMergedRegion(cra1);
            newRow = sheet.createRow(currRowIndex++);
            newRow.setHeightInPoints(24);
            newCell = newRow.createCell(0);
            newCell.setCellStyle(style1);

            for (Map<String, Object> factoryOrderProduct : factoryOrderProducts) {
                List<Map<String, Object>> factoryDetail = (List<Map<String, Object>>) factoryOrderProduct.get("factoryDetail");
                for (int i = 0; i < factoryDetail.size(); i++) {
                    Map<String, Object> goodsMap = factoryDetail.get(i);
                    BigDecimal totalPrice = new BigDecimal(goodsMap.get("goodTotal").toString());
                    totalMoney = totalMoney.add(totalPrice);
                }
            }
            newCell.setCellValue("总订货量："+totalBuy + "    总订货金额：" + totalMoney);

            for (Map<String, Object> factoryOrderProduct : factoryOrderProducts) {

                // 数据解析
                String factoryName = (String) factoryOrderProduct.get("factoryName");
                Integer factoryTotal = (Integer) factoryOrderProduct.get("factoryTotal");
                List<Map<String, Object>> factoryDetail = (List<Map<String, Object>>) factoryOrderProduct.get("factoryDetail");
                int seq = 1;
                for (int i = 0; i < factoryDetail.size(); i++) {
                    Map<String, Object> goodsMap = factoryDetail.get(i);
                    // 数据解析
                    List<Integer> sizeTitles = (List<Integer>) goodsMap.get("title");
                    Integer total = (Integer) goodsMap.get("total");
                    String goodID = (String) goodsMap.get("goodID");
                    BigDecimal price = new BigDecimal(goodsMap.get("tagPrice").toString());
                    BigDecimal totalPrice = new BigDecimal(goodsMap.get("goodTotal").toString());
                    List<Map<String, Object>> goodDetail = (List<Map<String, Object>>) goodsMap.get("details");

                    // 标题列表
                    List<String> titles = new ArrayList<>();
                    titles.add("生产厂家");
                    titles.add("订货量");
                    titles.add("订货量排名");
                    titles.add("货号");
                    titles.add("订货量");
                    titles.add("单价");
                    titles.add("总价");
                    titles.add("颜色");
                    titles.add("总数");
                    for (Integer sizeTitle : sizeTitles) {
                        titles.add(sizeTitle.toString());
                    }

                    // 创建标题行
                    // 相同区域下的第一个货号，显示区域和订货量
                    newRow = sheet.createRow(currRowIndex++);
                    if (i == 0) {
                        for (int k = 0; k < titles.size(); k++) {
                            newCell = newRow.createCell(k);
                            newCell.setCellStyle(style1);
                            String title = titles.get(k);
                            newCell.setCellValue(title);
                        }
                    } else {
                        for (int k = 2; k < titles.size(); k++) {
                            newCell = newRow.createCell(k);
                            newCell.setCellStyle(style1);
                            String title = titles.get(k);
                            newCell.setCellValue(title);
                        }
                    }

                    // 循环创建数据行
                    for (int n = 0; n < goodDetail.size(); n++) {
                        Map<String, Object> detail = goodDetail.get(n);
                        Map<Integer, Integer> sizeMap = (Map<Integer, Integer>) detail.get("size");
                        newRow = sheet.createRow(currRowIndex++);
                        if (n == 0) {
                            // 区域下的第一个货号
                            if (i == 0) {
                                newCell = newRow.createCell(0);
                                newCell.setCellStyle(style2);
                                newCell.setCellValue(factoryName);

                                newCell = newRow.createCell(1);
                                newCell.setCellStyle(style2);
                                newCell.setCellValue(factoryTotal);
                            }

                            newCell = newRow.createCell(2);
                            newCell.setCellStyle(style2);
                            newCell.setCellValue(seq++);

                            newCell = newRow.createCell(3);
                            newCell.setCellStyle(style2);
                            newCell.setCellValue(goodID);

                            newCell = newRow.createCell(4);
                            newCell.setCellStyle(style2);
                            newCell.setCellValue(total);

                            // 第三列 单价
                            newCell = newRow.createCell(5);
                            newCell.setCellStyle(style2);
                            newCell.setCellValue(price.toString());
                            // 第四列 总价
                            newCell = newRow.createCell(6);
                            newCell.setCellStyle(style2);
                            newCell.setCellValue(totalPrice.toString());
                        }
                        // 第五列 颜色
                        newCell = newRow.createCell(7);
                        newCell.setCellStyle(style2);
                        newCell.setCellValue(detail.get("color").toString());
                        // 第六列 颜色-尺码 订货量总数
                        newCell = newRow.createCell(8);
                        newCell.setCellStyle(style2);
                        newCell.setCellValue(detail.get("colorTotal").toString());

                        // 第7列开始 尺码列
                        int currCellIndex = 9;
                        for (Integer sizeTitle : sizeTitles) {
                            Integer sizeCount = sizeMap.get(sizeTitle + "");
                            newCell = newRow.createCell(currCellIndex);
                            newCell.setCellStyle(style2);
                            if (sizeCount != null) {
                                newCell.setCellValue(sizeCount);
                            }
                            currCellIndex++;
                        }
                    }

                }
                // 区域之间空一行
                currRowIndex++;
            }
            // 获取输出流，写入excel并关闭
            ServletOutputStream out = response.getOutputStream();
            wb.write(out);
            out.flush();
            out.close();
            wb.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error("生产厂家统计订货单: 文件名编码异常");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("生产厂家统计订货单: " + e.getMessage(), e);
        }
    }



    @PostMapping("exportCategoryExcel")
    @ApiOperation(value = "下载订货单-按类别统计", hidden = true)
    public void exportCategoryExcel(@ApiParam(value = "订货会序号", required = true) Integer periodSeq,
            @ApiParam(value = "订货会序号", required = true) Integer firstSeq,
            @ApiParam(value = "订货会序号", required = true) Integer secondSeq,
            @ApiParam(value = "订货会序号", required = true) Integer thirdSeq,
                                  HttpServletResponse response) {

        try {
            GoodsCategoryEntity firstCategory = goodsCategoryService.selectById(firstSeq);
            GoodsCategoryEntity secondCategory = goodsCategoryService.selectById(secondSeq);
            List<Map<String, Object>> categoryOrderProducts = orderCartService.selectOrderByCategory(getUser().getCompanySeq(),periodSeq,thirdSeq);
            if (categoryOrderProducts == null || categoryOrderProducts.size() == 0) {
                logger.warn("类别统计订货单下载-查询内容为空");
                return;
            }
            //查询总的订单量
            Integer totalBuy = onlineOrderService.selectTotalOrderNum(getUser().getCompanySeq(),null,periodSeq);
            BigDecimal totalMoney = new BigDecimal(0);
            // 获取订货会
            GoodsPeriodEntity goodsPeriodEntity = goodsPeriodService.selectById(periodSeq);
            String filePrefix = "";
            if (goodsPeriodEntity != null) {
                filePrefix += goodsPeriodEntity.getName() + "-";
            }

            // 2.创建Excel
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(filePrefix + "类别统计订货单.xlsx", "UTF-8"));

            // 创建excel2007工作簿
            XSSFWorkbook wb = new XSSFWorkbook();

            // 创建sheet页
            XSSFSheet sheet = wb.createSheet("sheet1");
            // 默认单元格宽度和高度
            sheet.setDefaultColumnWidth(17);
            sheet.setDefaultRowHeightInPoints(16);

            // 默认单元格宽度和高度
            sheet.setDefaultColumnWidth(8);
            sheet.setDefaultRowHeightInPoints(16);

            // 不同列的宽度
            sheet.setColumnWidth(0, 18 * 256);
            // sheet.setColumnWidth(1, 40 * 256);

            // 单元格样式1
            XSSFCellStyle style1 = wb.createCellStyle();
            style1.setAlignment(HorizontalAlignment.CENTER); // 水平居中
            style1.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
            style1.setBorderLeft(BorderStyle.THIN);
            style1.setLeftBorderColor(new XSSFColor(Color.black));
            style1.setBorderRight(BorderStyle.THIN);
            style1.setRightBorderColor(new XSSFColor(Color.black));
            style1.setBorderTop(BorderStyle.THIN);
            style1.setTopBorderColor(new XSSFColor(Color.black));
            style1.setBorderBottom(BorderStyle.THIN);
            style1.setBottomBorderColor(new XSSFColor(Color.black));

            // 单元格样式2
            XSSFCellStyle style2 = wb.createCellStyle();
            style2.setAlignment(HorizontalAlignment.CENTER); // 水平居中
            style2.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
            style2.setBorderLeft(BorderStyle.THIN);
            style2.setLeftBorderColor(new XSSFColor(Color.black));
            style2.setBorderRight(BorderStyle.THIN);
            style2.setRightBorderColor(new XSSFColor(Color.black));
            style2.setBorderTop(BorderStyle.THIN);
            style2.setTopBorderColor(new XSSFColor(Color.black));
            style2.setBorderBottom(BorderStyle.THIN);
            style2.setBottomBorderColor(new XSSFColor(Color.black));

            // 字体样式
            XSSFFont font = wb.createFont();
            font.setFontName("仿宋_GB2312");
            font.setBold(true); // 粗体
            font.setFontHeightInPoints((short) 12);

            style1.setFont(font); // 字体
            XSSFRow newRow;
            XSSFCell newCell;
            // 当前操作行,创建新行后+1
            int currRowIndex = 0;
            CellRangeAddress cra1 = new CellRangeAddress(currRowIndex, currRowIndex, 0, 12); // 合并单元格
            sheet.addMergedRegion(cra1);
            newRow = sheet.createRow(currRowIndex++);
            newRow.setHeightInPoints(24);
            newCell = newRow.createCell(0);
            newCell.setCellStyle(style1);
            for (Map<String, Object> categoryOrderProduct : categoryOrderProducts) {
                List<Map<String, Object>> categoryDetail = (List<Map<String, Object>>) categoryOrderProduct.get("categoryDetail");
                for (int i = 0; i < categoryDetail.size(); i++) {
                    Map<String, Object> goodsMap = categoryDetail.get(i);
                    BigDecimal totalPrice = new BigDecimal(goodsMap.get("goodTotal").toString());
                    totalMoney = totalMoney.add(totalPrice);
                }
            }
            newCell.setCellValue("总订货量："+totalBuy+ "    总订货金额：" + totalMoney+"           大类：" + firstCategory.getName() + "           小类：" + secondCategory.getName());

            for (Map<String, Object> categoryOrderProduct : categoryOrderProducts) {

                // 数据解析
                String categoryName = (String) categoryOrderProduct.get("categoryName");
                Integer categoryTotal = (Integer) categoryOrderProduct.get("categoryTotal");
                List<Map<String, Object>> categoryDetail = (List<Map<String, Object>>) categoryOrderProduct.get("categoryDetail");
                int seq = 1;
                for (int i = 0; i < categoryDetail.size(); i++) {
                    Map<String, Object> goodsMap = categoryDetail.get(i);
                    // 数据解析
                    List<Integer> sizeTitles = (List<Integer>) goodsMap.get("title");
                    Integer total = (Integer) goodsMap.get("total");
                    String goodID = (String) goodsMap.get("goodID");
                    BigDecimal price = new BigDecimal(goodsMap.get("tagPrice").toString());
                    BigDecimal totalPrice = new BigDecimal(goodsMap.get("goodTotal").toString());
                    List<Map<String, Object>> goodDetail = (List<Map<String, Object>>) goodsMap.get("details");

                    // 标题列表
                    List<String> titles = new ArrayList<>();
                    titles.add("类别");
                    titles.add("订货量");
                    titles.add("订货量排名");
                    titles.add("货号");
                    titles.add("订货量");
                    titles.add("单价");
                    titles.add("总价");
                    titles.add("颜色");
                    titles.add("总数");
                    for (Integer sizeTitle : sizeTitles) {
                        titles.add(sizeTitle.toString());
                    }

                    // 创建标题行
                    // 相同区域下的第一个货号，显示区域和订货量
                    newRow = sheet.createRow(currRowIndex++);
                    if (i == 0) {
                        for (int k = 0; k < titles.size(); k++) {
                            newCell = newRow.createCell(k);
                            newCell.setCellStyle(style1);
                            String title = titles.get(k);
                            newCell.setCellValue(title);
                        }
                    } else {
                        for (int k = 2; k < titles.size(); k++) {
                            newCell = newRow.createCell(k);
                            newCell.setCellStyle(style1);
                            String title = titles.get(k);
                            newCell.setCellValue(title);
                        }
                    }

                    // 循环创建数据行
                    for (int n = 0; n < goodDetail.size(); n++) {
                        Map<String, Object> detail = goodDetail.get(n);
                        Map<Integer, Integer> sizeMap = (Map<Integer, Integer>) detail.get("size");
                        newRow = sheet.createRow(currRowIndex++);
                        if (n == 0) {
                            // 区域下的第一个货号
                            if (i == 0) {
                                newCell = newRow.createCell(0);
                                newCell.setCellStyle(style2);
                                newCell.setCellValue(categoryName);

                                newCell = newRow.createCell(1);
                                newCell.setCellStyle(style2);
                                newCell.setCellValue(categoryTotal);
                            }

                            newCell = newRow.createCell(2);
                            newCell.setCellStyle(style2);
                            newCell.setCellValue(seq++);

                            newCell = newRow.createCell(3);
                            newCell.setCellStyle(style2);
                            newCell.setCellValue(goodID);

                            newCell = newRow.createCell(4);
                            newCell.setCellStyle(style2);
                            newCell.setCellValue(total);
                            // 第三列 单价
                            newCell = newRow.createCell(5);
                            newCell.setCellStyle(style2);
                            newCell.setCellValue(price.toString());
                            // 第四列 总价
                            newCell = newRow.createCell(6);
                            newCell.setCellStyle(style2);
                            newCell.setCellValue(totalPrice.toString());
                        }
                        // 第五列 颜色
                        newCell = newRow.createCell(7);
                        newCell.setCellStyle(style2);
                        newCell.setCellValue(detail.get("color").toString());
                        // 第六列 颜色-尺码 订货量总数
                        newCell = newRow.createCell(8);
                        newCell.setCellStyle(style2);
                        newCell.setCellValue(detail.get("colorTotal").toString());

                        // 第7列开始 尺码列
                        int currCellIndex = 7;
                        for (Integer sizeTitle : sizeTitles) {
                            Integer sizeCount = sizeMap.get(sizeTitle + "");
                            newCell = newRow.createCell(currCellIndex);
                            newCell.setCellStyle(style2);
                            if (sizeCount != null) {
                                newCell.setCellValue(sizeCount);
                            }
                            currCellIndex++;
                        }
                    }

                }
                // 区域之间空一行
                currRowIndex++;
            }
            // 获取输出流，写入excel并关闭
            ServletOutputStream out = response.getOutputStream();
            wb.write(out);
            out.flush();
            out.close();
            wb.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error("类别统计订货单: 文件名编码异常");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("类别统计订货单: " + e.getMessage(), e);
        }
    }

    @PostMapping("exportDistributeBoxExcel")
    public void exportDistributeBoxExcel(@ApiParam(value = "波次序号", required = true) Integer periodSeq,
                                        HttpServletResponse response) {
        try {
            // 根据订货会序号查询购物车信息
            List<OrderCartEntity> cartEntities = orderCartService.selectOrderCartByPeriod(periodSeq);
            if (cartEntities == null || cartEntities.size() == 0) {
                logger.warn("配码单下载-查询内容为空");
                return;
            }
            // 获取订货会
            GoodsPeriodEntity goodsPeriodEntity = goodsPeriodService.selectById(periodSeq);
            //获取本次订货会最小尺码和最大尺码
            Integer minSize = Integer.parseInt(orderCartService.selectMinSizeByPeriod(periodSeq));

            Integer maxSize = Integer.parseInt(orderCartService.selectMaxSizeByPeriod(periodSeq));


            String filePrefix = "";
            if (goodsPeriodEntity != null) {
                filePrefix += goodsPeriodEntity.getName() + "-";
            }
            List<OrderEntity> orderEntities = onlineOrderService.selectOrderAllColumn(getUser().getCompanySeq(),periodSeq);
            // 2.创建Excel
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(filePrefix + "配码单.xlsx", "UTF-8"));
            // 创建excel2007工作簿
            XSSFWorkbook wb = new XSSFWorkbook();
            // 单元格样式2
            XSSFCellStyle style2 = wb.createCellStyle();
            style2.setAlignment(HorizontalAlignment.CENTER); // 水平居中
            style2.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
            style2.setBorderLeft(BorderStyle.THIN);
            style2.setLeftBorderColor(new XSSFColor(Color.black));
            style2.setBorderRight(BorderStyle.THIN);
            style2.setRightBorderColor(new XSSFColor(Color.black));
            style2.setBorderTop(BorderStyle.THIN);
            style2.setTopBorderColor(new XSSFColor(Color.black));
            style2.setBorderBottom(BorderStyle.THIN);
            style2.setBottomBorderColor(new XSSFColor(Color.black));

            // 字体样式
            XSSFFont font = wb.createFont();
            font.setFontName("仿宋_GB2312");
            font.setBold(true); // 粗体
            font.setFontHeightInPoints((short) 12);


            //按客户生成创建sheet页
            // 创建sheet页
            XSSFSheet sheet1 = wb.createSheet("按客户");
            // 默认单元格宽度和高度
            sheet1.setDefaultColumnWidth(17);
            sheet1.setDefaultRowHeightInPoints(16);

            // 默认单元格宽度和高度
            sheet1.setDefaultColumnWidth(8);
            sheet1.setDefaultRowHeightInPoints(16);

            // 不同列的宽度
            sheet1.setColumnWidth(0, 18 * 256);
            // sheet.setColumnWidth(1, 40 * 256);
            // 当前操作行,创建新行后+1
            int currRowIndex = 0;
            XSSFRow newRow;
            XSSFCell newCell;
            newRow = sheet1.createRow(currRowIndex++);

            newCell = newRow.createCell(0);
            newCell.setCellStyle(style2);
            newCell.setCellValue("序号");

            newCell = newRow.createCell(1);
            newCell.setCellStyle(style2);
            newCell.setCellValue("客户");

            newCell = newRow.createCell(2);
            newCell.setCellStyle(style2);
            newCell.setCellValue("型号");

            newCell = newRow.createCell(3);
            newCell.setCellStyle(style2);
            newCell.setCellValue("客户型号");

            newCell = newRow.createCell(4);
            newCell.setCellStyle(style2);
            newCell.setCellValue("颜色");

            newCell = newRow.createCell(5);
            newCell.setCellStyle(style2);
            newCell.setCellValue("双数");



            for (int i = 0; i <= (maxSize - minSize); i++) {
                newCell = newRow.createCell(i+6);
                newCell.setCellStyle(style2);
                newCell.setCellValue((minSize+i)+"#");
            }

            newCell = newRow.createCell(maxSize-minSize+6);
            newCell.setCellStyle(style2);
            //newCell.setCellValue("建议");


            int seq=0;
            for (OrderEntity orderEntity : orderEntities) {
                Wrapper<OrderCartEntity> orderCartWrapper = new EntityWrapper<>();
                orderCartWrapper.where("OrderSeq = {0}",orderEntity.getSeq());
                List<OrderCartEntity> orderCartEntities = orderCartService.selectList(orderCartWrapper);
                CustomerUserInfo customerUserInfo = customerUserInfoService.selectById(orderEntity.getUserSeq());
                for (OrderCartEntity orderCartEntity : orderCartEntities) {
                    //客户型号
                    String userGoodID = orderCartEntity.getUserGoodID();
                    Integer orderCartSeq = orderCartEntity.getSeq();
                    Wrapper<OrderCartDistributeBoxEntity> orderCartDistributeBoxWrapper = new EntityWrapper<>();
                    orderCartDistributeBoxWrapper.where("OrderShoppingCart_Seq = {0}",orderCartSeq);
                    List<OrderCartDistributeBoxEntity> orderCartDistributeBoxEntities = orderCartDistributeBoxService.selectList(orderCartDistributeBoxWrapper);
                    for (OrderCartDistributeBoxEntity orderCartDistributeBoxEntity : orderCartDistributeBoxEntities) {
                        Integer orderCartDistributeBoxSeq = orderCartDistributeBoxEntity.getSeq();
                        Wrapper<OrderCartDetailEntity> orderCartDetailWrapper = new EntityWrapper<>();
                        orderCartDetailWrapper.where("OrderShoppingCartDistributeBox_Seq = {0}",orderCartDistributeBoxSeq);
                        List<OrderCartDetailEntity> orderCartDetailEntities = orderCartDetailService.selectList(orderCartDetailWrapper);
                        //颜色seq
                        Integer colorSeq =orderCartDistributeBoxEntity.getColorSeq();
                        GoodsColorEntity goodsColorEntity = goodsColorService.selectById(colorSeq);
                        newRow = sheet1.createRow(currRowIndex++);
                        seq++;
                        newCell = newRow.createCell(0);
                        newCell.setCellStyle(style2);
                        newCell.setCellValue(seq);

                        newCell = newRow.createCell(1);
                        newCell.setCellStyle(style2);
                        newCell.setCellValue(customerUserInfo.getNickName());

                        newCell = newRow.createCell(2);
                        newCell.setCellStyle(style2);
                        newCell.setCellValue(orderCartEntity.getGoodsID());

                        newCell = newRow.createCell(3);
                        newCell.setCellStyle(style2);
                        newCell.setCellValue(userGoodID);

                        newCell = newRow.createCell(4);
                        newCell.setCellStyle(style2);
                        newCell.setCellValue(goodsColorEntity.getName());



                        newCell = newRow.createCell(5);
                        newCell.setCellStyle(style2);
                        newCell.setCellValue(orderCartDistributeBoxEntity.getColorTotalNum());




                        Map<String, Integer>  distributeBox = new HashMap<String, Integer>();
                        for (OrderCartDetailEntity orderCartDetailEntity : orderCartDetailEntities) {
                            String sizeValue = orderCartDetailService.selectSize(orderCartDetailEntity.getSeq());
                            Integer size = 0;
                            if(sizeValue != null) {
                                size = Integer.parseInt(sizeValue);//尺码
                            }

                            Integer SelectNum = orderCartDetailEntity.getSelectNum();//选款数量
                            distributeBox.put(size.toString(), SelectNum);
                        }
                        for (int j = 0; j <= maxSize-minSize; j++) {
                            newCell = newRow.createCell(j+6);
                            newCell.setCellStyle(style2);
                            Integer key=minSize+j;
                            Integer selectNum =	distributeBox.get(key.toString());
                            if(selectNum!=null) {
                                newCell.setCellValue(selectNum);
                            }else {
                                newCell.setCellValue("");
                            }

                        }

                        newCell = newRow.createCell(maxSize-minSize+6);
                        newCell.setCellStyle(style2);

                    }

                }

            }



            //按型号生成创建sheet页
            // 创建sheet页
            XSSFSheet sheet2 = wb.createSheet("按型号");
            // 默认单元格宽度和高度
            sheet2.setDefaultColumnWidth(17);
            sheet2.setDefaultRowHeightInPoints(16);

            // 默认单元格宽度和高度
            sheet2.setDefaultColumnWidth(8);
            sheet2.setDefaultRowHeightInPoints(16);

            // 不同列的宽度
            sheet2.setColumnWidth(0, 18 * 256);

            // 当前操作行,创建新行后+1
            int currRowIndex2 = 0;
            newRow = sheet2.createRow(currRowIndex2++);

            newCell = newRow.createCell(0);
            newCell.setCellStyle(style2);
            newCell.setCellValue("序号");

            newCell = newRow.createCell(1);
            newCell.setCellStyle(style2);
            newCell.setCellValue("型号");

            newCell = newRow.createCell(2);
            newCell.setCellStyle(style2);
            newCell.setCellValue("颜色");

            newCell = newRow.createCell(3);
            newCell.setCellStyle(style2);
            newCell.setCellValue("双数");



            for (int i = 0; i <=maxSize-minSize; i++) {
                newCell = newRow.createCell(i+4);
                newCell.setCellStyle(style2);
                newCell.setCellValue((minSize+i)+"#");
            }
            seq=0;
            List<Map<String, Object>> orderCartDistributeBoxs = orderCartDistributeBoxService.selectColorNumByPeriod(periodSeq);
            for (Map<String, Object> map : orderCartDistributeBoxs) {
                Integer colorSeq = (Integer) map.get("colorSeq");
                Integer totalNum = (Integer) map.get("totalNum");
                newRow = sheet2.createRow(currRowIndex2++);
                seq++;

                newCell = newRow.createCell(0);
                newCell.setCellStyle(style2);
                newCell.setCellValue(seq);


                newCell = newRow.createCell(1);
                newCell.setCellStyle(style2);
                newCell.setCellValue(map.get("goodsId").toString());

                GoodsColorEntity goodsColorEntity=goodsColorService.selectById(colorSeq);

                newCell = newRow.createCell(2);
                newCell.setCellStyle(style2);
                newCell.setCellValue(goodsColorEntity.getName());

                newCell = newRow.createCell(3);
                newCell.setCellStyle(style2);
                newCell.setCellValue(totalNum);

                //根据货品序号和颜色序号查询各尺寸的件数
                Map<String, Integer>  distributeBox = new HashMap<String, Integer>();
                List<Map<String, Object>> sizeNums = orderCartDetailService.selectSizeNum(Integer.parseInt(map.get("shoesSeq").toString()), colorSeq);
                for (Map<String, Object> map2 : sizeNums) {
                    //尺码
                    String size = map2.get("sizeName").toString();
                    //选款数量
                    Integer selectNum = (Integer) map2.get("totalNum");
                    distributeBox.put(size, selectNum);
                }

                for (int j = 0; j <= maxSize-minSize; j++) {
                    newCell = newRow.createCell(j+4);
                    newCell.setCellStyle(style2);
                    Integer key = minSize+j;
                    Integer selectNum = distributeBox.get(key.toString());
                    if(selectNum != null) {
                        newCell.setCellValue(selectNum);
                    }else {
                        newCell.setCellValue("");
                    }

                }

            }
            ServletOutputStream out = response.getOutputStream();
            wb.write(out);
            out.flush();
            out.close();
            wb.close();


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error("配码单: 文件名编码异常");
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("配码单: " + e.getMessage(), e);
        }
    }
}
