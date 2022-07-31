package org.woorin.catudy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woorin.catudy.mapper.MainMapper;

@Service
public class TestServiceImpl implements TestService {
	@Autowired private MainMapper mapper;

	@Override
	public String testMethod(int p_id) {
		return mapper.testSelect(p_id);
	}

}
