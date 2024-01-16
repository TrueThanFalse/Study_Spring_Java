package com.hms.www.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
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
@ComponentScan(basePackages = {"com.hms.www.controller", "com.hms.www.handler"})
/*
 * Spring(xml) 버전의 ComponentScan을 어노테이션으로 처리할 수 있음.
 * @ComponentScan(basePackages = "com.hms.www")
 * @ComponentScan(basePackages = {"com.hms.www.controller", "com.hms.www.handler", ...})
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
		
		// 추후 File upload와 관련된 경로 추가 예정(=> File upload 진행하면서 작성 완료)
		registry.addResourceHandler("/upload/**")
			.addResourceLocations("file:///D:\\HMS\\myProject\\java\\fileUpload\\");
		// file:/// : 이후에 실제 저장되는 절대 경로를 작성해주고 마지막에 \\ 를 추가하여
		// 이후에 들어오는 날짜 폴더를 인식할 수 있도록 만들어 줌.
		// file:/// 접두사를 활용하면 해당 리소스가 로컬 파일 시스템의 파일인 것을 명시적으로 나타내면
		// 프레임워크는 위의 리소스 경로가 파일의 경로임을 인식할 수 있을 것입니다.
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
	
	// 추후 multipartResolver의 설정을 작성할 예정(=> File upload 진행하면서 작성 완료)
	@Bean
	/*
	* @Bean 어노테이션으로 빈 등록을 하면 기본적으로 Bean명이 Method명으로 등록이 됨
	* 하지만 MultipartResolver는 Bean명이 반드시 multipartResolver 로 등록되어야만 함
	* 따라서 MultipartResolver의 Method명은 반드시 multipartResolver로 작성되어야 함
	* 주의사항 : m이 대문자가 아니라 소문자 m
	* 
	* 만약 Method명을 multipartResolver가 아닌 다른 것으로 하고싶다면
	* @Bean 어노테이션에 직접 등록될 Bean명이 multipartResolver라고 인식시켜줘야 한다.
	* e.g.) @Bean(name = "multipartResolver")
	* 
	*/
	public MultipartResolver multipartResolver() {
		StandardServletMultipartResolver multipartResolver =
				new StandardServletMultipartResolver();
		return multipartResolver;
	}
	
}