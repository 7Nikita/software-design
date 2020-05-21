package com.nikita.rss.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nikita.rss.database.PostRepository;
import com.nikita.rss.model.Post;

import java.util.List;

public class PostViewModel extends AndroidViewModel {

    private PostRepository postRepository;

    private MutableLiveData<List<Post>> posts = new MutableLiveData<>();

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public PostViewModel(Application application) {
        super(application);
        postRepository = new PostRepository(application);
        isLoading.setValue(false);
    }

    public LiveData<List<Post>> getAll() {
        return posts;
    }

    public List<Post> getPosts(String feedUrl) {
        return postRepository.getPosts(feedUrl);
    }

    public void setPosts(List<Post> p) {
        posts.setValue(p);
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

    public void setIsLoading(Boolean val) {
        isLoading.setValue(val);
    }

    public void savePost(Post post) {
        postRepository.insert(post);
    }

    public void clearPosts(String feedUrl) {
        postRepository.clearPosts(feedUrl);
    }
}
