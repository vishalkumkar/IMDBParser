package com.ferris.parser.impl;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.ferris.parser.IMDBNextPageParser;
import com.ferris.parser.IMDBCreditPageParser;
import com.ferris.parser.MovieDetails;
import com.ferris.parser.persistence.DataStoreImpl;

public class BuildIndex {

	protected static void crawlAndbuildDataStructure(String startURL) {
		IMDBNextPageParser pageParser = new IMDBNextPageParser();
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println("               Scanning all Pages");
		System.out.println("-----------------------------------------------------------------------------");

		pageParser.storeMovieCreditPage( startURL );
	
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println("               Hang Tight.. Parsing All Pages");
		System.out.println("-----------------------------------------------------------------------------");

		final IMDBCreditPageParser recordNames = new IMDBCreditPageParser();
	
		ExecutorService executorService = Executors.newFixedThreadPool(15);
		for( final String movieCreditPage : pageParser.getLinks()){
			Runnable worker = new Runnable() {
				@Override
				public void run() {
					MovieDetails movie = recordNames.recordNames(movieCreditPage);
					buildReverseIndex(recordNames, movieCreditPage, "director", movie);
					buildReverseIndex(recordNames, movieCreditPage, "producer", movie);
					buildReverseIndex(recordNames, movieCreditPage, "writer", movie);
					buildReverseIndex(recordNames, movieCreditPage, "cast", movie);
					buildReverseIndex(recordNames, movieCreditPage, "all", movie);
				}
			};
	
			executorService.execute(worker);
		}
		executorService.shutdown();
	
		try {
			executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			System.out.println("Error while waiting " + e);
		}
	}

	private static void buildReverseIndex(IMDBCreditPageParser recordNames, String movieCreditPage, String castType, MovieDetails movieDetails) {
	
		List<String> people = null;
		if(castType.equals("director")){
			people = movieDetails.getDirectorNames();
			if( people != null ) {
				for( String fullName : people){
					if( fullName != null && !fullName.equals("")) {
						fullName = fullName.toLowerCase();
						for( String individualName : fullName.split(" ")) {
							DataStoreImpl.getInstance().addMovietoDirector(individualName, movieDetails.getMovieName());
						}
						DataStoreImpl.getInstance().addMovietoDirector(fullName, movieDetails.getMovieName());
					}
				}
			}
		} else if(castType.equals("producer")){
			people = movieDetails.getProducers();
			if( people != null ) {
				for( String fullName : people){
					if( fullName != null && !fullName.equals("")) {
						fullName = fullName.toLowerCase();
						for( String individualName : fullName.split(" ")) {
							DataStoreImpl.getInstance().addMovietoProducer(individualName, movieDetails.getMovieName());
						}
						DataStoreImpl.getInstance().addMovietoProducer(fullName, movieDetails.getMovieName());
					}
				}
			}
		} else if(castType.equals("writer")){
			people = movieDetails.getWritersNames();
			if( people != null ) {
	
				for( String fullName : people){
					if( fullName != null && !fullName.equals("")) {
						fullName = fullName.toLowerCase();
						for( String individualName : fullName.split(" ")) {
							DataStoreImpl.getInstance().addMovietoWriter(individualName, movieDetails.getMovieName());
						}
						DataStoreImpl.getInstance().addMovietoWriter(fullName, movieDetails.getMovieName());
					}
				}
			}
		} else if(castType.equals("cast")){
			people = movieDetails.getCrew();
			if( people != null ) {
	
				for( String fullName : people){
					if( fullName != null && !fullName.equals("")) {
						fullName = fullName.toLowerCase();
						for( String individualName : fullName.split(" ")) {
							DataStoreImpl.getInstance().addMovietoCast(individualName, movieDetails.getMovieName());
						}
						DataStoreImpl.getInstance().addMovietoCast(fullName, movieDetails.getMovieName());
					}
				}
			}
		} else if(castType.equals("all")){
			people = movieDetails.getAllMembers();
			if( people != null ) {
				for( String fullName : people){
					if( fullName != null && !fullName.equals("")) {
						fullName = fullName.toLowerCase();
						for( String individualName : fullName.split(" ")) {
							DataStoreImpl.getInstance().addMovietoAll(individualName, movieDetails.getMovieName());
						}
						DataStoreImpl.getInstance().addMovietoAll(fullName, movieDetails.getMovieName());
					}
				}			
			}
		}
	}

	public BuildIndex() {
		super();
	}

}