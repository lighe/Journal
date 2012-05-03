package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import play.modules.elasticsearch.*;
import play.modules.elasticsearch.annotations.*;

@ElasticSearchController.For(models.Article.class)
public class SearchArticleController extends ElasticSearchController {

}