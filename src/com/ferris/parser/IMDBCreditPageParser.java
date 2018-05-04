package com.ferris.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class IMDBCreditPageParser {

	public static void main(String[] args) {		
		IMDBCreditPageParser parser = new IMDBCreditPageParser();
		parser.recordNames("https://www.imdb.com/title/tt0111161/fullcredits/?ref_=tt_ov_st_sm");
	}

	public MovieDetails recordNames( String URL ) 
	{
		MovieDetails movie = new MovieDetails();

		try {
			Document document = Jsoup.connect(URL).get();
			List<Element> movieNames = document.select("div.parent");
			movie.setMovieName(movieNames.get(0).text());
			List<Element> castTypes = document.select("h4.dataHeaderWithBorder");
			
			for(Element castType: castTypes) {
				String type = castType.childNodes().get(0).toString();
				
				if( type.startsWith("Directed by") )
				{
					List<String> directors = new ArrayList<>();
					for( Element row : castType.nextElementSibling().select("tr") )
					{
						if( row.select("a[href]")!=null && !row.select("a[href]").text().equals("") )
							directors.add(row.select("a[href]").text());
					}
					movie.setDirectorNames(directors);
					movie.addMembers(directors);

				}
				else if( type.startsWith("Writing Credits") )
				{
					List<String> writers = new ArrayList<>();
					for( Element row : castType.nextElementSibling().select("tr") )
					{
						if(row.select("a[href]")!=null && !row.select("a[href]").text().equals(""))
							writers.add(row.select("a[href]").text());
					}
					movie.setWritersNames(writers);
					movie.addMembers(writers);

				} 
				else if( type.startsWith(" Cast ") )
				{
					List<String> cast = new ArrayList<>();
					for( Element row : castType.nextElementSibling().select("tr") )
					{
						if(row.select("a[href]")!=null && !row.select("a[href]").select("span.itemprop").text().equals("")){
							cast.add(row.select("a[href]").select("span.itemprop").text());
						}
					}
					movie.setCrew(cast);
					movie.addMembers(cast);
				} 
				else if( type.startsWith("Produced by") )
				{
					List<String> producers = new ArrayList<>();
					for( Element row : castType.nextElementSibling().select("tr") )
					{
						if(row.select("a[href]")!=null && !row.select("a[href]").text().equals(""))
							producers.add(row.select("a[href]").text());
					}
					movie.setProducers(producers);
					movie.addMembers(producers);
				} 				
			}	
		} catch (IOException e) {
			e.printStackTrace();
		}
		return movie;
	}
}
