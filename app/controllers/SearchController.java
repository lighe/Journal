package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

@With({ApplicationController.class, Security.class})
public class SearchController extends Controller {

	public static void index() {
		render();
	}
	
}