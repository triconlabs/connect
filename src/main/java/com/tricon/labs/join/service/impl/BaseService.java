package com.tricon.labs.join.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.tricon.labs.join.exceptions.ApplicationException;
import com.tricon.labs.join.repository.IBaseDao;
import com.tricon.labs.join.service.IBaseService;

/**
 * 
 * @author Shailesh
 * 
 * @param <E>
 */
@Service
public class BaseService<E> implements IBaseService<E> {

	@Resource(name = "baseDao")
	private IBaseDao baseDao;

	@Override
	public E get(String id, Class<E> entityClass) throws ApplicationException {
		return baseDao.findById(id, entityClass);
	}

	@Override
	public E get(String id, Class<E> entityClass, String collectionName)
			throws ApplicationException {
		return baseDao.findById(id, entityClass, collectionName);
	}

	@Override
	public List<E> getAll(Class<E> entityClass, Integer page, Integer rp)
			throws ApplicationException {
		List<E> objects = null;
		objects = baseDao.findAll(entityClass, page, rp);
		return objects;
	}
	
	@Override
	public List<E> getAll(Class<E> entityClass,
			Integer page, Integer rp, String sortParam) throws ApplicationException {
		List<E> objects = null;
		objects = baseDao.findAll(entityClass, page, rp, sortParam);
		return objects;
	}

	/**
	 * @param idList bson Object Id's
	 */
	@Override
	public List<E> getAllInList(Set<String> idList, Class<E> entityClass,
			Integer page, Integer rp) throws ApplicationException {
		return baseDao.findAllInList(idList, entityClass, page, rp);
	}

	@Override
	public <T> T getByParam(String paramName, Object paramValue,
			Class<T> entityClass, String collectionName)
			throws ApplicationException {
		return baseDao.getByParam(paramName, paramValue, entityClass,
				collectionName);
	}

	@Override
	public E getByParam(String paramName, Object paramValue,
			Class<E> entityClass) throws ApplicationException {
		return baseDao.getByParam(paramName, paramValue, entityClass);
	}

	@Override
	public List<E> getAllByParam(String paramName, Object paramValue,
			Class<E> entityClass, Integer page, Integer rp)	throws ApplicationException {
		return baseDao.getAllByParam(paramName, paramValue, entityClass, page, rp);
	}

	@Override
	public E getByParams(Map<String, Object> searchParams, Class<E> entityClass)
			throws ApplicationException {
		return baseDao.getOneByParams(searchParams, entityClass);
	}

	@Override
	public List<E> getAllByParams(Map<String, Object> filterParams,
			Class<E> entityClass, Integer page, Integer rp, String sortParam)
			throws ApplicationException {
		return baseDao.getAllByParams(filterParams, entityClass, page, rp, sortParam);
	}

	@Override
	public List<E> getAllRandom(String hint, Class<E> entityClass,
			String collectionName, Integer page, Integer rp)
			throws ApplicationException {
		List<E> objects = null;
		objects = baseDao.findAllRandom(hint, entityClass, collectionName,
				page, rp);
		return objects;
	}

	@Override
	public List<E> searchByParam(String paramName, String searchString,
			Class<E> entityClass, String collectionName, Integer page,
			Integer rp) throws ApplicationException {
		return baseDao.searchByParam(paramName, searchString, entityClass,
				collectionName, page, rp);
	}

	@Override
	public List<E> searchByParam(String paramName, String searchString,
			Class<E> entityClass, Integer page, Integer rp)
			throws ApplicationException {
		return baseDao.searchByParam(paramName, searchString, entityClass,
				page, rp);
	}
	
	@Override
	public List<E> searchByParams(Map<String, String> searchParams,
			Map<String, Object> matchParams, Class<E> entityClass,
			Integer page, Integer rp) throws ApplicationException {
		return baseDao.searchByParams(searchParams, matchParams, entityClass, page, rp);
	}

	@Override
	public List<E> searchByParamInList(String paramName,
			String paramNameInList, String searchString, Class<E> entityClass,
			String collectionName, Integer page, Integer rp)
			throws ApplicationException {
		return baseDao.searchByParamInList(paramName, paramNameInList,
				searchString, entityClass, collectionName, page, rp);
	}

	@Override
	public List<E> searchByParamInList(String paramName,
			String paramNameInList, String searchString, Class<E> entityClass,
			Integer page, Integer rp) throws ApplicationException {
		return baseDao.searchByParamInList(paramName, paramNameInList,
				searchString, entityClass, page, rp);
	}

	@Override
	public void add(E objectToAdd, String collectionName) throws ApplicationException {
		baseDao.create(objectToAdd, collectionName);
	}

	@Override
	public void add(E objectToAdd) throws ApplicationException {
		this.baseDao.create(objectToAdd);
	}
	
	@Override
	public void addAll(List<E> objectsToAdd, Class<E> entityClass) throws ApplicationException {
		this.baseDao.createAll(objectsToAdd, entityClass);
	}

	@Override
	public void update(E objectToUpdate, String collectionName)
			throws ApplicationException {
		baseDao.update(objectToUpdate, collectionName);
	}

	@Override
	public void update(E objectToUpdate) throws ApplicationException {
		baseDao.update(objectToUpdate);
	}

	@Override
	public void insertAll(List<E> objectToUpdateList, String collectionName)
			throws ApplicationException {
		baseDao.insertAll(objectToUpdateList, collectionName);
	}

	@Override
	public void insertAllTypes(List<E> objectToUpdateList)
			throws ApplicationException {
		baseDao.insertAllTypes(objectToUpdateList);
	}

	@Override
	public void updateAll(Class<E> entityClass, String updateParam,
			String updateValue) throws ApplicationException {
		baseDao.updateAll(entityClass, updateParam, updateValue);
	}

	@Override
	public E updateParam(Class<E> entityClass, String id, String paramName,
			Object paramValue) throws ApplicationException {
		return baseDao.updateParam(entityClass, id, paramName, paramValue);
	}
	
	@Override
	public E updateParams(Class<E> entityClass, String id, Map<String, Object> paramsToUpdate) throws ApplicationException {
		return baseDao.updateParams(entityClass, id, paramsToUpdate);
	}


	@Override
	public void updateParamInList(Class<?> entityClass, List<ObjectId> idList,
			String paramName, String paramValue) throws ApplicationException {
		baseDao.updateParamInList(entityClass, idList, paramName, paramValue);
	}
	
	@Override
	public void updateParamInList(Class<?> entityClass, List<ObjectId> idList,
			String paramName, Object paramValue) throws ApplicationException {
		baseDao.updateParamInList(entityClass, idList, paramName, paramValue);
	}

	@Override
	public void findAndUpdate(Class<E> entityClass, String searchParam,
			String searchText, String updateParam, String updateValue)
			throws ApplicationException {
		baseDao.findAndUpdate(entityClass, searchParam, searchText,
				updateParam, updateValue);
	}

	@Override
	public void delete(E objectToDelete, String collectionName)
			throws ApplicationException {
		baseDao.delete(objectToDelete, collectionName);
	}

	@Override
	public void delete(E objectToDelete) throws ApplicationException {
		baseDao.delete(objectToDelete);
	}

	@Override
	public void delete(String id, Class<E> entityClass, String collectionName)
			throws ApplicationException {
		baseDao.delete(id, entityClass, collectionName);
	}

	@Override
	public void delete(String id, Class<E> entityClass)
			throws ApplicationException {
		baseDao.delete(id, entityClass);
	}

	@Override
	public void deleteAllByParam(String paramName, String paramValue,
			Class<E> entityClass) throws ApplicationException {
		baseDao.deleteAllByParam(paramName, paramValue, entityClass);
	}

	@Override
	public Integer paramAvailable(String param, String value,
			Class<E> entityClass) throws ApplicationException {
		List<E> list = baseDao.getAllByParam(param, value, entityClass, null,
				null);
		if (list != null)
			return list.size();
		else
			return 0;
	}

}
