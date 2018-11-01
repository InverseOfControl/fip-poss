package com.ipaylinks.poss;

import com.ipaylinks.poss.biz.impl.HelloServiceImpl;
import com.ipaylinks.poss.facade.model.HelloRequest;
import org.apache.logging.log4j.ThreadContext;

public class TestLog4j {
    public static void main(String[] args) {
        ThreadContext.put("traceId", String.valueOf(123456));
        HelloServiceImpl helloService = new HelloServiceImpl();
        HelloRequest request = new HelloRequest();
        request.setName("cuber");
        helloService.sayHello(request);
    }
}
