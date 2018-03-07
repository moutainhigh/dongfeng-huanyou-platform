package com.navinfo.opentsp.dongfeng.common.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.WriteResult;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.IMongoDBService;

/**
 * Created by zhangyu on 2018/02/26.
 */
@Service(value = "mongoDBService")
public class MongoDBService extends BaseService implements IMongoDBService
{
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Override
    public <T> List<T> find(Query var1, Class<T> var2)
    {
        return mongoTemplate.find(var1, var2);
    }
    
    @Override
    public <T> List<T> find(Query var1, Class<T> var2, String var3)
    {
        return mongoTemplate.find(var1, var2, var3);
    }
    
    @Override
    public <T> T findById(Object var1, Class<T> var2)
    {
        return mongoTemplate.findById(var1, var2);
    }
    
    @Override
    public <T> T findById(Object var1, Class<T> var2, String var3)
    {
        return mongoTemplate.findById(var1, var2, var3);
    }
    
    @Override
    public <T> List<T> findAll(Class<T> var1)
    {
        return mongoTemplate.findAll(var1);
    }
    
    @Override
    public <T> List<T> findAll(Class<T> var1, String var2)
    {
        return mongoTemplate.findAll(var1, var2);
    }
    
    @Override
    public void insert(Object var1)
    {
        mongoTemplate.insert(var1);
    }
    
    @Override
    public void insert(Object var1, String var2)
    {
        mongoTemplate.insert(var1, var2);
    }
    
    @Override
    public void insert(Collection<? extends Object> var1, Class<?> var2)
    {
        mongoTemplate.insert(var1, var2);
    }
    
    @Override
    public void insert(Collection<? extends Object> var1, String var2)
    {
        mongoTemplate.insert(var1, var2);
    }
    
    @Override
    public void insertAll(Collection<? extends Object> var1)
    {
        mongoTemplate.insertAll(var1);
    }
    
    @Override
    public void save(Object var1)
    {
        mongoTemplate.save(var1);
    }
    
    @Override
    public void save(Object var1, String var2)
    {
        mongoTemplate.save(var1, var2);
    }
    
    @Override
    public WriteResult upsert(Query var1, Update var2, Class<?> var3)
    {
        return mongoTemplate.upsert(var1, var2, var3);
    }
    
    @Override
    public WriteResult upsert(Query var1, Update var2, String var3)
    {
        return mongoTemplate.upsert(var1, var2, var3);
    }
    
    @Override
    public WriteResult upsert(Query var1, Update var2, Class<?> var3, String var4)
    {
        return mongoTemplate.upsert(var1, var2, var3, var4);
    }
    
    @Override
    public WriteResult updateFirst(Query var1, Update var2, Class<?> var3)
    {
        return mongoTemplate.updateFirst(var1, var2, var3);
    }
    
    @Override
    public WriteResult updateFirst(Query var1, Update var2, String var3)
    {
        return mongoTemplate.updateFirst(var1, var2, var3);
    }
    
    @Override
    public WriteResult updateFirst(Query var1, Update var2, Class<?> var3, String var4)
    {
        return mongoTemplate.updateFirst(var1, var2, var3, var4);
    }
    
    @Override
    public WriteResult updateMulti(Query var1, Update var2, Class<?> var3)
    {
        return mongoTemplate.updateMulti(var1, var2, var3);
    }
    
    @Override
    public WriteResult updateMulti(Query var1, Update var2, String var3)
    {
        return mongoTemplate.updateMulti(var1, var2, var3);
    }
    
    @Override
    public WriteResult updateMulti(Query var1, Update var2, Class<?> var3, String var4)
    {
        return mongoTemplate.updateMulti(var1, var2, var3, var4);
    }
    
    @Override
    public DBCollection getCollection(final String collectionName)
    {
        return mongoTemplate.getCollection(collectionName);
    }
    
    @Override
    public DB getDb()
    {
        return mongoTemplate.getDb();
    }

    @Override
    public long count(Query query, Class<?> entityClass) {
        return mongoTemplate.count(query, entityClass);
    }

    @Override
    public long count(Query query, String collectionName) {
        return mongoTemplate.count(query, collectionName);
    }

    @Override
    public long count(Query query, Class<?> entityClass, String collectionName) {
        return mongoTemplate.count(query, entityClass, collectionName);
    }


}
