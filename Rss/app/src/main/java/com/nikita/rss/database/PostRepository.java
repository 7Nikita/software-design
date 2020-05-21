package com.nikita.rss.database;

import android.app.Application;

import com.nikita.rss.dao.PostDAO;
import com.nikita.rss.model.Post;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class PostRepository {

    private PostDAO postDAO;

    public PostRepository(Application application) {
        PostDatabase database = PostDatabase.getInstance(application);
        postDAO = database.postDAO();
    }

    public List<Post> getPosts(final String feedUrl) {
        Future<List<Post>> future = PostDatabase.databaseWriteExecutor.submit(() ->
                postDAO.getPosts(feedUrl));
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insert(Post post) {
        PostDatabase.databaseWriteExecutor.submit(() -> postDAO.insert(post));
    }

    public void clearPosts(String feedUrl) {
        Future future = PostDatabase.databaseWriteExecutor.submit(() -> postDAO.clearPosts(feedUrl));
        try {
            future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
