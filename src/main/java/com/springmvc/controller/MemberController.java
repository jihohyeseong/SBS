package com.springmvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springmvc.domain.Member;
import com.springmvc.service.MemberService;


@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	// 전체 회원 목록
	@GetMapping
	public String requestMemberList(Model model) {
		List<Member> list = memberService.getAllMemberList();
		model.addAttribute("memberList", list);
		return "member";
	}
	
	// 회원가입
    @GetMapping("/join")
    public String requestJoin(Model model) {
        model.addAttribute("member", new Member()); 
        return "join";
    }
	
    @PostMapping("/join")
    public String submitJoin(@ModelAttribute("member") Member member) {
        memberService.setNewMember(member);
        return "join";
    }
    
    // 개인 회원 목록
    @GetMapping("/mypage")
    public String requestMemberById(@RequestParam("id") String username, Model model) {
    	Member memberById = memberService.getMemberById(username);
    	model.addAttribute("member", memberById);
    	return "mypage";
    }
    
    // 개인 회원 수정
    @GetMapping("/update")
    public String getUpdateMemberForm(@ModelAttribute("updateMember") Member member, @RequestParam("id") String username, Model model)	{
    	Member memberById = memberService.getMemberById(username);
    	model.addAttribute("member", memberById);
    	return "memberForm";
    }
    @PostMapping("/update")
    public String submitUpdateMemberForm(@ModelAttribute("updateMember") Member member) {
    	memberService.setUpdateMember(member);
    	return "redirect:/home";
    }
    
    // 개인 회원 삭제(탈퇴)
    @RequestMapping(value="/delete")
    public String getDeleteMemberForm(Model model, @RequestParam("id") String username) {
    	memberService.setDeleteMember(username);
    	return "redirect:/home";
    }
}
