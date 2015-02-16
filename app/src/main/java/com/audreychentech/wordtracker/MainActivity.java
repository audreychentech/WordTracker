package com.audreychentech.wordtracker;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleCursorAdapter;

import com.audreychentech.wordtracker.persistence.LetterGroupDbHelper;
import com.audreychentech.wordtracker.provider.LetterGroupsContract;


public class MainActivity extends ListActivity implements LoaderManager.LoaderCallbacks <Cursor> {

    private static final String TAG = MainActivity.class.getName();

    private SimpleCursorAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fillLetterGroupDataInListView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
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

        if (id == R.id.action_add) {
            openCreateLetterGroup();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Log.v(TAG, "Entered onCreateLoader");
        String[] projection = {LetterGroupsContract.LetterGroup.COLUMN_NAME_ID, LetterGroupsContract.LetterGroup.COLUMN_NAME_LETTER_GROUP};
        CursorLoader cursorLoader = new CursorLoader(this, LetterGroupsContract.LetterGroup.CONTENT_URI,
                projection, null, null, null);
        return cursorLoader;
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }



    private void fillLetterGroupDataInListView() {


        Log.v(TAG, "Entered fillLetterGroupDataInListView");
        String[] from = {LetterGroupsContract.LetterGroup.COLUMN_NAME_LETTER_GROUP};
        int[] to = {android.R.id.text1};
        mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, from, to, 0);
        setListAdapter(mAdapter);

        getLoaderManager().initLoader(0, null, this);

    }

    // Opens CreateLetterGroupActivity
    private void openCreateLetterGroup() {
        Intent intent = new Intent(this, CreateLetterGroupActivity.class);
        startActivity(intent);
    }
}
