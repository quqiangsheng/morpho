package com.max256.morpho.common.security.esapi;

import java.io.IOException;

import javax.crypto.SecretKey;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Encoder;
import org.owasp.esapi.Encryptor;
import org.owasp.esapi.crypto.CipherText;
import org.owasp.esapi.crypto.PlainText;
import org.owasp.esapi.errors.EncryptionException;
import org.owasp.esapi.errors.IntegrityException;

/**
 * ESAPI加解密API
 * @author fbf
 * @since 2016年10月11日 上午10:39:23
 * @version V1.0
 * 
 */
public class ESAPIEncryptor {
	  private static final Encryptor encryptor = ESAPI.encryptor();

	  private static final Encoder encoder = ESAPI.encoder();
	  @SuppressWarnings("unused")
	  private static final int hash_iterations = 1024;

	  public String encrypt(String text)throws EncryptException
	  {
	    PlainText plainText = new PlainText(text);
	    try {
	      CipherText cipherText = encryptor.encrypt(plainText);
	      return encoder.encodeForBase64(cipherText.asPortableSerializedByteArray(), false); } 
	    	catch (EncryptionException e) {
	    		throw new EncryptException(e);
	    }
	  }

	  public String encrypt(SecretKey key, String text) throws EncryptException
	  {
	    PlainText plainText = new PlainText(text);
	    CipherText cipherText = null;
	    try {
	      cipherText = encryptor.encrypt(key, plainText);
	      return encoder.encodeForBase64(cipherText.asPortableSerializedByteArray(), false); } 
	    	catch (EncryptionException e) {
	    	  throw new EncryptException(e);
	    }
	  }

	  public String decrypt(byte[] data) throws EncryptException
	  {
	    try
	    {
	      PlainText plainText = encryptor.decrypt(CipherText.fromPortableSerializedBytes(data));
	      return plainText.toString(); } 
	    	catch (EncryptionException e) {
	    	  throw new EncryptException(e);
	    }
	  }

	  public String decrypt(String text)throws IOException, EncryptException
	  {
	    byte[] data = encoder.decodeFromBase64(text);
	    return decrypt(data);
	  }

	  public String decrypt(SecretKey key, byte[] data) throws EncryptException
	  {
	    try
	    {
	      PlainText plainText = encryptor.decrypt(key, CipherText.fromPortableSerializedBytes(data));
	      return plainText.toString(); } 
	      	catch (EncryptionException e) {
	    	  throw new EncryptException(e);
	    }
	  }

	  public String decrypt(SecretKey key, String text) throws EncryptException, IOException
	  {
	    byte[] data = encoder.decodeFromBase64(text);
	    return decrypt(key, data);
	  }

	  public String hash(String text, String salt) throws EncryptException
	  {
	    return hash(text, salt, 1024);
	  }

	  public String hash(String text, String salt, int iterations)throws EncryptException
	  {
	    try
	    {
	      return encryptor.hash(text, salt, iterations); } 
	    	catch (EncryptionException e) {
	    	  throw new EncryptException(e);
	    }
	  }

	  public String sign(String text) throws EncryptException
	  {
	    try
	    {
	      return encryptor.sign(text); } 
	    	catch (EncryptionException e) {
	    	  throw new EncryptException(e);
	    }
	  }

	  public boolean verifySignature(String sign, String data)
	  {
	    return encryptor.verifySignature(sign, data);
	  }

	  public String seal(String text, long expiration)throws EncryptException
	  {
	    try
	    {
	      return encryptor.seal(text, expiration); } 
	    	catch (IntegrityException e) {
	    	  throw new EncryptException(e);
	    }
	  }

	  public String unseal(String text)throws EncryptException
	  {
	    try
	    {
	      return encryptor.unseal(text); }
	    	catch (EncryptionException e) {
	    	  throw new EncryptException(e);
	    }
	  }

	  public long getCurrentTime()
	  {
	    return encryptor.getTimeStamp();
	  }

	  public long getRelativeTime(long offset)
	  {
	    return encryptor.getRelativeTimeStamp(offset);
	  }
}
