package com.mindsprint.agriconnect.dto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BuyerResponseDto {
    private Long id;
    private String name;
    private String gender;
    private LocalDate birthDate;
    private String phoneNumber;
}