package br.sapiens.daos;

import java.sql.SQLException;
import java.util.Optional;

public interface CrudRepository<T,ID> {

    <S extends T> S save(S entity) throws SQLException;

    <S extends T> Iterable<T> saveAll(Iterable<S> entities) throws SQLException;

    Optional<T> findById(ID id) throws SQLException;

    Iterable<T> findAllById(Iterable<ID> ids) throws SQLException;

    void delete(T entity);

    void deleteById(ID id);

    void deleteAll(Iterable<? extends T> entities);
}
