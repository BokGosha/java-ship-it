package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.delivery.ParcelBox;
import ru.yandex.practicum.delivery.model.FragileParcel;
import ru.yandex.practicum.delivery.model.PerishableParcel;
import ru.yandex.practicum.delivery.model.StandardParcel;

public class DeliveryCostTest {

    private static ParcelBox<StandardParcel> standardParcelParcelBox;

    @BeforeEach
    public void beforeEach() {
        standardParcelParcelBox = new ParcelBox<>(10);
    }

    @Test
    public void shouldCalculateCorrectCostForPerishableParcelWithWeight1AndSendDay1() {
        PerishableParcel perishableParcel = new PerishableParcel("суп", 1, "Москва", 1, 1);

        int expectedResult = 3;
        int actualResult = perishableParcel.calculateDeliveryCost();

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldCalculateCorrectCostForFragileParcelWithWeight2AndSendDay2() {
        FragileParcel fragileParcel = new FragileParcel("стакан", 2, "Москва", 2);

        int expectedResult = 8;
        int actualResult = fragileParcel.calculateDeliveryCost();

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldCalculateCorrectCostForStandardParcelWithWeight2AndSendDay2() {
        StandardParcel standardParcel = new StandardParcel("мяч", 2, "Москва", 2);

        int expectedResult = 4;
        int actualResult = standardParcel.calculateDeliveryCost();

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnFalseWhenParcelIsNotExpired() {
        PerishableParcel perishableParcel = new PerishableParcel("суп", 1, "Москва", 1, 1);

        boolean actualResult = perishableParcel.isExpired(1);

        Assertions.assertFalse(actualResult);
    }

    @Test
    public void shouldReturnFalseWhenParcelIsNotExpiredWhenSumSendDayAndTimeToLiveEqualsCurrentDay() {
        PerishableParcel perishableParcel = new PerishableParcel("суп", 1, "Москва", 1, 1);

        boolean actualResult = perishableParcel.isExpired(2);

        Assertions.assertFalse(actualResult);
    }

    @Test
    public void shouldReturnTrueWhenParcelIsExpired() {
        PerishableParcel perishableParcel = new PerishableParcel("суп", 1, "Москва", 1, 1);

        boolean actualResult = perishableParcel.isExpired(3);

        Assertions.assertTrue(actualResult);
    }

    @Test
    public void shouldAddParcelWhenWeightIsWithinLimit() {
        StandardParcel standardParcel = new StandardParcel("игрушка", 9, "Москва", 1);

        boolean actualResult = standardParcelParcelBox.addParcel(standardParcel);

        Assertions.assertTrue(actualResult);
    }

    @Test
    public void shouldAddParcelWhenWeightEqualsLimit() {
        StandardParcel standardParcel = new StandardParcel("игрушка", 10, "Москва", 1);

        boolean actualResult = standardParcelParcelBox.addParcel(standardParcel);

        Assertions.assertTrue(actualResult);
    }

    @Test
    public void shouldNotAddParcelWhenWeightExceedsLimit() {
        StandardParcel standardParcel = new StandardParcel("диван", 11, "Москва", 1);

        boolean actualResult = standardParcelParcelBox.addParcel(standardParcel);

        Assertions.assertFalse(actualResult);
    }
}
