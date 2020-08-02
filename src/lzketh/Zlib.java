package lzketh;

import lzketh.Image.ScreenShot;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class Zlib {

	public static byte[] compressByteArray(byte[] bytes){
		ByteArrayOutputStream baos = null;
		Deflater dfl = new Deflater();
		dfl.setLevel(Deflater.BEST_SPEED);
		dfl.setInput(bytes);
		dfl.finish();
		baos = new ByteArrayOutputStream();
		byte[] tmp = new byte[4*1024];
		try{
			while(!dfl.finished()){
				int size = dfl.deflate(tmp);
				baos.write(tmp, 0, size);
			}
		} catch (Exception ex){

		} finally {
			try{
				if(baos != null) baos.close();
			} catch(Exception ex){}
		}

		return baos.toByteArray();
	}

	public static byte[] decompressByteArray(byte[] bytes){
		// Utils.log("decompressByteArray start data len %s", bytes.length);
		ByteArrayOutputStream baos = null;
		Inflater iflr = new Inflater();
		iflr.setInput(bytes);
		baos = new ByteArrayOutputStream();
		byte[] tmp = new byte[4*1024];
		try {
			while(!iflr.finished()){
				// Utils.log("decompressByteArray ing");
				int size = iflr.inflate(tmp);
				baos.write(tmp, 0, size);
			}
		} catch (Exception ex){

		} finally {
			try{
				if(baos != null) {
					baos.close();
				}
			} catch(Exception ex) {
			}
		}
		// Utils.log("decompressByteArray end %s", baos.size());
		return baos.toByteArray();
	}

	public static void main(String[] args) {
	}
}
