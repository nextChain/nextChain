package com.blockchain.transaction.transaction;
import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {
	
	private int version;
	private String msg;
	private String sign;
	private Date regDate;
	private String publicKey;
	private String txid;

	public String getTxid() {
		return txid;
	}

	public void setTxid(String txid) {
		this.txid = txid;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	@Override
	public String toString() {
		return "Transaction [version=" + version + ", msg=" + msg + ", sign=" + sign + ", regDate=" + regDate
				+ ", publicKey=" + publicKey + ", txid=" + txid + "]";
	}

}
