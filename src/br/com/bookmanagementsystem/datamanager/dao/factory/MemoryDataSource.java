/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bookmanagementsystem.datamanager.dao.factory;

import br.com.bookmanagementsystem.model.Author;
import br.com.bookmanagementsystem.model.Book;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 *
 * @author renann
 */
class MemoryDataSource
{
    public HashMap<Class, LinkedHashMap<Integer, ?>> memoryDataSource;
    
    public MemoryDataSource()
    {
        memoryDataSource = new HashMap<>();
        
        memoryDataSource.put(Author.class, new LinkedHashMap<Integer, Author>());
        memoryDataSource.put(Book.class, new LinkedHashMap<Integer, Book>());
    }
    
    public LinkedHashMap<Integer, ?> getDataSourceByEntity(Class entity)
    {
        return memoryDataSource.get(entity);
    }
}
