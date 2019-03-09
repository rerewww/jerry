package com.jerry.controller;

import com.jerry.config.ServerConfig;
import com.jerry.model.LogModel;
import com.jerry.model.ResultModel;
import com.jerry.project.ProjectFile;
import com.jerry.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by son on 2019-02-25.
 */
@Slf4j
@Controller
public class MainController {
	private LogService logService;
	private ServerConfig config;

	@Autowired
	MainController(final LogService logService, final ServerConfig config) {
		this.logService = logService;
		this.config = config;
	}

	@RequestMapping("/")
	public ModelAndView main() {
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("sourcePackage", config.getSourcePackagePrefix());

		return mv;
	}

	@RequestMapping("/read.son")
	@ResponseBody
	public List<String> read() {
		return logService.getTomcatLogs();
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

	@RequestMapping("/usage.son")
	@ResponseBody
	public Map<String, Integer> getUsage() {
		return logService.getUsage();
	}
}
