
/*
* 提交回复
* */
// function post() {
//     var questionId = $("#question_id").val();
//     var content = $("#comment_content").val();
//
//     if(!content){
//         alert("回复内容不能为空~~");
//         return;
//     }
//
//     $.ajax({
//         type: "POST",
//         url: "/comment",
//         contentType:"application/json",
//         data: JSON.stringify({ //stringify把js对象装换成字符串，然后@RequestBody反序列化成JSON
//             "parentId":questionId,
//             "content":content,
//             "type":1
//         }),
//         success: function (response) {  //@ResponseBody把对象自动序列化成json发给前端
//             if(response.code == 200){
//                 //$("#comment_section").hide(); //回复之后关闭回复框，刷新之后再显示
//                 window.location.reload(); //提交问题后自动刷新显示新问题
//             }else{
//                 if(response.code == 2003){ //没有登录
//                     var isAccepted = confirm(response.message); //弹框
//                     if(isAccepted){ //点击确认，跳转到登录页面
//                         window.open("https://github.com/login/oauth/authorize?client_id=0e77bd341a4bed30d47e&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
//                         window.localStorage.setItem("closable",true); //
//                     }
//                 }else{
//                     alert(response.message);
//                 }
//             }
//             console.log(response);
//             console.log(response.code);
//         },
//         dataType: "json"
//     });
// }

/*
* 封装重构
* */
function comment2Target(targetId,type,content) {
    if(!content){
        alert("回复内容不能为空~~");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:"application/json",
        data: JSON.stringify({ //stringify把js对象装换成字符串，然后@RequestBody反序列化成JSON
            "parentId":targetId,
            "content":content,
            "type":type
        }),
        success: function (response) {  //@ResponseBody把对象自动序列化成json发给前端
            if(response.code == 200){
                //$("#comment_section").hide(); //回复之后关闭回复框，刷新之后再显示
                window.location.reload(); //提交问题后自动刷新显示新问题
            }else{
                if(response.code == 2003){ //没有登录
                    var isAccepted = confirm(response.message); //弹框
                    if(isAccepted){ //点击确认，跳转到登录页面
                        window.open("https://github.com/login/oauth/authorize?client_id=0e77bd341a4bed30d47e&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true); //
                    }
                }else{
                    alert(response.message);
                }
            }
            console.log(response);
            console.log(response.code);
        },
        dataType: "json"
    });
}

/*
* 提交回复
* */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2Target(questionId,1,content);
}

/*
* 提交二级回复
* */
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-"+commentId).val();
    comment2Target(commentId,2,content);
}

/*
* 展开二级评论
* */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-"+id);

    if(comments.hasClass("in")){
        //折叠二级评论
        comments.removeClass("in");
        e.classList.remove("active");
    }else {
        //获取数据
        $.getJSON("/comment/" + id, function (data) {
            $.each(data.data.reverse(), function (index, comment) {
                var mediaLeftElement = $("<div/>", {
                    "class": "media-left"
                }).append($("<img/>", {
                    "class": "media-object img-rounded",
                    "src": comment.user.avatarUrl
                }));

                var mediaBodyElement = $("<div/>", {
                    "class": "media-body"
                }).append($("<h5/>", {
                    "class": "media-heading",
                    "html": comment.user.name
                })).append($("<div/>", {
                    "html": comment.content
                })).append($("<div/>", {
                    "class": "menu"
                }).append($("<span/>", {
                    "class": "pull-right",
                    "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                })));

                var mediaElement = $("<div/>", {
                    "class": "media"
                }).append(mediaLeftElement).append(mediaBodyElement);

                var commentElement = $("<div/>", {
                    "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                }).append(mediaElement);

                subCommentContainer.prepend(commentElement);
            });


            //展开二级评论
            comments.addClass("in");
            e.classList.add("active"); //展开时图标显示颜色
        });
    }

}