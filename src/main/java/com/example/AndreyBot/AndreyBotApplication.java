

package com.example.AndreyBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.AndreyBot;

@SpringBootApplication
public class AndreyBotApplication {

	public static void main(String[] args) throws TelegramApiException {
		SpringApplication.run(AndreyBotApplication.class, args);
		AndreyBot andreyBot = new AndreyBot();
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
		telegramBotsApi.registerBot(andreyBot);
	}

}
