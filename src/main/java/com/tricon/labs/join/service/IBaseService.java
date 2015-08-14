package com.tricon.labs.join.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.types.ObjectId;

import com.tricon.labs.join.exceptions.ApplicationException;

/**
 * 
 * @author Shailesh
 *
 * @param <E>
 */
public interface IBaseService<E> {
	
	/**
	 * 
	 * @param id
	 * @param entityClass
	 * @return
	 * @throws ApplicationException
	 */
	E get(String id, Class<E> entityClass) throws ApplicationException;
	
	/**
	 * 
	 * @param id
	 * @param entityClass
	 * @param collectionName
	 * @return
	 * @throws ApplicationException
	 */
	E get(String id, Class<E> entityClass, String collectionName)
			throws ApplicationException;

	/**
	 * 
	 * @param entityClass
	 * @param collectionName
	 * @param page
	 * @param rp
	 * @return
	 * @throws ApplicationException
	 */
	List<E> getAll(Class<E> entityClass, Integer page,
			Integer rp, String sortParam) throws ApplicationException;
	
	/**
	 * 
	 * @param entityClass
	 * @param collectionName
	 * @param page
	 * @param rp
	 * @return
	 * @throws ApplicationException
	 */
	List<E> getAll(Class<E> entityClass, Integer page,
			Integer rp) throws ApplicationException;
	
	/**
	 * 
	 * @param idList
	 * @param entityClass
	 * @param page
	 * @param rp
	 * @return
	 * @throws ApplicationException
	 */
	List<E> getAllInList(Set<String> idList, Class<E> entityClass,
			Integer page, Integer rp) throws ApplicationException;
	
	/**
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param entityClass
	 * @param collectionName
	 * @return
	 * @throws ApplicationException
	 */
	<T> T getByParam(String paramName, Object paramValue, Class<T> entityClass,
			String collectionName) throws ApplicationException;

	/**
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param entityClass
	 * @return
	 * @throws ApplicationException
	 */
	E getByParam(String paramName, Object paramValue, Class<E> entityClass) throws ApplicationException;
	
	/**
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param entityClass
	 * @return
	 * @throws ApplicationException 
	 */
	List<E> getAllByParam(String paramName, Object paramValue, Class<E> entityClass, Integer page, Integer rp) 
			throws ApplicationException;
	
	/**
	 * 
	 * @param searchParams
	 * @param entityClass
	 * @return
	 * @throws ApplicationException
	 */
	E getByParams(Map<String, Object> searchParams, Class<E> entityClass) throws ApplicationException;

	/**
	 * 
	 * @param searchParams
	 * @param entityClass
	 * @param page
	 * @param rp
	 * @return
	 * @throws ApplicationException
	 */
	List<E> getAllByParams(Map<String, Object> searchParams, Class<E> entityClass,	Integer page, Integer rp, String sortParam) 
			throws ApplicationException;
	
	/**
	 * 
	 * @param hint
	 * @param entityClass
	 * @param collectionName
	 * @param page
	 * @param rp
	 * @return
	 * @throws ApplicationException
	 */
	List<E> getAllRandom(String hint, Class<E> entityClass,	String collectionName, Integer page, Integer rp)
			throws ApplicationException;
	
	/**
	 * 
	 * @param paramName
	 * @param searchString
	 * @param entityClass
	 * @param collectionName
	 * @param page
	 * @param rp
	 * @return
	 * @throws ApplicationException
	 */
	List<E> searchByParam(String paramName, String searchString, Class<E> entityClass, String collectionName, 
			Integer page, Integer rp) throws ApplicationException;


	/**
	 * 
	 * @param paramName
	 * @param searchString
	 * @param entityClass
	 * @param page
	 * @param rp
	 * @return
	 * @throws ApplicationException
	 */
	List<E> searchByParam(String paramName, String searchString, Class<E> entityClass, Integer page, Integer rp)
			throws ApplicationException;
	
	/**
	 * 
	 * @param paramName
	 * @param searchString
	 * @param entityClass
	 * @param page
	 * @param rp
	 * @return
	 * @throws ApplicationException
	 */
	List<E> searchByParams(Map<String, String> searchParams, Map<String, Object> matchParams, Class<E> entityClass, Integer page, Integer rp)
			throws ApplicationException;

	/**
	 * 
	 * @param paramName
	 * @param paramNameInList
	 * @param searchString
	 * @param entityClass
	 * @param collectionName
	 * @param page
	 * @param rp
	 * @return
	 * @throws ApplicationException
	 */
	List<E> searchByParamInList(String paramName, String paramNameInList,
			String searchString, Class<E> entityClass, String collectionName,
			Integer page, Integer rp) throws ApplicationException;

	/**
	 * 
	 * @param paramName
	 * @param paramNameInList
	 * @param searchString
	 * @param entityClass
	 * @param page
	 * @param rp
	 * @return
	 * @throws ApplicationException
	 */
	List<E> searchByParamInList(String paramName, String paramNameInList,
			String searchString, Class<E> entityClass, Integer page, Integer rp)
			throws ApplicationException;

	/**
	 * 
	 * @param objectToAdd
	 * @param collectionName
	 * @throws ApplicationException
	 */
	void add(E objectToAdd, String collectionName) throws ApplicationException;
	
	/**
	 * 
	 * @param objectToAdd
	 * @throws ApplicationException
	 */
	void add(E objectToAdd) throws ApplicationException;
	
	/**
	 * 
	 * @param objectsToAdd
	 * @throws ApplicationException
	 */
	void addAll(List<E> objectsToAdd, Class<E> entityClass) throws ApplicationException;
	
	/**
	 * 
	 * @param objectToUpdate
	 * @param collectionName
	 * @throws ApplicationException
	 */
	void update(E objectToUpdate, String collectionName) throws ApplicationException;
	
	/**
	 * 
	 * @param objectToUpdate
	 * @throws ApplicationException
	 */
	void update(E objectToUpdate) throws ApplicationException;

	/**
	 * 
	 * @param objectToUpdateList
	 * @param collectionName
	 * @throws ApplicationException
	 */
	void insertAll(List<E> objectToUpdateList, String collectionName) throws ApplicationException;
	
	/**
	 * 
	 * @param objectToUpdateList
	 * @param collectionName
	 * @throws ApplicationException
	 */
	void insertAllTypes(List<E> objectToUpdateList) throws ApplicationException;
	
	/**
	 * 
	 * @param entityClass
	 * @param updateParam
	 * @param updateValue
	 * @throws ApplicationException 
	 */
	void updateAll(Class<E> entityClass, String updateParam, String updateValue) throws ApplicationException;
	
	/**
	 * 
	 * @param entityClass
	 * @param id
	 * @param paramName
	 * @param paramValue
	 * @throws ApplicationException
	 */
	E updateParam(Class<E> entityClass, String id, String paramName,
			Object paramValue) throws ApplicationException;
	
	/**
	 * 
	 * @param entityClass
	 * @param id
	 * @param params
	 * @param values
	 * @throws ApplicationException
	 */
	E updateParams(Class<E> entityClass, String id, Map<String, Object> paramsToUpdate) throws ApplicationException;
	
	/**
	 * 
	 * @param entityClass
	 * @param idList
	 * @param paramName
	 * @param paramValue
	 * @throws ApplicationException
	 */
	void updateParamInList(Class<?> entityClass, List<ObjectId> idList,
			String paramName, String paramValue) throws ApplicationException;
	
	/**
	 * 
	 * @param entityClass
	 * @param idList
	 * @param paramName
	 * @param paramValue
	 * @throws ApplicationException
	 */
	void updateParamInList(Class<?> entityClass, List<ObjectId> idList,
			String paramName, Object paramValue) throws ApplicationException;
	
	/**
	 * 
	 * @param class1
	 * @param searchParam
	 * @param searchText
	 * @param updateParam
	 * @param updateValue
	 * @return
	 * @throws ApplicationException 
	 */
	void findAndUpdate(Class<E> entityClass, String searchParam,
			String searchText, String updateParam, String updateValue) throws ApplicationException;
	

	/**
	 * 
	 * @param objectToDelete
	 * @param collectionName
	 * @throws ApplicationException
	 */
	void delete(E objectToDelete, String collectionName) throws ApplicationException;
	
	/**
	 * 
	 * @param objectToDelete
	 * @throws ApplicationException
	 */
	void delete(E objectToDelete) throws ApplicationException;

	/**
	 * 
	 * @param id
	 * @param entityClass
	 * @param collectionName
	 * @throws ApplicationException
	 */
	void delete(String id, Class<E> entityClass, String collectionName)	throws ApplicationException;
	
	/**
	 * 
	 * @param id
	 * @param entityClass
	 * @throws ApplicationException
	 */
	void delete(String id, Class<E> entityClass)	throws ApplicationException;
	
	/**
	 * 
	 * @param id
	 * @param entityClass
	 * @throws ApplicationException
	 */
	void deleteAllByParam(String paramName, String paramValue, Class<E> entityClass)	throws ApplicationException;

	/**
	 * 
	 * @param param
	 * @param value
	 * @param entityClass
	 * @return
	 * @throws ApplicationException
	 */
	Integer paramAvailable(String param, String value, Class<E> entityClass)
			throws ApplicationException;

}
