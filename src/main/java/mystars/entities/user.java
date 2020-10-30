package mystars.entities;
/**
 * <h1>Class: user</h1>
 * 
 * This user class serves as the superclass for 'admin' and 'student'.
 * It manages the verification of a user's password when logging in.
 * 
 * @param userName The user name assigned to the admin or student user
 * instantiated by the subclasses.
 * 
 * @param passwordHash The hash of the password for an instance of the user
 * instantiated by the subclasses.
 * 
 * @param isAdmin Boolean variable used by the subclasses to determine if a user
 * is an admin with admin rights, or only a student with no admin rights.
 * 
 * @param users A hashmap hash table containing mapped key value pairs of user
 * names and their password hashes.  
 */

//Libraries for the SHA-256 hashing function.
import java.math.BigInteger;  
import java.nio.charset.StandardCharsets; 
import java.security.MessageDigest;  
import java.security.NoSuchAlgorithmException;  

import java.util.HashMap;

public class user
{
	protected String userName;
	private String passwordHash;
	protected boolean isAdmin = false;
	
	//Hashmap tables contain mapped key pair values.
	HashMap<String, user> users = new HashMap<String, user>();
	
	/**
	 * This method
	 * 
	 * Method is accessible to objects of this class and objects of all subclasses.
	 * 
	 * @param userName The user's username.
	 * @param password Theuser's password. 
	 */
	protected user(String userName, String password)
	{
		this.userName = userName;
	}
	
	/*
	 * ------------------------------------------------------
	 * Additional functions for calculating password hash.
	 */
	
	public static byte[] getSHA(String input) throws NoSuchAlgorithmException 
    {  
        // Static getInstance method is called with hashing SHA  
        MessageDigest md = MessageDigest.getInstance("SHA-256");  
  
        // digest() method called  
        // to calculate message digest of an input  
        // and return array of byte 
        return md.digest(input.getBytes(StandardCharsets.UTF_8));  
    }
	
	public static String toHexString(byte[] hash) 
    { 
        // Convert byte array into signum representation  
        BigInteger number = new BigInteger(1, hash);  
  
        // Convert message digest into hex value  
        StringBuilder hexString = new StringBuilder(number.toString(16));  
  
        // Pad with leading zeros 
        while (hexString.length() < 32)  
        {  
            hexString.insert(0, '0');  
        }  
        return hexString.toString();  
    }
	
	/**
	 * This method calculates the hash of the user's password
	 * using the SHA-256 algorithm.
	 * 
	 * @param input The user's plain text password.
	 * @return The SHA-256 hash of the user's password.
	 */
	protected String hashPassword(String input)
	{
		//Try to compute the hash.
		try 
        {  
			passwordHash = toHexString(getSHA(input));
            return passwordHash;
        } 
        //If the hash calculation fails.
        catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown for incorrect algorithm: " + e);  
        }
		return "Password hash not successfully generated.";
	}
	
	/**
	 * This method
	 * 
	 * @param userName The user's username.
	 * @param password The user's password. 
	 */
	public static user login(String userName, String password)
	{
		
	}
	
	/**
	 * This method
	 * 
	 * @param password The user's password.
	 */
	public user login(String password)
	{
		
	}
}
