/**
 * The User class - represents a library user.
 * Stores user information and provides access methods.
 * Author: Thomas Lavelle
 */
public class User {
    private String userID;
    private String first_name;
    private String last_name;
    private String email;


    // Constructor - constructs a new User with the specified attributes.
    public User(String userID, String first_name, String last_name, String email){
        this.userID = userID;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
    }

    // Getters and Setters
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFirst_name() {
        return first_name;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    // toString method to display object as String - overrides
    public String toString() {
        return "\nUser: " + userID + " | " + first_name + " | " + last_name + " | " + email + "\n";
    }


}
