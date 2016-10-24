package com.example.gwttest.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;



@RemoteServiceRelativePath("greet")
public interface GuestbookService extends RemoteService {
  boolean post_entry(String guestbook_name, String author_email, String author_id, String content) throws IllegalArgumentException;
  String get_last_entries(String guestbook_name, int count) throws IllegalArgumentException;
}
