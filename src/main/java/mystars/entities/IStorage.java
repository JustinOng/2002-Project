package mystars.entities;

/**
 * This Istorage interface contains the abstract methods for storing and retrieving data to and from entities.
 *
 */
public interface IStorage
{
	/**
	 * 
	 * @param type
	 * @param id
	 * @param e
	 */
	public void store(String type, String id, Entity e);
	
	/**
	 * 
	 * @param type
	 * @param id
	 * @return
	 */
	public Entity get(String type, String id);
}
