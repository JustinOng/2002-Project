package mystars.controllers;

import mystars.entities.*;

public class StorageController {
	private FileStorage storage;
	
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
	
	public void writeToDisk() {
		storage.writeToDisk();
	}

	public void loadFromDisk() {
		storage.loadFromDisk();
	}
}
