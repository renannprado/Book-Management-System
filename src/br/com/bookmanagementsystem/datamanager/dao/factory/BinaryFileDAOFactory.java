package br.com.bookmanagementsystem.datamanager.dao.factory;

import br.com.bookmanagementsystem.datamanager.dao.AbstractAuthorDAO;
import br.com.bookmanagementsystem.datamanager.dao.AbstractBookDAO;
import br.com.bookmanagementsystem.datamanager.dao.BinaryFileAuthorDAO;
import br.com.bookmanagementsystem.datamanager.dao.BinaryFileBookDAO;
import br.com.bookmanagementsystem.model.Author;
import br.com.bookmanagementsystem.model.Book;
import br.com.bookmanagementsystem.util.ApplicationConstants;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * File structure:
 * -- The number of entities
 * ---- The entities
 *
 * @author renann
 */
public class BinaryFileDAOFactory extends DAOFactory
{
    /**
     * The order of the entities in the .dat file
     */
    public final static ArrayList<Class> FileReadEntityOrder = new ArrayList<Class>()
    {
        
        {
            add(Author.class);
            add(Book.class);
        }
    };

    /**
     *
     */
    public BinaryFileDAOFactory()
    {
        createFile(ApplicationConstants.FULL_FILE_PATH);
    }

    @Override
    public AbstractBookDAO getBookDAO()
    {
        return new BinaryFileBookDAO(ApplicationConstants.BASE_PATH, ApplicationConstants.FILE_NAME);
    }

    @Override
    public AbstractAuthorDAO getAuthorDAO()
    {
        return new BinaryFileAuthorDAO(ApplicationConstants.BASE_PATH, ApplicationConstants.FILE_NAME);
    }

    /**
     * This methods creates and initializes the .dat file.
     * It will only try to create a file if the file doesn't exists already
     *
     * @param fullFilePath
     */
    private void createFile(final String fullFilePath)
    {
        File f = new File(fullFilePath);

        //doesn't try to create a new file if it already exists
        if (f.exists())
        {
            return;
        }

        try (BufferedOutputStream buffOutStream = new BufferedOutputStream(new FileOutputStream(fullFilePath));
             ObjectOutputStream objectOutStream = new ObjectOutputStream(buffOutStream))
        {
            ArrayList<ArrayList<?>> initArrayForFile = new ArrayList<>();

            //init data for the .dat file
            initArrayForFile.add(new ArrayList<Author>());
            initArrayForFile.add(new ArrayList<Book>());

            //write the init data into the file
            objectOutStream.writeObject(initArrayForFile);
        }
        catch (Exception ex)
        {
            Logger.getLogger(BinaryFileDAOFactory.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            System.err.println("It was not possible to create the .dat file. Exiting...");
            System.exit(-1);
        }
    }
}
