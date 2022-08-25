package org.woorin.catudy.service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woorin.catudy.mapper.MemberRoomAttendanceMapper;
import org.woorin.catudy.model.MemberRoomAttendanceDTO;

@Service
public class RoomAttendanceImpl implements RoomAttendance{

    @Autowired
    MemberRoomAttendanceMapper mapper;

    private final int SUCCESS = 0;

    @Override
    public List<MemberRoomAttendanceDTO> getMemberRoomAttendance(int room_no, int member_no) {
        return mapper.getMemberRoomAttendance(room_no, member_no);
    }

    /**
     * 가장 최근의 기록을 가져옵니다.
     * @param list
     * @return
     */
    public MemberRoomAttendanceDTO getCurrentMemberRoomAttended(List<MemberRoomAttendanceDTO> list) {

        MemberRoomAttendanceDTO currentLog = null;
        for(MemberRoomAttendanceDTO item : list) {
            if (currentLog == null) { currentLog = item; }
            if (currentLog.getAttendance_study_start_time().before(item.getAttendance_study_start_time())) {
                currentLog = item;
            }
        }

        return currentLog;
    }

    @Override
    public int memberRoomAttendCleaned(int room_no, int member_no) {
        // 출석된 데이터가 있는지 확인
        // 없으면 출석, 0 반환.(성공)
        // 출석 돼 있고, 종료 안돼있으면 불가능 반환(실패)
        boolean study_started = true;

        ArrayList<MemberRoomAttendanceDTO> attendMemberList = new ArrayList<MemberRoomAttendanceDTO>();
        // room_no번 방의 member_no번 사용자의 출석정보를 불러옵니다.
        attendMemberList.addAll(getMemberRoomAttendance(room_no, member_no));

        // 종료된/종료되지 않은 기록을 가져옵니다.
        // 질문: 종료 누르지 않고 그냥 가버리면? 대응방안을 생각해 두자.
        // ArrayList<MemberRoomAttendanceDTO> openedList = new ArrayList<MemberRoomAttendanceDTO>();
        for (MemberRoomAttendanceDTO dto : attendMemberList){
            if (dto.getAttendance_study_end_time() == null){
                // 종료된 기록
            } else {
                // 종료되지 않은 기록
                // openedList.add(dto);
                study_started = false;
                break;
            }
        }

        if(study_started){
            // 종료과정을 온전히 거친 사용자 입니다.
            return 0;
        } else {
            // 종료되지 않은 기록이 있다 (참가불가)
            return 1;
        }
    }

    /**
     * 사용 시 주의사항
     * studyStart, studyEnd 서비스는 회원이 중간에 다른방에 가지않고 사용한 것을 가정하에 만들어 졌습니다.
     * 따라서, 다른방에서 종료를 눌렀을 때 오류가 날 가능성이 있습니다.
     * 나중에 같은방에서 studyEnd를 실행했는지 검증하는 과정을 만들어야 합니다.
     */

    @Override
    public int studyEnd(int room_no, int member_no) {

        // 종료와 동시에 공부한 시간을 기록합니다.
        List<MemberRoomAttendanceDTO> attends = getMemberRoomAttendance(room_no, member_no);
        // 마지막 기록(최근)기록을 가져옵니다.
        MemberRoomAttendanceDTO memberLog = getCurrentMemberRoomAttended(attends);

        // 출석부터 종료까지의 시간을 구합니다.
        long time_a = memberLog.getAttendance_study_start_time().getTime();
        long time_b = new Date().getTime();
        long diff = (time_b - time_a) / 1000;

        int hour = (int) (diff / 3600);
		int min = ((int)diff % 3600) / 60;
		int sec = (int) (diff % 60);

        LocalTime time = LocalTime.of(hour, min, sec);
		Time study_time = Time.valueOf(time);

        mapper.studyEnd(room_no, member_no, study_time);
        return 0;
    }

    @Override
    public int studyStart(int room_no, int member_no) {
        if(memberRoomAttendCleaned(room_no, member_no) == SUCCESS){
            mapper.studyStart(room_no, member_no);
        }
        return 0;

        // return -1;
    }

}
    
