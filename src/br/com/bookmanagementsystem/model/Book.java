package br.com.bookmanagementsystem.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * An in-memory representation of the Book entity
 *
 * @author renann
 */
public class Book implements Serializable
{
    private Integer ID;
    private String title;
    private String edition;
    private Integer numberOfPages;
    private String language;
    private ArrayList<Integer> authorIDList = new ArrayList<>();
    private static final long serialVersionUID = 1092127L;

    public Integer getID()
    {
        return ID;
    }

    public void setID(Integer ID)
    {
        this.ID = ID;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getEdition()
    {
        return edition;
    }

    public void setEdition(String edition)
    {
        this.edition = edition;
    }

    public Integer getNumberOfPages()
    {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages)
    {
        this.numberOfPages = numberOfPages;
    }

    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }

    public ArrayList<Integer> getAuthorIDList()
    {
        return authorIDList;
    }

    public boolean hasAuthors()
    {
        return !authorIDList.isEmpty();
    }

    @Override
    public String toString()
    {
        return "ID: " + ID + "\nTitle: " + title + "\nEdition: " + edition + "\nLanguage: " + language + "\nNumber of Pages: " + numberOfPages;
    }
}