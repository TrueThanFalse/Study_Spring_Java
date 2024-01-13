package com.hms.www.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

// Spring(xml) 버전을 보면 <resources mapping>, <context:component-scan>, <annotation-driven>,
// <beans:bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
// 위 4가지에 대한 설정을 만들어야 함

@EnableWebMvc // Spring(xml) 버전의 <annotation-driven>과 같은 효과가 있는 어노테이션
@ComponentScan(basePackages = "com.hms.www")
/*
 * Spring(xml) 버전의 ComponentScan을 어노테이션으로 처리할 수 있음.
 * @ComponentScan(basePackages = {"com.myweb.www.controller", "com.myweb.www.handler", ...})
 * => 위와 같이 특정 Package를 지정하여 조회할 수 있다.
 * => 실무에서는 패키지가 수십개 이상이므로 지금처럼 모든 패키지를 조회하면 처리 시간이 오래 걸리므로
 * 		특정 Package를 조회하여 처리 시간을 단축시켜야 함
 */
public class ServletConfiguration implements WebMvcConfigurer{
	// 반드시 WebMvcConfigurer를 implements 해야됨
	
	// Shift + Alt + S : Source 단축키
	// => Override => WebMvcConfigurer
	// => addResourceHandlers, configureViewResolvers 체크 후 OK

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// resources 경로 설정
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		
		// 추후 File upload와 관련된 경로 추가 예정
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		// View 경로 설정
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setViewClass(JstlView.class); // Jstl을 볼 수 있는 클래스
		// registry에 저장
		registry.viewResolver(viewResolver);
	}
	
	// 추후 multipartResolver의 설정을 작성할 예정
	
}