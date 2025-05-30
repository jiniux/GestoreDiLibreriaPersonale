package it.jiniux.gdlp.application.mappers;

import it.jiniux.gdlp.application.dtos.GenreDto;
import it.jiniux.gdlp.domain.Genre;
import java.util.List;
import java.util.stream.Collectors;

public class GenreMapper {
    private static GenreMapper INSTANCE;

    public static synchronized GenreMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GenreMapper();
        }
        return INSTANCE;
    }

    public Genre toDomain(GenreDto genreName) {
        return new Genre(new Genre.Name(genreName.getName()));
    }

    public GenreDto toDto(Genre genre) {
        GenreDto genreDto = new GenreDto();
        genreDto.setName(genre.getName().getValue());
        return genreDto;
    }
    
    public List<Genre> toDomainList(List<GenreDto> genreDtos) {
        if (genreDtos == null) return null;
        return genreDtos.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
    
    public List<GenreDto> toDtoList(List<Genre> genres) {
        if (genres == null) return null;
        return genres.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
