package com.msr.cg.afrimeta.utils;

import java.util.List;

public interface AfrimetaCrudInterface <T>{
    List<T> findAll();
    T findById(Long id);
    T save(T t);
    T update(T t, Long id);
    void deleteById(Long id);
    void delete(T t);
}
