import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import java.io.File;
import javax.imageio.*;
import javax.naming.spi.DirStateFactory.Result;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.client.j2se.*;

/* I also need for decript/encript
 * import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
 * import com.google.zxing.client.j2se.MatrixToImageWriter;
*/

public class Scan {

	public static void main(String[] args) {

		new Scan();

	}
	
	// Image scanning
	
	public Scan() {
		String output = "Loaded QR code";
		String path = "code.png";
		String charset = "UTF-8";
		Map map = new HashMap();
		map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		try {
			encript(output, path, charset, map, 200, 200);
			System.out.println("No errors occured");
			System.out.println(decript(path, charset, map));
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//Create QRcode
	
	public void encript(String output, String file, String charset,
				Map hintmap, int width, int height)
				throws WriterException, IOException {
		BitMatrix mat = new MultiFormatWriter().encode(
				new String(output.getBytes(charset),charset),
				BarcodeFormat.QR_CODE , width, height); 
		MatrixToImageWriter.writeToFile(mat,
				file.substring(file.lastIndexOf('.') + 1), new File(file));
	}
	
	//Read QRcode
	
	public String decript(String file, String charset, Map map)
			throws IOException, NotFoundException {
		BinaryBitmap binBit = new BinaryBitmap(new HybridBinarizer(
				new LuminanceSource(ImageIO.read(new File(file)))));
		com.google.zxing.Result result = new MultiFormatReader().decode(binBit, map);
		return result.getText();
	}
}