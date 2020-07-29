package io.nuite.modules.system.service;

import org.springframework.web.multipart.MultipartFile;

public interface ExcelGoodsService {
    void importExcel(Integer companySeq, Integer brandSeq, MultipartFile excelFile) throws Exception;
    
    void importMeetingGoodsExcel(Integer companySeq,Integer SelectMeetingSeq, MultipartFile excelFile,Integer userSeq) throws Exception;

    /**
     * 导入线上平台货品
     * @param companySeq
     * @param brandSeq
     * @param excelFile
     * @throws Exception
     */
    void importOnlineGoodsExcel(Integer companySeq, Integer brandSeq, MultipartFile excelFile) throws Exception;
}
