package com.nikita.rss.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.UUID;
import java.util.Date;

@Entity(
        tableName = "post",
        indices = {
                @Index(value = "id", unique = true)
        }
)
public class Post {

        @NonNull
        @PrimaryKey
        @ColumnInfo(name = "id")
        private String id = UUID.randomUUID().toString();

        @ColumnInfo(name = "title")
        private String title;

        @ColumnInfo(name = "ling")
        private String link;

        @ColumnInfo(name = "description")
        private String description;

        @ColumnInfo(name = "media_url")
        private String mediaUrl;

        @ColumnInfo(name = "pub_date")
        private Date pubDate;

        @ColumnInfo(name = "feed_url")
        private String feedUrl;

        public String getId() {
                return id;
        }

        public void setId(String id) {
                this.id = id;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public String getLink() {
                return link;
        }

        public void setLink(String link) {
                this.link = link;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public String getMediaUrl() {
                return mediaUrl;
        }

        public void setMediaUrl(String mediaUrl) {
                this.mediaUrl = mediaUrl;
        }

        public Date getPubDate() {
                return pubDate;
        }

        public void setPubDate(Date pubDate) {
                this.pubDate = pubDate;
        }

        public String getFeedUrl() {
                return feedUrl;
        }

        public void setFeedUrl(String feedUrl) {
                this.feedUrl = feedUrl;
        }
}
