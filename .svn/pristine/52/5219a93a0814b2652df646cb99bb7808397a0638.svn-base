package io.nuite.modules.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.nuite.modules.order_platform_app.entity.MeetingOrderCartDetailEntity;
import io.nuite.modules.order_platform_app.entity.MeetingOrderCartDistributeBoxEntity;
import io.nuite.modules.system.entity.MeetingOrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订货会订单
 *
 * @author yangchuang
 * @email ${email}
 * @date 2019-04-17 13:42:11
 */
@Mapper
public interface MeetingOrderDao extends BaseMapper<MeetingOrderEntity> {
    
    /**
     * 按年份、订货会、定货方动态查询订货会订单
     *
     * @param page
     * @param companySeq
     * @param year
     * @param meetingSeq
     * @param meetingUserSeq
     * @return
     */
    List<MeetingOrderEntity> selectPageByCompanySeq(Page page,
                                                    @Param("companySeq") Integer companySeq,
                                                    @Param("year") Integer year,
                                                    @Param("meetingSeq") Integer meetingSeq,
                                                    @Param("meetingUserSeq") Integer meetingUserSeq,
                                                    @Param("sortColumn") String sortColumn,
                                                    @Param("sortType") String sortType
    );
    
    /**
     * 查询订单中所有存在的年份
     *
     * @param companySeq
     * @return
     */
    List<Integer> selectOrderExistYears(@Param("companySeq") Integer companySeq);
    
    /**
     * 查询订单中所有存在的指定年份订货会
     *
     * @param companySeq
     * @return
     */
    List<Map<String, Object>> selectOrderExistMeetings(@Param("companySeq") Integer companySeq, @Param("year") Integer year);
    
    /**
     * 查询订单中指定年份订货会的所有定货方
     *
     * @param companySeq
     * @param meetingSeq
     * @return
     */
    List<Map<String, Object>> selectOrderExistMeetingUsers(@Param("companySeq") Integer companySeq,
                                                           @Param("meetingSeq") Integer meetingSeq);
    
    /**
     * 根据订货会和定货方查询所有订单
     *
     * @param meetingSeq
     * @param meetingUserSeq
     * @return
     */
    List<MeetingOrderEntity> selectOrderByMeetingSeq(@Param("meetingSeq") Integer meetingSeq,
                                                     @Param("meetingUserSeq") Integer meetingUserSeq);


    /**
     * 获取当前订货会下订货方序号列表
     * @param map
     * @return
     * @throws Exception
     */
    List<Integer> selectUserSeqList(Map<String,Object> map) throws Exception;

    /**
     * 根据订货方获取订单详情
     * @param map
     * @return
     * @throws Exception
     */
    List<MeetingOrderEntity> selectCustomMeetingOrder(Map<String,Object> map) throws Exception;

    /**
     * 获取配码详情
     * @param map
     * @return
     * @throws Exception
     */
    List<MeetingOrderCartDetailEntity> getOrderCartDetail(Map<String,Object> map) throws Exception;
    
    Integer totalOrderByCategorySeq(@Param("categorySeq")Integer categorySeq,@Param("meetingSeq")Integer meetingSeq);


    List<Map<String, Object>> getCategoryRank(@Param("categorySeq")Integer categorySeq,@Param("meetingSeq")Integer meetingSeq);

    /**
     * 订单汇总
     * @param map
     * @return
     * @throws Exception
     */
    MeetingOrderEntity selectOrderTotalData(Map<String,Object> map) throws Exception;
    
    List<Map<String, Object>> getCategoryRankByArea(@Param("categorySeq")Integer categorySeq,@Param("meetingSeq")Integer meetingSeq,@Param("areaSeq")Integer areaSeq);

    List<Map<String, Object>> getCategoryRankByUser(@Param("categorySeq")Integer categorySeq,@Param("meetingSeq")Integer meetingSeq,@Param("userSeq")Integer userSeq);

    /**
     * 获取订货会尺码列表
     * @param map
     * @return
     * @throws Exception
     */
    List<Integer> selectMinMaxSize(Map<String,Object> map) throws Exception;
    
    /**
     * 获取订货会订单分类尺码
     * @param map
     * @return
     * @throws Exception
     */
    List<Integer> selectMinMaxSizeByOrder(Map<String,Object> map) throws Exception;
    
    /**
     * 获取订货会用户获取分类
     * @param meetingSeq
     * @param userSeq
     * @return
     */
    List<Integer> selectCategory(@Param("orderSeq")Integer orderSeq);
    
    /**
     * 订单统计分析列表
     */
    List<Map<String, Object>> orderStatisticsList(@Param("companySeq")Integer companySeq, 
			@Param("userSeqs")String userSeqs, 
			@Param("meetingSeq")Integer meetingSeq);

    
    MeetingOrderEntity selectTotalDataByOrderSeq(Integer orderSeq) throws Exception;
    
    List<Map<String, Object>> getOrderDetailList(@Param("orderSeq")Integer orderSeq,@Param("categorySeq")Integer categorySeq);

    MeetingOrderCartDistributeBoxEntity getBoxByOrderSeq(@Param("cartSeq")Integer cartSeq,@Param("colorSeq") Integer colorSeq,@Param("goodsSeq") Integer goodsSeq);		


    /**
     * 订单列表(订货会订单和订货平台订单合并)
     * @param map
     * @param page
     * @return
     * @throws Exception
     */
    List<MeetingOrderEntity> getOrderList(Map<String,Object> map,Page<MeetingOrderEntity> page) throws Exception;

}
