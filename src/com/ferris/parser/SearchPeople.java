package com.ferris.parser;

import java.util.Set;

public interface SearchPeople {

	Set<String> getMoviesDirectedBy( String name );
	
	Set<String> getMoviesProducedBy( String name );
	
	Set<String> getMoviesWrittenBy( String name );
	
	Set<String> getMoviesCastedBy( String name );
	
	Set<String> getMoviesBy(String name);
}
