SHA256.java:
-----------
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SHA256 {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        //Stores a user entered password
        Scanner in = new Scanner(System.in);
        System.out.println("Enter a password: ");
        String passwordToHash = in.nextLine();
        String salt = getSalt();
        String dictionary = "passList.txt";

        String securePassword = get_SHA_256_SecurePassword(passwordToHash, salt);
        System.out.println("Password hash in SHA-256 is: " + securePassword);
        try {
            dictionaryAttack(dictionary, securePassword, salt);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String get_SHA_256_SecurePassword(String passwordToHash,
            String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

   

    // Add salt
    private static String getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt.toString();
    }
    
    private static void dictionaryAttack(String dictionary, String targetPassword, String salt) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(dictionary))) {
            String password;
            long startTime = System.currentTimeMillis();
            //Goes through all passwords in dictionary and checks if they are equal to the target password
            while ((password = reader.readLine()) != null) {
                password = get_SHA_256_SecurePassword(password, salt);
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
