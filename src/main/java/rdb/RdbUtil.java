package rdb;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RdbUtil {
    private RdbUtil() {

	}

	private static final String REDIS_MAGIC_STR = "REDIS";

	public static List<Integer> openRdbFile(String dir, String fileName) throws IOException {
		var bytes = Files.readAllBytes(Paths.get(dir + "/" + fileName));
		var result = IntStream.range(0, bytes.length)
			.mapToObj(i -> Byte.toUnsignedInt(bytes[i]))
			.toList();

		if (!validateRdb(result)) {
			throw new IllegalArgumentException("redis failed - invalid rdb file");
		}

		return result;
	}

	public static boolean validateRdb(List<Integer> bytes) {
		return REDIS_MAGIC_STR.equals(convertIntByteListToString(bytes.subList(0, 5)));
	}

	public static List<Integer> convertStringToByIntByteList(String str) {
		var bytes = str.getBytes(Charset.defaultCharset());
		return IntStream.range(0, bytes.length)
			.mapToObj(i -> Byte.toUnsignedInt(bytes[i]))
			.toList();
	}

	public static String convertIntByteListToString(List<Integer> bytes) {
		return bytes.stream()
			.map(Character::toString)
			.collect(Collectors.joining());
	}
}
