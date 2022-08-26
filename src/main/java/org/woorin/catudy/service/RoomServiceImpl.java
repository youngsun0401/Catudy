package org.woorin.catudy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woorin.catudy.mapper.MainMapper;
import org.woorin.catudy.model.AttendDTO;
import org.woorin.catudy.model.RoomDTO;

@Service
public class RoomServiceImpl implements RoomService {
	@Autowired private MainMapper mapper;

	// 스터디방 개설
	@Override
	public void room_insert(RoomDTO dto, AttendDTO ato) {

		mapper.room_insert(dto);
		ato.setAttend_target_room(dto.getRoom_no());
		mapper.attend_room_insert(ato);
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
	// 스터디방 목록 조회
	@Override
	public List<RoomDTO> room_list() {
		return mapper.room_list();
	}


	@Override
	public String roomList(String room_title) {
		mapper.roomList(room_title);
		return room_title;
	}



}
