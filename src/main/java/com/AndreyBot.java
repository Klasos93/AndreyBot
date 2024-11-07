package com;


//testing git
//testing git terminal
import java.io.IOException;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class AndreyBot extends TelegramLongPollingBot {

    final String NEWS = " news";
    String newsFromSite = "";
    String linkFromSite = "";
    public static Boolean needToTranslate = false;
    public Site site = new Site(needToTranslate);
    @Override
    public String getBotToken() {
        return TelegramUser.getUserToken();
    }
    @Override
    public String getBotUsername() {
        return TelegramUser.getUserName();
    }
    
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText())   {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            switch (commandAnalyz(messageText)) {
                case 1 -> startcommandReceived(chatId, update.getMessage().getChat().getFirstName());
                case 2 -> {
                    try {
                        Site site = new Site();
                        site.getPageNews(messageText);
                        sendMessage(chatId,site.getSiteTitle() + "\n" + site.getSiteLink());
                    } catch (IOException e) {
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 3 -> {
                    try {
                        System.out.println(messageText); //test
                        UserInterface.setUserMap(chatId, messageText);
                        //System.out.println("messageText"); //test
                        site.searchNews(messageText + NEWS);
                        //System.out.println(site.getSiteLink()); //test
                        linkFromSite = site.getSiteLink();
                        newsFromSite = site.getSiteTitle();
                        DataStorage.setInCountryLinkMap(messageText, linkFromSite);
                        DataStorage.setInCountryNewsMap(messageText, newsFromSite);
                        sendMessage(chatId,newsFromSite + "\n" + linkFromSite);
                    } catch (IOException e) {
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 4 -> {
                    sendMessage(chatId,UserInterface.getUserCountries(chatId));

                }
                default -> sendMessage(chatId,"Неизвесная команда!");
            }
        }
    }
    private void startcommandReceived(long chatId, String firstName) {
            String answer = " Hi, "+ firstName + ", nice to meet you!";
            System.out.println(answer);
            sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend)    {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textToSend);

        try {
            execute(message);
        }
        catch (TelegramApiException e)    {
            
        }
    }

    private int commandAnalyz(String command){
        if ("/start".equals(command)){
            return 1;
        }else if ("https:".equals(command.substring(0,6))){
            return 2;
        }else if (command.equals("/command1")){
            return 4;
        }
        Boolean extistCountry = true;
        try {
            Country.valueOf(command.toUpperCase());
            System.out.println(Country.valueOf(command.toUpperCase()));
        } catch (IllegalArgumentException  e) {
            extistCountry = false;
        }
        if(extistCountry){
            return 3;
        }
        return -1;
       
    }

}
