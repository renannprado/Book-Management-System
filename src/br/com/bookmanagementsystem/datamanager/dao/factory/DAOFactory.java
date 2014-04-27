package br.com.bookmanagementsystem.datamanager.dao.factory;

import br.com.bookmanagementsystem.datamanager.dao.AbstractAuthorDAO;
import br.com.bookmanagementsystem.datamanager.dao.AbstractBookDAO;

/**
 * This class has the main role of construct the DAOs of any type, new
 * DAOFactories need to extend this class
 *
 * @author renann
 */
public abstract class DAOFactory
{
    /**
     * The current enumeration of supported DAOs/DAOFactories
     */
    public enum FactoryType
    {
        /**
         * This type of factory handles a memory persistence (which is lost when
         * the application is turned off)
         */
        MEMORY("MEMORY"),
        /**
         * This type of factory handles a .dat file where all the application
         * data lies
         */
        BINARY_FILE("BINARY_FILE");
        /**
         * The name of the factory
         */
        private String factoryName;

        /**
         *
         * @return the name of the factory
         */
        public String getFactoryName()
        {
            return factoryName;
        }

        private FactoryType(String factoryName)
        {
            this.factoryName = factoryName;
        }
    };

    /**
     * Creates a new DAOFactory based on the
     * <code>factoryType</code>
     *
     * @param factoryType the type of the factory that you want to create
     *
     * @return the new DAOFactory
     */
    public static DAOFactory getDAOFactory(FactoryType factoryType)
    {
        switch (factoryType)
        {
            case MEMORY:
            {
                return new MemoryDAOFactory();
            }
            case BINARY_FILE:
            {
                return new BinaryFileDAOFactory();
            }
            default:
            {
                throw new UnsupportedOperationException("Factory of type " + factoryType.getFactoryName() + " is not supported.");
            }
        }
    }

    /**
     * Creates a new BookDAO
     *
     * @return the new BookDAO
     */
    public abstract AbstractBookDAO getBookDAO();

    /**
     * Creates a new AuthorDAO
     *
     * @return the new AuthorDAO
     */
    public abstract AbstractAuthorDAO getAuthorDAO();
}
