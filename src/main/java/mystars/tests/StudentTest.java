package mystars.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import mystars.entities.*;
import mystars.enums.Gender;
import mystars.enums.Nationality;
import mystars.exceptions.AppException;

public class StudentTest {
	@Test
	void test_unique() throws AppException {
		FileStorage storage = new FileStorage("");
		Entity.setStorage(storage);

		new User("user1", "password").markPersistent();
		// should succeed: unique username
		new User("user2", "password").markPersistent();

		assertThrows(AppException.class, () -> new User("user1", "password"), "username not unique");

		new Student("Student One", "email1@example.com", "U1234567A", "student1", "password", Gender.Male,
				Nationality.Singaporean).markPersistent();
		
		// should succeed: unique username
		new Student("Student Two", "email2@example.com", "U1234568A", "student2", "password", Gender.Male,
				Nationality.Singaporean).markPersistent();
		
		assertThrows(AppException.class, () -> new Student("Student Three", "email3@example.com", "U1234569A", "student1", "password", Gender.Male,
				Nationality.Singaporean), "username already exists");
		
		assertThrows(AppException.class, () -> new Student("Student Three", "email3@example.com", "U1234568A", "student3", "password", Gender.Male,
				Nationality.Singaporean), "duplicate matric number");
	}
}
