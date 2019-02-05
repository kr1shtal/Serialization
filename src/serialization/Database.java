package serialization;

import static serialization.SerializationUtils.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Database extends Container {

	public static final byte[] HEADER = "JSDB".getBytes();
	public static final short VERSION = 0x0100;
	public static final byte CONTAINER_TYPE = ContainerType.DATABASE;
	
	private short objectCount;
	public List<Object> objects = new ArrayList<Object>();
	
	private Database() {
		
	}
	
	public Database(String name) {
		setName(name);
		
		size += HEADER.length + 2 + 1 + 2;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getBytes(byte[] dest, int pointer) {
		pointer = writeBytes(dest, pointer, HEADER);
		pointer = writeBytes(dest, pointer, VERSION);
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

	public Object findObject(String name) {
		for (Object object : objects)
			if (object.getName().equals(name))
				return object;
		return null;
	}
	
	public static Database Deserialize(byte[] data) {
		int pointer = 0;
		assert(readString(data, pointer, HEADER.length).equals(HEADER));
		pointer += HEADER.length;
		
		if (readShort(data, pointer) != VERSION) {
			System.err.println("Invalid JSDB version!");
			return null;
		}
		pointer += 2;
		
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
	
	public void serializeToFile(String path) {
		byte[] data = new byte[getSize()];
		getBytes(data, 0);
		try {
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(path));
			stream.write(data);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}