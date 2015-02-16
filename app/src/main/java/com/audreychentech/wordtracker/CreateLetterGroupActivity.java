package com.audreychentech.wordtracker;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.audreychentech.wordtracker.provider.LetterGroupsContract;


public class CreateLetterGroupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_letter_group);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_letter_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Handle saveLetterGroup
    public void saveLetterGroup(View view) {

        // get the entered letter group
        EditText letterGroupText = (EditText) findViewById(R.id.letterGroupText);
        String letterGroup = letterGroupText.getText().toString();

        // to do: add validation to check for duplicates


        ContentValues values = new ContentValues();
        values.put(LetterGroupsContract.LetterGroup.COLUMN_NAME_LETTER_GROUP, letterGroup);
        getContentResolver().insert(LetterGroupsContract.LetterGroup.CONTENT_URI, values);
    }
}
