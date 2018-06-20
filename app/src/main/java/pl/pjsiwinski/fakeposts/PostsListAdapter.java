package pl.pjsiwinski.fakeposts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import pl.pjsiwinski.fakeposts.model.FpPost;

public class PostsListAdapter extends RecyclerView.Adapter<PostViewHolder> {

    private RecyclerViewClickListener mListener;
    public ArrayList<FpPost> mDataset;

    public PostsListAdapter(RecyclerViewClickListener listener) {
        mDataset = new ArrayList<>();
        mListener = listener;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_post, parent, false);
        TextView textViewPostTitle = v.findViewById(R.id.textViewPostTitle);
        TextView textViewPostBody = v.findViewById(R.id.textViewPostBody);
        PostViewHolder vh = new PostViewHolder(v, textViewPostTitle, textViewPostBody, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        holder.textViewPostTitle.setText("\u25a0 " + mDataset.get(position).getTitle());
        holder.textViewPostBody.setText(mDataset.get(position).getBody());
    }

    @Override
    public long getItemId(int position) {
        return mDataset.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
