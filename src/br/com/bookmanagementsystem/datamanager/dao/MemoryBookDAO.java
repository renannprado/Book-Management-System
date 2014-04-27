package br.com.bookmanagementsystem.datamanager.dao;

import br.com.bookmanagementsystem.model.Book;
import br.com.bookmanagementsystem.util.BookManagerUtils;
import br.com.bookmanagementsystem.util.IdentitySequenceGenerator;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * This class handles the Memory persistence for entity
 * <code>Book</code>.
 *
 * @author renann
 */
public class MemoryBookDAO extends AbstractBookDAO
{
    /**
     * The memory data source for books
     */
    private static LinkedHashMap<Integer, Book> bookDataSource = null;

    public MemoryBookDAO(LinkedHashMap<Integer, Book> bookDataSource)
    {
        this.bookDataSource = bookDataSource;
    }

    @Override
    public List<Book> searchBookByTitle(String name)
    {
        ArrayList<Book> resultBookList = new ArrayList<>();

        for (Book b : (ArrayList<Book>) BookManagerUtils.getInstance().hashMapToArrayList(bookDataSource))
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

        bookDataSource.put(newEntity.getID(), newEntity);
    }

    @Override
    public Book findEntityByID(Integer entityID)
    {
        return bookDataSource.get(entityID);
    }

    @Override
    public List<Book> findAll()
    {
        return (ArrayList<Book>) BookManagerUtils.getInstance().hashMapToArrayList(bookDataSource);
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
        return bookDataSource.isEmpty();
    }
}
