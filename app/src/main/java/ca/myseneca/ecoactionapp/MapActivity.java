package ca.myseneca.ecoactionapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    public LatLng[] projects;
    public String[] projectName;
    public int length;

    //creates map activity, adds map fragment to activity, calls readFile method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        readFile();
    }

    //determines how map is displayed, adds markers of project locations
    @Override
    public void onMapReady(GoogleMap map) {

        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        for(int i=0;i<length; i++){
            map.addMarker(new MarkerOptions().position(projects[i]).title(projectName[i]));
        }

    }
    //code based on https://developers.google.com/maps/documentation/android-api/map#add_a_map_to_an_android_app

    //reads and parses JSON file to get coordinates and names of projects
    public void readFile(){
        String json=null;
        try{
            InputStream stream=getAssets().open("EcoAction.json");
            int size=stream.available();
            byte[] buffer=new byte[size];
            stream.read(buffer);
            stream.close();
            json=new String(buffer, "UTF-8");
        }catch(IOException e){
            e.printStackTrace();
        }//code from  http://stackoverflow.com/questions/13814503/reading-a-json-file-in-android

        try{
            JSONObject jsonObject=new JSONObject(json);
            JSONArray jsonArray=jsonObject.optJSONArray("features");

            length=jsonArray.length();
            projects=new LatLng[length];
            projectName=new String[length];

            for(int i=0; i<length;i++){
                JSONObject jsonObj=jsonArray.getJSONObject(i);
                JSONObject properties=jsonObj.getJSONObject("properties");

                projectName[i]=properties.optString("Name");

                JSONObject geometry=jsonObj.getJSONObject("geometry");
                JSONArray coordinates=geometry.optJSONArray("coordinates");

                projects[i]=new LatLng(coordinates.getDouble(0), coordinates.getDouble(1));
            }

        } catch(JSONException e){
            e.printStackTrace();
        }
        //code from http://www.tutorialspoint.com/android/android_json_parser.htm
    }
}
