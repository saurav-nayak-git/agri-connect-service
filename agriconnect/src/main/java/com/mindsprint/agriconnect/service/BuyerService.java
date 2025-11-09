package com.mindsprint.agriconnect.service;

import com.mindsprint.agriconnect.dto.BuyerResponseDto;
import com.mindsprint.agriconnect.entity.Buyer;
import com.mindsprint.agriconnect.repository.BuyerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuyerService {
    private final BuyerRepository buyerRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public BuyerResponseDto getBuyerById(Long buyerId) {
        Buyer buyer = buyerRepository.findById(buyerId).orElseThrow(() -> new EntityNotFoundException("Buyer Not " +
                "Found with id: " + buyerId));
        return modelMapper.map(buyer, BuyerResponseDto.class);
    }

    public List<BuyerResponseDto> getAllBuyers(Integer pageNumber, Integer pageSize) {
        return buyerRepository.findAllBuyers((Pageable) PageRequest.of(pageNumber, pageSize))
                .stream()
                .map(buyer -> modelMapper.map(buyer, BuyerResponseDto.class))
                .collect(Collectors.toList());
    }
}
