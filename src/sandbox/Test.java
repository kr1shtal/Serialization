package sandbox;

import serialization.*;
import serialization.Object;

import static serialization.SerializationUtils.*;

public class Test {
	
	public static void printBytes(byte[] data) {
		for (int i = 0; i < data.length; i++)
			System.out.printf("0x%x ", data[i]);
	}
	
	public static void serializationTest() {
		int[] numbers = new int[50_000];
		byte[] data = new byte[16];
		byte[] bytes = new byte[] { 0x0, 0x0, 0x1, 0x9 };
		boolean[] bools = new boolean[8];

		long number = Long.MAX_VALUE;
		String str = "String";
		int bitfield = 0;
		int pointer = writeBytes(data, 0, number);
		
		pointer = writeBytes(data, pointer, number);		
		printBytes(data);

		for (int i = 0; i < numbers.length; i++)
			numbers[i] = i / 2;
		
		for (int i = 0; i < bools.length; i++)
			bools[i] = i % 2 == 0 ? true : false;
		
		for (int i = 0; i < bools.length; i++)
			System.out.println(bools[i]);
		
		for (int i = 0; i < bools.length; i++)
			bitfield |= (bools[i] ? 1 : 0) << i;
		
		System.out.println(Integer.toBinaryString(bitfield));
				
		writeBytes(data, 0, str);
		printBytes(data);
		System.out.println(readInt(bytes, 0));

		Database db = new Database("Database");
		Array array = Array.Integer("Random Numbers", numbers);
		Field field = Field.Integer("Integer", 4);
		Object object = new Object("Object");
		Object entity = new Object("Entity");
		
		object.addArray(Array.Char("String", "Hello, World!".toCharArray()));
		object.addArray(array);
		entity.addField(Field.Boolean("F", false));
		object.addField(field);
		object.addString(Str.Create("Test string", "Testing String Class"));
		
		db.addObject(object);
		db.addObject(entity);
		
		
		db.serializeToFile("test.jds");
	}
	
	public static void deserializationTest() {
		Database db = Database.DeserializeFromFile("test.jds");
		System.out.println("Database: " + db.getName());
		for (Object obj : db.objects) {
			System.out.println("\t" + obj.getName());
			
			for (Field f : obj.fields)
				System.out.println("\t\t" + f.getName());
			for (Str s : obj.strings)
				System.out.println("\t\t" + s.getName());
			for (Array a : obj.arrays)
				System.out.println("\t\t" + a.getName());
		}
	}
	
	public static void main(String[] args) {
		serializationTest();
		deserializationTest();
	}
	
}