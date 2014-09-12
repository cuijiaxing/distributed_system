package com.jiaxing.process;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;

public class BarkingProcess extends MigratableProcess{

	public BarkingProcess(String[] args) throws Exception {
		super(args);
	}

	@Override
	public void run() {
		try {
			PrintStream out = new PrintStream(outFile);
			DataInputStream in = new DataInputStream(inFile);
			while (!suspending) {
				String line = in.readLine();

				if (line == null) break;
				
				out.println(line);
				System.out.println(this.getUid() + "\t:BARK");
				// Make grep take longer so that we don't require extremely large files for interesting results
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// ignore it
				}
			}
			suspending = false;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	//a simple program to test process
	public static void main(String[] args){
		String[] params = new String[]{"ddd", "input.txt", "output.txt"};
		try {
			MigratableProcess process = new BarkingProcess(params);
			new Thread(process).start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
