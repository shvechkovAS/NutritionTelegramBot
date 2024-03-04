package com.telegramBot.nutricion.bot;

import com.telegramBot.nutricion.bot.cache.BotCache;
import com.telegramBot.nutricion.bot.config.BotConfig;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Locale;

@Component
@AllArgsConstructor
public class NutritionTelegramBot extends TelegramLongPollingBot {

    @Autowired
    private BotConfig botConfig;

    @Autowired
    private BotStateContext botStateContext;

    @Autowired
    private BotCache botCache;

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            processCallbackQuery(update);
        }
        if (update.hasMessage() && update.getMessage().hasText()) {
            handleInputMessage(update);
        }
    }

    private void processCallbackQuery(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        long userId = callbackQuery.getFrom().getId();
        BotState botState = switch (callbackQuery.getData()) {

            case "start" -> BotState.SHOW_START_MENU;
            case "menu" -> BotState.FILLING_QUESTIONNAIRE;
            case "profit" -> BotState.PRODUCT_PROFIT;
            case "calendar" -> BotState.FEEDING_UP_CALENDAR;
            default -> botCache.getUsersCurrentBotState(userId);
        };
        botCache.setUsersCurrentBotState(userId, botState);
        sendNoTypeMessage(botState, update);
    }

    private void handleInputMessage(Update update){
        Message message = update.getMessage();
        long userId = message.getFrom().getId();
        String messageText = message.getText().toLowerCase();
        BotState botState = switch (messageText) {
            case "старт", "/start" -> BotState.SHOW_START_MENU;
            case "/menu" -> BotState.FILLING_QUESTIONNAIRE;
            case "/profit" -> BotState.PRODUCT_PROFIT;
            case "/calendar" -> BotState.FEEDING_UP_CALENDAR;
            default -> botCache.getUsersCurrentBotState(userId);
        };
        botCache.setUsersCurrentBotState(userId, botState);
        sendNoTypeMessage(botState, update);
    }

    private void sendNoTypeMessage(BotState botState, Update update){
        Object commonMsg = botStateContext.processInputMessage(botState, update);
        if(commonMsg instanceof SendMessage){
            sendMsg((SendMessage) commonMsg);
        } else if (commonMsg instanceof SendPhoto) {
            sendMsg((SendPhoto) commonMsg);
        } else if (commonMsg instanceof List) {
            if(((List<?>) commonMsg).get(0) instanceof SendPhoto) {
                sendPhotos((List<SendPhoto>) commonMsg);
            } else if (((List<?>) commonMsg).get(0) instanceof SendMessage){
                sendMsgs((List<SendMessage>) commonMsg);
            }
        }
    }

    private void sendMsg(SendMessage sendMessage){
        try {
            executeAsync(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMsg(SendPhoto sendPhoto){
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendPhotos(List<SendPhoto> sendPhotos){
        try {
            for (SendPhoto photo : sendPhotos){
                execute(photo);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMsgs(List<SendMessage> sendMessages){
        try {
            for (SendMessage message : sendMessages){
                execute(message);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
