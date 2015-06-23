package personal.com.tithingapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Currency;

import personal.com.tithingapp.database.IncomeTable;
import personal.com.tithingapp.database.Provider;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        ContentValues contentValues = new ContentValues();
        contentValues.put(IncomeTable.TOTAL, 124);
        getContentResolver().insert(Provider.INCOME_CONTENT_URI, contentValues);

        Cursor cursor = getContentResolver().query(Provider.INCOME_CONTENT_URI, null, null, null, null);
        if (cursor.moveToFirst()) {
            float total = cursor.getFloat(cursor.getColumnIndex(IncomeTable.TOTAL));
            Log.i("MainActivity", "Total: " + total);
        } else {
            Log.i("MainActivity", "Error getting income data");
        }
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
}
