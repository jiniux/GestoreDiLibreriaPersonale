package it.jiniux.gdlp.application.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GenreDto {
    public GenreDto(String name) {
        this.name = name;
    }

    private String name;
}
