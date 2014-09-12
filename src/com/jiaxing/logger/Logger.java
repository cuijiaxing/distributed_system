package com.jiaxing.logger;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import com.jiaxing.global.Global;


public class Logger {
	private static PrintWriter writer = null;
	static{
		try {
			Logger.writer = new PrintWriter(Global.host.getHostName());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void log(String message){
		if(writer != null){
			writer.print(message);
		}
	}
}
