package com.example.gwttest.server;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

import java.lang.String;
import java.util.Date;
import java.util.List;


@Entity
public class Greeting {
  @Parent Key<Guestbook> parent_guestbook;
  @Id public Long id;

  public String author_email;
  public String author_id;
  public String content;
  @Index public Date date;

  public Greeting() {
    this.date = new Date();
  }

  public Greeting(String book, String content) {
    this();
    if(book != null) {
      this.parent_guestbook = Key.create(Guestbook.class, book);
    } else {
      this.parent_guestbook = Key.create(Guestbook.class, "default");
    }
    this.content = content;
  }

  public Greeting(String book, String content, String id, String email) {
    this(book, content);
    this.author_email = email;
    this.author_id = id;
  }

}

