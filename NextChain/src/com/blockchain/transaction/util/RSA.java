package com.blockchain.transaction.util;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA {
	private final static BigInteger one = new BigInteger("1");
	private final static SecureRandom random = new SecureRandom();

	private BigInteger privateKey;
	private BigInteger publicKey;
	private BigInteger modulus;

	// generate an N-bit (roughly) public and private key
	RSA(int N) {
		BigInteger p = BigInteger.probablePrime(N / 2, random);
		BigInteger q = BigInteger.probablePrime(N / 2, random);
		BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));

		modulus = p.multiply(q);
		publicKey = new BigInteger("65537"); // common value in practice = 2^16 + 1
		privateKey = publicKey.modInverse(phi);
	}

	BigInteger encrypt(BigInteger message) {
		return message.modPow(publicKey, modulus);
	}
	BigInteger encrypt(byte array[]) {
		return new BigInteger(array).modPow(publicKey, modulus);
	}

	BigInteger decrypt(BigInteger encrypted) {
		return encrypted.modPow(privateKey, modulus);
	}
	byte[] decryptToByteArray(BigInteger encrypted) {
		return encrypted.modPow(privateKey, modulus).toByteArray();
	}

	public String toString() {
		String s = "";
		s += "public  = " + publicKey + "\n";
		s += "private = " + privateKey + "\n";
		s += "modulus = " + modulus;
		return s;
	}

	public static void main(String[] args) {
		int N =16;
		RSA key = new RSA(N);
		System.out.println(key);

		// create random message, encrypt and decrypt
		BigInteger message = new BigInteger(N - 1, random);

		//// create message by converting string to integer
		// String s = "test";
		// byte[] bytes = s.getBytes();
		// BigInteger message = new BigInteger(bytes);

		BigInteger encrypt = key.encrypt(message);
		BigInteger decrypt = key.decrypt(encrypt);
		System.out.println("message   = " + message);
		System.out.println("encrypted = " + encrypt);
		System.out.println("decrypted = " + decrypt);
		
		String strMsg = "hello";

		//// create message by converting string to integer
		// String s = "test";
		// byte[] bytes = s.getBytes();
		// BigInteger message = new BigInteger(bytes);

		encrypt = key.encrypt(strMsg.getBytes());
		byte[] decrMsg = key.decryptToByteArray(encrypt);
		System.out.println("message   = " + message);
		System.out.println("encrypted = " + encrypt);
		System.out.println("decrypted = " + new String(decrMsg));
	}
}
