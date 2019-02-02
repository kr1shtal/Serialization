package serialization;

import java.util.ArrayList;
import java.util.List;

import static serialization.SerializationUtils.*;

public class Object {
	
	public static final byte CONTAINER_TYPE = ContainerType.OBJECT;
	
	public short nameLength;
	public byte[] name;
	
	private short fieldCount;
	private short stringCount;
	private short arrayCount;
	
	private List<Field> fields = new ArrayList<Field>();
	private List<Str> strings = new ArrayList<Str>();
	private List<Array> arrays = new ArrayList<Array>();
	
	private int size = Type.getSize(Type.BYTE) + Type.getSize(Type.SHORT) + 
			Type.getSize(Type.INTEGER) + Type.getSize(Type.SHORT) + Type.getSize(Type.SHORT);
	
	public Object(String name) {
		setName(name);
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
	
	public int getSize() {
		return size;
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
	
}
