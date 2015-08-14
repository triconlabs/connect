package com.tricon.labs.join.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Query;

import com.tricon.labs.join.exceptions.ApplicationException;

/**
 * 
 * @author shailesh
 * 
 */
public interface IBaseDao {
	
	/**
	 * <p>
	 * creates or updates the object.
	 * </p>
	 * 
	 * @param object
	 *            object to create.
	 */
	void save(Object object) throws ApplicationException;
	
	/**
	 * <p>
	 * Inserts the object in its corresponding collection.
	 * </p>
	 * 
	 * @param object
	 *            object to insert.
	 */
	void create(Object object) throws ApplicationException;
	
	/**
	 * 
	 * @param batchToSave
	 * @param entityClass
	 * @throws ApplicationException
	 */
	<T> void createAll(List<T> batchToSave, Class<T> entityClass) throws ApplicationException;
	
	/**
	 * <p>
	 * Inserts the object in its corresponding collection.
	 * </p>
	 * @param <T>
	 * 
	 * @param object
	 *            object to insert.
	 */
	void create(Object object, String collectionName) throws ApplicationException;

	/**
	 * <p>
	 * To get the object from the collection referred by given entity class
	 * based on given id as primary key.
	 * </p>
	 * 
	 * @param id
	 * @param entityClass
	 * @return the object with the given id mapped onto the given target class.
	 */
	<T> T findById(String id, Class<T> entityClass) throws ApplicationException;
	
	/**
	 * <p>
	 * To get the object from the collection referred by given entity class
	 * based on given id as primary key.
	 * </p>
	 * 
	 * @param id
	 * @param entityClass
	 * @return the object with the given id mapped onto the given target class.
	 */
	<T> T findById(Object id, Class<T> entityClass) throws ApplicationException;
	
	/**
	 * 
	 * @param <T>
	 * @param collectionName
	 * @return
	 */
	<T> List<T> findAll(Class<T> entityClass, Integer page, Integer rp, String sortParam) throws ApplicationException;
	
	/**
	 * 
	 * @param <T>
	 * @param collectionName
	 * @return
	 */
	<T> List<T> findAllInList(Set<String> idList, Class<T> entityClass, Integer page, Integer rp) throws ApplicationException;
	
	/**
	 * 
	 * @param <T>
	 * @param collectionName
	 * @return
	 */
	<T> List<T> findAllRandom(String hint, Class<T> entityClass, String collectionName, Integer page, Integer rp) throws ApplicationException;

	/**
	 * <p>
	 * To get list of objects from the collection referred by given entity
	 * class.
	 * </p>
	 * 
	 * @param entityClass
	 * @return List of objects
	 */
	<T> List<T> findAll(Class<T> entityClass, Integer page, Integer rp) throws ApplicationException;
	
	/**
	 * 
	 * @param id
	 * @param collectionName
	 * @throws ApplicationException
	 */
	<T> void delete(String id, Class<T> entityClass, String collectionName) throws ApplicationException;

	/**
	 * <p>
	 * Deletes the object from the collection referred by given entityClass.
	 * </p>
	 * 
	 * @param id
	 * @param entityClass
	 * @return the object being deleted.
	 */
	<T> void delete(String id, Class<T> entityClass) throws ApplicationException;
	
	/**
	 * <p>
	 * Deletes the object from the collection referred by given entityClass.
	 * </p>
	 * 
	 * @param id
	 * @param entityClass
	 * @return the object being deleted.
	 */
	<T> void deleteAllByParam(String paramName, String paramValue, Class<T> entityClass) throws ApplicationException;
	
	/**
	 * 
	 * @param <T>
	 * @param airlines
	 * @throws ApplicationException
	 */
	<T> void insertAllTypes(List<T> objects) throws ApplicationException;

	/**
	 * 
	 * @param <T>
	 * @param entityClass
	 */
	<T> void deleteAll(List<T> objects) throws ApplicationException;

	/**
	 * 
	 * @param id
	 * @param collectionName
	 * @return
	 */
	<T> T findById(String id, Class<T> entityClass, String collectionName) throws ApplicationException;
	
	/**
	 * <p>
	 * Updates the object in its corresponding collection referred using @Document tag.
	 * </p>
	 * 
	 * @param object object to update.
	 */
	void update(Object object) throws ApplicationException;

	/**
	 * 
	 * @param e
	 * @param collectionName
	 */
	void update(Object objectToUpdate, String collectionName) throws ApplicationException;
	
	/**
	 * 
	 * @param objectToUpdateList
	 * @param collectionName
	 * @throws ApplicationException
	 */
	<T> void insertAll(List<T> objectToUpdateList, String collectionName) throws ApplicationException;
	
	/**
	 * 
	 * @param entityClass
	 * @param updateParam
	 * @param updateValue
	 * @throws ApplicationException
	 */
	<T> void updateAll(Class<T> entityClass, String updateParam, String updateValue) throws ApplicationException;
	
	/**
	 * 
	 * @param query
	 * @param entityClass
	 * @param page
	 * @param rp
	 * @return
	 * @throws ApplicationException
	 */
	<T> List<T> findByQuery(Query query, Class<T> entityClass, Integer page,
			Integer rp) throws ApplicationException;
	
	/**
	 * 
	 * @param query
	 * @param entityClass
	 * @param collectionName
	 * @param page
	 * @param rp
	 * @return
	 * @throws ApplicationException
	 */
	<T> List<T> findByQuery(Query query, Class<T> entityClass,
			String collectionName, Integer page, Integer rp) throws ApplicationException;
	
	/**
	 * 
	 * @param objectToDelete
	 * @param collectionName
	 * @throws ApplicationException
	 */
	<T> void delete(T objectToDelete,String collectionName) throws ApplicationException;
	
	/**
	 * 
	 * @param objectToDelete
	 * @param collectionName
	 * @throws ApplicationException
	 */
	<T> void delete(T objectToDelete) throws ApplicationException;

	/**
	 * 
	 * @param paramName
	 * @param searchString
	 * @param collectionName
	 * @return
	 * @throws ApplicationException 
	 */
	<T> List<T> searchByParam(String paramName, String searchString, Class<T> entityClass,
			String collectionName, Integer page, Integer rp) throws ApplicationException;
	
	/**
	 * 
	 * @param paramName
	 * @param searchString
	 * @param collectionName
	 * @return
	 * @throws ApplicationException 
	 */
	<T> List<T> searchByParam(String paramName, String searchString, Class<T> entityClass, Integer page, Integer rp) throws ApplicationException;
	
	/**
	 * 
	 * @param searchParams
	 * @param matchParams
	 * @param entityClass
	 * @param page
	 * @param rp
	 * @return
	 * @throws ApplicationException
	 */
	<T> List<T> searchByParams(Map<String, String> searchParams,
			Map<String, Object> matchParams, Class<T> entityClass,
			Integer page, Integer rp) throws ApplicationException;
	
	/**
	 * 
	 * @param paramName
	 * @param paramNameInList
	 * @param searchstring
	 * @param i
	 * @param j
	 * @return
	 */
	<T> List<T> searchByParamInList(String paramName, String paramNameInList,
			String searchString, Class<T> entityClass, String collectionName, Integer page, Integer rp) throws ApplicationException;
	
	/**
	 * 
	 * @param paramName
	 * @param paramNameInList
	 * @param searchstring
	 * @param i
	 * @param j
	 * @return
	 */
	<T> List<T> searchByParamInList(String paramName, String paramNameInList,
			String searchString, Class<T> entityClass, Integer page, Integer rp) throws ApplicationException;

	/**
	 * 
	 * @param paramName
	 * @param searchString
	 * @param collectionName
	 * @return
	 * @throws ApplicationException 
	 */
	<T> T getByParam(String paramName, Object paramValue, Class<T> entityClass,
			String collectionName) throws ApplicationException;
	
	/**
	 * 
	 * @param <T>
	 * @param paramName
	 * @param searchString
	 * @param collectionName
	 * @return
	 * @throws ApplicationException 
	 */
	<T> T getByParam(String paramName, Object paramValue, Class<T> entityClass) throws ApplicationException;
	
	/**
	 * 
	 * @param paramName
	 * @param searchString
	 * @param collectionName
	 * @return
	 * @throws ApplicationException 
	 */
	<T> List<T> getAllByParam(String paramName, Object paramValue, Class<T> entityClass, Integer page, Integer rp) throws ApplicationException;
	
	/**
	 * 
	 * @param searchParams
	 * @param entityClass
	 * @return
	 * @throws ApplicationException
	 */
	<T> T getOneByParams(Map<String, Object> searchParams, Class<T> entityClass) throws ApplicationException;
	
	/**
	 * 
	 * @param searchParams
	 * @param entityClass
	 * @param page
	 * @param rp
	 * @return
	 * @throws ApplicationException
	 */
	<T> List<T> getAllByParams(Map<String, Object> searchParams, Class<T> entityClass, Integer page, Integer rp, String sortParam) throws ApplicationException;
	
	/**
	 * 
	 * @param searchParams
	 * @param lastSyncTime
	 * @param entityClass
	 * @param page
	 * @param rp
	 * @return
	 * @throws ApplicationException
	 */
	<T> List<T> getResourcesModifiedAfterDate(Map<String, Object> searchParams,
			Long lastSyncTime, Class<T> entityClass, Integer page, Integer rp) throws ApplicationException;
	
	/**
	 * 
	 * @param entityClass
	 * @param id
	 * @param paramName
	 * @param paramValue
	 * @throws ApplicationException 
	 */
	<T> T updateParam(Class<T> entityClass, String id, String paramName, Object paramValue) throws ApplicationException;
	
	/**
	 * 
	 * @param entityClass
	 * @param id
	 * @param params
	 * @param values
	 * @throws ApplicationException
	 */
	<T> T updateParams(Class<T> entityClass, String id, Map<String, Object> paramsToUpdate) throws ApplicationException;
	
	/**
	 * 
	 * @param entityClass
	 * @param idList
	 * @param paramName
	 * @param paramValue
	 * @throws ApplicationException
	 */
	<T> void updateParamInList(Class<T> entityClass, List<ObjectId> idList,
			String paramName, String paramValue) throws ApplicationException;
	
	/**
	 * 
	 * @param entityClass
	 * @param idList
	 * @param paramName
	 * @param paramValue
	 * @throws ApplicationException
	 */
	<T> void updateParamInList(Class<T> entityClass, List<ObjectId> idList,
			String paramName, Object paramValue) throws ApplicationException;
	
	/**
	 * 
	 * @param entityClass
	 * @param searchParam
	 * @param searchText
	 * @param updateParam
	 * @param updateValue
	 * @throws ApplicationException
	 */
	<T> void findAndUpdate(Class<T> entityClass, String searchParam,
			String searchText, String updateParam, Object updateValue) throws ApplicationException;


	/**
	 * 
	 * @param collectionName
	 * @throws ApplicationException
	 */
	void dropCollection(String collectionName) throws ApplicationException;

	/**
	 * 
	 * @param ids
	 * @param collectionName
	 * @throws ApplicationException
	 */
	void deleteAll(List<String> ids, String collectionName)	throws ApplicationException;
	
	/**
	 * 
	 * @param <T>
	 * @param paramName
	 * @param paramValue
	 * @param class1
	 * @throws ApplicationException 
	 */
	<T> Long getCount(String paramName, String paramValue, Class<T> entityClass) throws ApplicationException;
	
	/**
	 * 
	 * @param query
	 * @param entityClass
	 * @return
	 * @throws ApplicationException
	 */
	<T> Long getCount(Query query, Class<T> entityClass) throws ApplicationException;
	
	/**
	 * 
	 * @param searchparams
	 * @param entityClass
	 * @return
	 * @throws ApplicationException
	 */
	<T> Long getCount(Map<String, Object> searchparams, Class<T> entityClass) throws ApplicationException;

}
