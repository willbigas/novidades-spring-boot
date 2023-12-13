package br.com.willbigas.novidadesspringboot.model;

import jakarta.validation.constraints.NotBlank;

public record FooDTO(@NotBlank(message = "{name.not.null.or.blank}") String name) {
}
