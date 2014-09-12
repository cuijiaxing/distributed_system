package com.jiaxing.io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;

public class TransactionalFileOutputStream extends OutputStream implements Serializable{
	
	private String outputFileName = "";//the file name of the output filename
	private int pos = 0;
	private transient FileOutputStream output = null;
	private boolean append = false;

	public TransactionalFileOutputStream(String string) {
		this.outputFileName = string;
	}
	
	public TransactionalFileOutputStream(String fileName, boolean append){
		this.outputFileName = fileName;
		this.append = append;
	}
	
	public void depreciate(){
		try {
			if(this.output != null){
				this.output.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.output = null;
	}
	
	public void ensureOpen() throws FileNotFoundException{
		if(this.output == null){
			if(this.pos == 0){
				this.output = new FileOutputStream(this.outputFileName, append);
			}else{
				this.output = new FileOutputStream(this.outputFileName, true);
			}
		}
	}

	@Override
	public void write(int input) throws IOException {
		ensureOpen();
		this.output.write(input);
		++this.pos;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public FileOutputStream getOutput() {
		return output;
	}

	public void setOutput(FileOutputStream output) {
		this.output = output;
	}

	public boolean isAppend() {
		return append;
	}

	public void setAppend(boolean append) {
		this.append = append;
	}
	
}
