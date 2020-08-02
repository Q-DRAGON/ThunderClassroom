package lzketh;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class TCPOperate {

	public static void socketSendAll(Socket socket, byte[] data) throws IOException {
		try {
			byte[] ms = data;
			Utils.log("socket send data len sign %s", Arrays.toString(Arrays.copyOfRange(ms, 0, 5)));
			OutputStream output = socket.getOutputStream();
			output.write(ms);
		} catch (IOException e) {
			socket.close();
			Utils.log("error socketSendAll %s", e.toString());
		}
	}

	public static void sendData(Socket s, SData sData) {
		try {
			if (sData != null) {
				TCPOperate.socketSendAll(s, sData.packData());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static SData socketRead(Socket socket) {
		SData sData = null;
		Utils.log("socket read start");
		try {
			DataInputStream in = new DataInputStream(socket.getInputStream());
			// 读 前4个字节 (数据长度)
			int bytePerLen = 4;
			byte[] len = new byte[bytePerLen];
			// int size = in.readNBytes(len, 0, len.length);
			int size = in.read(len, 0, bytePerLen);
			// bytes to num
			int maxBufferSize = 0;
			int i = 1;
			for (byte b : len) {
				maxBufferSize += i * b;
				i = i * 127;
			}
			Utils.log("socket %s read size %s", socket.getPort(), maxBufferSize);

			// 读 第5个字节 (数据标识符)
			int bytePerSign = 1;
			byte[] sign = new byte[bytePerSign];
			// size = in.readNBytes(sign, 0, sign.length);
			size = in.read(sign, 0, bytePerSign);

			Utils.log("socket %s read sign %s", socket.getPort(), sign[0]);

			byte[] data = new byte[maxBufferSize];
			// in.readNBytes(data, 0, data.length);
			size = in.read(data, 0, maxBufferSize);
			Utils.log("socketRead 完成 len %s%s sign %s", data.length, Arrays.toString(len), Arrays.toString(sign));
			sData = SData.deCompressData(sign[0], data);
		} catch (IOException e) {
			Utils.log("socketRead error %s", e.getMessage());
		}
		return sData;
	}
}