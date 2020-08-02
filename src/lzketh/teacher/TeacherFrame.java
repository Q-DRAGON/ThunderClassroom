package lzketh.teacher;

import lzketh.SData;
import lzketh.Sign;
import lzketh.Utils;
import lzketh.model.Question;
import lzketh.model.QuestionService;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;

public class TeacherFrame extends JFrame implements Runnable {
	private BufferedImage img = null;
	private Teacher teacher = null;
	private Box studentList = null;

	public TeacherFrame(String title, Teacher teacher) {
		super(title);
		this.teacher = teacher;
	}

	@Override
	public void run() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {}
		setLayout(new BorderLayout());
		setupButton();
		setupStudentList();
		setupQuestion();
		setLocation(200, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	private void setupQuestion() {
		Box p = Box.createVerticalBox();
		Box q = Box.createHorizontalBox();
		JLabel title = new JLabel("问题");
		JTextField titleInput = new JTextField();
		titleInput.setColumns(10);
		q.add(title);
		q.add(titleInput);

		Box A = Box.createHorizontalBox();
		JLabel Atext = new JLabel("A");
		JTextField AInput = new JTextField();
		AInput.setColumns(10);
		A.add(Atext);
		A.add(AInput);

		Box B = Box.createHorizontalBox();
		JLabel Btext = new JLabel("B");
		JTextField BInput = new JTextField();
		BInput.setColumns(10);
		B.add(Btext);
		B.add(BInput);

		Box C = Box.createHorizontalBox();
		JLabel Ctext = new JLabel("C");
		JTextField CInput = new JTextField();
		CInput.setColumns(10);
		C.add(Ctext);
		C.add(CInput);

		Box s = Box.createHorizontalBox();
		JLabel stext = new JLabel("答案");
		JComboBox<String> solution = new JComboBox<>();
		solution.addItem("A");
		solution.addItem("B");
		solution.addItem("C");
		solution.addItem("D");
		s.add(stext);
		s.add(solution);

		Box D = Box.createHorizontalBox();
		JLabel Dtext = new JLabel("D");
		JTextField DInput = new JTextField();
		DInput.setColumns(10);
		D.add(Dtext);
		D.add(DInput);

		Button commit = new Button("发送");
		commit.addActionListener(l -> {
			String questionTitle = titleInput.getText();
			String aText = AInput.getText();
			String bText = BInput.getText();
			String cText = CInput.getText();
			String dText = DInput.getText();
			String sText = solution.getSelectedItem().toString();
			ArrayList<String> ls = new ArrayList<>();
			ls.add(aText);
			ls.add(bText);
			ls.add(cText);
			ls.add(dText);
			Question hw = new Question(questionTitle, ls, sText);
			QuestionService.add(hw);
			Utils.log("question %s", Utils.toString(hw.getBytes()));
			SData sData = new SData(Sign.question, hw.getBytes());
			this.teacher.sendDataToStudents(sData);
		});

		p.add(q);
		p.add(A);
		p.add(B);
		p.add(C);
		p.add(D);
		p.add(s);
		p.add(commit);
		this.add(BorderLayout.WEST, p);
	}

	private void setupStudentList() {
		Box p = Box.createVerticalBox();
		// p.setLayout(new GridLayout(5, 1));
		this.studentList = p;
		this.add(BorderLayout.CENTER, p);
	}

	public void addStudent(StudentSocket ss) {
		Box p = this.studentList;
		JPanel j = new JPanel();
		JLabel name = new JLabel("" + ss.username);
		JButton b = new JButton("talk");
		b.addActionListener(event -> {
			boolean talk = ss.talk;
			this.teacher.studentTalkControl(ss);
			if (!talk) {
				b.setText("stop");
			} else {
				b.setText("talk");
			}
		});
		j.add(name);
		j.add(b);
		p.add(j);
		ss.panel = j;
		j.repaint();
		p.repaint();
	}

	public void removeStudent(StudentSocket ss) {
		Utils.log("removeStudent %s", ss.panel);
		Box p = this.studentList;
		p.remove(ss.panel);
		p.repaint();
	}

	private void setupButton() {
		JPanel b = new JPanel();
		Button imgOnButton = new Button("录屏开始");
		imgOnButton.addActionListener(p -> {
			this.teacher.imgOn = !this.teacher.imgOn;
			boolean imgOn = this.teacher.imgOn;
			if (imgOn) {
				imgOnButton.setLabel("录屏暂停");
			} else {
				imgOnButton.setLabel("录屏开始");
			}
		});
		b.add(imgOnButton);

		Button soundOnButton = new Button("声音开始");
		soundOnButton.addActionListener(p -> {
			this.teacher.soundOn = !this.teacher.soundOn;
			boolean soundOn = this.teacher.soundOn;
			if (soundOn) {
				soundOnButton.setLabel("声音暂停");
			} else {
				soundOnButton.setLabel("声音开始");
			}
			this.teacher.getSound.soundControl(soundOn);
		});
		b.add(soundOnButton);
		this.add(BorderLayout.NORTH, b);
	}
}
