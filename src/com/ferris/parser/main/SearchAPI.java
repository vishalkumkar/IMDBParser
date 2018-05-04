package com.ferris.parser.main;

import java.util.Set;

import com.ferris.parser.impl.BuildIndex;
import com.ferris.parser.impl.SearchPeopleImpl;

public class SearchAPI extends BuildIndex{

	public static void main(String[] args) {
		

		SearchPeopleImpl store = SearchPeopleImpl.getInstance();
		
		StringBuffer words = new StringBuffer();
		
		for( int index = 1 ; index < args.length ; index++ ) {
			words.append(args[index]);
			words.append(" ");
		}

		System.out.println(" Will search movies associated with "+ words);
		
		crawlAndbuildDataStructure(args[0]);

		Set<String> movieNames = store.getMovieListBy(words.toString().trim());
		System.out.println( movieNames );

	}

}
