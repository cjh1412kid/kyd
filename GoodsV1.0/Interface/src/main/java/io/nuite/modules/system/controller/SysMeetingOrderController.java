package io.nuite.modules.system.controller;

import java.awt.Color;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import io.nuite.modules.order_platform_app.service.*;
import io.nuite.modules.sr_base.entity.BaseCompanyEntity;
import io.nuite.modules.sr_base.entity.GoodsCategoryEntity;
import io.nuite.modules.sr_base.service.BaseCompanyService;
import io.nuite.modules.sr_base.service.GoodsCategoryService;
import io.nuite.modules.system.entity.*;
import io.nuite.modules.system.service.*;
import io.swagger.models.auth.In;
import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;

import io.nuite.common.utils.DateUtils;
import io.nuite.common.utils.PageUtils;
import io.nuite.common.utils.R;
import io.nuite.modules.order_platform_app.entity.MeetingGoodsEntity;
import io.nuite.modules.order_platform_app.entity.MeetingOrderCartDetailEntity;
import io.nuite.modules.order_platform_app.entity.MeetingOrderCartDistributeBoxEntity;
import io.nuite.modules.order_platform_app.entity.MeetingOrderCartEntity;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.sr_base.entity.GoodsColorEntity;
import io.nuite.modules.sr_base.service.BaseUserService;
import io.nuite.modules.sr_base.service.GoodsColorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 订货会订单
 *
 * @author yangchuang
 * @date 2019-04-17 13:42:11
 */
@RestController
@RequestMapping("/system/meetingorder")
@Api(tags = "后台-订货会订单")
public class SysMeetingOrderController extends AbstractController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SysMeetingOrderService meetingOrderService;

	@Autowired
	private MeetingService meetingService;

	@Autowired
	private MeetingOrderCartService meetingOrderCartService;
	
	@Autowired
	private MeetingOrderCartDetailService meetingOrderCartDetailService;

	
	@Autowired
	private MeetingOrderCartDistributeBoxService meetingOrderCartDistributeBoxService;
	
	@Autowired
	private GoodsColorService GoodsColorService;
	
	@Autowired
	private MeetingGoodsService meetingGoodsService;
	
	@Autowired
	private BaseUserService baseUserService;
	
	@Autowired
	private MeetingGoodsValuateService meetingGoodsValuateService;

	@Autowired
	private SysMeetingOrderProductService productService;

	@Autowired
	private MeetingAreaService meetingAreaService;

	@Autowired
	private MeetingShoppingCartService meetingShoppingCartService;

	@Autowired
	private OrderAgreementService orderAgreementService;

	@Autowired
	private BaseCompanyService baseCompanyService;

	@Autowired
	private GoodsCategoryService goodsCategoryService;
	
	@PostMapping("/list")
	@ApiOperation(value = "订货会订单分页查询+条件查询")
	public R list(@ApiParam("第几页") Integer page, @ApiParam("每页数量") Integer limit,
			@ApiParam("年份") @RequestParam(required = false) Integer year,
			@ApiParam("订货会") @RequestParam(required = false) Integer meetingSeq,
			@ApiParam("订货方") @RequestParam(required = false) Integer meetingUserSeq,
			@ApiParam("需排序的列") @RequestParam(required = false) String sortColumn,
			@ApiParam("排序方式") @RequestParam(required = false) String sortType) {

		Page<MeetingOrderEntity> meetingOrderEntityPage = new Page<>(page, limit);
		Page page2 = meetingOrderService.queryPageByCompanySeq(meetingOrderEntityPage, getUser().getCompanySeq(), year,
				meetingSeq, meetingUserSeq,sortColumn,sortType);

		return R.ok().put("page", new PageUtils(page2));
	}

	@GetMapping("product/{meetingOrderSeq}/{keywords}")
	@ApiOperation(value = "分页查询订货会订单下的货品列表")
	public R listProducts(@ApiParam("订货会订单序号") @PathVariable Integer meetingOrderSeq,@PathVariable String keywords) {
		List<Map<String, Object>> list = meetingOrderService.queryProductInfoByMeetingOrderSeq(meetingOrderSeq,keywords);
		return R.ok(list);
	}

	@GetMapping("/delete/{seq}")
	@ApiOperation(value = "删除订货会订单")
	public R delete(@PathVariable("seq") Integer meetingOrderSeq) {
		meetingOrderService.deleteById(meetingOrderSeq);
		return R.ok();
	}

	@GetMapping("select/years")
	@ApiOperation(value = "查询订货会订单中存在的订货会所有年份")
	public R listYear() {
		List<Integer> years = meetingOrderService.queryOrderExistYears(getUser().getCompanySeq());
		return R.ok(years);
	}

	@GetMapping("select/Meetings/{year}")
	@ApiOperation(value = "查询订货会订单中存在的订货会")
	public R listMeeting(@ApiParam(value = "年份", required = true) @PathVariable Integer year) {
		List<Map<String, Object>> meetings = meetingOrderService.queryOrderExistMeetings(getUser().getCompanySeq(),
				year);
		return R.ok(meetings);
	}

	@GetMapping("select/MeetingUsers/{meetingSeq}")
	@ApiOperation(value = "查询指定定货方的订货会订单中存在的定货方")
	public R listMeetingUser(@ApiParam(value = "订货会序号", required = true) @PathVariable Integer meetingSeq) {
		List<Map<String, Object>> meetingUsers = meetingOrderService
				.queryOrderExistMeetingUsers(getUser().getCompanySeq(), meetingSeq);
		return R.ok(meetingUsers);
	}

	@GetMapping("list/byGoods/{meetingSeq}/{keywords}/{isCancel}")
	@ApiOperation(value = "按货品统计")
	public R listByGood(@ApiParam(value = "订货会序号", required = true) @PathVariable Integer meetingSeq,@ApiParam(value = "模糊搜索关键字", required = true)@PathVariable String keywords,@ApiParam(value = "模糊搜索关键字", required = true) @PathVariable Integer isCancel) {
		List<Map<String, Object>> goodsOrderProducts = meetingOrderService
				.queryGoodsOrderByMeetingSeq(getUser().getCompanySeq(), meetingSeq,keywords,isCancel);
		return R.ok(goodsOrderProducts);
	}

	@GetMapping("list/byArea/{meetingSeq}/{areaSeq}")
	@ApiOperation(value = "按区域统计")
	public R listByArea(@ApiParam(value = "订货会序号", required = true) @PathVariable Integer meetingSeq,@PathVariable Integer areaSeq) {
		List<Map<String, Object>> areaOrderProducts = meetingOrderService
				.queryAreaOrderByMeetingSeq(getUser().getCompanySeq(), meetingSeq,areaSeq);
		return R.ok(areaOrderProducts);
	}

	@PostMapping("excel/user")
	@ApiOperation(value = "下载定货方订货单", hidden = true)
	public void downloadMeetingUserExcel(@ApiParam("订货会") @RequestParam Integer meetingSeq,
			@ApiParam("订货方") @RequestParam(required = false) Integer meetingUserSeq, HttpServletResponse response) {

		try {

			List<MeetingOrderEntity> meetingOrderEntities = meetingOrderService.queryOrderByMeetingSeq(meetingSeq,
					meetingUserSeq);
			if (meetingOrderEntities == null || meetingOrderEntities.size() == 0) {
				logger.warn("订货单下载-查询内容为空");
				return;
			}
			//查询总的订单量
			Integer totalBuy=meetingOrderService.getTotalCountByMeetingSeq(meetingSeq, meetingUserSeq);

			// 获取订货会
			String filePrefix = meetingOrderEntities.get(0).getMeetingName() + "-";

			// 2.创建Excel
			response.setCharacterEncoding("UTF-8");
			response.setHeader("content-Type", "application/vnd.ms-excel");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode(filePrefix + "订货单.xlsx", "UTF-8"));

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
			newCell.setCellValue("总订货量："+totalBuy);
			int CountSeq=1;
			for (int i = 0; i < meetingOrderEntities.size(); i++) {
				MeetingOrderEntity meetingOrderEntity = meetingOrderEntities.get(i);
				List<Map<String, Object>> orderGoods = meetingOrderService
						.queryProductInfoByMeetingOrderSeq(meetingOrderEntity.getSeq(),"");

				// 创建订单标题
				CellRangeAddress cra = new CellRangeAddress(currRowIndex, currRowIndex, 0, 12); // 合并单元格
				sheet.addMergedRegion(cra);
				newRow = sheet.createRow(currRowIndex++);
				newRow.setHeightInPoints(30);

				// 标题内容
				StringBuilder sb = new StringBuilder().append("    ").append(meetingOrderEntity.getUsername())// 定货方
						.append("    订单号：").append(meetingOrderEntity.getOrderNum()).append("    下单日期：")
						.append(DateUtils.format(meetingOrderEntity.getInputTime(), DateUtils.DATE_TIME_PATTERN))
						.append(" 收货地址:").append(meetingOrderEntity.getAddress()).append("  收货人："+meetingOrderEntity.getReceiverName()).append(" 收货电话："+meetingOrderEntity.getTelephone()).append("    订单量序号:").append(CountSeq++).append("  物流信息：").append(meetingOrderEntity.getExpressName())
						.append("  物流电话:"+meetingOrderEntity.getExpressPhone());
				newCell = newRow.createCell(0);
				// newCell.setCellStyle(style1);
				newCell.setCellValue(sb.toString());
				int seq = 1;
				for (int j = 0; j < orderGoods.size(); j++) {
					// 每个货号尺码范围不同，需要创建1行标题行
					Map<String, Object> orderGood = orderGoods.get(j);
					/**
					 * { goodID: 'A1111', details: [ {color: '米白', size: {34: 12, 35: 13, 36: 14},
					 * cancel: true}, {color: '黑色', size: {34: 18, 35: 17, 36: 14}, cancel: false}
					 * ], title: [34, 35, 36], total:100 }
					 */
					// 数据解析
					List<Integer> sizeTitles = (List<Integer>) orderGood.get("title");
					Integer total = (Integer) orderGood.get("total");
					String goodID = (String) orderGood.get("goodID");
					List<Map<String, Object>> goodDetail = (List<Map<String, Object>>) orderGood.get("details");

					// 标题列表
					List<String> titles = new ArrayList<>();
					titles.add("序号");
					titles.add("货号");
					titles.add("订货量");
					titles.add("颜色");
					titles.add("总数");
					for (Integer sizeTitle : sizeTitles) {
						titles.add(sizeTitle.toString());
					}
					titles.add("建议");
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
						}
						// 第三列 颜色
						newCell = newRow.createCell(3);
						newCell.setCellStyle(style2);
						newCell.setCellValue(detail.get("color").toString());
						// 第四列 颜色-尺码 订货量总数
						newCell = newRow.createCell(4);
						newCell.setCellStyle(style2);
						newCell.setCellValue(detail.get("colorTotal").toString());
						// 第四列开始 尺码列
						int currCellIndex = 5;
						for (Integer sizeTitle : sizeTitles) {
							Integer sizeCount = sizeMap.get(sizeTitle);
							newCell = newRow.createCell(currCellIndex);
							newCell.setCellStyle(style2);
							if (sizeCount != null) {
								if(detail.get("cancel").equals(1)) {
									newCell.setCellValue(sizeCount+"(已取消)");
								}else {
									newCell.setCellValue(sizeCount);
								}
							}
							currCellIndex++;
						}
						if (n == 0) {
							// 最后1列 所有建议
							newCell = newRow.createCell(currCellIndex);
							newCell.setCellStyle(style2);
							newCell.setCellValue(detail.get("valuate").toString());
						}

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

	@PostMapping("exportCustomOrder")
	@ApiOperation(value = "下载定货方订货单", hidden = true)
	public void exportCustomOrder(@ApiParam("订货会") @RequestParam Integer meetingSeq,
										 @ApiParam("订货方") @RequestParam(required = false) Integer meetingUserSeq, HttpServletResponse response) {
		ServletOutputStream out = null;
		HSSFWorkbook wb = null;
		try {
			List<Integer> userSeqList = meetingOrderService.selectUserSeqList(getUser().getCompanySeq(),meetingSeq,meetingUserSeq);

			List<MeetingOrderEntity> meetingOrderEntities = meetingOrderService.queryOrderByMeetingSeq(meetingSeq,
					meetingUserSeq);
			if (meetingOrderEntities == null || meetingOrderEntities.size() == 0) {
				logger.warn("订货单下载-查询内容为空");
				return;
			}

			// 获取订货会
			String filePrefix = meetingOrderEntities.get(0).getMeetingName() + "-";

			// 2.创建Excel
			response.setCharacterEncoding("UTF-8");
			response.setHeader("content-Type", "application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filePrefix + "订货单.xls", "UTF-8"));

			// 创建excel2007工作簿
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
			List<Integer> sizeList = meetingOrderService.selectMinMaxSize(meetingSeq);
			for(Integer userSeq : userSeqList) {
				List<MeetingOrderEntity> meetingOrderList = meetingOrderService.selectCustomMeetingOrder(getUser().getCompanySeq(),meetingSeq,userSeq);
				BaseUserEntity user = baseUserService.selectById(userSeq);


				// 创建sheet页
				HSSFSheet sheet = wb.createSheet(user.getUserName());
				//默认宽度和高度
				sheet.setDefaultColumnWidth(15);
				sheet.setDefaultRowHeightInPoints(18);
				//不同列的宽度
				sheet.setColumnWidth(0, 18 * 256);
				sheet.setColumnWidth(1, 18 * 256);
				sheet.setColumnWidth(2, 40 * 256);

				List<String> title = new ArrayList<>(30);
				title.add("序号");
				title.add("客户");
				title.add("货品货号");
				title.add("批发价");
				title.add("颜色");
				//合计列下标
				Integer totalIndex = 0;
				for(Integer size : sizeList) {
					title.add(size.toString());
				}
				title.add("合计");
				title.add("金额");
				HSSFRow row = sheet.createRow(0);
				row.setHeightInPoints(24);
				for (int i = 0; i < title.size(); i++) {
				    if("颜色".equals(title.get(i))) {
				        totalIndex = i +1;
                    }
					HSSFCell cell = row.createCell(i);
					cell.setCellStyle(headCellStyle);
					cell.setCellValue(title.get(i));
				}


				// 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
				HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
				// 循环多少次
				int h = 1;
				HSSFCell cell;
				for (int i = 0; i < meetingOrderList.size(); i++) {
					MeetingOrderEntity meetingOrderEntity = meetingOrderList.get(i);// 获得行对象
					Wrapper<MeetingOrderProductEntity> meetingOrderProductEntityWrapper = new EntityWrapper<>();
					meetingOrderProductEntityWrapper.eq("MeetingOrder_Seq",meetingOrderEntity.getSeq());
					meetingOrderProductEntityWrapper.eq("MeetingGoods_Seq",meetingOrderEntity.getGoodSeq());
					meetingOrderProductEntityWrapper.eq("Color_Seq",meetingOrderEntity.getColorSeq());
					List<MeetingOrderProductEntity> list = productService.selectList(meetingOrderProductEntityWrapper);
					if(list.size() > 0 && list.get(0).getCancel() == 1) {
						continue;
					}

					row = sheet.createRow(h++);// 创建行
					row.setHeightInPoints(15);//行高度
					//序号
					cell = row.createCell(0);
					cell.setCellStyle(contentCellStyle);
					cell.setCellValue(h-1);

					//客户
					cell = row.createCell(1);
					cell.setCellStyle(contentCellStyle);
					cell.setCellValue(meetingOrderEntity.getUsername());

					//货号
					cell = row.createCell(2);
					cell.setCellStyle(contentCellStyle);
					cell.setCellValue(meetingOrderEntity.getGoodId());

					//批发价
					cell = row.createCell(3);
					cell.setCellStyle(contentCellStyle);
					if(meetingOrderEntity.getPrice() != null) {
						String money = meetingOrderEntity.getPrice().toString();
						String[] cut = money.split("\\.");
						//价格为整数时去掉小数点及两位小数
						if("00".equals(cut[1])) {
							cell.setCellValue(Integer.parseInt(cut[0]));
						}else {
							cell.setCellValue(Integer.parseInt(money));
						}
					}


					//颜色
					cell = row.createCell(4);
					cell.setCellStyle(contentCellStyle);
					cell.setCellValue(meetingOrderEntity.getColorName());

					Integer totalNum = 0;
					Integer num = 0;
					List<MeetingOrderCartDetailEntity> meetingOrderCartDetailEntities = meetingOrderService.getOrderCartDetail(meetingOrderEntity.getDistributeBoxSeq());
					for(MeetingOrderCartDetailEntity meetingOrderCartDetailEntity : meetingOrderCartDetailEntities) {
						num = 5;
						if(meetingOrderCartDetailEntity != null && meetingOrderCartDetailEntity.getSize() != null) {
							for(Integer size : sizeList) {
								if(size.equals(meetingOrderCartDetailEntity.getSize())) {
									totalNum += meetingOrderCartDetailEntity.getSelectNum();
									cell = row.createCell(num);
									cell.setCellStyle(contentCellStyle);
									cell.setCellValue(meetingOrderCartDetailEntity.getSelectNum());
								}
								num++;
							}
						}
					}

					//合计
					cell = row.createCell(num++);
					cell.setCellStyle(contentCellStyle);
					cell.setCellValue(meetingOrderEntity.getColorNum());

					//金额
					cell = row.createCell(num++);
					cell.setCellStyle(contentCellStyle);
					if(meetingOrderEntity.getMoney() != null) {
						String money = meetingOrderEntity.getMoney().toString();
						String[] cut = money.split("\\.");
						if("00".equals(cut[1])) {
							cell.setCellValue(Integer.parseInt(cut[0]));
						}else {
							cell.setCellValue(Integer.parseInt(money));
						}

					}
				}
                row = sheet.createRow(h);// 创建行
                row.setHeightInPoints(15);//行高度
                cell = row.createCell(0);
                cell.setCellStyle(contentCellStyle);
                cell.setCellValue("合计");
                if(h > 1) {
	                for(int i = 0;i < sizeList.size() + 2;i++) {
	                    cell = row.createCell(totalIndex + i);
	                    cell.setCellStyle(contentCellStyle);
	                    String colString = CellReference.convertNumToColString(totalIndex + i);
	                    String sumString = "SUM(" + colString + "2:" + colString + h + ")";
	                    cell.setCellFormula(sumString);
	                }
                }

			}


			// 获取输出流，写入excel并关闭
			out = response.getOutputStream();
			wb.write(out);
			out.flush();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.error("定货方订货单: 文件名编码异常");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("定货方订货单: " + e.getMessage(), e);
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

	/*@PostMapping("excel/goods")
	@ApiOperation(value = "下载订货单-按货号统计", hidden = true)
	public void downloadGoodsExcel(@ApiParam(value = "订货会", required = true) @RequestParam Integer meetingSeq,
			HttpServletResponse response) {

		try {

			List<Map<String, Object>> goodsList = meetingOrderService
					.queryGoodsOrderByMeetingSeq(getUser().getCompanySeq(), meetingSeq);

			if (goodsList == null || goodsList.size() == 0) {
				logger.warn("货号统计订货单下载-查询内容为空");
				return;
			}
			//查询总的订单量
			Integer totalBuy=meetingOrderService.getTotalCountByMeetingSeq(meetingSeq, null);
			
			MeetingEntity meetingEntity = meetingService.selectById(meetingSeq);

			// 文件名前缀
			String filePrefix = "";
			if (meetingEntity != null) {
				filePrefix += meetingEntity.getName() + "-";
			}

			// 2.创建Excel
			response.setCharacterEncoding("UTF-8");
			response.setHeader("content-Type", "application/vnd.ms-excel");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode(filePrefix + "订货单.xlsx", "UTF-8"));

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

			*//*
			 * 数据结构 [ { goodID: 'A1111', details: [ {color: '米白', size: {34: 12, 35: 13, 36:
			 * 14}}, {color: '黑色', size: {34: 18, 35: 17, 36: 14}} ], title: [34, 35, 36],
			 * imgSrc:'http://', total:101 } ]
			 *//*

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
			newCell.setCellValue("总订货量："+totalBuy);
			int seq = 1;
			for (int j = 0; j < goodsList.size(); j++) {
				// 每个货号尺码范围不同，需要创建1行标题行
				Map<String, Object> orderGood = goodsList.get(j);
				// 数据解析
				List<Integer> sizeTitles = (List<Integer>) orderGood.get("title");
				Integer total = (Integer) orderGood.get("total");
				String goodID = (String) orderGood.get("goodID");
				List<Map<String, Object>> goodDetail = (List<Map<String, Object>>) orderGood.get("details");

				// 标题列表
				List<String> titles = new ArrayList<>();
				titles.add("序号");
				titles.add("货号");
				titles.add("订货量");
				titles.add("颜色");
				titles.add("总数");
				for (Integer sizeTitle : sizeTitles) {
					titles.add(sizeTitle.toString());
				}
				titles.add("建议");
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
					}
					// 第三列 颜色
					newCell = newRow.createCell(3);
					newCell.setCellStyle(style2);
					if(detail.get("color") != null) {
						newCell.setCellValue(detail.get("color").toString());
					}
					// 第四列 颜色下所有尺码的数量之和
					newCell = newRow.createCell(4);
					newCell.setCellStyle(style2);
					newCell.setCellValue(detail.get("colorTotal").toString());
					// 第四列开始 尺码列
					int currCellIndex = 5;
					for (Integer sizeTitle : sizeTitles) {
						Integer sizeCount = sizeMap.get(sizeTitle);
						newCell = newRow.createCell(currCellIndex);
						newCell.setCellStyle(style2);
						if (sizeCount != null) {
							if(detail.get("cancel").equals(1)) {
								newCell.setCellValue(sizeCount+"(已取消)");
							}else {
								newCell.setCellValue(sizeCount);
							}
							
						}
						currCellIndex++;
					}
					if (n == 0) {
						// 最后1列 所有建议
						newCell = newRow.createCell(currCellIndex);
						newCell.setCellStyle(style2);
						newCell.setCellValue(detail.get("valuate").toString());
					}

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
	}*/

	@PostMapping("excel/goods")
	@ApiOperation(value = "下载订货单-按货号统计", hidden = true)
	public void downloadGoodsExcel(@ApiParam(value = "订货会", required = true) @RequestParam Integer meetingSeq,
								   HttpServletResponse response) {

		try {

			List<Map<String, Object>> goodsList = meetingOrderService
					.queryGoodsOrderByMeetingSeq(getUser().getCompanySeq(), meetingSeq,"''",null);

			if (goodsList == null || goodsList.size() == 0) {
				logger.warn("货号统计订货单下载-查询内容为空");
				return;
			}
			//查询总的订单量
			Integer totalBuy = meetingOrderService.getTotalCountByMeetingSeq(meetingSeq, null);
			
			//查询取消的订单量
			Integer cancelBuy = productService.selectCancelTotal(getUser().getCompanySeq(), meetingSeq, null, null, null);
			

			MeetingEntity meetingEntity = meetingService.selectById(meetingSeq);

			// 文件名前缀
			String filePrefix = "";
			if (meetingEntity != null) {
				filePrefix += meetingEntity.getName() + "-";
			}

			// 2.创建Excel
			response.setCharacterEncoding("UTF-8");
			response.setHeader("content-Type", "application/vnd.ms-excel");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode(filePrefix + "订货单.xlsx", "UTF-8"));

			// 创建excel2007工作簿
			XSSFWorkbook wb = new XSSFWorkbook();

			// 创建sheet页
			XSSFSheet sheet = wb.createSheet("sheet1");
			// 默认单元格宽度和高度
			sheet.setDefaultColumnWidth(8);
			sheet.setDefaultRowHeightInPoints(16);

			// 不同列的宽度
			sheet.setColumnWidth(0, 5*256);
			sheet.setColumnWidth(1, 12*256);
			sheet.setColumnWidth(2, 12*256);
			sheet.setColumnWidth(3, 8*256);
			sheet.setColumnWidth(4, 5*256);
			sheet.setColumnWidth(5, 5*256);
			sheet.setColumnWidth(6, 5*256);
			sheet.setColumnWidth(7, 5*256);
			sheet.setColumnWidth(8, 5*256);
			sheet.setColumnWidth(9, 5*256);
			sheet.setColumnWidth(10, 5*256);
			sheet.setColumnWidth(11, 5*256);
			sheet.setColumnWidth(12, 5*256);
			sheet.setColumnWidth(13, 5*256);
			sheet.setColumnWidth(14, 5*256);
			sheet.setColumnWidth(15, 5*256);
			sheet.setColumnWidth(16, 5*256);
			sheet.setColumnWidth(17, 5*256);
			sheet.setColumnWidth(18, 5*256);
			sheet.setColumnWidth(19, 5*256);
			sheet.setColumnWidth(20, 5*256);
			sheet.setColumnWidth(21, 5*256);
			sheet.setColumnWidth(22, 8*256);
			sheet.setColumnWidth(23, 8*256);

			// 单元格样式1
			XSSFCellStyle style1 = wb.createCellStyle();
			style1.setAlignment(HorizontalAlignment.CENTER); // 水平居中
			style1.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中

			// 单元格样式2
			XSSFCellStyle style2 = wb.createCellStyle();
			style2.setAlignment(HorizontalAlignment.CENTER); // 水平居中
			style2.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中

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
			CellRangeAddress cra1 = new CellRangeAddress(currRowIndex, currRowIndex, 0, 23); // 合并单元格
			sheet.addMergedRegion(cra1);
			newRow = sheet.createRow(currRowIndex++);
			newRow.setHeightInPoints(24);
			newCell = newRow.createCell(0);
			newCell.setCellStyle(style1);
			newCell.setCellValue("实际订货数量：" + totalBuy + "， 取消订货数量" + cancelBuy);
			int seq = 1;
			// 标题列表
			List<Integer> sizeList = meetingOrderService.selectMinMaxSize(meetingSeq);
			List<String> titles = new ArrayList<>();
			titles.add("序号");
			titles.add("客户");
			titles.add("货品货号");
			titles.add("批发价");
			titles.add("颜色");
			for(Integer size : sizeList) {
				titles.add(size.toString());
			}
			titles.add("合计");
			titles.add("金额");
			// 创建标题行
			newRow = sheet.createRow(currRowIndex++);
			Integer totalIndex = 0;
			for (int k = 0; k < titles.size(); k++) {
			    if("颜色".equals(titles.get(k))) {
			        totalIndex = k + 1;
                }
				newCell = newRow.createCell(k);
				newCell.setCellStyle(style1);
				String title = titles.get(k);
				newCell.setCellValue(title);
			}
			for (int j = 0; j < goodsList.size(); j++) {
				// 每个货号尺码范围不同，需要创建1行标题行
				Map<String, Object> orderGood = goodsList.get(j);
				// 数据解析
				List<Integer> sizeTitles = (List<Integer>) orderGood.get("title");
				Integer total = (Integer) orderGood.get("total");
				String goodID = (String) orderGood.get("goodID");
				List<Map<String, Object>> goodDetail = (List<Map<String, Object>>) orderGood.get("details");



				// 循环创建数据行
				for (int n = 0; n < goodDetail.size(); n++) {
					Map<String, Object> detail = goodDetail.get(n);
					Map<Integer, Integer> sizeMap = (Map<Integer, Integer>) detail.get("size");
					newRow = sheet.createRow(currRowIndex++);

					newCell = newRow.createCell(0);
					newCell.setCellStyle(style2);
					newCell.setCellValue(seq++);

					newCell = newRow.createCell(1);
					newCell.setCellStyle(style2);
					//newCell.setCellValue((String)detail.get("userName"));

					newCell = newRow.createCell(2);
					newCell.setCellStyle(style2);
					newCell.setCellValue(goodID);

					newCell = newRow.createCell(3);
					newCell.setCellStyle(style2);
					if(detail.get("price") != null) {
						String price = detail.get("price").toString();
						String[] prices = price.split("\\.");
						if("00".equals(prices[1])) {
							newCell.setCellValue(Integer.parseInt(prices[0]));
						}else {
							newCell.setCellValue(Integer.parseInt(price));
						}
					}

					newCell = newRow.createCell(4);
					newCell.setCellStyle(style2);
					if(detail.get("color") != null) {
						newCell.setCellValue(detail.get("color").toString());
					}
					Integer totalNum = 0;
					Integer num = 0;
					for (Integer sizeTitle : sizeTitles) {
						num = 5;
						Integer sizeCount = sizeMap.get(sizeTitle);
						for(Integer size : sizeList) {
							if(sizeTitle.equals(size)) {
								newCell = newRow.createCell(num);
								newCell.setCellStyle(style2);
								if (sizeCount != null) {
//									if(detail.get("cancel").equals(1)) {
//										newCell.setCellValue(sizeCount+"(已取消)");
//									}else {
										newCell.setCellValue(sizeCount);
//									}
									totalNum += sizeCount;
								}
							}
							num++;
						}
					}

					newCell = newRow.createCell(num++);
					newCell.setCellStyle(style2);
					newCell.setCellValue(totalNum);

					newCell = newRow.createCell(num++);
					newCell.setCellStyle(style2);
					if(detail.get("price") != null) {
						String totalMoney = new BigDecimal(detail.get("price").toString()).multiply(new BigDecimal(totalNum)).toString();
						String[] totalMoneys = totalMoney.split("\\.");
						if("00".equals(totalMoneys[1])) {
							newCell.setCellValue(Integer.parseInt(totalMoneys[0]));
						}else {
							newCell.setCellValue(Integer.parseInt(totalMoney));
						}
					}

				}
			}
			newRow = sheet.createRow(currRowIndex);
			newCell = newRow.createCell(0);
			newCell.setCellStyle(style2);
			newCell.setCellValue("合计");
			if(currRowIndex > 1) {
				for(int i = 0;i < sizeList.size() + 2;i++) {
				    newCell = newRow.createCell(totalIndex + i);
				    newCell.setCellStyle(style2);
				    String colString = CellReference.convertNumToColString(totalIndex + i);
				    String sumString = "SUM(" + colString + "3:" + colString + currRowIndex + ")";
				    newCell.setCellFormula(sumString);
	            }
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

	@PostMapping("excel/area")
	@ApiOperation(value = "下载区域订货单", hidden = true)
	public void downloadAreaExcel(@ApiParam(value = "订货会序号", required = true) Integer meetingSeq,
			HttpServletResponse response) {

		try {

			List<Map<String, Object>> areaOrderProducts = meetingOrderService
					.queryAreaOrderByMeetingSeq(getUser().getCompanySeq(), meetingSeq,null);
			if (areaOrderProducts == null || areaOrderProducts.size() == 0) {
				logger.warn("区域统计订货单下载-查询内容为空");
				return;
			}
			//查询总的订单量
			Integer totalBuy=meetingOrderService.getTotalCountByMeetingSeq(meetingSeq, null);
			
			// 获取订货会
			MeetingEntity meetingEntity = meetingService.selectById(meetingSeq);
			String filePrefix = "";
			if (meetingEntity != null) {
				filePrefix += meetingEntity.getName() + "-";
			}

			// 2.创建Excel
			response.setCharacterEncoding("UTF-8");
			response.setHeader("content-Type", "application/vnd.ms-excel");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode(filePrefix + "区域统计订货单.xlsx", "UTF-8"));

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

			/*
			 * 返回的数据结构
			 * 
			 * [ {areaName:'郑州', areaTotal:101, areaDetail:[ { goodID: 'A1111', details: [
			 * {color: '米白', size: {34: 12, 35: 13, 36: 14}}, {color: '黑色', size: {34: 18,
			 * 35: 17, 36: 14}} ], title: [34, 35, 36], imgSrc:'http://', total:110, } ] } ]
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
			newCell.setCellValue("总订货量："+totalBuy);
			for (Map<String, Object> areaOrderProduct : areaOrderProducts) {

				// 数据解析
				String areaName = (String) areaOrderProduct.get("areaName");
				Integer areaTotal = (Integer) areaOrderProduct.get("areaTotal");
				List<Map<String, Object>> areaDetail = (List<Map<String, Object>>) areaOrderProduct.get("areaDetail");

				for (int i = 0; i < areaDetail.size(); i++) {
					Map<String, Object> goodsMap = areaDetail.get(i);
					// 数据解析
					List<Integer> sizeTitles = (List<Integer>) goodsMap.get("title");
					Integer total = (Integer) goodsMap.get("total");
					String goodID = (String) goodsMap.get("goodID");
					List<Map<String, Object>> goodDetail = (List<Map<String, Object>>) goodsMap.get("details");

					// 标题列表
					List<String> titles = new ArrayList<>();
					titles.add("区域");
					titles.add("订货量");
					titles.add("序号");
					titles.add("货号");
					titles.add("订货量");
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
					int seq = 1;
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
								newCell.setCellValue(areaName);

								newCell = newRow.createCell(1);
								newCell.setCellStyle(style2);
								newCell.setCellValue(areaTotal);
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
						}
						// 第5列 颜色
						newCell = newRow.createCell(5);
						newCell.setCellStyle(style2);
						newCell.setCellValue(detail.get("color").toString());
						// 第6列 颜色下尺码数量之和
						newCell = newRow.createCell(6);
						newCell.setCellStyle(style2);
						newCell.setCellValue(Integer.parseInt(detail.get("colorTotal").toString()));

						// 第7列开始 尺码列
						int currCellIndex = 7;
						for (Integer sizeTitle : sizeTitles) {
							Integer sizeCount = sizeMap.get(sizeTitle);
							newCell = newRow.createCell(currCellIndex);
							newCell.setCellStyle(style2);
							if (sizeCount != null) {
								if(detail.get("cancel").equals(1)) {
									newCell.setCellValue(sizeCount+"(已取消)");
								}else {
									newCell.setCellValue(sizeCount);
								}
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
			logger.error("区域统计订货单: 文件名编码异常");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("区域统计订货单: " + e.getMessage(), e);
		}
	}

	
	public void downloadDistributeBoxExcel(@ApiParam(value = "订货会序号", required = true) Integer meetingSeq,
			HttpServletResponse response) {
		try {
			// 根据订货会序号查询购物车信息
			List<MeetingOrderCartEntity> cartEntities = meetingOrderCartService.getListByMeetingSeq(meetingSeq);
			  if (cartEntities == null || cartEntities.size() == 0) {
				  logger.warn("配码单下载-查询内容为空"); 
				  return; 
			  }
			// 获取订货会
				MeetingEntity meetingEntity = meetingService.selectById(meetingSeq);
				String filePrefix = "";
				if (meetingEntity != null) {
					filePrefix += meetingEntity.getName() + "-";
				}
				List<MeetingOrderEntity> meetingOrderEntities=meetingOrderService.getAllListByMeeingSeq(meetingSeq);
				
				
				
				// 2.创建Excel
				response.setCharacterEncoding("UTF-8");
				response.setHeader("content-Type", "application/vnd.ms-excel");
				response.setHeader("Content-Disposition",
						"attachment;filename=" + URLEncoder.encode(filePrefix + "配码单.xlsx", "UTF-8"));
				// 创建excel2007工作簿
				XSSFWorkbook wb = new XSSFWorkbook();
			  //查询所有订货的用户
			  for (MeetingOrderEntity meetingOrderEntity : meetingOrderEntities) {
				  List<MeetingOrderCartEntity> meetingCartEntities =  meetingOrderCartService.getListByOrderSeq(meetingOrderEntity.getSeq());
				  BaseUserEntity baseUserEntity=baseUserService.selectById(meetingOrderEntity.getUserSeq());
				  	// 创建sheet页
					XSSFSheet sheet = wb.createSheet(baseUserEntity.getUserName()+"的"+meetingOrderEntity.getOrderNum() +"配码单");
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
					
					// 单元格样式3
					XSSFCellStyle style3 = wb.createCellStyle();
					style3.setAlignment(HorizontalAlignment.CENTER); // 水平居中
					style3.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
					style3.setBorderLeft(BorderStyle.THIN);
					style3.setLeftBorderColor(new XSSFColor(Color.black));
					style3.setBorderRight(BorderStyle.THIN);
					style3.setRightBorderColor(new XSSFColor(Color.black));
					style3.setBorderTop(BorderStyle.THIN);
					style3.setTopBorderColor(new XSSFColor(Color.black));
					style3.setBorderBottom(BorderStyle.THIN);
					style3.setBottomBorderColor(new XSSFColor(Color.black));
					style3.setFillPattern(FillPatternType.SOLID_FOREGROUND); //设置填充方案
					style3.setFillForegroundColor(new XSSFColor(new Color(255,217,102)));  //设置填充颜色

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
					for (MeetingOrderCartEntity shoppingCartEntity : meetingCartEntities) {
						String meetingGoodsID=shoppingCartEntity.getMeetingGoodsID(); //工厂货号
						
						String userGoodID=shoppingCartEntity.getUserGoodID();//客户型号
						Integer perBoxNum=shoppingCartEntity.getPerBoxNum();//每箱数量（配件）
						Integer meetingShoppingCartSeq=shoppingCartEntity.getSeq();
						Integer isAllocated=shoppingCartEntity.getIsAllocated();//是否已配码0：否 1：是
						Integer meetingGoodsSeq=shoppingCartEntity.getMeetingGoodsSeq();
						MeetingGoodsEntity meetingGoodsEntity=meetingGoodsService.selectById(meetingGoodsSeq);
						newRow = sheet.createRow(currRowIndex++);
						
						newCell = newRow.createCell(0);
						newCell.setCellStyle(style3);
						newCell.setCellValue("工厂货号");
						
						newCell = newRow.createCell(1);
						newCell.setCellStyle(style2);
						newCell.setCellValue(meetingGoodsEntity.getGoodID());
						
						newCell = newRow.createCell(2);
						newCell.setCellStyle(style3);
						newCell.setCellValue("客户型号");
						
						newCell = newRow.createCell(3);
						newCell.setCellStyle(style2);
						if(userGoodID!=null) {
							newCell.setCellValue(userGoodID);
						}else {
							newCell.setCellValue("");
						}
					
						
						newCell = newRow.createCell(4);
						newCell.setCellStyle(style3);
						newCell.setCellValue("配件数");
						
						newCell = newRow.createCell(5);
						newCell.setCellStyle(style2);
						if(perBoxNum!=null) {
							newCell.setCellValue(perBoxNum);
						}else {
							newCell.setCellValue("");
						}
						
						
						newRow = sheet.createRow(currRowIndex++);
						
						newCell = newRow.createCell(0);
						newCell.setCellStyle(style3);
						newCell.setCellValue("订单数量");
						
						
						Integer cellIndex=1;
					if(isAllocated.equals(1)) {
						List<Map<String, Object>> shoppingCartDistributeBoxEntities=meetingOrderCartDistributeBoxService.getSelectNum(meetingShoppingCartSeq);
						List<MeetingOrderCartDistributeBoxEntity> meetingShoppingCartDistributeBoxEntities=meetingOrderCartDistributeBoxService.getMeetingShoppingCartDistributeBoxListByShoppingCartSeq(meetingShoppingCartSeq);
						for (Map<String, Object> meetingShoppingCartDistributeBoxEntity : shoppingCartDistributeBoxEntities) {
							Integer colorTotalNum=(Integer) meetingShoppingCartDistributeBoxEntity.get("num");//本颜色总数量
							Integer colorSeq =(Integer) meetingShoppingCartDistributeBoxEntity.get("colorSeq");//颜色seq
							GoodsColorEntity goodsColorEntity=GoodsColorService.selectById(colorSeq);
							newCell = newRow.createCell(cellIndex++);
							newCell.setCellStyle(style3);
							newCell.setCellValue(goodsColorEntity.getName());
							
							newCell = newRow.createCell(cellIndex++);
							newCell.setCellStyle(style2);
							newCell.setCellValue(colorTotalNum);
						
						}
						
						CellRangeAddress cra = new CellRangeAddress(currRowIndex, currRowIndex, 0, 12); // 合并单元格
						sheet.addMergedRegion(cra);
						newRow = sheet.createRow(currRowIndex++);
						newRow.setHeightInPoints(24);

						// 标题内容
						
						newCell = newRow.createCell(0);
						 newCell.setCellStyle(style1);
						newCell.setCellValue("配码明细");
						
						newRow = sheet.createRow(currRowIndex++);
						
						newCell = newRow.createCell(0);
						newCell.setCellStyle(style3);
						newCell.setCellValue("颜色");
						
						newCell = newRow.createCell(1);
						newCell.setCellStyle(style3);
						newCell.setCellValue("数量");
						
						newCell = newRow.createCell(2);
						newCell.setCellStyle(style3);
						newCell.setCellValue("件数");
						//获取最小尺码段和最大尺码段
					
						
						Integer minSize=meetingGoodsEntity.getMinSize();
						Integer maxSize=meetingGoodsEntity.getMaxSize();
						
						for (int i = 0; i <=maxSize-minSize; i++) {
							newCell = newRow.createCell(i+3);
							newCell.setCellStyle(style3);
							newCell.setCellValue((minSize+i)+"#");
						}
						
						
						for (MeetingOrderCartDistributeBoxEntity meetingShoppingCartDistributeBoxEntity : meetingShoppingCartDistributeBoxEntities) {
							Integer meetingShoppingCartDistributeBoxSeq=meetingShoppingCartDistributeBoxEntity.getSeq();
							List<MeetingOrderCartDetailEntity> meetingShoppingCartDetailEntities=meetingOrderCartDetailService.getListByMeetingShoppingCartDistributeBoxSeq(meetingShoppingCartDistributeBoxSeq);
							Integer colorSeq =meetingShoppingCartDistributeBoxEntity.getColorSeq();//颜色seq
							GoodsColorEntity goodsColorEntity=GoodsColorService.selectById(colorSeq);
							newRow = sheet.createRow(currRowIndex++);
							
							newCell = newRow.createCell(0);
							newCell.setCellStyle(style2);
							newCell.setCellValue(goodsColorEntity.getName());
							
							newCell = newRow.createCell(1);
							newCell.setCellStyle(style2);
							newCell.setCellValue(meetingShoppingCartDistributeBoxEntity.getColorTotalNum());
							
							newCell = newRow.createCell(2);
							newCell.setCellStyle(style2);
							if(meetingShoppingCartDistributeBoxEntity.getBoxCount()!=null) {
								newCell.setCellValue(meetingShoppingCartDistributeBoxEntity.getBoxCount());
							}else {
								newCell.setCellValue(meetingShoppingCartDistributeBoxEntity.getColorTotalNum()/perBoxNum);	
							}
							
							
							
							Map<String, Integer>  distributeBox=new HashMap<String, Integer>();
							for (MeetingOrderCartDetailEntity meetingShoppingCartDetailEntity : meetingShoppingCartDetailEntities) {
								Integer Size=meetingShoppingCartDetailEntity.getSize();//尺码
								Integer SelectNum=meetingShoppingCartDetailEntity.getSelectNum();//选款数量
									distributeBox.put(Size.toString(), SelectNum);
							}
							
							for (int i = 0; i <=maxSize-minSize; i++) {
								newCell = newRow.createCell(i+3);
								newCell.setCellStyle(style2);
								Integer key=minSize+i;
								Integer selectNum=	distributeBox.get(key.toString());
								if(selectNum!=null) {
									newCell.setCellValue(selectNum);
								}else {
									newCell.setCellValue("");
								}
								
							}
						}
//						}else{
//							List<Map<String, Object>> shoppingCartDetailEntities=meetingShoppingCartDetailService.getSelectNum(meetingShoppingCartSeq);
//							List<MeetingShoppingCartDetailEntity> meetingShoppingCartDetailEntities=meetingShoppingCartDetailService.getListByMeetingShoppingCartDistributeBoxSeq(meetingShoppingCartSeq);	
//							for (Map<String, Object> shoppingCartDetailEntity : shoppingCartDetailEntities) {
//								Integer colorTotalNum=(Integer) shoppingCartDetailEntity.get("num");//本颜色总数量
//								Integer colorSeq =(Integer) shoppingCartDetailEntity.get("colorSeq");//颜色seq
//								GoodsColorEntity goodsColorEntity=GoodsColorService.selectById(colorSeq);
//								newCell = newRow.createCell(cellIndex++);
//								newCell.setCellStyle(style3);
//								newCell.setCellValue(goodsColorEntity.getName());
//								
//								newCell = newRow.createCell(cellIndex++);
//								newCell.setCellStyle(style2);
//								newCell.setCellValue(colorTotalNum);
//							
//							}
//							
//							CellRangeAddress cra = new CellRangeAddress(currRowIndex, currRowIndex, 0, 12); // 合并单元格
//							sheet.addMergedRegion(cra);
//							newRow = sheet.createRow(currRowIndex++);
//							newRow.setHeightInPoints(24);
//
//							// 标题内容
//							
//							newCell = newRow.createCell(0);
//							newCell.setCellStyle(style1);
//							newCell.setCellValue("配码明细");
//							
//							newRow = sheet.createRow(currRowIndex++);
//							
//							newCell = newRow.createCell(0);
//							newCell.setCellStyle(style3);
//							newCell.setCellValue("颜色");
//							
//							newCell = newRow.createCell(1);
//							newCell.setCellStyle(style3);
//							newCell.setCellValue("数量");
//							
//							newCell = newRow.createCell(2);
//							newCell.setCellStyle(style3);
//							newCell.setCellValue("件数");
//							//获取最小尺码段和最大尺码段
//							MeetingGoodsEntity meetingGoodsEntity=meetingGoodsService.selectById(meetingGoodsSeq);
//							
//							Integer minSize=meetingGoodsEntity.getMinSize();
//							Integer maxSize=meetingGoodsEntity.getMaxSize();
//							
//							for (int i = 0; i <=maxSize-minSize; i++) {
//								newCell = newRow.createCell(i+3);
//								newCell.setCellStyle(style3);
//								newCell.setCellValue((minSize+i)+"#");
//							}
						
					
	                  }
					currRowIndex++;
					currRowIndex++;
					currRowIndex++;
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
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("配码单: " + e.getMessage(), e);
		}

	}
	
	
	
	
	/**
	 * 下载配码
	 * @param meetingSeq
	 * @param response
	 */
	@PostMapping("excel/distributeBox")
	public void downloadDistributeExcel(@ApiParam(value = "订货会序号", required = true) Integer meetingSeq,
			HttpServletResponse response) {
		try {
			// 根据订货会序号查询购物车信息
			List<MeetingOrderCartEntity> cartEntities = meetingOrderCartService.getListByMeetingSeq(meetingSeq);
			  if (cartEntities == null || cartEntities.size() == 0) {
				  logger.warn("配码单下载-查询内容为空"); 
				  return; 
			  }
			// 获取订货会
				MeetingEntity meetingEntity = meetingService.selectById(meetingSeq);
			//获取本次订货会最小尺码和最大尺码
				Integer minSize=meetingGoodsService.getMinSizeByMeetingSeq(meetingSeq);
				
				Integer maxSize=meetingGoodsService.getMaxSizeByMeetingSeq(meetingSeq);
				
				
				String filePrefix = "";
				if (meetingEntity != null) {
					filePrefix += meetingEntity.getName() + "-";
				}
				List<MeetingOrderEntity> meetingOrderEntities=meetingOrderService.getAllListByMeeingSeq(meetingSeq);
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
				newCell.setCellValue("厂家");
				
				newCell = newRow.createCell(5);
				newCell.setCellStyle(style2);
				newCell.setCellValue("厂家型号");
				
				newCell = newRow.createCell(6);
				newCell.setCellStyle(style2);
				newCell.setCellValue("供应价");
				
				
				newCell = newRow.createCell(7);
				newCell.setCellStyle(style2);
				newCell.setCellValue("颜色");
				
				newCell = newRow.createCell(8);
				newCell.setCellStyle(style2);
				newCell.setCellValue("双数");
				
				
				
				for (int i = 0; i <=maxSize-minSize; i++) {
					newCell = newRow.createCell(i+9);
					newCell.setCellStyle(style2);
					newCell.setCellValue((minSize+i)+"#");
				}
				
				newCell = newRow.createCell(maxSize-minSize+10);
				newCell.setCellStyle(style2);
				newCell.setCellValue("备注");
				
				newCell = newRow.createCell(maxSize-minSize+11);
				newCell.setCellStyle(style2);
				newCell.setCellValue("建议");

				
				
				int seq=0;
				int totalCancelNum = 0;
				
				  for (MeetingOrderEntity meetingOrderEntity : meetingOrderEntities) {
					  List<MeetingOrderCartEntity> meetingOrderCartEntities =  meetingOrderCartService.getListByOrderSeq(meetingOrderEntity.getSeq());
					  BaseUserEntity baseUserEntity = baseUserService.selectById(meetingOrderEntity.getUserSeq());
						for (MeetingOrderCartEntity meetingOrderCartEntity : meetingOrderCartEntities) {
							String userGoodID = meetingOrderCartEntity.getUserGoodID();//客户型号
							Integer meetingGoodsSeq = meetingOrderCartEntity.getMeetingGoodsSeq();
							//根据货号和客户编号查询建议
						String valute=meetingGoodsValuateService.getMeetingGoodsValuateByUserSeq(meetingGoodsSeq, meetingOrderEntity.getUserSeq());
							
							MeetingGoodsEntity meetingGoodsEntity=meetingGoodsService.selectById(meetingGoodsSeq);
							Integer meetingOrderCartSeq = meetingOrderCartEntity.getSeq();
						//	List<Map<String, Object>> shoppingCartDistributeBoxEntities=meetingOrderCartDistributeBoxService.getSelectNum(meetingShoppingCartSeq);
							List<MeetingOrderCartDistributeBoxEntity> meetingOrderCartDistributeBoxEntities = meetingOrderCartDistributeBoxService.getMeetingShoppingCartDistributeBoxListByShoppingCartSeq(meetingOrderCartSeq);
							for (MeetingOrderCartDistributeBoxEntity meetingOrderCartDistributeBoxEntity : meetingOrderCartDistributeBoxEntities) {
								Integer meetingShoppingCartDistributeBoxSeq=meetingOrderCartDistributeBoxEntity.getSeq();
								List<MeetingOrderCartDetailEntity> meetingShoppingCartDetailEntities=meetingOrderCartDetailService.getListByMeetingShoppingCartDistributeBoxSeq(meetingShoppingCartDistributeBoxSeq);
								Integer colorSeq =meetingOrderCartDistributeBoxEntity.getColorSeq();//颜色seq
								GoodsColorEntity goodsColorEntity=GoodsColorService.selectById(colorSeq);
								newRow = sheet1.createRow(currRowIndex++);
								seq++;
								newCell = newRow.createCell(0);
								newCell.setCellStyle(style2);
								newCell.setCellValue(seq);
								
								newCell = newRow.createCell(1);
								newCell.setCellStyle(style2);
								newCell.setCellValue(baseUserEntity.getUserName());
								
								newCell = newRow.createCell(2);
								newCell.setCellStyle(style2);
								newCell.setCellValue(meetingGoodsEntity.getGoodID());
								
								newCell = newRow.createCell(3);
								newCell.setCellStyle(style2);
								newCell.setCellValue(userGoodID);
								
								
								newCell = newRow.createCell(4);
								newCell.setCellStyle(style2);
								newCell.setCellValue(meetingGoodsEntity.getFactory());
								
								newCell = newRow.createCell(5);
								newCell.setCellStyle(style2);
								newCell.setCellValue(meetingGoodsEntity.getFactoryGoodId());
								
								newCell = newRow.createCell(6);
								newCell.setCellStyle(style2);
								if(meetingGoodsEntity.getFactoryPrice() != null) {
									newCell.setCellValue(meetingGoodsEntity.getFactoryPrice().doubleValue());
								}
								
								newCell = newRow.createCell(7);
								newCell.setCellStyle(style2);
								newCell.setCellValue(goodsColorEntity.getName());
								
								newCell = newRow.createCell(8);
								newCell.setCellStyle(style2);
								newCell.setCellValue(meetingOrderCartDistributeBoxEntity.getColorTotalNum());
								
								
								
								Map<String, Integer>  distributeBox=new HashMap<String, Integer>();
								for (MeetingOrderCartDetailEntity meetingShoppingCartDetailEntity : meetingShoppingCartDetailEntities) {
									Integer Size=meetingShoppingCartDetailEntity.getSize();//尺码
									Integer SelectNum=meetingShoppingCartDetailEntity.getSelectNum();//选款数量
										distributeBox.put(Size.toString(), SelectNum);
										
								}
								for (int j = 0; j <=maxSize-minSize; j++) {
									newCell = newRow.createCell(j+9);
									newCell.setCellStyle(style2);
									Integer key=minSize+j;
									Integer selectNum=	distributeBox.get(key.toString());
									if(selectNum!=null) {
										newCell.setCellValue(selectNum);
									}else {
										newCell.setCellValue("");
									}
									
								}
								
								//判断此订单 此货品 此颜色 是否有任意一个已取消的尺码 （理论一个尺码取消所有尺码均已取消）
								Integer c_orderSeq = meetingOrderEntity.getSeq();
								Integer c_meetingGoodsSeq = meetingOrderCartEntity.getMeetingGoodsSeq();
								Integer c_colorSeq = meetingOrderCartDistributeBoxEntity.getColorSeq();
								boolean cancelFlag = productService.isOrderGoodsColorCancel(c_orderSeq, c_meetingGoodsSeq, c_colorSeq);
								newCell = newRow.createCell(maxSize-minSize+10);
								newCell.setCellStyle(style2);
								if(cancelFlag) {
									newCell.setCellValue("已取消");
									totalCancelNum += meetingOrderCartDistributeBoxEntity.getColorTotalNum();
								}
								
								newCell = newRow.createCell(maxSize-minSize+11);
								newCell.setCellStyle(style2);
								newCell.setCellValue(valute);
								
							}
					
						}
					  
				  }
				
					newRow = sheet1.createRow(currRowIndex);// 创建行
					newRow.setHeightInPoints(15);//行高度
					newCell = newRow.createCell(0);
					newCell.setCellStyle(style2);
					newCell.setCellValue("合计");
	                if(currRowIndex > 1) {
		                for(int i = 0;i < maxSize-minSize + 2;i++) {
		                	newCell = newRow.createCell(8 + i);
		                	newCell.setCellStyle(style2);
		                    String colString = CellReference.convertNumToColString(8 + i);
		                    String sumString = "SUM(" + colString + "2:" + colString + currRowIndex + ")";
		                    newCell.setCellFormula(sumString);
		                }
		                newCell = newRow.createCell(8 + maxSize-minSize + 2);
	                	newCell.setCellStyle(style2);
	                    newCell.setCellValue("合计取消：" + totalCancelNum);
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
				newCell.setCellValue("厂家");
				
				newCell = newRow.createCell(3);
				newCell.setCellStyle(style2);
				newCell.setCellValue("厂家型号");
				
				newCell = newRow.createCell(4);
				newCell.setCellStyle(style2);
				newCell.setCellValue("供应价");
				
				
				newCell = newRow.createCell(5);
				newCell.setCellStyle(style2);
				newCell.setCellValue("颜色");
				
				newCell = newRow.createCell(6);
				newCell.setCellStyle(style2);
				newCell.setCellValue("双数");
				
				
				
				for (int i = 0; i <=maxSize-minSize; i++) {
					newCell = newRow.createCell(i+7);
					newCell.setCellStyle(style2);
					newCell.setCellValue((minSize+i)+"#");
				}
				seq=0;
				List<Map<String, Object>> meetingOrderCartDistributeBoxs=meetingOrderCartDistributeBoxService.getTotalNumByMeetingSeq(meetingSeq);
				for (Map<String, Object> map : meetingOrderCartDistributeBoxs) {
					Integer meetingGoodsSeq=(Integer) map.get("meetingGoodsSeq");
					Integer colorSeq=(Integer) map.get("colorSeq");
					Integer totalNum=(Integer) map.get("totalNum");
					newRow = sheet2.createRow(currRowIndex2++);
					seq++;
					
					newCell = newRow.createCell(0);
					newCell.setCellStyle(style2);
					newCell.setCellValue(seq);
					
					MeetingGoodsEntity meetingGoodsEntity=meetingGoodsService.selectById(meetingGoodsSeq);
					
					newCell = newRow.createCell(1);
					newCell.setCellStyle(style2);
					newCell.setCellValue(meetingGoodsEntity.getGoodID());
					
					GoodsColorEntity goodsColorEntity=GoodsColorService.selectById(colorSeq);
					
					
					newCell = newRow.createCell(2);
					newCell.setCellStyle(style2);
					newCell.setCellValue(meetingGoodsEntity.getFactory());
					
					newCell = newRow.createCell(3);
					newCell.setCellStyle(style2);
					newCell.setCellValue(meetingGoodsEntity.getFactoryGoodId());
					
					newCell = newRow.createCell(4);
					newCell.setCellStyle(style2);
					if(meetingGoodsEntity.getFactoryPrice() != null) {
						newCell.setCellValue(meetingGoodsEntity.getFactoryPrice().doubleValue());
					}
					
					
					
					newCell = newRow.createCell(5);
					newCell.setCellStyle(style2);
					newCell.setCellValue(goodsColorEntity.getName());
					
					newCell = newRow.createCell(6);
					newCell.setCellStyle(style2);
					newCell.setCellValue(totalNum);
					
					//根据货品序号和颜色序号查询各尺寸的件数
					Map<String, Integer>  distributeBox=new HashMap<String, Integer>();
					List<Map<String, Object>> sizeNums=	meetingOrderService.getSizeNum(meetingGoodsSeq, colorSeq);
					for (Map<String, Object> map2 : sizeNums) {
						Integer Size=(Integer) map2.get("Size");//尺码
						Integer SelectNum=(Integer) map2.get("totalNum");//选款数量
						distributeBox.put(Size.toString(), SelectNum);
					}
					
					for (int j = 0; j <=maxSize-minSize; j++) {
						newCell = newRow.createCell(j+7);
						newCell.setCellStyle(style2);
						Integer key=minSize+j;
						Integer selectNum=	distributeBox.get(key.toString());
						if(selectNum!=null) {
							newCell.setCellValue(selectNum);
						}else {
							newCell.setCellValue("");
						}
						
					}
					
				}
				
				
				newRow = sheet2.createRow(currRowIndex2);// 创建行
				newRow.setHeightInPoints(15);//行高度
				newCell = newRow.createCell(0);
				newCell.setCellStyle(style2);
				newCell.setCellValue("合计");
                if(currRowIndex2 > 1) {
	                for(int i = 0;i < maxSize-minSize + 2;i++) {
	                	newCell = newRow.createCell(6 + i);
	                	newCell.setCellStyle(style2);
	                    String colString = CellReference.convertNumToColString(6 + i);
	                    String sumString = "SUM(" + colString + "2:" + colString + currRowIndex2 + ")";
	                    newCell.setCellFormula(sumString);
	                }
                }

                
                
				//按订单生成创建sheet页
				/*  for (MeetingOrderEntity meetingOrderEntity : meetingOrderEntities) {
					  List<MeetingOrderCartEntity> meetingCartEntities =  meetingOrderCartService.getListByOrderSeq(meetingOrderEntity.getSeq());
					  BaseUserEntity baseUserEntity=baseUserService.selectById(meetingOrderEntity.getUserSeq());
					  	// 创建sheet页
						XSSFSheet sheet = wb.createSheet(baseUserEntity.getUserName()+"的"+meetingOrderEntity.getOrderNum() +"配码单");
						// 默认单元格宽度和高度
						sheet.setDefaultColumnWidth(17);
						sheet.setDefaultRowHeightInPoints(16);

						// 默认单元格宽度和高度
						sheet.setDefaultColumnWidth(8);
						sheet.setDefaultRowHeightInPoints(16);

						// 不同列的宽度
						sheet.setColumnWidth(0, 18 * 256);  
						currRowIndex = 0;
						newRow = sheet.createRow(currRowIndex++);
						
						newCell = newRow.createCell(0);
						newCell.setCellStyle(style2);
						newCell.setCellValue("序号");
						
						newCell = newRow.createCell(1);
						newCell.setCellStyle(style2);
						newCell.setCellValue("型号");
						
						newCell = newRow.createCell(2);
						newCell.setCellStyle(style2);
						newCell.setCellValue("客户型号");
						
						newCell = newRow.createCell(3);
						newCell.setCellStyle(style2);
						newCell.setCellValue("颜色");
						
						newCell = newRow.createCell(4);
						newCell.setCellStyle(style2);
						newCell.setCellValue("双数");
						
						
						
						for (int i = 0; i <=maxSize-minSize; i++) {
							newCell = newRow.createCell(i+5);
							newCell.setCellStyle(style2);
							newCell.setCellValue((minSize+i)+"#");
						}
							int seq2=0;
									for (MeetingOrderCartEntity shoppingCartEntity:meetingCartEntities) {
										String userGoodID=shoppingCartEntity.getUserGoodID();//客户型号
										Integer meetingGoodsSeq=shoppingCartEntity.getMeetingGoodsSeq();
										
										MeetingGoodsEntity meetingGoodsEntity=meetingGoodsService.selectById(meetingGoodsSeq);
										Integer meetingShoppingCartSeq=shoppingCartEntity.getSeq();
									//	List<Map<String, Object>> shoppingCartDistributeBoxEntities=meetingOrderCartDistributeBoxService.getSelectNum(meetingShoppingCartSeq);
										List<MeetingOrderCartDistributeBoxEntity> meetingShoppingCartDistributeBoxEntities=meetingOrderCartDistributeBoxService.getMeetingShoppingCartDistributeBoxListByShoppingCartSeq(meetingShoppingCartSeq);
										for (MeetingOrderCartDistributeBoxEntity meetingShoppingCartDistributeBoxEntity : meetingShoppingCartDistributeBoxEntities) {
											Integer meetingShoppingCartDistributeBoxSeq=meetingShoppingCartDistributeBoxEntity.getSeq();
											List<MeetingOrderCartDetailEntity> meetingShoppingCartDetailEntities=meetingOrderCartDetailService.getListByMeetingShoppingCartDistributeBoxSeq(meetingShoppingCartDistributeBoxSeq);
											Integer colorSeq =meetingShoppingCartDistributeBoxEntity.getColorSeq();//颜色seq
											GoodsColorEntity goodsColorEntity=GoodsColorService.selectById(colorSeq);
											newRow = sheet.createRow(currRowIndex++);
											seq2++;
											newCell = newRow.createCell(0);
											newCell.setCellStyle(style2);
											newCell.setCellValue(seq2);
											
											newCell = newRow.createCell(1);
											newCell.setCellStyle(style2);
											newCell.setCellValue(meetingGoodsEntity.getGoodID());
											
											newCell = newRow.createCell(2);
											newCell.setCellStyle(style2);
											newCell.setCellValue(userGoodID);
											
											newCell = newRow.createCell(3);
											newCell.setCellStyle(style2);
											newCell.setCellValue(goodsColorEntity.getName());
											
											
											
											newCell = newRow.createCell(4);
											newCell.setCellStyle(style2);
											newCell.setCellValue(meetingShoppingCartDistributeBoxEntity.getColorTotalNum());
										
											
											
											
											Map<String, Integer>  distributeBox=new HashMap<String, Integer>();
											for (MeetingOrderCartDetailEntity meetingShoppingCartDetailEntity : meetingShoppingCartDetailEntities) {
												Integer Size=meetingShoppingCartDetailEntity.getSize();//尺码
												Integer SelectNum=meetingShoppingCartDetailEntity.getSelectNum();//选款数量
													distributeBox.put(Size.toString(), SelectNum);
											}
											for (int j = 0; j <=maxSize-minSize; j++) {
												newCell = newRow.createCell(j+5);
												newCell.setCellStyle(style2);
												Integer key=minSize+j;
												Integer selectNum=	distributeBox.get(key.toString());
												if(selectNum!=null) {
													newCell.setCellValue(selectNum);
												}else {
													newCell.setCellValue("");
												}
												
											}
											
										}	
								
									}
					  
				  }*/
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

	@GetMapping("getTotalStatistics/{meetingSeq}/{userSeq}/{keywords}/{areaSeq}")
	public R getTotalStatistics(@PathVariable Integer meetingSeq,@PathVariable Integer userSeq,@PathVariable String keywords,@PathVariable Integer areaSeq) {
		try {
			Map<String, Object> result = new HashMap<>(10);
			keywords = "%" + keywords + "%";
			MeetingOrderEntity meetingOrderEntity = meetingOrderService.selectOrderTotalData(getUser().getCompanySeq(), meetingSeq, userSeq, keywords, areaSeq);
			//订单总金额
			BigDecimal totalMoney = meetingOrderEntity.getMoney();
			//订单取消金额
			BigDecimal cancelMoney = productService.selectCancelMoney(getUser().getCompanySeq(), meetingSeq, userSeq, keywords, areaSeq);
			//订单量
			Integer totalOrderNum = meetingOrderEntity.getOrderCount();
			//订单取消量
			Integer cancelOrderNum = 0;
			Wrapper<MeetingOrderEntity> wrapper = new EntityWrapper<>();
			if (meetingSeq != -1) {
				wrapper.eq("Meeting_Seq", meetingSeq);
			}
			if (userSeq != -1) {
				wrapper.eq("User_Seq", userSeq);
			}
			wrapper.eq("Company_Seq", getUser().getCompanySeq());
			List<MeetingOrderEntity> meetingOrderEntities = meetingOrderService.selectList(wrapper);
			for (MeetingOrderEntity meetingOrder : meetingOrderEntities) {
				List<MeetingOrderProductEntity> productEntities = productService.selectCancelOrder(meetingOrder.getSeq());
				if (productEntities != null && productEntities.size() == 1 && productEntities.get(0).getCancel() == 1) {
					cancelOrderNum++;
				}
			}
			wrapper.setSqlSelect("User_Seq").groupBy("User_Seq");
			List<MeetingOrderEntity> meetingOrderCustoms = meetingOrderService.selectList(wrapper);
			Integer customNum = 0;
			if (meetingOrderCustoms != null) {
				customNum = meetingOrderCustoms.size();
			}
			//订单双数
			Integer totalConfirmNum = meetingOrderEntity.getColorNum();
			//订单取消双数
			Integer cancelNum = productService.selectCancelTotal(getUser().getCompanySeq(), meetingSeq, userSeq, keywords, areaSeq);
			//订单款数
			Integer goodNum = meetingOrderEntity.getGoodCount();
			//订单取消款数
			Integer cancelGoodNum = productService.selectCancelGoodNum(getUser().getCompanySeq(), meetingSeq, userSeq, keywords, areaSeq);
			//订单sku数
			Integer skuNum = meetingOrderEntity.getSkuCount();
			//订单取消sku数
			Integer cancelSkuNum = productService.selectCancelSku(getUser().getCompanySeq(), meetingSeq, userSeq, keywords);
			//选款数量
			Integer pickNum = meetingShoppingCartService.selectPickNum(getUser().getCompanySeq(), meetingSeq, userSeq, keywords);
			//实际下单金额
			if (totalMoney != null) {
				if (cancelMoney != null) {
					result.put("totalMoney", totalMoney.subtract(cancelMoney));
				} else {
					result.put("totalMoney", totalMoney);
				}
			} else {
				result.put("totalMoney", "");
			}
			//订单数量
			if (totalOrderNum != null) {
				if (cancelOrderNum != null) {
					result.put("totalOrderNum", totalOrderNum - cancelOrderNum);
				} else {
					result.put("totalOrderNum", totalOrderNum);
				}
			} else {
				result.put("totalOrderNum", "");
			}
			//实际下单数量
			if (totalConfirmNum != null) {
				if (cancelNum != null) {
					result.put("totalConfirmNum", totalConfirmNum - cancelNum);
				} else {
					result.put("totalConfirmNum", totalConfirmNum);
				}
			}else {
				result.put("totalConfirmNum", "");
			}
			//取消订单量
			result.put("cancelOrderNum", cancelOrderNum);
			//取消货品款数
			result.put("cancelGoodNum", cancelGoodNum);
			//订货方数量
			result.put("customNum", customNum);
			//取消订货量
			result.put("cancelNum", cancelNum);
			//取消Sku数
			result.put("cancelSkuNum", cancelSkuNum);
			//总订货量
			if(totalConfirmNum != null) {
				if (pickNum != null) {
					result.put("totalNum", totalConfirmNum + pickNum);
				}else {
					result.put("totalNum", totalConfirmNum);
				}
			}else {
				result.put("totalNum", "");
			}
			//订货款数
			if(goodNum != null) {
				if(cancelGoodNum != null) {
					result.put("confirmGoodNum",goodNum - cancelGoodNum);
				}else {
					result.put("confirmGoodNum",goodNum);
				}
			}else {
				result.put("confirmGoodNum","");
			}
			//订货sku数
			if(skuNum != null) {
				if(cancelSkuNum != null) {
					result.put("confirmSkuNum",skuNum - cancelSkuNum);
				}else {
					result.put("confirmSkuNum",skuNum);
				}
			}else {
				result.put("confirmSkuNum","");
			}
			return R.ok().put("result",result);
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return R.error();
	}

	@GetMapping("/selectMeetingArea")
	public R selectMeetingArea() {
		try {
			Wrapper<MeetingAreaEntity> wrapper = new EntityWrapper<>();
			wrapper.eq("Company_Seq",getUser().getCompanySeq());
			List<MeetingAreaEntity> list = meetingAreaService.selectList(wrapper);
			return R.ok().put("result",list);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return R.error();
	}

	@SuppressWarnings("unchecked")
	@PostMapping("printOrder")
	public R printOrder(@ApiParam("订货会") @RequestParam Integer meetingSeq,
						@ApiParam("订货方") @RequestParam(required = false) Integer meetingUserSeq, HttpServletResponse response) {
		ServletOutputStream out = null;
		HSSFWorkbook wb = null;
		try {
			//查询备注
			OrderAgreementEntity oa = orderAgreementService.getCompanyOrderAgreement(getUser().getCompanySeq());

			List<Integer> userSeqList = meetingOrderService.selectUserSeqList(getUser().getCompanySeq(),meetingSeq,meetingUserSeq);

			List<MeetingOrderEntity> meetingOrderEntities = meetingOrderService.queryOrderByMeetingSeq(meetingSeq,
					meetingUserSeq);
			if (meetingOrderEntities == null || meetingOrderEntities.size() == 0) {
				logger.warn("订货单下载-查询内容为空");
				return R.error();
			}
			Integer companySeq= meetingOrderEntities.get(0).getCompanySeq();
			BaseCompanyEntity baseCompanyEntity= baseCompanyService.selectById(companySeq);

			String filePrefix = baseCompanyEntity.getName();
			response.setCharacterEncoding("UTF-8");
			response.setHeader("content-Type", "application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filePrefix+ "产品订货合同.xls", "UTF-8"));

			// 创建excel2007工作簿
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
			HSSFCellStyle style1 = wb.createCellStyle();
			style1.setAlignment(HorizontalAlignment.CENTER); // 水平居中
			style1.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
			style1.setBorderLeft(BorderStyle.THIN);
			style1.setLeftBorderColor(HSSFColor.BLACK.index);
			style1.setBorderRight(BorderStyle.THIN);
			style1.setRightBorderColor(HSSFColor.BLACK.index);
			style1.setBorderTop(BorderStyle.THIN);
			style1.setTopBorderColor(HSSFColor.BLACK.index);
			style1.setBorderBottom(BorderStyle.THIN);
			style1.setBottomBorderColor(HSSFColor.BLACK.index);

			// 单元格样式2
			HSSFCellStyle style2 = wb.createCellStyle();
			style2.setAlignment(HorizontalAlignment.LEFT); // 水平居中
			style2.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
			style2.setLeftBorderColor(HSSFColor.BLACK.index);
			style2.setRightBorderColor(HSSFColor.BLACK.index);
			style2.setTopBorderColor(HSSFColor.BLACK.index);
			Font font2=wb.createFont();
			font2.setFontHeightInPoints((short) 12);
			font2.setFontName("Courier New ");
			font2.setItalic(false);
			font2.setStrikeout(true);
//			 font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			style2.setFont(font2);

			HSSFCellStyle style3 = wb.createCellStyle();
			style3.setAlignment(HorizontalAlignment.CENTER); // 水平居中
			style3.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
			style3.setBorderLeft(BorderStyle.THIN);
			style3.setLeftBorderColor(HSSFColor.BLACK.index);
			style3.setBorderRight(BorderStyle.THIN);
			style3.setRightBorderColor(HSSFColor.BLACK.index);
			style3.setBorderTop(BorderStyle.THIN);
			style3.setTopBorderColor(HSSFColor.BLACK.index);
			style3.setBorderBottom(BorderStyle.THIN);
			style3.setBottomBorderColor(HSSFColor.BLACK.index);
			Font font1=wb.createFont();
//			 font1.setFontHeightInPoints((short) 14);
			font1.setFontName("Courier New ");
			font1.setItalic(false);
			font1.setStrikeout(true);
			font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			style3.setFont(font1);

			HSSFCellStyle style4 = wb.createCellStyle();
			style4.setAlignment(HorizontalAlignment.RIGHT); // 水平居中
			style4.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
			style4.setLeftBorderColor(HSSFColor.BLACK.index);
			style4.setRightBorderColor(HSSFColor.BLACK.index);
			style4.setTopBorderColor(HSSFColor.BLACK.index);
			Font font4=wb.createFont();
			font4.setFontHeightInPoints((short) 12);
			font4.setFontName("Courier New ");
			font4.setItalic(false);
			font4.setStrikeout(true);
//			 font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			style4.setFont(font4);

			//设置居中:
			contentCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
			contentCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
			for (Integer userSeq : userSeqList) {
				List<MeetingOrderEntity> meetingOrderList = meetingOrderService.getOrderList(getUser().getCompanySeq(),meetingSeq,userSeq);
				BaseUserEntity user = baseUserService.selectById(userSeq);
				// 创建sheet页
				for (int i = 0; i < meetingOrderList.size(); i++) {
					HSSFSheet sheet = wb.createSheet(user.getUserName()+i);
					//默认宽度和高度
					sheet.setDefaultColumnWidth(15);
					sheet.setDefaultRowHeightInPoints(18);
					//不同列的宽度
					sheet.setColumnWidth(0, 18 * 256);
					sheet.setColumnWidth(1, 18 * 256);
					sheet.setColumnWidth(2, 40 * 256);
					MeetingOrderEntity meetingOrderEntity = meetingOrderList.get(i);// 获得行对象
					//判断当前订单是否存在分类
					List<Integer> categoryList=meetingOrderService.selectCategory(meetingOrderEntity.getSeq());
					HSSFRow  newRow;
					HSSFCell newCell;
					Calendar cal=Calendar.getInstance();
					cal.setTime(meetingOrderEntity.getInputTime());
					// 当前操作行,创建新行后+1
					int currRowIndex = 0;
					//标题
					CellRangeAddress cra1 = new CellRangeAddress(currRowIndex, currRowIndex, 0, 12); // 合并单元格
					sheet.addMergedRegion(cra1);
					newRow = sheet.createRow(currRowIndex++);
					newRow.setHeightInPoints(24);
					newCell = newRow.createCell(0);
					newCell.setCellStyle(headCellStyle);
					newCell.setCellValue(filePrefix+ "产品订货合同");
					//第二行
					newRow = sheet.createRow(currRowIndex++);
					newCell = newRow.createCell(0);
					newCell.setCellStyle(style2);
					newCell.setCellValue("订单号：");
					cra1 = new CellRangeAddress(currRowIndex-1, currRowIndex-1, 1, 3); // 合并单元格
					sheet.addMergedRegion(cra1);
					newCell = newRow.createCell(1);
					newCell.setCellStyle(style2);
					newCell.setCellValue(meetingOrderEntity.getOrderNum());
					newCell = newRow.createCell(4);
					newCell.setCellStyle(style2);
					newCell.setCellValue("物流：");
					cra1 = new CellRangeAddress(currRowIndex-1, currRowIndex-1, 5, 6); // 合并单元格
					sheet.addMergedRegion(cra1);
					newCell = newRow.createCell(5);
					newCell.setCellStyle(style2);
					newCell.setCellValue(meetingOrderEntity.getExpressName());

					cra1 = new CellRangeAddress(currRowIndex-1, currRowIndex-1, 7, 12); // 合并单元格
					sheet.addMergedRegion(cra1);
					newCell = newRow.createCell(7);
					newCell.setCellStyle(style4);
					newCell.setCellValue(cal.get(Calendar.YEAR)+"年"+(cal.get(Calendar.MONTH)+1)+"月"+cal.get(Calendar.DAY_OF_MONTH)+"日");
					newCell = newRow.createCell(8);
					newCell.setCellStyle(style4);
					newCell = newRow.createCell(9);
					newCell.setCellStyle(style4);
					newCell = newRow.createCell(10);
					newCell.setCellStyle(style4);
					newCell = newRow.createCell(11);
					newCell.setCellStyle(style4);
					newCell = newRow.createCell(12);
					newCell.setCellStyle(style4);
					//第三行
					newRow = sheet.createRow(currRowIndex++);
					newCell = newRow.createCell(0);
					newCell.setCellStyle(style2);
					newCell.setCellValue("客户：");

					newCell = newRow.createCell(1);
					newCell.setCellStyle(style2);
					BaseUserEntity baseUserEntity=baseUserService.selectById(userSeq);
					newCell.setCellValue(baseUserEntity.getAccountName());

					newCell = newRow.createCell(2);
					newCell.setCellStyle(style2);
					newCell.setCellValue("联系电话：");

					cra1 = new CellRangeAddress(currRowIndex-1, currRowIndex-1, 3, 4); // 合并单元格
					sheet.addMergedRegion(cra1);
					newCell = newRow.createCell(3);
					newCell.setCellStyle(style2);
					newCell.setCellValue(meetingOrderEntity.getTelephone());
					newCell = newRow.createCell(4);
					newCell.setCellStyle(style2);

					cra1 = new CellRangeAddress(currRowIndex-1, currRowIndex-1, 5, 6); // 合并单元格
					sheet.addMergedRegion(cra1);
					newCell = newRow.createCell(5);
					newCell.setCellStyle(style2);
					newCell.setCellValue("订货量：");
					newCell = newRow.createCell(6);
					newCell.setCellStyle(style2);

					//计算当前订单的订货总数量和订货总金额

					MeetingOrderEntity meetingOrderEntity2=meetingOrderService.selectTotalDataByOrderSeq(meetingOrderEntity.getSeq());

					BigDecimal totalMoney = meetingOrderEntity2.getMoney();
					//订单取消金额
					BigDecimal cancelMoney = productService.selectCancelMoneyByOrderSeq(meetingOrderEntity.getSeq());
					if (totalMoney != null) {
						if (cancelMoney != null) {
							totalMoney= totalMoney.subtract(cancelMoney);
						}
					} else {
						totalMoney= new BigDecimal(0);
					}
					//订单双数
					Integer totalConfirmNum = meetingOrderEntity2.getColorNum();
					//订单取消双数
					Integer cancelNum = productService.selectCancelTotalByOrderSeq(meetingOrderEntity.getSeq());
					if (totalConfirmNum != null) {
						if (cancelNum != null) {
							totalConfirmNum=totalConfirmNum - cancelNum;
						}
					}else {
						totalConfirmNum=0;
					}


					cra1 = new CellRangeAddress(currRowIndex-1, currRowIndex-1, 7, 8); // 合并单元格
					sheet.addMergedRegion(cra1);
					newCell = newRow.createCell(7);
					newCell.setCellStyle(style2);
					newCell.setCellValue(totalConfirmNum);
					newCell = newRow.createCell(8);
					newCell.setCellStyle(style2);

					cra1 = new CellRangeAddress(currRowIndex-1, currRowIndex-1, 9, 10); // 合并单元格
					sheet.addMergedRegion(cra1);
					newCell = newRow.createCell(9);
					newCell.setCellStyle(style2);
					newCell.setCellValue("总金额：");
					newCell = newRow.createCell(10);
					newCell.setCellStyle(style2);

					cra1 = new CellRangeAddress(currRowIndex-1, currRowIndex-1, 11, 12); // 合并单元格
					sheet.addMergedRegion(cra1);
					newCell = newRow.createCell(11);
					newCell.setCellStyle(style2);
					newCell.setCellValue(totalMoney.toString());
					newCell = newRow.createCell(12);
					newCell.setCellStyle(style2);
					newRow = sheet.createRow(currRowIndex++);
					//有分类的
					for (Integer categorySeq : categoryList) {
						GoodsCategoryEntity goodsCategoryEntity= goodsCategoryService.selectById(categorySeq);

						//获取当前分类下的尺码范围
						List<Integer> sizeList=meetingOrderService.selectMinMaxSizeByOrder(meetingOrderEntity.getSeq(), categorySeq);
						//第四行
						newRow = sheet.createRow(currRowIndex++);
						cra1 = new CellRangeAddress(currRowIndex-1, currRowIndex,0,0); // 合并单元格
						sheet.addMergedRegion(cra1);
						newCell = newRow.createCell(0);
						newCell.setCellStyle(style1);
						newCell.setCellValue("货号");

						cra1 = new CellRangeAddress(currRowIndex-1, currRowIndex,1,1); // 合并单元格
						sheet.addMergedRegion(cra1);
						newCell = newRow.createCell(1);
						newCell.setCellStyle(style1);
						newCell.setCellValue("客户型号");

						cra1 = new CellRangeAddress(currRowIndex-1, currRowIndex,2,2); // 合并单元格
						sheet.addMergedRegion(cra1);
						newCell = newRow.createCell(2);
						newCell.setCellStyle(style1);
						newCell.setCellValue("颜色");
						cra1 = new CellRangeAddress(currRowIndex-1, currRowIndex,3,3); // 合并单元格
						sheet.addMergedRegion(cra1);
						newCell = newRow.createCell(3);
						newCell.setCellStyle(style1);
						newCell.setCellValue("单价");
						if(sizeList.size()>1) {
							cra1 = new CellRangeAddress(currRowIndex-1, currRowIndex-1,4,3+sizeList.size()); // 合并单元格
							sheet.addMergedRegion(cra1);
							for (int j = 0; j <sizeList.size()-1; j++) {
								newCell = newRow.createCell(5+j);
								newCell.setCellStyle(style1);
							}
						}
						newCell = newRow.createCell(4);
						newCell.setCellStyle(style1);
						newCell.setCellValue("尺码");


						cra1 = new CellRangeAddress(currRowIndex-1, currRowIndex-1,4+sizeList.size(),5+sizeList.size()); // 合并单元格
						sheet.addMergedRegion(cra1);
						newCell = newRow.createCell(4+sizeList.size());
						newCell.setCellStyle(style1);
						newCell.setCellValue("合计");
						newCell = newRow.createCell(5+sizeList.size());
						newCell.setCellStyle(style1);


						//第五行
						newRow = sheet.createRow(currRowIndex++);
						newCell = newRow.createCell(0);
						newCell.setCellStyle(style1);
						newCell = newRow.createCell(1);
						newCell.setCellStyle(style1);
						newCell = newRow.createCell(2);
						newCell.setCellStyle(style1);


						for (int j = 0; j < sizeList.size(); j++) {
							newCell = newRow.createCell(4+j);
							newCell.setCellStyle(style1);
							newCell.setCellValue(sizeList.get(j));
						}
						newCell = newRow.createCell(4+sizeList.size());
						newCell.setCellStyle(style1);
						newCell.setCellValue("数量");
						newCell = newRow.createCell(5+sizeList.size());
						newCell.setCellStyle(style1);
						newCell.setCellValue("金额");
						//分类下的货品信息
						//根据订单seq和分类seq查询货品相关信息
						List<Map<String, Object>> orderDetails=meetingOrderService.getOrderDetailList(meetingOrderEntity.getSeq(), categorySeq);
						Double categoryTotalMoney=0d;
						Integer categoryTotalNum=0;
						Map<String, Integer> maps=new HashMap<String, Integer>();
						for (Map<String, Object> orderDetail : orderDetails) {
							MeetingOrderCartEntity meetingOrderCartEntity=meetingOrderCartService.selectById((int)orderDetail.get("cartSeq"));
							//下一行
							newRow = sheet.createRow(currRowIndex++);
							newCell = newRow.createCell(0);
							newCell.setCellStyle(style1);
							newCell.setCellValue(orderDetail.get("meetingGoodsID").toString());
							newCell = newRow.createCell(1);
							newCell.setCellStyle(style1);
							newCell.setCellValue(meetingOrderCartEntity.getUserGoodID());
							newCell = newRow.createCell(2);
							newCell.setCellStyle(style1);
							newCell.setCellValue(orderDetail.get("colorName").toString());
							newCell = newRow.createCell(3);
							newCell.setCellStyle(style1);
							String Price="0";
							if(orderDetail.get("Price")!=null) {
								Price=orderDetail.get("Price").toString();
							}
							newCell.setCellValue(Price);
							//根据订单seq和货品seq和颜色seq查询
							MeetingOrderCartDistributeBoxEntity meetingOrderCartDistributeBoxEntity=meetingOrderService.getBoxByOrderSeq((int)orderDetail.get("cartSeq"), (int)orderDetail.get("colorSeq"),(int) orderDetail.get("goodsSeq"));
							//根据配码表查询详细的尺码数
							if(meetingOrderCartDistributeBoxEntity!=null) {


								Integer totalNum = 0;
								Integer num = 0;
								List<MeetingOrderCartDetailEntity> meetingOrderCartDetailEntities = meetingOrderService.getOrderCartDetail(meetingOrderCartDistributeBoxEntity.getSeq());
								for(MeetingOrderCartDetailEntity meetingOrderCartDetailEntity : meetingOrderCartDetailEntities) {
									num = 4;
									if(meetingOrderCartDetailEntity != null && meetingOrderCartDetailEntity.getSize() != null) {
										for(Integer size : sizeList) {
											if(size.equals(meetingOrderCartDetailEntity.getSize())) {
												totalNum += meetingOrderCartDetailEntity.getSelectNum();
												newCell = newRow.createCell(num);
												newCell.setCellStyle(style1);
												newCell.setCellValue(meetingOrderCartDetailEntity.getSelectNum());
												if(maps.get(size.toString())==null) {
													maps.put(size.toString(), meetingOrderCartDetailEntity.getSelectNum());
												}else {
													maps.put(size.toString(),maps.get(size.toString())+meetingOrderCartDetailEntity.getSelectNum());
												}
											}
											num++;
										}
									}
								}
								newCell = newRow.createCell(sizeList.size()+4);
								newCell.setCellStyle(style3);
								newCell.setCellValue(totalNum);
								newCell = newRow.createCell(sizeList.size()+5);
								newCell.setCellStyle(style3);
								newCell.setCellValue(totalNum*Double.parseDouble(Price));
								categoryTotalMoney+=totalNum*Double.parseDouble(Price);
								categoryTotalNum+=totalNum;
							}
						}
						newRow = sheet.createRow(currRowIndex++);
						cra1 = new CellRangeAddress(currRowIndex-1, currRowIndex-1, 0, 3); // 合并单元格
						sheet.addMergedRegion(cra1);
						newCell = newRow.createCell(0);
						newCell.setCellStyle(style3);
						if(goodsCategoryEntity!=null) {
							newCell.setCellValue(goodsCategoryEntity.getName()+"小计");
						}else {
							newCell.setCellValue("小计");
						}
						newCell = newRow.createCell(1);
						newCell.setCellStyle(style3);
						newCell = newRow.createCell(2);
						newCell.setCellStyle(style3);
						newCell = newRow.createCell(3);
						newCell.setCellStyle(style3);
						//根据分类和订单获取每个尺码的数量
						for (int j = 0; j < sizeList.size(); j++) {
							newCell = newRow.createCell(4+j);
							newCell.setCellStyle(style3);
							newCell.setCellValue(maps.get(sizeList.get(j).toString()));
						}
						newCell = newRow.createCell(sizeList.size()+4);
						newCell.setCellStyle(style3);
						newCell.setCellValue(categoryTotalNum);
						newCell = newRow.createCell(sizeList.size()+5);
						newCell.setCellStyle(style3);
						newCell.setCellValue(categoryTotalMoney);
						newRow = sheet.createRow(currRowIndex++);
					}
					//无分类的
					List<Integer> sizeList=meetingOrderService.selectMinMaxSizeByOrder(meetingOrderEntity.getSeq(), null);
					if(sizeList.size()>0) {
						newRow = sheet.createRow(currRowIndex++);
						cra1 = new CellRangeAddress(currRowIndex-1, currRowIndex,0,0); // 合并单元格
						sheet.addMergedRegion(cra1);
						newCell = newRow.createCell(0);
						newCell.setCellStyle(style1);
						newCell.setCellValue("货号");
						cra1 = new CellRangeAddress(currRowIndex-1, currRowIndex,1,1); // 合并单元格
						sheet.addMergedRegion(cra1);
						newCell = newRow.createCell(1);
						newCell.setCellStyle(style1);
						newCell.setCellValue("客户型号");
						cra1 = new CellRangeAddress(currRowIndex-1, currRowIndex,2,2); // 合并单元格
						sheet.addMergedRegion(cra1);
						newCell = newRow.createCell(2);
						newCell.setCellStyle(style1);
						newCell.setCellValue("颜色");
						cra1 = new CellRangeAddress(currRowIndex-1, currRowIndex,3,3); // 合并单元格
						sheet.addMergedRegion(cra1);
						newCell = newRow.createCell(3);
						newCell.setCellStyle(style1);
						newCell.setCellValue("单价");
						if(sizeList.size()>1) {
							cra1 = new CellRangeAddress(currRowIndex-1, currRowIndex-1,4,3+sizeList.size()); // 合并单元格
							sheet.addMergedRegion(cra1);
							for (int j = 0; j <sizeList.size()-1; j++) {
								newCell = newRow.createCell(5+j);
								newCell.setCellStyle(style1);
							}
						}
						newCell = newRow.createCell(4);
						newCell.setCellStyle(style1);
						newCell.setCellValue("尺码");


						cra1 = new CellRangeAddress(currRowIndex-1, currRowIndex-1,4+sizeList.size(),5+sizeList.size()); // 合并单元格
						sheet.addMergedRegion(cra1);
						newCell = newRow.createCell(4+sizeList.size());
						newCell.setCellStyle(style1);
						newCell.setCellValue("合计");
						newCell = newRow.createCell(5+sizeList.size());
						newCell.setCellStyle(style1);


						//第五行
						newRow = sheet.createRow(currRowIndex++);
						newCell = newRow.createCell(0);
						newCell.setCellStyle(style1);
						newCell = newRow.createCell(1);
						newCell.setCellStyle(style1);
						newCell = newRow.createCell(2);
						newCell.setCellStyle(style1);
						newCell = newRow.createCell(3);
						newCell.setCellStyle(style1);

						for (int j = 0; j < sizeList.size(); j++) {
							newCell = newRow.createCell(4+j);
							newCell.setCellStyle(style1);
							newCell.setCellValue(sizeList.get(j));
						}
						newCell = newRow.createCell(4+sizeList.size());
						newCell.setCellStyle(style1);
						newCell.setCellValue("数量");
						newCell = newRow.createCell(5+sizeList.size());
						newCell.setCellStyle(style1);
						newCell.setCellValue("金额");
						//分类下的货品信息
						//根据订单seq和分类seq查询货品相关信息
						List<Map<String, Object>> orderDetails=meetingOrderService.getOrderDetailList(meetingOrderEntity.getSeq(), null);
						Double categoryTotalMoney=0d;
						Integer categoryTotalNum=0;
						Map<String, Integer> maps=new HashMap<String, Integer>();
						for (Map<String, Object> orderDetail : orderDetails) {
							//根据cartSeqc查询cart
							MeetingOrderCartEntity meetingOrderCartEntity=meetingOrderCartService.selectById((int)orderDetail.get("cartSeq"));

							//下一行
							newRow = sheet.createRow(currRowIndex++);
							newCell = newRow.createCell(0);
							newCell.setCellStyle(style1);
							newCell.setCellValue(orderDetail.get("meetingGoodsID").toString());
							newCell = newRow.createCell(1);
							newCell.setCellStyle(style1);
							newCell.setCellValue(meetingOrderCartEntity.getUserGoodID());
							newCell = newRow.createCell(2);
							newCell.setCellStyle(style1);
							newCell.setCellValue(orderDetail.get("colorName").toString());
							newCell = newRow.createCell(3);
							newCell.setCellStyle(style1);
							String Price="0";
							if(orderDetail.get("price")!=null) {
								Price=orderDetail.get("price").toString();
							}
							newCell.setCellValue(Price);
							//根据订单seq和货品seq和颜色seq查询
							MeetingOrderCartDistributeBoxEntity meetingOrderCartDistributeBoxEntity=meetingOrderService.getBoxByOrderSeq((int)orderDetail.get("cartSeq"), (int)orderDetail.get("colorSeq"),(int) orderDetail.get("goodsSeq"));
							//根据配码表查询详细的尺码数
							Integer totalNum = 0;
							Integer num = 0;
							List<MeetingOrderCartDetailEntity> meetingOrderCartDetailEntities = meetingOrderService.getOrderCartDetail(meetingOrderCartDistributeBoxEntity.getSeq());
							for(MeetingOrderCartDetailEntity meetingOrderCartDetailEntity : meetingOrderCartDetailEntities) {
								num = 4;
								if(meetingOrderCartDetailEntity != null && meetingOrderCartDetailEntity.getSize() != null) {
									for(Integer size : sizeList) {
										if(size.equals(meetingOrderCartDetailEntity.getSize())) {
											totalNum += meetingOrderCartDetailEntity.getSelectNum();
											newCell = newRow.createCell(num);
											newCell.setCellStyle(style1);
											newCell.setCellValue(meetingOrderCartDetailEntity.getSelectNum());
											if(maps.get(size.toString())==null) {
												maps.put(size.toString(), meetingOrderCartDetailEntity.getSelectNum());
											}else {
												maps.put(size.toString(),maps.get(size.toString())+meetingOrderCartDetailEntity.getSelectNum());
											}
										}
										num++;
									}
								}
							}
							newCell = newRow.createCell(sizeList.size()+4);
							newCell.setCellStyle(style3);
							newCell.setCellValue(totalNum);
							newCell = newRow.createCell(sizeList.size()+5);
							newCell.setCellStyle(style3);
							newCell.setCellValue(totalNum*Double.parseDouble(Price));
							categoryTotalMoney+=totalNum*Double.parseDouble(Price);
							categoryTotalNum+=totalNum;
						}
						newRow = sheet.createRow(currRowIndex++);
						cra1 = new CellRangeAddress(currRowIndex-1, currRowIndex-1, 0, 3); // 合并单元格
						sheet.addMergedRegion(cra1);
						newCell = newRow.createCell(0);
						newCell.setCellStyle(style3);
						newCell.setCellValue("小计");
						newCell = newRow.createCell(1);
						newCell.setCellStyle(style3);
						newCell = newRow.createCell(2);
						newCell.setCellStyle(style3);
						newCell = newRow.createCell(3);
						newCell.setCellStyle(style3);
						//根据分类和订单获取每个尺码的数量
						for (int j = 0; j < sizeList.size(); j++) {
							newCell = newRow.createCell(4+j);
							newCell.setCellStyle(style3);
							newCell.setCellValue(maps.get(sizeList.get(j).toString()));
						}
						newCell = newRow.createCell(sizeList.size()+4);
						newCell.setCellStyle(style3);
						newCell.setCellValue(categoryTotalNum);
						newCell = newRow.createCell(sizeList.size()+5);
						newCell.setCellStyle(style3);
						newCell.setCellValue(categoryTotalMoney);
					}
					newRow = sheet.createRow(currRowIndex++);
					newRow = sheet.createRow(currRowIndex++);
					cra1 = new CellRangeAddress(currRowIndex-1, currRowIndex-1, 0, 4); // 合并单元格
					sheet.addMergedRegion(cra1);
					newCell = newRow.createCell(0);
					newCell.setCellStyle(style2);
					newCell.setCellValue("备注："+oa.getAgreementContent());
					newCell = newRow.createCell(1);
					newCell.setCellStyle(style2);
					newCell = newRow.createCell(2);
					newCell.setCellStyle(style2);
					newCell = newRow.createCell(3);
					newCell.setCellStyle(style2);
					newCell = newRow.createCell(4);
					newCell.setCellStyle(style2);



					cra1 = new CellRangeAddress(currRowIndex-1, currRowIndex-1, 5, 12); // 合并单元格
					sheet.addMergedRegion(cra1);
					newCell = newRow.createCell(5);
					newCell.setCellValue("客户签字：");
					newCell.setCellStyle(style2);
					newCell = newRow.createCell(6);
					newCell.setCellStyle(style2);
					newCell = newRow.createCell(7);
					newCell.setCellStyle(style2);
					newCell = newRow.createCell(8);
					newCell.setCellStyle(style2);
					newCell = newRow.createCell(9);
					newCell.setCellStyle(style2);
					newCell = newRow.createCell(10);
					newCell.setCellStyle(style2);
					newCell = newRow.createCell(11);
					newCell.setCellStyle(style2);
					newCell = newRow.createCell(12);
					newCell.setCellStyle(style2);

				}

			}
			ExcelToHtmlConverter converter = new ExcelToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
			//设置不输出行号(1 2 3...)及列标(A B C...)等
			converter.setOutputColumnHeaders(false);
			converter.setOutputHiddenColumns(false);
			converter.setOutputColumnHeaders(false);
			converter.setOutputLeadingSpacesAsNonBreaking(false);
			converter.setOutputRowNumbers(false);
			converter.processWorkbook(wb);
			StringWriter writer = null;
			String content = null;
			writer = new StringWriter();
			Transformer serializer = TransformerFactory.newInstance().newTransformer();
			serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			serializer.setOutputProperty(OutputKeys.INDENT, "YES");
			serializer.setOutputProperty(OutputKeys.METHOD, "HTML");
			serializer.transform(
					new DOMSource(converter.getDocument()),
					new StreamResult(writer));
//               out = new FileOutputStream(htmlFile);
			content = writer.toString();
			//替换掉Sheet1 Sheet2 Sheet3...

//			out = response.getOutputStream();
//			out.write(content.getBytes("UTF-8"));
//			out.flush();
//			out.close();
//			writer.close();
			return R.ok(content);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.error("定货方订货单: 文件名编码异常");
			return R.error();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("定货方订货单: " + e.getMessage(), e);
			return R.error();
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
				return R.error();
			}

		}

	}
}
