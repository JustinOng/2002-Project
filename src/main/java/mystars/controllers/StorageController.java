package mystars.controllers;

import mystars.entities.*;

/**
 * <h1>Class: StorageController</h1>
 * 
 * This StorageController class manages the storage and application specific configuration for MySTARS.
 */
public class StorageController {
	/**
	 * Storage that will be used to persist data.
	 */
	private FileStorage storage;

	/**
	 * Creates a new StorageController and configures the storage.
	 * 
	 * @param filename The name of file to persist data to.
	 */
	public StorageController(String filename) {
		storage = new FileStorage(filename);
	}

	/**
	 * Loads data from disk (if already exists) and configure shutdown hook to persist data on exit.
	 */
	public void start() {
		loadFromDisk();

		Entity.setStorage(storage);

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				writeToDisk();
			}
		});
	}

	/**
	 * Write data to disk.
	 */
	public void writeToDisk() {
		storage.writeToDisk();
	}

	/**
	 * Load data from disk.
	 */
	public void loadFromDisk() {
		storage.loadFromDisk();
	}
}
