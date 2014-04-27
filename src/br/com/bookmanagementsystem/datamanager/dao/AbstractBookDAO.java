/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bookmanagementsystem.datamanager.dao;

import br.com.bookmanagementsystem.model.Book;
import java.util.List;

/**
 *
 * @author renann
 */
public abstract class AbstractBookDAO implements IDAO<Book>
{
    /**
     * This method searches for a book that contains
     * <code>searchTerm</code> in its title
     *
     * @param searchTerm the search term
     *
     * @return the book result list or an empty list if there are no books which
     * contains </code>searchTerm</code> in the title
     */
    public abstract List<Book> searchBookByTitle(final String searchTerm);
}
