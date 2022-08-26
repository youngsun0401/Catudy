package org.woorin.catudy.service;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woorin.catudy.mapper.MainMapper;
import org.woorin.catudy.model.AttendDTO;
import org.woorin.catudy.model.MemberDTO;
import org.woorin.catudy.model.RoomDTO;

/**
 * 멤버의 방에관련된 동작을 할때 사용됩니다.
 * 멤버가 사용하는 기능임으로 멤버의 구현체를 상속받아 사용합니다.
 */
@Service
public class MemberRoomAttendServiceImpl{

    @Autowired
    MainMapper mapper;
    
    // @번 멤버가 @번 방에 가입합니다.
    public int memberRoomJoin(MemberDTO member, RoomDTO room){

        AttendDTO attend = new AttendDTO();
        attend.setAttend_target_room(room.getRoom_no());
        attend.setAttend_target_member(member.getMember_no());
        attend.setAttend_comment("attend_comment_for test");
        return mapper.attend_room_insert(attend);
    }

    // @번 멤버가 @번 방에서 탈퇴합니다.
    public int memberRoomQuit(MemberDTO member, RoomDTO room) {
        AttendDTO attend = new AttendDTO();
        attend.setAttend_target_room(room.getRoom_no());
        attend.setAttend_target_member(member.getMember_no());
        return mapper.attend_room_quit(attend);
    }

    /// 미구현 목록
    // @번 멤버가 참석한 스터디 목록을 가져옵니다.
    public List<RoomDTO> getMyRooms(MemberDTO member) {

        List<AttendDTO> attends = mapper.member_attended_room(member.getMember_no());
        List<RoomDTO> rooms = new ArrayList<RoomDTO>();
        
        for(AttendDTO attend : attends){
            rooms.add(mapper.room_select(attend.getAttend_target_room()));
        }

        // 가져온 방 번호 목록에서 방제를 가져옵니다.
        return rooms;
    }
    // @번 멤버가 참석한 @번 스터디방의 상세정보를 가져옵니다.

}
