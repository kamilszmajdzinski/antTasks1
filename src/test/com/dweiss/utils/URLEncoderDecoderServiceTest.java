package com.dweiss.utils;

import junit.framework.TestCase;
import junitx.framework.ArrayAssert;

/**
 * Tests for the {@link URLEncoderDecoderService} class.
 * 
 * @author Dawid Weiss
 */
public class URLEncoderDecoderServiceTest extends TestCase {

	public void testEmptyByteArray() {
		URLEncoderDecoderService encoder = new URLEncoderDecoderService();
		// empty array.
		byte [] test = "".getBytes(); 
		ArrayAssert.assertEquals(test,
				encoder.decode(encoder.encode(test)));
	}
	
	public void testSimpleString() {
		URLEncoderDecoderService encoder = new URLEncoderDecoderService();
		// some string
		final byte [] test = "Dawid Weiss".getBytes(); 
		ArrayAssert.assertEquals(test,
				encoder.decode(encoder.encode(test)));
	}
	
	public void testBinaryData() {
		URLEncoderDecoderService encoder = new URLEncoderDecoderService();
		// encode and decode some binary data
		final byte [] test = new byte [] {0, 1, 2, 3, 10, -10, -1};
		final byte [] result = encoder.decode(encoder.encode(test));
		ArrayAssert.assertEquals(test, result);
	}
}
