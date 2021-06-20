package ua.kpi.comsys.iv8221.ThirdFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;

import ua.kpi.comsys.iv8221.R;
import ua.kpi.comsys.iv8221.ThirdFragment.BookData;

class BookAdapter extends ArrayAdapter<String> {
    private Context context;

    BookAdapter(Context context, String[] titles) {
        super(context, R.layout.list_item, R.id.book_title, titles);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.list_item, parent, false);
        ImageView images = row.findViewById(R.id.book_img);
        TextView titles = row.findViewById(R.id.book_title);
        TextView subTitles = row.findViewById(R.id.book_subtitle);
        TextView prices = row.findViewById(R.id.book_price);

        try {
            titles.setText(BookData.getBook(position).getString("title"));
            subTitles.setText(BookData.getBook(position).getString("subtitle"));
            prices.setText(BookData.getBook(position).getString("price"));
            String img_name = BookData.getBook(position).getString("image");
            img_name = img_name.length() > 0 ? img_name.substring(0,  img_name.length() - 4) : "";

            int img_id = context.getResources().getIdentifier(
                    img_name,
                    "drawable",
                    context.getPackageName()
            );

            images.setImageResource(img_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return row;
    }

}