package com.delivery.yunyun.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    @OneToMany(mappedBy = "store")
    List<Menu> menuList;

    // 가게 이름
    private String storeName;

    // 가게 전화번호
    private String storeNumber;

    // 가게 주소
    private String address;

    // 가게 카테고리
    @Enumerated(EnumType.STRING)
    private Category category;

    // 점주 ID
    private Long ownerId;

}
