import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class AvtoGenerator {
    private AvtoGenerator() {}

    public static User dataUser() {

        Faker faker = new Faker(new Locale("ru"));
        return new User(
                getCity(),
                faker.name().fullName(),
                faker.phoneNumber().phoneNumber()
        );
    }

    public static String getCity() {
        List<String> actualCity = new ArrayList<>();
        actualCity.add("Москва");
        actualCity.add("Грозный");
        actualCity.add("Казань");
        actualCity.add("Калининград");
        actualCity.add("Новосибирск");
        actualCity.add("Тюмень");

        Random random = new Random();
        int number = random.nextInt(6);
        return actualCity.get(number);
    }
}
