/**
 * Multimedia Subclass of Item - represents a multimedia item in the library system.
 * Extends the Item class with multimedia-specific properties.
 * Author: Thomas Lavelle
 */
public class Multimedia extends Item {
    // Constructor -  initialises a new Multimedia object with the provided attributes.
    // The 'type' parameter is automatically set to "Multimedia".
    public Multimedia(String barcode, String type, String author, String title, int year, String ISBN) {
        super(barcode, type, author, title, year, ISBN);
        type = "Multimedia";
    }

    // Overriding the abstract getLoanPeriod() method
    // Returns the loan period for a multimedia item, which is 1 week.
    public int getLoanPeriod() {
        return 1;
    }

    // Overriding the abstract getMaxRenews() method
    // Returns the maximum number of renewals allowed for a multimedia item, which is 2.
    public int getMaxRenews() {
        return 2;
    }
}
