package com.nikita.rss.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nikita.rss.R;
import com.nikita.rss.adapter.RssListviewAdapter;
import com.nikita.rss.controller.RssController;
import com.nikita.rss.controller.network.NetworkController;
import com.nikita.rss.controller.network.NetworkState;
import com.nikita.rss.model.Post;
import com.nikita.rss.util.NetworkIntentService;
import com.nikita.rss.viewmodel.PostViewModel;

import java.util.ArrayList;
import java.util.List;

public class RssListviewFragment extends Fragment {

    private SearchView searchView;
    private TextView networkTextView;
    private ProgressBar progressBar;

    private static PostViewModel postViewModel;
    private GetRssFeedTask task;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rss_listview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        networkTextView = view.findViewById(R.id.internet);
        searchView = view.findViewById(R.id.search);
        progressBar = view.findViewById(R.id.progressBar);

        Handler handler = new Handler((Message msg) -> {
            Bundle data = msg.getData();
            boolean isConnected = data.getBoolean("isConnected");
            networkTextView.setText(isConnected ? "Connected" : "No Internet");
            return true;
        });

        Intent intent = new Intent(getActivity(), NetworkIntentService.class);
        intent.putExtra("messenger", new Messenger(handler));
        getActivity().startService(intent);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        RssListviewAdapter adapter = new RssListviewAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        postViewModel = new ViewModelProvider(getActivity()).get(PostViewModel.class);
        postViewModel.isLoading().observe(getViewLifecycleOwner(), (Boolean isLoading) -> {
            if (isLoading) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });

        postViewModel.getAll().observe(getViewLifecycleOwner(), adapter::setPosts);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (NetworkController.getConnectivityStatus(getContext()) == NetworkState.NOT_CONNECTED) {
                    postViewModel.setPosts(postViewModel.getPosts(query));
                } else {
                    task = new GetRssFeedTask(getContext());
                    task.execute(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (task != null) {
            task.cancel(false);
        }
        Intent service = new Intent(getActivity(), NetworkIntentService.class);
        getActivity().stopService(service);
    }

    private static class GetRssFeedTask extends AsyncTask<String, Void, Exception> {
        private List<Post> posts = new ArrayList<>();
        private String feedUrl;
        private Context context;

        public GetRssFeedTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            postViewModel.setIsLoading(true);
        }

        @Override
        protected Exception doInBackground(String... params) {
            feedUrl = params[0];
            return RssController.ParseRss(posts, feedUrl);
        }

        @Override
        protected void onPostExecute(Exception exception) {
            super.onPostExecute(exception);
            if (exception != null) {
                postViewModel.setIsLoading(false);
                Toast toast = Toast.makeText(context, exception.toString(), Toast.LENGTH_LONG);
                toast.show();
                return;
            }
            postViewModel.setPosts(posts);
            postViewModel.clearPosts(feedUrl);
            int i = 1;
            for (Post post: posts) {
                postViewModel.savePost(post);
                if (i == RssController.MAX_CACHED_FEED) {
                    break;
                }
                ++i;
            }
            postViewModel.setIsLoading(false);
        }
    }

}
