package pl.pjsiwinski.fakeposts.loaders;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import pl.pjsiwinski.fakeposts.R;
import pl.pjsiwinski.fakeposts.model.FpPost;
import pl.pjsiwinski.fakeposts.model.FpUser;

public class PostDetailsLoaderAsyncTask extends AsyncTask<Integer, Void, FpPost> {

    private Activity mActivity;
    private FpPost mPost;
    private FpUser mUser;
    private String failure;

    public PostDetailsLoaderAsyncTask(Activity activity, FpPost post, FpUser user) {
        mActivity = activity;
        mPost = post;
        mUser = user;
        failure = "";
    }

    @Override
    protected void onPreExecute() {
        mActivity.findViewById(R.id.postDetailsView).setVisibility(View.GONE);
        mActivity.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
    }

    @Override
    protected FpPost doInBackground(Integer... postsIDs) {
        String urlString = "https://jsonplaceholder.typicode.com/posts/" + postsIDs[0];
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
                String key, userIdString, idString, title, body;
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
                                body = jsonReader.nextString();
                                mPost.setUserId(userIdString);
                                mPost.setId(idString);
                                mPost.setTitle(title);
                                mPost.setBody(body);
                            }
                        }
                    }
                }
                jsonReader.endObject();
            } else {
                failure = "No connection :(";
            }
        } catch (java.io.IOException e) {
            failure = "Connection problems :(";
        }
        return mPost;
    }

    @Override
    protected void onProgressUpdate(Void... voids) {
    }

    @Override
    protected void onPostExecute(FpPost result) {
        if (failure.length() > 0) {
            Toast.makeText(mActivity, failure, Toast.LENGTH_LONG).show();
        } else {
            new UserLoaderAsyncTask(mActivity, mUser).execute(mPost.getUserId());
            ((TextView) mActivity.findViewById(R.id.textViewPostTitle)).setText(result.getTitle());
            ((TextView) mActivity.findViewById(R.id.textViewPostBody)).setText(result.getBody());
        }
    }

}