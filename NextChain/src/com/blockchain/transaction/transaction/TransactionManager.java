package com.blockchain.transaction.transaction;

import java.security.GeneralSecurityException;

import com.blockchain.transaction.util.Base64BC;
import com.blockchain.transaction.util.ECDSA;

public class TransactionManager {

	public void signTransaction(Transaction transaction, ECDSA wallet) throws GeneralSecurityException{
		
		String orimsg = transaction.getVersion()
				+transaction.getMsg()
				+transaction.getRegDate();
		
		@SuppressWarnings("static-access")
		byte[] signByte = wallet.sign(wallet.getPrivateKey(), orimsg.getBytes());
		
		transaction.setSign(Base64BC.encode(signByte));
		
		
		
	}
}
