//处理菜单数据
function setMenu(arr){
	//清空菜单
	$(".nav .nav-ul").empty();
	$(".nav-slide").empty();
	
    for(var i=0; i<arr.length; i++){
    	if(undefined!=arr[i].url && arr[i].url.length>0){
    		var str = '<li><a onclick="getPage(\'' + arr[i].url + '\',\'' + arr[i].name + '\')" class="home"><span>' + arr[i].name + '</span></a></li>';
        	$(".nav .nav-ul").append(str);
        	$(".nav-slide").append('<div class="nav-slide-o"></div>');
    	}else{
    		var str = '<li><a href="javascript:void(0)" class="home"><span>' + arr[i].name + '</span></a></li>';
        	$(".nav .nav-ul").append(str);
        	
        	var item_str = '<div class="nav-slide-o">';
        	if(arr[i].items.length>0){
        		item_str += '<ul>';
        	}
        	
    		for(var j=0; j<arr[i].items.length; j++){
        		item_str += '<li><a onclick="getPage(\'' + arr[i].items[j].url + '\',\'' + arr[i].name + "--" + arr[i].items[j].name + '\')"><span>' + arr[i].items[j].name + '</span></a></li>';
        	}
    		
    		if(arr[i].items.length>0){
    			item_str += '</ul>';
        	}
    		item_str += '</div>';
    		
    		$(".nav-slide").append(item_str);
    	}
    }
    //链接选中效果
    $('#nav-box a').on('click',function(){
        if($(this).hasClass("link-flag")){
        	$(".link-flag").removeClass("menu-checked");
        	$(this).addClass("menu-checked");
        }
    });
    
    setMenuEffect();
}

function setMenuEffect(){
	var thisTime;
	//handleHtmlResize();
	$('.nav-ul li').mouseleave(function(even){
			thisTime	=	setTimeout(thisMouseOut,1000);
	})

	$('.nav-ul li').mouseenter(function(){
		clearTimeout(thisTime);
		var thisUB	=	$('.nav-ul li').index($(this));
		if($.trim($('.nav-slide-o').eq(thisUB).html()) != "")
		{
			$('.nav-slide').addClass('hover');
			$('.nav-slide').css("top", $('.nav-ul li').eq(thisUB).position().top + "px");
			$('.nav-slide-o').hide();
			$('.nav-slide-o').eq(thisUB).show();
		}
		else{
			$('.nav-slide').removeClass('hover');
		}
		
	})
	
	function thisMouseOut(){
		$('.nav-slide').removeClass('hover');
	}
	 
	$('.nav-slide').mouseenter(function(){
		clearTimeout(thisTime);
		$('.nav-slide').addClass('hover');
	})
	$('.nav-slide').mouseleave(function(){
		$('.nav-slide').removeClass('hover');
	})
}

//iframe子页面
function getPage(url,position){
	$("#iframe").attr("src",url);
	$("#position-name").text(position);
}

//iframe刷新
function refresh(){
	$("#iframe").attr("src",$("#iframe").attr("src"));
}

//登出
function logout(){
	xtlajax("GET","./logout.action",para);
}

//请求数据用参数
var para = {
		
};

//请求菜单数据
xtlajax("GET","./menu.json",para,setMenu);

