package com.telegramBot.nutricion.bot.handlers.startMenu;

import com.telegramBot.nutricion.bot.BotState;
import com.telegramBot.nutricion.bot.handlers.InputMessageHandler;
import com.telegramBot.nutricion.service.ButtonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.*;

import java.io.*;
import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;


@Component
public class StartMenuHandler implements InputMessageHandler {

    @Autowired
    private ButtonsService buttonsService;

    private final String textMessage;

    private final List<InputFile> startPics;

    public StartMenuHandler(){
        try {
            this.startPics = loadPics();
            this.textMessage = loadProperties().getProperty("reply.setStartAnswer");
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public SendPhoto handle(Update update) {
        return processUsersInput(update);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_START_MENU;
    }

    private static Properties loadProperties() throws IOException {
        Properties properties = new Properties();

        // Загружаем файл с правильной кодировкой UTF-8
        try (InputStream inputStream = StartMenuHandler.class.getClassLoader().getResourceAsStream("messages_ru_RU.properties")) {
            assert inputStream != null;
            try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
                properties.load(reader);
            }
        }
        return properties;
    }

    private static List<InputFile> loadPics() throws IOException {
        List<InputFile> inputFiles = new ArrayList<>();
        URL folderUrl = ResourceLoader.class.getClassLoader().getResource("pics/startPics");
        if (folderUrl != null) {
            File folder = new File(folderUrl.getFile());
            if (folder.isDirectory()) {
                File[] files = folder.listFiles();
                if (files != null) {
                    for (File file : files) {
                        inputFiles.add(
                                new InputFile(
                                        new FileInputStream(file),
                                        file.getName()
                                )
                        );
                    }
                }
            }
        } else {
            throw new IOException("Folder \"pics/startPics\" not found in resources");
        }
        return inputFiles;
    }

    private SendPhoto processUsersInput(Update update){
        String chatId;
        if (update.hasCallbackQuery()){
            CallbackQuery callback = update.getCallbackQuery();
            chatId = callback.getMessage().getChatId().toString();
        } else {
            Message inputMsg = update.getMessage();
            chatId = inputMsg.getChatId().toString();
        }

        Random rand = new Random();
        SendPhoto greetingsPhoto = new SendPhoto(
                chatId,
                startPics.get(rand.nextInt(startPics.size())));
        greetingsPhoto.setParseMode(ParseMode.MARKDOWN);
        greetingsPhoto.setCaption(textMessage);
        greetingsPhoto.setReplyMarkup(buttonsService.setStartButtons());
        return greetingsPhoto;
    }
}
