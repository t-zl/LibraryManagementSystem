/**
 * Book Subclass of Item - represents a book item in the library system.
 * Extends the Item class with book-specific properties.
 * Author: Thomas Lavelle
 */
public class Book extends Item{

    // Constructor - initialises a new Book object with the provided attributes.
    // The 'type' parameter is automatically set to "Book".
    public Book(String barcode, String type, String author, String title, int year, String ISBN) {
        super(barcode, type, author, title, year, ISBN);
        type = "Book";
    }

    // Overriding the abstract getLoanPeriod() method
    // Returns the loan period for a book item, which is 4 weeks.
    public int getLoanPeriod() {
        return 4; // for Book items loans are for a 4-week period
    }

    // Overriding the abstract getMaxRenews() method
    // Returns the maximum number of renewals allowed for a book item, which is 3.
    public int getMaxRenews() {
        return 3; // Book cannot be renewed more than three times
    }

}
