package com.my.bubbletea;

import static com.esafirm.imagepicker.features.ImagePickerLauncherKt.createImagePickerIntent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ImagePickerConfig;
import com.esafirm.imagepicker.features.ImagePickerMode;
import com.esafirm.imagepicker.model.Image;
import com.google.android.material.button.MaterialButton;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class UpgradeActivity extends AppCompatActivity {
    final int REQ_IMG = 114514;

    ImageView[] imgs=new ImageView[3];
    ImageButton imageButton;
    ArrayList<Image> imagePicked = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);

        Button publishButton = findViewById(R.id.publish_button);
        MaterialButton imagePickerButton =  findViewById(R.id.imagePicker);
        imgs[0] = findViewById(R.id.attachImage1);
        imgs[1] = findViewById(R.id.attachImage2);
        imgs[2] = findViewById(R.id.attachImage3);
        //imageButton = findViewById(R.id.addphoto);

        final boolean[] hasImage = {false};


        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                publishButton.setEnabled(false);
                if (currentUser == null) {
                    // 未登录
                    Toast.makeText(view.getContext(),"Not logged in",Toast.LENGTH_SHORT).show();
                } else {
                    // 登录了
                    EditText titleInput = findViewById(R.id.titleInput);
                    EditText contentInput = findViewById(R.id.contentInput);
                    ParseObject moment = new ParseObject("Moment");

                    // scanning for attachments.
                    ArrayList<ParseFile> attachments = new ArrayList<>();
                    for(Image i:imagePicked) {
                        try {
                            InputStream iStream =   getContentResolver().openInputStream(i.getUri());
                            byte[] inputData = getBytes(iStream);
                            ParseFile file = new ParseFile("pic.jpg", inputData);
                            file.save();
                            attachments.add(file);
                        } catch (IOException | ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    moment.put("title",String.valueOf(titleInput.getText()));
                    moment.put("content",String.valueOf(contentInput.getText()));
                    moment.put("attachments",attachments);
                    moment.put("publisher",currentUser);
                    moment.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null) {
                                //saved successfully
                                Toast.makeText(view.getContext(),"Published.",Toast.LENGTH_SHORT).show();
                            } else {
                                // error.
                                Log.e("Error When Saving",e.toString());
                            }
                        }
                    });

                }
                publishButton.setEnabled(true);
                finish();
            }
        });

        imagePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(hasImage[0]) { // 清除已经选择的照片
                    imagePicked.clear();
                } else { // 选择新照片
                    ImagePickerConfig conf = new ImagePickerConfig();
                    conf.setMode(ImagePickerMode.MULTIPLE);
                    conf.setLimit(3);
                    Intent intent = createImagePickerIntent(view.getContext(), conf);
                    startActivityForResult(intent, REQ_IMG);
                }
                hasImage[0] = !hasImage[0];
            }
        });
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_IMG && resultCode == RESULT_OK) {
            List<Image> l = ImagePicker.INSTANCE.getImages(data);
            if(l != null) {
                imagePicked.addAll(l);
//                Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(imageView);
            }

        }
    }



    public void turn_moment(View view) {
    }
}