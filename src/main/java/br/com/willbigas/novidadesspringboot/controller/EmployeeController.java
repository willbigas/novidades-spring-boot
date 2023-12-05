package br.com.willbigas.novidadesspringboot.controller;

import br.com.willbigas.novidadesspringboot.model.Employee;
import br.com.willbigas.novidadesspringboot.repository.EmployeeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	private final EmployeeRepository employeeRepository;

	public EmployeeController(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@GetMapping
    public ResponseEntity<List<Employee>> findAll() {
		return ResponseEntity.ok(this.employeeRepository.findAll());
    }

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Employee>> findById(@PathVariable String id) {
		return ResponseEntity.ok(this.employeeRepository.findById(id));
	}

	@PostMapping
    public ResponseEntity<Integer> create(@RequestParam String nome) {
		Employee employee = new Employee();
		employee.setFullName(nome);
		return ResponseEntity.ok(this.employeeRepository.create(employee));
    }

	@PutMapping("/{id}")
    public ResponseEntity<Integer> update(@RequestParam String nome , @PathVariable String id) {
		Employee employee = new Employee();
		employee.setFullName(nome);
		return ResponseEntity.ok(this.employeeRepository.update(employee , id));
    }

	@DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable String id) {
		return ResponseEntity.ok(this.employeeRepository.delete(id));
    }


}
