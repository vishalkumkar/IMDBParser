package com.ferris.parser.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.ferris.parser.SearchPeople;
import com.ferris.parser.persistence.DataStoreImpl;

public class SearchPeopleImpl implements SearchPeople{

	DataStoreImpl dataStore;
	private static SearchPeopleImpl search = null;


	private SearchPeopleImpl() {
		dataStore = DataStoreImpl.getInstance();
	}

	public static SearchPeopleImpl getInstance() {
		if( search == null ){
			search = new SearchPeopleImpl();
		}
		return search;
	}

	@Override
	public Set<String> getMoviesDirectedBy(String name) {
		return dataStore.getMoviesDirectedBy(name);
	}

	@Override
	public Set<String> getMoviesProducedBy(String name) {
		return dataStore.getMoviesProducedBy(name.toLowerCase());
	}

	@Override
	public Set<String> getMoviesWrittenBy(String name) {
		return dataStore.getMoviesWrittenBy(name.toLowerCase());
	}

	@Override
	public Set<String> getMoviesCastedBy(String name) {
		return dataStore.getMoviesCastActed(name.toLowerCase());
	}

	@Override
	public Set<String> getMoviesBy(String name) {
		return dataStore.getMoviesBy(name.toLowerCase());
	}

	/**
	 * 
	 * 
	 * @param name
	 * @return
	 */
	public Set<String> getMovieListBy(String name) {

		String names[] = name.split(" ");
		int count = names.length;
		HashSet<String> lists[] = new HashSet[count];
		for( int index = 0; index < count; index++ )
		{
			lists[index] = (HashSet<String>) getMoviesBy( names[index] );
			if( lists[ index ] == null )
				lists[ index ] = new HashSet<>();
		}

		return intersect(lists);
	}

	/**
	 * Perform intersecton of n lists.
	 * 
	 * @param lists
	 * @return
	 */
	private static Set<String> intersect(HashSet<String> lists[]) {

		if( lists.length == 1)
			return lists[0];
		HashSet<String> minLengthList = null;
		int minLength = Integer.MAX_VALUE;
		int processedList = -1;
		/*
		 *  find smallest list
		 */
		for(int index = 0; index < lists.length; index++ ) {
			if( lists[index].size() < minLength ) {
				minLength = lists[index].size();
				minLengthList = lists[index];
				processedList = index;
			}
		}

		HashSet<String> result = new HashSet<>();

		for(int index = 0; index < lists.length; index++ ) {
			if( index == processedList )
				continue;

			Iterator<String> setIterator = lists[index].iterator();
			while(setIterator.hasNext()) {
				String movieName = setIterator.next();
				if(minLengthList.contains(movieName)) {
					result.add(movieName);
				}
			}

			minLengthList = result;
		}
		
		return result;
	}
}
