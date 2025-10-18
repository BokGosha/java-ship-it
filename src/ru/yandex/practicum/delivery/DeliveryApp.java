package ru.yandex.practicum.delivery;

import ru.yandex.practicum.delivery.model.FragileParcel;
import ru.yandex.practicum.delivery.model.Parcel;
import ru.yandex.practicum.delivery.model.PerishableParcel;
import ru.yandex.practicum.delivery.model.StandardParcel;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Parcel> allParcels = new ArrayList<>();
    private static final List<Trackable> allTrackables = new ArrayList<>();
    private static final ParcelBox<PerishableParcel> perishableParcelBox = new ParcelBox<>(10);
    private static final ParcelBox<FragileParcel> fragileParcelBox = new ParcelBox<>(10);
    private static final ParcelBox<StandardParcel> standardParcelParcelBox = new ParcelBox<>(10);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addParcel();
                    break;
                case 2:
                    sendParcels();
                    break;
                case 3:
                    calculateCosts();
                    break;
                case 4:
                    showReportStatus();
                    break;
                case 5:
                    showParcelBox();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("4 — Трекинг посылок");
        System.out.println("5 — Показать содержимое коробки");
        System.out.println("0 — Завершить");
    }

    private static void addParcel() {
        System.out.println("Какого типа посылку добавить?");
        showTypesOfParcel();
        int type = scanner.nextInt();

        if (type != 1 && type != 2 && type != 3) {
            System.out.println("Неверный тип посылки");
            return;
        }

        scanner.nextLine();

        System.out.println("Добавьте описание посылки:");
        String description = scanner.nextLine();

        System.out.println("Укажите вес посылки:");
        int weight = scanner.nextInt();

        scanner.nextLine();

        System.out.println("Укажите день отправки посылки:");
        int sendDay = scanner.nextInt();

        scanner.nextLine();

        System.out.println("Укажите адрес доставки:");
        String deliveryAddress = scanner.nextLine();

        Parcel parcel = null;
        switch (type) {
            case 1:
                parcel = new StandardParcel(description, weight, deliveryAddress, sendDay);
                if (!standardParcelParcelBox.addParcel((StandardParcel) parcel)) {
                    return;
                }

                break;
            case 2:
                System.out.println("Укажите срок в днях, за который посылка не испортится:");
                int timeToLive = scanner.nextInt();

                scanner.nextLine();

                parcel = new PerishableParcel(description, weight, deliveryAddress, sendDay, timeToLive);
                if (!perishableParcelBox.addParcel((PerishableParcel) parcel)) {
                    return;
                }

                break;
            case 3:
                parcel = new FragileParcel(description, weight, deliveryAddress, sendDay);
                if (!fragileParcelBox.addParcel((FragileParcel) parcel)) {
                    return;
                }
                allTrackables.add((Trackable) parcel);

                break;
        }

        allParcels.add(parcel);
    }

    private static void showTypesOfParcel() {
        System.out.println("1 — Стандартная");
        System.out.println("2 — Скоропортящаяся");
        System.out.println("3 — Хрупкая");
    }

    private static void sendParcels() {
        if (allParcels.isEmpty()) {
            System.out.println("Посылок нет");
        } else {
            for (Parcel parcel : allParcels) {
                parcel.packageItem();
                parcel.deliver();
            }
        }
    }

    private static void calculateCosts() {
        if (allParcels.isEmpty()) {
            System.out.println("Посылок нет");
        } else {
            int resultCost = 0;
            for (Parcel parcel : allParcels) {
                resultCost += parcel.calculateDeliveryCost();
            }

            System.out.println("Общая стоимость всех доставок: " + resultCost);
        }
    }

    private static void showReportStatus() {
        if (allTrackables.isEmpty()) {
            System.out.println("Посылок, поддерживающих трекинг, нет");
        } else {
            for (Trackable trackable : allTrackables) {
                System.out.println("Введите местоположение посылки:");
                String newLocation = scanner.nextLine();

                trackable.reportStatus(newLocation);
            }
        }
    }

    private static void showParcelBox() {
        System.out.println("Укажите какого типа коробки показать содержимое:");
        showTypesOfParcel();
        int type = scanner.nextInt();

        scanner.nextLine();

        switch (type) {
            case 1:
                standardParcelParcelBox.getAllParcels();
                break;
            case 2:
                perishableParcelBox.getAllParcels();
                break;
            case 3:
                fragileParcelBox.getAllParcels();
                break;
            default:
                System.out.println("Неверный тип коробки");
        }
    }
}
