package com.example.videoClub.infraestructure.rest;

import com.example.videoClub.application.service.FilmService;
import com.example.videoClub.domain.dto.request.FilmRequest;
import com.example.videoClub.domain.dto.response.FilmDto;
import com.example.videoClub.domain.port.FilmController;
import com.example.videoClub.infraestructure.common.constants.ApiPathVariables;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.videoClub.infraestructure.common.constants.ExceptionMessages.*;

@RestController
@RequestMapping(ApiPathVariables.V1_ROUTE + ApiPathVariables.FILM_ROUTE)
public class FilmControllerImpl implements FilmController {

    private final FilmService filmService;

    public FilmControllerImpl(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/id")
    @Operation(
            summary = "Retrieve film by ID",
            description = "Fetches the details of a film by its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Film found successfully"),
            @ApiResponse(responseCode = "400", description = FILM_BAD_REQUEST),
            @ApiResponse(responseCode = "404", description = FILM_NOT_FOUND),
            @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR)
    })
    @Override
    public ResponseEntity<FilmRequest> getFilmById(@RequestParam(name = "id") String id) {
        FilmRequest reservedRequest = filmService.getFilmById(id);
        return ResponseEntity.ok(reservedRequest);
    }

    @Override
    @GetMapping("/name")
    @Operation(
            summary = "Retrieve film by name",
            description = "Fetches the details of a film by its name."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Film found successfully"),
            @ApiResponse(responseCode = "400", description = FILM_BAD_REQUEST),
            @ApiResponse(responseCode = "404", description = FILM_NOT_FOUND),
            @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<FilmRequest> getFilmByName(@RequestParam(name = "name") String name) {
        FilmRequest reservedRequest = filmService.getFilmByName(name);
        return ResponseEntity.ok(reservedRequest);
    }

    @PostMapping
    @Operation(
            summary = "Create a new film",
            description = "Creates a new film with the provided details."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Film created successfully"),
            @ApiResponse(responseCode = "400", description = FILM_BAD_REQUEST),
            @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR)
    })
    @Override
    public ResponseEntity<?> createFilm(@RequestBody @Valid FilmDto filmDto) {
        FilmDto createReserved = filmService.createFilm(filmDto);
        return ResponseEntity.ok(createReserved);
    }

    @Override
    @PutMapping("/id")
    @Operation(
            summary = "Update existing film",
            description = "Updates the details of an existing film."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Film updated successfully"),
            @ApiResponse(responseCode = "400", description = FILM_BAD_REQUEST),
            @ApiResponse(responseCode = "404", description = FILM_NOT_FOUND),
            @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<?> updateFilm(@RequestParam(name = "id") String id, @RequestBody @Valid FilmDto filmDto) {
        FilmDto updateFilm = filmService.updateFilm(id, filmDto);
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/id")
    @Operation(
            summary = "Delete film by ID",
            description = "Deletes a film by its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Film deleted successfully"),
            @ApiResponse(responseCode = "400", description = FILM_BAD_REQUEST),
            @ApiResponse(responseCode = "404", description = FILM_NOT_FOUND),
            @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<?> deleteFilmById(@RequestParam(name = "id") String id) {
        this.filmService.deleteFilmById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/name")
    @Operation(
            summary = "Delete film by name",
            description = "Deletes a film by its name."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Film deleted successfully"),
            @ApiResponse(responseCode = "400", description = FILM_BAD_REQUEST),
            @ApiResponse(responseCode = "404", description = FILM_NOT_FOUND),
            @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<?> deleteFilmByName(@RequestParam(name = "name") String name) {
        this.filmService.deleteFilmByName(name);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(
            summary = "List all films",
            description = "Fetches a list of all available films.",
            tags = {"Films"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Films retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Override
    public ResponseEntity<List<FilmRequest>> getAllFilms() {
        List<FilmRequest> games = this.filmService.getAllFilms();
        return ResponseEntity.ok(games);
    }
}
