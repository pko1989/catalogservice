package com.polarbookshop.catalogservice;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.polarbookshop.catalogservice.domain.Book;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Consumer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CatalogServiceApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void whenPostRequestThenBookCreated() {
		final var expectedBook = new Book("1231231231", "Title", "Author", 9.90);

		webTestClient
				.post()
				.uri("/books")
				.bodyValue(expectedBook)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(Book.class).value(bookMatcher(expectedBook));
	}

	private Consumer<Book> bookMatcher(final Book expectedBook) {
		return actualBook -> {
			assertThat(actualBook).isNotNull();
			assertThat(actualBook.isbn()).isEqualTo(expectedBook.isbn());
		};
	}

}
