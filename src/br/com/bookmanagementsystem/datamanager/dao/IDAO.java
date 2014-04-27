package br.com.bookmanagementsystem.datamanager.dao;

import java.io.Serializable;
import java.util.List;

/**
 * It defines a common interface which all Data Access Objects will have to
 * implement
 *
 * @param <T> The serializable entity that you want to persist
 *
 * @author renann
 */
public interface IDAO<T extends Serializable>
{
    /**
     * It'll insert the
     * <code>newEntity</code> into the data source. It's not needed to set the
     * ID for the
     * <code>newEntity</code>
     *
     * @param newEntity The new entity to be added into the data source
     */
    public void save(final T newEntity);

    /**
     * This method returns the entity according to the
     * <code>entityID</code>
     *
     * @param entityID the entity ID
     *
     * @return the entity or null if there's no entity
     * with <code>entityID</code>
     */
    public T findEntityByID(final Integer entityID);

    /**
     * This method returns all the records for the current entity
     *
     * @return A list with the entities, returns an empty list if there's no
     * entity in the data source
     */
    public List<T> findAll();

    /**
     * This method will go over the entities that are in the data source and put
     * the entity into the return list if there is entity with current ID
     *
     * @param entityIDList array of entity IDs
     *
     * @return A list with the entities that were found. If an ID doesn't exist,
     * it will be simple not added to the return list
     */
    public List<T> findEntitiesByIDs(final Integer[] entityIDList);

    /**
     * @return <code>true</code> if there is at least one record of this entity
     * in the datasource, <code>false</code> otherwise
     */
    public boolean isEmpty();
}
