package com.example.demo.servicios;

import com.example.demo.entidades.Base;
import org.hibernate.query.Page;

import java.awt.print.Pageable;
import java.io.Serializable;
import java.util.List;

public interface BaseService<E extends Base, ID extends Serializable>{

    List<E> findAll() throws Exception;
    Page findAllPageable(Pageable pageable) throws Exception;
    E findById(ID id) throws Exception;
    E save(E entity) throws Exception;
    E update(ID id, E entity) throws Exception;
    boolean delete(ID id) throws Exception;
}
