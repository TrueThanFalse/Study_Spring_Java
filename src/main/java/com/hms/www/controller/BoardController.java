package com.hms.www.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hms.www.domain.BoardDTO;
import com.hms.www.domain.BoardVO;
import com.hms.www.domain.FileVO;
import com.hms.www.domain.PagingVO;
import com.hms.www.handler.FileHandler;
import com.hms.www.handler.PagingHandler;
import com.hms.www.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/board/*")
@RequiredArgsConstructor
public class BoardController {

	/* 
 	@Inject 어노테이션으로 생성자 주입하지 않고
	@RequiredArgsConstructor 어노테이션을 활용할 수 있다.
	@RequiredArgsConstructor는 lombok에서 지원하는 어노테이션으로
	@Inject 어노테이션과 비슷한 효과를 가지고 있고
	멤버변수를 final로 선언하여 bsv의 불변성과 안정성을 확보할 수 있다.
	따라서 @Inject가 아닌 @RequiredArgsConstructor 어노테이션도 알아두자
	*/
	private final BoardService bsv;
	
	private final FileHandler fh;
	
	@GetMapping("/register")
	public void register() {}
	
	/*
	@PostMapping("/register")
	public String insert(BoardVO bvo) {
		// logback & log4jdbc 셋팅으로 직접 log를 찍지 않아도 웬만한 log는 전부 볼 수 있다.
		bsv.insert(bvo);
		return "home";
	}
	*/
	// form 태그의 multipart/form-data 가져오기
	@PostMapping("/register")
	public String insert(BoardVO bvo, @RequestParam(name="files", required = false)MultipartFile[] files
			, RedirectAttributes re) {
		List<FileVO> flist = null;
		
		// FileHandler 사용하여 flist 완성
		if(files[0].getSize() > 0) {
			// file upload는 클라이언트 upload 할수도 있고 안 할수도 있음
			// 따라서 만약 upload를 한다면 files의 0번지 요소의 size가 0보다 클 것임
			// 그때만 FileHandler를 활용하여 flist를 만들어준다.
			flist = fh.uploadFiles(files);
		}
		
		BoardDTO bdto = new BoardDTO(bvo, flist);
		
		int isOK = bsv.insert(bdto);
		if(isOK > 0) {
			re.addFlashAttribute("registerMsg", isOK);
		}
		
		return "redirect:/board/list";
	}
	
	
	@GetMapping("/list")
	public void list(Model m, PagingVO pgvo) {
		// 페이징네이션 구현
		log.info("pgvo >>> "+pgvo);
		
		List<BoardVO> list = bsv.getList(pgvo);
		// 페이징네이션을 위해 pgvo 매개변수 추가하여 List<BoardVO> 조회
	    int totalCount = bsv.getTotalCount(pgvo);
	    // 검색시 type 값을 get하기 위한 pgvo 매개변수 추가
	    
	    PagingHandler ph = new PagingHandler(pgvo, totalCount);
		
		m.addAttribute("list", list);
		m.addAttribute("ph", ph);
	}
	
	@GetMapping("/detail")
	public void detail(@RequestParam("bno")int bno, Model m) {
		BoardDTO bdto = bsv.getDetail(bno);
		m.addAttribute("bdto", bdto);
	}
	
	@GetMapping("/modify")
	public void modify(@RequestParam("bno")int bno, Model m) {
		BoardDTO bdto = bsv.getModify(bno);
		m.addAttribute("bdto", bdto);
	}
	
	@GetMapping("/delete")
	public String bvoDelete(@RequestParam("bno")int bno, RedirectAttributes re) {
		int isOK = bsv.bvoDelete(bno);
		re.addFlashAttribute("deleteMsg", isOK);
		return "redirect:/board/list";
	}
	
	/*
	@PostMapping("/edit")
	public String edit(BoardVO bvo, RedirectAttributes re) {
		int isOK = bsv.edit(bvo);
		re.addFlashAttribute("editMsg", isOK);
		return "redirect:/board/list";
	}
	*/
	@PostMapping("/edit")
	public String edit(BoardVO bvo, RedirectAttributes re,
			@RequestParam(name="files", required = false)MultipartFile[] files) {
		// register Method와 비슷함
		List<FileVO> flist = null;
		
		if(files[0].getSize() > 0) {
			flist = fh.uploadFiles(files);
		}
		
		BoardDTO bdto = new BoardDTO(bvo, flist);
		
		int isOK = bsv.edit(bdto);
		if(isOK > 0) {
			re.addFlashAttribute("editMsg", isOK);
		}
		
		return "redirect:/board/list";
	}
	
	@DeleteMapping(value="/file/{uuid}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> removeFile(@PathVariable("uuid")String uuid) {
		int isOK = bsv.deleteFile(uuid);
		return isOK > 0 ? new ResponseEntity<String>("1", HttpStatus.OK) :
			new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}