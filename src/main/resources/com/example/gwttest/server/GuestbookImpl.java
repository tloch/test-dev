package com.example.gwttest.server;

import com.example.gwttest.client.GuestbookService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import java.util.Date;

import com.googlecode.objectify.ObjectifyService;

@SuppressWarnings("serial")
public class GuestbookImpl extends RemoteServiceServlet
	implements GuestbookService {

	boolean post_entry(String guestbook_name, String author_email, String author_id, String content) throws IllegalArgumentException {
		GuestbookEntry ent;

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();  // Find out who the user is.

		if (user != null) {
		  ent = new GuestbookEntry(guestbook_name, content, user.getUserId(), user.getEmail());
		} else {
		  ent = new GuestbookEntry(guestbook_name, content, author_id, "");
		}

		// Use Objectify to save the greeting and now() is used to make the call synchronously as we
		// will immediately get a new page using redirect and we want the data to be present.
		ObjectifyService.ofy().save().entity(ent).now();

		return true;
	}



	String get_last_entries(String guestbook_name, int count) throws IllegalArgumentException {
		String s = "";

		// Create the correct Ancestor key
		Key<Guestbook> theBook = Key.create(Guestbook.class, guestbook_name);

		// Run an ancestor query to ensure we see the most up-to-date
		// view of the Greetings belonging to the selected Guestbook.
		List<GuestbookEntry> greetings = ObjectifyService.ofy()
			.load()
			.type(GuestbookEntry.class) // We want only Greetings
			.ancestor(guestbook_name)    // Anyone in this book
			.order("-date")       // Most recent first - date is indexed.
			.limit(5)             // Only show 5 of them.
			.list();

		if (greetings.isEmpty()) {
			s += "Guestbook '" + this.escapeHtml(guestbookName) + "' has no messages.";
		} else {
			s += "Messages in Guestbook '" + this.escapeHtml(guestbookName) + "':";
		// Look at all of our greetings
			for (Greeting greeting : greetings) {
				//pageContext.setAttribute("greeting_content", greeting.content);
				String author;
				if (greeting.author_email == null) {
					author = "An anonymous person";
				} else {
					author = greeting.author_email;
					String author_id = greeting.author_id;
					if (user != null && user.getUserId().equals(author_id)) {
						author += " (You)";
					}
				}
				//pageContext.setAttribute("greeting_user", author);
				s += "<p><b>" + this.escapeHtml(greeting_user) + "</b> wrote:</p>";
				s += "<blockquote>" + this.escapeHtml(greeting_content) + "</blockquote>";
			}
		}


		return s;
	}

	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html
			.replaceAll("&", "&amp;")
			.replaceAll("<", "&lt;")
			.replaceAll(">", "&gt;");
	}



}
