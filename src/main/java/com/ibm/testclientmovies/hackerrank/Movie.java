package com.ibm.testclientmovies.hackerrank;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Movie {

    @JsonProperty("Title")
    String title;

    public Movie() {
    }

    public Movie(String title) {
		this.title = title;
	}

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    
}
