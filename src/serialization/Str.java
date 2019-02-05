package serialization;

import static serialization.SerializationUtils.*;

public class Str extends Container {

	public static final byte CONTAINER_TYPE = ContainerType.STRING;
	
	public int count;
	private char[] characters;
	
	private Str() {
		size += 1 + 4;
	}
	
	public Str(String name) {
		setName(name);
	}
	
	public String getString() {
		return new String(characters);
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
	
	public int getBytes(byte[] dest, int pointer) {
		pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
		pointer = writeBytes(dest, pointer, nameLength);
		pointer = writeBytes(dest, pointer, name);
		pointer = writeBytes(dest, pointer, size);
		pointer = writeBytes(dest, pointer, count);
		pointer = writeBytes(dest, pointer, characters);
		
		return pointer;
	}
	
	public static Str Create(String name, String data) {
		Str string = new Str();
		string.setName(name);
		string.count = data.length();
		string.characters = data.toCharArray();
		string.updateSize();
		
		return string;
	}
	
	public static Str Deserialize(byte[] data, int pointer) {
		byte containerType = data[pointer++];
		assert(containerType == CONTAINER_TYPE);
		
		Str result = new Str();
		result.nameLength = readShort(data, pointer);
		pointer += 2;
		result.name = readString(data, pointer, result.nameLength).getBytes();
		pointer += result.nameLength;
	
		result.size = readInt(data, pointer);
		pointer += 4;
		
		result.count = readInt(data, pointer);
		pointer += 4;
		
		result.characters = new char[result.count];
		readChars(data, pointer, result.characters);
		
		pointer += result.count * Type.getSize(Type.CHAR);
		return result;
	}
	
}