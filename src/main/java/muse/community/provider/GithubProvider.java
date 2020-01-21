package muse.community.provider;

import com.alibaba.fastjson.JSON;
import muse.community.dto.AccessTokenDTO;
import muse.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
//  不用@Component需要这么写
//    public static void main(String[] args){
//        GithubProvider githubprovider = new GithubProvider();
//    }
    //okhttp
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");////报错
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO), mediaType);//
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string(); //access_token=e72e16c7e42f292c6912e7710c838347ae178b4a&token_type=bearer
            //System.out.println(string);
            String token = string.split("&")[0].split("=")[1];
            return token;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getGithubUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string,GithubUser.class); //string的JSON解析成对象
            return githubUser;
        }catch (IOException e){
        }
        return null;
    }

}
