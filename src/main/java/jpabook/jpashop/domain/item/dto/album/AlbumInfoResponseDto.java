package jpabook.jpashop.domain.item.dto.album;

import com.fasterxml.jackson.annotation.JsonInclude;
import jpabook.jpashop.domain.item.dto.item.ItemInfoResponseDto;
import jpabook.jpashop.domain.item.entity.Album;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlbumInfoResponseDto extends ItemInfoResponseDto {

    private SimpleAlbumInfoResponseDto simpleAlbum;  // TODO 데이터를 simpleAlbum 으로 감싸므로 고려

    @Builder
    public AlbumInfoResponseDto(int price, int stockQuantity, SimpleAlbumInfoResponseDto simpleAlbum) {
        super(price, stockQuantity);
        this.simpleAlbum = simpleAlbum;
    }

    public static AlbumInfoResponseDto from(Album album) {
        return AlbumInfoResponseDto.builder()
                .simpleAlbum(SimpleAlbumInfoResponseDto.from(album))
                .price(album.getPrice())
                .stockQuantity(album.getStockQuantity())
                .build();
    }
}
