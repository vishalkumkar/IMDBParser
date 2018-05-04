How to Run:

1. Direct from jar:

java -jar imdbsearch.jar "https://www.imdb.com/search/title?groups=top_1000&sort=user_rating&view=simple" "spielberg hanks"

Note: the above jars just exposes 1 search api.

For other api's for search check class com.ferris.parser.main.IMDBSearchAPIs

2. if running from eclipse :

Make sure jsoup-1.11.3.jar is available in classpath for code compilation.

Run class 
com.ferris.parser.main.IMDBSearchAPIs

======================================

the data structure is build for following categories:
1. Directed By
2. Produced By
3. Writer
4. Cast
5. combination of all above categories into 1

Data structure looks as below:


{

	"person-name1" : [ "movie1", "movie2", "movie3"... ]
  
	"person-name2" : [ "movie1", "movie2", ... ]
  
}



CODE STEP BY STEP:

1. Start with initial page, if there is next page linked then get the page like. for the given link, there are 20 pages,
So we crawl all the 20 pages. While Scanning these pages , we also record the movie page link.

2. Now that we have page links for all movies, we can crawl the individual movie page and record following details:
 	a. Director
 	b. Producer
 	c. Writer
 	d. Cast (full)
  
 3. Once we have the movie details(above details), we create a reverse lookup Index.
 
  for example: 
  
	{
  
		"person-name1" : [ "movie1", "movie2", "movie3"... ]
    
		"person-name2" : [ "movie1", "movie2", ... ]
    
	}
Note: Chris Hemsworth, will have 3 entries and storage of key will always be in lowercase
	eg:
  
	{
  
		"chris" : [ "Thor", "The Avengers", ....]
    
		"hemsworth" : [ "Thor", "The Avengers", ....]
    
		"chris hemsworth" : [ "Thor", "The Avengers", ....]
    
	}

4. Now we expose below 6 api's

	a. getMoviesDirectedBy(name) - searches in directors categories
  
	b. getMoviesProducedBy(name) - searches in producers categories
  
	c. getMoviesWrittenBy(name) - searches in writers categories
  
	d. getMoviesCastedBy(name) - searches in cast categories
  
	e  getMoviesBy(name)        - searches in all categories without performing any intersection of lists
  
	f. getMovieListBy(name)  -  searches in all categories. Performs intersection of all lists 
  
		eg: spielberg hanks chris
    
		step1: get movie list of spielberg - list1
    
		step2: get movie list of hanks - list2, chris - list3
    
		step3: find the smallest list eg: minList = list1
    
		step4: perform hashbased check. i.e iterate over list2 and check if words from list2 are present in minList.
    
				if yes then add word to result.
        
		step5: Now assign minList = result and continue till you scan all n lists (for n words). 
    
		
		
	Performance for Intersection:
  
	 for n lists
   
	 m words inside each list
   
	   where m1 = length of words in list1
     
	   		 m2 = length of words in list2
         
	 Time Complexity O( (n-1) * (sum of length of each list leaving smallest length list))
   
	     => O( (n-1) sum(m1+m2+m3...mn))
       
	 Space Complexity O( 2 * smallest size list ) i.e 1 list for storing min length list 2 for storing intersecion of 2 list.
   
		            
	
IMPROVEMENTS:

1. Code could be bit more Structured eg: using design patterns etc, adding comments, test cases

2. Parsing could be improved.

3. currently starting fixed number of threads to parse the pages. that could too be improved.

   Threads are currently fixed at 15, so that IMDB do not block IP (Rate Limiting)
   
4. If the movies list is sorted we can use heap based approach to find intersection of n lists.

	 for n words, there will be n lists
   
	 l = words in each list consider l to be max word length
   
 	 Time Complexity O( log n * (length of list having max words) + l log l )
   
 	 			=> O( l log n + (n * l) log l)
 	 			
 	 			Here l log n => heap based sorting
        
 	 			     l log l => sorting each list
 	 			  
	 Space Complexity O( n ) 
 
5. If there is memory constraint, then we have to store the hashmap datastructure on disk ( serialized ) , and store most frequent queried keys in memory ( LRU implementation ).

6. Furthur, we can improve this code to build n-gram index.

7. Performance benmarking for different techniques. we can try building

   a. TRIE  ( space vs time )
   
   
