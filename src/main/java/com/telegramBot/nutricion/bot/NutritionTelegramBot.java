package com.telegramBot.nutricion.bot;

import com.telegramBot.nutricion.bot.cache.BotCache;
import com.telegramBot.nutricion.bot.config.BotConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j
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
        answerProcessor(update);
    }

    public void answerProcessor(Update update) {
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
        BotState currentStatus = botCache.getUsersCurrentBotState(userId);
        String userName = callbackQuery.getFrom().getUserName();
        String userFio = callbackQuery.getFrom().getLastName() + " " + callbackQuery.getFrom().getFirstName();
        log.debug("(Callback) Пользователь: name=" + userName + "; fio=" + userFio + ". Текущий статус: " + currentStatus.getTitle());
        BotState botState = switch (callbackQuery.getData()) {
            case "start" -> BotState.SHOW_START_MENU;
            case "menu" -> BotState.FILLING_QUESTIONNAIRE;
            case "profit" -> BotState.PRODUCT_PROFIT;
            case "calendar" -> BotState.FEEDING_UP_CALENDAR;
            default -> currentStatus;
        };
        botCache.saveUsersCurrentBotState(userId, botState);
        sendNoTypeMessage(botState, update);
    }

    private void handleInputMessage(Update update){
        Message message = update.getMessage();
        long userId = message.getFrom().getId();
        String messageText = message.getText().toLowerCase();
        String userName = message.getFrom().getUserName();
        String userFio = message.getFrom().getLastName() + " " + message.getFrom().getFirstName();
        BotState currentStatus = botCache.getUsersCurrentBotState(userId);
        log.debug("(Messages) Пользователь: name=" + userName + "; fio=" + userFio + ". Текущий статус: " + currentStatus.getTitle());
        BotState botState = switch (messageText) {
            case "старт", "/start" -> BotState.SHOW_START_MENU;
            case "меню","/menu" -> BotState.FILLING_QUESTIONNAIRE;
            case "профит","/profit" -> BotState.PRODUCT_PROFIT;
            case "календарь","/calendar" -> BotState.FEEDING_UP_CALENDAR;
            default -> currentStatus;
        };
        botCache.saveUsersCurrentBotState(userId, botState);
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
            log.error(e);
        }
    }

    private void sendMsg(SendPhoto sendPhoto){
        executeAsync(sendPhoto);
    }

    private void sendPhotos(List<SendPhoto> sendPhotos){
        for (SendPhoto photo : sendPhotos){
            executeAsync(photo);
        }
    }

    private void sendMsgs(List<SendMessage> sendMessages){
        try {
            for (SendMessage message : sendMessages){
                executeAsync(message);
            }
        } catch (TelegramApiException e) {
            log.error(e);
        }
    }
}
