import buylov.CargoDimensions;
import buylov.DeliveryCalculator;
import buylov.RequestForDelivery;
import buylov.Workload;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DeliveryCalculatorTests {
    String message = "Значения должны быть одинаковыми";

    // для заполнения значениями смотри ТПР
    // для положительных тестов:
    TestValue value1 = new TestValue(new RequestForDelivery(1, CargoDimensions.SMALL, true, Workload.HIGHEST), 720);
    TestValue value2 = new TestValue(new RequestForDelivery(11, CargoDimensions.SMALL, true, Workload.INCREASED), 720);
    TestValue value3 = new TestValue(new RequestForDelivery(12, CargoDimensions.BIG, false, Workload.HIGH), 560);
    TestValue value4 = new TestValue(new RequestForDelivery(21, CargoDimensions.SMALL, true, Workload.HIGHEST), 960);
    TestValue value5 = new TestValue(new RequestForDelivery(29, CargoDimensions.BIG, false, Workload.NORMAL), 400);
    TestValue value6 = new TestValue(new RequestForDelivery(32, CargoDimensions.SMALL, false, Workload.INCREASED), 480);
    TestValue value7 = new TestValue(new RequestForDelivery(156, CargoDimensions.SMALL, false, Workload.HIGHEST), 640);
    TestValue value8 = new TestValue(new RequestForDelivery(2, CargoDimensions.BIG, false, Workload.NORMAL), 400);
    TestValue value9 = new TestValue(new RequestForDelivery(2, CargoDimensions.SMALL, true, Workload.INCREASED), 540);
    TestValue value10 = new TestValue(new RequestForDelivery(2, CargoDimensions.BIG, false, Workload.HIGH), 400);
    TestValue value11 = new TestValue(new RequestForDelivery(3, CargoDimensions.SMALL, true, Workload.INCREASED), 600);
    TestValue value12 = new TestValue(new RequestForDelivery(4, CargoDimensions.BIG, false, Workload.HIGH), 420);
    TestValue value13 = new TestValue(new RequestForDelivery(10, CargoDimensions.SMALL, true, Workload.HIGHEST), 800);
    TestValue value14 = new TestValue(new RequestForDelivery(9, CargoDimensions.BIG, false, Workload.NORMAL), 400);
    TestValue value15 = new TestValue(new RequestForDelivery(30, CargoDimensions.BIG, true, Workload.NORMAL), 700);
    TestValue value16 = new TestValue(new RequestForDelivery(30, CargoDimensions.SMALL, false, Workload.INCREASED), 400);
    TestValue value17 = new TestValue(new RequestForDelivery(30, CargoDimensions.BIG, true, Workload.HIGH), 980);
    TestValue value18 = new TestValue(new RequestForDelivery(30, CargoDimensions.SMALL, false, Workload.HIGHEST), 480);
    TestValue value19 = new TestValue(new RequestForDelivery(156, CargoDimensions.BIG, false, Workload.NORMAL), 500);
    TestValue value20 = new TestValue(new RequestForDelivery(156, CargoDimensions.BIG, false, Workload.HIGH), 700);
    // для негативных тестов:
    // исключение "Значение должно быть больше 0"
    RequestForDelivery value21 = new RequestForDelivery(-1, CargoDimensions.BIG, true, Workload.NORMAL);
    RequestForDelivery value22 = new RequestForDelivery(0, CargoDimensions.SMALL, true, Workload.HIGH);
    // исключение "Доставка хрупкого груза на расстояние более 30км не осуществляется"
    RequestForDelivery value23 = new RequestForDelivery(31, CargoDimensions.BIG, true, Workload.NORMAL);
    RequestForDelivery value24 = new RequestForDelivery(156, CargoDimensions.BIG, true, Workload.INCREASED);
    RequestForDelivery value25 = new RequestForDelivery(156, CargoDimensions.SMALL, true, Workload.HIGHEST);
    RequestForDelivery value26 = new RequestForDelivery(31, CargoDimensions.SMALL, true, Workload.INCREASED);

    @ParameterizedTest
    @DisplayName("Позитивные проверки калькулятора доставки")
    @Tags({@Tag("positive"), @Tag("smoke")})
    @MethodSource("argsCalculatorCostPositiveTest")
    void calculatorCostPositiveTest(TestValue argument) {
        double actualCost = DeliveryCalculator.calculatorCost(argument.getRequest());
        double expectedCost = argument.getCost();
        assertEquals(expectedCost, actualCost, message);
    }

    Stream<TestValue> argsCalculatorCostPositiveTest() {
        return Stream.of(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11,
                value12, value13, value14, value15, value16, value17, value18, value19, value20);
    }

    @ParameterizedTest
    @DisplayName("Негативные проверки калькулятора доставки с невалидными значениями расстояния")
    @Tag("negative")
    @MethodSource("argsCalculatorCostNegativeTest1")
    void calculatorCostNegativeTest1(RequestForDelivery argument) {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    DeliveryCalculator.calculatorCost(argument);
                });
        assertEquals(DeliveryCalculator.messageForDistance, exception.getMessage(), message);
    }

    Stream<RequestForDelivery> argsCalculatorCostNegativeTest1() {
        return Stream.of(value21, value22);
    }

    @ParameterizedTest
    @DisplayName("Негативные проверки калькулятора доставки с отказом в доставке")
    @Tag("negative")
    @MethodSource("argsCalculatorCostNegativeTest2")
    void calculatorCostNegativeTest2(RequestForDelivery argument) {
        Throwable exception = assertThrows(UnsupportedOperationException.class,
                () -> {
                    DeliveryCalculator.calculatorCost(argument);
                });
        assertEquals(DeliveryCalculator.messageNotDelivery, exception.getMessage(), message);
    }

    Stream<RequestForDelivery> argsCalculatorCostNegativeTest2() {
        return Stream.of(value23, value24, value25, value26);
    }

}
