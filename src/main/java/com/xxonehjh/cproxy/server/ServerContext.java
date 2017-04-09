package com.xxonehjh.cproxy.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xxonehjh.cproxy.server.inner.InnerChannelManage;
import com.xxonehjh.cproxy.server.outer.OuterChannelManage;

public class ServerContext {

	private ServerConfig config;
	private Map<Integer, InnerChannelManage> innerChannelManages = new ConcurrentHashMap<>();
	private OuterChannelManage outerChannelManage;

	public ServerContext(String configPath) {
		config = new ServerConfig(configPath);
		innerChannelManages = new ConcurrentHashMap<>();
		outerChannelManage = new OuterChannelManage(this);
	}

	public ServerConfig getConfig() {
		return config;
	}

	public InnerChannelManage getInnerChannelManage(int port) {
		InnerChannelManage result = innerChannelManages.get(port);
		if (null == result) {
			synchronized (this) {
				result = innerChannelManages.get(port);
				if (null == result) {
					result = new InnerChannelManage(this, port);
					innerChannelManages.put(port, result);
				}
			}
		}
		return result;
	}

	public OuterChannelManage getOuterChannelManage() {
		return outerChannelManage;
	}

}
