package com;

import java.util.HashMap;

public class UserProfile {
    private final HashMap<Integer,String> countryMap;
    private int count;
    public UserProfile(){
        countryMap = new HashMap<>();
        count = 0;
    }
    public void setCountryMap(String country){
        countryMap.put(count,country);
        count++;
    }
    public String getUserCountries() {
        return countryMap.toString();
    }
}
