package com.ibm.testclientmovies.HackerRank;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "https://jsonmock.hackerrank.com/api/movies", name = "apiMovies")
public interface ApiMoviesClient {
    
    @GetMapping(path = "/search")
    MoviesPage findMovies(@RequestParam("Title") String title, @RequestParam("page") int pageNumber);
}