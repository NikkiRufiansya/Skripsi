package com.example.chatbot_stiki.Server;

import com.example.chatbot_stiki.Api.UserService;
import com.example.chatbot_stiki.Chatbot.User;

public class Server {

    public static String localhost = "http://testingbotdialogflow.000webhostapp.com";

    public static final String URL_Login = localhost+"/UserLogin.php";

    public static final String URL_TBLMHS = localhost+"/selectTblMhs.php?id=";

    public static UserService getUserService(){
        return RetrofitClient.getClient(localhost).create(UserService.class);
    }
}
