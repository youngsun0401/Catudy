package org.woorin.catudy.service;

import java.util.List;

import org.woorin.catudy.model.RoomDTO;

public interface RoomService {
	// 스터디방 개설
	void room_insert(RoomDTO dto);
	// 스터디방 삭제
	// 스터디방 정보 조회
	public RoomDTO getRoom(int room_no);
	// 스터디방 최신순 조회
	public List<RoomDTO> getRooms_latest(int from, int number, boolean onlyopen);

	// 스터디방 정보 수정
	// 참여인원 조회
	// 참여신청 조회
	// 참여자 강퇴
	// 공부 시작
	// 공부 중단
	// 
	// 
}
