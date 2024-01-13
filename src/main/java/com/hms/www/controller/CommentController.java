package com.hms.www.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hms.www.domain.CommentVO;
import com.hms.www.domain.PagingVO;
import com.hms.www.handler.PagingHandler;
import com.hms.www.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/comment/*")
@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {

	private final CommentService csv;
	
	@PostMapping(value="/post", consumes = "application/json", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> post(@RequestBody CommentVO cvo){
		log.info("cvo >>> "+cvo);
		int isOK = csv.post(cvo);
		return isOK > 0 ? new ResponseEntity<String>("1", HttpStatus.OK)
				: new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping(value = "/{bno}/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagingHandler> list(@PathVariable("bno")int bno,
			@PathVariable("page")int page){
		PagingVO pgvo = new PagingVO(page, 5);
		PagingHandler ph = csv.getList(bno, pgvo);
		return new ResponseEntity<PagingHandler>(ph, HttpStatus.OK);
		// 비동기는 객체를 여러개 전송할 수 없다.
		// 따라서 페이징핸들러에 모든 정보를 담아서 return해야 됨
		// ph는 3가지(페이지네이션 정보, pgvo, cmtList) 정보를 가지고 있음
	}
	
	@PutMapping(value="/edit", consumes = "application/json", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> edit(@RequestBody CommentVO cvo){
		log.info("cvo >>> "+cvo);
		int isOK = csv.edit(cvo);
		return isOK > 0 ? new ResponseEntity<String>("1", HttpStatus.OK):
			new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/delete/{cno}")
	public ResponseEntity<String> delete(@PathVariable("cno")int cno) {
		int isOK = csv.delete(cno);
		return isOK > 0 ? new ResponseEntity<String>("1", HttpStatus.OK):
			new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
