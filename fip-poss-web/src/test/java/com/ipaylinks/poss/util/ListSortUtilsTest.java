/**
 * 启赟金融信息服务（上海）有限公司
 * Copyright (c) 2015-2017 iPayLinks.All Rights Reserved.
 */
package com.ipaylinks.poss.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.ipaylinks.poss.dal.domain.crm.Resource;

/**
 * List排序测试类
 * @author zhaoyang
 * @date 2018年9月21日
 */
public class ListSortUtilsTest {

    public static void main(String[] args) throws Exception {


        List<Resource> list = new ArrayList<Resource>();
        // public Resource(Integer userId, String username, Date birthDate,Integer age, float fRate, char ch)
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        Resource user1 = new Resource();
        user1.setId(11L);
        user1.setResName("资源管理");
        user1.setWeight(12);
        Resource user2 = new Resource();
        user2.setId(10L);
        user2.setResName("角色管理");
        user2.setWeight(11);
        Resource user3 = new Resource();
        user3.setId(3L);
        user3.setResName("用户管理");
        user3.setWeight(13);
        Resource user4 = new Resource();
        user4.setId(26L);
        user4.setResName("重置密码");
        user4.setWeight(null);
        Resource user5 = new Resource();
        user5.setId(23L);
        user5.setResName("编辑用户");
        user5.setWeight(null);
        Resource user6 = new Resource();
        user6.setId(41L);
        user6.setResName("test管理");
        user6.setWeight(12);
        

        list.add(user1);
        list.add(user2);
        list.add(user3);
        list.add(user4);
        list.add(user5);
        list.add(user6);

        System.out.println("\n-------原来序列-------------------");
        printfResourceList(list);

//        // 按userId升序、username降序、birthDate升序排序
//        String [] sortNameArr = {"userId","username","birthDate"};
//        boolean [] isAscArr = {true,false,true};
//        ListSortUtils.sort(list,sortNameArr,isAscArr);
//        System.out.println("\n--------按按userId升序、username降序、birthDate升序排序（如果userId相同，则按照username降序,如果username相同，则按照birthDate升序）------------------");
//        printfResourceList(list);
        
//        // 按userId、username、birthDate都升序排序
//        ListSortUtils.sort(list, true, "weight", "username");
//        System.out.println("\n--------按userId、username、birthDate排序（如果userId相同，则按照username升序,如果username相同，则按照birthDate升序）------------------");
//        printfResourceList(list);

        // 按userId、username都倒序排序
        ListSortUtils.sort(list, false, "weight");
        System.out.println("\n--------按userId和username倒序（如果userId相同，则按照username倒序）------------------");
        printfResourceList(list);

//        // 按username、birthDate都升序排序
//        ListSortUtils.sort(list, true, "username", "birthDate");
//        System.out.println("\n---------按username、birthDate升序（如果username相同，则按照birthDate升序）-----------------");
//        printfResourceList(list);

//        // 按birthDate倒序排序
//        ListSortUtils.sort(list, false, "birthDate");
//        System.out.println("\n---------按birthDate倒序-----------------");
//        printfResourceList(list);

//        // 按fRate升序排序
//        ListSortUtils.sort(list, true, "fRate");
//        System.out.println("\n---------按fRate升序-----------------");
//        printfResourceList(list);

        // 按ch倒序排序
//        ListSortUtils.sort(list, false, "ch");
//        System.out.println("\n---------按ch倒序-----------------");
//        printfResourceList(list);

    }

    private static void printfResourceList(List<Resource> list) {
        for (Resource user : list) {
            System.out.println(user.toString());
        }
    }
}
