package com.ipaylinks.poss.dal.dao;

import com.ipaylinks.poss.dal.common.AttachmentType;
import com.ipaylinks.poss.dal.domain.crm.Attachment;
import com.ipaylinks.poss.dal.domain.crm.Member;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AttachmentDao extends PagingAndSortingRepository<Attachment, Long> {
    
    Attachment findByFilePath(String filePath);

    List<Attachment> findByFilePathIsNotAndMemberAndType(String filePath, Member member, AttachmentType type);

}
