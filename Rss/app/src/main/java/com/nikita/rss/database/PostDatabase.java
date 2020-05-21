package com.nikita.rss.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.nikita.rss.dao.PostDAO;
import com.nikita.rss.model.Post;
import com.nikita.rss.util.TimestampConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {
                Post.class
        },
        version = 1
)
@TypeConverters(
        {
                TimestampConverter.class
        }
)
public abstract class PostDatabase extends RoomDatabase {

    private static volatile PostDatabase instance;

    private static final String DB_NAME = "post_database.db";

    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized PostDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static PostDatabase create(final Context context) {
        return Room.databaseBuilder(context, PostDatabase.class, DB_NAME).build();
    }

    public abstract PostDAO postDAO();
}
