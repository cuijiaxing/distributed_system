package com.jiaxing.manager;

import java.util.HashMap;
import java.util.Map;

import com.jiaxing.models.Host;

public class HostManager {
	//a map to record which process is on which host
	private  Map<String, Host> hostMap = new HashMap<String, Host>();
	
	public void addHost(Host host){
		if(!hasHost(host)){
			hostMap.put(host.getHostName(), host);
			System.out.println("Added host :" + host.getHostName());
		}
	}
	
	public void removeHost(String hostName){
		hostMap.remove(hostName);
		System.out.println("Removed host : " + hostName);
	}
	
	public boolean hasHost(Host host){
		if(this.hostMap.containsKey(host.getHostName())){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean hasHost(String hostName){
		return this.hostMap.containsKey(hostName);
	}
	
	public Host getHostByName(String hostName){
		if(!hasHost(hostName)){
			return null;
		}else{
			return this.hostMap.get(hostName);
		}
	}
}
