package com.jiaxing.timer;

import com.jiaxing.command.Commands;
import com.jiaxing.global.Global;
import com.jiaxing.models.Host;
import com.jiaxing.network.MessageSender;

public class HeartBeatTimer implements Runnable{
	public static void beat(Host host){
		MessageSender.sendPureCommand(host, Commands.HEART_BEAT);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			beat(Global.master);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
