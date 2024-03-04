package com.telegramBot.nutricion.bot;

public enum BotState {
    SHOW_START_MENU,//Показать стартовое меню
    FILLING_QUESTIONNAIRE,//Опросник по меню заполняется
    ASK_BABY_AGE,//Спросить возраст
    ASK_BIRTH_BABY_WEIGHT,//Спросить вес при рождении
    ASK_ILLNESS,//Спросить болезни
    ASK_BABY_WEIGHT,//Спросить вес при рождении
    ASK_BABY_SEX,//Спросить вес при рождении
    ASK_BABY_HEIGHT,//Спросить вес при рождении
    QUESTIONNAIRE_FILLED,//Опросник по меню заполнен
    FEEDING_UP_CALENDAR,//Календать прикормов
    PRODUCT_PROFIT,//Узнать больше о пользе продуктов
    CHOOSE_PRODUCT//Выбрать продукт
}
