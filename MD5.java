MD5.java:
--------
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MD5 
{
  public static void main(String[] args) 
  {
    //Stores a user entered password
    Scanner in = new Scanner(System.in);
    System.out.println("Enter a password: ");
    String passwordToHash = in.nextLine();
    String generatedPassword = null;
    String dictionary = "passList.txt";

    try 
    {
      // Create MessageDigest instance for MD5
      MessageDigest md = MessageDigest.getInstance("MD5");

      // Add password bytes to digest
      md.update(passwordToHash.getBytes());

      // Get the hash's bytes
      byte[] bytes = md.digest();

      // This bytes[] has bytes in decimal format. Convert it to hexadecimal format
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < bytes.length; i++) {
        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
      }

      // Get complete hashed password in hex format
      generatedPassword = sb.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    System.out.println(generatedPassword);
    
    try {
            dictionaryAttack(dictionary, generatedPassword);
        } catch (IOException e) {
            e.printStackTrace();
        }
    
  }
  private static void dictionaryAttack(String dictionary, String targetPassword) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(dictionary))) {
            String password;
            long startTime = System.currentTimeMillis();
            //Goes through all passwords in dictionary and checks if they are equal to the target password
            while ((password = reader.readLine()) != null) {
                try 
                {
                    // Create MessageDigest instance for MD5
                    MessageDigest md = MessageDigest.getInstance("MD5");

                    // Add password bytes to digest
                    md.update(password.getBytes());

                    // Get the hash's bytes
                    byte[] bytes = md.digest();

                    // This bytes[] has bytes in decimal format. Convert it to hexadecimal format
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < bytes.length; i++) {
                      sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                    }

                  // Get complete hashed password in hex format
                  password = sb.toString();
                }catch (NoSuchAlgorithmException e) {
                  e.printStackTrace();
                }
                //compares hashed input and hashed password from passlist
                if (password.equals(targetPassword)) {
                    long endTime = System.currentTimeMillis();
                    long elapsedTime = endTime - startTime;
                    System.out.println("Password cracked: " + password);
                    System.out.println("Time elapsed: " + elapsedTime + " milliseconds");
                    return; //Exits program once the password is found
                }
            }
            //prints if the target password is not found in the list
            System.out.println("Password not found in the dictionary.");
        }
    }
}
