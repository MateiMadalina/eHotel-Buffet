package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.MealType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface BuffetService {
    Boolean consumeFreshest(Buffet buffet, Guest guest, LocalDateTime currentTime);
    void refill(Buffet buffet, MealType meal, int amount, LocalDateTime currentTime);
    int collectWaste(List<LocalDateTime> meals, LocalDateTime currentTime, MealType meal);
    Map<MealType, List<LocalDateTime>> generatePortions(LocalDate chosenDate, int numberOfPersonForTheDay);
}