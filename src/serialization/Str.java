package serialization;

import static serialization.SerializationUtils.*;

public class Str {

public static final byte CONTAINER_TYPE = ContainerType.STRING;
	
	public int count;
	public short nameLength;
	public byte[] name;
	private char[] characters;
	
	public int size = Type.getSize(Type.BYTE) + Type.getSize(Type.SHORT) + 
			Type.getSize(Type.INTEGER) + Type.getSize(Type.INTEGER);
	
	private Str() {

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
		pointer = writeBytes(dest, pointer, count);
		pointer = writeBytes(dest, pointer, characters);
		
		return pointer;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getDataSize() {
		return characters.length * Type.getSize(Type.CHAR);
	}
	
	private void updateSize() {
		size += getDataSize();
	}
	
	
	public static Str Create(String name, String data) {
		Str string = new Str();
		string.setName(name);
		string.count = data.length();
		string.characters = data.toCharArray();
		string.updateSize();
		
		return string;
	}
	
}