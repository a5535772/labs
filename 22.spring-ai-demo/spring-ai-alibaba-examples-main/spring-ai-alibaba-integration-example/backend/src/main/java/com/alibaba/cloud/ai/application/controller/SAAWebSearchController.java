package com.alibaba.cloud.ai.application.controller;

import com.alibaba.cloud.ai.application.entity.result.Result;
import com.alibaba.cloud.ai.application.service.SAAWebSearch;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuluo
 * @author <a href="mailto:yuluo08290126@gmail.com">yuluo</a>
 */

@RestController
@Tag(name = "Web Search APIs")
@RequestMapping("/api/v1/")
public class SAAWebSearchController {

	private final SAAWebSearch webSearch;

	public SAAWebSearchController(SAAWebSearch webSearch) {
		this.webSearch = webSearch;
	}

	@GetMapping("/search")
	public Result<Object> search(
			@RequestParam(value = "query", required = true) String query
	) {

		return Result.success(webSearch.search(query));
	}

}
