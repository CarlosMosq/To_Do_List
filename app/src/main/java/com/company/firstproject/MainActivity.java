package com.company.firstproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String LIST_KEY = "list_key";
    EditText newItem;
    Button addItem;
    ListView listOfItems;
    SharedPreferences contentSaver;
    String newTextItem;
    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;
    String[] listArr;
    String jsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newItem = findViewById(R.id.newItem);
        addItem = findViewById(R.id.addItem);
        listOfItems = findViewById(R.id.listOfItems);
        buildAdapter();

        addItem.setOnClickListener(v -> {
            if (newItem.getText().toString().length() > 0) {
                list.add(newItem.getText().toString());
                buildAdapter();
                newItem.setText("");
            }
        });

        listOfItems.setOnItemClickListener((parent, view, position, id) -> {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog
                    .setTitle("Delete")
                    .setMessage("Do you want to delete this item?")
                    .setCancelable(false)
                    .setNegativeButton("No", (dialog, which) -> dialog.cancel())
                    .setPositiveButton("Yes", (dialog, which) -> {
                        list.remove(position);
                        buildAdapter();
                        adapter.notifyDataSetChanged();
                    })
                    .show();
            alertDialog.create();


        });

        retrieveData();
        retrieveList();
        Log.d("Message", "onCreate");
    }



    @Override
    protected void onPause() {
        super.onPause();
        saveData();
        Log.d("Message", "onPause");
    }

    public void buildAdapter() {
        listArr = list.toArray(new String[0]);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listArr);
        listOfItems.setAdapter(adapter);
    }

    public void saveData() {
        Gson gson = new Gson();
        jsonString = gson.toJson(list);

        contentSaver = getSharedPreferences("contentSaver", Context.MODE_PRIVATE);
        newTextItem = newItem.getText().toString();

        SharedPreferences.Editor editor = contentSaver.edit();
        editor.putString("listItem", newTextItem);
        editor.putString(LIST_KEY, jsonString);
        editor.apply();
        Toast.makeText(getApplicationContext(), "Your data is saved", Toast.LENGTH_LONG).show();
    }

    public void retrieveData() {
        contentSaver = getSharedPreferences("contentSaver", Context.MODE_PRIVATE);
        newTextItem = contentSaver.getString("listItem", null);
        jsonString = contentSaver.getString(LIST_KEY, null);
        newItem.setText(newTextItem);
    }

    public void retrieveList () {
        contentSaver = getSharedPreferences("contentSaver", Context.MODE_PRIVATE);
        jsonString = contentSaver.getString(LIST_KEY, null);

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        list = gson.fromJson(jsonString, type);
        buildAdapter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("Message", "onDestroy");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("Message", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("Message", "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("Message", "onStop");
    }

}