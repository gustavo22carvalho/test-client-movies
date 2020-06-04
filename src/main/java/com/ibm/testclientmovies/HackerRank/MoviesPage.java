package com.ibm.testclientmovies.HackerRank;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MoviesPage {
    @JsonProperty("page")
    int page;
    
    @JsonProperty("per_page")
    int perPage;
    
    @JsonProperty("total")
    int total;
    
    @JsonProperty("total_pages")
    int totalPages;
    
    @JsonProperty("data")
    List<Movie> data;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<Movie> getData() {
        return data;
    }

    public void setData(List<Movie> data) {
        this.data = data;
    }

    
}