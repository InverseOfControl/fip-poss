package com.ipaylinks.poss.common;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 
 * TODO
 * @author zhaoyang
 * @date 2018年8月14日
 */
public class ImUser {

    private long uid;

    private String realName;

    public ImUser() {
    }

    public ImUser(long uid, String realName) {
        this.uid = uid;
        this.realName = realName;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getUid() {
        return uid;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRealName() {
        return realName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ImUser user = (ImUser) o;

        return new EqualsBuilder()
                .append(uid, user.uid)
                .append(realName, user.realName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(uid)
                .append(realName)
                .toHashCode();
    }
}
