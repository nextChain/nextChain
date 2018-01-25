package com.blockchain.transaction.transaction;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.blockchain.transaction.block.BlockProtocal;


public class TrasactionSender {
	private String ip;
	private int port;	
	ObjectInputStream ois;
	ObjectOutputStream oos;
	
	public void connect() throws UnknownHostException, IOException, ClassNotFoundException{
		Socket socket = new Socket(ip, port);
		ois = new ObjectInputStream(socket.getInputStream());
		oos = new ObjectOutputStream(socket.getOutputStream());
		
		BlockProtocal blockProtocal = new BlockProtocal();
		blockProtocal.setCmd("hello");
		blockProtocal.setMsg("**");
		oos.writeObject(blockProtocal);
		oos.flush();
		blockProtocal =(BlockProtocal)ois.readObject();
		
		System.out.println("TrasactionSender connect : "+blockProtocal);
	}
	public TrasactionSender(String ip, int port) {
		super();
		this.ip = ip;
		this.port = port;
	}
	public void sendTransaction(Transaction transaction) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		BlockProtocal blockProtocal = new BlockProtocal();
		blockProtocal.setMsg(transaction);
		blockProtocal.setCmd("TR_ADD");		
		
		oos.writeObject(blockProtocal);
		oos.flush();
					
		blockProtocal = (BlockProtocal)ois.readObject();
		System.out.println("TrasactionSender sendTransaction : "+blockProtocal);
		
		
	}
	public void listTransaction()throws Exception {
		// TODO Auto-generated method stub
		BlockProtocal blockProtocal = new BlockProtocal();
		blockProtocal.setCmd("listTransaction");		
		
		oos.writeObject(blockProtocal);	
		oos.flush();		
		blockProtocal = (BlockProtocal)ois.readObject();
		
		System.out.println("TrasactionSender listTransaction : "+blockProtocal);
		System.out.println("tr list");
		@SuppressWarnings("unchecked")
		ArrayList<Transaction> list = (ArrayList<Transaction>)blockProtocal.getMsg();
		for(Transaction tr : list){
			System.out.println(tr);
		}
		
		
	}
	
}
