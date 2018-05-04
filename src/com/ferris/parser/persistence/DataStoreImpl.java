package com.ferris.parser.persistence;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.ferris.parser.DataStore;

public class DataStoreImpl implements DataStore {

	final static ConcurrentHashMap<String, Set<String>> directorToMoviemapping = new ConcurrentHashMap<>();
	final static ConcurrentHashMap<String, Set<String>> producerToMoviemapping = new ConcurrentHashMap<>();
	final static ConcurrentHashMap<String, Set<String>> writerToMoviemapping = new ConcurrentHashMap<>();
	final static ConcurrentHashMap<String, Set<String>> crewToMoviemapping = new ConcurrentHashMap<>();
	final static ConcurrentHashMap<String, Set<String>> allMembers = new ConcurrentHashMap<>();
	
	private static DataStoreImpl dataStore = null;
	
	private DataStoreImpl() {
	}
	
	public static DataStoreImpl getInstance() {
		if( dataStore == null ) {
			dataStore = new DataStoreImpl();
		}
		return dataStore;
	}
	
	
	public Set<String> getMoviesDirectedBy( String person ) {
		return directorToMoviemapping.get(person);
	}
	
	public Set<String> getMoviesProducedBy( String person ) {
		return producerToMoviemapping.get(person);
	}

	public Set<String> getMoviesWrittenBy( String person ) {
		return writerToMoviemapping.get(person);
	}

	public Set<String> getMoviesCastActed( String person ) {
		return crewToMoviemapping.get(person);
	}

	public Set<String> getMoviesBy( String person ) {
		return allMembers.get(person);
	}

	public void addMovietoDirector( String directorName, String movieName ) {
		if( directorToMoviemapping.get(directorName) == null)
			directorToMoviemapping.put(directorName, new HashSet<String>());
		Set<String> movies = directorToMoviemapping.get( directorName );
		movies.add( movieName );
		directorToMoviemapping.put( directorName, movies );
	}

	public void addMovietoProducer( String producerName, String movieName ) {
		if( producerToMoviemapping.get(producerName) == null)
			producerToMoviemapping.put(producerName, new HashSet<String>());
		Set<String> movies = producerToMoviemapping.get( producerName );
		movies.add( movieName );
		producerToMoviemapping.put( producerName, movies );
	}

	public void addMovietoWriter( String writerName, String movieName ) {
		if( writerToMoviemapping.get(writerName) == null)
			writerToMoviemapping.put(writerName, new HashSet<String>());
		Set<String> movies = writerToMoviemapping.get( writerName );
		movies.add( movieName );
		writerToMoviemapping.put( writerName, movies );
	}

	public void addMovietoCast( String castName, String movieName ) {
		if( crewToMoviemapping.get(castName) == null)
			crewToMoviemapping.put(castName, new HashSet<String>());
		Set<String> movies = crewToMoviemapping.get( castName );
		movies.add( movieName );
		crewToMoviemapping.put( castName, movies );
	}

	public void addMovietoAll( String personName, String movieName ) {
		if( allMembers.get(personName) == null)
			allMembers.put(personName, new HashSet<String>());
		Set<String> movies = allMembers.get( personName );
		movies.add( movieName );
		allMembers.put( personName, movies );
	}
}
