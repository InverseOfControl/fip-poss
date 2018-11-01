package com.ipaylinks.poss.facade.model;

import com.ipaylinks.common.rpc.BaseRequest;

public class HelloRequest extends BaseRequest{

    private static final long serialVersionUID = -2807145765316759332L;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
