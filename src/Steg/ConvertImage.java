package Steg;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;

public class ConvertImage {
	public static void main(String[] args) {
	}

	public static void imageIoWrite(String loc) {
		String mainLoc = "C:\\Users\\LENOVO\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\Final-Project\\uploads\\";
		BufferedImage bImage = null;
		try {
			File initialImage = new File(loc);
			bImage = ImageIO.read(initialImage);
			System.out.println(bImage.getRGB(60, 0));
			ImageIO.write(bImage, "png", new File(mainLoc + "PngConvertedImage.png"));
			System.out.println("Images were written succesfully.");
		} catch (IOException e) {
			System.out.println("Exception occured :" + e.getMessage());
		}
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
