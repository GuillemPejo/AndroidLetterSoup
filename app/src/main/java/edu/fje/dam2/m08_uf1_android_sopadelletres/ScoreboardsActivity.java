package edu.fje.dam2.m08_uf1_android_sopadelletres;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class ScoreboardsActivity extends AppCompatActivity {

    private ListView scoreboard;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboards);

        getScores();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        getScores();
    }

    public void getScores(){

        ArrayList<String> results = new ArrayList<String>();

        //Connecting to database --> Select scores
        SQLiteDatabase database = this.openOrCreateDatabase("SopaDeLletres", MODE_PRIVATE, null);

        try {
            Cursor cursor = database.rawQuery("SELECT username, score from Scoreboards ORDER BY score desc", null);

            int usernameColumn = cursor.getColumnIndex("username");
            int scoreColumn = cursor.getColumnIndex("score");

            if (cursor != null) {

                if (cursor.isBeforeFirst()) {
                    cursor.moveToFirst();
                    int i = 0;

                    do {
                        i++;

                        String username = cursor.getString(usernameColumn);
                        int score = cursor.getInt(scoreColumn);

                        String usernameColumnScore = cursor.getColumnName(scoreColumn);

                        results.add("" + i + " .-  " + username.toUpperCase() + " (" + + score + ")");
                    } while (cursor.moveToNext());
                }
            }

        } catch (android.database.CursorIndexOutOfBoundsException e){
            results.add("Sense puntuacions");
        }

        finally {
            if (database != null) {
                database.close();
            }
        }

        // Show database results on ScoreboardsActivity
        scoreboard = findViewById(R.id.listView_results);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, results);

        scoreboard.setAdapter(adapter);

    }

}