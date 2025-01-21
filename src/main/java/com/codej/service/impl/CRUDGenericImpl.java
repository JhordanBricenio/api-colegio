package com.codej.service.impl;

import com.codej.exceptions.ResourceNotFoundException;
import com.codej.repository.IGenericRepository;
import com.codej.service.ICRUDService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Method;
import java.util.List;

import static com.codej.constants.ErrorMessageConstants.NOT_RESULTS_FOUND_FOR_WITH_ID;

public abstract class CRUDGenericImpl<T,ID> implements ICRUDService<T, ID> {

    protected  abstract IGenericRepository<T, ID> getRepository();

    @Override
    public T save(T t) throws Exception {
        return getRepository().save(t);
    }

    @Override
    public T update(T t, ID id) throws Exception {
        getRepository().findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(NOT_RESULTS_FOUND_FOR_WITH_ID+ id));

        //Java Reflection
        Class<?> clazz = t.getClass();
        String className = clazz.getSimpleName();
        String methodName= "setId"+ className;
        Method setIdMethod = clazz.getMethod(methodName, id.getClass());

        setIdMethod.invoke(t, id);
        return getRepository().save(t);
    }

    @Override
    public List<T> findAll() throws Exception {
        return getRepository().findAll();
    }

    @Override
    public T findById(ID id) throws Exception {
      return   getRepository().findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(NOT_RESULTS_FOUND_FOR_WITH_ID+ id));
    }

    @Override
    public void delete(ID id) throws Exception {
         getRepository().findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(NOT_RESULTS_FOUND_FOR_WITH_ID+ id));

        getRepository().deleteById(id);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }
}
