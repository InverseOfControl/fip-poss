package com.ipaylinks.poss.facede.biz;

import com.ipaylinks.poss.facade.model.HelloRequest;
import com.ipaylinks.poss.facade.model.HelloResponse;

public interface HelloService {

    HelloResponse sayHello(HelloRequest request);
}
