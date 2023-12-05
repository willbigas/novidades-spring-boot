package br.com.willbigas.novidadesspringboot.client;

import br.com.willbigas.novidadesspringboot.client.dto.JsonPlaceholderResponseDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class JsonPlaceHolderClient {

	private final RestClient restClient;

	private final String URI_BASE = "https://jsonplaceholder.typicode.com";

	public JsonPlaceHolderClient(RestClient restClient) {
		this.restClient = restClient;
	}

	public List<JsonPlaceholderResponseDTO> findAll() {
		return restClient.get()
				.uri(URI_BASE + "/todos/")
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.body(new ParameterizedTypeReference<>() {});
	}

	public JsonPlaceholderResponseDTO getById(Integer id) {
		return restClient.get()
				.uri(URI_BASE + "/todos/{id}", id)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.body(JsonPlaceholderResponseDTO.class);
	}

	public ResponseEntity<Void> create(JsonPlaceholderResponseDTO body) {
		return restClient.post()
				.uri(URI_BASE + "/todos/")
				.accept(MediaType.APPLICATION_JSON)
				.body(body)
				.retrieve()
				.toBodilessEntity();

	}

}
