package com.jerry.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jerry.config.ServerConfig;
import com.jerry.model.LogModel;
import com.jerry.project.ProjectFile;
import com.jerry.service.LogService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by son on 2019-03-12.
 */
public class MainControllerTest {
	@InjectMocks private MainController controller;
	@Mock private LogService logService;
	@Mock private ServerConfig serverConfig;

	private MockMvc mockMvc;
	private ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		controller = new MainController(logService, serverConfig);

		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void mainTest() throws Exception {
		mockMvc.perform(post("/")).andExpect(status().isOk());
	}

	@Test
	public void readTest() throws Exception {
		List<String> results = new ArrayList<>();
		results.add("log content 1");
		results.add("log content 2");
		results.add("log content 3");
		doReturn(results).when(logService).getTomcatLogs();

		mockMvc.perform(post("/read.son")).andExpect(status().isOk());
	}

	@Test
	public void parseTest() throws Exception {
		doReturn(new LogModel()).when(logService).parse();
		mockMvc.perform(post("/parse.son")).andExpect(status().isOk());
	}

	@Test
	public void viewCodeTestByFileNull() throws Exception {
		List<String> results = new ArrayList<>();
		results.add("log content 1");
		results.add("log content 2");
		results.add("log content 3");

		doReturn(results).when(logService).getViewCode(any(ProjectFile.class), anyInt(), anyInt());
		MvcResult mvcResult = mockMvc.perform(post("/viewCode.son")
				.param("line", "1")
				.param("range", "10"))
				.andExpect(status().isOk()).andReturn();

		List<String> result = mapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
		assertThat(result.size(), is(3));
	}
}
