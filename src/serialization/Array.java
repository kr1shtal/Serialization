package serialization;

import static serialization.SerializationUtils.*;

public class Array {

	public static final byte CONTAINER_TYPE = ContainerType.ARRAY;
	
	public int count;
	public short nameLength;
	public byte type;
	public byte[] name;
	public byte[] data;

	private short[] shortData;
	private char[] charData;
	private int[] intData;
	private long[] longData;
	private float[] floatData;
	private double[] doubleData;
	private boolean[] booleanData;
	
	public int size = Type.getSize(Type.BYTE) + Type.getSize(Type.SHORT) + Type.getSize(Type.INTEGER) + 
			Type.getSize(Type.INTEGER) + Type.getSize(Type.BYTE) + Type.getSize(Type.INTEGER);
	
	private Array() {
		
	}
	
	public void setName(String name) {
		 assert(name.length() < Short.MAX_VALUE);
		 
		 if (this.name != null)
			 size -= this.name.length;
		 
		 nameLength = (short) name.length();
		 this.name = name.getBytes();
		 size += nameLength;
	}
	
	public int getBytes(byte[] dest, int pointer) {
		pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
		pointer = writeBytes(dest, pointer, nameLength);
		pointer = writeBytes(dest, pointer, name);
		pointer = writeBytes(dest, pointer, size);
		pointer = writeBytes(dest, pointer, type);
		pointer = writeBytes(dest, pointer, count);
		
		switch (type) {
			case Type.BYTE:
				pointer = writeBytes(dest, pointer, data);
				break;
			case Type.SHORT:
				pointer = writeBytes(dest, pointer, shortData);
				break;
			case Type.CHAR:
				pointer = writeBytes(dest, pointer, charData);
				break;
			case Type.INTEGER:
				pointer = writeBytes(dest, pointer, intData);
				break;
			case Type.LONG:
				pointer = writeBytes(dest, pointer, longData);
				break;
			case Type.FLOAT:
				pointer = writeBytes(dest, pointer, floatData);
				break;
			case Type.DOUBLE:
				pointer = writeBytes(dest, pointer, doubleData);
				break;
			case Type.BOOLEAN:
				pointer = writeBytes(dest, pointer, booleanData);
				break;
		}
		
		return pointer;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getDataSize() {
		switch (type) {
			case Type.BYTE:	 	return data.length * Type.getSize(Type.BYTE);
			case Type.SHORT:	return shortData.length * Type.getSize(Type.SHORT);
			case Type.CHAR:	 	return charData.length * Type.getSize(Type.CHAR);
			case Type.INTEGER:	return intData.length * Type.getSize(Type.INTEGER);
			case Type.LONG: 	return longData.length * Type.getSize(Type.LONG);
			case Type.FLOAT:	return floatData.length * Type.getSize(Type.FLOAT);
			case Type.DOUBLE: 	return doubleData.length * Type.getSize(Type.DOUBLE);
			case Type.BOOLEAN:	return booleanData.length * Type.getSize(Type.BOOLEAN);
		}
		
		return 0;	
	}
	
	private void updateSize() {
		size += getDataSize();
	}
	
	public static Array Byte(String name, byte[] data) {
		Array array = new Array();
		array.setName(name);
		array.type = Type.BYTE;
		array.count = data.length;
		array.data = data;
		array.updateSize();
		
		return array;
	}
	
	public static Array Short(String name, short[] data) {
		Array array = new Array();
		array.setName(name);
		array.type = Type.SHORT;
		array.count = data.length;
		array.shortData = data;
		array.updateSize();
		
		return array;
	}
	
	public static Array Char(String name, char[] data) {
		Array array = new Array();
		array.setName(name);
		array.type = Type.CHAR;
		array.count = data.length;
		array.charData = data;
		array.updateSize();
		
		return array;
	}
	
	public static Array Integer(String name, int[] data) {
		Array array = new Array();
		array.setName(name);
		array.type = Type.INTEGER;
		array.count = data.length;
		array.intData = data;
		array.updateSize();
		
		return array;
	}
	
	public static Array Long(String name, long[] data) {
		Array array = new Array();
		array.setName(name);
		array.type = Type.LONG;
		array.count = data.length;
		array.longData = data;
		array.updateSize();
		
		return array;
	}

	public static Array Float(String name, float[] data) {
		Array array = new Array();
		array.setName(name);
		array.type = Type.FLOAT;
		array.count = data.length;
		array.floatData = data;
		array.updateSize();
		
		return array;
	}
	
	public static Array Double(String name, double[] data) {
		Array array = new Array();
		array.setName(name);
		array.type = Type.DOUBLE;
		array.count = data.length;
		array.doubleData = data;
		array.updateSize();
		
		return array;
	}
	
	public static Array Boolean(String name, boolean[] data) {
		Array array = new Array();
		array.setName(name);
		array.type = Type.BOOLEAN;
		array.count = data.length;
		array.booleanData = data;
		array.updateSize();
		
		return array;
	}
	
}