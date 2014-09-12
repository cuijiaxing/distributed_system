package com.jiaxing.models;

import java.io.Serializable;

public class Host implements Serializable {
	private String hostName;
	private String hostAddress;
	private int port;
	
	public Host(){
		
	}
	public Host(String hostName, String hostAddress, int port){
		this.hostName = hostName;
		this.hostAddress = hostAddress;
		this.port = port;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getHostAddress() {
		return hostAddress;
	}
	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
}
