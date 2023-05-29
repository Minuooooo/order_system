package jpabook.jpashop.domain.item.service;

import jpabook.jpashop.domain.item.dto.album.AlbumInfoResponseDto;
import jpabook.jpashop.domain.item.dto.album.SimpleAlbumInfoResponseDto;
import jpabook.jpashop.domain.item.dto.book.BookInfoResponseDto;
import jpabook.jpashop.domain.item.dto.book.SimpleBookInfoResponseDto;
import jpabook.jpashop.domain.item.dto.item.ItemInfoResponseDto;
import jpabook.jpashop.domain.item.dto.item.SimpleItemInfoResponseDto;
import jpabook.jpashop.domain.item.dto.movie.MovieInfoResponseDto;
import jpabook.jpashop.domain.item.dto.movie.SimpleMovieInfoResponseDto;
import jpabook.jpashop.domain.item.entity.*;
import jpabook.jpashop.domain.item.repository.ItemRepository;
import jpabook.jpashop.exception.situation.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static jpabook.jpashop.domain.item.entity.Category.*;
import static jpabook.jpashop.response.Response.success;
import static jpabook.jpashop.response.SuccessMessage.SUCCESS_TO_GET_ITEM_INFOS;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;

    public Page<SimpleItemInfoResponseDto> getSimpleItemInfosAdapter(String name, Pageable pageable) {  // 상속을 활용하여 Item category 별로 나누어 Page 반환

        List<Item> items = itemRepository.findAll();

        if (nameOf(name) == BOOK) {
            return getSimpleBookInfos(items, pageable);
        } else if (nameOf(name) == ALBUM) {
            return getSimpleAlbumInfos(items, pageable);
        } else if (nameOf(name) == MOVIE){
            return getSimpleMovieInfos(items, pageable);
        } else {
            throw new RuntimeException("카테고리가 존재하지 않습니다.");
        }
    }

    public ItemInfoResponseDto getItemInfoAdapter(String name, Long itemId) {  // 상속을 활용하여 Item category 별로 나누어 반환

        Item foundItem = getItem(itemId);

        if (nameOf(name) == BOOK) {
            return getBookInfo(foundItem);
        } else if (nameOf(name) == ALBUM) {
            return getAlbumInfo(foundItem);
        } else if (nameOf(name) == MOVIE){
            return getMovieInfo(foundItem);
        } else {
            throw new RuntimeException("카테고리가 존재하지 않습니다.");
        }
    }

    public Page<SimpleItemInfoResponseDto> getSimpleBookInfos(List<Item> items, Pageable pageable) {
        return new PageImpl<>(items.stream()
                .filter(item -> item instanceof Book)  // dtype: B & ClassCastException 방지
                .map(item -> SimpleBookInfoResponseDto.from((Book) item))
                .filter(Objects::nonNull)
                .collect(Collectors.toList()),
                pageable,
                itemRepository.findAll().size());
    }

    public Page<SimpleItemInfoResponseDto> getSimpleAlbumInfos(List<Item> items, Pageable pageable) {
        return new PageImpl<>(items.stream()
                .filter(item -> item instanceof Album)  // dtype: A & ClassCastException 방지
                .map(item -> SimpleAlbumInfoResponseDto.from((Album) item))
                .filter(Objects::nonNull)
                .collect(Collectors.toList()),
                pageable,
                itemRepository.findAll().size());
    }

    public Page<SimpleItemInfoResponseDto> getSimpleMovieInfos(List<Item> items, Pageable pageable) {
        return new PageImpl<>(items.stream()
                .filter(item -> item instanceof Movie)  // dtype: M & ClassCastException 방지
                .map(item -> SimpleMovieInfoResponseDto.from((Movie) item))
                .filter(Objects::nonNull)
                .collect(Collectors.toList()),
                pageable,
                itemRepository.findAll().size());
    }

    public ItemInfoResponseDto getBookInfo(Item item) {
        if (item instanceof Book) {
            return BookInfoResponseDto.from((Book) item);
        }
        throw new RuntimeException("기대 객체: Book");
    }

    public ItemInfoResponseDto getAlbumInfo(Item item) {
        if (item instanceof Album) {
            return AlbumInfoResponseDto.from((Album) item);
        }
        throw new RuntimeException("기대 객체: Album");
    }

    public ItemInfoResponseDto getMovieInfo(Item item) {
        if (item instanceof Movie) {
            return MovieInfoResponseDto.from((Movie) item);
        }
        throw new RuntimeException("기대 객체: Movie");
    }

    private Item getItem(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);
    }
}
