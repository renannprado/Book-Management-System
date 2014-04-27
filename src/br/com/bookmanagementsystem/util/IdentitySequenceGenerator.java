package br.com.bookmanagementsystem.util;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Data Access Objects will use this class to generate a new ID for each new
 * Entity
 *
 * @author renann
 */
public class IdentitySequenceGenerator
{
    /**
     * HashMap that holds the IndentityGenerator for each Entity
     */
    public static HashMap<Class, AtomicInteger> identityGenList = new HashMap<>();

    /**
     * Generates a new entity ID based on
     * <code>theClass</code>
     *
     * @param theClass Entity that you want to generate the ID
     *
     * @return the new entity ID
     */
    public static Integer getNextID(Class theClass)
    {
        return identityGenList.get(theClass).getAndIncrement();
    }
}