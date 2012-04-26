$(document).ready(function () {	
	
	$("#volume-browse > ul > li > a").click(function(e) {
		e.preventDefault();
		var trigger = $(this);
		var shown = $("li.active.js");
		shown.removeClass("active js");
		$("#volume-browse").find("ul ul").hide();
		trigger.parents("li").addClass("active js").find("ul").show();
	});
	
	var homeSearch = $("#home-search");
	var searchInput = homeSearch.find("input[type=text]");
	var initialSearchWidth = searchInput.width();
	searchInput.focus(function() {
		homeSearch.addClass("selected").find("button").fadeIn();
		searchInput.animate({"width":initialSearchWidth + 50 + "px"}, 100);
	});
	
	$("body").click(function(e) {
		if($(e.target).parents("#home-search").length != 1) {
			homeSearch.find("button").fadeOut("fast");
			searchInput.animate({"width":initialSearchWidth + "px"}, 100, function() {homeSearch.removeClass("selected")});
		}
	});
	
});