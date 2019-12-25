package com.nikita.rss.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.nikita.rss.model.Post;

import java.util.List;

@Dao
public interface PostDAO {

    @Query("SELECT * FROM post WHERE feed_url=:feed_url")
    List<Post> getPosts(String feed_url);

    @Insert
    void insert(Post post);

    @Delete
    void delete(Post post);

    @Query("DELETE FROM post WHERE feed_url=:feed_url")
    void clearPosts(String feed_url);
}
