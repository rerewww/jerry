package com.jerry.system;

import com.jerry.config.ServerConfig;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.mockito.Mockito.doReturn;

/**
 * Created by son on 2019-03-09.
 */
public class SystemServiceTest {
    @Mock ServerConfig serverConfig;
    private SystemService systemService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        systemService = new SystemService(serverConfig);
    }

    @Test
    public void getUsageTest() {
        Map<String, Integer> result = systemService.getUsage();
        System.out.println(result.get("cpu"));
        System.out.println(result.get("memory"));
    }

    @Test
    public void tomcatVersionTest() {
        doReturn("D:/backup/log.txt").when(serverConfig).getLogFilePath();
        System.out.println(systemService.tomcatVersion());
    }

    @Test
    public void getBranchTest() {
        doReturn("D:/backup/sourceDir").when(serverConfig).getSourceDirPath();
        System.out.println(systemService.getBranch());
    }
}
