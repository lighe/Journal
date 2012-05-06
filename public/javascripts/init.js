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
	
	var criticismTextarea = $(".criticism-textarea").clone();
	
	$("#add-criticism-button").click(function(e) {
		e.preventDefault();
		criticismTextarea.clone().appendTo("#criticism-section");
	});
	
	$("#editor-email").click(function(e) {
		e.preventDefault();
		$(".editor-email").slideDown();
	});
	
	$(".editor-email button").live("click", function(e) {
		e.preventDefault();
		var form = $(".editor-email form");
		var dataString = form.serialize();
		$.ajax({
		  type: "POST",
		  url: form.attr("action"),
		  data: dataString,
		  success: function(data) {
			$("#feedback").hide().html(data).slideDown();
		  }
		});
	});
	
	$(".btn-danger").live("click", function(e) {
		e.preventDefault();
		if (confirm("Are you sure ?")){
			window.location.href = $(this).attr("href");	
		}
	});
	
	$('#myTab a').click(function (e) {
	  e.preventDefault();
	  $(this).tab('show');
	})
	
	$(".disabled").live("click", function(e) {
		e.preventDefault();	
	});
	
	$("td.options input[type=checkbox]").change(function() { 
		if($(this).is(':checked')) { 
			$(this).parents("td").find(".btn").removeClass("disabled").addClass("disabled-foo");
		}
		else {
			$(this).parents("td").find(".btn").removeClass("disabled-foo").addClass("disabled");
		}
	});
});

/* ========================================================
 * bootstrap-tab.js v2.0.0
 * http://twitter.github.com/bootstrap/javascript.html#tabs
 * ========================================================
 * Copyright 2012 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ======================================================== */


!function( $ ){

  "use strict"

 /* TAB CLASS DEFINITION
  * ==================== */

  var Tab = function ( element ) {
    this.element = $(element)
  }

  Tab.prototype = {

    constructor: Tab

  , show: function () {
      var $this = this.element
        , $ul = $this.closest('ul:not(.dropdown-menu)')
        , selector = $this.attr('data-target')
        , previous
        , $target

      if (!selector) {
        selector = $this.attr('href')
        selector = selector && selector.replace(/.*(?=#[^\s]*$)/, '') //strip for ie7
      }

      if ( $this.parent('li').hasClass('active') ) return

      previous = $ul.find('.active a').last()[0]

      $this.trigger({
        type: 'show'
      , relatedTarget: previous
      })

      $target = $(selector)

      this.activate($this.parent('li'), $ul)
      this.activate($target, $target.parent(), function () {
        $this.trigger({
          type: 'shown'
        , relatedTarget: previous
        })
      })
    }

  , activate: function ( element, container, callback) {
      var $active = container.find('> .active')
        , transition = callback
            && $.support.transition
            && $active.hasClass('fade')

      function next() {
        $active
          .removeClass('active')
          .find('> .dropdown-menu > .active')
          .removeClass('active')

        element.addClass('active')

        if (transition) {
          element[0].offsetWidth // reflow for transition
          element.addClass('in')
        } else {
          element.removeClass('fade')
        }

        if ( element.parent('.dropdown-menu') ) {
          element.closest('li.dropdown').addClass('active')
        }

        callback && callback()
      }

      transition ?
        $active.one($.support.transition.end, next) :
        next()

      $active.removeClass('in')
    }
  }


 /* TAB PLUGIN DEFINITION
  * ===================== */

  $.fn.tab = function ( option ) {
    return this.each(function () {
      var $this = $(this)
        , data = $this.data('tab')
      if (!data) $this.data('tab', (data = new Tab(this)))
      if (typeof option == 'string') data[option]()
    })
  }

  $.fn.tab.Constructor = Tab


 /* TAB DATA-API
  * ============ */

  $(function () {
    $('body').on('click.tab.data-api', '[data-toggle="tab"], [data-toggle="pill"]', function (e) {
      e.preventDefault()
      $(this).tab('show')
    })
  })

}( window.jQuery )
