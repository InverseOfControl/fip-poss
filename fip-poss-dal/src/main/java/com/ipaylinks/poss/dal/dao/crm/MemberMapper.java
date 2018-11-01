package com.ipaylinks.poss.dal.dao.crm;

import com.ipaylinks.poss.dal.domain.crm.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface MemberMapper {

	public List<Member> findAll();
}
