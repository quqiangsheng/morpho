package com.max256.morpho.common.security.rsa;


import java.math.BigInteger;
import java.security.interfaces.RSAPublicKey;

public  interface IRSAService
{
  public abstract RSAPublicKey getDefaultPublicKey();

  public abstract String decryptStringByJs(String paramString);

  public abstract String encodeHex(BigInteger paramBigInteger);
}