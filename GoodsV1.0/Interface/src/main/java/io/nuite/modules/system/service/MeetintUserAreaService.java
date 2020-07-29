package io.nuite.modules.system.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

import io.nuite.modules.system.entity.MeetintUserAreaEntity;


public interface MeetintUserAreaService extends IService<MeetintUserAreaEntity> {

	public void getAllUserByMeetingSeq(Integer areaSeq,List<Integer> seqs, List<Integer> allSeqs);
}

