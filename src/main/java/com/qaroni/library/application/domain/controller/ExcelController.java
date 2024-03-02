package com.qaroni.library.application.domain.controller;

import com.qaroni.library.application.domain.dto.FullAuthorDto;
import com.qaroni.library.application.domain.entity.Author;
import com.qaroni.library.application.domain.service.AuthorService;
import com.qaroni.library.application.domain.service.BookService;
import com.qaroni.library.application.domain.service.ExportExcelService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/private/excel/export")
public class ExcelController {
    @Autowired
    ExportExcelService exportExcelService;

    @Autowired
    AuthorService authorService;

    @Autowired
    BookService bookService;

    @Tag(name = "Export to Excel", description = "Exports data to an Excel file")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema()) }),
            @ApiResponse(responseCode = "403", description = "User's role is not allowed to perform this operation",
                    content = @Content)
    })
    @RolesAllowed("EXECUTIVE")
    @GetMapping()
    public void exportToExcel(HttpServletResponse servletResponse) throws IOException {
        Long numBooks = bookService.numBooks();
        List<Author> authors = authorService.findAll();
        this.exportExcelService.exportToExcel(servletResponse, numBooks, authors);
    }
}
