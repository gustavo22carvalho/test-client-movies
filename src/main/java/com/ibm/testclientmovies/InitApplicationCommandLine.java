package com.ibm.testclientmovies;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.ibm.testclientmovies.HackerRank.ApiMoviesClient;
import com.ibm.testclientmovies.HackerRank.Movie;
import com.ibm.testclientmovies.HackerRank.MoviesPage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitApplicationCommandLine implements CommandLineRunner{

    @Autowired
    private ApiMoviesClient apiMoviesClient ;

    @Override
    public void run(String... args) throws Exception {
        String search = this.readInput();
        System.err.println("consultado " + 1);
        MoviesPage page1 = this.apiMoviesClient.findMovies(search, 1);
        List<String> titles = new ArrayList<>(page1.getTotal());
        titles.addAll(extractTitles(page1.getData()));
        
        if(page1.getTotalPages() > 1){
            for(int i = 2; i <= page1.getTotalPages(); i++){
                System.err.println("consultado " + i);
                MoviesPage currentPage = this.apiMoviesClient.findMovies(search, i);
                titles.addAll(extractTitles(currentPage.getData()));
            }
        }

        sortTitles(titles).forEach(System.out::println);
        System.exit(0);
    }

    private List<String> sortTitles(List<String> titles) {
        return titles.stream().sorted((t1, t2) -> t1.compareToIgnoreCase(t2)).collect(Collectors.toList());
    }

    private List<String> extractTitles(List<Movie> list) {
        return list.stream().map(Movie::getTitle).collect(Collectors.toList());
    }
    
    private String readInput(){
        System.out.println("Busca:");
        Scanner scanner =  new Scanner(System.in);
        String title = scanner.nextLine();
        scanner.close();
        if(title.isEmpty()){
            throw new IllegalArgumentException("Chave para busca não pode ser vazia");
        }
        return title;
    }
}