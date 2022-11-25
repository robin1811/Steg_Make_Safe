package Steg;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

public class DecStegno {
	static final String DECODEDMESSAGEFILE = "C:\\Users\\LENOVO\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\Final-Project\\message_dec_file.txt";
	public static String b_msg = "";
	public static String b_pass = "";
	public static int len = 0, lenPass = 0;

	public static void main(String[] args) throws Exception {
		// readFile();
	}

	@SuppressWarnings("resource")
	public static String readFile() {
		String msgReceived = "";
		try {
			String MyTxtFileLocation = "C:\\Users\\LENOVO\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\Final-Project\\message_dec_file.txt";
			FileReader fr = new FileReader(MyTxtFileLocation);
			int i;
			while ((i = fr.read()) != -1) {
				msgReceived += ((char) i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return msgReceived;
	}

	public static int func(String loc, String passReceived) throws Exception {
		// Reading or checking image location (is it there or not)
		System.out.println("Working");
		BufferedImage yImage = readImageFile(loc);
		// function for retrieving password saved in image
		DecodeThePass(yImage);
		// Will save password retrieved from image in passFromImage variable
		String passFromImage = "";
		for (int i = 0; i < lenPass * 8; i = i + 8) {
			String sub = b_pass.substring(i, i + 8);
			int m = Integer.parseInt(sub, 2);
			char ch = (char) m;
			System.out.println("m " + m + " c " + ch);
			passFromImage += ch;
			System.out.println(passFromImage + " ");
		}
		// Decrypting Pass retrieved from image
		String pass = decryptPass(passFromImage); // Original Password
		// Checking if password given by user and password used by sender is same or not
		// if not return 0 to servlet
		if (!pass.equals(passReceived)) {
			len = 0;
			b_msg = "";
			b_pass = "";
			pass = "";
			passFromImage = "";
			lenPass = 0;
			return 0; // if pass is not same returning 0
		}
		// Password is correct
		// Now Calling function for retrieving message from image
		DecodeTheMessage(yImage);
		// Will save message retrieved from image in msgFromImage variable
		String msgFromImage = "";
		// System.out.println("len is "+len*8);
		for (int i = 0; i < len * 8; i = i + 8) {
			String sub = b_msg.substring(i, i + 8);
			int m = Integer.parseInt(sub, 2);
			char ch = (char) m;
			System.out.println("m " + m + " c " + ch);
			msgFromImage += ch;
		}
		// Double encryption was done at sender side thats why for decrypting msg doing
		// some encryption (Will explain verbally)
		String key2 = encryptPass(pass);
		// Now decrypting message by calling function
		String msg = decryptMsg(msgFromImage, pass, key2);
		// Saving message file
		PrintWriter out = new PrintWriter(new FileWriter(DECODEDMESSAGEFILE, false), true); // chk this true
		out.write(msg);
		out.close();
		len = 0;
		lenPass = 0;
		b_msg = "";
		b_pass = "";
		msgFromImage = "";
		msg = "";
		pass = "";
		passFromImage = "";
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return 1; // returning 1 that everything gone right
	}

	public static String decryptPass(String pass) {
		// Static key1 Used in Encryption in Stegno.java
		String cipherPass1 = "", key1 = "Delhi";
		int[] arrPass = new int[pass.length()];
		for (int i = 0; i < pass.length(); i++) {
			int val = pass.charAt(i) - '!';
			arrPass[i] = val;
		}
		int[] arrKey = new int[key1.length()];
		for (int i = 0; i < key1.length(); i++) {
			int val = key1.charAt(i) - '!';
			arrKey[i] = val;
		}
		int j = 0;
		int[] arr = new int[pass.length()];
		for (int i = 0; i < pass.length(); i++) {
			if (j == key1.length()) {
				j = 0;
			}
			arr[i] = (arrPass[i] - arrKey[j++]) % 94;
			if (arr[i] < 0) {
				arr[i] = 94 + arr[i];
			}
		}
		for (int i = 0; i < arr.length; i++) {
			int ch = arr[i] + 33;
			cipherPass1 += (char) ch;
		}

		return cipherPass1;
	}

	public static String encryptPass(String pass) {
		// Static key2
		String cipherPass2 = "", key2 = "Elhid";
		int[] arrPass = new int[pass.length()];
		for (int i = 0; i < pass.length(); i++) {
			int val = pass.charAt(i) - '!';
			arrPass[i] = val;
		}
		int[] arrKey = new int[key2.length()];
		for (int i = 0; i < key2.length(); i++) {
			int val = key2.charAt(i) - '!';
			arrKey[i] = val;
		}
		int j = 0;
		int[] arr = new int[pass.length()];
		for (int i = 0; i < pass.length(); i++) {
			if (j == key2.length()) {
				j = 0;
			}
			arr[i] = (arrPass[i] + arrKey[j++]) % 94;
		}
		for (int i = 0; i < arr.length; i++) {
			int ch = arr[i] + 33;
			cipherPass2 += (char) ch;
		}
		return cipherPass2;
	}

	public static String decryptMsg(String msg, String key1, String key2) {
		String decryptedMsg = "";
		int[] arrMsg = new int[msg.length()];
		for (int i = 0; i < msg.length(); i++) {
			int val = msg.charAt(i) - 32; // 32 is Ascii Code for Space
			arrMsg[i] = val;
		}
		int[] arrKey1 = new int[key1.length()];
		for (int i = 0; i < key1.length(); i++) {
			int val = key1.charAt(i) - 32;
			arrKey1[i] = val;
		}
		int[] arrKey2 = new int[key2.length()];
		for (int i = 0; i < key2.length(); i++) {
			int val = key2.charAt(i) - 32;
			arrKey2[i] = val;
		}
		int j = 0;
		for (int i = 0; i < msg.length(); i++) {
			if (j == key1.length()) {
				j = 0;
			}
			arrMsg[i] = (arrMsg[i] - arrKey1[j++]) % 95;
			if (arrMsg[i] < 0) {
				arrMsg[i] = 95 + arrMsg[i];
			}
		}
		j = 0;
		for (int i = 0; i < arrMsg.length; i++) {
			if (j == key2.length()) {
				j = 0;
			}
			arrMsg[i] = (arrMsg[i] - arrKey2[j++]) % 95;
			if (arrMsg[i] < 0) {
				arrMsg[i] = 95 + arrMsg[i];
			}
		}
		for (int i = 0; i < arrMsg.length; i++) {
			int ch = arrMsg[i] + 32;
			decryptedMsg += (char) ch;
		}
		return decryptedMsg;
	}

	public static BufferedImage readImageFile(String COVERIMAGEFILE) {
		BufferedImage theImage = null;
		File p = new File(COVERIMAGEFILE);
		try {
			theImage = ImageIO.read(p);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return theImage;
	}

	public static void DecodeThePass(BufferedImage yImage) throws Exception {
		int currentBitEntryPass = 0;
		String bx_pass = "";
		for (int x = 0; x < yImage.getWidth(); x++) {
			for (int y = 0; y < yImage.getHeight(); y++) {
				if (x == 0 && y < 8) {
					int currentPixel = yImage.getRGB(x, y);
					int red = currentPixel >> 16;
					red = red & 255;
					int green = currentPixel >> 8;
					green = green & 255;
					int blue = currentPixel;
					blue = blue & 255;
					String x_s = Integer.toBinaryString(blue);
					bx_pass += x_s.charAt(x_s.length() - 1);
					System.out.println("bx_pass" + bx_pass);
					lenPass = Integer.parseInt(bx_pass, 2);
					System.out.println("len " + lenPass);
				} else if (currentBitEntryPass < lenPass * 8) {
					int currentPixel = yImage.getRGB(x, y);
					int red = currentPixel >> 16;
					red = red & 255;
					int green = currentPixel >> 8;
					green = green & 255;
					int blue = currentPixel;
					blue = blue & 255;
					String x_s = Integer.toBinaryString(blue);
					b_pass += x_s.charAt(x_s.length() - 1);
					currentBitEntryPass++;
				}
			}
		}
		System.out.println("bin value of pass hided in img is " + b_pass);
	}

	public static void DecodeTheMessage(BufferedImage yImage) throws Exception {
		int b = 0;
		int currentBitEntry = 0, currentBitEntryPass = 0;
		String bx_msg = "";
		for (int x = 0; x < yImage.getWidth(); x++) {
			for (int y = 0; y < yImage.getHeight(); y++) {
				if (x == 0 && y < 8) {
				} else if (currentBitEntryPass < lenPass * 8) {
					currentBitEntryPass++;
				} else if (currentBitEntryPass == lenPass * 8 && b < 8) {
					int currentPixel = yImage.getRGB(x, y);
					int red = currentPixel >> 16;
					red = red & 255;
					int green = currentPixel >> 8;
					green = green & 255;
					int blue = currentPixel;
					blue = blue & 255;
					String x_s = Integer.toBinaryString(blue);
					bx_msg += x_s.charAt(x_s.length() - 1);
					System.out.println("bx_msg" + bx_msg);
					len = Integer.parseInt(bx_msg, 2);
					System.out.println("len " + len);
					b++;
				} else if (currentBitEntry < len * 8) {
					int currentPixel = yImage.getRGB(x, y);
					int red = currentPixel >> 16;
					red = red & 255;
					int green = currentPixel >> 8;
					green = green & 255;
					int blue = currentPixel;
					blue = blue & 255;
					String x_s = Integer.toBinaryString(blue);
					b_msg += x_s.charAt(x_s.length() - 1);
					currentBitEntry++;
				}
			}
		}
		System.out.println("bin value of msg hided in img is " + b_msg);
	}
}