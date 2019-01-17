package ftn.uns.ac.rs.naucnaCentrala.utils;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Mac;
import java.security.MessageDigest;
import java.security.SecureRandom;

import org.springframework.stereotype.Component;

import sun.misc.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;

public class AES {
	private static final String key = "aesEncryptionKey";

	private static final String initVector = "encryptionIntVec";

	public static String encrypt(String value) {

		try {

			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));

			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");

			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(value.getBytes());

			return Base64.getEncoder().encodeToString(encrypted);

		} catch (Exception ex) {

			ex.printStackTrace();

		}

		return null;

	}

	public static String decrypt(String encrypted) {

		try {

			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));

			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");

			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			System.out.println(encrypted);
			byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8)));

			return new String(original);

		} catch (Exception ex) {

			ex.printStackTrace();

		}

		return null;

	}

	/*public static void main(String[] args) {

		String originalString = "admin@mailinator.com";

		System.out.println("Original String to encrypt - " + originalString);

		String encryptedString = encrypt(originalString);

		System.out.println("Encrypted String - " + encryptedString);

		String decryptedString = decrypt(encryptedString);

		System.out.println("After decryption - " + decryptedString);

	}*/
	
}