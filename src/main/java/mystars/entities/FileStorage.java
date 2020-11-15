package mystars.entities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * This FileStorage class inherits from the abstract class 'Istorage'.
 * It manages the storage and retrival of data to and from entities.
 *
 */
public class FileStorage implements IStorage {
	private HashMap<String, HashMap<String, Entity>> storage = new HashMap<>();
	private String filename;

	/**
	 * Class constructor which sets the file path for the object.
	 * 
	 * @param filename The path to the file stored on disk.
	 */
	public FileStorage(String filename) {
		this.filename = filename;
	}

	@Override
	/**
	 * 
	 * @param type
	 * @param id
	 * @param e
	 */
	public void store(String type, String id, Entity e) {
		if (!storage.containsKey(type)) {
			storage.put(type, new HashMap<>());
		}
		storage.get(type).put(id, e);
	}

	@Override
	/**
	 * 
	 * @param type
	 * @param id
	 * @return
	 */
	public Entity get(String type, String id) {
		if (!storage.containsKey(type) || !storage.get(type).containsKey(id)) {
			return null;
		}
		return storage.get(type).get(id);
	}

	/**
	 * Create the object and file output streams to save object data to disk. 
	 */
	public void writeToDisk() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
			out.writeObject(storage);
			// close the object output stream when done.
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	/**
	 * Create the object and file input streams to retrieve object data from disk.
	 * Checks to ensure that the object data to be retrieved is present, otherwise it creates a new hashmap.
	 */
	public void loadFromDisk() {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
			storage = (HashMap<String, HashMap<String, Entity>>) in.readObject();
			// close the input object stream when done.
			in.close();
		} catch (IOException | ClassNotFoundException e) {
			storage = new HashMap<>();
		}
	}
}
