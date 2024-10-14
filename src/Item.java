/**
 * The Item Class - represents a library item available for loan.
 * Serves as the base class for subclasses Book and Multimedia.
 * Author: Thomas Lavelle
 */
public class Item implements Loanable {
    private String barcode;
    private String author;
    private String title;
    private String type; // This is to indicate whether it's a Book or Multimedia
    private int year;
    private String ISBN;

    // Variable storing Item loan period in weeks
    private int loanPeriod;  // value is set in the subclasses
    private int maxRenews;


    // Constructor
    public Item(String barcode, String author, String title, String type, int year, String ISBN) {
        this.barcode = barcode;
        this.author = author;
        this.title = title;
        this.type = type;
        this.year = year;
        this.ISBN = ISBN;
    }

    // Getters and setters for the items properties
    public String getBarcode() {
        return barcode;
    }
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getType() {
        return type;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getISBN() {
        return ISBN;
    }


    // Implementing methods from Loanable interface
    public int getLoanPeriod() {
        return loanPeriod;
    }

    public int getMaxRenews() {
        return maxRenews;
    }


    // toString method to display object as String in user-friendly format
    public String toString() {
        return "Barcode: " + getBarcode() + " | Author: " + getAuthor() + " | Title: " + getTitle()
                + " | Type: " + getType() + " | Year: " + getYear() + " | ISBN: " + getISBN() + "\n";
    }

}


