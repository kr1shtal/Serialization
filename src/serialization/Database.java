package serialization;

import static serialization.SerializationUtils.*;

import java.util.ArrayList;
import java.util.List;

public class Database {

	public static final byte[] HEADER = "JSDB".getBytes();
	public static final byte CONTAINER_TYPE = ContainerType.DATABASE;
	
	public short nameLength;
	public byte[] name;
	
	private short objectCount;
	private List<Object> objects = new ArrayList<Object>();
	
	private int size = HEADER.length + Type.getSize(Type.BYTE) + 
			Type.getSize(Type.SHORT) + Type.getSize(Type.INTEGER) + Type.getSize(Type.SHORT);
	
	public Database(String name) {
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
		pointer = writeBytes(dest, pointer, HEADER);
		pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
		pointer = writeBytes(dest, pointer, nameLength);
		pointer = writeBytes(dest, pointer, name);
		pointer = writeBytes(dest, pointer, size);
		
		pointer = writeBytes(dest, pointer, objectCount);
		for (Object object : objects)
			pointer = object.getBytes(dest, pointer);
		
		return pointer;
	}
	
	public int getSize() {
		return size;
	}
	
	public void addObject(Object object) {
		objects.add(object);
		size += object.getSize();
		
		objectCount = (short) objects.size();
	}
	
}