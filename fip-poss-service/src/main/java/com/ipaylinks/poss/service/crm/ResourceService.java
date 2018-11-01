/**
 * 启赟金融信息服务（上海）有限公司
 * Copyright (c) 2015-2017 iPayLinks.All Rights Reserved.
 */
package com.ipaylinks.poss.service.crm;

import com.ipaylinks.poss.dal.domain.crm.Resource;

/**
 * TODO
 * @author zhaoyang
 * @date 2018年8月15日
 */
public interface ResourceService {

    /**
     * 获取资源树
     *
     * @param status
     * @return
     */
    Iterable<Resource> getResourceTree(Boolean status);

    Iterable<Resource> getResourceTree();

}
