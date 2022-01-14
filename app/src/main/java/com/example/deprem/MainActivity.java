package com.example.deprem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.deprem.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.jsoup.Jsoup;

public class MainActivity extends AppCompatActivity {

    RecyclerView rc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rc = findViewById(R.id.rv);
        getData getdata = new getData();
        getdata.execute();
    }

    public class getData extends AsyncTask<String, String, String>
    {
        @Override
        protected String doInBackground(String... strings) {
            String str = "";
            try
            {
                str = Jsoup.connect("https://turkiyedepremapi.herokuapp.com/api")
                        .ignoreContentType(true)
                        .get()
                        .text();
            } catch (Exception e) { Log.e("x","ERR : error occured: " + e);}
            return str;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray jo = new JSONArray(s);
                SendToRc(jo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void SendToRc(JSONArray data)
    {
        RcAdapter adapter = new RcAdapter(this, data);
        rc.setLayoutManager(new LinearLayoutManager(this));
        rc.setAdapter(adapter);
    }
}