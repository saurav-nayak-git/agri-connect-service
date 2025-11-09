package com.mindsprint.agriconnect.mapper;

import com.mindsprint.agriconnect.dto.OrderDTO;
import com.mindsprint.agriconnect.entity.Order;
//import org.mapstruct.Mapper;
//import org.mapstruct.MappingConstants;

//@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    OrderDTO toDTO(Order order);

    Order toEntity(OrderDTO orderDTO);
}

