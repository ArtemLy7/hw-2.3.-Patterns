import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class testCardDeliveryDate {
    private String changeDay;

    @BeforeEach
    public void setUp() {
        LocalDate today = LocalDate.now();
        LocalDate newDay = today.plusDays(4);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        changeDay = newDay.format(formatter);
    }

   @Test
   @DisplayName("Проверка заполнение полей валидными данными")
    void checkVerificationFormWithCorrectInput() {
        open("http://localhost:9999/");
        User userActual = AvtoGenerator.dataUser();

        $("[data-test-id=city] input").setValue(userActual.getCity());
        $("[data-test-id=name] input").setValue(userActual.getFullName());
        $("[data-test-id=phone] input").setValue(userActual.getPhone());
        $("[data-test-id=agreement]").click();
        $$("[class=button__text]").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification]").waitUntil(Condition.visible, 3000);
        $("[class=notification__title]").should(Condition.exactText("Успешно!"));
    }

    @Test
    @DisplayName("Ввод другой даты, проверка появления окна перепланирования")
    void replaningDate() {
        open("http://localhost:9999/");
        User userActual = AvtoGenerator.dataUser();

        $("[data-test-id=city] input").setValue(userActual.getCity());
        $("[data-test-id=name] input").setValue(userActual.getFullName());
        $("[data-test-id=phone] input").setValue(userActual.getPhone());
        $("[data-test-id=agreement]").click();
        $$("[class=button__text]").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification]").waitUntil(Condition.visible, 3000);
        $("[class=notification__title]").should(Condition.exactText("Успешно!"));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue(String.valueOf(changeDay));
        $$("[class=button__text]").find(exactText("Запланировать")).click();
        $("[data-test-id=replan-notification]").waitUntil(Condition.visible, 3000);
        $$("[class=button__text]").find(exactText("Перепланировать")).click();
        $(withText("Успешно!")).waitUntil(Condition.visible, 3000);
    }
   }
