package com.example.gwttest.client;

import com.google.gwt.user.client.rpc.AsyncCallback;


@RemoteServiceRelativePath("greet")
public interface GuestbookService {
  boolean post_entry(String guestbook_name, String author_email, String author_id, String content, AsyncCallback<String> callback) throws IllegalArgumentException;
  String get_last_entries(String guestbook_name, int count, AsyncCallback<String> callback) throws IllegalArgumentException;
}
