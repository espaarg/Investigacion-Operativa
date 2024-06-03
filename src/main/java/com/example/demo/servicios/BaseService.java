package com.example.demo.servicios;

import com.example.demo.entidades.Base;
import org.hibernate.query.Page;

import java.awt.print.Pageable;
import java.io.Serializable;
import java.util.List;

public interface BaseService<E extends Base, ID extends Serializable>{

    public List<E> findAll() throws Exception;
    public Page findAllPageable(Pageable pageable) throws Exception;
    public E findById(ID id) throws Exception;
    public E save(E entity) throws Exception;
    public E update(ID id, E entity) throws Exception;
    public boolean delete(ID id) throws Exception;
}
