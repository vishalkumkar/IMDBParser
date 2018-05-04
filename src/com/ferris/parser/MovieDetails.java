package com.ferris.parser;

import java.util.ArrayList;
import java.util.List;

public class MovieDetails {
	
	String movieName;
	List<String> directorNames;
	List<String> writersNames;
	List<String> crew;
	List<String> producers;
	List<String> allMembers;
	
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public List<String> getDirectorNames() {
		return directorNames;
	}
	public void setDirectorNames(List<String> directorNames) {
		this.directorNames = directorNames;
	}
	public List<String> getWritersNames() {
		return writersNames;
	}
	public void setWritersNames(List<String> writersNames) {
		this.writersNames = writersNames;
	}
	public List<String> getCrew() {
		return crew;
	}
	public void setCrew(List<String> crew) {
		this.crew = crew;
	}
	public List<String> getProducers() {
		return producers;
	}
	public void setProducers(List<String> producers) {
		this.producers = producers;
	}
	public List<String> getAllMembers() {
		return allMembers;
	}
	public void setAllMembers(List<String> allMembers) {
		this.allMembers = allMembers;
	}
	
	public void addMembers(List<String> allMember) {
		if(this.allMembers == null) {
			this.allMembers = new ArrayList<>();
		}
		this.allMembers.addAll(allMember);
	}
}
