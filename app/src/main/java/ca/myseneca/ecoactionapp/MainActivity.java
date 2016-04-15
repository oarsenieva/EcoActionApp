package ca.myseneca.ecoactionapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final String TAG="EcoActionProjects APP: ";

    //creates main activity and creates buttons to call list and map activities
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button listButton=(Button) findViewById(R.id.listButton);
        listButton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              Log.i(TAG, "You clicked the list button!");//log entry
                                              Intent intent = new Intent(v.getContext(), ProjectListActivity.class);//callto next activity
                                              startActivityForResult(intent, 0);
                                          }
                                      }
        );
        
        Button mapButton=(Button) findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             Log.i(TAG, "You clicked the map button!");//log entry
                                             Intent intent = new Intent(v.getContext(), MapActivity.class);//callto next activity
                                             startActivityForResult(intent, 0);
                                         }
                                     }
        );//code from https://www.youtube.com/watch?v=Uen8B9p05tI,http://www.androidhive.info/2011/08/how-to-switch-between-activities-in-android/
    }

    //creates menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //deals with menu item selection
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
