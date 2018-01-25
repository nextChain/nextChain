package com.blockchain.transaction.main;

import java.util.Date;

import com.blockchain.transaction.transaction.Transaction;
import com.blockchain.transaction.transaction.TransactionManager;
import com.blockchain.transaction.transaction.TrasactionSender;
import com.blockchain.transaction.util.Base64BC;
import com.blockchain.transaction.util.ECDSA;

public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		//�궎�뙇�깮�꽦
		ECDSA ecdsa = new ECDSA();
		//publickey and privatre key �깮�꽦 
		String publicKey = Base64BC.encode(ecdsa.getPublicKey().getEncoded());
				
		Transaction transaction = new Transaction();
		transaction.setVersion(1);
		transaction.setMsg("hello");
		transaction.setPublicKey(publicKey);
		transaction.setRegDate(new Date());
		
		TransactionManager tm = new TransactionManager();		
		tm.signTransaction(transaction, ecdsa);
			
		System.out.println("print transaction : "+transaction);
		
		TrasactionSender trsender = new TrasactionSender("localhost",8089);
		trsender.connect();
		trsender.sendTransaction(transaction);
		trsender.listTransaction();
		
	// test 1	

	}

}
