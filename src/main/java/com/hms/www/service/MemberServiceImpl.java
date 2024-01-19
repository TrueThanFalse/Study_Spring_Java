package com.hms.www.service;

import org.springframework.stereotype.Service;

import com.hms.www.repository.MemberDAO;
import com.hms.www.security.MemberVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

	private final MemberDAO mdao;

	@Override
	public int signUP(MemberVO mvo) {
		return mdao.insert(mvo);
	} 
}
