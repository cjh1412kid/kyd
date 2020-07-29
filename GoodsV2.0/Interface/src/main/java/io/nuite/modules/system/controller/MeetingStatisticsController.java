package io.nuite.modules.system.controller;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Joiner;

import io.nuite.common.utils.R;
import io.nuite.modules.order_platform_app.entity.MeetingStatisticsModelEntity;
import io.nuite.modules.order_platform_app.utils.OrderStatusEnum;
import io.nuite.modules.system.service.MeetingStatisticsModelService;
import io.nuite.modules.system.service.MeetingStatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 订货平台订单统计分析
 *
 * @author yy
 * @date 2018-09-28
 */
@RestController
@RequestMapping("/meetingStatistics")
@Api(tags = "后台 - 订单统计分析接口", description = "")
public class MeetingStatisticsController extends AbstractController {

    @Autowired
    private MeetingStatisticsService meetingStatisticsService;

    @Autowired
    private MeetingStatisticsModelService meetingStatisticsModelService;

    /**
     * 订单统计分析列表
     */
    @GetMapping("/list")
    @ApiOperation("订单统计分析列表")
    public R orderlist(@RequestParam("statisticsModelSeq") Integer statisticsModelSeq,
    		 		@RequestParam(value = "meetingSeq", required = false) Integer meetingSeq,
                       @RequestParam(value = "userSeq", required = false) Integer userSeq
                     
                      
                      
    ) {
        try {
            //获取模板信息
            MeetingStatisticsModelEntity meetingStatisticsModelEntity = meetingStatisticsModelService.selectById(statisticsModelSeq);
            String lineField = meetingStatisticsModelEntity.getLineField();
            String summaryField = meetingStatisticsModelEntity.getSummaryField();

            //根据条件查询订单
            List<Object> userSeqList = new ArrayList<Object>();
            if (userSeq != null) {
                userSeqList.add(userSeq);
            } 
            String userSeqs = Joiner.on(",").join(userSeqList);
            List<Map<String, Object>> orderStatisticsList = meetingStatisticsService.orderStatisticsList(getUser().getCompanySeq(), userSeqs,meetingSeq);

            //添加订单状态对应中文名称
//            for (Map<String, Object> map : orderStatisticsList) {
//                Integer status = (Integer) map.get("orderStatus");
//                map.put("statusName", OrderStatusEnum.get(status).getFactoryName());
//            }

            for (Map<String, Object> map : orderStatisticsList) {
            	Integer buyCount=(Integer) map.get("buyCount");
            	BigDecimal productPrice=(BigDecimal) map.get("productPrice");
            	map.put("productTotalPrice", productPrice.multiply(new BigDecimal(buyCount)));
          }
            return R.ok().put("lineField", lineField).put("summaryField", summaryField).put("list", orderStatisticsList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }

    /**
     * 导出指定字段的excel
     *
     * @param response
     */
    @RequestMapping("exportExcel")
    public void exportOrderExcel(
                                 @RequestParam(value = "userSeq", required = false) Integer userSeq,
                                 @RequestParam(value = "meetingSeq", required = false) Integer meetingSeq,
                                 @RequestParam(value = "fieldsTitle", required = true) String[] title,
                                 @RequestParam(value = "fields", required = true) String[] fields,
                                 HttpServletResponse response) {
        try {
            //1.根据条件查询订单
            List<Object> userSeqList = new ArrayList<Object>();
            if (userSeq != null) {
                userSeqList.add(userSeq);
            }
            String userSeqs = Joiner.on(",").join(userSeqList);
            List<Map<String, Object>> orderStatisticsList = meetingStatisticsService.orderStatisticsList(getUser().getCompanySeq(), userSeqs, meetingSeq);
            //添加订单状态对应中文名称
//            for (Map<String, Object> map : orderStatisticsList) {
//                Integer status = (Integer) map.get("orderStatus");
//                map.put("statusName", OrderStatusEnum.get(status).getFactoryName());
//            }

            //2.创建Excel
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("订单汇总.xlsx", "UTF-8"));

            //创建excel2007工作簿
            XSSFWorkbook wb = new XSSFWorkbook();

            //创建sheet页
            XSSFSheet sheet = wb.createSheet("sheet1");
            //默认单元格宽度和高度
            sheet.setDefaultColumnWidth(17);
            sheet.setDefaultRowHeightInPoints(18);

            //标题行单元格样式
            XSSFCellStyle cellStyle = wb.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER); //水平居中 
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中

            //字体样式
            XSSFFont font = wb.createFont();
            font.setFontName("仿宋_GB2312");
            font.setBold(true); //粗体
            font.setFontHeightInPoints((short) 12);
            cellStyle.setFont(font); //字体

            //创建标题行
//          String[] title = {"订单号", "已付金额", "订单价格", "订单状态", "订货方", "备注", "留言"};
            XSSFRow row = sheet.createRow(0);
            row.setHeightInPoints(24);
            for (int i = 0; i < title.length; i++) {
                XSSFCell cell = row.createCell(i);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(title[i]);
            }

//            <result column="InputTime" 			property="inputTime" 		jdbcType="TIMESTAMP"/>
//            <result column="BuyCount" 			property="buyCount" 		jdbcType="INTEGER"/>
//            <result column="DeliverNum" 		property="deliverNum" 		jdbcType="INTEGER"/>
//            <result column="ProductPrice" 		property="productPrice" 	jdbcType="DECIMAL"/>
//            <result column="ProductTotalPrice" 	property="productTotalPrice" jdbcType="DECIMAL"/>
            List<String> doubleFileds = Arrays.asList("buyCount","productPrice", "productTotalPrice");

            // 循环创建数据行
            for (int i = 0; i < orderStatisticsList.size(); i++) {
                row = sheet.createRow(i + 1);
                Map<String, Object> orderMap = orderStatisticsList.get(i);

                for (int j = 0; j < fields.length; j++) {
                    if (doubleFileds.contains(fields[j])) {
                    	if(fields[j].equals("productTotalPrice")) {
                    		Integer buyCount=(Integer) orderMap.get("buyCount");
                        	BigDecimal productPrice=(BigDecimal) orderMap.get("productPrice");
                    		  row.createCell(j).setCellValue(productPrice.multiply(new BigDecimal(buyCount)).toString());
                    	}else {
                    		  row.createCell(j).setCellValue(Double.valueOf(orderMap.get(fields[j]).toString()));
                    	}
                      
                    } else {
                        row.createCell(j).setCellValue(orderMap.get(fields[j]).toString());
                    }
                }

            }

            // 获取输出流，写入excel并关闭
            ServletOutputStream out = response.getOutputStream();
            wb.write(out);
            out.flush();
            out.close();
            wb.close();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("订单统计分析汇总导出失败: " + e.getMessage(), e);
        }

    }

}
