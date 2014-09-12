package com.jiaxing.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import com.jiaxing.command.Commands;
import com.jiaxing.controller.Controller;
import com.jiaxing.global.Global;
import com.jiaxing.models.Host;
import com.jiaxing.process.MigratableProcess;

public class SlaveDaemon extends Daemon{
	
	public final int portNumber;
	public SlaveDaemon(int portNumber){
		this.portNumber = portNumber;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ServerSocket serverSocket = new ServerSocket(this.portNumber);
			Socket clientSocket = null;
			while((clientSocket = serverSocket.accept()) != null){
				ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
				String command = (String)ois.readObject();
				if(command.equals(Commands.HEART_BEAT)){
					Host host = (Host)ois.readObject();
					//update server information here
					Global.hostManager.addHost(host);
				}else
					if(command.equals(Commands.SEND_PROCESS)){
						//send process to other machine
						//command format is: processName, toHost
						String processName = (String)ois.readObject();
						Host destHost = (Host)ois.readObject();
						MigratableProcess process = Global.processManager.removeProcess(processName);
						MessageSender.sendProcess(destHost, process);
					}else
						if(command.equals(Commands.ACCEPT_PROCESS)){
							//accept process from other machines
							MigratableProcess process = (MigratableProcess)ois.readObject();
							//add process to this machine's process list
							Global.processManager.addProcess(process);
							System.out.println(process);
						}
						else
							if(command.equals(Commands.LAUNCH_PROCESS)){
								//command format is: className, args
								String className = (String)ois.readObject();
								List<String> list = (List<String>)ois.readObject();
								MigratableProcess process = Global.processManager.launch(className, list.toArray(new String[list.size()]));
								//send uid back to user
								oos.writeObject(process.getUid());
							}
							else
								if(command.equals(Commands.KILL_PROCESS)){
									String processName = (String)ois.readObject();
									MigratableProcess process = Global.processManager.removeProcess(processName);
								}
								else
									if(command.equals(Commands.GET_ALL_PROCESSES)){
										oos.writeObject(Global.processManager.getAllProcesses());
									}
				oos.close();
				ois.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
