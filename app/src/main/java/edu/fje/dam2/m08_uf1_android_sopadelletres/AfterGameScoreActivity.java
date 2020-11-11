package edu.fje.dam2.m08_uf1_android_sopadelletres;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class AfterGameScoreActivity extends AppCompatActivity {

    public Button backToMainMenu;
    public TextView scoreView;
    public String username;
    public int score;
    public long gameDuration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_game_score);
        MediaPlayer win = MediaPlayer.create(this, R.raw.win);
        win.start();

        //Getting data (username, score and game duration) from PlayActivity
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        score = intent.getIntExtra("score", 0);
        gameDuration = intent.getLongExtra("gameDuration", 0);


        // Showing score into textView (id: scoreView)
        scoreView = findViewById(R.id.scoreView);
        scoreView.setText(String.valueOf(score));


        // Button "Menú principal"
        backToMainMenu = findViewById(R.id.backToMainMenu);
        backToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // Calling function saveScoreToDatabase()
                saveScoreToDatabase();

                // Return to MainActivity
                Intent intent = new Intent(AfterGameScoreActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void saveScoreToDatabase(){
        // Get current time
        Date now = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = dateFormat.format(now);

        // Database SQLite connection --> Inserting new scores to database
        SQLiteDatabase database = null;

        try {
            // Connecting to "SopaDeLletres" table
            database = this.openOrCreateDatabase("SopaDeLletres", MODE_PRIVATE, null);
            String sql = "INSERT INTO Scoreboards VALUES ('" + username + "', " + score + ", " + gameDuration + ", '" + currentTime + "');";
            database.execSQL(sql);

            Log.i("AfterGameScoreActivity","Score saved successfully!");

            // Creating a toast
            Toast toast = Toast.makeText(getApplicationContext(), "Puntuació enregistrada!", Toast.LENGTH_SHORT);
            toast.show();

        } finally {
            if (database != null) {
                database.close();
            }
        }
    }

}