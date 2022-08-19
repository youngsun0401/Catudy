package org.woorin.catudy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woorin.catudy.mapper.MainMapper;
import org.woorin.catudy.model.RoomDTO;

@Service
public class RoomServiceImpl implements RoomService {
	@Autowired private MainMapper mapper;

	// 스터디방 개설
	@Override
	public void room_insert(RoomDTO dto) {
		mapper.room_insert(dto);
	}

	// 특정 스터디방 정보
	@Override
	public RoomDTO getRoom(int room_no) {
		return mapper.room_select(room_no);
	}

	// 스터디방 목록 (최신순 어디부터, 몇 개, 열린방만 선택할 것인가)
	@Override
	public List<RoomDTO> getRooms_latest(int from, int number, boolean onlyopen) {
		if (onlyopen) {// 열린 방만 검색
			return mapper.room_select_orderbyNo_onlyOpen(from, number);
		}else {// 최신순 무조건 검색
			return mapper.room_select_orderbyNo(from, number);
		}
	}

}
