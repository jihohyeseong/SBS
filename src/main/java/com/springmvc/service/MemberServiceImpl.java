package com.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.domain.Member;
import com.springmvc.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberRepository memberRepository;
	
	public void setNewMember(Member member) {
		memberRepository.setNewMember(member);
	}
	
	public List<Member> getAllMemberList() {
		return memberRepository.getAllMemberList();
	}

	public Member getMemberById(String memberId) {
		Member memberById = memberRepository.getMemberById(memberId);
		return memberById;
	}
	
	public void setUpdateMember(Member member) {
		memberRepository.setUpdateMember(member);
	}
	public void setDeleteMember(String memberId) {
		memberRepository.setDeleteMember(memberId);
	}
}
