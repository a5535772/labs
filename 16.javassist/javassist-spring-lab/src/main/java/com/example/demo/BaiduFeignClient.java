package com.example.demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="baiduFeignClient",url = "http://www.baidu.com")
public interface BaiduFeignClient {

    @RequestMapping(
            value = "/s?wd=dependabot&rsv_spt=1&rsv_iqid=0xa3fc6e3c0006eebb&issp=1&f=8&rsv_bp=1&rsv_idx=2&ie=utf-8&tn=baiduhome_pg&rsv_dl=tb&rsv_enter=0&rsv_sug3=2&rsv_n=2&rsv_sug1=1&rsv_sug7=100&rsv_btype=i&inputT=1247&rsv_sug4=1353",
            method = RequestMethod.GET)
    String hello();

}
