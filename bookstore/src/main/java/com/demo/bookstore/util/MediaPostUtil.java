package com.demo.bookstore.util;

import java.util.List;
import java.util.stream.Collectors;

import com.demo.bookstore.models.MediaPost;

/**
 * @author Sujith Simon
 * Created on : 15/08/20
 */
public class MediaPostUtil {

    public static List<MediaPost> filterPosts(String sbin, List<MediaPost> mediaPosts) {
        return mediaPosts.stream().filter(mediaPost -> matches(sbin, mediaPost)).collect(Collectors.toList());
    }

    private static boolean matches(String sbin, MediaPost mediaPost) {
        String title = mediaPost.getTitle();
        if (title != null && title.contains(sbin)) {
            return true;
        }

        String post = mediaPost.getPost();
        return post != null && post.contains(sbin);
    }
}
