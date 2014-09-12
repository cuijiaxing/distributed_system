package com.jiaxing.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

public class TransactionalFileInputStream extends java.io.InputStream implements Serializable{
	
	private int pos = 0;//record the current reading line number of file
	private String fileName = null;//the file name of the input file
	private transient FileInputStream input = null;
	
	public TransactionalFileInputStream(String string) {
		this.fileName = string;
		pos = 0;
	}
	
	public void depreciate(){
		if(this.input != null){
			try {
				this.input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.input = null;
	}

	
	public void seek() throws IOException{
		for(int i = 0; i < this.pos; ++i){
			this.input.read();
		}
	}
	public void ensureStream() throws IOException{
		if(this.input == null){
			this.input = new FileInputStream(this.fileName);
			//seek to the certain position
			seek();
		}
	}

	@Override
	public int read() throws IOException {
		ensureStream();
		++this.pos;
		return this.input.read();
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public FileInputStream getInput() {
		return input;
	}

	public void setInput(FileInputStream input) {
		this.input = input;
	}
	
	
	
}
