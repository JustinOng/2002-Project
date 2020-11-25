package mystars.entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This Istorage interface contains the abstract methods for storing and
 * retrieving serializable objects. Objects can be identified by a category and
 * id. For example, students can be stored all be stored with the category
 * "student", with each student having a unique id eg "u1", "u2", u3".
 */
public interface IStorage {
	/**
	 * Stores a serializable object identified by {@code type} and {@code id}.
	 * 
	 * @param type Category of the object to be stored
	 * @param id   ID of the object to be stored
	 * @param e    The serializable object to be stored
	 */
	public void store(String type, String id, Serializable e);

	/**
	 * Retrieves a serializable object identified by {@code type} and {@code id}.
	 * 
	 * @param type Category of the object to be retrieved
	 * @param id   ID of the object to be retrieved
	 * @return Retrieved object
	 */
	public Serializable get(String type, String id);

	/**
	 * Retrieves all objects with the category {@code type}
	 * 
	 * @param type Category to retrieve
	 * @return Retrieved objects
	 */
	public ArrayList<Serializable> getAll(String type);
}
