package com.example.indrianinatalia.database;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Random;

public class AddActivity extends AppCompatActivity {

    EditText txtname, txtNumb;
    Spinner spinGen;

    SQLHelperQ sqlHelperQ;
    SQLiteDatabase db;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        txtname = (EditText) findViewById(R.id.txtNameAdd);
        txtNumb = (EditText) findViewById(R.id.txtNumbAdd);
        spinGen = (Spinner) findViewById(R.id.spinGen);

        id = getIntent().getStringExtra("id");

    }

    public void addDB(String name, String gen, String numb){
        Random r = new Random();
        int i = r.nextInt(80-65) - 65;
        String id = ""+i;

        try{
            sqlHelperQ = new SQLHelperQ(this);
            db = sqlHelperQ.getWritableDatabase();
            db.execSQL("insert into tbAkil (id,name,gen,numb) values ('"+ id +"','"+ name +"','"+ gen +"','"+ numb +"')");
            finish();
        } catch (SQLException e){
            Toast.makeText(this, ""+e, Toast.LENGTH_LONG).show();
        }

    }

    public void updateDB(String id, String name, String gen, String numb){
        String idd ="001";
        sqlHelperQ = new SQLHelperQ(this);
        db = sqlHelperQ.getWritableDatabase();
        db.execSQL("update tbAkil set name = '"+ name +"',gen='"+ gen +"',numb = '"+ numb +"' where id ='"+ id +"'");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mnu_add, menu);
        return true;
    }

    public void delete(String id){
        try{
            sqlHelperQ = new SQLHelperQ(this);
            db = sqlHelperQ.getWritableDatabase();
            db.execSQL("delete from tbAkil where id='"+ id +"'");
            finish();
        } catch (SQLException e){
            Toast.makeText(this, ""+e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.actionClear:
                txtname.setText("");
                txtNumb.setText("");
                break;
            case R.id.actionSave:
                String name, gen, numb;
                name = txtname.getText().toString();
                gen = spinGen.getSelectedItem().toString();
                numb = txtNumb.getText().toString();
                addDB(name, gen, numb);
                break;
            case R.id.actionDelete:
                delete(id);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
