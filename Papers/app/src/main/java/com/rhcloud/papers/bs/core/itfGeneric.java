package com.rhcloud.papers.bs.core;

import com.rhcloud.papers.excecoes.excPassaErro;

import java.util.List;

/**
 * Created by Rodolfo on 26/11/16.
 */

public interface itfGeneric<T>{
    String create(T entity) throws excPassaErro;
    String update(T entity) throws excPassaErro;
    String delete(Integer id) throws excPassaErro;
    T findByID(Integer id) throws excPassaErro;
    List<T> findAll() throws excPassaErro;
}
