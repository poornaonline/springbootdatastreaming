package com.example.demo.service;

import com.example.demo.model.UserDetail;
import com.example.demo.repository.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Transactional
@Service
public class SampleService {

	@Autowired
	private SampleRepository sampleRepository;

	@Transactional(readOnly = true)
	public Stream<UserDetail> getAll() {
		return sampleRepository.getAll();
	}
}
