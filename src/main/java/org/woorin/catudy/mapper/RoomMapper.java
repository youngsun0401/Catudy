package org.woorin.catudy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.woorin.catudy.model.MemberDTO;
import org.woorin.catudy.model.RoomDTO;

@Mapper
public interface RoomMapper {
    List<RoomDTO> member_joined_room(MemberDTO dto);
}
