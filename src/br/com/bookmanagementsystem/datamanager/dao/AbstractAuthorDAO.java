package br.com.bookmanagementsystem.datamanager.dao;

import br.com.bookmanagementsystem.model.Author;
import java.util.List;

/**
 *
 * @author renann
 */
public abstract class AbstractAuthorDAO implements IDAO<Author>
{
    /**
     * Searches for Authors that have
     * <code>searchTerm</code> in its name
     *
     * @param searchTerm the search term
     *
     * @return the result author list or an empty list if there are no authors
     * with <code>searchTerm</code> in its first or last name
     */
    public abstract List<Author> searchAuthorByName(final String searchTerm);
}