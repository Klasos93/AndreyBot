package com;

import java.util.HashMap;
import java.util.Map;

public class DataStorage {
    private static final HashMap<String,String> countryLinkMap = new HashMap<>();
    private static final HashMap<String,String> countryNewsMap = new HashMap<>();

    public static void setInCountryLinkMap(String country, String link){
        countryLinkMap.put(country,link);

    }

    public static void setInCountryNewsMap(String country, String news){
        countryNewsMap.put(country,news);
    }
    public static String getCountryNewsMap(String country){
        return countryNewsMap.get(country);
    }

    public static void setCountryLinkMap(String country, String link){
        countryLinkMap.put(country,link);
    }

public static HashMap<String,String> getCountryLinkMap() {
    return countryLinkMap;
}
public class DataControler {
    
    public static void UpdateNews() throws Exception{
        for (Map.Entry entry: countryLinkMap.entrySet()){
            String site = entry.getKey().toString();
            String link = entry.getValue().toString();
            Site siteForUpdate = new Site(AndreyBot.needToTranslate);
            siteForUpdate.getPageNews(link);
            countryNewsMap.put(site,siteForUpdate.getSiteTitle());
        }
    }
}

}
