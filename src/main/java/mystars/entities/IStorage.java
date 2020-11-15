package mystars.entities;

import java.io.Serializable;
import java.util.ArrayList;

public interface IStorage {
	public void store(String type, String id, Serializable e);
	public Serializable get(String type, String id);
	public ArrayList<Serializable> getAll(String type);
}
