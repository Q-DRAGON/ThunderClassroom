package lzketh;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class SData {
	public int len;
	public byte sign;
	public byte[] data;

	public SData() {
	}

	public SData(byte sign, byte[] data) {
		this.sign = sign;
		this.data = data;
		this.len = data.length;
	}

	public SData(byte sign, String data) {
		this.sign = sign;
		this.data = data.getBytes(StandardCharsets.UTF_8);
		this.len = this.data.length;
	}

	public static SData deCompressData(byte sign, byte[] data) {
		data = Zlib.decompressByteArray(data);
		SData d = new SData(sign, data);
		return d;
	}

	public byte[] packData() {
		// 数据格式
		// 前四个字节 是 数据的长度
		// 第五个字节 是 数据的标识符
		byte[] data = this.data;
		byte sign = this.sign;
		data = Zlib.compressByteArray(data);
		int bytePerLen = 4;
		int len = data.length;
		byte[] lenBytes = Utils.numToBytes(len, bytePerLen);
		// 长度后面插入 sign
		int bytePerSign = 1;
		byte[] signBytes = new byte[bytePerSign];
		signBytes[0] = sign;

		byte[] lenAndSign = Utils.byteMerger(lenBytes, signBytes);
		return Utils.byteMerger(lenAndSign, data);
	}

	@Override
	public String toString() {
		return "SData{" +
				"len=" + len +
				", sign=" + sign +
				", data=" + Arrays.toString(Arrays.copyOfRange(data, 0, 5)) +
				"...}";
	}
}
