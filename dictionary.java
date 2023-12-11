MyProgram.java:
--------------
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class dictionary {
    public static void main(String[] args) {
        //Stores a user entered password
        Scanner in = new Scanner(System.in);
        System.out.println("Enter a password: ");
        String targetPassword = in.nextLine();
        //Pulls all stored passwords in dictionary and stores them
        String dictionary = "passList.txt";
        try {
            dictionaryAttack(dictionary, targetPassword);
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


