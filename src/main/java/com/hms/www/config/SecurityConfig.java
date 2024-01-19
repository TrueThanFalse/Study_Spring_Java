package com.hms.www.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.hms.www.security.CustomAuthMemberService;
import com.hms.www.security.LoginFailureHandler;
import com.hms.www.security.LoginSuccessHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	// WebSecurityConfigurerAdapter 상속 받아서 환경 설정하기
	
	// 상속 받은 후 WebConfig의 getRootConfigClasses Method에
	// SecurityConfig.class 추가하여 프로그램 실행 시 
	// WebConfig에서 인식할 수 있도록 설정해야 한다.
	
	// Shift+Alt+S -> Override ->
	// configure(AuthenticationManagerBuilder)와 configure(HttpSecurity)
	// 오버라이드하여 해당 Method들을 설정해야 한다. 
	// 따라서 위 2개의 Method 설정에 필요한 Method와 Class를 생성해야 한다.
	
	// PasswordEncoder
	@Bean
	public PasswordEncoder bcPasswordEncoder() {
		return new BCryptPasswordEncoder();
		// 비밀번호 암호화 객체(PasswordEncoder) 빈 등록
	}
	
	// SuccessHandler
	@Bean
	public AuthenticationSuccessHandler authSuccessHandler() {
		return new LoginSuccessHandler();
		// SuccessHandler 빈 등록
		// 클래스 LoginSuccessHandler 생성 필요 (security 패키지에 생성)
		// => 사용자 커스텀 설정
		// 빨간밑줄에 Create하면 자동으로 AuthenticationSuccessHandler 인터페이스를 잡아줌
	}
	
	// FailureHandler
	@Bean
	public AuthenticationFailureHandler authFailureHandler() {
		return new LoginFailureHandler();
		// FailureHandler 빈 등록
		// 클래스 LoginFailureHandler 생성 필요 (security 패키지에 생성)
		// => 사용자 커스텀 설정
		// 빨간밑줄에 Create하면 자동으로 AuthenticationFailureHandler 인터페이스를 잡아줌
	}
	
	// UserDetails
	@Bean
	public UserDetailsService customUserService() {
		return new CustomAuthMemberService();
		// UserDetails 빈 등록 (사용자 커스텀 설정)
		// 클래스 CustomAuthMemberService 생성 필요 (security 패키지에 생성)
		// => 사용자 커스텀 설정
		// 빨간밑줄에 Create하면 자동으로 UserDetailsService 인터페이스를 잡아줌
	}
	
	///// 위 4개의 Method는 하단 오버라이드 Method를 완성하기 위해 만든 custom Method /////
	
	@Override // WebSecurityConfigurerAdapter 오버라이드
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 인증되는 객체 설정하기
		auth.userDetailsService(customUserService()).passwordEncoder(bcPasswordEncoder());
	}

	@Override // WebSecurityConfigurerAdapter 오버라이드
	protected void configure(HttpSecurity http) throws Exception {
		// 화면에서 설정되는 권한에 따른 주소 맵핑 설정하기
		
		/* 
		* CSRF 공격에 대한 방어 Setting
		* https://devscb.tistory.com/123 참고
		* CSRF 공격을 방어하기 위해선 form 태그에서 토큰을 전송하여
		* Controller에서 토큰을 체크하여 CSRF 공격을 방어할 수 있다.
		* 모든 form 태그를 수정하는 것은 너무 번거로우므로 본 Project에선
		* CSRF를 disable 하겠다.
		*/
		http.csrf().disable();
		
		// 승인 요청 Setting
		/*
		 * antMatchers() : 접근을 허용하는 값(맵핑 주소)
		 * anyRequest() : 나머지 접근 요청
		 * hasRole() : 해당 권한을 가지고 있어야만 접근 가능
		 * permitAll() : 누구나 접근 가능(비로그인 사용자 접근 가능)
		 * authenticated : 인증된 사용자만 접근 가능(로그인 된 상태로만 접근 가능)
		 * authenticated는 로그인 없이 접근하면 로그인 하라고 Error가 발생할 것임
		 */
		http.authorizeRequests()
		.antMatchers("/member/list").hasRole("ADMIN")
		.antMatchers("/", "/board/list", "/board/detail", "/comment/**", "/upload/**", "/resources/**", "/member/register", "/member/login").permitAll()
		.anyRequest().authenticated();
		
		// 커스텀 로그인 Logic 구성하기
		// 주의사항 : Http method 전송 방식이 POST로 전송될 때만 formLogin()이 작동 함
		// 따라서 반드시 form 태그의 method를 POST로 지정해야 한다.
		/*
		 * Http Method 전송 방식이 반드시 post 여야만 
		 * usernameParameter : ID 역할을 하는 매개변수명
		 * passwordParameter : 비밀번호 역할을 하는 매개변수명
		 * loginPage : 로그인 경로
		 * successHandler : 성공하면...
		 * failureHandler : 실패하면...
		 */
		http.formLogin()
		.usernameParameter("email")
		.passwordParameter("pwd")
		.loginPage("/member/login") // 트리거 URL
		.successHandler(authSuccessHandler())
		.failureHandler(authFailureHandler());
		
		// 커스텀 로그아웃 Logic 구성하기
		// 주의사항 : HTTP method 전송 방식이 POST로 전송될 때만 logout()이 작동 함
		// 따라서 반드시 form 태그의 method를 POST로 지정해야 한다.
		// 참고사항 : logoutUrl은 승인 요청 Setting에서 authenticated()로 보호되어야 한다.
		http.logout()
		.logoutUrl("/member/logout") // 트리거 URL
		.invalidateHttpSession(true) // HTTP session 무효화
		.deleteCookies("JSESSIONID") // 쿠키 삭제
		.logoutSuccessUrl("/"); // redirect 경로
	}
}
