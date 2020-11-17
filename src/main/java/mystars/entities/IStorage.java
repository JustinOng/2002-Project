package mystars.entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <h1>Interface: IStorage</h1>
 * 
 * This Istorage interface contains the abstract methods for storing and retrieving data to and from entities.
 */
public interface IStorage
{
	/**
	 * Stores the content of the passed in serializable data object
	 * into a storage object using the passed in storage Id and string ID.
	 * 
	 * @param type	The type of the storage object.
	 * @param id	The ID corresponding to an entry in the storage object.
	 * @param e		The serializable object that the data is to be stored into.
	 */
	public void store(String type, String id, Serializable e);
	
	/**
	 * 
	 * 
	 * @param type	The type of the storage object.
	 * @param id	The ID corresponding to an entry in the storage object.
	 * @return		The serializable object matching the parameters passed to it.
	 */
	public Serializable get(String type, String id);
	
	/**
	 * Returns an Arraylist containing all the entries corresponding to the ID in the current entity's storage object.
	 * 
	 * @param type	The type of the storage object to be retrieved.
	 * @return		The Arraylist of the storage object entries matching the ID.
	 */
	public ArrayList<Serializable> getAll(String type);
}
