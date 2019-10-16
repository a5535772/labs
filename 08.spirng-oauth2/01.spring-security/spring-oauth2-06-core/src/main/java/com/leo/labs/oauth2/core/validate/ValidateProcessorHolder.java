/**
 * 
 */
package com.leo.labs.oauth2.core.validate;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leo.labs.oauth2.core.validate.exception.ValidateException;
import com.leo.labs.oauth2.core.validate.processors.ValidateProcessor;
import com.leo.labs.oauth2.core.validate.support.ValidateType;

/**
 * 
 * <一句话功能简述>
 * @Title: ValidateProcessorHolder.java
 * @Description: <功能详细描述>
 * @author  Leo Zhang
 * @date 2019年9月11日下午4:08:06
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
//@Component
public class ValidateProcessorHolder {

	@Autowired
	private Map<String, ValidateProcessor> ValidateProcessors;

	public ValidateProcessor findValidateProcessor(ValidateType type) {
		return findValidateProcessor(type.toString().toLowerCase());
	}

	public ValidateProcessor findValidateProcessor(String type) {
		String name = type.toLowerCase() + ValidateProcessor.class.getSimpleName();
		ValidateProcessor processor = ValidateProcessors.get(name);
		if (processor == null) {
			throw new ValidateException("验证码处理器" + name + "不存在");
		}
		return processor;
	}

}
