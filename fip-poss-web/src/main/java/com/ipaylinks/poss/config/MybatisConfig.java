package com.ipaylinks.poss.config;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.logging.log4j2.Log4j2Impl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Properties;

//@Configuration
//@MapperScan(basePackages = "com.ipaylinks.poss.dal.dao")
//@ConfigurationProperties("mybatis.pageHelper")
public class MybatisConfig extends Properties {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @PostConstruct
    public void addPlugin(){
        org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
        PageInterceptor pageInterceptor = new PageInterceptor();
        configuration.setLogImpl(Log4j2Impl.class);
        pageInterceptor.setProperties(this);
        configuration.addInterceptor(pageInterceptor);
    }
}