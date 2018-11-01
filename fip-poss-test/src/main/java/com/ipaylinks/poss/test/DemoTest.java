/**
 * 启赟金融信息服务（上海）有限公司
 * Copyright (c) 2015-2017 iPayLinks.All Rights Reserved.
 */
package com.ipaylinks.poss.test;

import com.ipaylinks.poss.PossApplication;
import com.ipaylinks.poss.integration.oss.UFSClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author hongke.yin on 2017/11/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PossApplication.class})
public class DemoTest {

    @Autowired
    private UFSClient ufsClient;

    @Test
    public void testOssFile() {
        String filePath = "verify-center/9056/20180828/c770a0843ebc4bb6a7d8b01f94ccb859.170327";
        String signUfl = ufsClient.getFileSign(filePath);
        System.out.println(signUfl);
    }
}
