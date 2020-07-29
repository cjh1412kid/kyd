package io.nuite.modules.system.dao;

import io.nuite.modules.system.entity.SysFactoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface SystemFactoryDao {
    List<SysFactoryEntity> queryFactoryPage(@Param("limit") int limit, @Param("page") int page, @Param("keywords") Object keywords);

    int queryFactoryTotal(@Param("keywords") Object keywords);

    SysFactoryEntity queryFactoryOne(Long seq);

    void updateOpUserJurisdiction(@Param("opSeq") Long seq,
                                  @Param("effectiveDate") Date date,
                                  @Param("userNumber") int number);

    void insertOpUserJurisdiction(@Param("userSeq") Integer userSeq,
                                  @Param("effectiveDate") Date date,
                                  @Param("userNumber") int number);

    void delOpUserJurisdiction(@Param("userSeq") Integer userSeq);


    void updateOlsUserJurisdiction(@Param("olsSeq") Long seq,
                                   @Param("effectiveDate") Date date);

    void insertOlsUserJurisdiction(@Param("userSeq") Integer userSeq,
                                   @Param("effectiveDate") Date date);

    void delOlsUserJurisdiction(@Param("userSeq") Integer userSeq);
}
