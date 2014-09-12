package com.jiaxing.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.jiaxing.controller.Controller;
import com.jiaxing.global.Global;
import com.jiaxing.models.Host;
import com.jiaxing.network.MessageSender;

public class Test {
	
	public static String createProcess(Host master, String serverName, String className, List<String> args){
		return MessageSender.clientLaunch(master, serverName, className, args);
	}
	
	public static List<String> getAllProcesses(Host master, String hostName){
		return MessageSender.clientGetAllProcesses(master, hostName);
	}
	
	public static void migrateProcess(Host master, String processName, String fromServer, String toServer){
		MessageSender.clientMigrateProcess(master, processName, fromServer, toServer);
	}
	
	public static void main(String[] args) throws Exception{
		test1();
	}
	
	public static void test2() throws InterruptedException{
		String firstServer = "dragonfly";
		String secondServer = "batman";
		System.out.println(Global.isMaster());
		List<String> processList = Test.getAllProcesses(Global.master, firstServer);
		System.out.println("--------Process in " + firstServer + "---------------");
		for(String p : processList){
			System.out.println(p);
		}
		processList = Test.getAllProcesses(Global.master, secondServer);
		System.out.println("--------Process in " + secondServer + "---------------");
		for(String p : processList){
			System.out.println(p);
		}
	}
	
	
	public static void test1() throws InterruptedException{
		String[] processNames = new String[100];
		String firstServer = "dragonfly";
		String secondServer = "batman";
		System.out.println(Global.isMaster());
		for(int i = 0; i < 1; ++i){
			List<String> list = new ArrayList<String>();
			list.add(i + "");
			list.add("input.txt");
			list.add("output.txt");
			String processName = Test.createProcess(Global.master, firstServer, "com.jiaxing.process.BarkingProcess", list);
			processNames[i] = processName;
		}
		List<String> processList = Test.getAllProcesses(Global.master, firstServer);
		System.out.println("--------Process in " + firstServer + "---------------");
		for(String p : processList){
			System.out.println(p);
		}
		processList = Test.getAllProcesses(Global.master, secondServer);
		System.out.println("--------Process in " + secondServer + "---------------");
		for(String p : processList){
			System.out.println(p);
		}
		Thread.sleep(1000);
		Test.migrateProcess(Global.master, processNames[0], firstServer, secondServer);
		Thread.sleep(1000);
		processList = Test.getAllProcesses(Global.master, firstServer);
		System.out.println("--------Process in " + firstServer + "---------------");
		for(String p : processList){
			System.out.println(p);
		}
		processList = Test.getAllProcesses(Global.master, secondServer);
		System.out.println("--------Process in " + secondServer + "---------------");
		for(String p : processList){
			System.out.println(p);
		}
	}
	
}
