package jpabook.jpashop.domain.item.dto.movie;

import com.fasterxml.jackson.annotation.JsonInclude;
import jpabook.jpashop.domain.item.dto.item.ItemInfoResponseDto;
import jpabook.jpashop.domain.item.dto.item.SimpleItemInfoResponseDto;
import jpabook.jpashop.domain.item.entity.Movie;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimpleMovieInfoResponseDto extends SimpleItemInfoResponseDto {
    private String director;

    @Builder
    public SimpleMovieInfoResponseDto(Long id, String name, String director) {
        super(id, name);
        this.director = director;
    }

    public static SimpleMovieInfoResponseDto from(Movie movie) {
        return SimpleMovieInfoResponseDto.builder()
                .id(movie.getId())
                .name(movie.getName())
                .director(movie.getDirector())
                .build();
    }
}
