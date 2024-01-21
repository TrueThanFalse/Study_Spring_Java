package com.hms.www.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
	
	@PostMapping("/getEmail")
	@ResponseBody
	public String getEmail(@RequestBody String email) {
		log.info("@@@@@ getEmail Join check");
		log.info("@@@@@ @RequestBody email : " + email);
		int isOK = msv.selectEmail(email);
		return String.valueOf(isOK);
	}
	
	@GetMapping("/login")
	public void login() {}
	
	@PostMapping("/loginError")
	public String loginFail(HttpServletRequest request, RedirectAttributes re) {
		// 로그인 실패하면 다시 로그인 페이지로 이동하여 오류 메세지를 전송하고 다시 로그인을 유도
		
		re.addFlashAttribute("email", request.getAttribute("email"));
		re.addFlashAttribute("errMsg", request.getAttribute("errMsg"));
		
		return "redirect:/member/login";
	}
	
}
