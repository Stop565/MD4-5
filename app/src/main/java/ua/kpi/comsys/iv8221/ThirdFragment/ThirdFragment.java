package ua.kpi.comsys.iv8221.ThirdFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import ua.kpi.comsys.iv8221.R;

public class ThirdFragment extends Fragment {
    private ListView listView;
    private ArrayAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);

        listView = (ListView) view.findViewById(R.id.book_list_view);
        String[] bookTitles;
        try {
            bookTitles = BookData.getTitleArray();
            adapter = new BookAdapter(getActivity(), bookTitles);
            listView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ListItemActivity.class);
                intent.putExtra("pos", position);
                startActivity(intent);
            }
        });

        FloatingActionButton myFab = (FloatingActionButton) view.findViewById(R.id.addItemBtn);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Hello add btn");
                Intent intent = new Intent(getContext(), AddItemActivity.class);
//                intent.putExtra("pos", position);
                startActivity(intent);
            }
        });

        return view;
    }
}


