package com.leo.labs.oauth2.core.validate.support;

import com.leo.labs.oauth2.core.properties.SecurityConstants;

/**
 * 
 * <一句话功能简述>
 * 
 * @Title: ValidateType.java
 * @Description: <功能详细描述>
 * @author Leo Zhang
 * @date 2019年9月11日下午4:05:49
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public enum ValidateType {

	/**
	 * 短信验证码
	 */
	SMS {
		@Override
		public String getParamNameOnValidate() {
			return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
		}
	},
	/**
	 * 图片验证码,unused
	 */
	IMAGE {
		@Override
		public String getParamNameOnValidate() {
			return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
		}
	};

	/**
	 * 校验时从请求中获取的参数的名字
	 * 
	 * @return
	 */
	public abstract String getParamNameOnValidate();

}
