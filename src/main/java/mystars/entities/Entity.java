package mystars.entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <h1>Class: Entity</h1>
 * 
 * This class implements the entities used in the MySTARS program.
 */
public abstract class Entity implements Serializable {
	/**
	 * Serialization of the course ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * Create a new storage object.
	 */
	private static IStorage storage;
	
	/**
	 * (Overloaded method) Stores the content of the passed in serializable data object
	 * into a storage object using the passed in storage Id and integer ID.
	 * 
	 * @param storageId The ID corresponding to the storage object.
	 * @param id		The ID corresponding to an entry in the storage object.
	 * @param e			The serializable object that the data is to be stored into.
	 */
	protected static void store(String storageId, int id, Serializable e) {
		store(storageId, Integer.toString(id), e);
	}
	
	/**
	 * (Overloaded method) Stores the content of the passed in serializable data object
	 * into a storage object using the passed in storage Id and string ID.
	 * 
	 * @param storageId The ID corresponding to the storage object.
	 * @param id		The ID corresponding to an entry in the storage object.
	 * @param e			The serializable object that the data is to be stored into.
	 */
	protected static void store(String storageId, String id, Serializable e) {
		storage.store(storageId, id, e);
	}
	
	/**
	 * (Overloaded method) Retrieves an entry from a storage object based on its ID.
	 * 
	 * @param storageId The ID corresponding to the storage object.
	 * @param id		The ID corresponding to an entry in the storage object.
	 * @return			The serializable object matching the parameters passed to it.
	 */
	protected static Serializable get(String storageId, int id) {
		return get(storageId, Integer.toString(id));
	}
	
	/**
	 * (Overloaded method) Retrieves an entry from a storage object based on its ID.
	 * Throws an exception if the storage object is not yet created or configured.
	 * 
	 * @param storageId	The ID corresponding to the storage object.
	 * @param id		The ID corresponding to an entry in the storage object.
	 * @return 			The serializable object matching the parameters passed to it. 
	 */
	protected static Serializable get(String storageId, String id) {
		if (storage == null) throw new IllegalStateException("Storage not configured");
		return storage.get(storageId, id); 
	}
	
	/**
	 * Returns an Arraylist containing all the entries corresponding to the ID in the entity's storage object.
	 * 
	 * @param storageId The ID of the storage object to be retrieved.
	 * @return 			The Arraylist of the storage object entries matching the ID.
	 */
	protected static ArrayList<Serializable> getAll(String storageId) {
		return storage.getAll(storageId);
	}
	
	/**
	 * Assign the current entity's storage object as the storage object being passed in.
	 * 
	 * @param _storage The storage object being passed in.
	 */
	public static void setStorage(IStorage _storage) {
		storage = _storage;
	}
}
