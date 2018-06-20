package pl.pjsiwinski.fakeposts.loaders;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import pl.pjsiwinski.fakeposts.R;
import pl.pjsiwinski.fakeposts.model.FpComment;

public class CommentsLoaderAcyncTask extends AsyncTask<Integer, FpComment, ArrayList<FpComment>> {

    private Activity mActivity;
    private ArrayList<FpComment> commentsList;
    private String failure;

    public CommentsLoaderAcyncTask(Activity activity) {
        mActivity = activity;
        commentsList = new ArrayList<>();
        failure = "";
    }

    @Override
    protected void onPreExecute() {
        mActivity.findViewById(R.id.postDetailsView).setVisibility(View.GONE);
        mActivity.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
    }

    @Override
    protected ArrayList<FpComment> doInBackground(Integer... postsIDs) {
        String urlString = "https://jsonplaceholder.typicode.com/comments?postId=" + postsIDs[0];
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
                String key, postIdString, idString, name, email, body;
                FpComment comment;
                while (jsonReader.hasNext()) {
                    jsonReader.beginObject();
                    key = jsonReader.nextName();
                    if (key.equals("postId")) {
                        postIdString = jsonReader.nextString();
                        key = jsonReader.nextName();
                        if (key.equals("id")) {
                            idString = jsonReader.nextString();
                            key = jsonReader.nextName();
                            if (key.equals("name")) {
                                name = jsonReader.nextString();
                                key = jsonReader.nextName();
                                if (key.equals("email")) {
                                    email = jsonReader.nextString();
                                    key = jsonReader.nextName();
                                    if (key.equals("body")) {
                                        body = jsonReader.nextString();
                                        comment = new FpComment(postIdString, idString, name, email, body);
                                        commentsList.add(comment);
                                        publishProgress(comment);
                                    }
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
            failure = "Couldn't load comments :(";
        }
        return commentsList;
    }

    @Override
    protected void onProgressUpdate(FpComment... comments) {
        View commentView = LayoutInflater.from(mActivity).inflate(R.layout.item_comment, null, false);
        ((TextView) commentView.findViewById(R.id.textViewEmail)).setText(comments[0].getEmail());
        ((TextView) commentView.findViewById(R.id.textViewName)).setText(comments[0].getName());
        ((TextView) commentView.findViewById(R.id.textViewBody)).setText(comments[0].getBody());
        ((LinearLayout)  mActivity.findViewById(R.id.postDetailsView) ).addView(commentView);
    }

    @Override
    protected void onPostExecute(ArrayList<FpComment> result) {
        if (failure.length() > 0) {
            Toast.makeText(mActivity, failure, Toast.LENGTH_LONG).show();
        } else {
            mActivity.findViewById(R.id.postDetailsView).setVisibility(View.VISIBLE);
        }
        mActivity.findViewById(R.id.progressBar).setVisibility(View.GONE);
    }

}
