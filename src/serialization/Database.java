package serialization;

import static serialization.SerializationUtils.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Database {

	public static final byte[] HEADER = "JSDB".getBytes();
	public static final byte CONTAINER_TYPE = ContainerType.DATABASE;
	
	public short nameLength;
	public byte[] name;
	
	private short objectCount;
	public List<Object> objects = new ArrayList<Object>();
	
	private int size = HEADER.length + 1 + 2 + 4 + 2;
	
	private Database() {
		
	}
	
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
	
	public String getName() {
		return new String(name, 0, nameLength);
	}
	
	public int getSize() {
		return size;
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
	
	public void addObject(Object object) {
		objects.add(object);
		size += object.getSize();
		
		objectCount = (short) objects.size();
	}

	public static Database Deserialize(byte[] data) {
		int pointer = 0;
		assert(readString(data, pointer, HEADER.length).equals(HEADER));
		pointer += HEADER.length;
		
		byte containerType = readByte(data, pointer++);
		assert(containerType == CONTAINER_TYPE);
		
		Database result = new Database();
		result.nameLength = readShort(data, pointer);
		pointer += 2;
		result.name = readString(data, pointer, result.nameLength).getBytes();
		pointer += result.nameLength;
		
		result.size = readInt(data, pointer);
		pointer += 4;
		
		result.objectCount = readShort(data, pointer);
		pointer += 2;
	
		for (int i = 0; i < result.objectCount; i++) {
			Object object = Object.Deserialize(data, pointer);
			result.objects.add(object);
			pointer += object.getSize();
		}
		
		return result;
	}
	
	public static Database DeserializeFromFile(String path) {
		byte[] buffer = null; 
		try {
			BufferedInputStream stream = new BufferedInputStream(new FileInputStream(path));
			buffer = new byte[stream.available()];
			stream.read(buffer);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return Deserialize(buffer);
	}
	
}