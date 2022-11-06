package Libraries;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Basket {

    protected Map<String, Long> basket = new HashMap<>();
    protected long maxSumLong;
    protected String maxSumCategory;

    public Basket() {
    }

    public void addItem(String category, long sum) {
        if (basket.get(category) == null) {
            basket.put(category, sum);
        } else {
            basket.replace(category, basket.get(category), basket.get(category) + sum);
        }
    }

//    public long returnSum(String category) {
//        return basket.get(category);
//    }

    public void sumCompare() {
        Optional<Map.Entry<String, Long>> maxEntry = basket.entrySet().stream()
                .max(Map.Entry.comparingByValue());
        maxSumLong = maxEntry.get().getValue();
        maxSumCategory = maxEntry.get().getKey();
    }

    public long getMaxSumLong() {
        return maxSumLong;
    }

    public String getMaxSumCategory() {
        return maxSumCategory;
    }
}
