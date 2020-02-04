package vseroglazov.utils;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class HumanReadableHash {

	private final SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
	private static final String DEFAULT_PASSWORD = "5a208cad-eb14-41a3-b809-a8d16fb4ec71";
	private static final int DEFAULT_ITERRATION_COUNT = 5;
	private static final String DEFAULT_SALT = "cip_salt";

	private final Cipher dcipher;
	private final Cipher ecipher;

	public HumanReadableHash()
			throws NoSuchAlgorithmException,
			NoSuchPaddingException,
			InvalidAlgorithmParameterException,
			InvalidKeyException,
			InvalidKeySpecException {

		this(DEFAULT_PASSWORD, DEFAULT_SALT, DEFAULT_ITERRATION_COUNT);
	}

	public HumanReadableHash(String password, String saltString, int iterationCount) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException,
			InvalidKeyException,
			InvalidKeySpecException{

		final byte[] salt = saltString.getBytes();

		final PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, iterationCount);
		final AlgorithmParameterSpec parameters = new PBEParameterSpec(keySpec.getSalt(), keySpec.getIterationCount());

		final SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);

		this.ecipher = Cipher.getInstance(secretKeyFactory.getAlgorithm());
		this.ecipher.init(Cipher.ENCRYPT_MODE, secretKey, parameters);

		this.dcipher = Cipher.getInstance(secretKeyFactory.getAlgorithm());
		this.dcipher.init(Cipher.DECRYPT_MODE, secretKey, parameters);
	}

	public String stringHash(Long id)
			throws BadPaddingException, IllegalBlockSizeException {
		final byte[] encrypted = ecipher.doFinal(longToByte(id));
		return Base64.getEncoder().encodeToString(encrypted);
	}

	public Long fromHash(String hash) throws BadPaddingException, IllegalBlockSizeException {
		final byte[] decrypted = dcipher.doFinal(Base64.getDecoder().decode(hash));
		return bytesToLong(decrypted);
	}

	private static byte[] longToByte(long x){
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.putLong(x);
		return buffer.array();
	}

	private static long bytesToLong(byte[] sourceArray){
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.put(sourceArray);
		buffer.flip();

		return buffer.getLong();
	}
}
