package com.hms.www.config;

import javax.servlet.Filter;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

// AbstractAnnotationConfigDispatcherServletInitializer 상속(Import) 처리 후
// WebConfig에 빨간줄 나오면 add method 실행하여 Override 상속 받기
public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer{

	// add method 클릭하면 3개의 method가 생성되는데
	// 이 3가지는 원래 web.xml에서 설정해주던 내용들과 동일한 것을 알 수 있음
	// => Spring(xml)에서 설정된 내용을 참조해보면 xml에서 설정했던 내용을 Java로 변경하여 셋팅하는 것임
	
	// https://eunoia3jy.tistory.com/38 참고하면 WebConfig를 2가지 방법으로 구성할 수 있음을 참고할 것
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		// Class 객체 생성하여 리턴
		return new Class[] {RootConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// ServletConfig는 javax.servlet.ServletConfig로 클래스가 이미 존재함
		// => 클래스명을 ServletConfiguration로 Class 생성하였음
		// Class 객체 생성하여 리턴
		return new Class[] {ServletConfiguration.class};
	}

	@Override
	protected String[] getServletMappings() {
		// xml 설정에서 getServletMappings은 간단하게 "/" 였음
		// => / 는 HomeController에서 home.jsp로 이동하라는 의미
		// => 즉, home.jsp로 이동하라는 의미
		
		// 배열을 리턴 하기위해선 new 예약어로 객체 생성이 필수
		return new String[] {"/"};
	}

	// Shift + Alt + S : Source 단축키
	// => Override => AbstractDipatcherServletInitializer
	// => getServletFilters, customizeRegistration 체크 후 OK
	@Override
	protected Filter[] getServletFilters() {
		// Filter 설정을 하는 Method
		
		CharacterEncodingFilter encoding = new CharacterEncodingFilter();
		encoding.setEncoding("UTF-8");
		encoding.setForceEncoding(true); // 외부로 내보내는 데이터의 인코딩 설정
		
		// 추가할 필터가 있다면 객체를 생성하고 그 객체를 return하는 Filter 배열에 쉼표 찍고 추가하면 됨
		// e.g.) => return new Filter[] {encoding, encoding2};
		
		return new Filter[] {encoding};
	}

	@Override
	protected void customizeRegistration(Dynamic registration) {
		// 개발자가 직접 추가하는 사용자 설정 Method
		
		/*
			Exception 처리 설정을 사용자 지정으로 만들 수 있음
			=> 예를 들면 404 Exception 페이지를 꾸밀 수 있음
			=> e.g.) GitHub의 404 페이지
			=> 화면의 헤더를 가져와서 404 페이지에서 home 버튼과 로그인 버튼등을
			클릭 할 수 있도록 만들 수 있음
		*/
		
		// 추후 multipartConfig 설정을 추가할 예정
		
		super.customizeRegistration(registration);
	}
}
