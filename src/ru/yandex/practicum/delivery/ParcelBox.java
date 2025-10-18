package ru.yandex.practicum.delivery;

import ru.yandex.practicum.delivery.model.Parcel;

import java.util.ArrayList;

public class ParcelBox<T extends Parcel> {

    private final int maxWeight;
    private final ArrayList<T> parcels = new ArrayList<>();

    public ParcelBox(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public boolean addParcel(T parcel) {
        if (validateWeight(parcel.getWeight())) {
            parcels.add(parcel);
            return true;
        } else {
            System.out.println("Превышение максимального веса коробки!");
            return false;
        }
    }

    private boolean validateWeight(int currentWeight) {
        return getWeightAllParcels() + currentWeight <= maxWeight;
    }

    private int getWeightAllParcels() {
        int resultWeight = 0;
        for (T parcel : parcels) {
            resultWeight += parcel.getWeight();
        }

        return resultWeight;
    }

    public void getAllParcels() {
        if (parcels.isEmpty()) {
            System.out.println("Посылок нет");
        } else {
            System.out.println("Содержимое коробки:");
            for (T parcel : parcels) {
                System.out.println(parcel.getDescription());
            }
        }
    }
}
