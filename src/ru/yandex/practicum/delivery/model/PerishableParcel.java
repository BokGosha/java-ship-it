package ru.yandex.practicum.delivery.model;

public class PerishableParcel extends Parcel {

    private static final int COST = 3;
    private final int timeToLive;

    public PerishableParcel(String description, int weight, String deliveryAddress, int sendDay, int timeToLive) {
        super(description, weight, deliveryAddress, sendDay);
        this.timeToLive = timeToLive;
    }

    public boolean isExpired(int currentDay) {
        return sendDay + timeToLive < currentDay;
    }

    @Override
    public int calculateDeliveryCost() {
        return COST * weight;
    }
}
