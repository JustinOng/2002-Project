package mystars.entities;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Entity implements Serializable {
	private static final long serialVersionUID = 1L;
	private static IStorage storage;
	
	protected static void store(String storageId, int id, Serializable e) {
		store(storageId, Integer.toString(id), e);
	}
	
	protected static void store(String storageId, String id, Serializable e) {
		storage.store(storageId, id, e);
	}
	
	protected static Serializable get(String storageId, int id) {
		return get(storageId, Integer.toString(id));
	}
	
	protected static Serializable get(String storageId, String id) {
		if (storage == null) throw new IllegalStateException("Storage not configured");
		
		return storage.get(storageId, id); 
	}
	
	protected static ArrayList<Serializable> getAll(String storageId) {
		return storage.getAll(storageId);
	}
	
	public static void setStorage(IStorage _storage) {
		storage = _storage;
	}
}
