package com.example.loaddata;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements recyclerAdapter.OnClickListener {
    private ArrayList<Series> seriesList;
    private RecyclerView recyclerView;
    private recyclerAdapter adapter;

    private TextView indexNumber;
    private FloatingActionButton aButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        aButton  = findViewById(R.id.actionButton);
        indexNumber = findViewById(R.id.IndexNumber);

        loadData();
        setAdapter();
        //setData();
        addNewTitle();
    }


    private void setAdapter(){
        adapter = new recyclerAdapter(seriesList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new  MyItemTouchHelper(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        adapter.setTouchHelper(itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

// Gets called when App close
    @Override
    protected void onStop() {
        super.onStop();
        saveData();
    }



    /*
                                                            Insert Menu
     */

    //Get Data from AddMenu
    public void addNewTitle() {
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String newTitle = extras.getString("add_Title");
            String newSeason = extras.getString("add_Season");
            String newEpisode = extras.getString("add_Episode");
            insertItem(newTitle, newSeason, newEpisode);// Insert Data in RecyclerView
            Toast.makeText(this,"Added to the List", Toast.LENGTH_LONG).show();
        }
    }

    //Adds Data to RecyclerView
    public void insertItem(String seriesName, String season, String episode) {
        seriesList.add(0,new Series ( seriesName, season, episode));//New item gets index zero
        adapter.notifyItemInserted(0); //insert at the top else use serieslist.size()
        recyclerView.scrollToPosition(0); //always show top entry
        adapter.notifyDataSetChanged();
    }


    //called by ActionButton
    public void callAddMenu(View view){
        Intent addMenu= new Intent( this, AddMenu.class);
        startActivity(addMenu);
    }



/*
                                                     Database/SharedPreferences
*/
    // Database takes the configuration of the Displayed arraylist
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferencs",MODE_PRIVATE);
        SharedPreferences.Editor editor  = sharedPreferences.edit();
        Gson gson = new Gson(); //Google Json

        String json = gson.toJson(seriesList);//Cast List to Json
        editor.putString("watch_db",json);
        editor.apply();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferencs",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("watch_db",null);
        Type type = new TypeToken<ArrayList<Series>>() {}.getType();
        seriesList = gson.fromJson(json,type);
       // Toast.makeText(this,"Welcome",Toast.LENGTH_SHORT).show();

        //Create ArrayList when empty
        if(seriesList == null){
            seriesList = new ArrayList<>();
            Toast.makeText(this,"Your list is empty",Toast.LENGTH_SHORT).show();
        }
    }




/*
*                ******************* Edit Menu *********************
    */


    /*
            Send Data to SeriesProfile
     */
    @Override
    public void onClickListener(int position) {

        Intent intent = new Intent( this, SeriesProfile.class);
        intent.putExtra("current_Position",position);
        intent.putExtra("current_Title",seriesList.get(position).getTitle());
        intent.putExtra("current_Episode",seriesList.get(position).getEpisode());
        intent.putExtra("current_Season",seriesList.get(position).getSeason());
        startActivityForResult(intent,1);
    }


    /*
                            Get edited Data from SeriesProfil
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Series series;
        if (requestCode == 1 && resultCode == RESULT_OK){
            int index = data.getIntExtra("new_current_Position",0);

            String title = data.getStringExtra("new_current_Title");
            String season = data.getStringExtra("new_current_Season");
            String episode = data.getStringExtra("new_current_Episode");

            series = new Series(title , season, episode);
            seriesList.set(index, series);
            adapter.notifyItemChanged(index);

            Toast.makeText(this,"Itadakimasuuu!",Toast.LENGTH_SHORT).show();

        }else if(resultCode == RESULT_CANCELED){
          //  Toast.makeText(this,"No changes",Toast.LENGTH_SHORT).show();
        }
    }


}