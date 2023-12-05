package br.com.willbigas.novidadesspringboot.repository;

import br.com.willbigas.novidadesspringboot.model.Employee;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {

	List<T> findAll();

	Optional<T> findById(String id);

	int create(T object);

	int update(T object , String id);

	int delete(String id);

}
