package controllers;

import play.mvc.*;

@With(ApplicationController.class)
public class ErrorController extends Controller {

	public static void notFound() {
		render("errors/file_not_found.html"); 	
	}
	
}
 