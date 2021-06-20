package ua.kpi.comsys.iv8221.ThirdFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public final class BookData {
    private static JSONObject data;
    private static Map<String, JSONObject> items = new HashMap<>();
    private static Boolean isDataSet = false;

    public static JSONObject getData() {
        return data;
    }

    public static void setData(JSONObject data) {
        if (!isDataSet) {
            System.out.println("==== Data is rewritten =====");
            BookData.data = data;
        }

    }

    public static void setData(JSONObject data, Boolean isForce) {
            BookData.data = data;
    }

    public static int getBookDataSize() {
        try {

            JSONArray arr = data.getJSONArray("books");
            return arr.length();
        } catch (JSONException e) {
            return 0;
        }

    }

    public static JSONObject getBook(int index) throws JSONException {
        JSONArray arr = data.getJSONArray("books");
        return (JSONObject) arr.get(index);
    }

    public static String[] getTitleArray() throws JSONException {
        String[] bookList = new String[BookData.getBookDataSize()];
        for (int i = 0; i < BookData.getBookDataSize(); i++) {
            bookList[i] = getBook(i).getString("title");

        }
        return bookList;
    }

    public static String getIsbnFromPos(int pos) throws JSONException {
        try {
            return getBook(pos).getString("isbn13");
        } catch (Exception e) {
            return "";
        }
    }

    public static void setItem(String itemKey ,JSONObject data) {
        BookData.items.put(itemKey, data);
    }

    public static JSONObject getItem(String itemKey) {
        return items.get(itemKey);
    }

}
