package ua.kpi.comsys.iv8221;

import android.content.Intent;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.GridLayout;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class ForthFragment extends Fragment {

    private static int RESULT_LOAD_IMAGE = 1;
    private static View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_forth, container, false);
        FloatingActionButton myFab = (FloatingActionButton) view.findViewById(R.id.addImgBtn);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        return view;
    }

    public void addImage(Bitmap bm) {
        GridLayout gl = view.findViewById(R.id.image_layout);

        View pictureBlueprints = LayoutInflater.from(getContext()).inflate(R.layout.picture_blueprints, null);

        ImageView img;
        if (gl.getChildCount() % 8 == 1) {
            img = pictureBlueprints.findViewById(R.id.big_picture);

        } else {
            img = pictureBlueprints.findViewById(R.id.small_picture);
        }
        ((ViewGroup) img.getParent()).removeView(img);
        img.setImageBitmap(bm);

        gl.addView(img);
        pictureBlueprints.destroyDrawingCache();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;

            try (ParcelFileDescriptor pfd = getContext().getContentResolver().openFileDescriptor(selectedImage, "r")) {
                if (pfd != null) {
                    bitmap = BitmapFactory.decodeFileDescriptor(pfd.getFileDescriptor());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            addImage(bitmap);
            System.out.println("==== size ====");
            System.out.println(bitmap.getByteCount());

        }
    }
}


