package Libraries;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonConverter {
    protected String title;
    protected String date;
    protected long sum;

//    public JsonConverter(String inRequest) {
//        jsonToElement(inRequest);
//    }
    public JsonConverter() {}

    public void jsonToElement(String inRequest) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(inRequest);
            JSONObject inJson = (JSONObject) obj;
            title = (String) inJson.get("title");
            date = (String) inJson.get("date");
            sum = (long) inJson.get("sum");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public JSONObject elementToJson(String maxCategory, long maxSum) {
        JSONObject maxCategoryJson = new JSONObject();
        maxCategoryJson.put("category", maxCategory);
        maxCategoryJson.put("sum", maxSum);
        JSONObject outJson = new JSONObject();
        outJson.put("maxCategory", maxCategoryJson);
        return outJson;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public long getSum() {
        return sum;
    }
}
