package com.jerry.controller;

import com.jerry.config.ServerConfig;
import com.jerry.model.UserInfo;
import com.jerry.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by son on 2019-04-07.
 */
@Slf4j
@Controller
public class AuthController {
	private static final String AUTH_KEY = "auth";
	private AuthService authService;
	private ServerConfig config;

	@Autowired
	AuthController(final AuthService authService, final ServerConfig config) {
		this.authService = authService;
		this.config = config;
	}

	@RequestMapping("/")
	public ModelAndView init(
			final HttpSession session,
			final UserInfo userInfo) {
		if (authService.checkSession(userInfo.getUserId(), (String)session.getAttribute(AUTH_KEY))) {
			ModelAndView mv = new ModelAndView("index");
			mv.addObject("sourcePackage", config.getSourcePackagePrefix());
			return mv;
		}

		return new ModelAndView("login");
	}

	@RequestMapping("/login")
	public ModelAndView main(
			final HttpSession session,
			final UserInfo userInfo,
			@RequestParam("pw") final String pw) {
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("sourcePackage", config.getSourcePackagePrefix());
		if (authService.checkSession(userInfo.getUserId(), (String)session.getAttribute(AUTH_KEY))) {
			return mv;
		}

		if (authService.checkAuth(userInfo.getUserId(), pw)) {
			session.setAttribute(AUTH_KEY, authService.createUniqueKey(userInfo.getUserId()));
			return mv;
		} else {
			return new ModelAndView("login");
		}
	}

	@RequestMapping("/setting")
	public ModelAndView setting() {
		ModelAndView mv = new ModelAndView("setting");
		mv.addObject("data", authService.getServerEnviornments());
		return mv;
	}

	@ResponseBody
	@PostMapping(value = "/apply.son")
	public boolean apply(@RequestBody final Map<String, String> data) {
		authService.setEnviornments(data);
		return true;
	}
}
