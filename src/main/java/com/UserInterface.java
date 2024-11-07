package com;

import java.util.HashMap;

public class UserInterface {
    private static final HashMap<Long,UserProfile> userMap = new HashMap<>();

    public static void setUserMap(long chatId, String country){
        if (userMap.containsKey(chatId)){
            userMap.get(chatId).setCountryMap(country);
        }else {
            userMap.put(chatId, new UserProfile());
            userMap.get(chatId).setCountryMap(country);
        }
        
    }

    public static String getUserCountries(long chatId){
        if (userMap.containsKey(chatId)){
            return userMap.get(chatId).getUserCountries();
        }
        return "";
    }
}

