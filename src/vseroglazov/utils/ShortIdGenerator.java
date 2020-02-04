package vseroglazov.utils;

public class ShortIdGenerator {

	private static final char[] map =  "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
			.toCharArray();

	static String generateShortUrl(Long id){

		final StringBuilder buildShortUrl = new StringBuilder();

		final long devider = 62L;
		long remaining = id;

		while(remaining != 0){

			buildShortUrl.append(map[(int)(remaining % devider)]);

			remaining = remaining / devider;
		}

		return buildShortUrl.reverse().toString();
	}


	static Long fromShortUrl(String shortURL){
		long id = 0; // initialize result

		final char[] extractedMap = shortURL.toCharArray();

		// A simple base conversion logic
		for (int i=0; i < shortURL.length(); i++)
		{
			if ('a' <= extractedMap[i] && extractedMap[i] <= 'z')
				id = id*62 + extractedMap[i] - 'a';
			if ('A' <= extractedMap[i] && extractedMap[i] <= 'Z')
				id = id*62 + extractedMap[i] - 'A' + 26;
			if ('0' <= extractedMap[i] && extractedMap[i] <= '9')
				id = id*62 + extractedMap[i] - '0' + 52;
		}
		return id;
	}
}
