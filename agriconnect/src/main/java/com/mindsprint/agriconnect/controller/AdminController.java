package com.mindsprint.agriconnect.controller;

import com.mindsprint.agriconnect.dto.BuyerResponseDto;
import com.mindsprint.agriconnect.dto.SellerRequestDto;
import com.mindsprint.agriconnect.dto.SellerResponseDto;
import com.mindsprint.agriconnect.service.BuyerService;
import com.mindsprint.agriconnect.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final BuyerService buyerService;
    private final SellerService sellerService;

    @GetMapping("/buyers")
    public ResponseEntity<List<BuyerResponseDto>> getAllBuyers(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "size", defaultValue = "10") Integer pageSize
    ) {
        return ResponseEntity.ok(buyerService.getAllBuyers(pageNumber, pageSize));
    }

    @PostMapping("/sellers")
    public ResponseEntity<List<SellerResponseDto>> getAllSellers(@RequestBody SellerRequestDto sellerRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sellerService.getAllSellers());
    }
}