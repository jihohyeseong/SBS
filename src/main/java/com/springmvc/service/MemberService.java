package com.springmvc.service;

import java.util.List;

import com.springmvc.domain.Member;

public interface MemberService {
	List<Member> getAllMemberList();
	Member getMemberById(String memberId);
	void setNewMember(Member member);
	void setUpdateMember(Member member);
	void setDeleteMember(String memberId);
}
