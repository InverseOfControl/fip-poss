package com.ipaylinks.poss.dal.dao;

import com.ipaylinks.poss.dal.domain.crm.Member;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberDao extends PagingAndSortingRepository<Member, Long>, JpaSpecificationExecutor {

    int countByUserName(String userName);

    Member findByUserName(String userName);
}
