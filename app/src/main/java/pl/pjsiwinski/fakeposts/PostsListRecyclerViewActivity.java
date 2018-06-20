package pl.pjsiwinski.fakeposts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import pl.pjsiwinski.fakeposts.loaders.PostsListLoaderAsyncTask;

public class PostsListRecyclerViewActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private PostsListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_list_recycler_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.posts_recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent myIntent = new Intent(PostsListRecyclerViewActivity.this, PostDetailsScrollingActivity.class);
                myIntent.putExtra("postId", (int) mAdapter.getItemId(position));
                startActivity(myIntent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        };

        mAdapter = new PostsListAdapter(listener);
        mRecyclerView.setAdapter(mAdapter);
        new PostsListLoaderAsyncTask(PostsListRecyclerViewActivity.this, mAdapter).execute(1);
    }

}
