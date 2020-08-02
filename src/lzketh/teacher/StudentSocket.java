package lzketh.teacher;

import lzketh.SData;
import lzketh.Sign;
import lzketh.TCPOperate;
import lzketh.Utils;
import lzketh.model.StudentModel;
import lzketh.model.StudnetService;

import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

public class StudentSocket {
	public Socket socket;
	public boolean logined = false;
	public boolean talk = false;
	public String username;
	public String password;
	public int studentId = 1;
	public Component panel = null;
	public long focused = 0L;

	public StudentSocket(Socket socket) {
		this.socket = socket;
	}

	public String validLogin(SData sData) {
		byte sign = sData.sign;
		byte[] data = sData.data;
		Utils.log("userAndPass bytes %s", Arrays.toString(sData.data));
		String result = Sign.Failure;
		if (sign == Sign.userAndPass) {
			// 得到 username password
			String s = new String(data);
			String[] userAndPass = s.split("#", 2);
			Utils.log("userAndPass %s", s);
			String username = userAndPass[0];
			String password = userAndPass[1];
			Utils.log("validLogin message %s %s", username, password);

			// 验证
			StudentModel sm = StudnetService.valid(username, password);
			if (sm != null) {
				this.username = sm.username;
				this.password = sm.password;
				this.studentId = sm.id;
				this.logined = true;
				result = Sign.Success;
			}
			// 返回结果
			SData successMs = new SData(Sign.loginResult, result);
			TCPOperate.sendData(this.socket, successMs);
		}
		return result;
	}

}
