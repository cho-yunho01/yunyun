package com.delivery.yunyun.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long menuId;

    // 음식 이름
    private String name;

    // 음식 가격
    private Integer price;

    // 음실 설명
    private String introduction;

    // 가맹점 id
    @ManyToOne
    private Store store;

}
