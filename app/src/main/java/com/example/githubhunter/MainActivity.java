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

import com.example.githubhunter.entities.RepoEntity;
import com.example.githubhunter.recyclerview.RepoAdapter;
import com.example.githubhunter.utils.NetworkUtils;
import com.example.githubhunter.utils.RepoJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements RepoAdapter.ListItemClickListener {
    EditText searchBox;
    TextView urlDisplay;
    TextView searchResults;
    TextView errorMsgDisplay;
    ProgressBar request_progress;
    RecyclerView repoList;
    RepoAdapter adapter;
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

    public class gitHubQueryTask extends AsyncTask<URL, Void, String>{

        @Override
        protected void onPreExecute() {
            request_progress.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String gitHubSearchResults = null;

            try {
                Log.d("Juan", "Número de registros haciendo llamada");
                gitHubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
                Log.d("Juan", "salida");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return gitHubSearchResults;
        }

        @Override
        protected void onPostExecute(String s) {
            request_progress.setVisibility(View.INVISIBLE);
            if (s != null && !s.equals("")) {
                try {
                    RepoEntity[] parsedApiOutput = RepoJsonUtils.parseRepoFromJson(s);
                    Log.d("Juan", "Número de registros " + parsedApiOutput.length);
                    adapter.setRepoData(parsedApiOutput);
//                    Log.d("JUAN", parsedApiOutput[0]);
//                    Log.d("JUAN", parsedApiOutput[1]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                showJsonData();
//                searchResults.setText(s);
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

            new gitHubQueryTask().execute(githubUrl);
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
        repoList = (RecyclerView) findViewById(R.id.rv_response);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        request_progress = (ProgressBar) findViewById(R.id.request_progress);

        repoList.setLayoutManager(layoutManager);

        repoList.setHasFixedSize(true);

        adapter = new RepoAdapter(this);
        repoList.setAdapter(adapter);
    }
}