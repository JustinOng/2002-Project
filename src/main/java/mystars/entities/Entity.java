package mystars.entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <h1>Class: Entity</h1>
 * 
 * This class provides helper functions for persistance of child classes.
 * Classes that need to be retrievable directly (ie be retrievable from an id)
 * inherit from this class.
 * 
 * @see IStorage
 */
public abstract class Entity implements Serializable {
	/**
	 * ID for versioning of serialized data.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Object to use to store data
	 */
	private static IStorage storage;

	/**
	 * Stores a serializable object identified by {@code storageId} and {@code id}
	 * into the configured storage. {@code id} is converted to String before being
	 * used.
	 * 
	 * @param storageId Category of the object to be stored
	 * @param id        ID of the object to be stored
	 * @param e         The serializable object to be stored
	 */
	protected static void store(String storageId, int id, Serializable e) {
		store(storageId, Integer.toString(id), e);
	}

	/**
	 * Stores a serializable object identified by {@code storageId} and {@code id}
	 * into the configured storage.
	 * 
	 * @param storageId Category of the object to be stored
	 * @param id        ID of the object to be stored
	 * @param e         The serializable object to be stored
	 */
	protected static void store(String storageId, String id, Serializable e) {
		storage.store(storageId, id, e);
	}

	/**
	 * Retrieves a serializable object identified by {@code storageId} and
	 * {@code id} from the configured storage. {@code id} is converted to String
	 * before being used.
	 * 
	 * @param storageId Category of the object to be retrieved
	 * @param id        ID of the object to be retrieved
	 * @return Retrieved object
	 */
	protected static Serializable get(String storageId, int id) {
		return get(storageId, Integer.toString(id));
	}

	/**
	 * Retrieves a serializable object identified by {@code storageId} and
	 * {@code id} from the configured storage.
	 * 
	 * @param storageId Category of the object to be retrieved
	 * @param id        ID of the object to be retrieved
	 * @return Retrieved object
	 */
	protected static Serializable get(String storageId, String id) {
		if (storage == null)
			throw new IllegalStateException("Storage not configured");
		return storage.get(storageId, id);
	}

	/**
	 * Returns all objects with the category {@code storageId}
	 * 
	 * @param storageId Category of the objects to be retrieved
	 * @return List of all objects with the category {@code storageId}
	 */
	protected static ArrayList<Serializable> getAll(String storageId) {
		return storage.getAll(storageId);
	}

	/**
	 * Configures the storage to be used
	 * 
	 * @param _storage The storage object to be used.
	 */
	public static void setStorage(IStorage _storage) {
		storage = _storage;
	}
	
	/**
	 * Marks this instance as persistent (ie keep in storage)
	 */
	public abstract void markPersistent();
}
