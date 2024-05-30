package com.springmvc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.springmvc.domain.Member;

public class MemberRowMapper implements RowMapper<Member> {
	public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
		Member member = new Member();
		member.setUsername(rs.getString(1));
		member.setPassword(rs.getString(2));
		member.setName(rs.getString(3));
		member.setPhonenum(rs.getString(4));
		member.setEnabled(rs.getBoolean(5));
		member.setAuthority(rs.getString(6));
		return member;
	}

}
