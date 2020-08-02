package lzketh;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class Utils {

	public static void save(String path, String data) {
		try (FileOutputStream out = new FileOutputStream(path)) {
			out.write(data.getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			String s = String.format("Save file <%s>, error: <%s>", path, e);
			throw new RuntimeException(s);
		}
	}

	public static String load(String path) {
		try (FileInputStream is = new FileInputStream(path)) {
			String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
			return content;
		} catch (IOException e) {
			String s = String.format("load file <%s>, error: <%s>", path, e);
			throw new RuntimeException(s);
		}
	}

	public static void log(String format, Object... args) {
		System.out.println(String.format(format, args));
	}

	public static byte[] numToBytes(int num, int bytePerNum) {
		byte[] r = new byte[bytePerNum];
		int current = num;
		for (int i = 0; i < bytePerNum; i++) {
			int j = current % 127;
			r[i] = (byte) j;
			current = current / 127;
		}
		return r;
	}

	public static byte[] byteMerger(byte[] bt1, byte[] bt2) {
		byte[] bt3 = new byte[bt1.length+bt2.length];
		System.arraycopy(bt1, 0, bt3, 0, bt1.length);
		System.arraycopy(bt2, 0, bt3, bt1.length, bt2.length);
		return bt3;
	}

	public static int byteToInt(byte b) {
		int r = (int) b;
		if (r < 0) {
			r = r + 256;
		}
		return r;
	}

	public static int bytesToNum(byte[] bytes, int start, int end) {
		int r = 0;
		int c = 1;
		for (int i = start; i < end + 1; i++) {
			int b = byteToInt(bytes[i]);
			r += b * c;
			c *= 127;
		}
		return r;
	}

	public static byte[] getBytes(String str) {
		return str.getBytes(StandardCharsets.UTF_8);
	}

	public static String toString(byte[] bytes) {
		return new String(bytes, StandardCharsets.UTF_8);
	}

	public static void main(String[] args) {
	}

}
