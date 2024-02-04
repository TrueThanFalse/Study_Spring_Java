package com.hms.www.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	private final BCryptPasswordEncoder bcEncoder;
	
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
	
	@GetMapping("/list")
	public void list(Model m) {
		List<MemberVO> memberList = msv.selectMemberList();
		m.addAttribute("list", memberList);
	}
	
	// @RequestParam("email")String email : 쿼리스트링 방식(jsp에서 파라미터 받기)
	// Principal : Principal 객체를 가져와서 활용하는 방법
	@GetMapping("/modify")
	public void modify(Principal p, Model m) {
		// Principal 객체를 컨트롤러에 바로 가져와서 직접 추출하는 방법
		log.info("principal >>> " + p.getName());
		String email = p.getName();
		m.addAttribute("mvo", msv.detail(email));
	}
	
	@PostMapping("/modify")
	public String modify(MemberVO mvo, HttpServletRequest request, HttpServletResponse response) {
		if(mvo.getPwd().isEmpty()) {
			// 새로운 비밀번호가 없는 업데이트 진행
			msv.noPwdUpdate(mvo);
		}else {
			// 입력된 새로운 비밀번호를 다시 인코딩하여 업데이트 진행
			mvo.setPwd(bcEncoder.encode(mvo.getPwd()));
			msv.pwdUpdate(mvo);
		}
		
		// 로그아웃 진행
		logout(request, response);
		return "redirect:/member/login";
	}
	
	@GetMapping("/Withdrawal")
	public String Withdrawal(@RequestParam("email")String email, HttpServletRequest request, HttpServletResponse response) {
		int isOK = msv.Withdrawal(email);
		logout(request, response);
		return "redirect:/member/login";
	}
	
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication Authentication = SecurityContextHolder.getContext().getAuthentication(); // 현재 로그인 되어있는 객체
		new SecurityContextLogoutHandler().logout(request, response, Authentication);
		// 로그아웃 호출 => Authentication 객체가 없어지고 로그아웃 완료
	}
}
