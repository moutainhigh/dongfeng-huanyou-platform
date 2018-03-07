package com.navinfo.opentsp.dongfeng.common.dao;

import com.navinfo.opentsp.dongfeng.common.dao.base.BaseDao;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.Query;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用dao
 *
 * Created by zhangy on 2016/04/08.
 */
@Repository
public class CommonDao<T> extends BaseDao
{
    /**
     * 删除
     */
    public void delete(T t)
            throws RuntimeException
    {
        if (null == t)
        {
            throw new RuntimeException("请求删除的对象为空!");
        }
        try
        {
            if (em.contains(t))
            {
                em.remove(t);
            }
            else
            {
                Object id = em.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(t);
                em.remove(em.find(t.getClass(), id));
            }
            this.flush();
        }
        catch (Exception e)
        {
            throw new RuntimeException("删除对象时出错!");
        }
    }

    /**
     * 批量删除 传入集合
     */
    public void batchDelete(Collection<T> ts)
    {
        for (T t : ts)
        {
            delete(t);
        }
        // this.flush();
    }

    /**
     * sql 取得总数
     *
     * @param sql
     * @return
     */
    public long getSqlCount(String sql)
    {
        Object O = em.createNativeQuery(JpaUtil.getStrCountSql(removeOrders(sql))).getSingleResult();
        return Long.valueOf(O.toString());
    }

    /**
     * hql 取得总数
     *
     * @param hql
     * @return
     */
    public long getHqlCount(String hql)
    {
        Object O = em.createQuery(JpaUtil.getStrCountSql(removeOrders(hql))).getSingleResult();
        return Long.valueOf(O.toString());
    }

    /**
     * 保存
     */
    public T save(T t)
    {
        try
        {
            em.persist(t);
            this.flush();
            return t;
        }
        catch (Exception e)
        {
            throw new RuntimeException("更新新建失败：" + e.getMessage());
        }
    }

    /**
     * 批量保存
     */
    public Collection<T> batchSave(Collection<T> ts, boolean isCommit)
    {
        Collection<T> collection = new HashSet<T>();
        for (T t : ts)
        {
            collection.add(save(t));
        }
        // this.flush();
        return collection;
    }

    /**
     * 若数据库中已有此记录，置为托管状态并刷新实体信息
     */
    public T update(T t)
            throws RuntimeException
    {
        try
        {
            t = em.contains(t) ? t : em.merge(t);
            this.flush();
        }
        catch (Exception e)
        {
            throw new RuntimeException("更新失败！");
        }
        return t;
    }

    /**
     * 批次更新
     */
    public Collection<T> batchUpdate(Collection<T> ts)
    {
        Collection<T> collection = new HashSet<T>();
        for (T t : ts)
        {
            collection.add(update(t));
        }
        // this.flush();
        return collection;
    }

    /**
     * 查询一条数
     *
     * @param t
     * @return
     */
    public T findOne(T t)
    {
        Object id = em.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(t);
        return (T)em.find(t.getClass(), id);

    }

    /**
     * sql查询不带分页
     *
     * @param sqlId
     * @param entityClass
     * @return
     */
    public List<T> sqlFind(String sqlId, T entityClass)
    {
        String sql = sqlLaberUtil.getSqlLabel(sqlId, entityClass);
        Query querySql = em.createNativeQuery(sql, entityClass.getClass());

        return querySql.getResultList();
    }

    /**
     * sql查询对象（自定义返回实体，用于多表查询）
     *
     * @param sqlId
     * @param paramEntity
     * @param pojoClass 返回类型
     * @return
     */
    public Object sqlFindObject(String sqlId, T paramEntity, Class<?> pojoClass)
    {
        String sql = sqlLaberUtil.getSqlLabel(sqlId, paramEntity);
        Query query = em.createNativeQuery(sql);
        SQLQuery sqlQuery = query.unwrap(SQLQuery.class);
        sqlQuery.setResultTransformer(Transformers.aliasToBean(pojoClass));
        List<T> list = query.getResultList();
        if (StringUtil.isEmpty(list))
        {
            return null;
        }
        else
        {
            // 返回第一条
            return list.get(0);
        }
    }

    /**
     * sql查询字段（返回单个字段）
     *
     * @param sqlId sqlId
     * @param paramEntity paramEntity
     * @return List 返回类型
     */
    public List sqlFindField(String sqlId, T paramEntity)
    {
        String sql = sqlLaberUtil.getSqlLabel(sqlId, paramEntity);
        Query query = em.createNativeQuery(sql);
        List rows = query.getResultList();
        return rows;
    }

    /**
     * sql查询不带分页（自定义返回实体，用于多表查询）
     *
     * @param sqlId
     * @param paramEntity
     * @param pojoClass 返回类型
     * @return
     */
    public List<T> sqlFind(String sqlId, T paramEntity, Class<?> pojoClass)
    {
        String sql = sqlLaberUtil.getSqlLabel(sqlId, paramEntity);
        Query query = em.createNativeQuery(sql);
        SQLQuery sqlQuery = query.unwrap(SQLQuery.class);
        sqlQuery.setResultTransformer(Transformers.aliasToBean(pojoClass));
        return query.getResultList();
    }

    /**
     * sql查询不带分页（自定义返回实体，用于多表查询）
     *
     * @param pojoClass 返回类型
     * @return
     */
    public List<T> bySqlFind(String sql, Class<?> pojoClass)
    {
        Query query = em.createNativeQuery(sql);
        SQLQuery sqlQuery = query.unwrap(SQLQuery.class);
        sqlQuery.setResultTransformer(Transformers.aliasToBean(pojoClass));
        return query.getResultList();
    }

    /**
     * sql查询不带分页（自定义返回实体，用于多表查询）
     *
     * @param sqlId
     * @param pojoClass 返回类型
     * @return
     */
    public List<T> sqlFind(String sqlId, Class<?> pojoClass)
    {
        String sql = sqlLaberUtil.getSqlLabel(sqlId);
        Query query = em.createNativeQuery(sql);
        SQLQuery sqlQuery = query.unwrap(SQLQuery.class);
        sqlQuery.setResultTransformer(Transformers.aliasToBean(pojoClass));
        return query.getResultList();
    }

    /**
     * hql查询不带分页
     *
     * @param hqlId
     * @param entityClass
     * @return
     */
    public List<T> hqlFind(String hqlId, T entityClass)
    {
        String sql = sqlLaberUtil.getSqlLabel(hqlId, entityClass);
        Query querySql = em.createQuery(sql, entityClass.getClass());
        return querySql.getResultList();
    }

    /**
     * sql查询带分页
     *
     * @param sqlId
     * @param entityClass
     * @param pageIndex 页数
     * @param pageSize 每页显示条数
     * @return
     */
    public PagingInfo<T> sqlPagelFind(String sqlId, T entityClass, int pageIndex, int pageSize)
    {
        PagingInfo<T> pageList = new PagingInfo<T>();

        String sql = sqlLaberUtil.getSqlLabel(sqlId, entityClass);
        Query querySql = em.createNativeQuery(sql, entityClass.getClass());

        // 设置分页
        JpaUtil.setQueryPage(querySql, pageIndex, pageSize);

        List<T> list = querySql.getResultList();
        pageList.setList(list);

        // 总页数
        long count = getSqlCount(sql);

        // 总条数
        pageList.setTotal(count);

        // 总页数
        pageList.setPage_total((count + pageSize - 1) / pageSize);
        return pageList;
    }

    /**
     * sql查询带分页（自定义返回实体，用于多表查询）
     *
     * @param sqlId
     * @param paramEntity
     * @param pageIndex 页数
     * @param pageSize 每页显示条数
     * @param pojoClass 返回类型
     * @return
     */
    public PagingInfo<T> sqlPagelFind(String sqlId, T paramEntity, int pageIndex, int pageSize, Class<?> pojoClass)
    {
        PagingInfo<T> pageList = new PagingInfo<T>();

        String sql = sqlLaberUtil.getSqlLabel(sqlId, paramEntity);
        Query querySql = em.createNativeQuery(sql);
        // 设置分页
        JpaUtil.setQueryPage(querySql, pageIndex, pageSize);

        SQLQuery sqlQuery = querySql.unwrap(SQLQuery.class);
        sqlQuery.setResultTransformer(Transformers.aliasToBean(pojoClass));

        List<T> list = querySql.getResultList();
        pageList.setList(list);

        // 总页数
        long count = getSqlCount(sql);

        // 总条数
        pageList.setTotal(count);

        // 总页数
        pageList.setPage_total((count + pageSize - 1) / pageSize);
        return pageList;
    }
    
    public PagingInfo<T> sqlPageFind(String sqlId , T paramEntity , int pageIndex , int pageSize , String countSql , Class<?> pojoClass) {

        PagingInfo<T> pageList = new PagingInfo<T>();
        String sql = sqlLaberUtil.getSqlLabel(sqlId, paramEntity);
        Query querySql = em.createNativeQuery(sql);
        // 设置分页
        JpaUtil.setQueryPage(querySql, pageIndex, pageSize);
        
        SQLQuery sqlQuery = querySql.unwrap(SQLQuery.class);
        
        sqlQuery.setResultTransformer(Transformers.aliasToBean(pojoClass));
        List<T> list = querySql.getResultList();

        pageList.setList(list);

        Long countNum = null;

        if (!StringUtils.isEmpty(countSql)) {
            String count = sqlLaberUtil.getSqlLabel(countSql, paramEntity);
        	// 总页数
            countNum = getSqlCount(count);
        } else {
        	countNum = getSqlCount(sql);
        }

        // 总条数
        pageList.setTotal(countNum);
        // 总页数
        pageList.setPage_total((countNum + pageSize - 1) / pageSize);
        return pageList;
    }

    /**
     * hql查询带分页
     *
     * @param hqlId
     * @param entityClass
     * @param pageIndex 页数
     * @param pageSize 每页显示条数
     * @return
     */
    public PagingInfo<T> hqlPagelFind(String hqlId, T entityClass, int pageIndex, int pageSize)
    {
        PagingInfo<T> pageList = new PagingInfo<T>();

        String sql = sqlLaberUtil.getSqlLabel(hqlId, entityClass);
        Query queryHql = em.createQuery(sql, entityClass.getClass());

        // 设置分页
        JpaUtil.setQueryPage(queryHql, pageIndex, pageSize);

        List<T> list = queryHql.getResultList();
        pageList.setList(list);

        // 总页数
        long count = getHqlCount(sql);

        // 总条数
        pageList.setTotal(count);

        // 总页数
        pageList.setPage_total((count + pageSize - 1) / pageSize);
        return pageList;
    }

    /**
     * 同步jpa容器和数据库
     */
    public void flush()
    {
        em.flush();
    }

    /**
     * 刷新缓存
     */
    public void clear()
    {
        em.clear();
    }

    /**
     * 同步jpa容器和数据库
     */
    public void commit()
    {
        em.getTransaction().commit();
    }

    /**
     * 执行sql(增删改操作)
     */
    public void executeUpdate(String sqlId, T entityClass)
    {
        String sql = sqlLaberUtil.getSqlLabel(sqlId, entityClass);
        em.createNativeQuery(sql).executeUpdate();
        this.flush();
    }

    /**
     * 去除sql的orderBy子句。
     *
     * @param qlString
     * @return
     */
    private static String removeOrders(String qlString)
    {
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(qlString);
        StringBuffer sb = new StringBuffer();
        while (m.find())
        {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return sb.toString();
    }
}
