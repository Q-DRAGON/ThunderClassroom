package lzketh.student;

import lzketh.*;
import lzketh.Sound.SoundCapture;
import lzketh.Sound.PlaySound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Socket;
import java.util.*;
import java.util.Timer;
import java.util.function.Consumer;

public class Student extends JFrame {
	public StudentWindow win;
	public Socket socket;
	public boolean talk = false;
	private SoundCapture soundCapture = new SoundCapture();
	private Long focused = 0L;
	// consumer 就是一个 参数是 byte[] 没有返回值 的 函数
	private HashMap<Byte, Consumer<byte[]>> functionMap = new HashMap<>();

	public Student() {
		super("student");
		win = new StudentWindow(this);
		this.setLayout(new BorderLayout());
		this.add(win);
		// 设置屏幕大小
		this.setSize(StudentWindow.WindowWidth, StudentWindow.WindowHeight);
		// 默认关闭操作
		this.setClosing();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setup();
	}

	private void setup() {
		this.initHandleMap();
		// connect teacher
		final String host = "localhost";
		final int port = 8000;
		try {
			this.socket = new Socket(host, port);
			Utils.log("student socket 连接成功");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean login(String username, String password) {
		String userAndPass = String.format("%s#%s", username, password);
		SData sData = new SData(Sign.userAndPass, userAndPass);
		// 发送登录请求
		Utils.log("userAndPass bytes %s", Arrays.toString(sData.data));
		TCPOperate.sendData(this.socket, sData);
		Utils.log("login userAndPass 发送完成");
		// 是否登录成功
		SData result = TCPOperate.socketRead(this.socket);
		int sign = result.sign;
		String success = new String(result.data);
		if (success.equals(Sign.Success)) {
			Utils.log("student login success");
			this.start();
			return true;
		}
		Utils.log("student login false");
		return false;
	}

	public void start() {
		this.receiveFlow();
		this.talk();
		this.focusTime();
	}

	public void receiveFlow() {
		Thread t = new Thread(() -> {
			Utils.log("student receiveFlow start");
			while (true) {
				SData data = TCPOperate.socketRead(socket);
				this.distributeData(data);
			}
		});
		t.start();
	}

	public void focusTime() {
		Student self = this;
		Timer timer = new Timer();
		int interval = 1000;
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (self.win.focused()) {
					String distance = String.valueOf(interval / 1000);
					self.focused += Long.parseLong(distance);
				}
			}
		}, interval, interval);
	}

	private void setClosing() {
		Student self = this;
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int option= JOptionPane.showConfirmDialog(
						self, "确定退出? ", "提示 ",JOptionPane.YES_NO_CANCEL_OPTION);
				if(option==JOptionPane.YES_OPTION) {
					if (e.getWindow() == self) {
						self.sendFocusTime();
						super.windowClosing(e);
					} else {
						return;
					}
				}
			}
		});
	}

	public void sendFocusTime() {
		if (!this.socket.isClosed()) {
			String ms = String.valueOf(this.focused);
			SData sData = new SData(Sign.studentFocused, ms);
			TCPOperate.sendData(socket, sData);
		}
	}

	public void talk() {
		Student self = this;
		Timer timer = new Timer();
		int interval = 1000 / 24;
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (self.talk) {
					// 收集声音 然后 传给老师
					byte[] sound = soundCapture.getSound();
					if (sound.length > 50) {
						// Utils.log("sound length %s", sound.length);
						SData sData = new SData(Sign.studentSound, sound);
						TCPOperate.sendData(socket, sData);
					}
				}
			}
		}, interval, interval);
	}

	// 发送数据
	public void distributeData(SData sData) {
		byte[] data = sData.data;
		byte sign = sData.sign;
		// 去除标志位
		Consumer<byte[]> handle = this.functionMap.get(sign);
		handle.accept(data);
	}

	private void initHandleMap() {
		this.functionMap.put(Sign.picture, this::showImage);
		this.functionMap.put(Sign.sound, this::playSound);
		this.functionMap.put(Sign.talkControl, this::talkControl);
		this.functionMap.put(Sign.question, this::question);
	}

	private void showImage(byte[] data) {
		BufferedImage bufferedImage = getImage(data);
		this.win.changeImage(bufferedImage);
	}

	private void playSound(byte[] data) {
		PlaySound.playAudio(data);
	}

	private void talkControl(byte[] data) {
		String r = "success";
		this.talk = !this.talk;
		this.soundCapture.soundControl(this.talk);
		Utils.log("当前是否可以讲话: %s", this.talk);
	}

	private void question(byte[] data) {
		String ms = Utils.toString(data);
		String[] msList = ms.split("@@");
		Utils.log("收到老师发过来的题目 %s", ms);
		this.win.questionBox.updateQuestion(msList);
	}

	public static BufferedImage getImage(byte[] imgArray) {
		// int version = Utils.bytesToNum(imgArray, 0, 0);
		int h = Utils.bytesToNum(imgArray, 1, 2);
		int w = Utils.bytesToNum(imgArray, 3, 4);
		int imgDataStart = 5;

		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
		int elementPerPixel = 4;
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				int index = imgDataStart + (i * w * elementPerPixel) + (j * elementPerPixel);
				int r = Utils.byteToInt(imgArray[index]);
				int g = Utils.byteToInt(imgArray[index + 1]);
				int b = Utils.byteToInt(imgArray[index + 2]);
				int a = Utils.byteToInt(imgArray[index + 3]);
				Color c = new Color(r, g, b, a);
				bi.setRGB(j, i, c.getRGB());
			}
		}
		return bi;
	}

	public static void main(String[] args) {
		Student s = new Student();
	}
}
