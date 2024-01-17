package com.hms.www.handler;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.hms.www.domain.FileVO;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Slf4j
@Component
// @Component : 개발자가 직접 만든 클래스를 빈으로 등록하는 어노테이션
public class FileHandler {

	private final String UP_DIR = "D:\\HMS\\myProject\\java\\fileUpload";
	
	public List<FileVO> uploadFiles(MultipartFile[] files) {
		// ArrayList 생성
		List<FileVO> flist = new ArrayList<FileVO>();
		
		// FileHandler에서 동작하는 Logic
		// FileVO 생성, 파일을 저장 경로에 저장, 이미지 파일이면 썸네일 생성 및 저장
		// 각 날짜를 폴더로 생성하여 그날그날 업로드한 파일을 관리하도록 만들 것임
		
		LocalDate date = LocalDate.now(); // 2024-01-17
		String today = date.toString(); // "2024-01-17"
		today = today.replace("-", File.separator);
		// File.separator는 운영체제에 따라 다르다.
		
		File folders = new File(UP_DIR, today);
		// folders => D:\\HMS\\myProject\\java\\fileUpload\\2024\\01\\17
		
		// 금일 날짜 폴더 생성하기
		// exists() : 존재 유무 확인 Method => 존재하면 true, 없으면 false
		if(!folders.exists()) {
			// 금일 날짜의 폴더가 존재하지 않는다면...
			folders.mkdirs(); // 2024\\01\\17 => 여러개의 폴더를 동시에 생성
			// 왜 여러개의 폴더를 생성해야 하는가? 기본 경로는
			// 날짜 폴더는 연도가 바뀌면 2025년 폴더도 새로 생성되어야 하므로
			// 여러개의 폴더를 동시에 생성해야 됨
			// 참고 : mkdir() => 하나의 폴더만 생성
		}
		
		// files 배열 내부의 fileVO 객체 하나에 대한 세팅 및 flist에 추가하기
		for(MultipartFile file : files) {
			FileVO fvo = new FileVO();
			fvo.setSaveDir(today);
			fvo.setFileSize(file.getSize());
			
			String originalFileName = file.getOriginalFilename();
			String fileName = originalFileName.substring(originalFileName.lastIndexOf(File.separator)+1);
			fvo.setFileName(fileName);
			log.info("@@@@@ fileName @@@ " + fileName);
			
			UUID getUUID = UUID.randomUUID();
			String uuid = getUUID.toString();
			fvo.setUuid(uuid);
			// ----- 기본 fvo 세팅 완료 -----
			
			// fvo의 bno는 현 시점에서 세팅할 수 없다.
			// 왜? upload 할 파일들이 저장될 bvo는 아직 DB에 저장되지 않았으므로
			// bno가 생성되지 않았음(bno는 auto_increment)
			
			// 로컬디스크에 저장할 파일 객체 만들기
			String fullFileName = uuid + "_" + fileName;
			File storeFile = new File(folders, fullFileName);
			/*
			 * 파일이 실제로 저장되기 위해선 첫 경로부터 파일의 확장자명까지 전부 세팅되어야 함
			 * e.g.) D:\\HMS\\myProject\\java\\fileUpload\\2024\\01\17\\apple.txt
			 * 여기서 upload하는 파일명인 apple.txt가 fileName이 되는 것이고
			 * 당일날 중복될 수 있는 파일명을 분류하기 위해서 uuid로 분류를 해주는 것이다.
			 * 따라서 실제 storeFile에 저장되는 값은
			 * D:\\HMS\\myProject\\java\\fileUpload\\2024\\01\17\\uuid_apple.txt 이다.
			 */
			
			// 로컬디스크에 실제로 저장하기
			try {
				file.transferTo(storeFile);
				// storeFile 경로에 업로드한 file을 로컬디스크에 실제로 저장 완료
				
				// 썸네일 생성은 업로드된 file이 이미지 파일일 때만 생성할 것임
				if(isImageFile(storeFile)) { // 이미지 파일 검증
					fvo.setFileType(1); // 이미지 파일이면 fvo의 fileType을 1로 변경
					
					// 썸네일 파일 생성 및 저장하기
					File thumbNail = new File(folders, uuid + "_th_" + fileName);
					// thumbnailator 라이브러리 활용
					Thumbnails.of(storeFile).size(75, 75).toFile(thumbNail);
					// storeFile 경로에 존재하는 파일을 75,75 사이즈로 변환하여 thumbNail 경로에 File 생성(로컬디스크 저장)
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.info("@@@@@ 파일 생성 오류 발생");
			}
			
			// 모든 세팅이 완료되면 flist에 fvo 추가
			flist.add(fvo);
		}
		
		return flist;
	}
	
	// 이미지 파일인지 확인하는 검증 Method
	private boolean isImageFile(File storeFile) throws IOException{
		// 내부 class에서만 사용할 목적의 Method라면 private로 생성하는 것이 옳다.
		
		// Tika 라이브러리를 활용한 mime Type 체크
		String mimeType = new Tika().detect(storeFile);
		log.info("@@@@@ mimeType @@@ " + mimeType);
		// 만약 storeFile이 이미지(jpg) 확장자를 가지고 있다면 mimeType에 "image/jpg"로 저장됨
		// 따라서 mimeType이 이미지일 경우 true를 리턴하면 이미지 파일인지 검증할 수 있다.
		
		return mimeType.startsWith("image") ? true : false;
		// startsWith : String 클래스의 Method로 문자열이 지정된 접두사로 시작하는지 비교하여
		// 일치하면 true, 아니면 false
	}
}
