package com.excel.database.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    private Integer productId;

    private String productName;

    private String productDesc;

    private Double productPrice;

    private Long contact;

}
