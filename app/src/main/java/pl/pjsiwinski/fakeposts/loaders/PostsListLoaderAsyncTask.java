package pl.pjsiwinski.fakeposts.loaders;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import pl.pjsiwinski.fakeposts.PostsListAdapter;
import pl.pjsiwinski.fakeposts.R;
import pl.pjsiwinski.fakeposts.model.FpPost;

public class PostsListLoaderAsyncTask extends AsyncTask<Integer, FpPost, ArrayList<FpPost>> {

    private ArrayList<FpPost> mArrayList;
    private Activity mActivity;
    private PostsListAdapter mAdapter;
    private String failure;

    public PostsListLoaderAsyncTask(Activity activity, PostsListAdapter adapter) {
        mActivity = activity;
        mAdapter = adapter;
        failure = "";
    }

    @Override
    protected void onPreExecute() {
        mAdapter.mDataset = new ArrayList<>();
        mArrayList = new ArrayList<>();
        mActivity.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        ((ProgressBar) mActivity.findViewById(R.id.progressBar)).setProgress(0);
    }

    @Override
    protected ArrayList<FpPost> doInBackground(Integer... taskIDs) {
        String urlString = "https://jsonplaceholder.typicode.com/posts";
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            failure = "Bad URL :(";
        }
        try {
            HttpsURLConnection myConnection = (HttpsURLConnection) url.openConnection();
            if (myConnection.getResponseCode() == 200) {
                InputStream responseBody = myConnection.getInputStream();
                InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                JsonReader jsonReader = new JsonReader(responseBodyReader);
                jsonReader.beginArray();
                String key, userIdString, idString, title, body;
                FpPost post;
                while (jsonReader.hasNext()) {
                    jsonReader.beginObject();
                    key = jsonReader.nextName();
                    if (key.equals("userId")) {
                        userIdString = jsonReader.nextString();
                        key = jsonReader.nextName();
                        if (key.equals("id")) {
                            idString = jsonReader.nextString();
                            key = jsonReader.nextName();
                            if (key.equals("title")) {
                                title = jsonReader.nextString();
                                key = jsonReader.nextName();
                                if (key.equals("body")) {
                                    body = jsonReader.nextString().replace("\n", " ");
                                    post = new FpPost(userIdString, idString, title, body);
                                    mArrayList.add(post);
                                    publishProgress(post);
                                }
                            }
                        }
                    }
                    jsonReader.endObject();
                }
                jsonReader.endArray();
            } else {
                failure = "No connection :(";
            }
        } catch (java.io.IOException e) {
            failure = "Connection problems :(";
        }
        return mArrayList;
    }

    @Override
    protected void onProgressUpdate(FpPost... posts) {
        mAdapter.mDataset.add(posts[0]);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostExecute(ArrayList<FpPost> result) {
        if (failure.length() > 0) {
            Toast.makeText(mActivity, failure, Toast.LENGTH_LONG).show();
        }
        mAdapter.notifyDataSetChanged();
        mActivity.findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
    }

}