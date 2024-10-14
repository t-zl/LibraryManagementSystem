/**
 * MainApplication Class
 * Entry point for the library management application, using a menu system.
 * Initializes components and interacts with users via console.
 * Author: Thomas Lavelle
 */

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainApplication {

    // Declaring Calendar and SimpleDateFormat instances to be used in methods for formatting Date objects
    private static final Calendar calendar = Calendar.getInstance();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    // Declare Scanner object for getting keyboard input - Avoiding unnecessary multiple scanner instances
    private static Scanner sc = new Scanner(System.in);

    // Declaring all ArrayLists used as static to be used throughout the class
    private static ArrayList<Item> itemArrayList = readItemsFromFile();
    private static ArrayList<User> userArrayList = readUsersFromFile();
    private static ArrayList<Loan> loanArrayList = readInLoans();  // at start off application


    // RUN THE READ IN LOAN ARRAY METHOD HERE

    public static void main(String[] args) {
        MainApplication mainApp = new MainApplication();   // instantiate
//        System.out.println(loanArrayList); // TEST
        mainApp.runMenu();
    }

    /**
     *  runMenu Method - contains the menu options for the Library Management System - user chooses from 7 options
     *  The user is asked/prompted to keep going within the menu until they select to exit.
     *  Upon exit, the loans and all changes made to them are written to file.
     */
    private void runMenu(){
        boolean exit = false;   // variable to store if user has chosen to exit from main program loop.

        // Start of Console interface
        System.out.println("-----------------------------------");
        System.out.println("|||| Library Management System ||||");
        System.out.println("-----------------------------------");

        do {
            // Try catch block for input error catching
            try {

                System.out.println("\nPlease Select a Function:");
                System.out.println("(1) Issue a Loan\n(2) Renew a Loan\n(3) Return a Loan\n" +
                        "(4) View all items on loan currently\n(5) Report on the Loan\n(6) Search for a Loan" +
                        "\n(7) Exit System + Save Changes");
                System.out.print("\nEnter your choice (1-7): ");
                int selectedFunction = sc.nextInt();

                // Switch statement to call corresponding function based on choice
                switch (selectedFunction) {
                    case 1:
                        System.out.print("\n[ISSUE LOAN ITEM]\nEnter barcode of Item to issue: ");
                        String barcode = sc.next();
                        System.out.print("Enter the Loan's associated User ID : ");
                        String userId = sc.next();

                        issueLoan(barcode, userId);  // calling the issueLoan function from Loan class.
                        break;
                    case 2:
                        System.out.print("\n[RENEW LOAN]\nEnter Item barcode of Loan to renew: ");
                        String barcode_no = sc.next();
                        renewLoan(barcode_no);      // should renew loan be static??
                        break;
                    case 3:
                        System.out.print("\n[RETURN LOAN]\nEnter barcode of Loaned Item to return: ");
                        String loan_barcode = sc.next();
                        returnLoan(loan_barcode);   // should return loan be static??
                        break;
                    case 4:
                        viewLoans();
                        break;
                    case 5:
                        System.out.println("\n[DISPLAYING REPORT]");
                        reportDetails();
                        break;
                    case 6:
                        System.out.print("\n[SEARCH]\nEnter barcode of Loan to search for: ");
                        String search_barcode = sc.next();
                        searchLoan(search_barcode);
                        break;
                    case 7:
                        exit = true;
                        // call my method to save loans to loans.csv and exit the system.
                        exitProgram(); // When the program exits, the list of current loans is written to LOANS.csv
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 7.\n");
                }

                // If the user did not choose to exit
                if (!exit) {
                    exit =  keepGoingMenu(); // Asking user do they want to keep using the program and set exit to answer
                }



            }catch (InputMismatchException e){
                System.out.println("\nINVALID CHOICE - You must enter an Integer from 1 to 7");
                // Consume invalid input - ensures that the invalid input does not remain in the input stream
                sc.nextLine();
            }catch (Exception e){
                System.err.println(e.getMessage());
            }

        }while (!exit); // end of do-while loop

    }


    /**
     * keepGoingMenu Method - to ask the user if they want to continue using the program.
     * If the user chooses to stop the program, it calls the exitProgram method
     * to save the current list of loans to file before exiting.
     *
     * @return true if the user wants to continue using the program, false otherwise.
     */
    private boolean keepGoingMenu() {
        System.out.println("\nWould you like To Continue Within The Program Menu?");
        System.out.print("Enter 'n' to stop program and save OR enter any character to continue: ");
        String decision = sc.next().toLowerCase(); // keep input lower case

        // if statement - checks users decision - only exits if user enters n
        if (decision.equals("n")) {
            exitProgram(); // call the exit program method - this writes current loans to file
            return true;
        }else{
            return false; // returns exit false
        }
    }

    // method used for exiting the main program - saves all current loans and prints exit message

    /**
     * exitProgram method - calls writeLoans function and then displays Exiting System message to user.
     */
    private void exitProgram(){
        // call function to save loans to loans.csv
        writeLoans();

        System.out.print("\n[EXITING SYSTEM - Saving Current List Of Loans]\n\t\tG\tO\tO\tD\tB\tY\tE");
    }

    // ------------------READING IN FROM FILE METHODS----------------------

    /**
     * readInLoans method - for reading in items currently on loan from the specified file into an ArrayList.
     *
     * @return ArrayList of Loan objects representing the items currently on loan.
     */
    private static ArrayList<Loan> readInLoans(){

        // filepath to read from
        String filepath = "src/files/LOANS.csv";

        // Initialize ARRAY LIST to store the Loans
        ArrayList<Loan> loans = new ArrayList<>();

        // initialise line variable
        String line;

        try{
            BufferedReader br = new BufferedReader(new FileReader(filepath));

            // skip first line which contains headings
            String headingLine = br.readLine(); // consumes first line

            while ((line = br.readLine()) != null){

//                System.out.println(line);  // TEST print

                String [] arr = line.trim().split(",");

                String barcode = arr[0];
                String userID = arr[1];
                String currentDate = arr[2];
                String dueDate = arr[3];
                int no_of_renews = Integer.parseInt(arr[4]);

                Loan this_loan = new Loan(barcode, userID, currentDate, dueDate, no_of_renews);
                loans.add(this_loan);


            } // end while loop

            br.close(); // Closing the buffered reader

        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage()+"\nPlease ensure the file is in the correct filepath");  // prints relevant error message to error stream

        }catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Array index out of bounds error occurred: " + e.getMessage());
            throw e; // Rethrow the exception - stops program

        } catch (IOException e) {
            // Handling IO exception, and logging an error message
            System.err.println("An IO error occurred: " + e.getMessage());
            System.exit(1); // Exiting the program with error code 1 - stop program

        }catch (Exception e){
            System.out.println("An Unexpected Error Occurred");// end try
            System.out.println(e.getMessage());
        }

        return loans;
    }

    /**
     * readItemsFromFile method - for reading in items from the ITEMS file and converting them into an
     * ArrayList of Item objects.
     *
     * @return ArrayList of Item objects read from the file.
     */
    private static ArrayList<Item> readItemsFromFile() {

        // filepath to read from
        String filepath = "src/files/ITEMS.csv";

        // Initialize ARRAY LIST to store the ITEMS
        ArrayList<Item> items = new ArrayList<>();

        // initialise line variable
        String line;

        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));

            // skip first line which contains headings
            String headingLine = br.readLine(); // consumes first line

            while ((line = br.readLine()) != null) {
//                System.out.println(line);  // TEST print

                String[] arr = line.trim().split(",");

                String barcode = arr[0];
                String author = arr[1];
                String title = arr[2];
                String type = arr[3];
                int year = Integer.parseInt(arr[4]);
                String ISBN = arr[5];

                // If the entry type is
                if (type.equals("Book")) {
                    Book item = new Book(barcode, author, title, type, year, ISBN);
                    items.add(item);
                } else {
                    Multimedia item = new Multimedia(barcode, author, title, type, year, ISBN);
                    items.add(item);
                }


            } // end while loop

            br.close(); // Closing the buffered reader

        } catch (IOException e) {
            // Handling IO exception, and logging an error message
            System.err.println("An IO error occurred: " + e.getMessage());
            System.exit(1); // Exiting the program with error code 1 - stop program

        }catch (Exception e){
            System.out.println("An Unexpected Error Occurred");// end try
            System.out.println(e.getMessage());
        }

        return items;
    }

    // readUsersFromFile function which reads in the USERS file and turns it into an array list of item objects
    /**
     * readUsersFromFile method - for reading users from the USERS.csv file and converting them into
     * an ArrayList of User objects.
     *
     * @return ArrayList of User objects read from the file.
     */
    private static ArrayList<User> readUsersFromFile() {

        // filepath to read from
        String filepath = "src/files/USERS.csv";

        // Initialize ARRAY LIST to store the USERS
        ArrayList<User> users = new ArrayList<>();

        // initialise line variable
        String line;

        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));

            while ((line = br.readLine()) != null) {

//                System.out.println(line);  // TEST print

                String[] arr = line.trim().split(",");

                String userID = arr[0];
                String first_name = arr[1];
                String last_name = arr[2];
                String email = arr[3];

                User user = new User(userID, first_name, last_name, email);
                users.add(user);

            } // end while loop

            br.close(); // Closing the buffered reader

        } catch (IOException e) {
            // Handling IO exception, and logging an error message
            System.err.println("An IO error occurred: " + e.getMessage());
            System.exit(1); // Exiting the program with error code 1 - stop program

        }catch (Exception e) {
            System.out.println("An Unexpected Error Occurred: " + e.getMessage());// end try

        }

        return users;
    }


    // ------------------FUNCTIONAL REQUIREMENT METHODS----------------------

    /**
     *
     * issueLoan Method to create a loan of an Item
     *
     * @param barcode - barcode entered by program user from keyboard
     * @param userID - userID entered by program user from keyboard
     *
     */
    private void issueLoan(String barcode, String userID){

        //  check that the user id exists first
        boolean userExists = false;

        for (User eachUser : userArrayList){                // userArrayList defined at top of class
            if (eachUser.getUserID().equals(userID)){
                userExists = true;
                break;  // break from for loop
            }
        }

        // if the user ID provided does not exist
        if (!userExists){
            System.out.println("Sorry. This User ID '" + userID + "' does not exist.");
            return; // Exit the issueLoan method if the user ID doesn't exist
        }

        // check that the Item is not already on loan
        if (isAlreadyLoaned(barcode)) {
            System.out.printf("%nSorry. Item(Barcode #: %s) is already on loan.%n" +
                    "You must try loaning an item that is not already on loan.%n",barcode);
            return; // Exit the issueLoan method if the Item is already in the loan record.
        }

        // Setting the Loan Issue and Return Date
        try {
            Item theItem = getItem(barcode); // get the item corresponding to the barcode

            // null checks for if the item or loan is not found from getItem or getLoan
            if (theItem == null) {
                // Handle the case where either thisItem or thisLoan is null - error message is within getLoan and getItem
                return;
            }

            // Get Issue Date + Return Date
            Date currentDate = new Date();

            // Using previously declared Calendar instance and set it to the current date
            calendar.setTime(currentDate);

            // ADD THE LOAN PERIOD AMOUNT to get DUE DATE
            calendar.add(Calendar.WEEK_OF_YEAR, theItem.getLoanPeriod());

            // Get the return date after adding weeks
            Date dueDate = calendar.getTime();

            //FORMATTING the DATE to a STRING
            // Here formatting the return date to display in format (dd/MM/yyyy)
            String formattedCurrentDate = dateFormat.format(currentDate);
            String formattedDueDate = dateFormat.format(dueDate);

//            System.out.println("Current Date: " + formattedCurrentDate);  // TEST
//            System.out.println("Return Date: " + formattedDueDate); // TEST

            // Create loan object // number of renews is set to 0 by default
            Loan new_loan = new Loan(barcode, userID, formattedCurrentDate, formattedDueDate, 0);

            // adds to loan array in main program
            loanArrayList.add(new_loan);
//             System.out.println(loans);  // TEST

            System.out.println("| Loan Successfully Added! |");

        }catch (NullPointerException e){
            System.out.println("Null Pointer Exception");
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }


    }

    /**
     * Method to renew an existing loan - return date is increased by 2weeks for Books and 1week for multimedia
     *
     * @param barcode - barcode supplied by user from keyboard
     *
     */
    private void renewLoan(String barcode){
        Item thisItem = getItem(barcode); // get item by barcode supplied
        Loan thisLoan = getLoan(barcode); // get Loan by barcode supplied

        try{
            // null checks for if the item or loan is not found from getItem or getLoan
            if (thisItem == null || thisLoan == null) {
                // Handle the case where either thisItem or thisLoan is null - error message is within getLoan and getItem
                return;
            }

            // Check that it has not exceeded max renews for the item
            if (thisLoan.getNumberOfRenews() >= thisItem.getMaxRenews()){
                System.out.println("Sorry You Cannot Renew This Loan. It has been renewed too many times. | No of Renews: "
                        + thisLoan.getNumberOfRenews() + " | Max Renews: " + thisItem.getMaxRenews() + " |");
            }
            else {
                // check whether it is book or multimedia
                String typeOfItem = thisItem.getType();
                String returnDate = thisLoan.getDueDate();  // get the loans return date

//                System.out.println("Return Date before: " +  returnDate); // TEST


                // parse the existing String date into a Date object
                Date formatReturnDate = dateFormat.parse(returnDate); // date format used for parsing

                // Use calendar instance to set to parsed date
                calendar.setTime(formatReturnDate);

                // Conditional for whether it is a Book or Multimedia Type
                // specifies to add to the week of the year field within the Calendar object
                if (typeOfItem.equals("Book")) {
                    calendar.add(Calendar.WEEK_OF_YEAR, 2); // increase by 2 weeks - BOOK
                }else {
                    calendar.add(Calendar.WEEK_OF_YEAR, 1); // increase by 1 week - Multimedia
                }

                // Get the due date after addition
                Date dueDate = calendar.getTime();
                String newReturnDate = dateFormat.format(dueDate); // format this date to a String again

                // Set new return date
                thisLoan.setDueDate(newReturnDate);
//                System.out.println("Return Date after: " +  newReturnDate); // TEST

                // NoOfRenews is increased by one
//                System.out.println("NoRenews before: " + thisLoan.getNumberOfRenews()); // TEST
                thisLoan.incrementRenews();
//                System.out.println("NoRenews after: " + thisLoan.getNumberOfRenews()); // TEST
                System.out.printf("| %s Loan Successfully Renewed! |%n", typeOfItem);  // %n used for newline
            }

        }catch (ParseException e){
            throw new RuntimeException(e);   // throwing exception if error parsing date
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    /**
     * returnLoan Method to record the return of an item on loan.
     *
     * @param barcode The barcode of the item being returned.
     */
    private void returnLoan(String barcode){
        Loan thisLoan = getLoan(barcode); // get Loan by barcode supplied

        // null checks for if loan is not found from getLoan
        if (thisLoan == null) {
            // Handle the case where either thisItem or thisLoan is null - error message is printed within getLoan
            return;
        }

        //  Items are always returned on or before their due date
        try{
            // check that it is before its due date.
            Date currentDate = new Date(); // get current date
            // parse the existing String date into a Date object
            Date dueDate = dateFormat.parse(thisLoan.getDueDate()); // date format used for parsing

            // conditional - if returned after the due date displays different message
            if (currentDate.after(dueDate)) {
                System.out.println("Loan of Item " + barcode + " has been RETURNED LATE." );
//                System.out.println("You cannot return this item past its due date.");
//                return;  // break from function and don't remove the loan if it has been returned after due date.
            } else{
                System.out.println("| Loan Successfully Returned! |");
            }

            // REMOVE loan from arrayList, therefore removed from csv when it is updated/overwritten
            loanArrayList.remove(thisLoan);

        }catch (ParseException e){
            throw new RuntimeException(e);   // throwing runtime Exception
        }
    }

    /**
     * viewLoans method - to view all items on loan currently.
     */
    private void viewLoans(){
        // First Check if there are no loans currently
        if (loanArrayList.isEmpty()){
            System.out.println("[There are currently NO LOANS on file]");
        }else{
            // prints out the loans array in a user-friendly format
            System.out.println("\n[VIEWING ITEMS ON LOAN CURRENTLY]");
            int loanNum = 1;   // loan count

            for(Loan loan : loanArrayList){
                System.out.println("Loan Number: " + loanNum);
                System.out.println(loan);
                System.out.println(getItem(loan.getBarcode()));  // display the item corresponding to the loan
                loanNum++;
            }
        }

    }

    /**
     * reportDetails Method to print details of loans to the screen.
     */
    private void reportDetails(){
        String libraryName = "OldTown Library";  // hard-coded library name

        // Get the total number of each type of loans held by the library
        // and the percentage of loan items having been renewed more than once.
        int bookCount = 0; // initialise book loan item count
        int mmCount = 0;  // initialise multimedia loan item count

        int totalNoLoans = loanArrayList.size();  // get the length of the loanArrayList
        int multipleRenewCount = 0; // initialise count variable

        // iterate through loan list and count item type and multipleRenewCount
        for (Loan loan : loanArrayList) {
            try {
                String itemType = getItem(loan.getBarcode()).getType();  // item type stored as a string
                if (itemType.equals("Book")) {
                    bookCount++;
                }else if(itemType.equals("Multimedia")) {
                    mmCount++; // increment multimedia count by 1
                }else {
                    System.err.println("Invalid Item Type In Loan Array"); //  print error messages to the error stream
                }

                int noRenews = loan.getNumberOfRenews();  // using numberOfRenews Getter method
                if (noRenews > 1) {
                    multipleRenewCount++;  // increment renew count
                }

            } catch (Exception e) {
                System.err.println("Error processing loan: " + e.getMessage());
            }

        }

        // calculate percentage of loans with multiple renews
        double percentage = (double) multipleRenewCount / totalNoLoans * 100;
        String formatPercentage = String.format("%.2f%%", percentage);  // String format the percentage to 2dp and add %

        // String to return to the user - using String.format and placeholders to display nicely
        String returnString = String.format("\n|| Library name: %s ||\nTotal Number of Book Loans: %d " +
                        "\nTotal Number of Multimedia Item Loans: %d " +
                        "\nPercentage of loan items renewed more than once: %s",
                libraryName, bookCount, mmCount, formatPercentage);

        System.out.println(returnString);
    }

    // Write Loan to CSV file method - Static

    /**
     * writeLoans Method to write loans to a CSV file.
     * It overwrites the existing loan CSV file to prevent duplicates.
     */
    private static void writeLoans(){

        //
        // NOTES: Have in main method so that it overwrites the old loan csv, because I will read in the old values
        // and then append any new loans to that same array, don't want to have any duplicates.
        //

        // filepath to read from
        String filepath = "src/files/LOANS.csv";

        try{
            // Using Buffered Writer to write to csv file
            BufferedWriter bw = new BufferedWriter(new FileWriter(filepath));

            String fileHeading = "Barcode,User ID,Issue Date ,Due Date,No Of Renews";
            // headings for loan csv
            bw.write(fileHeading);
            bw.newLine(); // new line at end of headings

            for (Loan loan : loanArrayList) {
                // write each loan item from ArrayList to the csv file
                bw.write(loan.getBarcode() + "," + loan.getUserID() + "," + loan.getIssueDate() + ","
                        + loan.getDueDate() + "," + loan.getNumberOfRenews());
                bw.newLine(); // new line at end of each loan item
            }
            bw.close();  // ensure to close the bufferedWriter

        }catch (FileNotFoundException e) {
        // Handling file not found exception
        System.err.println("The specified file was not found: " + e.getMessage());
        System.err.println("No Changes have been saved.");

        }catch (IOException e) {
            // Handling IO exception, and logging an error message
            System.err.println("An IO error occurred: " + e.getMessage());
            System.err.println("No Changes have been saved.");

        }catch (Exception e){
            System.out.println("An Unexpected Error Occurred");// end try
            System.out.println(e.getMessage());
        }// end of try catch

    }

    /**
     * searchLoan method - Searches for a loan item by its barcode using the getItem method.
     * If the item is found, its details are displayed.
     *
     * @param barcode The barcode of the item to search for.
     */
    private void searchLoan(String barcode){
        // Attempt to retrieve the item by barcode
        Loan loan = getLoan(barcode);  // call getItem to implement search for the item object.

        if (loan != null) {
            // If the item is found, display its details
            System.out.println("\nLoan Item Found!\nHere are the details: " + loan);
        }
        // if loan item is not found, the getItem method displays the error message.
    }


    // ------------------------------------------------------------------------------
    /**
     * getItem method - retrieves and returns an item from the itemArrayList based on the provided barcode.
     *
     * @param barcode The barcode of the item to retrieve.
     * @return The item with the specified barcode, or null if not found.
     */
    public static Item getItem(String barcode){
        // Sought book details to be displayed if present.

        // for loop to iterate through record ArrayList - using enhanced for loop
        for (Item item : itemArrayList) {           // itemArrayList defined at top of class

            // Getting item at index i and the n the barcode of that item
            String currentBarcode = item.getBarcode();

            // Check if barcode matches
            if (barcode.equals(currentBarcode)) {

                // Display the matched items details
                return item; // return the item
            }
        }

        // else display that item could not be found
        System.out.println("Sorry, item with barcode '" + barcode + "' could not be found.");


        return null;  //
    }

    /**
     * getLoan method - retrieves and returns a loan from the loanArrayList based on the provided barcode.
     *
     * @param barcode The barcode of the loan to retrieve.
     * @return The loan with the specified barcode, or null if not found.
     */
    public static Loan getLoan(String barcode){
        // returns the loan if found

        // for loop to iterate through LoanArrayList
        for (Loan loan : loanArrayList) {

            // Getting item at index i and the n the barcode of that item
            String currentBarcode = loan.getBarcode();

            // Check if barcode matches
            if (barcode.equals(currentBarcode)) {

                // Display the matched items details

                return loan; // return the item
            }
        }

        // else display that the loan could not be found
        System.out.println("Sorry, loan with barcode " + barcode + " could not be found.");


        return null; // is this bad practice?
    }

    // ------------------------------------------------------------------------------
    /**
     * isAlreadyLoaned method - Checks if an item is already loaned based on the provided barcode.
     *
     * @param barcode The barcode of the item to check.
     * @return True if the item is already loaned, false otherwise.
     */
    private boolean isAlreadyLoaned(String barcode){

        for(Loan loan : loanArrayList){
            // Check if the barcode we are attempting to loan is already being loaned
            if (loan.getBarcode().equals(barcode)) {
                return true;
            }
        }
        return false; // If the book is not loaned to anyone

    }

    // ------------------------------------------------------------------------------





}
