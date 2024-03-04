package com.qaroni.library.application.adapters.gateway;

import com.qaroni.library.application.adapters.repository.dto.BookDto;
import com.qaroni.library.application.adapters.repository.dto.CreateBookDto;
import com.qaroni.library.application.adapters.repository.entity.Author;
import com.qaroni.library.application.adapters.repository.entity.Book;
import com.qaroni.library.domain.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {AuthorController.class})
@ComponentScan(basePackages = {"com.qaroni"})
@AutoConfigureMockMvc(addFilters = false)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BookService bookService;

    private Book book;

    @BeforeEach
    void setUp() {
        Author author = Author.builder()
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
    @DisplayName("When calling the endpoint GET /api/public/books it should return status Ok")
    void findAll() throws Exception {
        when(bookService.findAll()).thenReturn(List.of(book));
        mockMvc.perform(get("/api/public/books").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("When calling the endpoint GET /api/public/books/1 it should return status Ok")
    void findById() throws Exception {
        when(bookService.findById(1L)).thenReturn(book);
       mockMvc.perform(get("/api/public/books/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("When calling the endpoint GET /api/public/books/by/1 it should return status Ok")
    void findByAuthorId() throws Exception {
        when(bookService.findByAuthorId(1L)).thenReturn(List.of(book));
        mockMvc.perform(get("/api/public/books/by/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("When calling the endpoint POST /api/private/books it should return status Created")
    void save() throws Exception {
        CreateBookDto bookDto = CreateBookDto.builder()
                .isbn("111111111111")
                .title("Book Test")
                .authorIds(List.of(1L))
                .build();

        when(bookService.save(bookDto)).thenReturn(book);

        mockMvc.perform(post("/api/private/books").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "isbn": "111111111111",
                                    "title": "Book Test",
                                    "authors": [1]
                                }"""))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("When calling the endpoint PUT /api/private/books/1 it should return status Ok")
    void update() throws Exception {
        BookDto bookDto = BookDto.builder()
                .isbn("111111111111")
                .title("Book Test")
                .build();

        when(bookService.update(bookDto, 1L)).thenReturn(book);

        mockMvc.perform(put("/api/private/books/1").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "isbn": "111111111111",
                                    "title": "Book Test"
                                }"""))
                .andExpect(status().isOk());
    }
}