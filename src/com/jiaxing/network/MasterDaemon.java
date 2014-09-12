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

public class MasterDaemon extends Daemon{
	
	public int portNumber;
	public MasterDaemon(int portNumber){
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
				if(command.equals(Commands.MIGRATE_PROCESS)){
					//command form is processName, fromHost, toHost
					String processName = (String)ois.readObject();
					String fromHostName = (String)ois.readObject();
					String toHostName = (String)ois.readObject();
					Controller.sendMigrateProcessCommand(processName, fromHostName, toHostName);
				}else
					if(command.equals(Commands.LAUNCH_PROCESS)){
						//command form is: destHost, className, args
						String destName = (String)ois.readObject();
						String className = (String)ois.readObject();
						List<String> args = (List<String>)ois.readObject();
						String processName = Controller.launghProcessInSlave(destName, className, args);
						oos.writeObject(processName);
					}
					else
						if(command.equals(Commands.HEART_BEAT)){
							Host host = (Host)ois.readObject();
							//update server information here
							Global.hostManager.addHost(host);
						}
						else
							if(command.equals(Commands.GET_ALL_PROCESSES)){
								String hostName = (String)ois.readObject();
								List<String> processList = Controller.getAllProcesses(hostName);
								oos.writeObject(processList);
							}
				ois.close();
				oos.close();
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
		}
	}
}
