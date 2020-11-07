package mystars.entities;

import java.io.Serializable;

public abstract class Entity implements Serializable {
	private static final long serialVersionUID = 1L;
	private static String storageId;
	private static IStorage storage;
	
	protected static void store(int id, Entity e) {
		store(Integer.toString(id), e);
	}
	
	protected static void store(String id, Entity e) {
		storage.store(storageId, id, e);
	}
	
	protected static Entity get(int id) {
		return get(Integer.toString(id));
	}
	
	protected static Entity get(String id) {
		if (storage == null) throw new IllegalStateException("Storage not configured");
		
		return storage.get(storageId, id); 
	}
	
	public static void setStorage(String _storageId, IStorage _storage) {
		storageId = _storageId;
		storage = _storage;
	}
}
