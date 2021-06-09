package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    RecyclerView rcv_person;
    Button btnGetAll,btnPost,btnPut,btnDelete;
    EditText edId,edName;
    ArrayList<Person> mpersons;
    CustomerAdapter adt;
    String url = "https://60b5d3bbfe923b0017c84b46.mockapi.io/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcv_person = findViewById(R.id.rcv_person);
        btnDelete = findViewById(R.id.btnDelete);
        btnPost = findViewById(R.id.btnPost);
        btnGetAll = findViewById(R.id.btnGetAll);
        btnPut = findViewById(R.id.btnPut);
        edId = findViewById(R.id.edId);
        edName = findViewById(R.id.edName);

        mpersons = new ArrayList<>();

//        mpersons.add(new Person("1","mynguyen"));
//        mpersons.add(new Person("2","mynguyen1"));
//        mpersons.add(new Person("3","mynguyen2"));

//        adt = new CustomerAdapter(mpersons);
//        rcv_person.setHasFixedSize(true);
//        rcv_person.setAdapter(adt);
//        rcv_person.setLayoutManager(new GridLayoutManager(this,1));

//        String getall = url+"person";
        btnGetAll.setOnClickListener(view->{
            mpersons.clear();
            GetData(url);
        });
        btnPut.setOnClickListener(view->{
            PutApi(url);
        });
        btnPost.setOnClickListener(view->{
            PostApi(url);
        });
        btnDelete.setOnClickListener(view->{
            DeleteApi(url);
        });


    }

    private void GetData(String url)
    {
        JsonArrayRequest request = new JsonArrayRequest(url+"person1", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Toast.makeText(MainActivity.this, "True", Toast.LENGTH_SHORT).show();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject obj = (JSONObject) response.get(i);
                        String id = obj.getString("id");
                        String name = obj.getString("name");
                        Person person = new Person(id,name);
                        mpersons.add(person);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adt = new CustomerAdapter(mpersons);
                rcv_person.setHasFixedSize(true);
                rcv_person.setAdapter(adt);
                rcv_person.setLayoutManager(new GridLayoutManager(MainActivity.this,1));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Error make by API server!",Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void PutApi(String url)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url + "person1/" + edId.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error by Post Data", Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String ,String> params = new HashMap<>();
                params.put("name",edName.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void PostApi(String url)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "person1/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error by Post Data", Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String ,String> params = new HashMap<>();
                String id = edId.getText().toString();
                String name = edName.getText().toString();
                params.put("id",id);
                params.put("name",name);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void DeleteApi(String url)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url + "person1/"+ edId.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error by Post Data", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}