package com.qaroni.library.application.adapters.gateway;

import com.qaroni.library.application.adapters.repository.dto.AuthorDto;
import com.qaroni.library.application.adapters.repository.entity.Author;
import com.qaroni.library.domain.AuthorService;
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
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    private Author author;

    @BeforeEach
    void setUp() {
        author = Author.builder()
                .id(1L)
                .documentId("12345678")
                .fullName("Author Test")
                .books(Collections.emptyList())
                .build();
    }

    @Test
    @DisplayName("When calling the endpoint GET /api/public/authors it should return status Ok")
    void findAll() throws Exception {
        when(authorService.findAll()).thenReturn(List.of(author));
        mockMvc.perform(get("/api/public/authors").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("When calling the endpoint GET /api/public/authors/1 it should return status Ok")
    void findById() throws Exception {
        when(authorService.findById(1L)).thenReturn(author);
        mockMvc.perform(get("/api/public/authors/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("When calling the endpoint POST /api/private/authors it should return status Created")
    void create() throws Exception {
        AuthorDto authorDto = AuthorDto.builder().documentId("12345678").fullName("Author Test").build();
        when(authorService.save(authorDto)).thenReturn(author);
        mockMvc.perform(post("/api/private/authors").contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "document_id": "12345678",
                            "full_name": "Author Test"
                        }""")).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("When calling the endpoint PUT /api/private/authors/1 it should return status Ok")
    void update() throws Exception {
        AuthorDto authorDto = AuthorDto.builder().documentId("12345678").fullName("Author Test").build();
        when(authorService.update(authorDto, 1L)).thenReturn(author);
        mockMvc.perform(put("/api/private/authors/1").contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "document_id": "12345678",
                            "full_name": "Author Test"
                        }""")).andExpect(status().isOk());
    }
}