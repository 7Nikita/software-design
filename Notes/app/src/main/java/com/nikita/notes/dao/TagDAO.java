package com.nikita.notes.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.nikita.notes.model.Tag;

import java.util.List;

@Dao
public interface TagDAO {

    @Insert
    void insert(Tag tag);

    @Delete
    void delete(Tag tag);

    @Update
    void update(Tag tag);

    @Query("SELECT * FROM tag_table")
    LiveData<List<Tag>> getTags();

    @Query("SELECT * FROM tag_table ORDER BY title")
    LiveData<List<Tag>> getTagsSortedByTitle();

    @Query("SELECT * FROM tag_table")
    List<Tag> getTagList();
}
