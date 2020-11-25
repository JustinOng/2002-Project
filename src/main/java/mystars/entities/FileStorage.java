package mystars.entities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class stores serializable data to a flat file on disk
 */
public class FileStorage implements IStorage {
	/**
	 * HashMap to store objects. Two layers to store each object with
	 * {@code <category, id}
	 */
	private HashMap<String, HashMap<String, Serializable>> storage = new HashMap<>();

	/**
	 * The name of the file to read/write data from/to
	 */
	private String filename;

	/**
	 * Creates a new instance of FileStorage
	 * 
	 * @param filename The name of the file to read/write data from/to
	 */
	public FileStorage(String filename) {
		this.filename = filename;
	}

	@Override
	public void store(String type, String id, Serializable e) {
		if (!storage.containsKey(type)) {
			storage.put(type, new HashMap<>());
		}
		storage.get(type).put(id, e);
	}

	@Override
	public Serializable get(String type, String id) {
		if (!storage.containsKey(type) || !storage.get(type).containsKey(id)) {
			return null;
		}
		return storage.get(type).get(id);
	}

	@Override
	public ArrayList<Serializable> getAll(String type) {
		if (storage.get(type) == null) {
			return new ArrayList<>();
		}

		return new ArrayList<Serializable>(storage.get(type).values());
	}

	/**
	 * Writes data to disk.
	 */
	public void writeToDisk() {
		try {
			System.out.println("Saving data");
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
			out.writeObject(storage);
			out.close();
			System.out.println("Done saving");
		} catch (IOException e) {
			System.err.println("Failed to save data");
			e.printStackTrace();
		}
	}

	/**
	 * Reads data from disk.
	 */
	@SuppressWarnings("unchecked")
	public void loadFromDisk() {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
			storage = (HashMap<String, HashMap<String, Serializable>>) in.readObject();
			in.close();
		} catch (IOException | ClassNotFoundException e) {
			storage = new HashMap<>();
		}
	}
}
