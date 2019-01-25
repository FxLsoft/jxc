package com.jeeplus;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

import com.jeeplus.modules.gen.util.a;

public class Test {
	static String productID = "Y2017121841";
	static String license = "65840780B5E2137727BEC58D7D835FE187CD2E92DEA28581977EBD32ECC1606637EB99CB6E767ED83B149B47638F9D1A20EE615073CEC90596F43F2A468A3E5F";
	static String publicKey = "65537";
	static String module = "7895676554377612126675053673296433964718538182351200665675913115728361847036411842194972769116298015398953574664037947420586168168159630635833853468128323";

	public static void main(String[] args) throws Exception {
		System.out.println(a.b1(license));
		InetAddress ia = InetAddress.getLocalHost();
		byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
		NetworkInterface.getByInetAddress(ia);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < mac.length; i++) {
			if (i != 0) {
				sb.append("D");
			}
			String s = Integer.toHexString(mac[i] & 0xFF);
			sb.append(s.length() == 1 ? 0 + s : s);
		}
		System.out.println("" + sb.toString().toUpperCase());
		System.out.println("Serial -> " + getSerial(license));
		System.out.println(a.b1(license));
	}

	public static String getSerial(String license) {
		RSAPublicKey pubKey = getPublicKey(module, publicKey);
		String ming = "";
		try {
			ming = decryptByPublicKey(license, pubKey);
		} catch (Exception localException) {
			ming = "ERROR";
		}
		return ming;
	}

	public static RSAPublicKey getPublicKey(String module, String publicKey) {
		try {
			BigInteger b1 = new BigInteger(module);
			BigInteger b2 = new BigInteger(publicKey);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String decryptByPublicKey(String data, RSAPublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(2, publicKey);

		int key_len = publicKey.getModulus().bitLength() / 8;
		byte[] bytes = data.getBytes();
		byte[] bcd = ASCII_To_BCD(bytes, bytes.length);
		System.err.println(bcd.length);

		String ming = "";
		byte[][] arrays = splitArray(bcd, key_len);
		byte[][] arrayOfByte1;
		int j = (arrayOfByte1 = arrays).length;
		for (int i = 0; i < j; i++) {
			byte[] arr = arrayOfByte1[i];
			ming = ming + new String(cipher.doFinal(arr));
		}
		return ming;
	}

	public static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {
		byte[] bcd = new byte[asc_len / 2];
		int j = 0;
		for (int i = 0; i < (asc_len + 1) / 2; i++) {
			bcd[i] = asc_to_bcd(ascii[(j++)]);
			bcd[i] = ((byte) ((j >= asc_len ? 0 : asc_to_bcd(ascii[(j++)])) + (bcd[i] << 4)));
		}
		return bcd;
	}

	public static byte asc_to_bcd(byte asc) {
		byte bcd;
		if ((asc >= 48) && (asc <= 57)) {
			bcd = (byte) (asc - 48);
		} else {
			if ((asc >= 65) && (asc <= 70)) {
				bcd = (byte) (asc - 65 + 10);
			} else {
				if ((asc >= 97) && (asc <= 102)) {
					bcd = (byte) (asc - 97 + 10);
				} else {
					bcd = (byte) (asc - 48);
				}
			}
		}
		return bcd;
	}

	public static byte[][] splitArray(byte[] data, int len) {
		int x = data.length / len;
		int y = data.length % len;
		int z = 0;
		if (y != 0) {
			z = 1;
		}
		byte[][] arrays = new byte[x + z][];
		for (int i = 0; i < x + z; i++) {
			byte[] arr = new byte[len];
			if ((i == x + z - 1) && (y != 0)) {
				System.arraycopy(data, i * len, arr, 0, y);
			} else {
				System.arraycopy(data, i * len, arr, 0, len);
			}
			arrays[i] = arr;
		}
		return arrays;
	}
}
