package com.hms.www.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Component
@Slf4j
public class LoginFailureHandler implements AuthenticationFailureHandler {

	private String authEmail;
	private String errorMessage;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		// TODO Auto-generated method stub

		// request.getParameter("email") => SecurityConfig의 http.formLogin().usernameParameter("email")
		setAuthEmail(request.getParameter("email"));
		
		// Exception 발생하면 Exception 메시지를 저장하고 MemberController로 전송
		if(exception instanceof BadCredentialsException ||
				exception instanceof InternalAuthenticationServiceException) {
			
			setErrorMessage(exception.getMessage().toString());
			log.info("@@@@@ errMsg @@@ " + errorMessage);
			
			request.setAttribute("email", getAuthEmail());
			request.setAttribute("errMsg", getErrorMessage());
			
			// MemberController로 request와 response의 데이터를 가지고 전송 (post 방식)
			request.getRequestDispatcher("/member/loginError").forward(request, response);			
		}
	}

}
