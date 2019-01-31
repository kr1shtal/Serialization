package serialization;

import java.nio.ByteBuffer;

public class SerializationUtils {

	public static int writeBytes(byte[] dest, int pointer, byte[] source) {
		assert(dest.length > pointer + source.length);
		for (int i = 0; i < source.length; i++)
			dest[pointer++] = source[i];
		
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer, short[] source) {
		assert(dest.length > pointer + source.length);
		for (int i = 0; i < source.length; i++)
			pointer = writeBytes(dest, pointer, source[i]);
		
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer, char[] source) {
		assert(dest.length > pointer + source.length);
		for (int i = 0; i < source.length; i++)
			pointer = writeBytes(dest, pointer, source[i]);
		
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer, int[] source) {
		assert(dest.length > pointer + source.length);
		for (int i = 0; i < source.length; i++)
			pointer = writeBytes(dest, pointer, source[i]);
		
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer, long[] source) {
		assert(dest.length > pointer + source.length);
		for (int i = 0; i < source.length; i++)
			pointer = writeBytes(dest, pointer, source[i]);
		
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer, float[] source) {
		assert(dest.length > pointer + source.length);
		for (int i = 0; i < source.length; i++)
			pointer = writeBytes(dest, pointer, source[i]);
		
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer, double[] source) {
		assert(dest.length > pointer + source.length);
		for (int i = 0; i < source.length; i++)
			pointer = writeBytes(dest, pointer, source[i]);
		
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer, boolean[] source) {
		assert(dest.length > pointer + source.length);
		for (int i = 0; i < source.length; i++)
			pointer = writeBytes(dest, pointer, source[i]);
		
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer, byte value) {
		assert(dest.length > pointer + Type.getSize(Type.BYTE));
		dest[pointer++] = value;
		
		return pointer;
	}

	public static int writeBytes(byte[] dest, int pointer, short value) {
		assert(dest.length > pointer + Type.getSize(Type.SHORT));
		dest[pointer++] = (byte)((value >> 8) & 0xff);
		dest[pointer++] = (byte)((value >> 0) & 0xff);
		
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer, char value) {
		assert(dest.length > pointer + Type.getSize(Type.CHAR));
		dest[pointer++] = (byte)((value >> 8) & 0xff);
		dest[pointer++] = (byte)((value >> 0) & 0xff);
		
		return pointer;
	}

	public static int writeBytes(byte[] dest, int pointer, int value) {
		assert(dest.length > pointer + Type.getSize(Type.INTEGER));
		dest[pointer++] = (byte)((value >> 24) & 0xff);
		dest[pointer++] = (byte)((value >> 16) & 0xff);
		dest[pointer++] = (byte)((value >> 8)  & 0xff);
		dest[pointer++] = (byte)((value >> 0)  & 0xff);
		
		return pointer;
	}

	public static int writeBytes(byte[] dest, int pointer, long value) {
		assert(dest.length > pointer + Type.getSize(Type.LONG));
		dest[pointer++] = (byte)((value >> 56) & 0xff);
		dest[pointer++] = (byte)((value >> 48) & 0xff);
		dest[pointer++] = (byte)((value >> 40) & 0xff);
		dest[pointer++] = (byte)((value >> 32) & 0xff);
		dest[pointer++] = (byte)((value >> 24) & 0xff);
		dest[pointer++] = (byte)((value >> 16) & 0xff);
		dest[pointer++] = (byte)((value >> 8)  & 0xff);
		dest[pointer++] = (byte)((value >> 0)  & 0xff);
		
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer, float value) {
		assert(dest.length > pointer + Type.getSize(Type.FLOAT));
		int data = Float.floatToIntBits(value);
		
		return writeBytes(dest, pointer, data);
	}

	public static int writeBytes(byte[] dest, int pointer, double value) {
		assert(dest.length > pointer + Type.getSize(Type.DOUBLE));
		long data = Double.doubleToLongBits(value);
		
		return writeBytes(dest, pointer, data);
	}
	
	public static int writeBytes(byte[] dest, int pointer, boolean value) {
		assert(dest.length > pointer + Type.getSize(Type.BOOLEAN));
		dest[pointer++] = (byte)(value ? 1 : 0);
		
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer, String string) {
		pointer = writeBytes(dest, pointer, (short) string.length());
		
		return writeBytes(dest, pointer, string.getBytes());
	}
	
	public static void readBytes(byte[] source, int pointer, byte[] dest) {
		for (int i = 0; i < dest.length; i++)
			dest[i] = source[pointer + i];
	}
	
	public static void readShorts(byte[] source, int pointer, short[] dest) {
		for (int i = 0; i < dest.length; i++) {
			dest[i] = readShort(source, pointer);
			pointer += Type.getSize(Type.SHORT);
		}
	}
	
	public static void readChars(byte[] source, int pointer, char[] dest) {
		for (int i = 0; i < dest.length; i++) {
			dest[i] = readChar(source, pointer);
			pointer += Type.getSize(Type.CHAR);
		}
	}
	
	public static void readIntegers(byte[] source, int pointer, int[] dest) {
		for (int i = 0; i < dest.length; i++) {
			dest[i] = readInt(source, pointer);
			pointer += Type.getSize(Type.INTEGER);
		}
	}
	
	public static void readLongs(byte[] source, int pointer, long[] dest) {
		for (int i = 0; i < dest.length; i++) {
			dest[i] = readLong(source, pointer);
			pointer += Type.getSize(Type.LONG);
		}
	}
	
	public static void readFloats(byte[] source, int pointer, float[] dest) {
		for (int i = 0; i < dest.length; i++) {
			dest[i] = readFloat(source, pointer);
			pointer += Type.getSize(Type.FLOAT);
		}
	}
	
	public static void readDoubles(byte[] source, int pointer, double[] dest) {
		for (int i = 0; i < dest.length; i++) {
			dest[i] = readDouble(source, pointer);
			pointer += Type.getSize(Type.DOUBLE);
		}
	}
	
	public static void readBooleans(byte[] source, int pointer, boolean[] dest) {
		for (int i = 0; i < dest.length; i++) {
			dest[i] = readBoolean(source, pointer);
			pointer += Type.getSize(Type.BOOLEAN);
		}
	}
	
	public static byte readByte(byte[] source, int pointer) {
		return source[pointer];
	}

	public static short readShort(byte[] source, int pointer) {
		return ByteBuffer.wrap(source, pointer, 2).getShort();
	}

	public static char readChar(byte[] source, int pointer) {
		return ByteBuffer.wrap(source, pointer, 2).getChar();
	}

	public static int readInt(byte[] source, int pointer) {
		return ByteBuffer.wrap(source, pointer, 4).getInt();
	}

	public static long readLong(byte[] source, int pointer) {
		return ByteBuffer.wrap(source, pointer, 8).getLong();
	}

	public static float readFloat(byte[] source, int pointer) {
		return Float.intBitsToFloat(readInt(source, pointer));
	}

	public static double readDouble(byte[] source, int pointer) {
		return Double.longBitsToDouble(readLong(source, pointer));
	}

	public static boolean readBoolean(byte[] source, int pointer) {
		assert(source[pointer] == 0 || source[pointer] == 1);
		return source[pointer] != 0;
	}
	
	public static String readString(byte[] source, int pointer, int length) {
		return new String(source, pointer, length);
	}
	
}