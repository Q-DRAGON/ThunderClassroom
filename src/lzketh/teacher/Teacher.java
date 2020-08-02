package lzketh.teacher;

import lzketh.Image.ScreenShot;
import lzketh.SData;
import lzketh.Sign;
import lzketh.Sound.SoundCapture;
import lzketh.TCPOperate;
import lzketh.Utils;
import lzketh.model.Answer;
import lzketh.model.AnswerService;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.BiConsumer;
import java.util.function.Consumer;


public class Teacher {
	ScreenShot cameraTest = new ScreenShot();
	SoundCapture getSound = new SoundCapture();
	public boolean imgOn = false;
	public boolean soundOn = false;
	public ArrayList<StudentSocket> studentSockets = new ArrayList<>();
	private HashMap<Byte, BiConsumer<byte[], StudentSocket>> functionMap = new HashMap<>();
	public TeacherFrame myWin;

	public Teacher(){
		setup();
	}

	private void setup() {
		myWin = new TeacherFrame("雷课堂", this);
		javax.swing.SwingUtilities.invokeLater(myWin);
		this.initFunctionMap();
		this.openSocket();
		this.pushFlow();
	}

	private void openSocket() {
		// 接受学生连接
		int port = 8000;
		Thread t = new Thread(() -> {
			Utils.log("老师开始直播 %s", port);
			try {
				ServerSocket serverSocket = new ServerSocket(port);
				while (true) {
					Socket socket = serverSocket.accept();
					Utils.log("学生连入 %s:%s",
							socket.getLocalAddress().toString(),
							socket.getPort());
					StudentSocket ss = new StudentSocket(socket);
					this.receiveStudentData(ss);
					this.studentSockets.add(ss);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		t.start();
	}

	// 接受学生数据
	private void validLogin(StudentSocket studentSocket) {
		StudentSocket ss = studentSocket;
		Socket s = ss.socket;
		SData sData = TCPOperate.socketRead(s);
		Utils.log("receiveLogin validLogin start");
		String result = ss.validLogin(sData);
		if (result.equals(Sign.Success)) {
			this.myWin.addStudent(ss);
		}
	}

	private void receiveStudentData(StudentSocket studentSocket) {
		StudentSocket ss = studentSocket;
		Teacher self = this;
		Timer timer = new Timer();
		int interval = 1000 / 24;
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (self.studentSockets.contains(ss)) {
					if (ss.logined) {
						SData sData = TCPOperate.socketRead(ss.socket);
						self.distributeData(sData, ss);
					} else {
						validLogin(ss);
					}
				} else {
					timer.cancel();
				}
			}
		}, interval, interval);
	}

	private void distributeData(SData sData, StudentSocket studentSocket) {
		byte sign = sData.sign;
		byte[] data = sData.data;
		BiConsumer<byte[], StudentSocket> f = this.functionMap.get(sign);
		if (f != null) {
			f.accept(data, studentSocket);
		}
	}

	private void initFunctionMap() {
		this.functionMap.put(Sign.studentSound, this::studentSound);
		this.functionMap.put(Sign.answer, this::studentAnswer);
		this.functionMap.put(Sign.studentFocused, this::studentFocused);
	}

	private void studentSound(byte[] sound, StudentSocket studentSocket) {
		Utils.log("收到学生发来的语音");
		// PlaySound.playAudio(sound);
		SData sData = new SData(Sign.sound, sound);
		this.sendDataToStudents(sData);
	}

	private void studentAnswer(byte[] message, StudentSocket studentSocket) {
		// PlaySound.playAudio(sound);
		String ms = Utils.toString(message);
		Utils.log("学生发来的答案 %s", ms);
		Answer a = new Answer();
		String[] msList = ms.split("@@");
		a.questionId = msList[0];
		a.solution = msList[1];
		a.studentId = studentSocket.studentId;
		AnswerService.add(a);
	}

	private void studentFocused(byte[] message, StudentSocket studentSocket) {
		// PlaySound.playAudio(sound);
		String ms = Utils.toString(message);
		long time = Long.parseLong(ms);
		studentSocket.focused += time;
		Utils.log("%s 学生焦点时间 %s", studentSocket.username, studentSocket.focused);
	}

	// 发送数据给学生
	private void pushFlow() {
		ArrayList<StudentSocket> studentSockets = this.studentSockets;
		Teacher self = this;
		Timer timer = new Timer();
		int interval = 1000 / 24;
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
			SData img = self.imgOn ? new SData(Sign.picture, cameraTest.snapShot()) : null;
			SData sound = self.soundOn ? new SData(Sign.sound, getSound.getSound()) : null;
			self.sendDataToStudents(img, sound);
			}
		}, interval, interval);
	}

	public void sendDataToStudents(SData... data) {
		for (StudentSocket ss : studentSockets) {
			if (ss.socket.isClosed()) {
				this.myWin.removeStudent(ss);
				studentSockets.remove(ss);
				// 不 break 就报错.
				break;
			} else {
				if (ss.logined) {
					Socket s = ss.socket;
					for (SData sData : data) {
						TCPOperate.sendData(s, sData);
					}
				}
			}
		}
	}

	public void studentTalkControl(StudentSocket studentSocket) {
		Thread t = new Thread(() -> {
			String control = "talkControl";
			SData sData = new SData(Sign.talkControl, control);
			TCPOperate.sendData(studentSocket.socket, sData);
			studentSocket.talk = !studentSocket.talk;
			Utils.log("%s 学生 当前是否可以讲话 %s", studentSocket.username, studentSocket.talk);
		});
		t.start();
	}

	public static void main(String[] args) {
		Teacher t = new Teacher();
	}
}

