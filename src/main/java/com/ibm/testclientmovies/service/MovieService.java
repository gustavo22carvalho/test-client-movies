package com.ibm.testclientmovies.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.testclientmovies.hackerrank.ApiMoviesClient;
import com.ibm.testclientmovies.hackerrank.Movie;
import com.ibm.testclientmovies.hackerrank.MoviesPage;

@Service
public class MovieService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MovieService.class);

	 @Autowired
	 private ApiMoviesClient apiMoviesClient ;
	
	 public List<String> findAllTitles(String search) {
		 if(search.trim().isEmpty()) {
			 throw new IllegalArgumentException("Chave para busca não pode ser vazia");
		 }
		 	
        LOGGER.debug("Consultando pagina: 1");
        MoviesPage page1 = this.apiMoviesClient.findMovies(search, 1);
        List<String> titles = new ArrayList<>(page1.getTotal());
        titles.addAll(extractTitles(page1.getData()));
	        
        if(page1.getTotalPages() > 1){
            for(int i = 2; i <= page1.getTotalPages(); i++){
                LOGGER.debug(String.format("Consultando pagina %s / %s", i, page1.getTotalPages()));
                MoviesPage currentPage = this.apiMoviesClient.findMovies(search, i);
                titles.addAll(extractTitles(currentPage.getData()));
            }
        }
        return titles;
    }

    private List<String> sortTitles(List<String> titles) {
        return titles.stream().sorted((t1, t2) -> t1.compareToIgnoreCase(t2)).collect(Collectors.toList());
    }

    private List<String> extractTitles(List<Movie> list) {
        return list.stream().map(Movie::getTitle).collect(Collectors.toList());
    }

	public List<String> findAllTitlesSorted(String search) {
		return this.sortTitles(this.findAllTitles(search));
	}
    
	    
}
