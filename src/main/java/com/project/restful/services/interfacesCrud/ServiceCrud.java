package com.project.restful.services.interfacesCrud;

import java.util.List;

public interface ServiceCrud<T,W> {
    List<T> selectAll();
    T search(Long id);
    T create(W object);
    T update(W object);
    T delete(Long id);
}
