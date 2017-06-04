package com.max256.morpho.common.security.rsa;

import com.max256.morpho.common.security.util.RSAUtils;
import java.math.BigInteger;
import java.security.interfaces.RSAPublicKey;
import org.apache.commons.codec.binary.Hex;

public class RSAService implements IRSAService {
	public RSAPublicKey getDefaultPublicKey() {
		return RSAUtils.getDefaultPublicKey();
	}

	public String decryptStringByJs(String encrypttext) {
		return RSAUtils.decryptStringByJs(encrypttext);
	}

	public String encodeHex(BigInteger key) {
		return new String(Hex.encodeHex(key.toByteArray()));
	}
}