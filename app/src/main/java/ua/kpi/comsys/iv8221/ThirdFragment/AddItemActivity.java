package ua.kpi.comsys.iv8221.ThirdFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;

import ua.kpi.comsys.iv8221.MainActivity;
import ua.kpi.comsys.iv8221.R;

public class AddItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
    }

    public void onAddBtnClick(View view) {
        try {
            JSONArray arr = BookData.getData().getJSONArray("books");
            JSONObject obj = new JSONObject();

            EditText title = findViewById(R.id.input_title);
            String titleText = title.getText().toString();

            EditText subtitle = findViewById(R.id.input_subtitle);
            String subtitleText = subtitle.getText().toString();


            EditText price = findViewById(R.id.input_price);
            String priceText = price.getText().toString();

            obj.put("title", titleText);
            obj.put("subtitle", subtitleText);
            obj.put("isbn13", "");
            obj.put("price", priceText);
            obj.put("image", "");

            arr.put(obj);
            JSONObject data = BookData.getData();
            data.put("books", arr);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(view.getContext(), MainActivity.class);
        intent.putExtra("isDataSet", true);
        startActivity(intent);
    }
}