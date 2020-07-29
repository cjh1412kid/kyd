package io.nuite.modules.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.binarywang.utils.qrcode.QrcodeUtils;
import io.nuite.common.utils.DateUtils;
import io.nuite.common.utils.PageUtils;
import io.nuite.common.utils.R;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.sr_base.entity.SysMenuEntity;
import io.nuite.modules.system.entity.MeetingEntity;
import io.nuite.modules.system.entity.MeetingPermissionEntity;
import io.nuite.modules.system.service.MeetingPermissionService;
import io.nuite.modules.system.service.MeetingService;
import io.nuite.modules.system.service.order_platform.OrderPartyManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 订货会管理
 *
 * @author yangchuang
 * @email ${email}
 * @date 2019-04-16 16:13:34
 */
@RestController
@RequestMapping("/system/meeting" )
@Api(tags = "后台-订货会管理接口" )
public class SysMeetingController extends AbstractController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private MeetingService meetingService;
    
    @Autowired
    private OrderPartyManagementService OrderPartyManagementService;
    
    @Autowired
    private MeetingPermissionService meetingPermissionService;
    
    /**
     * 列表
     */
    @RequestMapping("/list" )
    public R list(@RequestParam Map<String, Object> params) {
        Integer companySeq = getUser().getCompanySeq();
        
        PageUtils page = meetingService.queryPage(params, companySeq);
        
        return R.ok().put("page", page);
    }
    
    /**
     * 信息
     */
    @GetMapping("/info/{seq}" )
    public R info(@PathVariable("seq" ) Integer seq) {
        MeetingEntity meeting = meetingService.selectById(seq);
        
        return R.ok().put("meeting", meeting);
    }
    
    /**
     * 保存或更新
     */
    @RequestMapping("/save" )
    public R save(MeetingEntity meeting) {
        System.out.println("meeting = [" + meeting + "]" );
        
        if (meeting.getSeq() != null) {
        	meetingService.updateById(meeting);
        } else {
            meeting.setCompanySeq(getUser().getCompanySeq());
            meetingService.insert(meeting);
        }
        
        return R.ok();
    }
    
    /**
     * 删除
     */
    @ApiOperation(value = "删除指定序号的订货会管理" )
    @GetMapping("/delete/{seq}" )
    public R delete(@ApiParam("序号" ) @PathVariable Integer seq) {
    	try {
    		  // 判断该分类下是否有鞋子
            Boolean flag = meetingService.hasMeetingInMeetingGoods(seq);
            if (flag) {
                return R.error("该订货会已被使用，不可删除");
            }
    		meetingService.deleteById(seq);
        	return R.ok("删除成功");
    	} catch (Exception e) {
               e.printStackTrace();
               logger.error(e.getMessage(), e);
               return R.error("服务器开小差啦~");
        }
    }
    

    
    /**
     * 查询拥有当前功能的订货会用户
     */
    @PostMapping("/meetingList")
    @ApiOperation("订货方的列表")
    public R meetingPartyList(@ApiParam("公司类别序号") @RequestParam("companyType") Integer saleType,
                            @ApiParam("当前页码") @RequestParam("page") Integer page,
                            @ApiParam("每页记录数") @RequestParam("limit") Integer limit, @ApiParam("订货会编号") @RequestParam("seq")  Integer seq) {
        try {
            PageUtils companyBrandPage = OrderPartyManagementService.meetingPartyList(getUser(), saleType, page,
                    limit,seq);
            return R.ok().put("page", companyBrandPage);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("获取列表失败");
        }
    }
    
    /**
     * 订货会用户授权
     */
    @RequestMapping("/savePermission")
    public R savePermission(@RequestParam("meetingSeq") Integer meetingSeq,@RequestParam(value = "seqs[]",required = false) List<Integer> seqs,@RequestParam("allSeqs[]") List<Integer> allSeqs) {
    	//查询当前meetingSeq下的所有用户
    	meetingPermissionService.getAllPermissionByMeetingSeq(meetingSeq, seqs,allSeqs);
    	 return R.ok();
    }
    
    /**
     * 导出订货会商品二维码
     *
     * @param response
     */
    @PostMapping("exportQRCode" )
    public void exportQRCode(HttpServletResponse response) {
        ServletOutputStream out = null;
        HSSFWorkbook wb = null;
        try {
            response.setCharacterEncoding("UTF-8" );
            response.setHeader("content-Type", "application/vnd.ms-excel" );
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("订货会管理二维码.xls", "UTF-8" ));
            // 创建excel
            wb = new HSSFWorkbook();
            
            //列标题单元格样式
            HSSFCellStyle headCellStyle = wb.createCellStyle();
            //设置居中:
            headCellStyle.setAlignment(HorizontalAlignment.CENTER); // 居中
            headCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
            //设置字体:
            HSSFFont font = wb.createFont();
            font.setFontName("仿宋_GB2312" );
            font.setBold(true);//粗体显示
            font.setFontHeightInPoints((short) 12);
            headCellStyle.setFont(font);//选择需要用到的字体格式
            
            //内容单元格样式
            HSSFCellStyle contentCellStyle = wb.createCellStyle();
            //设置居中:
            contentCellStyle.setAlignment(HorizontalAlignment.CENTER); // 居中
            contentCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
            
            // 创建sheet页
            HSSFSheet sheet = wb.createSheet("sheet1" );
            //默认宽度和高度
            sheet.setDefaultColumnWidth(15);
            sheet.setDefaultRowHeightInPoints(18);
            //不同列的宽度
            sheet.setColumnWidth(0, 30 * 256);
            sheet.setColumnWidth(1, 40 * 256);
            sheet.setColumnWidth(2, 60 * 256);
            sheet.setColumnWidth(3, 40 * 256);
            sheet.setColumnWidth(4, 40 * 256);
            
            String[] title = {"序号", "订货会名称", "二维码", "开始时间", "结束时间"};
            HSSFRow row = sheet.createRow(0);
            row.setHeightInPoints(24);
            for (int i = 0; i < title.length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(headCellStyle);
                cell.setCellValue(title[i]);
            }
            
            // 订货会货品列表
            List<MeetingEntity> meetingEntities = meetingService.selectList(new EntityWrapper<MeetingEntity>()
                .eq("CompanySeq", getUser().getCompanySeq()));
            
            // 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            // 循环多少次
            int rowIndex = 1;
            HSSFCell cell;
            for (MeetingEntity meetingEntity : meetingEntities) {
                row = sheet.createRow(rowIndex);// 创建行
                row.setHeightInPoints(210);//行高度
                
                //第一列
                cell = row.createCell(0);
                cell.setCellStyle(contentCellStyle);
                cell.setCellValue(meetingEntity.getSeq());
                //第二列
                cell = row.createCell(1);
                cell.setCellStyle(contentCellStyle);
                cell.setCellValue(meetingEntity.getName());
                //第三列
                // anchor主要用于设置图片的属性
                HSSFClientAnchor anchor = new HSSFClientAnchor(10, 2, 1023, 255, (short) 2, rowIndex, (short) 2, rowIndex);
                // 插入图片
                HSSFPicture picture = patriarch.createPicture(anchor, wb.addPicture(QrcodeUtils.createQrcode(meetingEntity.getSeq().toString(), 280, null), HSSFWorkbook.PICTURE_TYPE_JPEG));
                picture.resize();
                rowIndex++;
                //第四列
                cell = row.createCell(3);
                cell.setCellStyle(contentCellStyle);
                cell.setCellValue(DateUtils.format(meetingEntity.getStartTime(),DateUtils.DATE_TIME_PATTERN));
                
                cell = row.createCell(4);
                cell.setCellStyle(contentCellStyle);
                cell.setCellValue(DateUtils.format(meetingEntity.getEndTime(),DateUtils.DATE_TIME_PATTERN));
                
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
    
}
