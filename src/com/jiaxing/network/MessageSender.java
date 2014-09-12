package com.jiaxing.network;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.jiaxing.command.Commands;
import com.jiaxing.global.Global;
import com.jiaxing.models.Host;
import com.jiaxing.process.*;

public class MessageSender {
	public static boolean sendProcess(Host host, MigratableProcess process){
		boolean result = true;
		try {
			Socket socket = new Socket(host.getHostAddress(), host.getPort());
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(Commands.ACCEPT_PROCESS);
			oos.writeObject(process);
			oos.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	public static String remoteLaunch(Host host, String className, List<String> args){
		String result = null;
		try {
			Socket socket = new Socket(host.getHostAddress(), host.getPort());
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			oos.writeObject(Commands.LAUNCH_PROCESS);
			oos.writeObject(className);
			oos.writeObject(args);
			result = (String)ois.readObject();
			oos.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static void sendMigrateProcessCommand(Host destHost, String processName, Host toHost){
		try {
			Socket socket = new Socket(destHost.getHostAddress(), destHost.getPort());
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(Commands.SEND_PROCESS);
			oos.writeObject(processName);
			oos.writeObject(toHost);
			oos.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void sendPureCommand(Host destHost, String commands){
		try {
			Socket socket = new Socket(destHost.getHostAddress(), destHost.getPort());
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(commands);
			oos.writeObject(Global.host);
			oos.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String clientLaunch(Host master, String serverName, String className, List<String> args){
		String processName = null;
		try {
			Socket socket = new Socket(master.getHostAddress(), master.getPort());
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			oos.writeObject(Commands.LAUNCH_PROCESS);
			oos.writeObject(serverName);
			oos.writeObject(className);;
			oos.writeObject(args);
			processName = (String)ois.readObject();
			oos.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return processName;
	}
	
	public static List<String> getAllProcesses(Host host){
		List<String> processList = new ArrayList<String>();
		try {
			Socket socket = new Socket(host.getHostAddress(), host.getPort());
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			oos.writeObject(Commands.GET_ALL_PROCESSES);
			processList = (List<String>)ois.readObject();
			oos.close();
			ois.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return processList;
	}
	
	
	
	public static List<String> clientGetAllProcesses(Host master, String hostName){
		List<String> processList = new ArrayList<String>();
		try {
			Socket socket = new Socket(master.getHostAddress(), master.getPort());
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			oos.writeObject(Commands.GET_ALL_PROCESSES);
			oos.writeObject(hostName);
			processList = (List<String>)ois.readObject();
			oos.close();
			ois.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return processList;
	}
	
	public void removeProcess(Host host, String processName){
		try {
			Socket socket = new Socket(host.getHostAddress(), host.getPort());
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			oos.writeObject(Commands.REMOVE_PROCESS);
			oos.close();
			ois.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void clientRemoveProcess(Host master, String hostName, String processName){
		
	}
	
	public static void clientMigrateProcess(Host master, String processName, String fromServer, String toServer){
		try {
			Socket socket = new Socket(master.getHostAddress(), master.getPort());
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			oos.writeObject(Commands.MIGRATE_PROCESS);
			oos.writeObject(processName);
			oos.writeObject(fromServer);
			oos.writeObject(toServer);
			oos.close();
			ois.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
