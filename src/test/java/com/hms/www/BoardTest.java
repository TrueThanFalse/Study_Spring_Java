package com.hms.www;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hms.www.domain.BoardVO;
import com.hms.www.repository.BoardDAO;

import lombok.extern.slf4j.Slf4j;

@ContextConfiguration(classes = {com.hms.www.config.RootConfig.class})
// RootConfig의 환경 설정을 적용시키는 어노테이션
@RunWith(SpringJUnit4ClassRunner.class)
public class BoardTest {

	@Inject
	private BoardDAO bdao;
	
	@Test
	// @Test : JUnit framework와 연결
	public void insertBoard() {
		// 테스트할 Method 내부를 클릭하고 우클릭 -> Run as -> JUnit Test
		// => 선택한 하나의 Method만 Test 실행됨
		// Project명을 우클릭해서 JUnit Test을 실행하면 모든 @Test가 실행됨
		for(int i=0; i<177; i++) {
			BoardVO bvo = new BoardVO();
			bvo.setTitle("Test"+i);
			bvo.setWriter("tester");
			bvo.setContent("Test"+i);
			bdao.insert(bvo);
		}
	}
}