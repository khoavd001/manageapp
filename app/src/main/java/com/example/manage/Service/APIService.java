package com.example.manage.Service;

public class APIService {
    private static String base_url ="https://regulatory-alcoholi.000webhostapp.com/server/";

    public static DataService getService(){
        return APIRetroficClient.getclient(base_url).create(DataService.class);
    }
}