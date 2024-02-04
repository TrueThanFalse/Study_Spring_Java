package com.hms.www.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@MapperScan(basePackages = {"com.hms.www.repository"})
@ComponentScan(basePackages = {"com.hms.www.service", "com.hms.www.exception"})
@EnableTransactionManagement // 트랜잭션 설정
@Configuration
// WebConfig, ServletConfiguration처럼 상속 받지 않으므로 RootConfig가
// 설정파일 인것을 @Configuration 어노테이션으로 인식 시켜줘야 함
@EnableScheduling
// Scheduler 기능 활성화
public class RootConfig {
	
	// RootConfig는 상속받거나 인터페이스 없이 처음부터 생으로 작성해야 함
	// RootConfig는 DB와 관련된 설정임
	
	// xml 버전과 비교하여 hikariCP & log4jdbc-log4j2를 추가하여 사용할 것이므로
	// 추가되는 로직을 잘 공부해보자
	
	// xml 버전에서는 dataSource, sqlSessionFactory, mybatis-spring:scan을 설정했고
	// namespace에서 5가지를 체크해 줬었는데 이 5가지 체크하는 것은 ServletConfiguration에서
	// @EnableWebMvc 어노테이션이 그 역할을 대신 해주고 있다.
	// 따라서 RootConfig에서는 dataSource, sqlSessionFactory, mybatis-spring:scan에
	// 대한 로직을 처리해주면 됨
	
	// import org.springframework.context.ApplicationContext;
	@Autowired
	ApplicationContext applicationContext; // Mapper 위치를 applicationContext에 알려줘야 한다.
	
	// 1. dataSource (javax.sql) 만들기
	@Bean
	public DataSource dataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		// HikariCP를 사용하기 위해서는 DB와의 연결을 직접 연결하지 않고
		// HikariCP에 그 역할을 위임하여 DB와 연결해야 함
		// 그리고 DB와 관련된 log를 보기 위해서 Driver와 URL을 log4jdbc-log4j2가
		// 사용할 수 있도록 로직을 구현해야 함

		// log4jdbc-log4j2란? Spring에서 SQL문을 실행한 로그를 직관적으로
		// 볼 수 있도록 해주는 라이브러리
		
		// xml 버전에서의 dataSource의 property 4가지를 설정해줘야 함
		// 이 4가지 설정은 기본적으로 필수로 셋팅임
		hikariConfig.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
		hikariConfig.setJdbcUrl("jdbc:log4jdbc:mysql://localhost:3306/mywebdb");
		hikariConfig.setUsername("mywebUser");
		hikariConfig.setPassword("mysql");
		
		// HikariCP 기본 설정 시작
		hikariConfig.setMaximumPoolSize(5);
		// 최대 연결 개수 설정
		hikariConfig.setMinimumIdle(5);
		// 최소 유휴 연결 개수 설정(반드시 MaximumPoolSize와 동일한 값 입력)
		hikariConfig.setConnectionTestQuery("SELECT now()");
		// 연결 확인 test 쿼리문(SELECT now()는 일반적으로 사용하는 test 구문)
		hikariConfig.setPoolName("springHikariCP");
		// HikariCP 기본 설정 끝
		
		// HikariCP 최적화 설정 시작 (HikariCP 공식 홈페이지 참고(GitHub) 혹은 하단 URL 참고)
		// https://velog.io/@mohai2618/%EC%84%B1%EB%8A%A5%EC%9D%84-%EC%B5%9C%EC%A0%81%ED%99%94-%ED%95%B4%EB%B3%B4%EC%9E%90-HikariCP-%ED%8E%B8
		hikariConfig.addDataSourceProperty("dataSource.cachePrepStmts", "true");
		// cachePrepStmts : cache 사용 여부 설정 (cache memory를 사용하면 처리 속도가 향상됨)
		hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSize", "250");
		// MySQL 연결 드라이버당 cache statement의 수에 대한 설정 (250~500 사이 값 권장)
		hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit", "true");
		// 연결당 캐싱할 preparedStatement의 개수 지정 설정(default 256)(true : 기본값 자동 설정)
		hikariConfig.addDataSourceProperty("useServerPrepStmts", "true");
		// 서버에 최신 이슈가 있을 경우 지원받을 것인지 설정 (생략 가능한 설정)
		
		// Config를 담을 DataSource 객체 생성 후 return
		HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
		
		return hikariDataSource;
	}
	
	// 2. sqlSessionFactory 만들기
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		// DB에 접속하는 User명, 비밀번호 등 오류가 있을 수 있으므로 throws 설정 필요
		
		SqlSessionFactoryBean SSFB = new SqlSessionFactoryBean();
		
		SSFB.setDataSource(dataSource());
		
		SSFB.setMapperLocations(applicationContext.getResources("classpath:/mappers/*.xml"));
		// applicationContext의 기본 경로(classpath)는 src/main/resources 이다.
		
		// alias 설정 (생략 가능한 설정)
		SSFB.setConfigLocation(applicationContext.getResource("classpath:/MybatisConfig.xml"));
		// MybatisConfig.xml을 만드는 이유는 RootConfig.java에서 설정할 수 없는 것을 설정하기 위함이다.
		
		return SSFB.getObject();
	}
	
	// 3. 트랜잭션 매니저 빈 설정
	@Bean
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}
	 
}
