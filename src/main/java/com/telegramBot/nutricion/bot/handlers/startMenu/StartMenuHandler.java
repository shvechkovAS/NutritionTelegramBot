package com.telegramBot.nutricion.bot.handlers.startMenu;

import com.telegramBot.nutricion.bot.BotState;
import com.telegramBot.nutricion.bot.handlers.InputMessageHandler;
import com.telegramBot.nutricion.service.ButtonsService;
import com.telegramBot.nutricion.service.ReplyMessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
public class StartMenuHandler implements InputMessageHandler {

    @Autowired
    private ReplyMessagesService messagesService;

    @Autowired
    private ButtonsService buttonsService;

    @Override
    public SendMessage handle(Update update) {
        return processUsersInput(update);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_START_MENU;
    }

    private SendMessage processUsersInput(Update update){
        String chatId;
        if (update.hasCallbackQuery()){
            CallbackQuery callback = update.getCallbackQuery();
            chatId = callback.getMessage().getChatId().toString();
        } else {
            Message inputMsg = update.getMessage();
            chatId = inputMsg.getChatId().toString();
        }
        SendMessage message = messagesService.getReplyMessage(chatId, "reply.setStartAnswer");
        message.setReplyMarkup(buttonsService.setStartButtons());
        return message;
    }
}
