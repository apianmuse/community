function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();

    if(!content){
        alert("回复内容不能为空~~");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:"application/json",
        data: JSON.stringify({ //stringify把js对象装换成字符串，然后@RequestBody反序列化成JSON
            "parentId":questionId,
            "content":content,
            "type":1
        }),
        success: function (response) {  //@ResponseBody把对象自动序列化成json发给前端
            if(response.code == 200){
                console.log("chenggong");
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