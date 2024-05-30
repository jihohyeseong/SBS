package com.springmvc.repository;

import java.util.List;

import com.springmvc.domain.Member;

public interface MemberRepository {
	List<Member> getAllMemberList();
	Member getMemberById(String memberId);
	void setNewMember(Member member);
	void setUpdateMember(Member member);
	void setDeleteMember(String memberId);
}