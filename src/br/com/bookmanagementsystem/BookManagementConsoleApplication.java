package br.com.bookmanagementsystem;

import br.com.bookmanagementsystem.datamanager.dao.AbstractAuthorDAO;
import br.com.bookmanagementsystem.datamanager.dao.AbstractBookDAO;
import br.com.bookmanagementsystem.datamanager.dao.factory.DAOFactory;
import br.com.bookmanagementsystem.datamanager.dao.factory.DAOFactory.FactoryType;
import br.com.bookmanagementsystem.model.Author;
import br.com.bookmanagementsystem.model.Book;
import br.com.bookmanagementsystem.util.ApplicationConstants;
import br.com.bookmanagementsystem.util.IdentitySequenceGenerator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class handles the view of the console application
 *
 * @author renann
 */
public class BookManagementConsoleApplication
{
    /**
     * Please use this static member to configure which DAO you want to use
     */
    private static FactoryType TypeOfFactory = FactoryType.BINARY_FILE;
    /**
     * The maximum number of entities that will be shown per page
     */
    private static int maxEntitiesPerPage = 10;

    /**
     * @param args if you want to change the path of the .dat file, pass as an
     *             argument
     */
    public static void main(String[] args)
    {
        if (args.length > 0 && FactoryType.BINARY_FILE == TypeOfFactory)
        {
            ApplicationConstants.setPathConstants(args[0]);
        }
        else if (FactoryType.BINARY_FILE == TypeOfFactory)
        {
            Logger.getLogger(BookManagementConsoleApplication.class.getName()).log(Level.INFO, "No base path was passed to the app, using the default.");
        }

        if (!BookManagementConsoleApplication.applicationBootstrap())
        {
            System.exit(-1);
        }

        //starts the views of the application
        try (Scanner scan = new Scanner(System.in))
        {
            showMenu(scan);
        }
    }

    /**
     * Bootstrap tasks:
     * 1 - "Calculate" and set the last ID for each class entity (Author and
     * Book)
     * It could read configuration (.properties) file(s), etc
     *
     * @return <code>true</code> if the bootstrap was done
     * successfully; <code>false</code> otherwise
     */
    public static boolean applicationBootstrap()
    {
        IdentitySequenceGenerator.identityGenList = new HashMap<>();

        AbstractAuthorDAO authorDAO = DAOFactory.getDAOFactory(TypeOfFactory).getAuthorDAO();
        AbstractBookDAO bookDAO = DAOFactory.getDAOFactory(TypeOfFactory).getBookDAO();
        Integer lastAuthorID = new Integer(0);
        Integer lastBookID = new Integer(0);

        for (Book b : bookDAO.findAll())
        {
            if (b.getID() > lastBookID)
            {
                lastBookID = b.getID();
            }
        }

        for (Author a : authorDAO.findAll())
        {
            if (a.getID() > lastAuthorID)
            {
                lastAuthorID = a.getID();
            }
        }

        //after calculating the last ID for each entity, set into the identity generator
        IdentitySequenceGenerator.identityGenList.put(Author.class, new AtomicInteger(lastAuthorID + 1));
        IdentitySequenceGenerator.identityGenList.put(Book.class, new AtomicInteger(lastBookID + 1));

        return true;
    }

    /**
     * This method handles the Create New Author form
     * @param scan
     */
    public static void createNewAuthor(Scanner scan)
    {

        Author author = new Author();
        System.out.println("--- New Author Form ---");
        System.out.print("First Name: ");
        author.setFirstName(scan.nextLine());
        System.out.print("Last Name: ");
        author.setLastName(scan.nextLine());

        boolean continueLoop = true;
        do
        {
            System.out.println("Are you sure that you want to save it? (Y/N)");
            String answer = scan.nextLine();

            switch (answer.toUpperCase())
            {
                case "Y":
                {
                    AbstractAuthorDAO authorDAO = DAOFactory.getDAOFactory(TypeOfFactory).getAuthorDAO();

                    authorDAO.save(author);

                    continueLoop = false;
                    break;
                }
                case "N":
                {
                    System.out.println("Author wasn't saved.");

                    continueLoop = false;
                    break;
                }
            }
        }
        while (continueLoop);

    }

    /**
     * This method handles the Create New Book form
     * @param scan the scanner
     */
    public static void createNewBook(Scanner scan)
    {
        AbstractAuthorDAO authorDAO = DAOFactory.getDAOFactory(TypeOfFactory).getAuthorDAO();

        if (authorDAO.isEmpty())
        {
            System.out.println("There are no Authors registered, you can't create a new book.");
            return;
        }

        Book book = new Book();
        boolean continueLoop = true;

        System.out.println("--- New Book Form ---");
        System.out.print("Book Title: ");
        book.setTitle(scan.nextLine());
        System.out.print("Edition: ");
        book.setEdition(scan.nextLine());
        System.out.print("Language: ");
        book.setLanguage(scan.nextLine());

        do
        {
            try
            {
                System.out.print("Number of pages (number): ");
                Integer numberOfPages = Integer.parseInt(scan.nextLine());

                book.setNumberOfPages(numberOfPages);
                continueLoop = false;

            }
            catch (NumberFormatException e)
            {
                // simple swallow the exception and let the user input the number of pages again
            }
        }
        while (continueLoop);

        do
        {
            System.out.println("Enter a comma separated Author ID list ");
            System.out.print("(if you choose at least one existing ID, not warning will be given): ");
            String[] authorIDList = scan.nextLine().trim().split(",");
            Integer[] authorIDs = new Integer[authorIDList.length];

            for (int i = 0; i < authorIDList.length; i++)
            {
                try
                {
                    authorIDs[i] = Integer.parseInt(authorIDList[i]);
                }
                catch (NumberFormatException e)
                {
                    System.out.println("ID \"" + authorIDList[i] + "\" is not valid.");
                }
            }

            if (authorIDs.length > 0)
            {
                List<Author> authorList = authorDAO.findEntitiesByIDs(authorIDs);

                if (!authorList.isEmpty())
                {
                    for (Author a : authorList)
                    {
                        book.getAuthorIDList().add(a.getID());
                    }

                    break;
                }
                else
                {
                    System.out.println("No authors were found, please input the author IDs again.");
                }
            }

        }
        while (true);

        continueLoop = true;

        do
        {
            System.out.println("Are you sure that you want to save it? (Y/N)");
            String answer = scan.nextLine();

            switch (answer.toUpperCase())
            {
                case "Y":
                {
                    AbstractBookDAO bookDAO = DAOFactory.getDAOFactory(TypeOfFactory).getBookDAO();

                    bookDAO.save(book);

                    continueLoop = false;
                    break;
                }
                case "N":
                {
                    System.out.println("Book wasn't saved.");

                    continueLoop = false;
                    break;
                }
            }
        }
        while (continueLoop);
    }

    /**
     * This method receives the input search term from the user and DAO handles
     * the search for the Book
     *
     * @param scan the scanner
     */
    public static void searchBookByTitle(Scanner scan)
    {
        AbstractBookDAO bookDAO = DAOFactory.getDAOFactory(TypeOfFactory).getBookDAO();

        if (bookDAO.isEmpty())
        {
            System.out.println("There are no books registered.");
            return;
        }

        String searchTerm = "";
        System.out.println("--- Search Book Form ---");
        System.out.print("Enter search term: ");
        searchTerm = scan.nextLine();

        List<Book> searchResults = bookDAO.searchBookByTitle(searchTerm);

        if (searchResults.isEmpty())
        {
            System.out.println("Your search returned no results.");
            return;
        }

        showBookList(searchResults, scan);
    }

    /**
     * This method receives the input search term from the user and DAO handles
     * the search for the Author
     *
     * @param scan the scanner
     */
    public static void searchAuthorByName(Scanner scan)
    {
        AbstractAuthorDAO authorDAO = DAOFactory.getDAOFactory(TypeOfFactory).getAuthorDAO();

        if (authorDAO.isEmpty())
        {
            System.out.println("There are no authors registered.");
            return;
        }

        String searchTerm = "";
        System.out.println("--- Search Author Form ---");
        System.out.print("Enter search term: ");
        searchTerm = scan.nextLine();

        List<Author> searchResults = authorDAO.searchAuthorByName(searchTerm);

        if (searchResults.isEmpty())
        {
            System.out.println("Your search returned no results.");
            return;
        }

        showAuthorList(searchResults, scan);
    }

    /**
     * This method shows the
     * <code>bookList</code>
     *
     * @param bookList the book list to be shown
     * @param scan     the scanner
     */
    public static void showBookList(List<Book> bookList, Scanner scan)
    {
        int currentPage = 0;

        AbstractAuthorDAO authorDAO = DAOFactory.getDAOFactory(TypeOfFactory).getAuthorDAO();

        do
        {
            System.out.println("--- List of Books ---");
            System.out.println("--- Current Page: " + (currentPage + 1) + " ---");

            for (int i = 0; i < maxEntitiesPerPage; i++)
            {
                int realIndex = i + currentPage;

                if (realIndex >= bookList.size())
                {
                    break;
                }

                Book b = bookList.get(realIndex);

                System.out.println("--- Book " + (i + 1) + " ---");
                System.out.println(b.toString());

                Integer[] ids = b.getAuthorIDList().toArray(new Integer[b.getAuthorIDList().size()]);
                List<Author> authorList = authorDAO.findEntitiesByIDs(ids);

                System.out.println("--- Authors of Book " + (i + 1) + " ---");
                for (Author a : authorList)
                {
                    System.out.println(a.toString() + "\n");
                }
            }

            if (bookList.size() > maxEntitiesPerPage)
            {
                System.out.println("There are more results to be shown.");
                System.out.println("B: previous page; N: next page");
                String option = scan.nextLine();

                switch (option.toUpperCase())
                {
                    case "B":
                    {
                        if (currentPage >= 0)
                        {
                            currentPage--;
                            break;
                        }
                        else
                        {
                            System.out.println("There's no previous page.");
                            break;
                        }
                    }
                    case "N":
                    {
                        if (currentPage < maxEntitiesPerPage)
                        {
                            currentPage++;
                            break;
                        }
                        else
                        {
                            System.out.println("There's no next page.");
                            break;
                        }
                    }
                }
            }
            else
            {
                break;
            }
        }
        while (true);
    }

    /**
     * This method shows the
     * <code>authorList</code>
     *
     * @param authorList the author list to be shown
     * @param scan       the scanner
     */
    public static void showAuthorList(List<Author> authorList, Scanner scan)
    {
        int currentPage = 0;

        do
        {
            System.out.println("--- List of Authors ---");
            System.out.println("--- Current Page: " + (currentPage + 1) + " ---");

            for (int i = 0; i < maxEntitiesPerPage; i++)
            {
                int realIndex = i + currentPage;

                if (realIndex >= authorList.size())
                {
                    break;
                }

                Author a = authorList.get(realIndex);

                System.out.println("--- Author " + (i + 1) + " ---");
                System.out.println(a.toString());
            }

            if (authorList.size() > maxEntitiesPerPage)
            {
                System.out.println("There are more results to be shown.");
                System.out.println("B: previous page; N: next page");
                String option = scan.nextLine();

                switch (option.toUpperCase())
                {
                    case "B":
                    {
                        if (currentPage >= 0)
                        {
                            currentPage--;
                            break;
                        }
                        else
                        {
                            System.out.println("There's no previous page.");
                            break;
                        }
                    }
                    case "N":
                    {
                        if (currentPage < maxEntitiesPerPage)
                        {
                            currentPage++;
                            break;
                        }
                        else
                        {
                            System.out.println("There's no next page.");
                            break;
                        }
                    }
                }
            }
            else
            {
                break;
            }
        }
        while (true);
    }

    /**
     * This method shows all the authors that are in the data source
     *
     * @param scan the scanner
     */
    public static void findAllAuthors(Scanner scan)
    {
        AbstractAuthorDAO authorDAO = DAOFactory.getDAOFactory(TypeOfFactory).getAuthorDAO();

        List<Author> lstAuthors = authorDAO.findAll();

        if (!lstAuthors.isEmpty())
        {
            showAuthorList(lstAuthors, scan);
        }
        else
        {
            System.out.println("There are no authors to be shown.");
        }
    }

    /**
     * This method shows all the books that are in the data source
     *
     * @param scan the scanner
     */
    public static void findAllBooks(Scanner scan)
    {
        AbstractBookDAO bookDAO = DAOFactory.getDAOFactory(TypeOfFactory).getBookDAO();

        List<Book> lstBooks = bookDAO.findAll();

        if (!lstBooks.isEmpty())
        {
            showBookList(lstBooks, scan);
        }
        else
        {
            System.out.println("There are no books to be shown.");
        }
    }

    /**
     * This method handles the main menu of the application
     *
     * @param scan
     */
    public static void showMenu(Scanner scan)
    {
        boolean continueLoop = true;

        do
        {
            System.out.println("--- Main Menu ---");
            System.out.println("1 - Create a new Author");
            System.out.println("2 - Create a new Book");
            System.out.println("3 - Search author by name");
            System.out.println("4 - Search book by title");
            System.out.println("5 - Show all authors");
            System.out.println("6 - Show all books and its respectives authors");
            System.out.println("0 - Exit");
            String option = scan.nextLine();

            switch (option)
            {
                case "1":
                {
                    createNewAuthor(scan);
                    break;
                }
                case "2":
                {
                    createNewBook(scan);
                    break;
                }
                case "3":
                {
                    searchAuthorByName(scan);
                    break;
                }
                case "4":
                {
                    searchBookByTitle(scan);
                    break;
                }
                case "5":
                {
                    findAllAuthors(scan);
                    break;
                }
                case "6":
                {
                    findAllBooks(scan);
                    break;
                }
                case "0":
                {
                    continueLoop = false;
                    break;
                }
                default:
                {
                    break;
                }
            }

        }
        while (continueLoop);
    }
}