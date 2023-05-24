package jpabook.jpashop.domain.item.dto.movie;

import com.fasterxml.jackson.annotation.JsonInclude;
import jpabook.jpashop.domain.item.dto.item.ItemInfoResponseDto;
import jpabook.jpashop.domain.item.entity.Movie;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieInfoResponseDto extends ItemInfoResponseDto {

    private SimpleMovieInfoResponseDto simpleMovie;  // TODO 데이터를 simpleMovie 으로 감싸므로 고려

    @Builder
    public MovieInfoResponseDto(int price, int stockQuantity, SimpleMovieInfoResponseDto simpleMovie) {
        super(price, stockQuantity);
        this.simpleMovie = simpleMovie;
    }

    public static MovieInfoResponseDto from(Movie movie) {
        return MovieInfoResponseDto.builder()
                .simpleMovie(SimpleMovieInfoResponseDto.from(movie))
                .price(movie.getPrice())
                .stockQuantity(movie.getStockQuantity())
                .build();
    }
}
