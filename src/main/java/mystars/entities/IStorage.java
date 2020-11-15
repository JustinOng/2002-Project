package mystars.entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This Istorage interface contains the abstract methods for storing and retrieving data to and from entities.
 *
 */
public interface IStorage
{
	public void store(String type, String id, Serializable e);
	public Serializable get(String type, String id);
	public ArrayList<Serializable> getAll(String type);
}
