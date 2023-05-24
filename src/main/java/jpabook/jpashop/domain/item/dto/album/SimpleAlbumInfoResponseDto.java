package jpabook.jpashop.domain.item.dto.album;

import com.fasterxml.jackson.annotation.JsonInclude;
import jpabook.jpashop.domain.item.dto.item.SimpleItemInfoResponseDto;
import jpabook.jpashop.domain.item.entity.Album;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimpleAlbumInfoResponseDto extends SimpleItemInfoResponseDto {
    private String artist;

    @Builder
    public SimpleAlbumInfoResponseDto(Long id, String name, String artist) {
        super(id, name);
        this.artist = artist;
    }

    public static SimpleAlbumInfoResponseDto from(Album album) {
        return SimpleAlbumInfoResponseDto.builder()
                .id(album.getId())
                .name(album.getName())
                .artist(album.getArtist())
                .build();
    }
}
