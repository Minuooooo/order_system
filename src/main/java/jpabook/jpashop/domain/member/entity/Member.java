package jpabook.jpashop.domain.member.entity;

import jpabook.jpashop.domain.EntityDateInfo;
import jpabook.jpashop.domain.chatmessage.entity.ChatMessage;
import jpabook.jpashop.domain.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Member extends EntityDateInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    @OneToMany(mappedBy = "customer", cascade = ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();
    @OneToMany(mappedBy = "sender", cascade = ALL, orphanRemoval = true)
    private List<ChatMessage> chatMessages = new ArrayList<>();

    private String username;
    private String password;
    private String name;
    @Embedded
    private Address address;
    private String profileImageUrl;
    @Enumerated(EnumType.STRING)
    private Authority authority;

    public String changeProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
        return this.profileImageUrl;
    }

    public void editMember(String name, Address address) {
        this.name = name;
        this.address = address;
    }
}
