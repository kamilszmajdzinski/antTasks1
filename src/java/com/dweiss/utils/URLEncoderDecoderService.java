package com.dweiss.utils;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

/**
 * A "www-url-encoding" utility class.
 * 
 * @author Dawid Weiss
 */
public class URLEncoderDecoderService {
    private final static char[] hex = "0123456789abcdef".toCharArray();
    private final static String hexDecode = "0123456789abcdef";
    private final static String allowedChars = "qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM0123456789.-*_";

    public String encode(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            if (allowedChars.indexOf(data[i]) >= 0) {
                buf.append((char) data[i]);
            } else if (data[i] == ' ') {
                buf.append('+');
            } else {
                buf.append('%');
                buf.append(hex[(data[i] >> 4) & 0x0f]);
                buf.append(hex[data[i] & 0x0f]);
            }
        }
        return buf.toString();
    }

    public byte[] decode(String data) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        for (int i = 0; i < data.length(); i++) {
            final char chr = data.charAt(i);
            if (chr == '%') {
                try {
                    i++;
                    int major = hexDecode.indexOf(Character.toLowerCase(data.charAt(i)));
                    i++;
                    int minor = hexDecode.indexOf(Character.toLowerCase(data.charAt(i)));
                    os.write((major << 4) + minor);
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new IllegalArgumentException("Bad input data.");
                }
            } else if (chr == '+') {
                os.write(' ');
            } else
                os.write(chr);
        }
        return os.toByteArray();
    }

    public static void main(final String[] args) throws UnsupportedEncodingException {
        if (args.length == 0) {
            System.out.println(URLEncoderDecoderService.class.getName() + " [-e|-d] [argument] [argument]");
            return;
        }

        final URLEncoderDecoderService service = new URLEncoderDecoderService();
        if ("-e".equals(args[0])) {
            // Encoding.
            for (int i = 1; i < args.length; i++) {
                System.out.println(args[i] + " -> " + service.encode(args[i].getBytes("UTF-8")));
            }
        } else if ("-d".equals(args[0])) {
            // Decoding
            for (int i = 1; i < args.length; i++) {
                System.out.println(args[i] + " -> " + service.decode(args[i]));
            }
        } else {
            throw new IllegalArgumentException(args[0]);
        }
    }
}