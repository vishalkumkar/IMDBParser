package com.ferris.parser;

import java.util.Set;

public interface DataStore {
	Set<String> getMoviesDirectedBy( String person );
	
	Set<String> getMoviesProducedBy( String person );

	Set<String> getMoviesWrittenBy( String person );

	Set<String> getMoviesCastActed( String person );

	Set<String> getMoviesBy( String person );

	void addMovietoDirector( String directorName, String movieName );

	void addMovietoProducer( String producerName, String movieName );

	void addMovietoWriter( String writerName, String movieName );

	void addMovietoCast( String castName, String movieName );

	void addMovietoAll( String personName, String movieName );
}
