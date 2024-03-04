package com.telegramBot.nutricion.bot.handlers.fillingQuestionnaire;

import com.telegramBot.nutricion.bot.BotState;
import com.telegramBot.nutricion.bot.handlers.InputMessageHandler;
import com.telegramBot.nutricion.bot.cache.BotCache;
import com.telegramBot.nutricion.dictionaries.CodeBodyMassIndex;
import com.telegramBot.nutricion.model.NutritionQuestionnaire;
import com.telegramBot.nutricion.service.ButtonsService;
import com.telegramBot.nutricion.service.NutritionQuestionnaireService;
import com.telegramBot.nutricion.service.ReplyMessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class FillingQuestionnaireHandler implements InputMessageHandler {

    @Autowired
    private BotCache botCache;

    @Autowired
    private ReplyMessagesService messagesService;

    @Autowired
    private NutritionQuestionnaireService nutritionQuestionnaireService;

    @Autowired
    private ButtonsService buttonsService;

    @Override
    public Object handle(Update update) {
        if (update.hasCallbackQuery()){
            CallbackQuery callbackQuery = update.getCallbackQuery();
            if(botCache.getUsersCurrentBotState(callbackQuery.getFrom().getId())
                    .equals(BotState.FILLING_QUESTIONNAIRE)){
                botCache.setUsersCurrentBotState(callbackQuery.getFrom().getId(), BotState.ASK_BABY_AGE);
            }
        } else if (update.hasMessage()){
            Message message = update.getMessage();
            if(botCache.getUsersCurrentBotState(message.getFrom().getId())
                    .equals(BotState.FILLING_QUESTIONNAIRE)){
                botCache.setUsersCurrentBotState(message.getFrom().getId(), BotState.ASK_BABY_AGE);
            }
        }

        return processUsersInput(update);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.FILLING_QUESTIONNAIRE;
    }

    private List<SendMessage> processUsersInput(Update update){
        String usersAnswer;
        long userId;
        String chatId;
        List<SendMessage> messages = new ArrayList<>();
        if (update.hasCallbackQuery()){
            CallbackQuery callback = update.getCallbackQuery();
            usersAnswer = callback.getData();
            userId = callback.getFrom().getId();
            chatId = callback.getMessage().getChatId().toString();
        } else {
            Message inputMsg = update.getMessage();
            usersAnswer = inputMsg.getText();
            userId = inputMsg.getFrom().getId();
            chatId = inputMsg.getChatId().toString();
        }

        NutritionQuestionnaire questionnaireData = botCache.getNutritionQuestionnaireData(userId);
        BotState botState = botCache.getUsersCurrentBotState(userId);

        SendMessage replyToUser;

        if (botState.equals(BotState.ASK_BABY_AGE)) {
            replyToUser = messagesService.getReplyMessage(chatId, "reply.askBabyAge");
            replyToUser.setReplyMarkup(buttonsService.setMenuButtons());
            botCache.setUsersCurrentBotState(userId, BotState.ASK_BIRTH_BABY_WEIGHT);
            messages.add(replyToUser);
        }

        if (botState.equals(BotState.ASK_BIRTH_BABY_WEIGHT)) {
            if (update.hasCallbackQuery()){
                usersAnswer = update.getCallbackQuery().getData();
            } else {
                botCache.setUsersCurrentBotState(userId, BotState.ASK_BABY_AGE);
                messages.add(new SendMessage(chatId, "Выберите возраст с помощью предлагаемых кнопок."));
                return messages;
            }
            questionnaireData.setBabyAge(usersAnswer);
            replyToUser = messagesService.getReplyMessage(chatId, "reply.askBirthBabyWeight");
            messages.add(replyToUser);
            botCache.setUsersCurrentBotState(userId, BotState.ASK_BABY_HEIGHT);
        }

        if (botState.equals(BotState.ASK_BABY_HEIGHT)) {
            questionnaireData.setBirthBabyWeight(usersAnswer.replaceAll("[^0-9]", ""));
            replyToUser = messagesService.getReplyMessage(chatId, "reply.askBabyHeight");
            messages.add(replyToUser);
            botCache.setUsersCurrentBotState(userId, BotState.ASK_BABY_WEIGHT);
        }

        if (botState.equals(BotState.ASK_BABY_WEIGHT)) {
            questionnaireData.setBabyHeight(usersAnswer.replaceAll(",", ".").replaceAll("[^0-9.]", ""));
            replyToUser = messagesService.getReplyMessage(chatId, "reply.askBabyWeight");
            messages.add(replyToUser);
            botCache.setUsersCurrentBotState(userId, BotState.ASK_BABY_SEX);
        }

        if (botState.equals(BotState.ASK_BABY_SEX)) {
            if(Objects.isNull(questionnaireData.getBabyWeight())) {
                questionnaireData.setBabyWeight(usersAnswer.replaceAll(",", ".").replaceAll("[^0-9.]", ""));
            }
            replyToUser = messagesService.getReplyMessage(chatId, "reply.askBabySex");
            replyToUser.setReplyMarkup(buttonsService.setSexButtons());
            messages.add(replyToUser);
            botCache.setUsersCurrentBotState(userId, BotState.ASK_ILLNESS);
        }

        if (botState.equals(BotState.ASK_ILLNESS)) {
            if (update.hasCallbackQuery()){
                usersAnswer = update.getCallbackQuery().getData();
            } else {
                botCache.setUsersCurrentBotState(userId, BotState.ASK_BABY_SEX);
                messages.add(new SendMessage(chatId, "Выберите пол с помощью предлагаемых кнопок."));
                return messages;
            }
            if (usersAnswer.equals("male")){
                questionnaireData.setMale(true);
            } else if (usersAnswer.equals("female")) {
                questionnaireData.setMale(false);
            }
            nutritionQuestionnaireService.countValues(questionnaireData);
            if(questionnaireData.getCodeMassBodyIndex()== CodeBodyMassIndex.CODE_NORMAL){
                replyToUser = messagesService.getReplyMessage(chatId, "reply.askIllness");
                replyToUser.setReplyMarkup(buttonsService.setIllnessButtons());
                messages.add(replyToUser);
                botCache.setUsersCurrentBotState(userId, BotState.QUESTIONNAIRE_FILLED);
            } else {
                replyToUser = messagesService.getReplyMessage(chatId, "reply.askCount");
                replyToUser.setReplyMarkup(buttonsService.setCountButtons());
                messages.add(replyToUser);
                botCache.setUsersCurrentBotState(userId, BotState.QUESTIONNAIRE_FILLED);
            }
        }

        if (botState.equals(BotState.QUESTIONNAIRE_FILLED)) {
            if (update.hasCallbackQuery()){
                usersAnswer = update.getCallbackQuery().getData();
            }  else {
                botCache.setUsersCurrentBotState(userId, BotState.ASK_ILLNESS);
                messages.add(new SendMessage(chatId, "Выберите болезни с помощью предлагаемых кнопок."));
                return messages;
            }
            if(questionnaireData.getCodeMassBodyIndex() == CodeBodyMassIndex.CODE_NORMAL) {
                questionnaireData.setIllness(Integer.parseInt(usersAnswer));
            } else {
                questionnaireData.setIllness(0);
            }
            nutritionQuestionnaireService.menuText(questionnaireData);
            botCache.setUsersCurrentBotState(userId, BotState.SHOW_START_MENU);

            String builder = questionnaireData.getTextMassBodyIndex()
                    + "\n\n"
                    + "Один приём пищи для вашего ребёнка должен укладываться в "
                    + questionnaireData.getOneVolume()
                    + " мл (Разовый объём).\n\n"
                    + questionnaireData.getMenu();
            replyToUser = new SendMessage(chatId, builder);
            if(Objects.isNull(questionnaireData.getDescription()) || questionnaireData.getDescription().isEmpty()){
                replyToUser.setReplyMarkup(buttonsService.setBacktrackButton());
            }
            messages.add(replyToUser);

            if(Objects.nonNull(questionnaireData.getDescription()) && !questionnaireData.getDescription().isEmpty()){
                replyToUser = new SendMessage(chatId, questionnaireData.getDescription());
                replyToUser.setReplyMarkup(buttonsService.setBacktrackButton());
                messages.add(replyToUser);
            }
        }
        botCache.saveNutritionQuestionnaireData(userId, questionnaireData);
        return messages;
    }
}
