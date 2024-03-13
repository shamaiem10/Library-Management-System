import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

//create a Book class
class Book {
    long bookID;
    String title;
    String author;
    String genre;
    boolean availabilityStatus;

    // constructor to initialize default values
    public Book() {
        bookID = 0;
        title = "Null";
        author = "Null";
        genre = "Null";
        availabilityStatus = false;
    }
}

// Class representing User information
class UserInfo {
    long userID;
    String name;
    String contactInformation;
    String[] borrowedBooks;

    // Constructor to initialize default values
    public UserInfo() {
        userID = 0;
        name = "Null";
        contactInformation = "Null";
    }
}

// Main class for Library Management System
public class LibraryManagementSystem {
    static ArrayList<Book> booksList = new ArrayList<>(); // List to store books
    static ArrayList<UserInfo> usersList = new ArrayList<>(); // List to store users

    public static void main(String[] args) {
        File myFile = new File("LibraryManagementSystemfile.txt");
        try {
            myFile.createNewFile(); // Creating a new file if not exists
        } catch (IOException e) {
            System.out.println("Unable to create file");
            e.printStackTrace();
        }

        // Load data from file into booksList
        loadDataFromFile();
        // Displaying options to the user
        Scanner sc = new Scanner(System.in);
        System.out.println("LIBRARY MANAGEMENT SYSTEM");
        System.out.println("From the options below please choose a number according to the task you want to perform:");
        System.out.println("1. ADDING BOOKS");
        System.out.println("2. ADDING USERS");
        System.out.println("3. DISPLAY BOOKS");
        System.out.println("4. CHECK OUT BOOK");
        System.out.println("5. RETURN BOOK");
        System.out.println("6. SEARCH BOOK");
        System.out.print("Enter A number:");

        int input = sc.nextInt();

        // Performing tasks based on user input
        if (sc.hasNextInt()) {
            if (input == 1 || input == 2 || input == 3 || input == 4 || input == 5 || input == 6) {
                if (input == 1) {
                    addNewBooks();
                } else if (input == 2) {
                    addNewUser();
                } else if (input == 3) {
                    displayBooks();
                } else if (input == 4) {
                    checkingOutBooks();
                } else if (input == 5) {
                    returningBooks();
                } else if (input == 6) {
                    bookSearch();
                } else {
                    System.out.println("Please enter a valid number between 1-6.");
                }
            } else {
                System.out.println("PLZ ENTER NUMBERS ONLY");
            }
        } else {
            System.out.println("Enter numbers plz");
        }

    }

    public static void loadDataFromFile() {
        try {

            File myFile = new File("LibraryManagementSystemfile.txt");
            Scanner scanner = new Scanner(myFile);

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String title = parts[0];
                    String author = parts[1];
                    long bookId = Long.parseLong(parts[2]);
                    String genre = parts[3];
                    boolean availabilityStatus = Boolean.parseBoolean(parts[4]);

                    // Creating a new book object and adding it to the booksList
                    Book book = new Book();
                    book.title = title;
                    book.author = author;
                    book.bookID = bookId;
                    book.genre = genre;
                    book.availabilityStatus = availabilityStatus;

                    booksList.add(book);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: LibraryManagementSystemfile.txt");
            e.printStackTrace();
        }
    }

    // Method to add new books
    public static void addNewBooks() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the details of the book you want to add.");
        System.out.print("TITLE:");
        String title = sc.nextLine();
        System.out.print("AUTHOR:");
        String author = sc.nextLine();
        System.out.print("Book ID:");

        long bookId;
        if (sc.hasNextLong()) {
            bookId = sc.nextLong();
            sc.nextLine();
        } else {
            System.out.println("Invalid input for Book ID. Please enter a valid number.");
            return;
        }

        System.out.print("Genre:");
        String genre = sc.nextLine();
        System.out.print("Availability Status (true/false)...true for available.:");
        // Read availability status as string and then parse it into a boolean
        boolean availabilityStatus;
        if (sc.hasNextBoolean()) {
            availabilityStatus = sc.nextBoolean();
        } else {
            System.out.println("Invalid input for Availability Status. Please enter 'true' or 'false'.");
            return;
        }

        // Creating a new book object for the entered book
        Book newBook = new Book();
        newBook.title = title;
        newBook.author = author;
        newBook.bookID = bookId;
        newBook.genre = genre;
        newBook.availabilityStatus = availabilityStatus;

        // Adding the new book to the booksList
        booksList.add(newBook);

        try {
            FileWriter fileWriter = new FileWriter("LibraryManagementSystemfile.txt", false); // overwrite mode
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter fileWriter = new FileWriter("LibraryManagementSystemfile.txt", true); // append mode
            for (Book b : booksList) {
                fileWriter.write(
                        b.title + "," + b.author + "," + b.bookID + "," + b.genre + "," + b.availabilityStatus + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Book added successfully.");
    }

    // Method to add a new user
    public static void addNewUser() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the details of the user you want to add.");

        // User ID input
        long userId;
        while (true) {
            System.out.print("USER ID:");
            if (sc.hasNextLong()) {
                userId = sc.nextLong();
                sc.nextLine(); // Consume newline character left by nextLong()
                break;
            } else {
                System.out.println("Invalid input for User ID. Please enter a valid number.");
                sc.nextLine(); // Consume the invalid input
            }
        }

        // Name input
        String name;
        while (true) {
            System.out.print("NAME:");
            name = sc.nextLine();
            if (name.matches("[a-zA-Z ]+")) {
                break;
            } else {
                System.out.println("Invalid input for Name. Please enter alphabetic characters only.");
            }
        }

        // Contact information input
        String contactNumber;
        while (true) {
            System.out.print("CONTACT NUMBER:");
            contactNumber = sc.nextLine();
            if (contactNumber.matches("\\d+")) {
                break;
            } else {
                System.out.println("Invalid input for Contact Number. Please enter digits only.");
            }
        }

        // Borrowed books input
        System.out.print("BORROWED BOOKS (comma-separated list):");
        String borrowedBooksInput = sc.nextLine(); // Read input as a string
        String[] borrowedBooks = borrowedBooksInput.split(",");

        // Create a user object
        UserInfo user = new UserInfo();
        user.userID = userId;
        user.name = name;
        user.contactInformation = contactNumber;
        user.borrowedBooks = borrowedBooks;

        usersList.add(user);

        // Writes to the file
        try (FileWriter fileWriter = new FileWriter("LibraryManagementSystemfile.txt", true)) {
            fileWriter.write(userId + "," + name + "," + contactNumber + ",");
            for (String book : borrowedBooks) {
                fileWriter.write(book + ",");
            }
            fileWriter.write("\n");
            System.out.println("User added successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while adding the user.");
            e.printStackTrace();
        }
    }

    // Create a display book method
    public static void displayBooks() {
        File myFile = new File("LibraryManagementSystemfile.txt");
        try {

            Scanner scanner = new Scanner(myFile);
            if (!scanner.hasNextLine()) {
                System.out.println("No Books Available At the Moment:(");
            } else {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(",");
                    if (parts.length >= 5) {
                        String title = parts[0];
                        String author = parts[1];
                        long bookId = Long.parseLong(parts[2]);
                        String genre = parts[3];
                        boolean availabilityStatus = Boolean.parseBoolean(parts[4]);

                        // Display book details
                        System.out.println("BOOK TITLE: " + title);
                        System.out.println("AUTHOR: " + author);
                        System.out.println("GENRE: " + genre);
                        if (availabilityStatus == true) {
                            System.out.println("AVAILABILITY STATUS: Free");
                        } else if (availabilityStatus == false) {
                            System.out.println("AVAILABILITY STATUS: Borrowed");
                        } else {
                            System.out.println("Invalid Status Of Book Entered...");
                        }

                        System.out.println();
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: LibraryManagementSystemfile.txt");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error occurred while clearing file contents.");
            e.printStackTrace();
        }
    }

    public static void checkingOutBooks() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the details of the book you want to check out.");

        // Input for book title
        System.out.print("TITLE:");
        String title = sc.nextLine().toLowerCase().trim(); // Convert input to lowercase and remove leading/trailing
                                                           // whitespace

        // Input for book author
        System.out.print("AUTHOR:");
        String author = sc.nextLine().toLowerCase().trim(); // Convert input to lowercase and remove leading/trailing
                                                            // whitespace

        // Input for book ID with validation
        long bookId;
        System.out.print("BOOK ID:");
        while (true) {
            if (sc.hasNextLong()) {
                bookId = sc.nextLong();
                sc.nextLine(); // Consume newline character left by nextLong()
                break;
            } else {
                System.out.println("Invalid input for Book ID. Please enter digits only.");
                sc.nextLine(); // Consume the invalid input
                System.out.print("BOOK ID:");
            }
        }

        // Iterate through the list of books to find the book to check out
        boolean bookFound = false;
        for (Book b : booksList) {
            if (b.title.equalsIgnoreCase(title) && b.author.equalsIgnoreCase(author) && b.bookID == bookId) {
                bookFound = true;
                if (b.availabilityStatus) {
                    // Mark the book as checked out
                    b.availabilityStatus = false;
                    System.out.println("Book checked out successfully.");
                    // Update file
                    updateFile();
                } else {
                    System.out.println("Sorry, the book is already checked out.");
                }
                break;
            }
        }

        if (!bookFound) {
            System.out.println("No book found with the provided details.");
        }
    }

    public static void returningBooks() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the details of the book you want to return.");
        System.out.print("TITLE:");
        String title = sc.nextLine().toLowerCase().trim(); // Convert input to lowercase and remove leading/trailing
                                                           // whitespace
        System.out.print("AUTHOR:");
        String author = sc.nextLine().toLowerCase().trim(); // Convert input to lowercase and remove leading/trailing
                                                            // whitespace
        System.out.print("Book ID:");
        long bookId;
        while (true) {
            if (sc.hasNextLong()) {
                bookId = sc.nextLong();
                sc.nextLine(); // Consume newline character left by nextLong()
                break;
            } else {
                System.out.println("Invalid input for Book ID. Please enter digits only.");
                sc.nextLine(); // Consume the invalid input
            }
        }

        // Iterate through the list of books to find the book to return
        boolean bookFound = false;
        for (Book b : booksList) {
            if (b.title.toLowerCase().equals(title) && b.author.toLowerCase().equals(author) && b.bookID == bookId) {
                bookFound = true;
                b.availabilityStatus = true;
                // Update file
                updateFile();
                System.out.println("Book returned successfully.");
                break;
            }
        }

        if (!bookFound) {
            System.out.println("No book found with this data.");
        }
    }

    public static void bookSearch() {
        Scanner sc = new Scanner(System.in);
        System.out.println("PLZ CHOOSE SEARCH METHOD");
        System.out.println("1. Search by BOOK TITLE");
        System.out.println("2. Search by BOOK ID");
        System.out.print("Enter option number:");
        int searchMethod = sc.nextInt();
        sc.nextLine(); // Consume newline character left by nextInt()

        boolean bookFound = false; // Flag to track if the book is found

        if (searchMethod == 1) {
            System.out.print("Enter Book Title:");
            String title = sc.nextLine().toLowerCase(); // Convert input to lowercase
            for (Book b : booksList) {
                if (b.title.toLowerCase().equals(title)) { // Compare lowercase titles
                    bookFound = true;
                    System.out.println("The book details are as follows....");
                    System.out.println("AUTHOR:" + b.author);
                    System.out.println("GENRE:" + b.genre);
                    System.out.println("Availability Status:" + b.availabilityStatus);
                    break; // Exit the loop once the book is found
                }
            }
            if (!bookFound) {
                System.out.println("No book found with this title.");
            }
        } else if (searchMethod == 2) {
            System.out.print("Enter Book ID:");
            long bookId = sc.nextLong();
            for (Book b : booksList) {
                if (b.bookID == bookId) {
                    bookFound = true;
                    System.out.println("The book details are as follows....");
                    System.out.println("TITLE:" + b.title);
                    System.out.println("AUTHOR:" + b.author);
                    System.out.println("GENRE:" + b.genre);
                    System.out.println("Availability Status:" + b.availabilityStatus);
                    break; // Exit the loop once the book is found
                }
            }
            if (!bookFound) {
                System.out.println("No book found with this ID.");
            }
        } else {
            System.out.println("Please enter a valid search method (1 or 2).");
        }
    }

    public static void updateFile() {
        try (FileWriter fileWriter = new FileWriter("LibraryManagementSystemfile.txt", false)) { // overwrite mode
            for (Book b : booksList) {
                fileWriter.write(
                        b.title + "," + b.author + "," + b.bookID + "," + b.genre + "," + b.availabilityStatus + "\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while updating the file.");
            e.printStackTrace();
        }
    }
}
