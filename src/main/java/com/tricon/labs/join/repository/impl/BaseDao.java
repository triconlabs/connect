package com.tricon.labs.join.repository.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoException;
import com.tricon.labs.join.exceptions.ApplicationException;
import com.tricon.labs.join.exceptions.DataBaseException;
import com.tricon.labs.join.repository.IBaseDao;

/**
 * 
 * @author Shailesh
 * 
 */
@Repository
public class BaseDao implements IBaseDao {
	
	private static Logger LOGGER = LoggerFactory.getLogger(BaseDao.class);

	/**
	 * mongoTemplate to perform DB operations.
	 */
	@Autowired
	protected MongoOperations mongoOperations;

	public MongoOperations getMongoOperations() {
		return mongoOperations;
	}

	public void setMongoOperations(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}

	/**
	 * 
	 */
	@Override
	public void save(Object object) throws ApplicationException {
		try {
			this.mongoOperations.save(object);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}", e.getMessage());
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}",  e.getMessage());
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}

	/**
	 * 
	 */
	@Override
	public void create(Object object) throws ApplicationException {
		try {
			this.mongoOperations.insert(object);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}",  e.getMessage());
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}",  e.getMessage());
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}
	
	/**
	 * 
	 */
	@Override
	public <T> void createAll(List<T> batchToSave, Class<T> entityClass) throws ApplicationException {
		try {
			this.mongoOperations.insert(batchToSave, entityClass);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}",  e.getMessage());
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}",  e.getMessage());
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}

	/**
	 * 
	 */
	@Override
	public void create(Object object, String collectionName)
			throws ApplicationException {
		try {
			this.mongoOperations.insert(object, collectionName);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, collectionName - {}",  e.getMessage(), collectionName);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, collectionName - {}",  e.getMessage(), collectionName);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}

	/**
	 * 
	 */
	@Override
	public <T> T findById(String id, Class<T> entityClass)
			throws ApplicationException {
		try {
			T findById = this.mongoOperations.findById(id, entityClass);
			return findById;
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, id - {}, entityClass - {}",  e.getMessage(), id, entityClass.getName());
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, id - {}, entityClass - {}",  e.getMessage(), id, entityClass.getName());
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}

	/**
	 * 
	 */
	@Override
	public <T> T findById(Object id, Class<T> entityClass)
			throws ApplicationException {
		try {
			T findById = this.mongoOperations.findById(id, entityClass);
			return findById;
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, id - {}, entityClass - {}",  e.getMessage(), id, entityClass.getName());
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, id - {}, entityClass - {}",  e.getMessage(), id, entityClass.getName());
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}

	/**
	 * 
	 */
	@Override
	public <T> List<T> findAll(Class<T> entityClass, Integer page, Integer rp, String sortParam) throws ApplicationException {
		try {
			if (page != null && rp != null) {
				return this.mongoOperations.find(
						new Query().with(new Sort(Sort.Direction.DESC, sortParam)).with(new PageRequest(page, rp)),
						entityClass);
			}
			return this.mongoOperations.find(
					new Query().with(new Sort(Sort.Direction.DESC, sortParam)), entityClass);
		} catch (MongoException e) {
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}

	/**
	 * TODO find out how hint is used.
	 */
	@Override
	public <T> List<T> findAllRandom(String hint, Class<T> entityClass,
			String collectionName, Integer page, Integer rp)
			throws ApplicationException {
		if (hint == null) {
			hint = "serendipity";
		}
		try {
			if (rp != null) {
				return this.mongoOperations.find(new Query().withHint(hint)
						.limit(rp), entityClass);
			}
			return this.mongoOperations.find(new Query().withHint(hint),
					entityClass);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, hint - {}, entityClass - {}, collectionName - {}, page - {}, rp - {}", 
					 e.getMessage(), entityClass.getName(), hint, collectionName, page, rp);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, hint - {}, entityClass - {}, collectionName - {}, page - {}, rp - {}", 
					 e.getMessage(), entityClass.getName(), hint, collectionName, page, rp);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}

	}

	/**
	 * 
	 */
	@Override
	public <T> List<T> findAll(Class<T> entityClass, Integer page, Integer rp)
			throws ApplicationException {
		try {
			if (page != null && rp != null) {
				return this.mongoOperations.find(
						new Query().with(new PageRequest(page, rp)),
						entityClass);
			}
			return this.mongoOperations.findAll(entityClass);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, entityClass - {}, page - {}, rp - {}", 
					 e.getMessage(), entityClass.getName(), page, rp);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, entityClass - {}, page - {}, rp - {}", 
					 e.getMessage(), entityClass.getName(), page, rp);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}

	/**
	 * 
	 */
	@Override
	public <T> List<T> findAllInList(Set<String> idList, Class<T> entityClass,
			Integer page, Integer rp) throws ApplicationException {
		List<ObjectId> objectIds = new ArrayList<ObjectId>();
		for (String id : idList) {
			objectIds.add(new ObjectId(id));
		}
		try {
			if (page != null && rp != null) {
				return this.mongoOperations.find(new Query(Criteria
						.where("_id").in(objectIds)).with(new PageRequest(page,	rp)), entityClass);
			}
			return this.mongoOperations.find(new Query(Criteria.where("_id")
					.in(objectIds)), entityClass);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, idList - {}, entityClass - {}, page - {}, rp - {}", 
					 e.getMessage(), idList, entityClass.getName(), page, rp);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, idList - {}, entityClass - {}, page - {}, rp - {}", 
					 e.getMessage(), idList, entityClass.getName(), page, rp);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}

	@Override
	public <T> List<T> findByQuery(Query query, Class<T> entityClass,
			Integer page, Integer rp) throws ApplicationException {
		try {
			if (page != null && rp != null) {
				return this.mongoOperations.find(
						query.with(new PageRequest(page, rp)), entityClass);
			}
			return this.mongoOperations.find(query, entityClass);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, query - {}, entityClass - {}, page - {}, rp - {}", 
					 e.getMessage(), query.toString(), entityClass.getName(), page, rp);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, query - {}, entityClass - {}, page - {}, rp - {}", 
					 e.getMessage(), query.toString(), entityClass.getName(), page, rp);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}

	@Override
	public <T> List<T> findByQuery(Query query, Class<T> entityClass,
			String collectionName, Integer page, Integer rp)
			throws ApplicationException {
		try {
			if (page != null && rp != null) {
				return this.mongoOperations.find(
						query.with(new PageRequest(page, rp)), entityClass,
						collectionName);
			}
			return this.mongoOperations
					.find(query, entityClass, collectionName);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, query - {}, entityClass - {}, collectionName - {}, page - {}, rp - {}", 
					 e.getMessage(), query.toString(), entityClass.getName(), collectionName, page, rp);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, query - {}, entityClass - {}, collectionName - {}, page - {}, rp - {}", 
					 e.getMessage(), query.toString(), entityClass.getName(), collectionName, page, rp);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}
	
	@Override
	public <T> List<T> getResourcesModifiedAfterDate(Map<String, Object> searchParams, Long lastSyncTime, Class<T> entityClass, Integer page, Integer rp) throws ApplicationException {
		Iterator<Entry<String, Object>> iterator = searchParams.entrySet().iterator();
		Entry<String, Object> next = iterator.next();
		Criteria criteria = Criteria.where("auditInfo.modifiedOn").gte(new Date(lastSyncTime)).and(next.getKey()).is(next.getValue());
		while (iterator.hasNext()) {
			next = iterator.next();
			criteria = criteria.and(next.getKey()).is(next.getValue());
		}
		return findByQuery(new Query(criteria), entityClass, page, rp);
	}

	@Override
	public <T> void delete(String id, Class<T> entityClass)
			throws ApplicationException {
		try {
			this.mongoOperations.findAndRemove(new Query(Criteria.where("_id").is(id)), entityClass);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, id - {}, entityClass - {}",  e.getMessage(), id, entityClass.getName());
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, id - {}, entityClass - {}",  e.getMessage(), id, entityClass.getName());
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}
	
	@Override
	public <T> void deleteAllByParam(String paramName, String paramValue, Class<T> entityClass)
			throws ApplicationException {
		try {
			this.mongoOperations.findAllAndRemove(new Query(Criteria.where(paramName).is(paramValue)), entityClass);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, param - {}, value - {}, entityClass - {}", e.getMessage(), paramName, paramValue, entityClass.getName());
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, param - {}, value - {}, entityClass - {}", e.getMessage(), paramName, paramValue,entityClass.getName());
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}

	@Override
	public <T> void delete(String id, Class<T> entityClass,	String collectionName) throws ApplicationException {
		try {
			this.mongoOperations.findAndRemove(new Query(Criteria.where("_id").is(id)), entityClass, collectionName);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, id - {}, entityClass - {}, collectionName - {}", 
					 e.getMessage(), id, entityClass.getName(), collectionName);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, id - {}, entityClass - {}, collectionName - {}", 
					 e.getMessage(), id, entityClass.getName(), collectionName);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}

	@Override
	public void dropCollection(String collectionName) throws ApplicationException {
		try {
			this.mongoOperations.dropCollection(collectionName);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, collectionName - {}",  e.getMessage(), collectionName);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, collectionName - {}",  e.getMessage(), collectionName);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}

	@Override
	public <T> void insertAllTypes(List<T> objects) throws ApplicationException {
		try {
			if (objects != null && objects.size() > 0)
				this.mongoOperations.insertAll(objects);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, objects - {}",  e.getMessage(), objects);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, objects - {}",  e.getMessage(), objects);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}

	/**
	 * TODO not working api.. to be fixed.
	 */
	@Override
	public <T> void deleteAll(List<T> objects) throws ApplicationException {
		try {
			if (objects != null && objects.size() > 0) {
				for (T t : objects) {
					this.mongoOperations.remove(t);
				}
			}
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, objects - {}",  e.getMessage(), objects);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, objects - {}",  e.getMessage(), objects);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}

	@Override
	public <T> T findById(String id, Class<T> entityClass, String collectionName)
			throws ApplicationException {
		try {
			return this.mongoOperations.findById(id, entityClass,
					collectionName);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, id - {}, entityClass - {}, collectionName - {}", 
					 e.getMessage(), id, entityClass, collectionName);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, id - {}, entityClass - {}, collectionName - {}", 
					 e.getMessage(), id, entityClass, collectionName);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}

	}

	@Override
	public void update(Object object) throws ApplicationException {
		try {
			this.mongoOperations.save(object);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, object - {}",  e.getMessage(), object);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, object - {}",  e.getMessage(), object);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} 
	}

	@Override
	public void update(Object objectToUpdate, String collectionName)
			throws ApplicationException {
		try {
			this.mongoOperations.save(objectToUpdate, collectionName);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, objectToUpdate - {}, collectionName - {}", 
					 e.getMessage(), objectToUpdate, collectionName);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, objectToUpdate - {}, collectionName - {}", 
					 e.getMessage(), objectToUpdate, collectionName);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}

	@Override
	public <T> void insertAll(List<T> objectToUpdateList, String collectionName)
			throws ApplicationException {
		try {
			this.mongoOperations.insert(objectToUpdateList, collectionName);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, objectToUpdateList - {}, collectionName - {}", 
					 e.getMessage(), objectToUpdateList, collectionName);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, objectToUpdateList - {}, collectionName - {}", 
					 e.getMessage(), objectToUpdateList, collectionName);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}

	@Override
	public <T> void delete(T objectToDelete, String collectionName)
			throws ApplicationException {
		try {
			this.mongoOperations.remove(objectToDelete, collectionName);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, objectToDelete - {}, collectionName - {}", 
					 e.getMessage(), objectToDelete, collectionName);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, objectToDelete - {}, collectionName - {}", 
					 e.getMessage(), objectToDelete, collectionName);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}

	@Override
	public <T> void delete(T objectToDelete) throws ApplicationException {
		try {
			this.mongoOperations.remove(objectToDelete);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, objectToDelete - {}",  e.getMessage(), objectToDelete);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, objectToDelete - {}",  e.getMessage(), objectToDelete);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}

	/**
	 * TODO verify this api. "id" is being used generally, analyze it.
	 */
	@Override
	public void deleteAll(List<String> ids, String collectionName)
			throws ApplicationException {
		try {
			this.mongoOperations.remove(
					new Query(Criteria.where("_id").in(ids)), collectionName);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, ids - {}, collectionName -{}",  e.getMessage(), ids, collectionName);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, ids - {}, collectionName -{}",  e.getMessage(), ids, collectionName);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}

	@Override
	public <T> List<T> searchByParam(String paramName, String searchString,
			Class<T> entityClass, String collectionName, Integer page,
			Integer rp) throws ApplicationException {
		try {
			if (page != null && rp != null) {
				return this.mongoOperations.find(
						new Query(Criteria.where(paramName).regex(searchString,
								"i")).with(new PageRequest(page, rp)),
						entityClass, collectionName);
			}
			return this.mongoOperations.find(new Query(Criteria
					.where(paramName).regex(searchString, "i")), entityClass,
					collectionName);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, paramName - {}, searchString - {}, entityClass - {}, collectionName -{}, page - {}, rp - {}", 
					 e.getMessage(), paramName, searchString, entityClass.getName(), collectionName, page, rp);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, paramName - {}, searchString - {}, entityClass - {}, collectionName -{}, page - {}, rp - {}", 
					 e.getMessage(), paramName, searchString, entityClass.getName(), collectionName, page, rp);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}

	@Override
	public <T> List<T> searchByParam(String paramName, String searchString,
			Class<T> entityClass, Integer page, Integer rp)
			throws ApplicationException {
		try {
			if (page != null && rp != null) {
				return this.mongoOperations.find(
						new Query(Criteria.where(paramName).regex(searchString,
								"i")).with(new PageRequest(page, rp)),
						entityClass);
			}
			return this.mongoOperations.find(new Query(Criteria
					.where(paramName).regex(searchString, "i")), entityClass);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, paramName - {}, searchString - {}, entityClass - {}, page - {}, rp - {}", 
					 e.getMessage(), paramName, searchString, entityClass.getName(), page, rp);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, paramName - {}, searchString - {}, entityClass - {}, page - {}, rp - {}", 
					 e.getMessage(), paramName, searchString, entityClass.getName(), page, rp);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}
	
	@Override
	public <T> List<T> searchByParams(Map<String, String> searchParams,
			Map<String, Object> matchParams, Class<T> entityClass,
			Integer page, Integer rp) throws ApplicationException {
		Criteria criteria = new Criteria();
		if (matchParams != null && !matchParams.isEmpty()) {
			Iterator<Entry<String, Object>> matchIterator = matchParams.entrySet().iterator();
			Entry<String, Object> matchNext = matchIterator.next();
			criteria = Criteria.where(matchNext.getKey()).is(matchNext.getValue());
			while (matchIterator.hasNext()) {
				matchNext = matchIterator.next();
				criteria = criteria.and(matchNext.getKey()).is(matchNext.getValue());
			}
		}
		if (searchParams != null && !searchParams.isEmpty()) {
			Iterator<Entry<String, String>> searchIterator = searchParams.entrySet().iterator();
			Entry<String, String> searchNext = searchIterator.next();
			List<Criteria> searchCriteria = new ArrayList<Criteria>();
			searchCriteria.add(Criteria.where(searchNext.getKey()).regex(searchNext.getValue(), "i"));
			while (searchIterator.hasNext()) {
				searchNext = searchIterator.next();
				searchCriteria.add(Criteria.where(searchNext.getKey()).regex(searchNext.getValue(), "i"));
			}
			criteria.orOperator(searchCriteria.toArray(new Criteria[]{}));
		}
		try {
			if (page != null && rp != null) {
				return this.mongoOperations.find(new Query(criteria).with(new PageRequest(page, rp)), entityClass);
			}
			return this.mongoOperations.find(new Query(criteria), entityClass);
		} catch (MongoException e) {
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}

	@Override
	public <T> List<T> searchByParamInList(String paramName,
			String paramNameInList, String searchString, Class<T> entityClass,
			String collectionName, Integer page, Integer rp)
			throws ApplicationException {
		try {
			Query query = new Query(Criteria.where(paramName).elemMatch(
					Criteria.where(paramNameInList).regex(searchString, "i")));
			if (page != null && rp != null) {
				return this.mongoOperations.find(
						query.with(new PageRequest(page, rp)), entityClass,
						collectionName);
			}
			return this.mongoOperations
					.find(query, entityClass, collectionName);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, paramName - {}, paramNameInList - {}, searchString - {}, entityClass - {}, collectionName -{}, page - {}, rp - {}", 
					 e.getMessage(), paramName, paramNameInList, searchString, entityClass.getName(), collectionName, page, rp);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, paramName - {}, paramNameInList - {}, searchString - {}, entityClass - {}, collectionName -{}, page - {}, rp - {}", 
					 e.getMessage(), paramName, paramNameInList, searchString, entityClass.getName(), collectionName, page, rp);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}

	@Override
	public <T> List<T> searchByParamInList(String paramName,
			String paramNameInList, String searchString, Class<T> entityClass,
			Integer page, Integer rp) throws ApplicationException {
		try {
			Query query = new Query(Criteria.where(paramName).elemMatch(
					Criteria.where(paramNameInList).regex(searchString, "i")));
			if (page != null && rp != null) {
				return this.mongoOperations.find(
						query.with(new PageRequest(page, rp)), entityClass);
			}
			return this.mongoOperations.find(query, entityClass);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, paramName - {}, paramNameInList - {}, searchString - {}, entityClass - {},  page - {}, rp - {}", 
					 e.getMessage(), paramName, paramNameInList, searchString, entityClass.getName(), page, rp);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, paramName - {}, paramNameInList - {}, searchString - {}, entityClass - {}, page - {}, rp - {}", 
					 e.getMessage(), paramName, paramNameInList, searchString, entityClass.getName(), page, rp);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}

	@Override
	public <T> T getByParam(String paramName, Object paramValue,
			Class<T> entityClass, String collectionName)
			throws ApplicationException {
		try {
			return this.mongoOperations.findOne(
					new Query(Criteria.where(paramName).is(paramValue)),
					entityClass, collectionName);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, paramName - {}, paramValue - {}, entityClass - {}, collectionName -{}", 
					 e.getMessage(), paramName, paramValue, entityClass.getName(), collectionName);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, paramName - {}, paramValue - {}, entityClass - {}, collectionName -{}", 
					 e.getMessage(), paramName, paramValue, entityClass.getName(), collectionName);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}

	@Override
	public <T> T getByParam(String paramName, Object paramValue, Class<T> entityClass) throws ApplicationException {
		try {
			return this.mongoOperations.findOne(
					new Query(Criteria.where(paramName).is(paramValue)),
					entityClass);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, paramName - {}, paramValue - {}, entityClass - {}", 
					 e.getMessage(), paramName, paramValue, entityClass.getName());
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, paramName - {}, paramValue - {}, entityClass - {}", 
					 e.getMessage(), paramName, paramValue, entityClass.getName());
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}

	@Override
	public <T> List<T> getAllByParam(String paramName, Object paramValue,
			Class<T> entityClass, Integer page, Integer rp) throws ApplicationException {
		try {
			if (page != null & rp != null) {
				return this.mongoOperations.find(
						new Query(Criteria.where(paramName).is(paramValue)).with(new PageRequest(page, rp)), entityClass);
			}
			return this.mongoOperations.find(new Query(Criteria.where(paramName).is(paramValue)), entityClass);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, paramName - {}, paramValue - {}, entityClass - {}, page - {}, rp - {}", 
					 e.getMessage(), paramName, paramValue, entityClass.getName(), page, rp);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, paramName - {}, paramValue - {}, entityClass - {}, page - {}, rp - {}", 
					 e.getMessage(), paramName, paramValue, entityClass.getName(), page, rp);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}

	@Override
	public <T> T getOneByParams(Map<String, Object> searchparams,
			Class<T> entityClass) throws ApplicationException {
		Iterator<Entry<String, Object>> iterator = searchparams.entrySet()
				.iterator();
		Entry<String, Object> next = iterator.next();
		Criteria criteria = Criteria.where(next.getKey()).is(next.getValue());
		while (iterator.hasNext()) {
			next = iterator.next();
			criteria = criteria.and(next.getKey()).is(next.getValue());
		}
		try {
			return this.mongoOperations.findOne(new Query(criteria),
					entityClass);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, searchparams - {}, entityClass - {}", 
					 e.getMessage(), searchparams.keySet(), entityClass.getName());
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, searchparams - {}, entityClass - {}", 
					 e.getMessage(), searchparams.keySet(), entityClass.getName());
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}

	@Override
	public <T> List<T> getAllByParams(Map<String, Object> searchparams,
			Class<T> entityClass, Integer page, Integer rp, String sortParam) throws ApplicationException {
		Iterator<Entry<String, Object>> iterator = searchparams.entrySet().iterator();
		Entry<String, Object> next = iterator.next();
		Criteria criteria = Criteria.where(next.getKey()).is(next.getValue());
		while (iterator.hasNext()) {
			next = iterator.next();
			criteria = criteria.and(next.getKey()).is(next.getValue());
		}
		try {
			if (page != null & rp != null) {
				return this.mongoOperations.find(new Query(criteria).with(new Sort(Sort.Direction.DESC, sortParam))
								.with(new PageRequest(page, rp)), entityClass);
			}
			return this.mongoOperations.find(new Query(criteria).with(new Sort(Sort.Direction.DESC, sortParam)), entityClass);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, searchparams - {}, entityClass - {}, page - {}, rp - {}", 
					 e.getMessage(), searchparams.keySet(), entityClass.getName(), page, rp);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, searchparams - {}, entityClass - {}, page - {}, rp - {}", 
					 e.getMessage(), searchparams.keySet(), entityClass.getName(), page, rp);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}

	@Override
	public <T> T updateParam(Class<T> entityClass, String id,
			String paramName, Object paramValue) throws ApplicationException {
		try {
			return this.mongoOperations.findAndModify(new Query(Criteria.where("_id")
					.is(id)), new Update().set(paramName,
					paramValue), entityClass);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, entityClass - {}, id - {}, paramName - {}, paramValue - {}", 
					 e.getMessage(), entityClass.getName(), id, paramName, paramValue);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, entityClass - {}, id - {}, paramName - {}, paramValue - {}", 
					 e.getMessage(), entityClass.getName(), id, paramName, paramValue);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}
	
	@Override
	public <T> T updateParams(Class<T> entityClass, String id, Map<String, Object> paramsToUpdate) throws ApplicationException {
		Iterator<Entry<String, Object>> iterator = paramsToUpdate.entrySet().iterator();
		Entry<String, Object> next = iterator.next();
		Update updateParams = new Update().set(next.getKey(), next.getValue());
		while (iterator.hasNext()) {
			next = iterator.next();
			updateParams = updateParams.set(next.getKey(), next.getValue());
		}
		try {
			return this.mongoOperations.findAndModify(new Query(Criteria.where("_id")
					.is(id)), updateParams, new FindAndModifyOptions().returnNew(true), entityClass);
		} catch (MongoException e) {
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}

	@Override
	public <T> void updateParamInList(Class<T> entityClass,
			List<ObjectId> idInList, String paramName, String paramValue)
			throws ApplicationException {
		try {
			this.mongoOperations.updateMulti(new Query(Criteria.where("_id")
					.in(idInList)), new Update().set(paramName, paramValue),
					entityClass);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, entityClass - {}, idInList - {}, paramName - {}, paramValue - {}", 
					 e.getMessage(), entityClass.getName(), idInList, paramName, paramValue);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, entityClass - {}, idInList - {}, paramName - {}, paramValue - {}", 
					 e.getMessage(), entityClass.getName(), idInList, paramName, paramValue);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}
	
	@Override
	public <T> void updateParamInList(Class<T> entityClass,
			List<ObjectId> idInList, String paramName, Object paramValue)
			throws ApplicationException {
		try {
			this.mongoOperations.updateMulti(new Query(Criteria.where("_id")
					.in(idInList)), new Update().set(paramName, paramValue),
					entityClass);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, entityClass - {}, idInList - {}, paramName - {}, paramValue - {}", 
					 e.getMessage(), entityClass.getName(), idInList, paramName, paramValue);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, entityClass - {}, idInList - {}, paramName - {}, paramValue - {}", 
					 e.getMessage(), entityClass.getName(), idInList, paramName, paramValue);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}
	
	@Override
	public <T> void updateAll(Class<T> entityClass, String paramName,
			String paramValue) throws ApplicationException {
		try {
			this.mongoOperations.updateMulti(new Query(Criteria.where("_id")
					.exists(true)), new Update().set(paramName, paramValue),
					entityClass);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, entityClass - {}, paramName - {}, paramValue - {}", 
					 e.getMessage(), entityClass.getName(), paramName, paramValue);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, entityClass - {}, paramName - {}, paramValue - {}", 
					 e.getMessage(), entityClass.getName(), paramName, paramValue);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}

	@Override
	public <T> void findAndUpdate(Class<T> entityClass, String searchParam,
			String searchText, String updateParam, Object updateValue)
			throws ApplicationException {
		try {
			this.mongoOperations.updateMulti(
					new Query(Criteria.where(searchParam).is(searchText)),
					new Update().set(updateParam, updateValue), entityClass);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, entityClass - {}, searchParam - {}, searchText - {}, updateParam - {}, updateValue - {}", 
					 e.getMessage(), entityClass.getName(), searchParam, searchText, updateParam, updateValue);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, entityClass - {}, searchParam - {}, searchText - {}, updateParam - {}, updateValue - {}", 
					 e.getMessage(), entityClass.getName(), searchParam, searchText, updateParam, updateValue);
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}

	@Override
	public <T> Long getCount(String paramName, String paramValue,
			Class<T> entityClass) throws ApplicationException {
		try {
			return this.mongoOperations.count(
					new Query(Criteria.where(paramName).is(paramValue)),
					entityClass);
		} catch (MongoException e) {
			LOGGER.error("Failed, message - {}, paramName - {}, paramValue - {}, entityClass - {}", 
					 e.getMessage(), paramName, paramValue, entityClass.getName());
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			LOGGER.error("Failed, message - {}, paramName - {}, paramValue - {}, entityClass - {}", 
					 e.getMessage(), paramName, paramValue, entityClass.getName());
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}
	
	@Override
	public <T> Long getCount(Map<String, Object> searchparams, Class<T> entityClass) throws ApplicationException {
		Iterator<Entry<String, Object>> iterator = searchparams.entrySet().iterator();
		Entry<String, Object> next = iterator.next();
		Criteria criteria = Criteria.where(next.getKey()).is(next.getValue());
		while (iterator.hasNext()) {
			next = iterator.next();
			criteria = criteria.and(next.getKey()).is(next.getValue());
		}
		try {
			return this.mongoOperations.count(new Query(criteria), entityClass);
		} catch (MongoException e) {
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}
	
	@Override
	public <T> Long getCount(Query query, Class<T> entityClass) throws ApplicationException {
		try {
			return this.mongoOperations.count(query, entityClass);
		} catch (MongoException e) {
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		} catch (DataAccessException e) {
			throw new DataBaseException("Database error : " + e.getMessage() , e);
		}
	}

}
