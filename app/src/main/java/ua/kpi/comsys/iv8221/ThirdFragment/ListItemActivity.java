package ua.kpi.comsys.iv8221.ThirdFragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ua.kpi.comsys.iv8221.MainActivity;
import ua.kpi.comsys.iv8221.R;

public class ListItemActivity extends AppCompatActivity {

    ImageView img;
    String[] attrArr = {
            "title",
            "subtitle",
            "authors",
            "publisher",
//            "isbn13",
            "pages",
            "year",
            "rating",
            "desc",
            "price",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        img = findViewById(R.id.book_item_img);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            int pos  = bundle.getInt("pos");
            setImage(pos);
            setAttributes(pos);
        }
    }

    public void onDeleteBtnClick(View view) {
        try {
            JSONArray arr = BookData.getData().getJSONArray("books");

            Bundle bundle = getIntent().getExtras();
            if (bundle != null){
                int pos  = bundle.getInt("pos");
                arr.remove(pos);
            }

            JSONObject data = BookData.getData();
            data.put("books", arr);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(view.getContext(), MainActivity.class);
        intent.putExtra("isDataSet", true);
        startActivity(intent);
    }

    void setImage(int pos) {
        try {
            JSONObject item = BookData
                    .getItem(BookData.getIsbnFromPos(pos));
            String image_name;
            if (item == null) {
                image_name = "";
            } else {
                image_name = item.getString("image");
            }

            image_name = image_name.length() > 0
                    ?
                    image_name.substring(0,  image_name.length() - 4)
                    :
                    "";
            System.out.println("Image name " + image_name);
            int img_id = getResources().getIdentifier(
                    image_name,
                    "drawable",
                    getPackageName()
            );
            System.out.println("Image id " + img_id);
            img.setImageResource(img_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void setAttributes(int pos) {
        try {
            LinearLayout ll = findViewById(R.id.item_attrs_layout);
            JSONObject item = BookData
                    .getItem(BookData.getIsbnFromPos(pos));
            LinearLayout attrContainer = findViewById(R.id.attributes_list);
            ll.removeView(attrContainer);

            for (int i = 0; i < attrArr.length; i++) {
                try {
                    LinearLayout layout = new LinearLayout(this);
                    layout.setOrientation(LinearLayout.HORIZONTAL);
                    layout.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.MATCH_PARENT
                    ));

                    TextView decs = new TextView(this);
                    decs.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                    String name = attrArr[i].substring(0, 1).toUpperCase() + attrArr[i].substring(1);
                    String s = name + ": " + item.getString(attrArr[i]);
                    SpannableString ss1 =  new SpannableString(s);
                    ss1.setSpan(new ForegroundColorSpan(Color.GRAY), 0, attrArr[i].length(), 0);
                    ss1.setSpan(new ForegroundColorSpan(Color.BLACK), attrArr[i].length() + 2, s.length(), 0);
                    decs.setText(ss1);

                    decs.setPadding(5, 5, 5, 5);
                    layout.addView(decs);

                    ll.addView(layout);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}