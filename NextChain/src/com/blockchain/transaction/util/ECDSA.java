package com.blockchain.transaction.util;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Formatter;

public class ECDSA {
	private PublicKey publicKey = null;
	private PrivateKey privateKey = null;

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}
	public void setPublicKey(byte[] publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeyFactory keyFactory = KeyFactory.getInstance("EC");
		this.publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKey));
	}
	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	public ECDSA() throws Exception {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("EC");
		generator.initialize(256);
		KeyPair pair = generator.generateKeyPair();
		publicKey = pair.getPublic();
		privateKey = pair.getPrivate();
		FileOutputStream outputPublic = new FileOutputStream(new File("publicECDSA.key"));
		outputPublic.write(publicKey.getEncoded());
		FileOutputStream outputPrivate = new FileOutputStream(new File("privateECDSA.key"));
		outputPrivate.write(privateKey.getEncoded());
	}

	public static void main(String[] args) throws Exception {
		File publicKeyFile = new File("publicECDSA.key");
		File privateKeyFile = new File("privateECDSA.key");
		PublicKey publicKey = null;
		PrivateKey privateKey = null;

		if (publicKeyFile.exists() && privateKeyFile.exists()) {
			// 파일에서 키 읽어오기
			Path publicFile = Paths.get("publicECDSA.key");
			byte[] publicKeyBytes = Files.readAllBytes(publicFile);
			Path privateFile = Paths.get("privateECDSA.key");
			byte[] privateKeyBytes = Files.readAllBytes(privateFile);
			KeyFactory keyFactory = KeyFactory.getInstance("EC");
			publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
			privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
		} else {
			// 공개키쌍 생성
			KeyPairGenerator generator = KeyPairGenerator.getInstance("EC");
			generator.initialize(256);
			KeyPair pair = generator.generateKeyPair();
			publicKey = pair.getPublic();
			privateKey = pair.getPrivate();
			FileOutputStream outputPublic = new FileOutputStream(new File("publicECDSA.key"));
			outputPublic.write(publicKey.getEncoded());
			FileOutputStream outputPrivate = new FileOutputStream(new File("privateECDSA.key"));
			outputPrivate.write(privateKey.getEncoded());
		}

		System.out.println("공개키: " + bytesToHex(publicKey.getEncoded()));
		System.out.println("개인키: " + bytesToHex(privateKey.getEncoded()));

		// 서명 생성
		String plainText = "오늘도 별이 바람에 스치운다.";
		System.out.println("평문: " + plainText);
		Charset charset = Charset.forName("UTF-8");
		byte[] signature = sign(privateKey, plainText.getBytes(charset));
		System.out.println("서명문: " + bytesToHex(signature));

		// 서명 검증
		boolean verified = verify(publicKey, signature, plainText.getBytes(charset));
		System.out.println("서명검증 = " + verified);
	}

	public static byte[] sign(PrivateKey privateKey, byte[] plainData) throws GeneralSecurityException {
		Signature signature = Signature.getInstance("SHA256withECDSA");
		signature.initSign(privateKey);
		signature.update(plainData);
		byte[] signatureData = signature.sign();
		return signatureData;
	}

	public static boolean verify(PublicKey publicKey, byte[] signatureData, byte[] plainData)
			throws GeneralSecurityException {
		Signature signature = Signature.getInstance("SHA256withECDSA");
		signature.initVerify(publicKey);
		signature.update(plainData);
		return signature.verify(signatureData);
	}

	public static String bytesToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		@SuppressWarnings("resource")
		Formatter formatter = new Formatter(sb);
		for (byte b : bytes) {
			formatter.format("%02x", b);
		}
		return sb.toString();
	}
}
