package com.ipaylinks.poss.util;

import org.junit.Test;
import org.junit.runner.RunWith;

import static com.ipaylinks.poss.util.StringUtil.camelToUnderline;
import static org.junit.Assert.*;

/**
 * @author hubin.wei
 * @date 2018/9/12 13:51
 **/

public class StringUtilTest {

    public static void main(String[] args) {


        System.out.println(camelToUnderline("merchantId"));
        System.out.println(camelToUnderline("chargeScene"));
        System.out.println(camelToUnderline("chargeRuleName"));
        System.out.println(camelToUnderline("id"));
        System.out.println(camelToUnderline("status"));
        System.out.println(camelToUnderline("effectiveDate"));
        System.out.println(camelToUnderline("createTime"));
        System.out.println(camelToUnderline("updateTime"));


    }
}