package lzketh.Sound;

import javax.sound.sampled.*;

public class PlaySound {

	public static void playAudio(byte[] data) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// Utils.log("playAudio");
					AudioFormat af = getFormat();
					DataLine.Info info = new DataLine.Info(SourceDataLine.class, af);
					SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);

					line.open(af, data.length);
					line.start();
					// prepare audio output
					// output wave form repeatedly
					line.write(data, 0, data.length);
					// shut down audio
					line.drain();
					line.stop();
					line.close();
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
		});
		t.start();
	}

	private static AudioFormat getFormat() {
		float sampleRate = 10000;
		int sampleSizeInBits = 16;
		int channels = 1;
		boolean signed = true;
		boolean bigEndian = true;
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}

	public static void main(String[] args) {
		Mixer.Info[] line = AudioSystem.getMixerInfo();
	}

}
