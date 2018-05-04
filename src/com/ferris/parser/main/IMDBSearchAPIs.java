package com.ferris.parser.main;

import java.util.Set;

import com.ferris.parser.impl.BuildIndex;
import com.ferris.parser.impl.SearchPeopleImpl;

public class IMDBSearchAPIs extends BuildIndex {

	public static void main(String[] args) {
		crawlAndbuildDataStructure("https://www.imdb.com/search/title?groups=top_1000&sort=user_rating&view=simple");

		SearchPeopleImpl store = SearchPeopleImpl.getInstance();

		System.out.println("Movies That Mention chris hemsworth as crew - " + store.getMoviesCastedBy("chris hemsworth"));
		System.out.println("Movies directed by spielberg - "+ store.getMoviesDirectedBy("spielberg"));
		System.out.println("Movies produced by victoria - "+ store.getMoviesProducedBy("victoria"));
		System.out.println("Movies written by stephen - "+ store.getMoviesWrittenBy("stephen"));

		Set<String> movieNames = store.getMovieListBy("hanks");
		System.out.println("Movies containing both hanks and spielberg " +movieNames);
	}
}
