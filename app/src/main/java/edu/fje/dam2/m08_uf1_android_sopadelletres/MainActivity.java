package edu.fje.dam2.m08_uf1_android_sopadelletres;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;



public class MainActivity extends AppCompatActivity {

    private Button playButton, scoresButton, instructionsButton;
    private TextView title;
    private ImageView sopa;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = (TextView)findViewById(R.id.title);
        playButton = (Button) findViewById(R.id.playButton);
        scoresButton = (Button) findViewById(R.id.scoresButton);
        instructionsButton = (Button) findViewById(R.id.instructionsButton);
        sopa = (ImageView) findViewById(R.id.imageView);


        Animation dedalt = AnimationUtils.loadAnimation(this, R.anim.dedalt);
        Animation desota = AnimationUtils.loadAnimation(this,R.anim.desota);
        Animation girar = AnimationUtils.loadAnimation(this,R.anim.girar);

        title.setAnimation(AnimationUtils.loadAnimation(this,R.anim.dedalt));
        playButton.setAnimation(AnimationUtils.loadAnimation(this,R.anim.desota));
        instructionsButton.setAnimation(AnimationUtils.loadAnimation(this,R.anim.desota));
        scoresButton.setAnimation(AnimationUtils.loadAnimation(this,R.anim.desota));
        sopa.setAnimation(AnimationUtils.loadAnimation(this,R.anim.girar));

        // Request CONTACTS permissions
        checkPermission();


        // Button "Jugar"
        playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                playButton.playSoundEffect(SoundEffectConstants.CLICK);
                // Start a new activity onClick the button
                Intent intent = new Intent(MainActivity.this, M17_CellesActivity.class);
                startActivity(intent);
            }
        });

        // Button "Puntuacions"
        scoresButton = findViewById(R.id.scoresButton);
        scoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // Start a new activity onClick the button
                Intent intent = new Intent(MainActivity.this, ScoreboardsActivity.class);
                startActivity(intent);
            }
        });

        // Button "Instructions"
        instructionsButton = findViewById(R.id.instructionsButton);
        instructionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // Start a new activity onClick the button
                Intent intent = new Intent(MainActivity.this, InstructionsActivity.class);
                startActivity(intent);
            }
        });

        // Connection to SQLite Database --> Creation of the database
        SQLiteDatabase database = null;

        try {
            // Creating the table "SopaDeLletres"
            database = this.openOrCreateDatabase("SopaDeLletres", MODE_PRIVATE, null);
            database.execSQL("CREATE TABLE IF NOT EXISTS Scoreboards (username VARCHAR, score INTEGER, gameDuration INTEGER, currentTime TEXT);");

            Log.i("MainActivity","Database created successfully");

        } finally {
            if (database != null) {
                database.close();
            }
        }

    }


    // Function to check and request permission.
    public void checkPermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                // Request the permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }
    }

    // This function is called when the user accepts or decline the permission.
    // Request Code is used to check which permission called this function.
    // This request code is provided when the user is prompt for permission.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this,"Has donat permissos per a accedir als contactes", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this,"Accés als contactes denegat", Toast.LENGTH_SHORT).show();
            }
        }
    }

}