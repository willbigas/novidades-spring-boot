package br.com.willbigas.novidadesspringboot.repository;

import br.com.willbigas.novidadesspringboot.model.Employee;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class EmployeeRepository implements CrudRepository<Employee> {

	private final JdbcClient jdbcClient;

	public EmployeeRepository(JdbcClient jdbcClient) {
		this.jdbcClient = jdbcClient;
	}

	@Override
	public List<Employee> findAll() {
		return jdbcClient.sql("SELECT * FROM employee")
				.query(Employee.class)
				.list();
	}

	@Override
	public Optional<Employee> findById(String id) {
		return jdbcClient.sql("SELECT id,full_name FROM employee WHERE id = :id")
				.param("id", id)
				.query(Employee.class)
				.optional();
	}

	@Override
	public int create(Employee employee) {
		return jdbcClient.sql("INSERT INTO employee (full_name) values (:fullName) ")
				.param("fullName", employee.getFullName())
				.update();
	}

	@Override
	public int update(Employee employee, String id) {
		return jdbcClient.sql("update employee set full_name = ? where id = ?")
				.params(List.of(employee.getFullName(), id))
				.update();
	}

	@Override
	public int delete(String id) {
		return jdbcClient.sql("delete from employee where id = :id")
				.param("id", id)
				.update();
	}

}
