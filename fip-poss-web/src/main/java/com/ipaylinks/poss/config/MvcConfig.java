package com.ipaylinks.poss.config;

import com.ipaylinks.poss.converter.DateConverter;
import com.ipaylinks.poss.interceptor.AuthInterceptor;
import com.ipaylinks.poss.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.*;


@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 和页面有关的静态目录都放在项目的static目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    /**
     * 注册时间转换器。
     * 处理前端字符串日期自动转换成后台date类型，需要转换的字段增加如下注解@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")，
     * 时间格式根据自己需要的定义，注意DateConverter中是否已经包含你需要的时间格式。
     * 
     * {@inheritDoc}
     * 
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addFormatters(org.springframework.format.FormatterRegistry)
     */
    @Override
    public void addFormatters(FormatterRegistry registry){
        registry.addConverter(new DateConverter());
    }
    
    /**
     * 添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 注册登录拦截器
        InterceptorRegistration loginReg = registry.addInterceptor(new LoginInterceptor());
        loginReg.addPathPatterns("/**");
        loginReg.excludePathPatterns("/login", "/error", "/reg", "/verify/code");

        // 注册权限拦截器
        InterceptorRegistration authReg = registry.addInterceptor(new AuthInterceptor());
        authReg.addPathPatterns("/**");
        // 不受权限控制的请求
        authReg.excludePathPatterns("/", "/login", "/error", "/im/**", "/reg", "/verify/code");

        super.addInterceptors(registry);
    }

}