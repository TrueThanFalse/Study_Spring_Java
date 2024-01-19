package com.hms.www.handler;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hms.www.domain.FileVO;
import com.hms.www.repository.FileDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class FileSweeper {

	// @Component : Bean 목록에 Class 단위로 등록 가능
	// @Bean : Bean 목록에 Method 단위로 등록 가능 (Class 단위 등록 불가능)
	
	private final String BASE_PATH = "D:\\HMS\\myProject\\java\\fileUpload\\";
	
	private final FileDAO fdao;
	
	// cron 표현식 : https://zamezzz.tistory.com/197 참고
	// 초, 분, 시, 일, 월, 요일, 연도(연도는 생략 가능)
	@Scheduled(cron = "50 59 23 * * *")
	public void fileSweeper() {
		log.info("@@@@@ FileSweeper Running Start!!! @@@ {}", LocalDateTime.now());
		
		// file Table에 등록되어 있는 모든 fvo 목록 가져오기
		List<FileVO> dbList = fdao.selectAllFileList();
		
		// dbList의 fvo들의 경로를 String으로 추출
		List<String> currentDBFilesPath = new ArrayList<String>();
		
		for(FileVO fvo : dbList) {
			String filePath = fvo.getSaveDir() + File.separator + fvo.getUuid();
			String fileName = fvo.getFileName();
			
			// 로컬디스크에 저장된 파일의 실질적인 경로를 List에 추가
			currentDBFilesPath.add(BASE_PATH + filePath + "_" + fileName);
			
			// 만약 file이 이미지 파일이면 썸네일 파일도 삭제하기 위하여 List에 추가해야 함
			if(fvo.getFileType() > 0) {
				currentDBFilesPath.add(BASE_PATH + filePath + "_th_" + fileName);
			}
		}
		
		// 금일 날짜 폴더 구조 만들기
		LocalDate now = LocalDate.now();
		String today = now.toString(); // 2024-01-19
		today = today.replace("-", File.separator); // 2024\\01\\19
		
		File directory = Paths.get(BASE_PATH + today).toFile(); // 금일 날짜 폴더 경로
		// 오늘 날짜 폴더 내부에 존재하는 모든 파일의 목록 (경로를 기반으로 저장되어 있는 파일 검색)
		File[] todayAllFileObjects = directory.listFiles();
		if(todayAllFileObjects == null) {
			log.info("@@@@@ FileSweeper Running Finish!!! @@@ {}", LocalDateTime.now());
			log.info("@@@@@ 금일 DB에서 삭제된 File이 없습니다.");
			return;
		}
		
		// 로컬디스크에 실제 저장되어 있는 파일들과 DB의 file Table에 등록되어 있는 파일들을 비교하여
		// DB에 등록되어 있지 않은 로컬디스크 파일들을 모두 삭제하기
		for(File file : todayAllFileObjects) {
			String storedFilePath = file.toPath().toString();
			
			if(!currentDBFilesPath.contains(storedFilePath)) {
				// DB에는 없는데 로컬디스크에는 있다면...
				log.info("@@@@@ Delete File @@@ {}", storedFilePath);
				file.delete(); // 로컬디스크에서 해당 파일을 삭제
			}
		}

		log.info("@@@@@ FileSweeper Running Finish!!! @@@ {}", LocalDateTime.now());
		return;
	}
}
