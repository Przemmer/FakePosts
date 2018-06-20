package pl.pjsiwinski.fakeposts.loaders;

import android.app.Activity;
import android.content.Intent;
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

public class UserLoaderAsyncTask extends AsyncTask<Integer, Void, FpUser> {

    private Activity mActivity;
    private FpUser mUser;
    private String failure;

    public UserLoaderAsyncTask(Activity activity, FpUser user) {
        mActivity = activity;
        mUser = user;
        failure = "";
    }

    @Override
    protected void onPreExecute() {
        mActivity.findViewById(R.id.postDetailsView).setVisibility(View.GONE);
        mActivity.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
    }

    @Override
    protected FpUser doInBackground(Integer... usersIDs) {
        String urlString = "https://jsonplaceholder.typicode.com/users/" + usersIDs[0];
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
                String key, idString, name, username, email;
                jsonReader.beginObject();
                key = jsonReader.nextName();
                if (key.equals("id")) {
                    idString = jsonReader.nextString();
                    key = jsonReader.nextName();
                    if (key.equals("name")) {
                        name = jsonReader.nextString();
                        key = jsonReader.nextName();
                        if (key.equals("username")) {
                            username = jsonReader.nextString();
                            key = jsonReader.nextName();
                            if (key.equals("email")) {
                                email = jsonReader.nextString();
                                mUser.setId(idString);
                                mUser.setName(name);
                                mUser.setUsername(username);
                                mUser.setEmail(email);
                            }
                        }
                    }
                }
                jsonReader.close();
            } else {
                failure = "No connection :(";
            }
        } catch (java.io.IOException e) {
            failure = "Couldn't load user data :(";
        }
        return mUser;
    }

    @Override
    protected void onProgressUpdate(Void... voids) {
    }

    @Override
    protected void onPostExecute(final FpUser result) {
        if (failure.length() > 0) {
            Toast.makeText(mActivity, failure, Toast.LENGTH_LONG).show();
        } else {
            ((TextView) mActivity.findViewById(R.id.textViewUser)).setText(result.getName() + " (" + result.getUsername() + ")");
            mActivity.findViewById(R.id.textViewUser).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                    emailIntent.setType("plain/text");
                    String[] emails = new String[1];
                    emails[0] = result.getEmail();
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, emails);
                    mActivity.startActivity(Intent.createChooser(emailIntent, "Send email to user..."));

                }
            });
            mActivity.findViewById(R.id.postDetailsView).setVisibility(View.VISIBLE);
        }
        mActivity.findViewById(R.id.progressBar).setVisibility(View.GONE);
    }

}