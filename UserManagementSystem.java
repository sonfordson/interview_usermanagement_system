import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserManagementSystem {
    private Map<String, User> userCredentials;

    public UserManagementSystem() {
        userCredentials = new HashMap<>();
    }

    public void registerUser(String username, String password) {
        if (userCredentials.containsKey(username)) {
            System.out.println("Username already exists. Please choose a different username.");
            return;
        }

        String hashedPassword = hashPassword(password);
        User user = new User(username, hashedPassword);
        userCredentials.put(username, user);
        System.out.println("Registration successful.");
    }

    public void loginUser(String username, String password) {
        if (!userCredentials.containsKey(username)) {
            System.out.println("Username not found. Please register first.");
            return;
        }

        String hashedPassword = hashPassword(password);
        User user = userCredentials.get(username);

        if (hashedPassword.equals(user.getHashedPassword())) {
            System.out.println("Login successful.");
        } else {
            System.out.println("Incorrect password. Please try again.");
        }
    }

    public void logoutUser() {
        System.out.println("Logged out successfully.");
    }

    public void resetPassword(String username, String newPassword) {
        if (!userCredentials.containsKey(username)) {
            System.out.println("Username not found. Please register first.");
            return;
        }

        String hashedPassword = hashPassword(newPassword);
        User user = userCredentials.get(username);
        userCredentials.put(username, new User(username, hashedPassword));
        System.out.println("Password reset successful.");
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();

            for (byte b : hashedBytes) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error hashing password.");
            return null;
        }
    }

    public static void main(String[] args) {
        UserManagementSystem userManagementSystem = new UserManagementSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Register\n2. Login\n3. Logout\n4. Reset Password");