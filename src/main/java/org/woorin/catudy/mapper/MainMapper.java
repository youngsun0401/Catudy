package org.woorin.catudy.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MainMapper {

	String testSelect(int p_id);

}
