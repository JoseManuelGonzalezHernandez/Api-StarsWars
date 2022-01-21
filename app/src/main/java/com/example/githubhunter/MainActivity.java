package com.example.githubhunter;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.githubhunter.entities.PlanetEntity;
import com.example.githubhunter.recyclerview.PlanetAdapter;
import com.example.githubhunter.utils.NetworkUtils;
import com.example.githubhunter.utils.PlanetJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements PlanetAdapter.ListItemClickListener {
    EditText searchBox;
    TextView urlDisplay;
    TextView searchResults;
    TextView errorMsgDisplay;
    ProgressBar request_progress;
    RecyclerView planetList;
    PlanetAdapter adapter;
    Toast clickToast;

    @Override
    public void onListItemClick(int clickedItemindex) {
        String toastMessage = "Se ha pulsado sobre " + clickedItemindex;
        if (clickToast != null) {
            clickToast.cancel();
        }
        clickToast = Toast.makeText(this, toastMessage,Toast.LENGTH_LONG);
        clickToast.show();
    }

    public class starsWarsQueryTask extends AsyncTask<URL, Void, String>{

        @Override
        protected void onPreExecute() {
            request_progress.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String apiSearchResults = null;

            try {
                apiSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return apiSearchResults;
        }

        @Override
        protected void onPostExecute(String s) {
            request_progress.setVisibility(View.INVISIBLE);
            if (s != null && !s.equals("")) {
                try {
                    PlanetEntity[] parsedApiOutput = PlanetJsonUtils.parseRepoFromJson(s);
                    adapter.setRepoData(parsedApiOutput);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                showJsonData();
            } else {
                showErrorMsg();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.search) {
            Log.i("MainActivity", "El usuario ha pulsado search");

            Context context = MainActivity.this;
            Toast.makeText(context, R.string.seach_pressed, Toast.LENGTH_LONG).show();
            URL githubUrl = NetworkUtils.buildUrl(searchBox.getText().toString());
            urlDisplay.setText(githubUrl.toString());

            new starsWarsQueryTask().execute(githubUrl);
        }
        return true;
    }

    private void showJsonData(){
        errorMsgDisplay.setVisibility(View.INVISIBLE);
//        searchResults.setVisibility(View.VISIBLE);
    }

    private void showErrorMsg(){
//        searchResults.setVisibility(View.INVISIBLE);
        errorMsgDisplay.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchBox = (EditText) findViewById(R.id.search_box);
        urlDisplay = (TextView) findViewById(R.id.url_display);
//        searchResults = (TextView) findViewById(R.id.github_search_results);
        errorMsgDisplay = (TextView) findViewById(R.id.error_message_display);
        planetList = (RecyclerView) findViewById(R.id.rv_response);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        request_progress = (ProgressBar) findViewById(R.id.request_progress);

        planetList.setLayoutManager(layoutManager);

        planetList.setHasFixedSize(true);

        adapter = new PlanetAdapter(this);
        planetList.setAdapter(adapter);
    }
}