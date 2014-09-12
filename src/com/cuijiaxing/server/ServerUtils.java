package com.cuijiaxing.server;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerUtils {
	
	public static String getIpAddress(){
		try {
			InetAddress addr = InetAddress.getLocalHost();
			return addr.getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args){
		System.out.println(ServerUtils.getIpAddress());
	}
}
