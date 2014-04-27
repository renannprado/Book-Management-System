/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bookmanagementsystem.datamanager.dao.factory;

import br.com.bookmanagementsystem.datamanager.dao.AbstractAuthorDAO;
import br.com.bookmanagementsystem.datamanager.dao.AbstractBookDAO;
import br.com.bookmanagementsystem.datamanager.dao.MemoryAuthorDAO;
import br.com.bookmanagementsystem.datamanager.dao.MemoryBookDAO;
import br.com.bookmanagementsystem.model.Author;
import br.com.bookmanagementsystem.model.Book;
import java.util.LinkedHashMap;

/**
 *
 * @author renann
 */
public class MemoryDAOFactory extends DAOFactory
{
    private static MemoryDataSource dataSource = new MemoryDataSource();

    @Override
    public AbstractBookDAO getBookDAO()
    {
        return new MemoryBookDAO((LinkedHashMap<Integer, Book>) dataSource.getDataSourceByEntity(Book.class));
    }

    @Override
    public AbstractAuthorDAO getAuthorDAO()
    {
        return new MemoryAuthorDAO((LinkedHashMap<Integer, Author>) dataSource.getDataSourceByEntity(Author.class));
    }  
}
