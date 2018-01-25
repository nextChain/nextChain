package com.blockchain.transaction.block;

import java.io.Serializable;

public class BlockProtocal implements Serializable {	
	
	public int version =1;
	public String cmd;
	public Object msg;
	public String result;
	
	public String getResult(){
		return result;
	}
	
	public void setResult(String result){
		this.result = result;
	}
	
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	
	public Object getMsg() {
		return msg;
	}
	public void setMsg(Object msg) {
		this.msg = msg;
	}
	
	@Override
	public String toString() {
		return "BlockProtocal [version=" + version + ", cmd=" + cmd + ", msg=" + msg + ", ServerResult= "+ result +"]";
	}	
	
	
	
}
