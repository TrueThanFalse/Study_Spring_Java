package com.hms.www.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityInitalizer extends AbstractSecurityWebApplicationInitializer{

	/*
	 * AbstractSecurityWebApplicationInitializer를 상속 받아야
	 * Security 관련 필터 기능이 활성화 된다.
	 * 따라서 SecurityInitalizer 클래스에서 상속만 받은 후
	 * 실질적인 Security 설정은 SecurityConfig에서 Setting 한다.
	 */
}
