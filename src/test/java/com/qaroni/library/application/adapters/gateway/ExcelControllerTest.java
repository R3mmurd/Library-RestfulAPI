package com.qaroni.library.application.adapters.gateway;

import com.qaroni.library.application.adapters.repository.entity.Author;
import com.qaroni.library.application.adapters.repository.entity.Book;
import com.qaroni.library.domain.AuthorService;
import com.qaroni.library.domain.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {ExcelController.class})
@ComponentScan(basePackages = {"com.qaroni"})
@AutoConfigureMockMvc(addFilters = false)
class ExcelControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookService bookService;

    @MockBean
    AuthorService authorService;

    private Book book;

    private Author author;

    @BeforeEach
    void setUp() {
        author = Author.builder()
                .id(1L)
                .documentId("12345678")
                .fullName("Author Test")
                .books(Collections.emptyList())
                .build();

        book = Book.builder()
                .id(1L)
                .isbn("111111111111")
                .title("Book Test")
                .authors(List.of(author))
                .build();
    }

    @Test
    @DisplayName("When calling the endpoint GET /api/private/excel/export it should return status Ok")
    void exportToExcel() throws Exception {
        when(bookService.numBooks()).thenReturn(1L);
        when(authorService.findAll()).thenReturn(List.of(author));
        mockMvc.perform(get("/api/private/excel/export")).andExpect(status().isOk());
    }
}