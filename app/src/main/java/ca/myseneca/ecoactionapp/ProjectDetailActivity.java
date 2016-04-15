package ca.myseneca.ecoactionapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ProjectDetailActivity extends AppCompatActivity {

    //creates activity displaying details for one project. Receives project title and info from intent and displays in textviews
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent=getIntent();

        String iTitle=intent.getStringExtra("Name");
        TextView title=(TextView)findViewById(R.id.projectTitle);
        title.setText(iTitle);

        String iPopup=intent.getStringExtra("PopupInfo");
        TextView description=(TextView)findViewById(R.id.projectDescription);
        description.setText(iPopup);

        //used code from http://www.androidhive.info/2011/08/how-to-switch-between-activities-in-android/

    }

}
