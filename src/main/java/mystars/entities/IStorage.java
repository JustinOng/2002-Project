package mystars.entities;

import java.io.Serializable;

public interface IStorage {
	public void store(String type, String id, Serializable e);
	public Serializable get(String type, String id);
}
