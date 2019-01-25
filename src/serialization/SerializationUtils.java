package serialization;

public class SerializationUtils {
	
	public static final byte[] HEADER = "RC".getBytes();
	public static final short VERSION = 0x0100;

	public static int writeBytes(byte[] dest, int pointer, byte[] source) {
		assert(dest.length > pointer + source.length);
		for (int i = 0; i < source.length; i++)
			dest[pointer++] = source[i];
		
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer, byte value) {
		dest[pointer++] = value;
		
		return pointer;
	}

	public static int writeBytes(byte[] dest, int pointer, short value) {
		dest[pointer++] = (byte)((value >> 8) & 0xff);
		dest[pointer++] = (byte)((value >> 0) & 0xff);
		
		return pointer;
	}

	public static int writeBytes(byte[] dest, int pointer, int value) {
		dest[pointer++] = (byte)((value >> 24) & 0xff);
		dest[pointer++] = (byte)((value >> 16) & 0xff);
		dest[pointer++] = (byte)((value >> 8)  & 0xff);
		dest[pointer++] = (byte)((value >> 0)  & 0xff);
		
		return pointer;
	}

	public static int writeBytes(byte[] dest, int pointer, long value) {
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
	
	public static int writeBytes(byte[] dest, int pointer, char value) {
		dest[pointer++] = (byte)((value >> 8) & 0xff);
		dest[pointer++] = (byte)((value >> 0) & 0xff);
		
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer, float value) {
		int data = Float.floatToIntBits(value);
		
		return writeBytes(dest, pointer, data);
	}

	public static int writeBytes(byte[] dest, int pointer, double value) {
		long data = Double.doubleToLongBits(value);
		
		return writeBytes(dest, pointer, data);
	}
	
	public static int writeBytes(byte[] dest, int pointer, boolean value) {
		dest[pointer++] = (byte)(value ? 1 : 0);
		
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer, String string) {
		pointer = writeBytes(dest, pointer, (short) string.length());
		
		return writeBytes(dest, pointer, string.getBytes());
	}
	
	public static byte readByte(byte[] source, int pointer) {
		return source[pointer];
	}

	public static short readShort(byte[] source, int pointer) {
		return (short)((source[pointer] << 8) | source[pointer + 1]);
	}

	public static char readChar(byte[] source, int pointer) {
		return (char)((source[pointer] << 8) | source[pointer + 1]);
	}

	public static int readInt(byte[] source, int pointer) {
		return (int)((source[pointer]) << 24 | (source[pointer + 1] << 16) | (source[pointer + 2] << 8) | source[pointer + 3]);
	}

	public static long readLong(byte[] source, int pointer) {
		return (long)((source[pointer]) << 56 | (source[pointer + 1] << 48) | (source[pointer + 2] << 40) | source[pointer + 3] << 32 | 
					  (source[pointer + 4] << 24) | (source[pointer + 5] << 16) | (source[pointer + 6] << 8) | source[pointer + 7]);
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
	
}