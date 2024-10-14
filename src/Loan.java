/**
 * Loan Class
 * Represents a loan item in the library system.
 * Stores details such as barcode, user ID, issue date, due date, and number of renews.
 * Author: Thomas Lavelle
 */
public class Loan {
    //"For each loan, their Barcode, User ID, Issue Date,
    // Due Date and Number of Renews must be recorded."
    private final String barcode; // The barcode of the loaned item
    private final String userID; // The user ID of the borrower
    private String issueDate; // The date when the item was issued - I use java.util.Date for modifications
    private String dueDate; // The date when the item is due for return

    // keeps track of how many times a loan object has been renewed
    private int numberOfRenews;

    // Constructor
    public Loan(String barcode, String userID, String issueDate, String dueDate, int numberOfRenews) {
        this.barcode = barcode;
        this.userID = userID;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.numberOfRenews = numberOfRenews;
    }

    // Getters and setters
    public String getBarcode() {return barcode;}

    public String getUserID() {
        return userID;
    }

    public String getIssueDate() {
        return issueDate;
    }
    public String getDueDate() {
        return dueDate;
    }
    public void setDueDate(String dueDate) {this.dueDate = dueDate;}

    // Getter and increment method for the number of renewals
    public int getNumberOfRenews() {return numberOfRenews;}
    public void incrementRenews() {this.numberOfRenews++;} // increments the items renewal count

    // toString method to display loan details in a user-friendly format
    public String toString() {
        return "Barcode: " + barcode +
                "\tUser ID: " + userID +
                "\tIssue Date: " + issueDate +
                "\tDue Date: " + dueDate +
                "\tNumber of Renews: " + numberOfRenews;
    }
}


