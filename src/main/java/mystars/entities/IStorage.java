package mystars.entities;

public interface IStorage {
	public void store(String type, String id, Entity e);
	public Entity get(String type, String id);
}
