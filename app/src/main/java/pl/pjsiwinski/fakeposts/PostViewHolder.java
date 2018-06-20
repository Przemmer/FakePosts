package pl.pjsiwinski.fakeposts;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private RecyclerViewClickListener mListener;
    private View mView;
    public TextView textViewPostTitle;
    public TextView textViewPostBody;


    public PostViewHolder(View v, TextView textViewPostTitle, TextView textViewPostBody, RecyclerViewClickListener listener) {
        super(v);
        mListener = listener;
        this.mView = v;
        this.textViewPostTitle = textViewPostTitle;
        this.textViewPostBody = textViewPostBody;
        v.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mListener.onClick(view, getAdapterPosition());
    }

}
