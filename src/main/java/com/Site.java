package com;

import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Site {
    private final HashMap<String,String> siteMap;
    private final HashMap<Integer,String> googleLinks;
    private Boolean needToTranslate = false;
    private String fromLang = "en";
    private String toLang = "ru";

    public Site() { 
        siteMap = new HashMap<>();
        googleLinks = new HashMap<>();
    }
    public Site(Boolean needToTranslate,String fromLang,String toLang){
        googleLinks = new HashMap<>();
        siteMap = new HashMap<>();
        this.needToTranslate = needToTranslate;
        this.fromLang = fromLang;
        this.toLang = toLang;
    }
    public Site(Boolean needToTranslate){
        googleLinks = new HashMap<>();
        siteMap = new HashMap<>();
        this.needToTranslate = needToTranslate;
    }

    
    public int getPageNews(String page) throws IOException{
        try {
            Document document =  Jsoup.connect(page).get();
            Elements h3Elements = document.select("h3");
            Element h3First = h3Elements.getFirst();
            this.siteMap.put("title", h3First.select("h3").text());
            int firstSlash = page.indexOf("/",8);
            String siteName = page.substring(0, firstSlash);
            this.siteMap.put("link",siteName + h3First.select("a[href]").get(0).attr("href"));
        } catch (IOException e) {
            return 400;
        }

        return 200;
    }


    private void getPageFromGoogle(String searchString) {
        try {
            Document document =  Jsoup.connect("http://google.com/search?q="+searchString).get();
            Elements searchResults = document.select("div.MjjYud");
            Element firstResult = searchResults.getFirst();
            this.googleLinks.put(1,firstResult.select("a[href]").get(0).attr("href"));
            this.googleLinks.put(2,searchResults.next().select("a[href]").get(0).attr("href"));
            this.googleLinks.put(3,searchResults.next().next().select("a[href]").get(0).attr("href"));
            this.googleLinks.put(4,searchResults.next().next().select("a[href]").get(0).attr("href"));
            System.out.println(googleLinks);

        } catch (IOException e) {
        }
    }
    public void searchNews(String searchString) throws IOException {
        getPageFromGoogle(searchString);
        for (int i = 1; i < 4; i++){
            if (getPageNews(this.googleLinks.get(i)) == 200){
                break;
            }
        }
        

    }
    public String getSiteTitle() throws Exception {
        if (needToTranslate){
            return Translator.translate(fromLang, toLang, siteMap.get("title"));
        }
        return siteMap.get("title");
        
    }
    public String getSiteLink() {
        return siteMap.get("link");
    }

}
