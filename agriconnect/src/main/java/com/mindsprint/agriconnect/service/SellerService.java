package com.mindsprint.agriconnect.service;

import com.mindsprint.agriconnect.dto.SellerRequestDto;
import com.mindsprint.agriconnect.dto.SellerResponseDto;
import com.mindsprint.agriconnect.entity.RoleType;
import com.mindsprint.agriconnect.entity.Seller;
import com.mindsprint.agriconnect.entity.User;
import com.mindsprint.agriconnect.repository.SellerRepository;
import com.mindsprint.agriconnect.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerService {

    private final SellerRepository sellerRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public List<SellerResponseDto> getAllSellers() {
        return sellerRepository.findAll()
                .stream()
                .map(seller -> modelMapper.map(seller, SellerResponseDto.class))
                .collect(Collectors.toList());
    }


    @Transactional
    public SellerResponseDto getSellerById(SellerRequestDto sellerRequestDto) {
        User user = userRepository.findById(sellerRequestDto.getUserId()).orElseThrow();

        if(sellerRepository.existsById(sellerRequestDto.getUserId())) {
            throw new IllegalArgumentException("Already seller exist");
        }

        Seller seller = Seller.builder()
                .name(sellerRequestDto.getName()) // name
                .company(sellerRequestDto.getCompany()) // company
                .user(user)
                .build();

        user.getRoles().add(RoleType.SELLER);

        return modelMapper.map(sellerRepository.save(seller), SellerResponseDto.class);
    }
}
