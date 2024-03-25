package com.cdp.camaravideo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    private final int REQUEST_VIDEO_CAPTURE = 1 ;
    private VideoView videoView;


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);


        init();


        findViewById(R.id.btnGrabarVideo).setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                videoLauncher.launch(new Intent(MediaStore.ACTION_VIDEO_CAPTURE));


            }

        });



    }


    private void init() {

        videoView = findViewById(R.id.videoView);



        // TODO: Set up video view properties and event listeners

    }


    ActivityResultLauncher<Intent> videoLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode()== RESULT_OK){
                Bundle extras = result.getData().getExtras();
                Bitmap vidBitmap = (Bitmap) extras.get("data");
                videoView.setVideoPath(vidBitmap.toString());
            }
        }
    });

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData();
            if (videoUri != null) {
                videoView.setVideoURI(videoUri);
                videoView.start();
            }
        }
    }
}
