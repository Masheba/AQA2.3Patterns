package ru.netology;



import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

        DataGenerator dataGenerator = new DataGenerator();

        @BeforeEach
        void Setup() {
            open("http://localhost:9999");
        }

        @Test
        void shouldSubmitRequest() {
            Configuration.timeout = 15000;
            String firstMeetingDate = DataGenerator.forwardDate(4);
            String secondMeetingDate = DataGenerator.forwardDate(5);
            $("[placeholder='Город']").setValue(DataGenerator.randomCity());
            $("[placeholder='Дата встречи']").doubleClick().sendKeys(firstMeetingDate);
            $("[name=name]").setValue(DataGenerator.randomName());
            $("[name=phone]").setValue(DataGenerator.randomPhone());
            $(".checkbox__box").click();
            $(".button__text").click();
            $(".notification__content").shouldBe(visible)
                    .shouldHave(exactText("Встреча успешно забронирована на " +firstMeetingDate));
            $("input[placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
            $("[placeholder='Дата встречи']").doubleClick().sendKeys(secondMeetingDate);
            $(".button__text").click();
            $(withText("У вас уже запланирована встреча на другую дату. Перепланировать?")).shouldBe(visible);
            $("[data-test-id=replan-notification] button.button").click();
            $(withText("Успешно")).shouldBe(visible);
            $(".notification__content").shouldBe(visible)
                    .shouldHave(exactText("Встреча успешно запланирована на " + secondMeetingDate));
        }

        @Test
        void shouldNotSubmitWithoutCity() {
            $("[placeholder='Дата встречи']").doubleClick().sendKeys(dataGenerator.forwardDate(3));
            $("[name=name]").setValue(dataGenerator.randomName());
            $("[name=phone]").setValue(dataGenerator.randomPhone());
            $(".checkbox__box").click();
            $(".button__text").click();
            $(".input_theme_alfa-on-white.input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
        }

        @Test
        void shouldNotSubmitWithoutName() {
            $("[placeholder='Город']").setValue(dataGenerator.randomCity());
            $("[placeholder='Дата встречи']").doubleClick().sendKeys(dataGenerator.forwardDate(3));
            $("[name=phone]").setValue(dataGenerator.randomPhone());
            $(".checkbox__box").click();
            $(".button__text").click();
            $(".input_theme_alfa-on-white.input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
        }

        @Test
        void shouldNotSubmitWithIncorrectName() {
            $("[placeholder='Город']").setValue(dataGenerator.randomCity());
            $("[placeholder='Дата встречи']").doubleClick().sendKeys(dataGenerator.forwardDate(3));
            $("[name=name]").setValue("Nikolay Ageev");
            $("[name=phone]").setValue(dataGenerator.randomPhone());
            $(".checkbox__box").click();
            $(".button__text").click();
            $(".input_theme_alfa-on-white.input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        }

        @Test
        void shouldNotSubmitWithoutPhoneNumber() {
            $("[placeholder='Город']").setValue(dataGenerator.randomCity());
            $("[placeholder='Дата встречи']").doubleClick().sendKeys(dataGenerator.forwardDate(3));
            $("[name=name]").setValue(dataGenerator.randomName());
            $(".checkbox__box").click();
            $(".button__text").click();
            $(".input_theme_alfa-on-white.input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
        }

        @Test
        void shouldNotSubmitWithoutCheckbox() {
            $("[placeholder='Город']").setValue(dataGenerator.randomCity());
            $("[placeholder='Дата встречи']").doubleClick().sendKeys(dataGenerator.forwardDate(3));
            $("[name=name]").setValue(dataGenerator.randomName());
            $("[name=phone]").setValue(dataGenerator.randomPhone());
            $(".button__text").click();
            $(".input_invalid").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
        }
}

