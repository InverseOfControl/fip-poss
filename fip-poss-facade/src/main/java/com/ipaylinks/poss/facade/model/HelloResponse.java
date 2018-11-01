package com.ipaylinks.poss.facade.model;

import com.ipaylinks.common.rpc.BaseResponse;

public class HelloResponse extends BaseResponse{

    private static final long serialVersionUID = -3790548497868234667L;
    private String reply;

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
}
