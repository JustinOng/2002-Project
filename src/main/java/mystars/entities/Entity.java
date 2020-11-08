package mystars.entities;

import java.io.Serializable;

public abstract class Entity implements Serializable {
	private static final long serialVersionUID = 1L;
	private static IStorage storage;
	
	protected static void store(String storageId, int id, Entity e) {
		store(storageId, Integer.toString(id), e);
	}
	
	protected static void store(String storageId, String id, Entity e) {
		storage.store(storageId, id, e);
	}
	
	protected static Entity get(String storageId, int id) {
		return get(storageId, Integer.toString(id));
	}
	
	protected static Entity get(String storageId, String id) {
		if (storage == null) throw new IllegalStateException("Storage not configured");
		
		return storage.get(storageId, id); 
	}
	
	public static void setStorage(IStorage _storage) {
		storage = _storage;
	}
}
