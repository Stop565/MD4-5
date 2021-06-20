package ua.kpi.comsys.iv8221;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jjoe64.graphview.GraphView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Arrays;

import ua.kpi.comsys.iv8221.SecondFragment.SecondFragment;
import ua.kpi.comsys.iv8221.ThirdFragment.BookData;
import ua.kpi.comsys.iv8221.ThirdFragment.ThirdFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(
                R.id.mainFrameLayout,
                new FirstFragment()
        ).commit();

        Boolean isDataSet;
        try {
            Bundle bundle = getIntent().getExtras();
            isDataSet = bundle.getBoolean("isDataSet");
        } catch (Exception e) {
            isDataSet = false;
        }

        System.out.println("Data is set");
        System.out.println(isDataSet);
        if (!isDataSet){
            try {
                InputStream is = getAssets().open("BooksList.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                String json = new String(buffer, "UTF-8");
                JSONObject jsonObject = new JSONObject(json);
                BookData.setData(jsonObject);
                System.out.println(Arrays.toString(getAssets().list("book_items")));
                readBookItems(getAssets().list("book_items"));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.BottomNavMenu);

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selected = null;
                    switch (menuItem.getItemId()) {
                        case R.id.first:
                            selected = new FirstFragment();
                            break;
                        case R.id.second:
                            selected = new SecondFragment();
                            break;
                        case R.id.third:
                            selected = new ThirdFragment();
                            break;
                        case R.id.forth:
                            selected = new ForthFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout,
                            selected).commit();
                    return true;
                }

            };

    private void readBookItems(String[] names){
        for (int i = 0; i < names.length; i++) {
            try {
                InputStream is = getAssets().open("book_items/" + names[i]);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                String json = new String(buffer, "UTF-8");
                JSONObject jsonObject = new JSONObject(json);
                BookData.setItem(names[i].replace(".json", "") ,jsonObject);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
