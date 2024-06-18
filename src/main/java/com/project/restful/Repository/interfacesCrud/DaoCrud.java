package com.project.restful.Repository.interfacesCrud;

import java.util.List;
import java.util.Optional;

public interface DaoCrud<T> {
    List<T> selectAll();
    Optional<T> get(Long id);
    Optional<T> create(T object);
    Optional<T> update(T object);
    Optional<T> delete(Long id);
}
