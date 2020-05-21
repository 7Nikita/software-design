package com.nikita.rss.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.nikita.rss.R;
import com.nikita.rss.activity.MainActivity;
import com.nikita.rss.fragment.WebViewFragment;
import com.nikita.rss.model.Post;
import com.nikita.rss.viewmodel.WebviewViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RssListviewAdapter extends RecyclerView.Adapter<RssListviewAdapter.RssViewHolder> {

    private final LayoutInflater inflater;
    private List<Post> posts;
    private Context context;

    public RssListviewAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public @NonNull RssViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.post_item, parent, false);
        return new RssViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RssViewHolder holder, int position) {
        if (posts != null) {
            Post current = posts.get(position);
            holder.feedTitleTextView.setText(current.getTitle());
            holder.feedPubDateTextView.setText(current.getPubDate().toString());
            holder.feedDescriptionTextView.setText(current.getDescription());
            holder.cardView.setOnClickListener((View view) -> {
                WebviewViewModel postViewModel = new ViewModelProvider((MainActivity) context).get(WebviewViewModel.class);
                postViewModel.setLink(current.getLink());

                FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                if (fragmentManager.findFragmentByTag("WebViewFragment") == null) {
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.addToBackStack(null);
                    WebViewFragment fragment = new WebViewFragment();
                    transaction.replace(R.id.frameLayout, fragment, "WebViewFragment");
                    transaction.commit();
                }
            });
            if (current.getMediaUrl() == null) {
                holder.imageView.setVisibility(View.GONE);
            }
            else {
                Picasso.get()
                        .load(current.getMediaUrl())
                        .resize(750, 750)
                        .centerCrop()
                        .into(holder.imageView);
            }
        }
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return posts != null ? posts.size() : 0;
    }

    class RssViewHolder extends RecyclerView.ViewHolder {
        private final TextView feedTitleTextView;
        private final TextView feedPubDateTextView;
        private final TextView feedDescriptionTextView;
        private final CardView cardView;
        private final ImageView imageView;

        private RssViewHolder(View itemView) {
            super(itemView);
            feedTitleTextView = itemView.findViewById(R.id.feedTitle);
            feedPubDateTextView = itemView.findViewById(R.id.feedPubDate);
            feedDescriptionTextView = itemView.findViewById(R.id.feedDescription);
            cardView = itemView.findViewById(R.id.card_view);
            imageView = itemView.findViewById(R.id.thumbnail);
        }
    }

}
