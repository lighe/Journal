package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

@With({ApplicationController.class, Security.class})
public class SearchController extends Controller {

	public static void index() {
		render();
	}
	
	public static void search(String search){
		ArrayList<String> searchArray = partitionString(search);

		ArrayList<Article> releventArticleList = new ArrayList<Article>();
		/*
		 * For authors
		 */
		List<User> userList = User.all().fetch();
		//Iterate through all users comparing names to all the search words
		ArrayList<User> releventUserList = new ArrayList<User>();
		for(int x=0; x<searchArray.size(); x++){
			String srch = searchArray.get(x);
			for(int y=0; y<userList.size(); y++){
				User usr = userList.get(y);
				if(usr.name.contains(srch)){
					releventUserList.add(usr);
				}
			}
		}
		//Get articles by users and store them in the relivent article list
		for(int x=0; x<releventUserList.size();x++){
			User usr = releventUserList.get(x);
			List<Article> artList = Article.find("user", usr).fetch();
			for(int z=0; z<artList.size();z++){
				releventArticleList.add(artList.get(z));
			}
		}
		
		/*
		 * For Articles
		 */
		List<Article> publishedList = Article.find("published", true).fetch();
		//Iterate through all the Articles list comparing titles to all the search words
		for(int x=0; x<searchArray.size(); x++){
			String srch = searchArray.get(x);
			for(int y=0; y<publishedList.size(); y++){
				Article art = publishedList.get(y);
				if(art.title.contains(srch)){
					releventArticleList.add(art);
				}
			}
		}
		
		/*
		 * For Tags
		 */
		//Iterate through all the tags list for each article in the published list comparing them to all the search words
		for(int z=0; z<publishedList.size();z++){
			Article art = publishedList.get(z);
			List<Tag> tagList = art.tags;
			for(int x=0; x<searchArray.size(); x++){
				String srch = searchArray.get(x);
				for(int y=0; y<tagList.size(); y++){
					if(tagList.get(y).title.contains(srch)){
						releventArticleList.add(art);
					}
				}
			}
		}
		//make elements unique
		Set<Article> results = new HashSet<Article>(releventArticleList);
		
		render("/searchController/index.html", results);		
	}
	
	public static void searchByAuthors(String names){
		ArrayList<String> namesArray = partitionString(names);
		
		render("/searchController/index.html");	
	}
	
	public static void searchByTitles(String titles){
		ArrayList<String> titlessArray = partitionString(titles);
		
		render("/searchController/index.html");	
		
	}
	
	public static void searchByTags(String tags){
		ArrayList<String> tagsArray = partitionString(tags);
		
		render("/searchController/index.html");	
		
	}
	
	public static void searchByDate(String fromMonth, String fromYear, String toMonth, String toYear){

		render("/searchController/index.html");	
		
	}
	
	private static ArrayList<String> partitionString(String string){
		ArrayList<String> list = new ArrayList<String>();
		while(string.contains(",")){
			list.add(string.substring(0, string.indexOf(",")));
			string = string.substring(string.indexOf(",")+1, string.length());
		}
		if(!string.isEmpty()){
			list.add(string);
		}
		return list;
	}
	
	
}