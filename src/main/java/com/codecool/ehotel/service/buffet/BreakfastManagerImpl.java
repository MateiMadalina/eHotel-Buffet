package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.MealDurability;
import com.codecool.ehotel.model.MealType;
import com.codecool.ehotel.service.guest.GuestService;
import com.codecool.ehotel.service.logger.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class BreakfastManagerImpl implements BreakfastManager {
    private final BuffetService buffetService;
    private final GuestService guestService;
    private final Logger logger;

    public BreakfastManagerImpl(BuffetService buffetService, GuestService guestService, Logger logger) {
        this.buffetService = buffetService;
        this.guestService = guestService;
        this.logger = logger;
    }

    @Override
    public void serve(Set<Guest> guestsForTheDay, LocalDate chosenDate) {
        List<Guest> happyGuests = new ArrayList<>();
        List<Guest> unhappyGuests = new ArrayList<>();
        LocalDateTime startDateTime = LocalDateTime.of(chosenDate.getYear(), chosenDate.getMonth(), chosenDate.getDayOfMonth(), 6, 0, 0);

        Buffet buffet = new Buffet(buffetService.generatePortions(chosenDate, guestsForTheDay.size()));

        int totalWasteCost = 0;
        int cycle = 8;
        int amountToRefill = guestsForTheDay.size() < 80 ? 1 : Math.round((float) guestsForTheDay.size() / 80);

        Map<Integer, List<Guest>> guestsPerCycle = guestService.groupGuestsIntoCycles(guestsForTheDay, cycle);

        LocalDateTime currentTime = startDateTime;

        logger.logInfo("Initial spread: " + buffet);
        logger.logInfo("Cycle 1 starts!!!\n");

        for (int i = 0; i < cycle; i++) {
            addGuestsToHappyOrUnhappyList(guestsPerCycle, i, buffet, currentTime, happyGuests, unhappyGuests);
            currentTime = currentTime.plusMinutes(30);

            logger.logInfo("After cycle " + (i + 1) + " : " + buffet);
            logger.logInfo("Discarded meals: \n");

            totalWasteCost += discardMealsAndCalculateWasteCost(i, buffet, amountToRefill, currentTime);

            if (i < 7) {
                logger.logInfo("After refill " + (i + 1) + " : " + buffet);
                logger.logInfo("----------------------------------------------------------");
                logger.logInfo("Cycle " + (i + 2) + " starts!!!\n");
            } else {
                logger.logInfo("----------------------------------------------------------");
                logger.logInfo("Remaining meals: " + buffet);
            }
        }

        logger.logInfo("Number of happy guests: " + happyGuests.size());
        logger.logInfo("Number of unhappy guests: " + unhappyGuests.size());
        logger.logInfo("Total money loss today :( " + totalWasteCost);
    }

    private void addGuestsToHappyOrUnhappyList(Map<Integer, List<Guest>> guestsPerCycle,
                                               int i,
                                               Buffet buffet,
                                               LocalDateTime currentTime,
                                               List<Guest> happyGuests,
                                               List<Guest> unhappyGuests) {
        for (Guest guest : guestsPerCycle.get(i)) {
            boolean isHappy = buffetService.consumeFreshest(buffet, guest, currentTime);
            if (isHappy) {
                happyGuests.add(guest);
            } else {
                unhappyGuests.add(guest);
            }
        }
    }

    private int discardMealsAndCalculateWasteCost(int i, Buffet buffet, int amountToRefill, LocalDateTime currentTime) {
        int totalWasteCost = 0;
        for (MealType mealType : MealType.values()) {
            if (i < 7) {
                totalWasteCost += buffetService.collectWaste(buffet.portions().get(mealType), currentTime, mealType);
                buffetService.refill(buffet, mealType, amountToRefill, currentTime);
            } else {
                if (mealType.getDurability() == MealDurability.SHORT || mealType.getDurability() == MealDurability.MEDIUM) {
                    totalWasteCost += mealType.getCost() * buffet.portions().get(mealType).size();
                    System.out.println(buffet.portions().get(mealType).size() + " " + mealType);
                    buffet.portions().get(mealType).clear();
                }
            }
        }
        return totalWasteCost;
    }
}