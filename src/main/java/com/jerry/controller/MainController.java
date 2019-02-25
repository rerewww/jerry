package com.jerry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by son on 2019-02-25.
 */
@Controller
public class MainController {

	@RequestMapping("/")
	public String main() {
		return "index";
	}
}
