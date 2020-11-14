package mystars.entities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class FileStorage implements IStorage {
	private HashMap<String, HashMap<String, Entity>> storage = new HashMap<>();
	private String filename;

	public FileStorage(String filename) {
		this.filename = filename;
	}

	@Override
	public void store(String type, String id, Entity e) {
		if (!storage.containsKey(type)) {
			storage.put(type, new HashMap<>());
		}

		storage.get(type).put(id, e);
	}

	@Override
	public Entity get(String type, String id) {
		if (!storage.containsKey(type) || !storage.get(type).containsKey(id)) {
			return null;
		}

		return storage.get(type).get(id);
	}

	public void writeToDisk() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
			out.writeObject(storage);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void loadFromDisk() {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
			storage = (HashMap<String, HashMap<String, Entity>>) in.readObject();
			in.close();
		} catch (IOException | ClassNotFoundException e) {
			storage = new HashMap<>();
		}
	}
}
