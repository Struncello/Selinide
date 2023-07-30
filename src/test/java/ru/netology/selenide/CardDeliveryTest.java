package ru.netology.Selenide;


import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {


    private String generateDate(int addDays, String pattern) {//String, потому что мы в результате получаем строку с датой
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldBeSuccessfullyCompleted() {
        open("http://localhost:9999/");
        $("[data-test-id = city] input").setValue("Мо");//когда нужно значение вводить в поле, то пишем input
        $$(".menu-item__control").findBy(text("Москва")).click();// не забыть кликнуть по городу
        String currentDate = generateDate(4, "dd.MM.yyyy"); //задаем переменную с нужными параметрами
        $("[data-test-id = date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.DELETE)); //удаляем текущую дату с сайта, кторая стоит по умолчанию
        $("[data-test-id = date] input").sendKeys(currentDate); // заполняем поле "Дата" текущей датой. Использовать setValue не можем, тк это не хардкод
        $("[data-test-id = name] input").setValue("Авраам-Ликольн Иванович");
        $("[data-test-id = phone] input").setValue("+74957778899");
        $("[data-test-id = agreement]").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));
    }

}

