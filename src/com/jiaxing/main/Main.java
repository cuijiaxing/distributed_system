package com.jiaxing.main;

import com.jiaxing.global.Global;
import com.jiaxing.network.Daemon;
import com.jiaxing.network.MasterDaemon;
import com.jiaxing.network.SlaveDaemon;
import com.jiaxing.timer.HeartBeatTimer;

public class Main {
	public static void main(String[] args){
		Daemon server = null;
		if(Global.isMaster()){
			server = new MasterDaemon(Global.master.getPort());
		}else{
			server = new SlaveDaemon(Global.host.getPort());
			new Thread(new HeartBeatTimer()).start();
		}
		new Thread(server).start();
		System.out.println("Server started at port: " + Global.host.getPort());
	}
}
