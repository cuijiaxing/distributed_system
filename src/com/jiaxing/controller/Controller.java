package com.jiaxing.controller;

import java.util.List;

import com.jiaxing.global.Global;
import com.jiaxing.models.Host;
import com.jiaxing.network.MessageSender;

public class Controller {
	public static String launghProcessInSlave(String hostName, String className, List<String> args){
		Host destHost = Global.hostManager.getHostByName(hostName);
		return MessageSender.remoteLaunch(destHost, className, args);
	}
	
	public static void sendMigrateProcessCommand(String processName, String fromHostName,
			String toHostName){
		Host fromHost = Global.hostManager.getHostByName(fromHostName);
		Host toHost = Global.hostManager.getHostByName(toHostName);
		MessageSender.sendMigrateProcessCommand(fromHost, processName, toHost);
	}
	
	public static List<String> getAllProcesses(String hostName){
		Host host = Global.hostManager.getHostByName(hostName);
		return MessageSender.getAllProcesses(host);
	}
	
	
}
