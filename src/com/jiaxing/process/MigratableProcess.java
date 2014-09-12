package com.jiaxing.process;

import java.io.Serializable;
import java.util.UUID;

import com.jiaxing.io.TransactionalFileInputStream;
import com.jiaxing.io.TransactionalFileOutputStream;

public abstract class MigratableProcess implements Runnable, Serializable{	
	
	public void suspend(){
		suspending = true;
		while (suspending);
	}
	
	public void resume(){
		suspending = false;
	}
	
	protected TransactionalFileInputStream  inFile;
	protected TransactionalFileOutputStream outFile;
	protected String inputFileName = "";
	protected String outputFileName = "";
	public String query;
	protected int inputPos = 0;
	protected volatile boolean suspending = false;
	protected String uid = "";
	
	public MigratableProcess(String[] args) throws Exception{
		if (args.length != 3) {
			System.out.println("usage: GrepProcess <queryString> <inputFile> <outputFile>");
			throw new Exception("Invalid Arguments");
		}
		
		query = args[0];
		inFile = new TransactionalFileInputStream(args[1]);
		outFile = new TransactionalFileOutputStream(args[2], false);
		this.inputFileName = args[1];
		this.outputFileName = args[2];
		this.uid = UUID.randomUUID().toString();
	}
	
	public void migrate(){
		this.suspend();
		this.inFile.depreciate();
		this.outFile.depreciate();
	}

	public TransactionalFileInputStream getInFile() {
		return inFile;
	}

	public void setInFile(TransactionalFileInputStream inFile) {
		this.inFile = inFile;
	}

	public TransactionalFileOutputStream getOutFile() {
		return outFile;
	}

	public void setOutFile(TransactionalFileOutputStream outFile) {
		this.outFile = outFile;
	}

	public String getInputFileName() {
		return inputFileName;
	}

	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public int getInputPos() {
		return inputPos;
	}

	public void setInputPos(int inputPos) {
		this.inputPos = inputPos;
	}

	public boolean isSuspending() {
		return suspending;
	}

	public void setSuspending(boolean suspending) {
		this.suspending = suspending;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String toString(){
		return this.query + "\t" + this.inputFileName + ":" + this.inFile.getPos() + "\t" + this.outputFileName + ":" +
					this.outFile.getPos();
	}
}
