package lzketh.Sound;

import lzketh.*;

import javax.imageio.plugins.tiff.TIFFDirectory;
import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class SoundCapture {

	public boolean running = false;
	public ByteArrayOutputStream out;

	public SoundCapture() {
		this.out = new ByteArrayOutputStream();
		captureSound();
	}

	public byte[] getSound() {
		byte[] data = out.toByteArray();
		out.reset();
		return data;
	}

	public void captureSound() {
		try {
			final AudioFormat format = getFormat();
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
			final TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
			line.open(format);
			line.start();
			int bufferSize = (int)format.getSampleRate()
					* format.getFrameSize();
			byte[] buffer = new byte[bufferSize];

			Timer timer = new Timer();
			int interval = 1000 / 60;
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
				// Utils.log("收集声音");
				if (running) {
					int count = line.read(buffer, 0, buffer.length);
					if (count > 0) {
						out.write(buffer, 0, count);
					}
				}
				}
			}, interval, interval);
		} catch (Exception e) {
			Utils.log("声音捕捉出现问题 %s", e.getMessage());
		}
	}

	public void putSoundInOut(byte[] data) {
		out.writeBytes(data);
	}

	private static AudioFormat getFormat() {
		float sampleRate = 10000;
		int sampleSizeInBits = 16;
		int channels = 1;
		boolean signed = true;
		boolean bigEndian = true;
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}

	public void soundControl(boolean on) {
		this.running = on;
	}

	public static void main(String[] args) {
		// testCapture();
		// captureSoundAndSend();
		// playAudio(new byte[0]);
	}
}