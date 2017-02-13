package com.example.indrianinatalia.database;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    AdapterQ adapterQ;
    SQLHelperQ sqlHelperQ;
    SQLiteDatabase db;

    RecyclerView mRecycler;
    Cursor cursor;

    List<String> id = new ArrayList<>();
    List<String> name = new ArrayList<>();
    List<String> gen = new ArrayList<>();
    List<String> numb = new ArrayList<>();

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = (ImageView) findViewById(R.id.imgNoItems);

        showDB("");



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });

    }

    public void setRecycler(){
        adapterQ = new AdapterQ(MainActivity.this, id, name, gen, numb);

        mRecycler = (RecyclerView) findViewById(R.id.mRecycler);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mRecycler.setAdapter(adapterQ);
    }

    public void showDB(String nameS){
        name.clear(); gen.clear(); numb.clear();
        try{
            sqlHelperQ = new SQLHelperQ(this);
            db = sqlHelperQ.getReadableDatabase();
            cursor = db.rawQuery("select * from tbAkil where name like '%"+ nameS +"%'", null);
            cursor.moveToFirst();
            if (cursor.getCount()==0){
                imageView.setVisibility(View.VISIBLE);
            }else{
                for (int i = 0; i < cursor.getCount(); i++){
                    cursor.moveToPosition(i);
                    id.add(cursor.getString(0));
                    name.add(cursor.getString(1));
                    gen.add(cursor.getString(2));
                    numb.add(cursor.getString(3));
                }
                imageView.setVisibility(View.GONE);
            }
            setRecycler();
        }catch (SQLException e){
            Toast.makeText(this, ""+e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPostResume() {
        showDB("");
        super.onPostResume();
    }

    @Override
    protected void onResume() {
        showDB("");
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searviewItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searviewItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                showDB(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }
}
