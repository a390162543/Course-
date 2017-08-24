package cn.zhsite.dao;

import java.io.Serializable;
import java.util.List;

public interface BaseDAO<T extends Serializable> {

    public void insert(T t);

    public void insertAll(List<T> ts);

    public void deleteById(String id);

    public void delete(T t);

    public void update(T t);

    public void saveOrUpdate(T t);

    public T getById(String id);

    public T getById(Integer id);

    public T getBy(String column,Object value);

    public List<T> getAllBy(String column,Object value);

    public List<T> getAllBy(String[] columns,Object[] values);

    public List<T> getAll();

}
