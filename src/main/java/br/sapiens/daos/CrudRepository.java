package br.sapiens.daos;

import java.util.Optional;

public interface CrudRepository<T,ID> {

    <S extends T> S save(S entity);

    <S extends T> Iterable<T> saveAll(Iterable<S> entities);

    Optional<T> findById(ID id);

    Iterable<T> findAllById(Iterable<ID> ids);

    void delete(T entity);

    void deleteById(ID id);

    void deleteAll(Iterable<? extends T> entities);
}
