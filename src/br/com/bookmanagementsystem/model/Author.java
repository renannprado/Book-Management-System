package br.com.bookmanagementsystem.model;

import java.io.Serializable;

/**
 * An in-memory representation of the Author entity
 *
 * @author renann
 */
public class Author implements Serializable
{
    private Integer ID;
    private String firstName;
    private String lastName;
    private static final long serialVersionUID = 1932137L;

    public Author()
    {
    }

    public Integer getID()
    {
        return ID;
    }

    public void setID(Integer ID)
    {
        this.ID = ID;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getFirstAndLastName()
    {
        return firstName + " " + lastName;
    }

    @Override
    public String toString()
    {
        return "ID: " + ID + "\nName: " + getFirstAndLastName();
    }
}