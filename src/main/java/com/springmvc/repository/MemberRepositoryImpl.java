package com.springmvc.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.springmvc.domain.Member;
import com.springmvc.exception.BookIdException;

@Repository
public class MemberRepositoryImpl implements MemberRepository {
	
	private JdbcTemplate template;
	
	@Autowired
	public void setJdbctemplate(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}
		
	public void setNewMember(Member member) {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(member.getPassword());
		
		if (member.getAuthority() == null || member.getAuthority().isEmpty()) {
		    String authority = "ROLE_USER";		
		    String SQL = "INSERT INTO member (username, password, m_name, m_phonenum, enabled, m_authority)" + "VALUES (?, ?, ?, ?, ?, ?)";
		    template.update(SQL, member.getUsername(), encodedPassword, member.getName(), member.getPhonenum(), member.getEnabled(), authority);
		}
		else {
			String SQL = "INSERT INTO member (username, password, m_name, m_phonenum, enabled, m_authority)" + "VALUES (?, ?, ?, ?, ?, ?)";
			template.update(SQL, member.getUsername(), encodedPassword, member.getName(), member.getPhonenum(), member.getEnabled(), member.getAuthority());
		}
			
		System.out.println("회원가입이 완료되었습니다.");
	}

	public List<Member> getAllMemberList() {
		String SQL = "SELECT * FROM member";
		List<Member> listOfMembers = template.query(SQL, new MemberRowMapper());
		return listOfMembers;
	}	
	
	public Member getMemberById(String memberId) {
		Member memberInfo = null;
		String SQL = "SELECT count(*) FROM member where username=?";
		int rowCount = template.queryForObject(SQL, Integer.class, memberId);
		if (rowCount != 0) {
			SQL = "SELECT * FROM member where username=?";
			memberInfo = template.queryForObject(SQL, new Object[] {memberId}, new MemberRowMapper());
		}
		
		if(memberInfo ==  null)
			throw new BookIdException(memberId);
		return memberInfo;
	}
	
	public void setUpdateMember(Member member) {
		if (member.getAuthority() == null || member.getAuthority().isEmpty()) {
		    String authority = "ROLE_USER";	
		    String SQL = "UPDATE Member SET m_name=?, m_phonenum=?, m_authority=? where username=?";
			template.update(SQL, member.getName(), member.getPhonenum(), authority, member.getUsername());
			System.out.println("회원수정이 authority 완료되었습니다.");
		}
		else {
			String SQL = "UPDATE Member SET m_name=?, m_phonenum=?, m_authority=? where username=?";
			template.update(SQL, member.getName(), member.getPhonenum(), member.getAuthority(), member.getUsername());
			System.out.println("회원수정이 m_authority 완료되었습니다.");
		}	
	}

	public void setDeleteMember(String memberId) {
		String SQL = "DELETE from Member where username = ?";
		this.template.update(SQL, memberId);
		System.out.println("회원 탈퇴가 완료되었습니다.");
	}
}