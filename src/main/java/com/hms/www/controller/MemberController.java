package com.hms.www.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hms.www.security.MemberVO;
import com.hms.www.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/member/*")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService msv;
	
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/register")
	public String register(MemberVO mvo, RedirectAttributes re) {
		log.info("@@@@@ member register mvo @@@ " + mvo);
		
		// 비밀번호 암호화 Setting
		BCryptPasswordEncoder bcEncoder = new BCryptPasswordEncoder();
		String encodePwd = bcEncoder.encode(mvo.getPwd());
		mvo.setPwd(encodePwd);
		
		// 회원가입
		int isOK = msv.signUP(mvo);
		re.addFlashAttribute("signUpMsg", isOK);
		
		return "redirect:/";
	}
}
