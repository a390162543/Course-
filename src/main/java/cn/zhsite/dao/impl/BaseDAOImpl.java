package cn.zhsite.dao.impl;

import cn.zhsite.dao.BaseDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;


@SuppressWarnings("unchecked")
public abstract class BaseDAOImpl<T extends Serializable> implements BaseDAO<T> {

    @Resource
    protected SessionFactory sessionFactory;

    @Override
    public void insert(T t) {
        Session session = sessionFactory.getCurrentSession();
        session.save(t);
    }

    @Override
    public void insertAll(List<T> ts) {
        Session session = sessionFactory.getCurrentSession();
        int count = 0;
        for (T t : ts){
            session.save(t);
            count++;
            if (count>10000){
                session.flush();
                session.clear();
            }
        }
    }

    @Override
    public void deleteById(String id) {
        Session session = sessionFactory.getCurrentSession();
        T t = (T) session.get(getModelClass(),id);
        session.delete(t);
    }

    @Override
    public void delete(T t) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(t);
    }

    @Override
    public void update(T t) {
        Session session = sessionFactory.getCurrentSession();
        session.update(t);
    }

    @Override
    public void saveOrUpdate(T t) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(t);
    }

    @Override
    public T getById(String id) {
        Session session = sessionFactory.getCurrentSession();
        return (T) session.get(getModelClass(),Integer.parseInt(id));
    }

    @Override
    public T getById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return (T) session.get(getModelClass(),id);
    }

    public T getBy(String column,Object value){
        Session session = sessionFactory.getCurrentSession();
        T t = (T) session
                .createQuery("from "+getTableName()+" a where a."+column+"=?")
                .setParameter(0,value)
                .uniqueResult();
        return t;
    }

    public List<T> getAllBy(String column,Object value){
        Session session = sessionFactory.getCurrentSession();
        List<T> ts = (List<T>) session
                .createQuery("from "+getTableName()+" a where a."+column+"=?")
                .setParameter(0,value)
                .list();
        return ts;
    }

    public List<T> getAllBy(String[] columns,Object[] values){
        Session session = sessionFactory.getCurrentSession();
        StringBuilder stringBuilder = new StringBuilder(100);
        stringBuilder.append("from "+getTableName()+" a where ");
        for(int i=0;i<columns.length-1;i++){
            stringBuilder.append("a."+columns[i]+"=? and ");
        }
        stringBuilder.append("a."+columns[columns.length-1]+"=? ");
        Query<T> query = session.createQuery(stringBuilder.toString());
        for(int i=0;i<values.length;i++){
            query = query.setParameter(i,values[i]);
        }
        List<T> ts = query.list();
        return ts;
    }

    @Override
    public List<T> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<T> query = session.createQuery("from "+getTableName());
        return query.list();
    }

    protected abstract Class getModelClass();

    protected String getTableName(){
        return getModelClass().getName();
    }

}
