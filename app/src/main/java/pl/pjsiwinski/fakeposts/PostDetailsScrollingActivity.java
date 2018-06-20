package pl.pjsiwinski.fakeposts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import pl.pjsiwinski.fakeposts.loaders.CommentsLoaderAcyncTask;
import pl.pjsiwinski.fakeposts.loaders.PostDetailsLoaderAsyncTask;
import pl.pjsiwinski.fakeposts.model.FpPost;
import pl.pjsiwinski.fakeposts.model.FpUser;

public class PostDetailsScrollingActivity extends AppCompatActivity {

    private int postId;
    private FpPost mPost;
    private FpUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        postId = intent.getIntExtra("postId", 0);
        mPost = new FpPost();
        mUser = new FpUser();
        new PostDetailsLoaderAsyncTask(PostDetailsScrollingActivity.this, mPost, mUser).execute(postId);
        new CommentsLoaderAcyncTask(PostDetailsScrollingActivity.this).execute(postId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

}
