package br.com.bookmanagementsystem.datamanager.dao;

import br.com.bookmanagementsystem.model.Author;
import br.com.bookmanagementsystem.util.BookManagerUtils;
import br.com.bookmanagementsystem.util.IdentitySequenceGenerator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class handles the Memory persistence for entity
 * <code>Author</code>.
 *
 * @author renann
 */
public class MemoryAuthorDAO extends AbstractAuthorDAO
{
    /**
     * The memory data source for authors
     */
    private HashMap<Integer, Author> authorDataSource = null;

    public MemoryAuthorDAO(HashMap<Integer, Author> authorHashMap)
    {
        this.authorDataSource = authorHashMap;
    }

    @Override
    public List<Author> searchAuthorByName(final String name)
    {
        ArrayList<Author> resultAuthorList = new ArrayList<>();

        for (Author a : (ArrayList<Author>) BookManagerUtils.getInstance().hashMapToArrayList(authorDataSource))
        {
            if (a.getFirstAndLastName().toUpperCase().contains(name.toUpperCase()))
            {
                resultAuthorList.add(a);
            }
        }

        return resultAuthorList;
    }

    @Override
    public void save(Author newEntity)
    {
        newEntity.setID(IdentitySequenceGenerator.getNextID(Author.class));

        authorDataSource.put(newEntity.getID(), newEntity);
    }

    @Override
    public Author findEntityByID(Integer entityID)
    {
        return authorDataSource.get(entityID);
    }

    @Override
    public List<Author> findAll()
    {
        return (ArrayList<Author>) BookManagerUtils.getInstance().hashMapToArrayList(authorDataSource);
    }

    @Override
    public List<Author> findEntitiesByIDs(Integer[] entityIDList)
    {
        ArrayList<Author> authorList = new ArrayList<>();

        for (int i = 0; i < entityIDList.length; i++)
        {
            Author a = findEntityByID(entityIDList[i]);

            if (a != null)
            {
                authorList.add(a);
            }
        }

        return authorList;
    }

    @Override
    public boolean isEmpty()
    {
        return authorDataSource.isEmpty();
    }
}