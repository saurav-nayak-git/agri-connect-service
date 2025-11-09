package com.mindsprint.agriconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerResponseDto {
    private Long id;
    private String name;
    private String company; // This can be modelled to a different Company model to have company info of the seller.
    private String email;
}
