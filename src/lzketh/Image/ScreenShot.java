package lzketh.Image;

import lzketh.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ScreenShot {
	Dimension d; //获取全屏幕的宽高尺寸等数据
	Robot robot;
	int width;
	int height;

	public ScreenShot() {
		this.d = Toolkit.getDefaultToolkit().getScreenSize();
		try {
			robot = new Robot();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.width = 300;
		this.height = 300;
	}

	public byte[] snapShot() {
		BufferedImage screenShot = getScreenShot();
		byte[] data = imgStructure(screenShot);
		return data;
	}

	public BufferedImage getScreenShot() {
		int x = 0;
		int y = 0;
		// int w = (int) d.getWidth();
		// int h = (int) d.getHeight();
		int w = width;
		int h = height;
		Rectangle screenSize = new Rectangle(x, y, w, h);

		BufferedImage screenShot = robot.createScreenCapture(screenSize);
		return screenShot;
	}

	private byte[] getPixels(BufferedImage image) {
		BufferedImage img = image;

		int h = img.getHeight();
		int w = img.getWidth();
		int elementPerPixel = 4;
		int pixelsArrayLen = h * w * elementPerPixel;
		byte[] pixels = new byte[pixelsArrayLen];
		// 插入 pixels
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int index = (y * w * elementPerPixel) + (x * elementPerPixel);
				int px = img.getRGB(x,y); //get pixel on x,y location
				Color c = new Color(px);
				byte r = (byte) c.getRed();
				byte b = (byte) c.getBlue();
				byte g = (byte) c.getGreen();
				byte a = (byte) c.getAlpha();
				pixels[index] = r;
				pixels[index + 1] = g;
				pixels[index + 2] = b;
				pixels[index + 3] = a;
			}
		}
		return pixels;
	}

	private byte[] imgStructure(BufferedImage image) {
		// 图片数据结构化
		// 第一个字节 是 版本号
		// 第二, 三个字节 是 长
		// 第四, 五哥字节 是 宽
		// 剩下是图片数据
		BufferedImage img = image;

		int version = 1;
		int h = img.getHeight();
		int w = img.getWidth();

		byte[] versionBytes = Utils.numToBytes(version, 1);
		byte[] hBytes = Utils.numToBytes(h, 2);
		byte[] wBytes = Utils.numToBytes(w, 2);
		byte[] pixels = this.getPixels(img);

		// 合并成一个 bytes
		byte[] data = Utils.byteMerger(versionBytes, hBytes);
		byte[] data1 = Utils.byteMerger(wBytes, pixels);
		data = Utils.byteMerger(data, data1);

		return data;
	}

	public static void main(String[] args) {
		ScreenShot screenShot = new ScreenShot();
		screenShot.snapShot();
	}
}