package com.example.ShopAppSelling.Responses;

import com.example.ShopAppSelling.Models.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ProductResponse extends BaseEntity {

    private String name;

    private Double price;

    private String description;

    @JsonProperty("category_id")
    private Double categoryId;

    private String thumbnail;
}
