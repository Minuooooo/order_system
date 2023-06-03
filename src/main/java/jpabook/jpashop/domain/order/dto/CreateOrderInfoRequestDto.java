package jpabook.jpashop.domain.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jpabook.jpashop.domain.member.entity.Address;
import jpabook.jpashop.domain.member.entity.Member;
import jpabook.jpashop.domain.order.entity.DeliveryStatus;
import jpabook.jpashop.domain.order.entity.Order;
import jpabook.jpashop.domain.order.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderInfoRequestDto {

    @NotBlank(message = "시,도,군을 입력해주세요.")
    @Schema(description = "시,도,군", defaultValue = "경기도")
    private String city;

    @NotBlank(message = "도로명을 입력해주세요.")
    @Schema(description = "도로명", defaultValue = "고양이 네로")
    private String street;

    @NotBlank(message = "우편번호를 입력해주세요.")
    @Pattern(regexp = "^\\d{5}$", message = "우편번호는 5자리이어야 합니다.") // 우편번호 형식
    @Schema(description = "우편번호", defaultValue = "11111")
    private String zipcode;

    public Order toEntity(Member customer) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return Order.builder()
                .customer(customer)
                .address(Address.getAddress(this.city, this.street, this.zipcode))
                .date(LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter))
                .orderStatus(OrderStatus.WAIT)
                .deliveryStatus(DeliveryStatus.READY)
                .build();
    }
}
