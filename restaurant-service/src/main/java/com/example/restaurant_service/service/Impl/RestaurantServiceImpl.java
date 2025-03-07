package com.example.restaurant_service.service.Impl;
import com.example.restaurant_service.repository.RestaurantRepository;
import com.example.restaurant_service.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
}
