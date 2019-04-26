package com.jerry.handler;

import com.jerry.config.ServerConfig;
import com.jerry.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by son on 2019-02-27.
 */
@Slf4j
@Service
public class UserInfoArgumentResolver implements HandlerMethodArgumentResolver {
	private ServerConfig config;

	UserInfoArgumentResolver(final ServerConfig config) {
		this.config = config;
	}

	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return UserInfo.class.isAssignableFrom(methodParameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter methodParameter, @Nullable ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, @Nullable WebDataBinderFactory webDataBinderFactory) throws Exception {
		final HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
		String userId = request.getParameter("userId");
		if (StringUtils.isEmpty(userId)) {
			return new UserInfo("");
		}
		return new UserInfo(userId);
	}
}
