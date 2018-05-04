package com.ferris.parser;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class IMDBNextPageParser {
	
	private HashSet<String> links = null;
	private HashSet<String> visitedPages = null;

	public IMDBNextPageParser() {
		links = new HashSet<>();
		visitedPages = new HashSet<>();
	}

	public void storeMovieCreditPage( String URL ) {
		try {
			System.out.println("Parinsg URL ["+ URL+"]");
			Document document = Jsoup.connect(URL).get();

			List<Element> moviesList = document.select("div.lister-item-image");
			List<Element> nextPages = document.select("a.lister-page-next.next-page");
			for( Element movie: moviesList) {
				if( movie != null ){
					String link = movie.select("a[href]").get(0).attributes().get("href");
					String movieLink = "https://www.imdb.com/title/"+link.substring(7, 16)+"/fullcredits/?ref_=tt_ov_st_sm";
					links.add(movieLink);
				}
			}
			for( Element nextPage : nextPages)
			{
				if( nextPage != null ) {
					String nextpageLink = "https://www.imdb.com/search/title"+nextPage.select("a[href]").get(0).attributes().get("href");
					if(!visitedPages.contains(nextpageLink)){
						visitedPages.add(nextpageLink);
						storeMovieCreditPage(nextpageLink);
					}
				}
			}
		} 
		catch (IOException e)
		{
			System.err.println("For '" + URL + "': " + e.getMessage());
		}
	}

	public HashSet<String> getLinks() {
		return links;
	}

	public HashSet<String> getVisitedPages() {
		return visitedPages;
	}


	public static void main(String[] args) {
		//		System.setProperty("http.proxyHost", "www-proxy.us.oracle.com");
		//		System.setProperty("http.proxyPort", "80");
		//		System.setProperty("https.proxyHost", "www-proxy.us.oracle.com");
		//		System.setProperty("https.proxyPort", "80");
		//		System.setProperty("http.nonProxyHosts", "*.us.oracle.com|*.oraclecorp.com");

		System.out.println("/title/tt0015324/?ref_=adv_li_i".substring(7, 16));
		IMDBNextPageParser d = new IMDBNextPageParser();
		d.storeMovieCreditPage("https://www.imdb.com/search/title?groups=top_1000&sort=user_rating&view=simple");
		System.out.println(d.links.size());
		System.out.println(d.links);
	}
}
