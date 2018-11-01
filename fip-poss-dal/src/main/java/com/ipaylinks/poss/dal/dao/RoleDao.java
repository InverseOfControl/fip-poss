package com.ipaylinks.poss.dal.dao;

import com.ipaylinks.poss.dal.domain.crm.Role;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDao extends PagingAndSortingRepository<Role, Long> {

    List<Role> findByStatus(boolean b);
}
