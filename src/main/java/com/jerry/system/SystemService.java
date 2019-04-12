package com.jerry.system;

import java.util.Map;

/**
 * Created by son on 2019-04-12.
 */
public interface SystemService {
	public Map<String, Integer> getUsage();
	public Map<String, String> getInfos();
}
