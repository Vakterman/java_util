package vseroglazov.utils;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

public class Main {
	public static void main(String[] args) {
		final Random random = new Random();

                /*final String password = "5a208cad-eb14-41a3-b809-a8d16fb4ec71";

                final SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");

                final byte[] salt = "Seroglaz".getBytes();

                final int iterationCount = 5;

                final PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, iterationCount);

                final SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);


                final AlgorithmParameterSpec parameters = new PBEParameterSpec(keySpec.getSalt(), keySpec.getIterationCount());

                final Cipher ecipher = Cipher.getInstance(secretKeyFactory.getAlgorithm());
                ecipher.init(Cipher.ENCRYPT_MODE, secretKey, parameters);

                final Long sourceId = random.nextLong();
                final byte[] encrypted = ecipher.doFinal(longToByte(sourceId));

                String encoded = Base64.getEncoder().encodeToString(encrypted);

                final Cipher dcipher = Cipher.getInstance(secretKeyFactory.getAlgorithm());
                dcipher.init(Cipher.DECRYPT_MODE, secretKey, parameters);

                final byte[] decrypted = dcipher.doFinal(Base64.getDecoder().decode(encoded));*/

		final Long sourceId = Math.abs(random.nextLong());

		System.out.println(String.format("sourceId: %s", sourceId));

		final String hash = ShortIdGenerator.generateShortUrl(sourceId);


		System.out.println(hash);

		System.out.println(ShortIdGenerator.fromShortUrl(hash));
	}

}
