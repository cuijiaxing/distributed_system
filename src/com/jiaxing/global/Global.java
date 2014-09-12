package com.jiaxing.global;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.cuijiaxing.server.ServerUtils;
import com.jiaxing.manager.HostManager;
import com.jiaxing.manager.ProcessManager;
import com.jiaxing.models.Host;

public class Global {
	public static ProcessManager processManager = null;
	public static HostManager hostManager = null;
	public static Host host = null;
	public static Host master = null;
	//prepare the global information
	static{
		Global.master = getMaster();
		if(Global.isMaster()){
			Global.host = Global.master;
		}else{
			Global.host = getSelfInfo();
		}
		Global.processManager = new ProcessManager();
		Global.hostManager = new HostManager();
		//set self ip address
		Global.host.setHostAddress(ServerUtils.getIpAddress());
	}
	
	public static Host getSelfInfo(){
		Host host = new Host();
		try {
			Properties prop = new Properties();
			InputStream input = new FileInputStream("config.properties");
			prop.load(input);
			host.setPort(Integer.parseInt(prop.getProperty("self_port")));
			host.setHostName(prop.getProperty("self_name"));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return host;
	}
	
	public static boolean isMaster(){
		boolean result = false;
		try {
			Properties prop = new Properties();
			InputStream input = new FileInputStream("config.properties");
			prop.load(input);
			String isMaster = prop.getProperty("is_master");
			result = Integer.parseInt(isMaster) == 1 ? true : false;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	public static Host getMaster(){
		Host host = new Host();
		try {
			Properties prop = new Properties();
			InputStream input = new FileInputStream("config.properties");
			prop.load(input);
			String hostIp = prop.getProperty("master_ip");
			int hostPort = Integer.parseInt(prop.getProperty("master_port"));
			host.setHostAddress(hostIp);
			host.setPort(hostPort);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return host;
	}
	
}
