package com.example.rahul.listviewexample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        String[] restaurants = getAllRestaurants();

        ListAdapter restaurnatsListAdapter = new RestaurantImageTextAdapter(this, restaurants);

        ListView theRestaurantsListView = (ListView) findViewById(R.id.allHotelsView);

        theRestaurantsListView.setAdapter(restaurnatsListAdapter);

        theRestaurantsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int me, long id) {
                String restaurantPicked = "You wana eat at "+ String.valueOf(parent.getItemAtPosition(me));

                Toast.makeText(MainActivity.this, restaurantPicked, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    private String[] getAllRestaurants() {
        System.out.println("******RESPONSE*********"+new NetworkCallTask().execute(""));
        return new String[]{"BBQ", "Butter Beans", "Cafe Nero"};
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class NetworkCallTask extends AsyncTask<String, Void, String> {

        private String response;

        @Override
        protected String doInBackground(String... params) {
            String url = "http://192.168.81.129:3000/api/hotels";

            RestTemplate restTemplate = new RestTemplate();

            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            String result = restTemplate.getForObject(url, String.class);

            System.out.println("***************************" + result);
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            this.response = result;
        }
    }
}
