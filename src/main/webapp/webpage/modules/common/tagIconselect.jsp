<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>图标选择</title>
	<meta name="decorator" content="blank"/>
    <style type="text/css">
    	.page-header {clear:both;margin:0 20px;padding-top:20px;}
		.the-icons {padding:25px 10px 15px;list-style:none;}
		.the-icons li {float:left;width:22%;line-height:25px;margin:2px 5px;cursor:pointer;}
		.the-icons i {margin:1px 5px;font-size:16px;} .the-icons li:hover {background-color:#efefef;}
        .the-icons li.active {background-color:#0088CC;color:#ffffff;}
        .the-icons li:hover i{font-size:20px;}
    </style>
    <script type="text/javascript">
	    $(document).ready(function(){
	    	$("#icons li").click(function(){
	    		$("#icons li").removeClass("active");
	    		$("#icons li i").removeClass("icon-white");
	    		$(this).addClass("active");
	    		$(this).children("i").addClass("icon-white");
	    		$("#icon").val($(this).find('i').attr('class').split(" ")[0]+' '+$(this).find('i').attr('class').split(" ")[1]);
	    	});
	    	$("#icons li").each(function(){
	    		if ($(this).text()=="${value}"){
	    			$(this).click();
	    		}
	    	});
	    });
    </script>
</head>
<body>
<input type="hidden" id="icon" value="${value}" />
<div id="icons">
		
			    <h2 class="page-header">66 New Icons in 4.4</h2>
			     <ul class="the-icons">
							<li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-glass"></i>
								fa-glass 
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-music"></i>
								fa-music
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-search"></i>
							fa-search
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-envelope-o"></i>
								fa-envelope-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-heart"></i>
								fa-heart
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-star"></i>
								fa-star
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-star-o"></i>
								fa-star-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-user"></i>
								fa-user
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-film"></i>
								fa-film
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-th-large"></i>
								fa-th-large
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-th"></i>
								fa-th
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-th-list"></i>
								fa-th-list
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-check"></i>
								fa-check
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-times"></i>
								fa-times
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-search-plus"></i>
								fa-search-plus
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-search-minus"></i>
								fa-search-minus
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-power-off"></i>
								fa-power-off
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-signal"></i>
								fa-signal
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-gear"></i>
								fa-gear
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cog"></i>
								fa-cog
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-trash-o"></i>
								fa-trash-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-home"></i>
								fa-home
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-o"></i>
								fa-file-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-clock-o"></i>
								fa-clock-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-road"></i>
								fa-road
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-download"></i>
								fa-download
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrow-circle-o-down"></i>
								fa-arrow-circle-o-down
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrow-circle-o-up"></i>
								fa-arrow-circle-o-up
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-inbox"></i>
								fa-inbox
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-play-circle-o"></i>
								fa-play-circle-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-rotate-right"></i>
								fa-rotate-right
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-repeat"></i>
								fa-repeat
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-refresh"></i>
								fa-refresh
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-list-alt"></i>
								fa-list-alt
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-lock"></i>
								fa-lock
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-flag"></i>
								fa-flag
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-headphones"></i>
								fa-headphones
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-volume-off"></i>
								fa-volume-off
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-volume-down"></i>
								fa-volume-down
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-volume-up"></i>
								fa-volume-up
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-qrcode"></i>
								fa-qrcode
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-barcode"></i>
								fa-barcode
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-tag"></i>
								fa-tag
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-tags"></i>
								fa-tags
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-book"></i>
								fa-book
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bookmark"></i>
								fa-bookmark
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-print"></i>
								fa-print
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-camera"></i>
								fa-camera
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-font"></i>
								fa-font
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bold"></i>
								fa-bold
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-italic"></i>
								fa-italic
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-text-height"></i>
								fa-text-height
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-text-width"></i>
								fa-text-width
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-align-left"></i>
								fa-align-left
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-align-center"></i>
								fa-align-center
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-align-right"></i>
								fa-align-right
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-align-justify"></i>
								fa-align-justify
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-list"></i>
								fa-list
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-dedent"></i>
								fa-dedent
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-outdent"></i>
								fa-outdent
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-indent"></i>
								fa-indent
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-video-camera"></i>
								fa-video-camera
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-photo"></i>
								fa-photo
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-image"></i>
								fa-image
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-picture-o"></i>
								fa-picture-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-pencil"></i>
								fa-pencil
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-map-marker"></i>
								fa-map-marker
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-adjust"></i>
								fa-adjust
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-tint"></i>
								fa-tint
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-edit"></i>
								fa-edit
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-pencil-square-o"></i>
								fa-pencil-square-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-share-square-o"></i>
								fa-share-square-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-check-square-o"></i>
								fa-check-square-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrows"></i>
								fa-arrows
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-step-backward"></i>
								fa-step-backward
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-fast-backward"></i>
								fa-fast-backward
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-backward"></i>
								fa-backward
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-play"></i>
								fa-play
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-pause"></i>
								fa-pause
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-stop"></i>
								fa-stop
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-forward"></i>
								fa-forward
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-fast-forward"></i>
								fa-fast-forward
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-step-forward"></i>
								fa-step-forward
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-eject"></i>
								fa-eject
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-chevron-left"></i>
								fa-chevron-left
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-chevron-right"></i>
								fa-chevron-right
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-plus-circle"></i>
								fa-plus-circle
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-minus-circle"></i>
								fa-minus-circle
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-times-circle"></i>
								fa-times-circle
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-check-circle"></i>
								fa-check-circle
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-question-circle"></i>
								fa-question-circle
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-info-circle"></i>
								fa-info-circle
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-crosshairs"></i>
								fa-crosshairs
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-times-circle-o"></i>
								fa-times-circle-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-check-circle-o"></i>
								fa-check-circle-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-ban"></i>
								fa-ban
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrow-left"></i>
								fa-arrow-left
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrow-right"></i>
								fa-arrow-right
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrow-up"></i>
								fa-arrow-up
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrow-down"></i>
								fa-arrow-down
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-mail-forward"></i>
								fa-mail-forward
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-share"></i>
								fa-share
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-expand"></i>
								fa-expand
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-compress"></i>
								fa-compress
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-plus"></i>
								fa-plus
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-minus"></i>
								fa-minus
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-asterisk"></i>
								fa-asterisk
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-exclamation-circle"></i>
								fa-exclamation-circle
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-gift"></i>
								fa-gift
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-leaf"></i>
								fa-leaf
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-fire"></i>
								fa-fire
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-eye"></i>
								fa-eye
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-eye-slash"></i>
								fa-eye-slash
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-warning"></i>
								fa-warning
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-exclamation-triangle"></i>
								fa-exclamation-triangle
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-plane"></i>
								fa-plane
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-calendar"></i>
								fa-calendar
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-random"></i>
								fa-random
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-comment"></i>
								fa-comment
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-magnet"></i>
								fa-magnet
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-chevron-up"></i>
								fa-chevron-up
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-chevron-down"></i>
								fa-chevron-down
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-retweet"></i>
								fa-retweet
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-shopping-cart"></i>
								fa-shopping-cart
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-folder"></i>
								fa-folder
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-folder-open"></i>
								fa-folder-open</li>							
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrows-h"></i>
								fa-arrows-h
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bar-chart-o"></i>
								fa-bar-chart-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-twitter-square"></i>
								fa-twitter-square
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-facebook-square"></i>
								fa-facebook-square
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-camera-retro"></i>
								fa-camera-retro
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-key"></i>
								fa-key
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-gears"></i>
								fa-gears
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cogs"></i>
								fa-cogs
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-comments"></i>
								fa-comments
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-thumbs-o-up"></i>
								fa-thumbs-o-up
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-thumbs-o-down"></i>
								fa-thumbs-o-down
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-star-half"></i>
								fa-star-half
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-heart-o"></i>
								fa-heart-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sign-out"></i>
								fa-sign-out
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-linkedin-square"></i>
								fa-linkedin-square
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-thumb-tack"></i>
								fa-thumb-tack
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-external-link"></i>
								fa-external-link
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sign-in"></i>
								fa-sign-in
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-trophy"></i>
								fa-trophy
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-github-square"></i>
								fa-github-square
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-upload"></i>
								fa-upload
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-lemon-o"></i>
								fa-lemon-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-phone"></i>
								fa-phone
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-square-o"></i>
								fa-square-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bookmark-o"></i>
								fa-bookmark-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-phone-square"></i>
								fa-phone-square
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-twitter"></i>
								fa-twitter
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-facebook"></i>
								fa-facebook
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-github"></i>
								fa-github
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-unlock"></i>
								fa-unlock
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-credit-card"></i>
								fa-credit-card
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-rss"></i>
								fa-rss
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hdd-o"></i>
								fa-hdd-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bullhorn"></i>
								fa-bullhorn
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bell"></i>
								fa-bell
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-certificate"></i>
								fa-certificate
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hand-o-right"></i>
								fa-hand-o-right
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hand-o-left"></i>
								fa-hand-o-left
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hand-o-up"></i>
								fa-hand-o-up
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hand-o-down"></i>
								fa-hand-o-down
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrow-circle-left"></i>
								fa-arrow-circle-left
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrow-circle-right"></i>
								fa-arrow-circle-right
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrow-circle-up"></i>
								fa-arrow-circle-up
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrow-circle-down"></i>
								fa-arrow-circle-down
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-globe"></i>
								fa-globe
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-wrench"></i>
								fa-wrench
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-tasks"></i>
								fa-tasks
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-filter"></i>
								fa-filter
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-brifiase"></i>
								fa-brifiase
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrows-alt"></i>
								fa-arrows-alt
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-group"></i>
								fa-group
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-users"></i>
								fa-users
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-chain"></i>
								fa-chain
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-link"></i>
								fa-link
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cloud"></i>
								fa-cloud
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-flask"></i>
								fa-flask
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cut"></i>
								fa-cut
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-scissors"></i>
								fa-scissors
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-copy"></i>
								fa-copy
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-files-o"></i>
								fa-files-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-paperclip"></i>
								fa-paperclip
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-save"></i>
								fa-save
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-floppy-o"></i>
								fa-floppy-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-square"></i>
								fa-square
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-navicon"></i>
								fa-navicon
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-reorder"></i>
								fa-reorder
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bars"></i>
								fa-bars
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-list-ul"></i>
								fa-list-ul
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-list-ol"></i>
								fa-list-ol
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-strikethrough"></i>
								fa-strikethrough
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-underline"></i>
								fa-underline
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-table"></i>
								fa-table
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-magic"></i>
								fa-magic
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-truck"></i>
								fa-truck
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-pinterest"></i>
								fa-pinterest
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-pinterest-square"></i>
								fa-pinterest-square
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-google-plus-square"></i>
								fa-google-plus-square
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-google-plus"></i>
								fa-google-plus
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-money"></i>
								fa-money
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-caret-down"></i>
								fa-caret-down
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-caret-up"></i>
								fa-caret-up
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-caret-left"></i>
								fa-caret-left
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-caret-right"></i>
								fa-caret-right
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-columns"></i>
								fa-columns
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-unsorted"></i>
								fa-unsorted
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sort"></i>
								fa-sort
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sort-down"></i>
								fa-sort-down
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sort-desc"></i>
								fa-sort-desc
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sort-up"></i>
								fa-sort-up
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sort-asc"></i>
								fa-sort-asc
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-envelope"></i>
								fa-envelope
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-linkedin"></i>
								fa-linkedin
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-rotate-left"></i>
								fa-rotate-left
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-undo"></i>
								fa-undo
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-legal"></i>
								fa-legal
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-gavel"></i>
								fa-gavel
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-dashboard"></i>
								fa-dashboard
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-tachometer"></i>
								fa-tachometer
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-comment-o"></i>
								fa-comment-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-comments-o"></i>
								fa-comments-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-flash"></i>
								fa-flash
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bolt"></i>
								fa-bolt
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sitemap"></i>
								fa-sitemap
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-mbreli"></i>
								fa-umbreli
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-paste"></i>
								fa-paste
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-clipboard"></i>
								fa-clipboard
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-lightbulb-o"></i>
								fa-lightbulb-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-exchange"></i>
								fa-exchange
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cloud-download"></i>
								fa-cloud-download
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cloud-upload"></i>
								fa-cloud-upload
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-user-md"></i>
								fa-user-md
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-stethoscope"></i>
								fa-stethoscope
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-suitcase"></i>
								fa-suitcase
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bell-o"></i>
								fa-bell-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-coffee"></i>
								fa-coffee
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cutlery"></i>
								fa-cutlery
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-text-o"></i>
								fa-file-text-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-building-o"></i>
								fa-building-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hospital-o"></i>
								fa-hospital-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-ambulance"></i>
								fa-ambulance
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-medkit"></i>
								fa-medkit
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-fighter-jet"></i>
								fa-fighter-jet
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-beer"></i>
								fa-beer
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-h-square"></i>
								fa-h-square
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-plus-square"></i>
								fa-plus-square
							</li>						
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-angle-double-right"></i>
								fa-angle-double-right
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-angle-double-up"></i>
								fa-angle-double-up
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-angle-double-down"></i>
								fa-angle-double-down
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-angle-left"></i>
								fa-angle-left
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-angle-right"></i>
								fa-angle-right
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-angle-up"></i>
								fa-angle-up
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-angle-down"></i>
								fa-angle-down
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-desktop"></i>
								fa-desktop
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-laptop"></i>
								fa-laptop
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-tablet"></i>
								fa-tablet
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-mobile-phone"></i>
								fa-mobile-phone
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-mobile"></i>
								fa-mobile
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-circle-o"></i>
								fa-circle-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-quote-left"></i>
								fa-quote-left
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-quote-right"></i>
								fa-quote-right
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-spinner"></i>
								fa-spinner
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-circle"></i>
								fa-circle
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-mail-reply"></i>
								fa-mail-reply
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-reply"></i>
								fa-reply
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-github-alt"></i>
								fa-github-alt
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-folder-o"></i>
								fa-folder-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-folder-open-o"></i>
								fa-folder-open-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-smile-o"></i>
								fa-smile-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-frown-o"></i>
								fa-frown-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-meh-o"></i>
								fa-meh-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-gamepad"></i>
								fa-gamepad
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-keyboard-o"></i>
								fa-keyboard-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-flag-o"></i>
								fa-flag-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-flag-checkered"></i>
								fa-flag-checkered
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-terminal"></i>
								fa-terminal
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-code"></i>
								fa-code
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-mail-reply-all"></i>
								fa-mail-reply-all
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-reply-all"></i>
								fa-reply-all
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-star-half-empty"></i>
								fa-star-half-empty
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-star-half-full"></i>
								fa-star-half-full
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-star-half-o"></i>
								fa-star-half-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-location-arrow"></i>
								fa-location-arrow
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-crop"></i>
								fa-crop
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-code-fork"></i>
								fa-code-fork
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-unlink"></i>
								fa-unlink
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-chain-broei"></i>
								fa-chain-broei
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-question"></i>
								fa-question
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-info"></i>
								fa-info
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-exclamation"></i>
								fa-exclamation
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-superscript"></i>
								fa-superscript
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-subscript"></i>
								fa-subscript
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-eraser"></i>
								fa-eraser
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-puzzle-piece"></i>
								fa-puzzle-piece
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-microphone"></i>
								fa-microphone
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-microphone-slash"></i>
								fa-microphone-slash
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-shield"></i>
								fa-shield
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-calendar-o"></i>
								fa-calendar-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-fire-extinguisher"></i>
								fa-fire-extinguisher
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-rocket"></i>
								fa-rocket
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-maxcdn"></i>
								fa-maxcdn
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-chevron-circle-left"></i>
								fa-chevron-circle-left
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-chevron-circle-right"></i>
								fa-chevron-circle-right
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-chevron-circle-up"></i>
								fa-chevron-circle-up
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-chevron-circle-down"></i>
								fa-chevron-circle-down
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-html5"></i>
								fa-html5
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-css3"></i>
								fa-css3
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-anchor"></i>
								fa-anchor
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-unlock-alt"></i>
								fa-unlock-alt
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bullseye"></i>
								fa-bullseye
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-ellipsis-h"></i>
								fa-ellipsis-h
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-ellipsis-v"></i>
								fa-ellipsis-v
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-rss-square"></i>
								fa-rss-square
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-play-circle"></i>
								fa-play-circle
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-ticket"></i>
								fa-ticket
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-minus-square"></i>
								fa-minus-square
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-minus-square-o"></i>
								fa-minus-square-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-level-up"></i>
								fa-level-up
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-level-down"></i>
								fa-level-down
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-check-square"></i>
								fa-check-square
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-pencil-square"></i>
								fa-pencil-square
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-external-link-square"></i>
								fa-external-link-square
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-share-square"></i>
								fa-share-square
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-compass"></i>
								fa-compass
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-toggle-down"></i>
								fa-toggle-down
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-caret-square-o-down"></i>
								fa-caret-square-o-down
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-toggle-up"></i>
								fa-toggle-up
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-caret-square-o-up"></i>
								fa-caret-square-o-up
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-toggle-right"></i>
								fa-toggle-right
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-caret-square-o-right"></i>
								fa-caret-square-o-right
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-euro"></i>
								fa-euro
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-eur"></i>
								fa-eur
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-gbp"></i>
								fa-gbp
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-dollar"></i>
								fa-dollar
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-usd"></i>
								fa-usd
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-rupee"></i>
								fa-rupee
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-inr"></i>
								fa-inr
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cny"></i>
								fa-cny
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-rmb"></i>
								fa-rmb
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-yen"></i>
								fa-yen
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-jpy"></i>
								fa-jpy
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-ruble"></i>
								fa-ruble
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-rouble"></i>
								fa-rouble
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-rub"></i>
								fa-rub
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-won"></i>
								fa-won
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-krw"></i>
								fa-krw
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bitcoin"></i>
								fa-bitcoin
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-btc"></i>
								fa-btc
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file"></i>
								fa-file
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-text"></i>
								fa-file-text
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sort-alpha-asc"></i>
								fa-sort-alpha-asc
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sort-alpha-desc"></i>
								fa-sort-alpha-desc
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sort-amount-asc"></i>
								fa-sort-amount-asc
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sort-amount-desc"></i>
								fa-sort-amount-desc
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sort-numeric-asc"></i>
								fa-sort-numeric-asc
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sort-numeric-desc"></i>
								fa-sort-numeric-desc
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-thumbs-up"></i>
								fa-thumbs-up
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-thumbs-down"></i>
								fa-thumbs-down
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-youtube-square"></i>
								fa-youtube-square
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-youtube"></i>
								fa-youtube
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-xing"></i>
								fa-xing
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-xing-square"></i>
								fa-xing-square
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-youtube-play"></i>
								fa-youtube-play
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-dropbox"></i>
								fa-dropbox
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-stack-overflow"></i>
								fa-stack-overflow
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-instagram"></i>
								fa-instagram
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-flickr"></i>
								fa-flickr
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-adn"></i>
								fa-adn
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bitbucket"></i>
								fa-bitbucket
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bitbucket-square"></i>
								fa-bitbucket-square
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-tumblr"></i>
								fa-tumblr
							</li>						
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-long-arrow-down"></i>
								fa-long-arrow-down
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-long-arrow-up"></i>
								fa-long-arrow-up
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-long-arrow-left"></i>
								fa-long-arrow-left
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-long-arrow-right"></i>
								fa-long-arrow-right
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-apple"></i>
								fa-apple
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-windows"></i>
								fa-windows
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-android"></i>
								fa-android
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-linux"></i>
								fa-linux
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-dribbble"></i>
								fa-dribbble
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-skype"></i>
								fa-skype
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-foursquare"></i>
								fa-foursquare
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-trello"></i>
								fa-trello
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-female"></i>
								fa-female
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-male"></i>
								fa-male
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-gittip"></i>
								fa-gittip
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sun-o"></i>
								fa-sun-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-moon-o"></i>
								fa-moon-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-archive"></i>
								fa-archive
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bug"></i>
								fa-bug
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-vk"></i>
								fa-vk
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-weibo"></i>
								fa-weibo
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-renren"></i>
								fa-renren
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-pagelines"></i>
								fa-pagelines
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-stack-exchange"></i>
								fa-stack-exchange
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrow-circle-o-right"></i>
								fa-arrow-circle-o-right
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrow-circle-o-left"></i>
								fa-arrow-circle-o-left
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-toggle-left"></i>
								fa-toggle-left
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-caret-square-o-left"></i>
								fa-caret-square-o-left
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-dot-circle-o"></i>
								fa-dot-circle-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-wheelchair"></i>
								fa-wheelchair
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-vimeo-square"></i>
								fa-vimeo-square
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-turkish-lira"></i>
								fa-turkish-lira
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-try"></i>
								fa-try
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-plus-square-o"></i>
								fa-plus-square-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-space-shuttle"></i>
								fa-space-shuttle
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-slack"></i>
								fa-slack
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-envelope-square"></i>
								fa-envelope-square
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-wordpress"></i>
								fa-wordpress
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-openid"></i>
								fa-openid
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-institution"></i>
								fa-institution
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bank"></i>
								fa-bank
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-university"></i>
								fa-university
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-mortar-board"></i>
								fa-mortar-board
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-graduation-cap"></i>
								fa-graduation-cap
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-yahoo"></i>
								fa-yahoo
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-google"></i>
								fa-google
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-reddit"></i>
								fa-reddit
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-reddit-square"></i>
								fa-reddit-square
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-stumbleupon-circle"></i>
								fa-stumbleupon-circle
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-stumbleupon"></i>
								fa-stumbleupon
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-delicious"></i>
								fa-delicious
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-digg"></i>
								fa-digg
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-pied-piper-square"></i>
								fa-pied-piper-square
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-pied-piper"></i>
								fa-pied-piper
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-pied-piper-alt"></i>
								fa-pied-piper-alt
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-drupal"></i>
								fa-drupal
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-joomla"></i>
								fa-joomla
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-language"></i>
								fa-language
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-fax"></i>
								fa-fax
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-building"></i>
								fa-building
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-child"></i>
								fa-child
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-paw"></i>
								fa-paw
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-spoon"></i>
								fa-spoon
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cube"></i>
								fa-cube
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cubes"></i>
								fa-cubes
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-behance"></i>
								fa-behance
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-behance-square"></i>
								fa-behance-square
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-steam"></i>
								fa-steam
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-steam-square"></i>
								fa-steam-square
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-recycle"></i>
								fa-recycle
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-automobile"></i>
								fa-automobile
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-car"></i>
								fa-car
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cab"></i>
								fa-cab
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-taxi"></i>
								fa-taxi
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-tree"></i>
								fa-tree
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-spotify"></i>
								fa-spotify
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-deviantart"></i>
								fa-deviantart
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-soundcloud"></i>
								fa-soundcloud
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-database"></i>
								fa-database
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-pdf-o"></i>
								fa-file-pdf-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-word-o"></i>
								fa-file-word-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-excel-o"></i>
								fa-file-excel-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-powerpoint-o"></i>
								fa-file-powerpoint-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-photo-o"></i>
								fa-file-photo-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-picture-o"></i>
								fa-file-picture-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-image-o"></i>
								fa-file-image-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-zip-o"></i>
								fa-file-zip-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-archive-o"></i>
								fa-file-archive-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-sound-o"></i>
								fa-file-sound-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-audio-o"></i>
								fa-file-audio-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-movie-o"></i>
								fa-file-movie-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-video-o"></i>
								fa-file-video-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-code-o"></i>
								fa-file-code-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-vine"></i>
								fa-vine
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-codepen"></i>
								fa-codepen
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-jsfiddle"></i>
								fa-jsfiddle
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-life-bouy"></i>
								fa-life-bouy
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-life-saver"></i>
								fa-life-saver
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-support"></i>
								fa-support
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-life-ring"></i>
								fa-life-ring
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-circle-o-notch"></i>
								fa-circle-o-notch
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-ra"></i>
								fa-ra
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-rebel"></i>
								fa-rebel
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-ge"></i>
								fa-ge
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-empire"></i>
								fa-empire
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-git-square"></i>
								fa-git-square
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-git"></i>
								fa-git
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hacker-news"></i>
								fa-hacker-news
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-tencent-weibo"></i>
								fa-tencent-weibo
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-qq"></i>
								fa-qq
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-wechat"></i>
								fa-wechat
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-weixin"></i>
								fa-weixin
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-send"></i>
								fa-send
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-paper-plane"></i>
								fa-paper-plane
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-send-o"></i>
								fa-send-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-paper-plane-o"></i>
								fa-paper-plane-o
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-history"></i>
								fa-history
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-circle-thin"></i>
								fa-circle-thin
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-header"></i>
								fa-header
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-paragraph"></i>
								fa-paragraph
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sliders"></i>
								fa-sliders
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-share-alt"></i>
								fa-share-alt
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-share-alt-square"></i>
								fa-share-alt-square
							</li> <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bomb"></i>
								fa-bomb
							</li>
			    </ul>
			    <h2 class="page-header">Web Application Icons</h2>
			     <ul class="the-icons">
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-adjust"></i> adjust</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-anchor"></i> anchor</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-archive"></i> archive</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-area-chart"></i> area-chart</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrows"></i> arrows</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrows-h"></i> arrows-h</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrows-v"></i> arrows-v</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-asterisk"></i> asterisk</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-at"></i> at</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-automobile"></i> automobile <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-balance-scale"></i> balance-scale</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-ban"></i> ban</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bank"></i> bank <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bar-chart"></i> bar-chart</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bar-chart-o"></i> bar-chart-o <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-barcode"></i> barcode</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bars"></i> bars</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-battery-0"></i> battery-0 <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-battery-1"></i> battery-1 <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-battery-2"></i> battery-2 <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-battery-3"></i> battery-3 <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-battery-4"></i> battery-4 <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-battery-empty"></i> battery-empty</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-battery-full"></i> battery-full</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-battery-half"></i> battery-half</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-battery-quarter"></i> battery-quarter</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-battery-three-quarters"></i> battery-three-quarters</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bed"></i> bed</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-beer"></i> beer</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bell"></i> bell</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bell-o"></i> bell-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bell-slash"></i> bell-slash</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bell-slash-o"></i> bell-slash-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bicycle"></i> bicycle</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-binoculars"></i> binoculars</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-birthday-cake"></i> birthday-cake</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bolt"></i> bolt</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bomb"></i> bomb</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-book"></i> book</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bookmark"></i> bookmark</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bookmark-o"></i> bookmark-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-briefcase"></i> briefcase</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bug"></i> bug</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-building"></i> building</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-building-o"></i> building-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bullhorn"></i> bullhorn</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bullseye"></i> bullseye</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bus"></i> bus</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cab"></i> cab <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-calculator"></i> calculator</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-calendar"></i> calendar</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-calendar-check-o"></i> calendar-check-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-calendar-minus-o"></i> calendar-minus-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-calendar-o"></i> calendar-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-calendar-plus-o"></i> calendar-plus-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-calendar-times-o"></i> calendar-times-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-camera"></i> camera</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-camera-retro"></i> camera-retro</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-car"></i> car</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-caret-square-o-down"></i> caret-square-o-down</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-caret-square-o-left"></i> caret-square-o-left</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-caret-square-o-right"></i> caret-square-o-right</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-caret-square-o-up"></i> caret-square-o-up</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cart-arrow-down"></i> cart-arrow-down</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cart-plus"></i> cart-plus</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cc"></i> cc</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-certificate"></i> certificate</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-check"></i> check</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-check-circle"></i> check-circle</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-check-circle-o"></i> check-circle-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-check-square"></i> check-square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-check-square-o"></i> check-square-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-child"></i> child</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-circle"></i> circle</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-circle-o"></i> circle-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-circle-o-notch"></i> circle-o-notch</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-circle-thin"></i> circle-thin</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-clock-o"></i> clock-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-clone"></i> clone</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-close"></i> close <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cloud"></i> cloud</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cloud-download"></i> cloud-download</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cloud-upload"></i> cloud-upload</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-code"></i> code</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-code-fork"></i> code-fork</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-coffee"></i> coffee</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cog"></i> cog</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cogs"></i> cogs</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-comment"></i> comment</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-comment-o"></i> comment-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-commenting"></i> commenting</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-commenting-o"></i> commenting-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-comments"></i> comments</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-comments-o"></i> comments-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-compass"></i> compass</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-copyright"></i> copyright</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-creative-commons"></i> creative-commons</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-credit-card"></i> credit-card</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-crop"></i> crop</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-crosshairs"></i> crosshairs</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cube"></i> cube</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cubes"></i> cubes</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cutlery"></i> cutlery</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-dashboard"></i> dashboard <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-database"></i> database</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-desktop"></i> desktop</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-diamond"></i> diamond</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-dot-circle-o"></i> dot-circle-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-download"></i> download</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-edit"></i> edit <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-ellipsis-h"></i> ellipsis-h</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-ellipsis-v"></i> ellipsis-v</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-envelope"></i> envelope</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-envelope-o"></i> envelope-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-envelope-square"></i> envelope-square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-eraser"></i> eraser</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-exchange"></i> exchange</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-exclamation"></i> exclamation</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-exclamation-circle"></i> exclamation-circle</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-exclamation-triangle"></i> exclamation-triangle</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-external-link"></i> external-link</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-external-link-square"></i> external-link-square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-eye"></i> eye</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-eye-slash"></i> eye-slash</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-eyedropper"></i> eyedropper</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-fax"></i> fax</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-feed"></i> feed <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-female"></i> female</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-fighter-jet"></i> fighter-jet</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-archive-o"></i> file-archive-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-audio-o"></i> file-audio-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-code-o"></i> file-code-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-excel-o"></i> file-excel-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-image-o"></i> file-image-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-movie-o"></i> file-movie-o <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-pdf-o"></i> file-pdf-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-photo-o"></i> file-photo-o <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-picture-o"></i> file-picture-o <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-powerpoint-o"></i> file-powerpoint-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-sound-o"></i> file-sound-o <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-video-o"></i> file-video-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-word-o"></i> file-word-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-zip-o"></i> file-zip-o <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-film"></i> film</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-filter"></i> filter</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-fire"></i> fire</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-fire-extinguisher"></i> fire-extinguisher</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-flag"></i> flag</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-flag-checkered"></i> flag-checkered</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-flag-o"></i> flag-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-flash"></i> flash <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-flask"></i> flask</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-folder"></i> folder</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-folder-o"></i> folder-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-folder-open"></i> folder-open</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-folder-open-o"></i> folder-open-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-frown-o"></i> frown-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-futbol-o"></i> futbol-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-gamepad"></i> gamepad</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-gavel"></i> gavel</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-gear"></i> gear <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-gears"></i> gears <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-gift"></i> gift</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-glass"></i> glass</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-globe"></i> globe</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-graduation-cap"></i> graduation-cap</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-group"></i> group <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hand-grab-o"></i> hand-grab-o <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hand-lizard-o"></i> hand-lizard-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hand-paper-o"></i> hand-paper-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hand-peace-o"></i> hand-peace-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hand-pointer-o"></i> hand-pointer-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hand-rock-o"></i> hand-rock-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hand-scissors-o"></i> hand-scissors-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hand-spock-o"></i> hand-spock-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hand-stop-o"></i> hand-stop-o <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hdd-o"></i> hdd-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-headphones"></i> headphones</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-heart"></i> heart</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-heart-o"></i> heart-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-heartbeat"></i> heartbeat</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-history"></i> history</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-home"></i> home</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hotel"></i> hotel <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hourglass"></i> hourglass</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hourglass-1"></i> hourglass-1 <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hourglass-2"></i> hourglass-2 <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hourglass-3"></i> hourglass-3 <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hourglass-end"></i> hourglass-end</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hourglass-half"></i> hourglass-half</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hourglass-o"></i> hourglass-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hourglass-start"></i> hourglass-start</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-i-cursor"></i> i-cursor</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-image"></i> image <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-inbox"></i> inbox</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-industry"></i> industry</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-info"></i> info</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-info-circle"></i> info-circle</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-institution"></i> institution <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-key"></i> key</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-keyboard-o"></i> keyboard-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-language"></i> language</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-laptop"></i> laptop</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-leaf"></i> leaf</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-legal"></i> legal <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-lemon-o"></i> lemon-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-level-down"></i> level-down</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-level-up"></i> level-up</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-life-bouy"></i> life-bouy <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-life-buoy"></i> life-buoy <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-life-ring"></i> life-ring</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-life-saver"></i> life-saver <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-lightbulb-o"></i> lightbulb-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-line-chart"></i> line-chart</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-location-arrow"></i> location-arrow</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-lock"></i> lock</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-magic"></i> magic</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-magnet"></i> magnet</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-mail-forward"></i> mail-forward <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-mail-reply"></i> mail-reply <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-mail-reply-all"></i> mail-reply-all <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-male"></i> male</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-map"></i> map</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-map-marker"></i> map-marker</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-map-o"></i> map-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-map-pin"></i> map-pin</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-map-signs"></i> map-signs</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-meh-o"></i> meh-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-microphone"></i> microphone</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-microphone-slash"></i> microphone-slash</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-minus"></i> minus</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-minus-circle"></i> minus-circle</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-minus-square"></i> minus-square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-minus-square-o"></i> minus-square-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-mobile"></i> mobile</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-mobile-phone"></i> mobile-phone <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-money"></i> money</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-moon-o"></i> moon-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-mortar-board"></i> mortar-board <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-motorcycle"></i> motorcycle</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-mouse-pointer"></i> mouse-pointer</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-music"></i> music</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-navicon"></i> navicon <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-newspaper-o"></i> newspaper-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-object-group"></i> object-group</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-object-ungroup"></i> object-ungroup</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-paint-brush"></i> paint-brush</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-paper-plane"></i> paper-plane</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-paper-plane-o"></i> paper-plane-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-paw"></i> paw</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-pencil"></i> pencil</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-pencil-square"></i> pencil-square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-pencil-square-o"></i> pencil-square-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-phone"></i> phone</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-phone-square"></i> phone-square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-photo"></i> photo <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-picture-o"></i> picture-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-pie-chart"></i> pie-chart</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-plane"></i> plane</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-plug"></i> plug</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-plus"></i> plus</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-plus-circle"></i> plus-circle</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-plus-square"></i> plus-square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-plus-square-o"></i> plus-square-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-power-off"></i> power-off</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-print"></i> print</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-puzzle-piece"></i> puzzle-piece</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-qrcode"></i> qrcode</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-question"></i> question</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-question-circle"></i> question-circle</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-quote-left"></i> quote-left</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-quote-right"></i> quote-right</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-random"></i> random</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-recycle"></i> recycle</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-refresh"></i> refresh</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-registered"></i> registered</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-remove"></i> remove <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-reorder"></i> reorder <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-reply"></i> reply</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-reply-all"></i> reply-all</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-retweet"></i> retweet</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-road"></i> road</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-rocket"></i> rocket</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-rss"></i> rss</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-rss-square"></i> rss-square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-search"></i> search</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-search-minus"></i> search-minus</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-search-plus"></i> search-plus</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-send"></i> send <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-send-o"></i> send-o <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-server"></i> server</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-share"></i> share</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-share-alt"></i> share-alt</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-share-alt-square"></i> share-alt-square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-share-square"></i> share-square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-share-square-o"></i> share-square-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-shield"></i> shield</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-ship"></i> ship</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-shopping-cart"></i> shopping-cart</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sign-in"></i> sign-in</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sign-out"></i> sign-out</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-signal"></i> signal</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sitemap"></i> sitemap</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sliders"></i> sliders</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-smile-o"></i> smile-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-soccer-ball-o"></i> soccer-ball-o <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sort"></i> sort</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sort-alpha-asc"></i> sort-alpha-asc</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sort-alpha-desc"></i> sort-alpha-desc</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sort-amount-asc"></i> sort-amount-asc</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sort-amount-desc"></i> sort-amount-desc</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sort-asc"></i> sort-asc</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sort-desc"></i> sort-desc</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sort-down"></i> sort-down <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sort-numeric-asc"></i> sort-numeric-asc</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sort-numeric-desc"></i> sort-numeric-desc</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sort-up"></i> sort-up <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-space-shuttle"></i> space-shuttle</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-spinner"></i> spinner</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-spoon"></i> spoon</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-square"></i> square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-square-o"></i> square-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-star"></i> star</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-star-half"></i> star-half</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-star-half-empty"></i> star-half-empty <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-star-half-full"></i> star-half-full <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-star-half-o"></i> star-half-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-star-o"></i> star-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sticky-note"></i> sticky-note</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sticky-note-o"></i> sticky-note-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-street-view"></i> street-view</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-suitcase"></i> suitcase</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sun-o"></i> sun-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-support"></i> support <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-tablet"></i> tablet</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-tachometer"></i> tachometer</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-tag"></i> tag</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-tags"></i> tags</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-tasks"></i> tasks</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-taxi"></i> taxi</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-television"></i> television</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-terminal"></i> terminal</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-thumb-tack"></i> thumb-tack</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-thumbs-down"></i> thumbs-down</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-thumbs-o-down"></i> thumbs-o-down</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-thumbs-o-up"></i> thumbs-o-up</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-thumbs-up"></i> thumbs-up</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-ticket"></i> ticket</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-times"></i> times</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-times-circle"></i> times-circle</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-times-circle-o"></i> times-circle-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-tint"></i> tint</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-toggle-down"></i> toggle-down <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-toggle-left"></i> toggle-left <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-toggle-off"></i> toggle-off</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-toggle-on"></i> toggle-on</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-toggle-right"></i> toggle-right <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-toggle-up"></i> toggle-up <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-trademark"></i> trademark</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-trash"></i> trash</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-trash-o"></i> trash-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-tree"></i> tree</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-trophy"></i> trophy</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-truck"></i> truck</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-tty"></i> tty</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-tv"></i> tv <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-umbrella"></i> umbrella</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-university"></i> university</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-unlock"></i> unlock</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-unlock-alt"></i> unlock-alt</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-unsorted"></i> unsorted <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-upload"></i> upload</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-user"></i> user</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-user-plus"></i> user-plus</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-user-secret"></i> user-secret</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-user-times"></i> user-times</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-users"></i> users</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-video-camera"></i> video-camera</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-volume-down"></i> volume-down</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-volume-off"></i> volume-off</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-volume-up"></i> volume-up</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-warning"></i> warning <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-wheelchair"></i> wheelchair</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-wifi"></i> wifi</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-wrench"></i> wrench</li>
			    </ul>
			    <h2 class="page-header">Hand Icons</h2>
			       <ul class="the-icons">
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hand-grab-o"></i> hand-grab-o <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hand-lizard-o"></i> hand-lizard-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hand-o-down"></i> hand-o-down</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hand-o-left"></i> hand-o-left</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hand-o-right"></i> hand-o-right</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hand-o-up"></i> hand-o-up</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hand-paper-o"></i> hand-paper-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hand-peace-o"></i> hand-peace-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hand-pointer-o"></i> hand-pointer-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hand-rock-o"></i> hand-rock-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hand-scissors-o"></i> hand-scissors-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hand-spock-o"></i> hand-spock-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hand-stop-o"></i> hand-stop-o <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-thumbs-down"></i> thumbs-down</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-thumbs-o-down"></i> thumbs-o-down</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-thumbs-o-up"></i> thumbs-o-up</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-thumbs-up"></i> thumbs-up</li>
			    </ul>
			    <h2 class="page-header">Transportation Icons</h2>
			     <ul class="the-icons">
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-ambulance"></i> ambulance</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-automobile"></i> automobile <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bicycle"></i> bicycle</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bus"></i> bus</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cab"></i> cab <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-car"></i> car</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-fighter-jet"></i> fighter-jet</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-motorcycle"></i> motorcycle</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-plane"></i> plane</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-rocket"></i> rocket</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-ship"></i> ship</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-space-shuttle"></i> space-shuttle</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-subway"></i> subway</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-taxi"></i> taxi</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-train"></i> train</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-truck"></i> truck</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-wheelchair"></i> wheelchair</li>
			    </ul>
			    <h2 class="page-header">Gender Icons</h2>
			      <ul class="the-icons">
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-genderless"></i> genderless</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-intersex"></i> intersex <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-mars"></i> mars</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-mars-double"></i> mars-double</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-mars-stroke"></i> mars-stroke</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-mars-stroke-h"></i> mars-stroke-h</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-mars-stroke-v"></i> mars-stroke-v</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-mercury"></i> mercury</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-neuter"></i> neuter</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-transgender"></i> transgender</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-transgender-alt"></i> transgender-alt</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-venus"></i> venus</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-venus-double"></i> venus-double</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-venus-mars"></i> venus-mars</li>
			    </ul>
			    <h2 class="page-header">File Type Icons</h2>
			      <ul class="the-icons">
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file"></i> file</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-archive-o"></i> file-archive-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-audio-o"></i> file-audio-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-code-o"></i> file-code-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-excel-o"></i> file-excel-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-image-o"></i> file-image-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-movie-o"></i> file-movie-o <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-o"></i> file-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-pdf-o"></i> file-pdf-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-photo-o"></i> file-photo-o <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-picture-o"></i> file-picture-o <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-powerpoint-o"></i> file-powerpoint-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-sound-o"></i> file-sound-o <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-text"></i> file-text</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-text-o"></i> file-text-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-video-o"></i> file-video-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-word-o"></i> file-word-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-zip-o"></i> file-zip-o <span class="text-muted">(alias)</span></li>
			    </ul>
			    <h2 class="page-header">Spinner Icons</h2>
			   <ul class="the-icons">
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-circle-o-notch"></i> circle-o-notch</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cog"></i> cog</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-gear"></i> gear <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-refresh"></i> refresh</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-spinner"></i> spinner</li>
			    </ul>
			    <h2 class="page-header">Form Control Icons</h2>
			       <ul class="the-icons">
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-check-square"></i> check-square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-check-square-o"></i> check-square-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-circle"></i> circle</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-circle-o"></i> circle-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-dot-circle-o"></i> dot-circle-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-minus-square"></i> minus-square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-minus-square-o"></i> minus-square-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-plus-square"></i> plus-square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-plus-square-o"></i> plus-square-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-square"></i> square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-square-o"></i> square-o</li>
			    </ul>
			    <h2 class="page-header">Payment Icons</h2>
			       <ul class="the-icons">
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cc-amex"></i> cc-amex</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cc-diners-club"></i> cc-diners-club</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cc-discover"></i> cc-discover</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cc-jcb"></i> cc-jcb</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cc-mastercard"></i> cc-mastercard</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cc-paypal"></i> cc-paypal</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cc-stripe"></i> cc-stripe</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cc-visa"></i> cc-visa</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-credit-card"></i> credit-card</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-google-wallet"></i> google-wallet</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-paypal"></i> paypal</li>
			    </ul>
			    <h2 class="page-header">Chart Icons</h2>
			      <ul class="the-icons">
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-area-chart"></i> area-chart</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bar-chart"></i> bar-chart</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bar-chart-o"></i> bar-chart-o <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-line-chart"></i> line-chart</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-pie-chart"></i> pie-chart</li>
			    </ul>
			    <h2 class="page-header">Currency Icons</h2>
			      <ul class="the-icons">
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bitcoin"></i> bitcoin <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-btc"></i> btc</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cny"></i> cny <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-dollar"></i> dollar <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-eur"></i> eur</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-euro"></i> euro <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-gbp"></i> gbp</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-gg"></i> gg</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-gg-circle"></i> gg-circle</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-ils"></i> ils</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-inr"></i> inr</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-jpy"></i> jpy</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-krw"></i> krw</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-money"></i> money</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-rmb"></i> rmb <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-rouble"></i> rouble <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-rub"></i> rub</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-ruble"></i> ruble <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-rupee"></i> rupee <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-shekel"></i> shekel <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sheqel"></i> sheqel <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-try"></i> try</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-turkish-lira"></i> turkish-lira <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-usd"></i> usd</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-won"></i> won <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-yen"></i> yen <span class="text-muted">(alias)</span></li>
			    </ul>
			    <h2 class="page-header">Text Editor Icons</h2>
			       <ul class="the-icons">
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-align-center"></i> align-center</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-align-justify"></i> align-justify</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-align-left"></i> align-left</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-align-right"></i> align-right</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bold"></i> bold</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-chain"></i> chain <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-chain-broken"></i> chain-broken</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-clipboard"></i> clipboard</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-columns"></i> columns</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-copy"></i> copy <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cut"></i> cut <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-dedent"></i> dedent <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-eraser"></i> eraser</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file"></i> file</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-o"></i> file-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-text"></i> file-text</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-file-text-o"></i> file-text-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-files-o"></i> files-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-floppy-o"></i> floppy-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-font"></i> font</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-header"></i> header</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-indent"></i> indent</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-italic"></i> italic</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-link"></i> link</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-list"></i> list</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-list-alt"></i> list-alt</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-list-ol"></i> list-ol</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-list-ul"></i> list-ul</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-outdent"></i> outdent</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-paperclip"></i> paperclip</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-paragraph"></i> paragraph</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-paste"></i> paste <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-repeat"></i> repeat</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-rotate-left"></i> rotate-left <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-rotate-right"></i> rotate-right <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-save"></i> save <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-scissors"></i> scissors</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-strikethrough"></i> strikethrough</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-subscript"></i> subscript</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-superscript"></i> superscript</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-table"></i> table</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-text-height"></i> text-height</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-text-width"></i> text-width</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-th"></i> th</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-th-large"></i> th-large</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-th-list"></i> th-list</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-underline"></i> underline</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-undo"></i> undo</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-unlink"></i> unlink <span class="text-muted">(alias)</span></li>
			    </ul>
			    <h2 class="page-header">Directional Icons</h2>
			       <ul class="the-icons">
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-angle-double-down"></i> angle-double-down</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-angle-double-left"></i> angle-double-left</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-angle-double-right"></i> angle-double-right</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-angle-double-up"></i> angle-double-up</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-angle-down"></i> angle-down</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-angle-left"></i> angle-left</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-angle-right"></i> angle-right</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-angle-up"></i> angle-up</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrow-circle-down"></i> arrow-circle-down</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrow-circle-left"></i> arrow-circle-left</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrow-circle-o-down"></i> arrow-circle-o-down</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrow-circle-o-left"></i> arrow-circle-o-left</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrow-circle-o-right"></i> arrow-circle-o-right</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrow-circle-o-up"></i> arrow-circle-o-up</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrow-circle-right"></i> arrow-circle-right</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrow-circle-up"></i> arrow-circle-up</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrow-down"></i> arrow-down</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrow-left"></i> arrow-left</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrow-right"></i> arrow-right</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrow-up"></i> arrow-up</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrows"></i> arrows</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrows-alt"></i> arrows-alt</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrows-h"></i> arrows-h</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrows-v"></i> arrows-v</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-caret-down"></i> caret-down</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-caret-left"></i> caret-left</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-caret-right"></i> caret-right</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-caret-square-o-down"></i> caret-square-o-down</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-caret-square-o-left"></i> caret-square-o-left</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-caret-square-o-right"></i> caret-square-o-right</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-caret-square-o-up"></i> caret-square-o-up</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-caret-up"></i> caret-up</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-chevron-circle-down"></i> chevron-circle-down</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-chevron-circle-left"></i> chevron-circle-left</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-chevron-circle-right"></i> chevron-circle-right</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-chevron-circle-up"></i> chevron-circle-up</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-chevron-down"></i> chevron-down</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-chevron-left"></i> chevron-left</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-chevron-right"></i> chevron-right</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-chevron-up"></i> chevron-up</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-exchange"></i> exchange</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hand-o-down"></i> hand-o-down</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hand-o-left"></i> hand-o-left</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hand-o-right"></i> hand-o-right</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hand-o-up"></i> hand-o-up</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-long-arrow-down"></i> long-arrow-down</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-long-arrow-left"></i> long-arrow-left</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-long-arrow-right"></i> long-arrow-right</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-long-arrow-up"></i> long-arrow-up</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-toggle-down"></i> toggle-down <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-toggle-left"></i> toggle-left <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-toggle-right"></i> toggle-right <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-toggle-up"></i> toggle-up <span class="text-muted">(alias)</span></li>
			    </ul>
			    <h2 class="page-header">Video Player Icons</h2>
			       <ul class="the-icons">
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-arrows-alt"></i> arrows-alt</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-backward"></i> backward</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-compress"></i> compress</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-eject"></i> eject</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-expand"></i> expand</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-fast-backward"></i> fast-backward</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-fast-forward"></i> fast-forward</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-forward"></i> forward</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-pause"></i> pause</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-play"></i> play</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-play-circle"></i> play-circle</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-play-circle-o"></i> play-circle-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-random"></i> random</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-step-backward"></i> step-backward</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-step-forward"></i> step-forward</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-stop"></i> stop</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-youtube-play"></i> youtube-play</li>
			    </ul>
			    <h2 class="page-header">Brand Icons</h2>
			      <ul class="the-icons">
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-500px"></i> 500px</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-adn"></i> adn</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-amazon"></i> amazon</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-android"></i> android</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-angellist"></i> angellist</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-apple"></i> apple</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-behance"></i> behance</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-behance-square"></i> behance-square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bitbucket"></i> bitbucket</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bitbucket-square"></i> bitbucket-square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-bitcoin"></i> bitcoin <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-black-tie"></i> black-tie</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-btc"></i> btc</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-buysellads"></i> buysellads</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cc-amex"></i> cc-amex</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cc-diners-club"></i> cc-diners-club</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cc-discover"></i> cc-discover</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cc-jcb"></i> cc-jcb</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cc-mastercard"></i> cc-mastercard</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cc-paypal"></i> cc-paypal</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cc-stripe"></i> cc-stripe</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-cc-visa"></i> cc-visa</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-chrome"></i> chrome</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-codepen"></i> codepen</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-connectdevelop"></i> connectdevelop</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-contao"></i> contao</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-css3"></i> css3</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-dashcube"></i> dashcube</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-delicious"></i> delicious</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-deviantart"></i> deviantart</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-digg"></i> digg</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-dribbble"></i> dribbble</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-dropbox"></i> dropbox</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-drupal"></i> drupal</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-empire"></i> empire</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-expeditedssl"></i> expeditedssl</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-facebook"></i> facebook</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-facebook-f"></i> facebook-f <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-facebook-official"></i> facebook-official</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-facebook-square"></i> facebook-square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-firefox"></i> firefox</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-flickr"></i> flickr</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-fonticons"></i> fonticons</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-forumbee"></i> forumbee</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-foursquare"></i> foursquare</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-ge"></i> ge <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-get-pocket"></i> get-pocket</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-gg"></i> gg</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-gg-circle"></i> gg-circle</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-git"></i> git</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-git-square"></i> git-square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-github"></i> github</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-github-alt"></i> github-alt</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-github-square"></i> github-square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-gittip"></i> gittip <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-google"></i> google</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-google-plus"></i> google-plus</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-google-plus-square"></i> google-plus-square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-google-wallet"></i> google-wallet</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-gratipay"></i> gratipay</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hacker-news"></i> hacker-news</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-houzz"></i> houzz</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-html5"></i> html5</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-instagram"></i> instagram</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-internet-explorer"></i> internet-explorer</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-ioxhost"></i> ioxhost</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-joomla"></i> joomla</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-jsfiddle"></i> jsfiddle</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-lastfm"></i> lastfm</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-lastfm-square"></i> lastfm-square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-leanpub"></i> leanpub</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-linkedin"></i> linkedin</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-linkedin-square"></i> linkedin-square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-linux"></i> linux</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-maxcdn"></i> maxcdn</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-meanpath"></i> meanpath</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-medium"></i> medium</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-odnoklassniki"></i> odnoklassniki</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-odnoklassniki-square"></i> odnoklassniki-square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-opencart"></i> opencart</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-openid"></i> openid</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-opera"></i> opera</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-optin-monster"></i> optin-monster</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-pagelines"></i> pagelines</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-paypal"></i> paypal</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-pied-piper"></i> pied-piper</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-pied-piper-alt"></i> pied-piper-alt</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-pinterest"></i> pinterest</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-pinterest-p"></i> pinterest-p</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-pinterest-square"></i> pinterest-square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-qq"></i> qq</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-ra"></i> ra <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-rebel"></i> rebel</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-reddit"></i> reddit</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-reddit-square"></i> reddit-square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-renren"></i> renren</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-safari"></i> safari</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-sellsy"></i> sellsy</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-share-alt"></i> share-alt</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-share-alt-square"></i> share-alt-square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-shirtsinbulk"></i> shirtsinbulk</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-simplybuilt"></i> simplybuilt</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-skyatlas"></i> skyatlas</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-skype"></i> skype</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-slack"></i> slack</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-slideshare"></i> slideshare</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-soundcloud"></i> soundcloud</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-spotify"></i> spotify</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-stack-exchange"></i> stack-exchange</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-stack-overflow"></i> stack-overflow</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-steam"></i> steam</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-steam-square"></i> steam-square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-stumbleupon"></i> stumbleupon</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-stumbleupon-circle"></i> stumbleupon-circle</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-tencent-weibo"></i> tencent-weibo</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-trello"></i> trello</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-tripadvisor"></i> tripadvisor</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-tumblr"></i> tumblr</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-tumblr-square"></i> tumblr-square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-twitch"></i> twitch</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-twitter"></i> twitter</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-twitter-square"></i> twitter-square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-viacoin"></i> viacoin</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-vimeo"></i> vimeo</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-vimeo-square"></i> vimeo-square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-vine"></i> vine</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-vk"></i> vk</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-wechat"></i> wechat <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-weibo"></i> weibo</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-weixin"></i> weixin</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-whatsapp"></i> whatsapp</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-wikipedia-w"></i> wikipedia-w</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-windows"></i> windows</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-wordpress"></i> wordpress</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-xing"></i> xing</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-xing-square"></i> xing-square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-y-combinator"></i> y-combinator</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-y-combinator-square"></i> y-combinator-square <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-yahoo"></i> yahoo</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-yc"></i> yc <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-yc-square"></i> yc-square <span class="text-muted">(alias)</span></li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-yelp"></i> yelp</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-youtube"></i> youtube</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-youtube-play"></i> youtube-play</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-youtube-square"></i> youtube-square</li>
			    </ul>
			    <h2 class="page-header">Medical Icons</h2>
			       <ul class="the-icons">
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-ambulance"></i> ambulance</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-h-square"></i> h-square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-heart"></i> heart</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-heart-o"></i> heart-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-heartbeat"></i> heartbeat</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-hospital-o"></i> hospital-o</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-medkit"></i> medkit</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-plus-square"></i> plus-square</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-stethoscope"></i> stethoscope</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-user-md"></i> user-md</li>
			       <li class="fa-hover col-md-3 col-sm-4"><i class="fa fa-wheelchair"></i> wheelchair</li>
			    </ul>
	<br/><br/>
</div>
</body>