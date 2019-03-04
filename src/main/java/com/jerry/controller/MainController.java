package com.jerry.controller;

import com.jerry.model.LogModel;
import com.jerry.model.ResultModel;
import com.jerry.project.ProjectFile;
import com.jerry.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

/**
 * Created by son on 2019-02-25.
 */
@Controller
public class MainController {
	private LogService logService;

	@Autowired
	MainController(final LogService logService) {
		this.logService = logService;
	}

	@RequestMapping("/")
	public String main() {
		return "index";
	}

	@RequestMapping("/read.son")
	public ModelAndView read() {
		ModelAndView mv = new ModelAndView("index");
		return mv;
	}

	@RequestMapping("/parse.son")
	@ResponseBody
	public ResultModel parse() {
		LogModel logModel = logService.parse();
		ResultModel resultModel = new ResultModel(true, "success", logModel);
		return resultModel;
	}

	@RequestMapping("/viewCode.son")
	@ResponseBody
	public List<String> viewCode(
			final ProjectFile projectFile,
			@RequestParam("line") int line,
			@RequestParam("range") int range
	) {
		if (projectFile == null) {
			return Collections.emptyList();
		}
		List<String> contents = logService.getViewCode(projectFile, line, range);
		return contents;
	}
}
