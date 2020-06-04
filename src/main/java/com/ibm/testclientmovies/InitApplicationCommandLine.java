package com.ibm.testclientmovies;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ibm.testclientmovies.service.MovieService;

@Component
public class InitApplicationCommandLine implements CommandLineRunner{
	
	@Autowired
	private MovieService movieService;
   
    @Override
    public void run(String... args) throws Exception {
        String search = this.readInput();
        List<String> titles = this.movieService.findAllTitlesSorted(search);
        titles.forEach(System.out::println);
        System.exit(0);
    }

    private String readInput(){
        System.out.println("Busca:");
        Scanner scanner =  new Scanner(System.in);
        String title = scanner.nextLine();
        scanner.close();
        return title;
    }
}