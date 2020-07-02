$(function () {
    $('.tabList span').click(function () {
        $(this).addClass('active').siblings('span').removeClass('active');
        var idx = $(this).index();
        $(".tabBody").eq(idx).addClass('tactive').siblings('.tabBody').removeClass('tactive');
    })

    $('.menu > .title').click(function() {
		if(!$(this).hasClass('ontitle')) {
            $('.menu > .title').removeClass('ontitle');
			$(this).addClass('ontitle');
		}else{
			$(this).removeClass('ontitle');
		}
	})

    $('.downmenu>li>a').click(function(event) {
		event.stopPropagation();
		$('.downmenu>li').removeClass('onLink');
		$(this).parent('li').addClass('onLink');
    });

    $("a[id='delect01']").click(function(){
        if(confirm("确定要删除此Blog吗")){

        }else{

        }
    });

    $("a[id='delect']").click(function(){
        if(confirm("确定要删除此Blog吗")){

        }else{

        }
    });


})
