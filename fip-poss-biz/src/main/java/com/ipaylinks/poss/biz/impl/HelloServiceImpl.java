package com.ipaylinks.poss.biz.impl;

import com.ipaylinks.poss.facade.model.HelloRequest;
import com.ipaylinks.poss.facade.model.HelloResponse;
import com.ipaylinks.poss.facede.biz.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloServiceImpl implements HelloService{
    private Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);
    @Override
    public HelloResponse sayHello(HelloRequest request) {
        logger.info(" request body :{}",request);
        return null;
    }

}
