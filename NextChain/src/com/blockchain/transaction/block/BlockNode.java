package com.blockchain.transaction.block;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.blockchain.transaction.transaction.Transaction;

public class BlockNode {

	private int port;
	public BlockNode (int port){
		
		this.port = port;
	}
	
	private boolean isRunning = false;	
	
	@SuppressWarnings("resource")
	public void init() throws Exception {
		isRunning = true;
		
		ServerSocket serverSocket = new ServerSocket(port);
		
		do{
			System.out.println("waiting...................");
			final Socket socket = serverSocket.accept();
			new Thread(new Runnable(){				
				public void run(){
				try{
					
					ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
					ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
					
					do{
//						Connection reset Exception 발생
						BlockProtocal blockProtocal =(BlockProtocal)ois.readObject();
						
						processProtocal(blockProtocal);
						blockProtocal.setResult("ServerSend");
						oos.writeObject(blockProtocal);					
						oos.flush();					
						
						
					}while(isRunning);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}).start();
		
	}while(isRunning);

 }
	private List<Transaction> traPool = new ArrayList<Transaction>();
	
	public void processProtocal(BlockProtocal blockProtocal){
		
		
		if(blockProtocal.getCmd().equals("hello")){			
			
			
		}else if(blockProtocal.getCmd().equals("TR_ADD")){
			Transaction tr = (Transaction)blockProtocal.getMsg();
			System.out.println("**transaction : "+tr);
			traPool.add(tr);
			blockProtocal.setMsg("success");
		}else if(blockProtocal.getCmd().equals("listTransaction")){
						
			blockProtocal.setMsg(traPool);
		}
	}
}