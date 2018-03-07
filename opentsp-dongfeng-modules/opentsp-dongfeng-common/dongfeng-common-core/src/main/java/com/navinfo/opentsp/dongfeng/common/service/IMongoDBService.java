package com.navinfo.opentsp.dongfeng.common.service;

import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.WriteResult;

/**
 * Created by zhangyu on 2018/02/26.
 */
public interface IMongoDBService
{
    
    public <T> List<T> find(Query var1, Class<T> var2);
    
    public <T> List<T> find(Query var1, Class<T> var2, String var3);
    
    public <T> T findById(Object var1, Class<T> var2);
    
    public <T> T findById(Object var1, Class<T> var2, String var3);
    
    public <T> List<T> findAll(Class<T> var1);
    
    public <T> List<T> findAll(Class<T> var1, String var2);
    
    public void insert(Object var1);
    
    public void insert(Object var1, String var2);
    
    public void insert(Collection<? extends Object> var1, Class<?> var2);
    
    public void insert(Collection<? extends Object> var1, String var2);
    
    public void insertAll(Collection<? extends Object> var1);
    
    public void save(Object var1);
    
    public void save(Object var1, String var2);
    
    public WriteResult upsert(Query var1, Update var2, Class<?> var3);
    
    public WriteResult upsert(Query var1, Update var2, String var3);
    
    public WriteResult upsert(Query var1, Update var2, Class<?> var3, String var4);
    
    public WriteResult updateFirst(Query var1, Update var2, Class<?> var3);
    
    public WriteResult updateFirst(Query var1, Update var2, String var3);
    
    public WriteResult updateFirst(Query var1, Update var2, Class<?> var3, String var4);
    
    public WriteResult updateMulti(Query var1, Update var2, Class<?> var3);
    
    public WriteResult updateMulti(Query var1, Update var2, String var3);
    
    public WriteResult updateMulti(Query var1, Update var2, Class<?> var3, String var4);
    
    public DBCollection getCollection(final String collectionName);
    
    public DB getDb();

    public long count(Query query, Class<?> entityClass);

    public long count(Query query, String collectionName);

    public long count(Query query, Class<?> entityClass, String collectionName);

}
