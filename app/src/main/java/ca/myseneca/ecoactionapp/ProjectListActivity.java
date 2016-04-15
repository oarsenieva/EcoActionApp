package ca.myseneca.ecoactionapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class ProjectListActivity extends AppCompatActivity {


    private ArrayList<String> projectNames=new ArrayList<>();
    private ArrayList<String> projectDescriptions=new ArrayList<>();

    ArrayAdapter<String> aa;
    int length;

    //creates activity, parses JSON file to display listview of project names
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView lv=(ListView)findViewById(R.id.listView);
        EditText search=(EditText)findViewById(R.id.editText);

        readFile();

        aa=new ArrayAdapter<String>(this, R.layout.list_layout_item, projectNames);
        lv.setAdapter(aa);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                String selection=(String)((TextView) view).getText();//finds index of selected project in the arrays to add data to intent
                int index=projectNames.indexOf(selection);

                Intent intent = new Intent(ProjectListActivity.this, ProjectDetailActivity.class);

                intent.putExtra("Name", projectNames.get(index));
                intent.putExtra("PopupInfo", projectDescriptions.get(index));
                startActivity(intent);
            }
        });//http://zenit.senecac.on.ca/wiki/index.php/MAP524/DPS924_Lecture_5 and http://www.androidhive.info/2011/08/how-to-switch-between-activities-in-android/

        lv.setTextFilterEnabled(true);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ProjectListActivity.this.aa.getFilter().filter(s);

            }
        });//code from http://stackoverflow.com/questions/3550024/android-search-listview

    }

    //reads and parses JSON file to get names and descriptions of projects
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
        }
        //code from  http://stackoverflow.com/questions/13814503/reading-a-json-file-in-android

        try{
            JSONObject jsonObject=new JSONObject(json);
            JSONArray jsonArray=jsonObject.optJSONArray("features");
            length=jsonArray.length();

            for(int i=0; i<length;i++){
                JSONObject jsonObj=jsonArray.getJSONObject(i);
                JSONObject properties=jsonObj.getJSONObject("properties");

                String name=properties.optString("Name");
                projectNames.add(name);
                String popup=properties.optString("PopupInfo");
                projectDescriptions.add(popup);
            }
        } catch(JSONException e){
            e.printStackTrace();
        }
        //code from http://www.tutorialspoint.com/android/android_json_parser.htm
    }


}
