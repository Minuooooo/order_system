package jpabook.jpashop.domain.item.service;

import jpabook.jpashop.domain.item.dto.ItemInfoResponseDto;
import jpabook.jpashop.domain.item.entity.Item;
import jpabook.jpashop.domain.item.repository.ItemRepository;
import jpabook.jpashop.exception.situation.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;

    public Page<ItemInfoResponseDto> getItemInfos(Pageable pageable) {

        Page<Item> foundItems = itemRepository.findAll(pageable);  // Page vs Slice => 총 요소의 개수, DB 쿼리

        return new PageImpl<>(foundItems.getContent().stream()  // Page 정보를 가져온 뒤  PageImpl 메소드를 사용하여 Dto 변환
                .map(ItemInfoResponseDto::from)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()),
                pageable,
                foundItems.getTotalElements()
        );
    }

    @Transactional
    public void putItem(Long itemId, int count) {
        getItem(itemId).put(count);
    }

    @Transactional
    public void cancelItem(Long itemId, int count) {
        getItem(itemId).cancel(count);
    }

    public Item getItem(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);
    }
}
