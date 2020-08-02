package lzketh.student;

import lzketh.SData;
import lzketh.Sign;
import lzketh.TCPOperate;
import lzketh.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class StudentWindow extends JPanel {
	public static final int WindowWidth= 400;
	public static final int WindowHeight = 500;
	private ImageJPanel panel = null;
	private Student student;
	public QuestionBox questionBox;

	public StudentWindow(Student student) {
		this.student = student;
		this.setLayout(new FlowLayout(FlowLayout.LEADING,2, 2));
		this.setupLogin();
	}

	public boolean focused() {
		return this.student.isFocused();
	}

	public void setupLogin() {
		JLabel useText = new JLabel("username");
		JTextField userInput = new JTextField();
		userInput.setColumns(10);
		JLabel passText = new JLabel("password");
		JTextField passInput = new JTextField();
		passInput.setColumns(10);
		Button login = new Button("登录");
		this.add(useText);
		this.add(userInput);
		this.add(passText);
		this.add(passInput);
		this.add(login);
		login.addActionListener(l -> {
			String username = userInput.getText();
			String password = passInput.getText();
			if (this.student.login(username, password)) {
				this.removeAll();
				this.repaint();
				setupImage();
				setupQuestion();
			}
		});
	}

	private void setupImage() {
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
		panel = new ImageJPanel();
		panel.setImage(img);
		this.add(panel);
	}

	private void setupQuestion() {
		questionBox = new QuestionBox(student);
		this.add(questionBox);
	}

	public void changeImage(BufferedImage image) {
		if ((panel != null) && (panel.getImage() != image)) {
			panel.setImage(image);
		}
	}

	public static void main(String[] args) {
		// StudentWindow s = new StudentWindow(this);
	}
}

class QuestionBox extends Box {
	private String id;
	private JLabel title;
	private JLabel A;
	private JLabel B;
	private JLabel C;
	private JLabel D;
	private JComboBox<String> answer;
	private Student student;

	public QuestionBox(Student student) {
		super(1);
		this.student = student;

		setup();
	}

	public void updateQuestion(String[] questionList) {
		String[] msList = questionList;
		String id = msList[0];
		String title = msList[1];
		String A = msList[1 + 1];
		String B = msList[2 + 1];
		String C = msList[3 + 1];
		String D = msList[4 + 1];
		this.id = id;
		this.title.setText(title);
		this.A.setText(A);
		this.B.setText(B);
		this.C.setText(C);
		this.D.setText(D);
	}

	private void setup() {
		Box t = Box.createHorizontalBox();
		JLabel timu = new JLabel("题目: ");
		title = new JLabel();
		t.add(timu);
		t.add(title);

		Box aBox = Box.createHorizontalBox();
		JLabel axrxd = new JLabel("A: ");
		A = new JLabel();
		aBox.add(axrxd);
		aBox.add(A);

		Box bBox = Box.createHorizontalBox();
		JLabel bxrxd = new JLabel("B: ");
		B = new JLabel();
		bBox.add(bxrxd);
		bBox.add(B);

		Box cBox = Box.createHorizontalBox();
		JLabel cxrxd = new JLabel("C: ");
		C = new JLabel();
		cBox.add(cxrxd);
		cBox.add(C);

		Box dBox = Box.createHorizontalBox();
		JLabel dxrxd = new JLabel("D: ");
		D = new JLabel();
		dBox.add(dxrxd);
		dBox.add(D);

		answer = new JComboBox<>();
		answer.addItem("A");
		answer.addItem("B");
		answer.addItem("C");
		answer.addItem("D");

		JButton b = new JButton("提交");
		b.addActionListener(l -> {
			String id = this.id;
			String answer = String.valueOf(this.answer.getSelectedItem());
			String ms = String.format("%s@@%s", id, answer);
			SData sData = new SData(Sign.answer, Utils.getBytes(ms));
			Utils.log("发送给老师的答案 %s", ms);
			TCPOperate.sendData(this.student.socket, sData);
		});

		this.add(t);
		this.add(aBox);
		this.add(bBox);
		this.add(cBox);
		this.add(dBox);
		this.add(answer);
		this.add(b);
	}
}

class ImageJPanel extends JPanel {
	private Image img = null;

	public ImageJPanel() {
	}

	public void setImage(Image value) {
		if (img != value) {
			Image old = img;
			this.img = value;
			firePropertyChange("image", old, img);
			revalidate();
			repaint();
		}
	}

	public Image getImage() {
		return img;
	}

	@Override
	public Dimension getPreferredSize() {
		return img == null ? new Dimension(200, 200) : new Dimension(img.getWidth(this), img.getHeight(this));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (img != null) {
			Graphics2D g2d = (Graphics2D) g.create();

			int width = getWidth();
			int height = getHeight();
			// Dimension d = new Dimension(200, 200);
			Dimension d = getSize();
			// 图片显示大小
			double scaleFactor = getScaleFactorToFit(new Dimension(img.getWidth(this), img.getHeight(this)), d);
			// 图片显示位置
			int x = (int) ((width - (img.getWidth(this) * scaleFactor)) / 2);
			int y = (int) ((height - (img.getHeight(this) * scaleFactor)) / 2);
			// int x = 0;
			// int y = 0;
			AffineTransform at = new AffineTransform();
			at.translate(x, y);
			at.scale(scaleFactor * 2, scaleFactor * 2);
			g2d.setTransform(at);
			g2d.drawImage(img, 0, 0, this);
			g2d.dispose();
		}
	}

	public double getScaleFactor(int iMasterSize, int iTargetSize) {
		return (double) iTargetSize / (double) iMasterSize;
	}

	public double getScaleFactorToFit(Dimension original, Dimension toFit) {
		double dScale = 1d;
		if (original != null && toFit != null) {
			double dScaleWidth = getScaleFactor(original.width, toFit.width);
			double dScaleHeight = getScaleFactor(original.height, toFit.height);
			dScale = Math.min(dScaleHeight, dScaleWidth);
		}
		return dScale;
	}
}
