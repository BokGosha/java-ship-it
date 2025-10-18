package ru.yandex.practicum.delivery.model;

import ru.yandex.practicum.delivery.Trackable;

public class FragileParcel extends Parcel implements Trackable {

    private static final int COST = 4;

    public FragileParcel(String description, int weight, String deliveryAddress, int sendDay) {
        super(description, weight, deliveryAddress, sendDay);
    }

    @Override
    public void packageItem() {
        System.out.println("Посылка " + description + " обёрнута в защитную плёнку");
        super.packageItem();
    }

    @Override
    public int calculateDeliveryCost() {
        return COST * weight;
    }

    @Override
    public void reportStatus(String newLocation) {
        System.out.println("Хрупкая посылка " + description + " изменила местоположение на " + newLocation);
    }
}
