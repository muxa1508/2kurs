package Libraries;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class BasketTest {

    protected Map<String, Long> basket;


    @BeforeEach
    void setUp() {
    basket =new HashMap<>();
}
    @Test
    void addItemInNullMap() {
        String category = null;
        long sum = 100;

        if (basket.get(category) == null) {
            basket.put(category, sum);
        } else {
            basket.replace(category, basket.get(category), basket.get(category) + sum);
        }
        Assertions.assertEquals(100, basket.get(category));

    }
    @Test
    void addItemInMap() {
        String category = "еда";
        long sum = 100;

        basket.put("еда", 300L);
        if (basket.get(category) == null) {
            basket.put(category, sum);
        } else {
            basket.replace(category, basket.get(category), basket.get(category) + sum);
        }
        Assertions.assertEquals(400, basket.get(category));

    }

    @Test
    void sumCompare() {
        basket.put("еда", 300L);
        basket.put("другое", 200L);
        Optional<Map.Entry<String, Long>> maxEntry = basket.entrySet().stream()
                .max(Map.Entry.comparingByValue());
        long maxSumLong = maxEntry.get().getValue();
        String maxSumCategory = maxEntry.get().getKey();
        Assertions.assertEquals("еда", maxSumCategory);
        Assertions.assertEquals(300L, maxSumLong);
    }
}