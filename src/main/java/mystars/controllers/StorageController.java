package mystars.controllers;

import mystars.entities.*;

/**
 * This StorageController class manages the storage and retrieving of the object data files to and from disk.
 *
 */
public class StorageController {
	/**
	 * Create a new filestorage object.
	 */
	private FileStorage storage;
	
	/**
	 * Assigns the file storage object to a file on the disk based on the file path,
	 * before calling the methods to read the data from the file.
	 * 
	 * @param filename The path to the file stored on disk.
	 */
	public StorageController(String filename) {
		storage = new FileStorage(filename);
		
		loadFromDisk();
		
		Entity.setStorage(storage);
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				writeToDisk();
			}
		});
	}
	
	/**
	 * Calls the method that writes data to the file on disk.
	 */
	public void writeToDisk() {
		storage.writeToDisk();
	}

	/**
	 * Calls the method that reads data from the file on disk.
	 */
	public void loadFromDisk() {
		storage.loadFromDisk();
	}
}
