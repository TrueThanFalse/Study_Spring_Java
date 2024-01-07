package com.hms.www.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hms.www.domain.BoardVO;
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
	
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/register")
	public String insert(BoardVO bvo) {
		// logback & log4jdbc 셋팅으로 직접 log를 찍지 않아도 웬만한 log는 전부 볼 수 있다.
		bsv.insert(bvo);
		return "home";
	}
	
	@GetMapping("/list")
	public void list(Model m) {
		List<BoardVO> list = bsv.getList();
		m.addAttribute("list", list);
	}
	
	@GetMapping("/detail")
	public void detail(@RequestParam("bno")int bno, Model m) {
		BoardVO bvo = bsv.getDetail(bno);
		m.addAttribute("bvo", bvo);
	}
	
	@GetMapping("/modify")
	public void modify(@RequestParam("bno")int bno, Model m) {
		BoardVO bvo = bsv.getModify(bno);
		m.addAttribute("bvo", bvo);
	}
	
	@GetMapping("/delete")
	public String bvoDelete(@RequestParam("bno")int bno, RedirectAttributes re) {
		int isOK = bsv.bnoDelete(bno);
		re.addFlashAttribute("deleteMsg", isOK);
		return "redirect:/board/list";
	}
	
}