package com.gdy.shiro;

import java.util.LinkedHashMap;

public class FilterChainDefinitionMapBuilder {

    public LinkedHashMap<String,String> buildFilterChainDefinitionMap(){
        LinkedHashMap<String, String> map = new LinkedHashMap<>();

        //需要注意加载的顺序
        map.put("/login.jsp", "anon");
        map.put("/**", "authc");

        return map;
    }
}
