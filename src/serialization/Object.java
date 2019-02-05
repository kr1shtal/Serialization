package serialization;

import java.util.ArrayList;
import java.util.List;

import static serialization.SerializationUtils.*;

public class Object extends Container {
	
	public static final byte CONTAINER_TYPE = ContainerType.OBJECT;
	
	private short fieldCount;
	private short stringCount;
	private short arrayCount;
	
	public List<Field> fields = new ArrayList<Field>();
	public List<Str> strings = new ArrayList<Str>();
	public List<Array> arrays = new ArrayList<Array>();
	
	private Object() {
		
	}
	
	public Object(String name) {
		size += 1 + 2 + 2 + 2;
		setName(name);
	}
	
	public int getSize() {
		return size;
	}
	
	public int getBytes(byte[] dest, int pointer) {
		pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
		pointer = writeBytes(dest, pointer, nameLength);
		pointer = writeBytes(dest, pointer, name);
		pointer = writeBytes(dest, pointer, size);
		
		pointer = writeBytes(dest, pointer, fieldCount);
		for (Field field : fields)
			pointer = field.getBytes(dest, pointer);
		
		pointer = writeBytes(dest, pointer, stringCount);
		for (Str string : strings)
			pointer = string.getBytes(dest, pointer);
		
		pointer = writeBytes(dest, pointer, arrayCount);
		for (Array array : arrays)
			pointer = array.getBytes(dest, pointer);
		
		return pointer;
	}
	
	public void addField(Field field) {
		fields.add(field);
		size += field.getSize();
		
		fieldCount = (short) fields.size();
	}
	
	public void addString(Str string) {
		strings.add(string);
		size += string.getSize();
		
		stringCount = (short) strings.size();
	}
	
	public void addArray(Array array) {
		arrays.add(array);
		size += array.getSize();
		
		arrayCount = (short) arrays.size();
	}
	
	public Field findField(String name) {
		for (Field field : fields)
			if (field.getName().equals(name))
				return field;
		return null;
	}
	
	
	public Str findString(String name) {
		for (Str string : strings)
			if (string.getName().equals(name))
				return string;
		return null;
	}
	
	public Array findArray(String name) {
		for (Array array : arrays)
			if (array.getName().equals(name))
				return array;
		return null;
	}
	
	public static Object Deserialize(byte[] data, int pointer) {
		byte containerType = data[pointer++];
		assert(containerType == CONTAINER_TYPE);
		
		Object result = new Object();
		result.nameLength = readShort(data, pointer);
		pointer += 2;
		result.name = readString(data, pointer, result.nameLength).getBytes();
		pointer += result.nameLength;
		
		result.size = readInt(data, pointer);
		pointer += 4;
		
 		result.fieldCount = readShort(data, pointer);
		pointer += 2;

		for (int i = 0; i < result.fieldCount; i++) {
			Field field = Field.Deserialize(data, pointer);
			result.fields.add(field);
			pointer += field.getSize();
		}
		
		result.stringCount = readShort(data, pointer);
		pointer += 2;

		for (int i = 0; i < result.stringCount; i++) {
			Str string = Str.Deserialize(data, pointer);
			result.strings.add(string);
			pointer += string.getSize();
		}
		
		result.arrayCount = readShort(data, pointer);
		pointer += 2;

		for (int i = 0; i < result.arrayCount; i++) {
			Array array = Array.Deserialize(data, pointer);
			result.arrays.add(array);
			pointer += array.getSize();
		}
		
		return result;
		
	}
	
}
