$(document).ready(function () {	
	
	$("#volume-browse > ul > li > a").click(function(e) {
		e.preventDefault();
		var trigger = $(this);
		var shown = $("li.active.js");
		shown.removeClass("active js");
		$("#volume-browse").find("ul ul").hide();
		trigger.parents("li").addClass("active js").find("ul").show();
	});
	
	
});