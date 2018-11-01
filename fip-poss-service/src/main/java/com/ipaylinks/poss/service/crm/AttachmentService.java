/**
 * 启赟金融信息服务（上海）有限公司
 * Copyright (c) 2015-2017 iPayLinks.All Rights Reserved.
 */
package com.ipaylinks.poss.service.crm;

import com.ipaylinks.poss.dal.common.AttachmentType;
import com.ipaylinks.poss.dal.domain.crm.Attachment;
import com.ipaylinks.poss.dal.domain.crm.Member;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

/**
 * TODO
 * @author zhaoyang
 * @date 2018年8月15日
 */
public interface AttachmentService {

    /**
     * 存储附件
     *
     * @param member
     * @param type
     * @param multipartFile
     * @return
     * @throws Exception
     */
    Attachment saveFile(Member member, AttachmentType type, MultipartFile multipartFile) throws Exception;

    /**
     * 获取附件文件
     *
     * @param filePath
     * @return
     * @throws FileNotFoundException
     */
    Attachment getFile(String filePath) throws FileNotFoundException;

    /**
     * 清除用户无用头像
     *
     * @param member
     */
    void clearAvatar(Member member);

}
