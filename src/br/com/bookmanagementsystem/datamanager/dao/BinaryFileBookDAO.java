/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bookmanagementsystem.datamanager.dao;

import br.com.bookmanagementsystem.datamanager.dao.factory.BinaryFileDAOFactory;
import br.com.bookmanagementsystem.model.Book;
import br.com.bookmanagementsystem.util.ApplicationConstants;
import br.com.bookmanagementsystem.util.IdentitySequenceGenerator;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class handles the BinaryFile persistence for entity
 * <code>Book</code>.
 * I know that the strategy that I'm using here is not good with bigger files,
 * but I wasn't focused on that
 *
 * @author renann
 */
public class BinaryFileBookDAO extends AbstractBookDAO
{
    /**
     * Base directory for the files
     */
    private String basePath = null;
    /**
     * Name of the .dat file
     */
    private String fileName = null;
    /**
     * basePath + fileName
     */
    private String fullFilePath = null;
    /**
     * Entity index based on
     * <code>BinaryFileDAOFactory#FileReadEntityOrder</code>
     *
     * @see BinaryFileDAOFactory#FileReadEntityOrder
     */
    private int entityIndex = -1;
    /**
     * Backup path + backup file name
     */
    private String backupFilePath = null;
    /**
     * Holds the data from the file
     */
    private ArrayList<ArrayList<?>> allDataSource = null;
    /**
     * Cached list of books
     */
    private ArrayList<Book> cachedBookList = null;

    public BinaryFileBookDAO(final String basePath, final String fileName)
    {
        this.basePath = basePath;
        this.fileName = fileName;
        this.fullFilePath = basePath + fileName;
        this.backupFilePath = basePath + ApplicationConstants.BACKUP_FILE_NAME;

        entityIndex = BinaryFileDAOFactory.FileReadEntityOrder.indexOf(Book.class);

        loadEntitiesIntoCache();
    }

    @Override
    public List<Book> searchBookByTitle(String name)
    {
        ArrayList<Book> resultBookList = new ArrayList<>();

        for (Book b : cachedBookList)
        {
            if (b.getTitle().toUpperCase().contains(name.toUpperCase()))
            {
                resultBookList.add(b);
            }
        }

        return resultBookList;
    }

    @Override
    public void save(Book newEntity)
    {
        newEntity.setID(IdentitySequenceGenerator.getNextID(Book.class));

        cachedBookList.add(newEntity);

        writeEntitiesToFile();
    }

    @Override
    public Book findEntityByID(Integer entityID)
    {
        for (Book b : cachedBookList)
        {
            if (b.getID().equals(entityID))
            {
                return b;
            }
        }

        return null;
    }

    @Override
    public List<Book> findAll()
    {
        return cachedBookList;
    }

    @Override
    public List<Book> findEntitiesByIDs(Integer[] entityIDList)
    {
        ArrayList<Book> bookList = new ArrayList<>();

        for (int i = 0; i < entityIDList.length; i++)
        {
            Book b = findEntityByID(entityIDList[i]);

            if (b != null)
            {
                bookList.add(b);
            }
        }

        return bookList;
    }

    @Override
    public boolean isEmpty()
    {
        return cachedBookList.isEmpty();
    }

    private void loadEntitiesIntoCache()
    {
        try (FileInputStream fileIn = new FileInputStream(fullFilePath);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn))
        {
            allDataSource = (ArrayList<ArrayList<?>>) objectIn.readObject();

            cachedBookList = (ArrayList<Book>) allDataSource.get(entityIndex);
        }
        catch (IOException | ClassNotFoundException ex)
        {
            Logger.getLogger(BinaryFileBookDAO.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            System.err.println("It was not possible to load the data into cache. Exiting...");
            System.exit(-1);
        }
    }

    /**
     * This method writes the whole .dat file
     * I know that here there is code duplication here, but I didn't want this
     * method
     * to be accessed outside of the BinaryFileDAOs
     */
    private void writeEntitiesToFile()
    {
        File bkpFile = new File(backupFilePath);
        File allDataFile = new File(fullFilePath);

        if (bkpFile.exists()) //if the file exists
        {
            bkpFile.delete(); //to avoid issues, the backup file is deleted if it already exists
        }

        if (allDataFile.exists()) //at this point the .dat file should exists, but it's just to make sure :)
        {
            allDataFile.renameTo(bkpFile); //rename the .dat to a .bkp.dat file before writing the .dat file again
        }

        try (BufferedOutputStream buffOutStream = new BufferedOutputStream(new FileOutputStream(fullFilePath));
             ObjectOutputStream objectOutStream = new ObjectOutputStream(buffOutStream))
        {
            //write the data into the .dat file
            objectOutStream.writeObject(allDataSource);

            //if everything went well, the backup file will be deleted
            bkpFile.delete();
        }
        catch (Exception ex)
        {
            //if the creation process of the file fails for any reason, the backup file must be restaured
            if (bkpFile.exists()) //if the backup file is there
            {
                bkpFile.renameTo(allDataFile); //we rename it to be again the .dat file
            }

            Logger.getLogger(BinaryFileBookDAO.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            Logger.getLogger(BinaryFileBookDAO.class.getName()).log(Level.WARNING, "It was not possible to create the new .dat file, recovering the backup file", ex);
        }
    }
}
