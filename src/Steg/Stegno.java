package Steg;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class Stegno {
	static final String STEGIMAGEFILE = "C:\\Users\\LENOVO\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\Final-Project\\stegEncryptFile.png";

	public static void main(String[] args) throws Exception {
	}

	public static int func(String msg, String pass, String loc) throws Exception {
		/*
		 * We doing this to make our message secure, as if hacker got our pass from
		 * photo after that also he cant be able to extract our message from photo.
		 */
		String cipher1 = encryptPass1(pass); // Will save in photo
		System.out.println(cipher1);
		String cipher2 = encryptPass2(pass); // Will use as 1st key for encrypting message
		System.out.println(cipher2);
		String message = msg;
		String contentOfMessage = encryptMsg(message, cipher2, pass);
		// Converting cipher1 in bits to save in image
		int[] bit_pass = bit_Msg(cipher1);
		// Converting message content in bits to save in image
		int[] bits = bit_Msg(contentOfMessage);
		// Reading or checking image location (is it there or not)
		BufferedImage theImage = readImageFile(loc); // COVERIMAGEFILE);
		// Calling function for hiding message and pass in image
		hideTheMessage(bits, bit_pass, theImage);
		// hideTheMessage(bits, theImage);
		return 1;
	}

	public static String encryptPass1(String pass) {
		// Encryption with static key1
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
			arr[i] = (arrPass[i] + arrKey[j++]) % 94;
		}
		for (int i = 0; i < arr.length; i++) {
			int ch = arr[i] + 33;
			cipherPass1 += (char) ch;
		}
		return cipherPass1;
	}

	public static String encryptPass2(String pass) {
		// Encryption with static key2
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

	public static String encryptMsg(String msg, String key1, String key2) {
		// Encrypt msg with key1
		// Encrypt cipher received from Step1 with key2
		String cipherMsg1 = "", cipherMsg2 = "";
		int[] arrMsg = new int[msg.length()];
		for (int i = 0; i < msg.length(); i++) {
			int val = msg.charAt(i) - 32; // 32 ASCII of Space
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
			arrMsg[i] = (arrMsg[i] + arrKey1[j++]) % 95;
		}
		j = 0;
		for (int i = 0; i < arrMsg.length; i++) {
			if (j == key2.length()) {
				j = 0;
			}
			arrMsg[i] = (arrMsg[i] + arrKey2[j++]) % 95;
			System.out.println("arrMsg[" + i + "]=" + arrMsg[i]);
		}
		for (int i = 0; i < arrMsg.length; i++) {
			int ch = arrMsg[i] + 32;
			cipherMsg2 += (char) ch;
		}
		return cipherMsg2; // return cipherMsg2
	}

	public static int[] bit_Msg(String msg) {
		int j = 0;
		int[] b_msg = new int[msg.length() * 8];
		for (int i = 0; i < msg.length(); i++) {
			int x = msg.charAt(i);
			String x_s = Integer.toBinaryString(x);
			while (x_s.length() != 8) {
				x_s = '0' + x_s;
			}
			System.out.println("dec value for " + x + " is " + x_s);

			for (int i1 = 0; i1 < 8; i1++) {
				b_msg[j] = Integer.parseInt(String.valueOf(x_s.charAt(i1)));
				j++;
			}
		}
		return b_msg;
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

	public static void hideTheMessage(int[] bits, int[] bit_pass, BufferedImage theImage) throws Exception {
		File f = new File(STEGIMAGEFILE);
		BufferedImage sten_img = null;
		int bit_l = bits.length / 8;
		int bit_passl = bit_pass.length / 8;
		int[] bl_msg = new int[8];
		int[] bl_pass = new int[8];
		System.out.println("bit lent " + bit_l); // no. of letters + spaces
		String bl_s = Integer.toBinaryString(bit_l); // convert in binary
		String bl_pass_s = Integer.toBinaryString(bit_passl);
		while (bl_s.length() != 8) {
			bl_s = '0' + bl_s;
		}
		while (bl_pass_s.length() != 8) {
			bl_pass_s = '0' + bl_pass_s;
		}
		for (int i1 = 0; i1 < 8; i1++) {
			bl_msg[i1] = Integer.parseInt(String.valueOf(bl_s.charAt(i1))); // length of msg
			bl_pass[i1] = Integer.parseInt(String.valueOf(bl_pass_s.charAt(i1))); // length of pass
		}
		;
		// loop count
		int loopCount = 0;
		int j = 0, r = 0;
		int b = 0;
		int a1 = 0, a2 = 0;
		int currentBitEntryPass = 8;
		int currentBitEntry = 8;
		System.out.println(theImage.getWidth());
		System.out.println(theImage.getHeight());
		for (int x = 0; x < theImage.getWidth(); x++) {
			for (int y = 0; y < theImage.getHeight(); y++) {
				if (x == 0 && y < 8) {
					int currentPixel = theImage.getRGB(x, y);
					int ori = currentPixel;
					int red = currentPixel >> 16;
					red = red & 255;
					int green = currentPixel >> 8;
					green = green & 255;
					int blue = currentPixel;
					blue = blue & 255;
					String x_s = Integer.toBinaryString(blue);
					String sten_s = x_s.substring(0, x_s.length() - 1);
					sten_s = sten_s + Integer.toString(bl_pass[a2]); // 00000101 //01001011
					a2++;
					int s_pixel = Integer.parseInt(sten_s, 2);
					int a = 255;
					int rgb = (a << 24) | (red << 16) | (green << 8) | s_pixel;
					theImage.setRGB(x, y, rgb);
					ImageIO.write(theImage, "png", f);
					loopCount++;
				} else if (currentBitEntryPass < bit_pass.length + 8) {
					int currentPixel = theImage.getRGB(x, y);
					int ori = currentPixel;
					int red = currentPixel >> 16;
					red = red & 255;
					int green = currentPixel >> 8;
					green = green & 255;
					int blue = currentPixel;
					blue = blue & 255;
					String x_s = Integer.toBinaryString(blue);
					String sten_s = x_s.substring(0, x_s.length() - 1);
					sten_s = sten_s + Integer.toString(bit_pass[r]);
					r++;
					int temp = Integer.parseInt(sten_s, 2);
					int s_pixel = Integer.parseInt(sten_s, 2);
					int a = 255;
					int rgb = (a << 24) | (red << 16) | (green << 8) | s_pixel;
					theImage.setRGB(x, y, rgb);
					ImageIO.write(theImage, "png", f);
					currentBitEntryPass++;
					loopCount++;
				} else if (currentBitEntryPass == bit_pass.length + 8 && b < 8) {
					int currentPixel = theImage.getRGB(x, y);
					System.out.println(theImage.getRGB(x, y));
					int ori = currentPixel;
					int red = currentPixel >> 16;
					System.out.println("before red" + red);
					red = red & 255;
					System.out.println("after red" + red);
					int green = currentPixel >> 8;
					System.out.println("before green" + green);
					green = green & 255;
					System.out.println("after green" + green);
					int blue = currentPixel;
					System.out.println("before blue" + blue);
					blue = blue & 255;
					System.out.println("after blue" + blue);
					String x_s = Integer.toBinaryString(blue);
					System.out.println("X_S value" + x_s + "length " + x_s.length());
					String sten_s = x_s.substring(0, x_s.length() - 1);
					System.out.println("before sten_s " + sten_s);
					sten_s = sten_s + Integer.toString(bl_msg[b]); // 0 0000101 //01001011
					b++;
					System.out.println("after sten_s " + sten_s);
					int temp = Integer.parseInt(sten_s, 2);
					System.out.println("temp value " + temp);
					int s_pixel = Integer.parseInt(sten_s, 2);
					int a = 255;
					int rgb = (a << 24) | (red << 16) | (green << 8) | s_pixel;
					theImage.setRGB(x, y, rgb);
					ImageIO.write(theImage, "png", f);
					loopCount++;
				} else if (currentBitEntry < bits.length + 8) {
					int currentPixel = theImage.getRGB(x, y);
					int ori = currentPixel;
					int red = currentPixel >> 16;
					red = red & 255;
					int green = currentPixel >> 8;
					green = green & 255;
					int blue = currentPixel;
					blue = blue & 255;
					String x_s = Integer.toBinaryString(blue);
					String sten_s = x_s.substring(0, x_s.length() - 1);
					sten_s = sten_s + Integer.toString(bits[j]);
					j++;
					int temp = Integer.parseInt(sten_s, 2);
					int s_pixel = Integer.parseInt(sten_s, 2);
					int a = 255;
					int rgb = (a << 24) | (red << 16) | (green << 8) | s_pixel;
					theImage.setRGB(x, y, rgb);
					ImageIO.write(theImage, "png", f);
					currentBitEntry++;
					loopCount++;
				}
			}
		}
		System.out.println("==================: Loop Count :=============== " + loopCount);
	}
}
