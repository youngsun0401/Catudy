package org.woorin.catudy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woorin.catudy.mapper.MainMapper;

@Service
public class DocumentServiceImpl implements DocumentService {
	@Autowired private MainMapper mapper;

}
