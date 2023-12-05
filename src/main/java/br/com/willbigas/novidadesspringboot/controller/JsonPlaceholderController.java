package br.com.willbigas.novidadesspringboot.controller;

import br.com.willbigas.novidadesspringboot.client.JsonPlaceHolderClient;
import br.com.willbigas.novidadesspringboot.client.dto.JsonPlaceholderResponseDTO;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/json")
public class JsonPlaceholderController {

	private final JsonPlaceHolderClient jsonPlaceHolderClient;

	public JsonPlaceholderController(JsonPlaceHolderClient jsonPlaceHolderClient) {
		this.jsonPlaceHolderClient = jsonPlaceHolderClient;
	}

	@GetMapping
	public List<JsonPlaceholderResponseDTO> findAll() {
		return jsonPlaceHolderClient.findAll();
	}

	@GetMapping("/{id}")
	public JsonPlaceholderResponseDTO getById(@PathVariable Integer id) {
		return jsonPlaceHolderClient.getById(id);
	}

	@PostMapping
	public ResponseEntity<Void> create(@RequestBody JsonPlaceholderResponseDTO jsonPlaceholderResponseDTO) {
		return jsonPlaceHolderClient.create(jsonPlaceholderResponseDTO);
	}
}
