package com.nikita.rss.controller;


import com.nikita.rss.model.Post;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class RssController {

    public static final int MAX_CACHED_FEED = 3;

    private static InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        }
        catch (IOException e) {
            return null;
        }
    }

    public static Exception ParseRss(List<Post> allFeed, String feedUrl) {
        Exception exception = null;
        try {
            URL url = new URL(feedUrl);

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(getInputStream(url), "UTF_8");

            boolean insideItem = false;
            int eventType = parser.getEventType();
            String title = null, description = null, link = null, pubDate = null, mediaUrl = null;

            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                if (eventType == XmlPullParser.START_TAG) {
                    if (parser.getName().equalsIgnoreCase("item")) {
                        insideItem = true;
                    }
                    else if (parser.getName().equalsIgnoreCase("title") && insideItem) {
                        title = parser.nextText();
                    }
                    else if (parser.getName().equalsIgnoreCase("link") && insideItem) {
                        link = parser.nextText();
                    }
                    else if (parser.getName().equalsIgnoreCase("pubdate") && insideItem) {
                        pubDate = parser.nextText();
                    }
                    else if (parser.getName().equalsIgnoreCase("description") && insideItem) {
                        description = parser.nextText();
                    }
                    else if (parser.getName().equalsIgnoreCase("media:thumbnail") && insideItem) {
                        mediaUrl = parser.getAttributeValue(null, "url");
                    }
                }
                else if (eventType == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("item")) {
                    insideItem = false;
                    Post post = new Post();
                    post.setTitle(title);
                    post.setDescription(description);
                    post.setLink(link);
                    post.setFeedUrl(feedUrl);
                    post.setMediaUrl(mediaUrl);
                    try {
                        post.setPubDate(new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH).parse(pubDate));
                    }
                    catch (ParseException e) {
                        exception = e;
                    }
                    allFeed.add(post);
                }

                eventType = parser.next();
            }

        }
        catch (XmlPullParserException | IOException e) {
            exception = e;
        }
        catch (Exception e) {
            exception = e;
        }

        return exception;
    }
}
