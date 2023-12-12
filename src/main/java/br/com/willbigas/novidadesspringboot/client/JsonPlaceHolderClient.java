package br.com.willbigas.novidadesspringboot.client;

import br.com.willbigas.novidadesspringboot.client.dto.JsonPlaceholderResponseDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
				.body(new ParameterizedTypeReference<>() {
				});
	}

	public JsonPlaceholderResponseDTO getById(Integer id) {
		return restClient.get()
				.uri(URI_BASE + "/todos/{id}", id)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.body(JsonPlaceholderResponseDTO.class);
	}

//	// Threads Convencionais
//	public List<JsonPlaceholderResponseDTO> getByIds(Integer... ids) {
//
//		ExecutorService executor = Executors.newFixedThreadPool(ids.length);
//
//		List<Future<JsonPlaceholderResponseDTO>> futures = new ArrayList<>();
//
//		for (int id : ids) {
//			futures.add(executor.submit(() -> {
//				try {
//					return restClient.get()
//							.uri(URI_BASE + "/todos/{id}", id)
//							.accept(MediaType.APPLICATION_JSON)
//							.retrieve()
//							.body(JsonPlaceholderResponseDTO.class);
//				} catch (Exception e) {
//					// Handle exception
//					return null;
//				}
//			}));
//		}
//
//		List<JsonPlaceholderResponseDTO> results = new ArrayList<>();
//
//		try {
//			for (Future<JsonPlaceholderResponseDTO> future : futures) {
//				JsonPlaceholderResponseDTO response = future.get();
//				if (response != null) {
//					results.add(response);
//				}
//			}
//		} catch (Exception e) {
//			// Handle exception
//		} finally {
//			executor.shutdown();
//		}
//
//		return results;
//	}

	// Virtual Threads
	public List<JsonPlaceholderResponseDTO> getByIds(Integer... ids) {

		ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

		List<CompletableFuture<JsonPlaceholderResponseDTO>> futures = Arrays.stream(ids)
				.map(id -> CompletableFuture.supplyAsync(() -> {
					try {
						return restClient.get()
								.uri(URI_BASE + "/todos/{id}", id)
								.accept(MediaType.APPLICATION_JSON)
								.retrieve()
								.body(JsonPlaceholderResponseDTO.class);
					} catch (Exception e) {
						// Handle exception
						return null;
					}
				}, executor))
				.toList();



		try {
			// Wait for all futures to complete
			CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).get();

			// Combine the results
			return futures.stream()
					.map(CompletableFuture::join)
					.filter(Objects::nonNull)
					.toList();
		} catch (Exception e) {
			// Handle exception
			return null;
		} finally {
			executor.shutdown();
		}
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
