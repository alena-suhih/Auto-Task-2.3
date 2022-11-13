import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class SubmitFormTests {

    private String generateNeedDate(int plusDays, String pattern) {
        return LocalDate.now().plusDays(plusDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @BeforeEach
    void openBrowser() {
        open("http://localhost:9999");
    }


    @Test
    void shouldSuccessfulSubmitForm() {
        $("[data-test-id=\"city\"] input").sendKeys("Саратов");
        String currentDate = generateNeedDate(5, "dd.MM.yyyy");
        $("[data-test-id=\"date\"] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.DELETE));
        $("[data-test-id=\"date\"] input").sendKeys(currentDate);
        $("[data-test-id=\"name\"] input").sendKeys("Иванов-Петров Иван");
        $("[data-test-id=\"phone\"] input").sendKeys("+79998877654");
        $("[data-test-id=\"agreement\"] span").click();
        $("button.button").click();
        $("[data-test-id=\"notification\"]")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldBe(Condition.text("Успешно!"))
                .shouldBe(Condition.text("Встреча успешно забронирована на " + currentDate));
    }
}
