package com.max256.morpho.common.security.util;

import java.math.BigInteger;
import java.security.InvalidParameterException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class RSAUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(RSAUtils.class);
	@SuppressWarnings("unused")
	private static final String ALGORITHOM = "RSA";
	@SuppressWarnings("unused")
	private static final int KEY_SIZE = 1024;
	private static final Provider DEFAULT_PROVIDER = new BouncyCastleProvider();

	private static KeyPairGenerator keyPairGen = null;
	private static KeyFactory keyFactory = null;

	private static KeyPair oneKeyPair = null;

	public static synchronized KeyPair generateKeyPair() {
		try {
			keyPairGen.initialize(1024,
					new SecureRandom(DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMdd").getBytes()));
			oneKeyPair = keyPairGen.generateKeyPair();
			return oneKeyPair;
		} catch (InvalidParameterException ex) {
			LOGGER.error("KeyPairGenerator does not support a key length of 1024.", ex);
		} catch (NullPointerException ex) {
			LOGGER.error("RSAUtils#KEY_PAIR_GEN is null, can not generate KeyPairGenerator instance.", ex);
		}
		return null;
	}

	public static KeyPair getKeyPair() {
		return oneKeyPair;
	}

	public static void setKeyPair(KeyPair redisKeyPair) {
		oneKeyPair = redisKeyPair;
	}

	public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) {
		RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(publicExponent));
		try {
			return (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
		} catch (InvalidKeySpecException ex) {
			LOGGER.error("RSAPublicKeySpec is unavailable.", ex);
		} catch (NullPointerException ex) {
			LOGGER.error("RSAUtils#KEY_FACTORY is null, can not generate KeyFactory instance.", ex);
		}
		return null;
	}

	public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) {
		RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus),
				new BigInteger(privateExponent));
		try {
			return (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);
		} catch (InvalidKeySpecException ex) {
			LOGGER.error("RSAPrivateKeySpec is unavailable.", ex);
		} catch (NullPointerException ex) {
			LOGGER.error("RSAUtils#KEY_FACTORY is null, can not generate KeyFactory instance.", ex);
		}
		return null;
	}

	public static RSAPrivateKey getRSAPrivateKey(String hexModulus, String hexPrivateExponent) {
		if ((StringUtils.isBlank(hexModulus)) || (StringUtils.isBlank(hexPrivateExponent))) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(
						"hexModulus and hexPrivateExponent cannot be empty. RSAPrivateKey value is null to return.");
			}
			return null;
		}
		byte[] modulus = null;
		byte[] privateExponent = null;
		try {
			modulus = Hex.decodeHex(hexModulus.toCharArray());
			privateExponent = Hex.decodeHex(hexPrivateExponent.toCharArray());
		} catch (DecoderException ex) {
			LOGGER.error("hexModulus or hexPrivateExponent value is invalid. return null(RSAPrivateKey).");
		}
		if ((modulus != null) && (privateExponent != null)) {
			return generateRSAPrivateKey(modulus, privateExponent);
		}
		return null;
	}

	public static RSAPublicKey getRSAPublidKey(String hexModulus, String hexPublicExponent) {
		if ((StringUtils.isBlank(hexModulus)) || (StringUtils.isBlank(hexPublicExponent))) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("hexModulus and hexPublicExponent cannot be empty. return null(RSAPublicKey).");
			}
			return null;
		}
		byte[] modulus = null;
		byte[] publicExponent = null;
		try {
			modulus = Hex.decodeHex(hexModulus.toCharArray());
			publicExponent = Hex.decodeHex(hexPublicExponent.toCharArray());
		} catch (DecoderException ex) {
			LOGGER.error("hexModulus or hexPublicExponent value is invalid. return null(RSAPublicKey).");
		}
		if ((modulus != null) && (publicExponent != null)) {
			return generateRSAPublicKey(modulus, publicExponent);
		}
		return null;
	}

	public static byte[] encrypt(PublicKey publicKey, byte[] data) throws Exception {
		Cipher ci = Cipher.getInstance("RSA", DEFAULT_PROVIDER);
		ci.init(1, publicKey);
		return ci.doFinal(data);
	}

	public static byte[] decrypt(PrivateKey privateKey, byte[] data) throws Exception {
		Cipher ci = Cipher.getInstance("RSA", DEFAULT_PROVIDER);
		ci.init(2, privateKey);
		return ci.doFinal(data);
	}

	public static String encryptString(PublicKey publicKey, String plaintext) {
		if ((publicKey == null) || (plaintext == null)) {
			return null;
		}
		byte[] data = plaintext.getBytes();
		try {
			byte[] en_data = encrypt(publicKey, data);
			return new String(Hex.encodeHex(en_data));
		} catch (Exception ex) {
			LOGGER.error(ex.getCause().getMessage());
		}
		return null;
	}

	public static String encryptString(String plaintext) {
		if (plaintext == null) {
			return null;
		}
		byte[] data = plaintext.getBytes();
		KeyPair keyPair = getKeyPair();
		try {
			byte[] en_data = encrypt((RSAPublicKey) keyPair.getPublic(), data);
			return new String(Hex.encodeHex(en_data));
		} catch (NullPointerException ex) {
			LOGGER.error("keyPair cannot be null.");
		} catch (Exception ex) {
			LOGGER.error(ex.getCause().getMessage());
		}
		return null;
	}

	public static String decryptString(PrivateKey privateKey, String encrypttext) {
		if ((privateKey == null) || (StringUtils.isBlank(encrypttext)))
			return null;
		try {
			byte[] en_data = Hex.decodeHex(encrypttext.toCharArray());
			byte[] data = decrypt(privateKey, en_data);
			return new String(data);
		} catch (Exception ex) {
			LOGGER.error(String.format("\"%s\" Decryption failed. Cause: %s",
					new Object[] { encrypttext, ex.getCause().getMessage() }));
		}
		return null;
	}

	public static String decryptString(String encrypttext) {
		if (StringUtils.isBlank(encrypttext)) {
			return null;
		}
		KeyPair keyPair = getKeyPair();
		try {
			byte[] en_data = Hex.decodeHex(encrypttext.toCharArray());
			byte[] data = decrypt((RSAPrivateKey) keyPair.getPrivate(), en_data);
			return new String(data);
		} catch (NullPointerException ex) {
			LOGGER.error("keyPair cannot be null.");
		} catch (Exception ex) {
			LOGGER.error(String.format("\"%s\" Decryption failed. Cause: %s",
					new Object[] { encrypttext, ex.getMessage() }));
		}
		return null;
	}

	public static String decryptStringByJs(String encrypttext) {
		String text = decryptString(encrypttext);
		if (text == null) {
			return null;
		}
		return StringUtils.reverse(text);
	}

	public static RSAPublicKey getDefaultPublicKey() {
		KeyPair keyPair = getKeyPair();
		if (keyPair != null) {
			return (RSAPublicKey) keyPair.getPublic();
		}
		return null;
	}

	public static RSAPrivateKey getDefaultPrivateKey() {
		KeyPair keyPair = getKeyPair();
		if (keyPair != null) {
			return (RSAPrivateKey) keyPair.getPrivate();
		}
		return null;
	}

	static {
		try {
			keyPairGen = KeyPairGenerator.getInstance("RSA", DEFAULT_PROVIDER);
			keyFactory = KeyFactory.getInstance("RSA", DEFAULT_PROVIDER);
		} catch (NoSuchAlgorithmException ex) {
			LOGGER.error(ex.getMessage());
		}
	}
}