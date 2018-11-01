package com.ipaylinks.poss.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath*:dubbo/dubbo-provider.xml","classpath*:dubbo/dubbo-*-client.xml"})
public class DubboConfig {

}