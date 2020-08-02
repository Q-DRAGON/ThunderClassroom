package lzketh.Image;


import lzketh.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.im.InputContext;
import java.awt.image.BufferedImage;

class MyJPanel extends JPanel {
	private Image img = null;

	public MyJPanel() {
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

public class MyJFrame extends JFrame implements Runnable {
	private BufferedImage img = null;
	private MyJPanel panel = null;

	public MyJFrame(BufferedImage image, String title) {
		super(title);
		img = image;
		// this.setMaximumSize(new Dimension(400, 400));
	}

	@Override
	public void run() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {}

		panel = new MyJPanel();
		panel.setImage(img);

		// Button b = new Button();
		// b.setLabel("button");

		setLayout(new BorderLayout());
		add(BorderLayout.CENTER, panel);
		// Button b = new Button();
		// b.addActionListener(p -> {
		// 	Utils.log("点到了");
		// });
		// add(BorderLayout.NORTH, b);
		// add(BorderLayout.NORTH, b);
		// 窗口位置
		setLocation(200, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public void changeImage(BufferedImage image) {
		if ((panel != null) && (panel.getImage() != image)) panel.setImage(image);
	}
}