package com.alibaba.example.rag.internet.controller;

import com.alibaba.example.rag.internet.service.InternetSearchService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuluo
 * @author <a href="mailto:yuluo08290126@gmail.com">yuluo</a>
 */

@RestController
@RequestMapping("/internet")
public class InterNetSearchController {

	private final InternetSearchService internetSearchService;

}
