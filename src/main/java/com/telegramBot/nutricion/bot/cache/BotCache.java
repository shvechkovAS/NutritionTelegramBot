package com.telegramBot.nutricion.bot.cache;

import com.telegramBot.nutricion.bot.BotState;
import com.telegramBot.nutricion.model.NutritionQuestionnaire;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class BotCache {
    private final Map<Long, BotState> BotStates = new HashMap<>();
    private final Map<Long, NutritionQuestionnaire> questionnaireData = new HashMap<>();

    public void setUsersCurrentBotState(long userId, BotState botState){
        BotStates.put(userId, botState);
    }

    public BotState getUsersCurrentBotState(long userId){
        BotState botState = BotStates.get(userId);
        if(botState == null){
            botState = BotState.SHOW_START_MENU;
        }
        return botState;
    }

    public NutritionQuestionnaire getNutritionQuestionnaireData(long userId){
        NutritionQuestionnaire nutritionQuestionnaire = questionnaireData.get(userId);
        if(nutritionQuestionnaire == null){
            nutritionQuestionnaire = new NutritionQuestionnaire();
        }
        return nutritionQuestionnaire;
    }

    public void saveNutritionQuestionnaireData(long userId, NutritionQuestionnaire nutritionQuestionnaire){
        questionnaireData.put(userId, nutritionQuestionnaire);
    }
}
