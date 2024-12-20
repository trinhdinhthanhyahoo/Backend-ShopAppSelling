package com.example.ShopAppSelling.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class ProductDTO {

    @NotBlank(message = "Product name is required")
    @Size(min = 3, max = 350, message = "Product name must be between 3 and 350 characters")
    private String name;

    @Min(value = 0, message = "Product price must be greater than 0")
    @Max(value = 10000000, message = "Product price must be less than 100,000,000")
    @NotNull(message = "Product price is required")
    private Double price;

    private String description;

    @JsonProperty("category_id")
    private Double categoryId;

    // private List<MultipartFile> files;

    private String thumbnail;

    // public List<MultipartFile> getFiles() {
    // return files;
    // }

    // public void setFiles(List<MultipartFile> files) {
    // this.files = files;
    // }

    // public String getThumbnail() {
    // return thumbnail;
    // }

    // public void setThumbnail(String thumbnail) {
    // this.thumbnail = thumbnail;
    // }
}
