/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bookmanagementsystem.util;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Singleton class which holds some utility methods.
 * Singleton pattern is not mandatory here, it's for future use.
 *
 * @author renann
 */
public class BookManagerUtils
{
    /**
     * The instance
     */
    private static BookManagerUtils instance;

    private BookManagerUtils()
    {
    }

    /**
     * Method to get the instance for this singleton class
     *
     * @return the instance of this class
     */
    public static BookManagerUtils getInstance()
    {
        return instance == null ? instance = new BookManagerUtils() : instance;
    }

    /**
     * Converts any hashmap to an ArrayList<?>
     *
     * @param hashmap the hashmap to be converted
     *
     * @return the converted ArrayList<?>
     *
     * @throws NullPointerException if <code>hashmap</code> is null
     */
    public ArrayList<?> hashMapToArrayList(final HashMap<?, ?> hashmap)
    {
        final ArrayList<?> theArrayList;

        theArrayList = new ArrayList<>(hashmap.values());

        return theArrayList;
    }
}
