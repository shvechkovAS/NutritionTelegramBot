package com.telegramBot.nutricion.service;

import com.telegramBot.nutricion.dictionaries.*;
import com.telegramBot.nutricion.model.NutritionQuestionnaire;
import org.springframework.stereotype.Service;

@Service
public class NutritionQuestionnaireService {

    public void countValues(NutritionQuestionnaire nutritionQuestionnaire){
        double dmt;
        int babyAge = Integer.parseInt(nutritionQuestionnaire.getBabyAge());
        int birthWeight = Integer.parseInt(nutritionQuestionnaire.getBirthBabyWeight());
        double weight = Double.parseDouble(nutritionQuestionnaire.getBabyWeight());
        int height = Integer.parseInt(nutritionQuestionnaire.getBabyHeight());

        if (babyAge <= 6) {
            dmt = birthWeight + (800 * babyAge);
        } else {
            dmt = birthWeight + 4800 + 400 * (babyAge - 6);
        }
        nutritionQuestionnaire.setDmt(dmt);

        double dayVolume = getDayVolume(babyAge, dmt);
        nutritionQuestionnaire.setDayVolume(dayVolume);

        double oneVolume;
        if(babyAge < 6){
            oneVolume = dayVolume / 6.00;
        } else {
            oneVolume = dayVolume / 5.00;
        }
        String formattedOneVolume = String.format("%.0f", oneVolume);
        nutritionQuestionnaire.setOneVolume(formattedOneVolume);

        double heightInMeters = (double) height / 100;
        double massBodyIndex = weight / (heightInMeters * heightInMeters);
        generateCodeMassBodyIndex(nutritionQuestionnaire, massBodyIndex);
        generateTextMassBodyIndex(nutritionQuestionnaire);
    }

    private static double getDayVolume(int babyAge, double dmt) {
        double dayVolume = 0.0;
        if(babyAge >= 4 && babyAge<6){
            dayVolume = dmt / 7.0;
        } else if (babyAge >= 6 && babyAge<8){
            dayVolume = dmt / 8.0;
        } else if (babyAge >= 8 && babyAge<=12) {
            dayVolume = dmt / 9.0;
        }
        if(dayVolume > 1000){
            dayVolume = 1000;
        }
        return dayVolume;
    }

    private void generateCodeMassBodyIndex(NutritionQuestionnaire nutritionQuestionnaire, double massBodyIndex){
        nutritionQuestionnaire.setCodeMassBodyIndex(CodeBodyMassIndex.CODE_NORMAL);
        int babyAge = Integer.parseInt(nutritionQuestionnaire.getBabyAge());
        boolean sexMale = nutritionQuestionnaire.isMale();
        if (sexMale){
            if(((babyAge == 4 || babyAge == 11) && massBodyIndex < 14.5)
                    || ((babyAge == 5 || babyAge == 6 || babyAge == 8 || babyAge == 9) && massBodyIndex < 14.7)
                    || (babyAge == 7 && massBodyIndex < 14.8)
                    || (babyAge == 10 && massBodyIndex < 14.6)
                    || (babyAge == 12 && massBodyIndex < 14.4)) {
                 nutritionQuestionnaire.setCodeMassBodyIndex(CodeBodyMassIndex.CODE_VERY_THIN);
            } else if((babyAge == 4 && massBodyIndex >= 14.5 && massBodyIndex <= 15.7)
                    ||(babyAge == 5 && massBodyIndex >= 14.7 && massBodyIndex <= 15.8)
                    ||(babyAge == 6 && massBodyIndex >= 14.7 && massBodyIndex <= 15.9)
                    ||(babyAge == 7 && massBodyIndex >= 14.8 && massBodyIndex <= 15.9)
                    ||(babyAge == 8 && massBodyIndex >= 14.7 && massBodyIndex <= 15.8)
                    ||(babyAge == 9 && massBodyIndex >= 14.7 && massBodyIndex <= 15.7)
                    ||(babyAge == 10 && massBodyIndex >= 14.6 && massBodyIndex <= 15.6)
                    ||(babyAge == 11 && massBodyIndex >= 14.5 && massBodyIndex <= 15.5)
                    ||(babyAge == 12 && massBodyIndex >= 14.4 && massBodyIndex <= 15.4)) {
                nutritionQuestionnaire.setCodeMassBodyIndex(CodeBodyMassIndex.CODE_THIN);
            } else if((babyAge == 4 && massBodyIndex >= 18.8 && massBodyIndex <= 20.3)
                    ||((babyAge == 5 || babyAge == 6 || babyAge == 7) && massBodyIndex >= 18.9 && massBodyIndex <= 20.5)
                    ||(babyAge == 8 && massBodyIndex >= 18.8 && massBodyIndex <= 20.4)
                    ||(babyAge == 9 && massBodyIndex >= 18.7 && massBodyIndex <= 20.3)
                    ||(babyAge == 10 && massBodyIndex >= 18.6 && massBodyIndex <= 20.1)
                    ||(babyAge == 11 && massBodyIndex >= 18.5 && massBodyIndex <= 20.0)
                    ||(babyAge == 12 && massBodyIndex >= 18.3 && massBodyIndex <= 19.8)) {
                nutritionQuestionnaire.setCodeMassBodyIndex(CodeBodyMassIndex.CODE_THICK);
            } else if(((babyAge == 4 || babyAge == 9) && massBodyIndex >20.3)
                    || ((babyAge == 5 || babyAge == 6 || babyAge == 7) && massBodyIndex >20.5)
                    || (babyAge == 8 && massBodyIndex >20.4) || (babyAge == 10 && massBodyIndex >20.1)
                    || (babyAge == 11 && massBodyIndex >20.0) || (babyAge == 12 && massBodyIndex >19.8)) {
                nutritionQuestionnaire.setCodeMassBodyIndex(CodeBodyMassIndex.CODE_VERY_THICK);
            }
        } else {
            if(((babyAge == 4 || babyAge == 11) && massBodyIndex < 13.9)
                    || ((babyAge == 5 || babyAge == 6 || babyAge == 8 || babyAge == 9) && massBodyIndex < 14.1)
                    || (babyAge == 7 && massBodyIndex < 14.2)
                    || (babyAge == 10 && massBodyIndex < 14.0)
                    || (babyAge == 12 && massBodyIndex < 13.8)) {
                nutritionQuestionnaire.setCodeMassBodyIndex(CodeBodyMassIndex.CODE_VERY_THIN);
            } else if((babyAge == 4 && massBodyIndex >= 13.9 && massBodyIndex <= 15.1)
                    ||((babyAge == 5 || babyAge == 8) && massBodyIndex >= 14.1 && massBodyIndex <= 15.3)
                    ||(babyAge == 6 && massBodyIndex >= 14.1 && massBodyIndex <= 15.4)
                    ||(babyAge == 7 && massBodyIndex >= 14.2 && massBodyIndex <= 15.4)
                    ||(babyAge == 9 && massBodyIndex >= 14.1 && massBodyIndex <= 15.2)
                    ||(babyAge == 10 && massBodyIndex >= 14.0 && massBodyIndex <= 15.1)
                    ||(babyAge == 11 && massBodyIndex >= 13.9 && massBodyIndex <= 15.0)
                    ||(babyAge == 12 && massBodyIndex >= 13.8 && massBodyIndex <= 14.9)) {
                nutritionQuestionnaire.setCodeMassBodyIndex(CodeBodyMassIndex.CODE_THIN);
            } else if((babyAge == 4 && massBodyIndex >= 18.4 && massBodyIndex <= 20.0)
                    ||((babyAge == 5 || babyAge == 8) && massBodyIndex >= 18.5 && massBodyIndex <= 20.2)
                    ||((babyAge == 6 || babyAge == 7) && massBodyIndex >= 18.6 && massBodyIndex <= 20.3)
                    ||(babyAge == 9 && massBodyIndex >= 18.4 && massBodyIndex <= 20.1)
                    ||(babyAge == 10 && massBodyIndex >= 18.3 && massBodyIndex <= 19.9)
                    ||(babyAge == 11 && massBodyIndex >= 18.1 && massBodyIndex <= 19.8)
                    ||(babyAge == 12 && massBodyIndex >= 18.0 && massBodyIndex <= 19.6)) {
                nutritionQuestionnaire.setCodeMassBodyIndex(CodeBodyMassIndex.CODE_THICK);
            } else if((babyAge == 4 && massBodyIndex >20.0)
                    || ((babyAge == 5 || babyAge == 8) && massBodyIndex >20.2)
                    || (babyAge == 6 || babyAge == 7 && massBodyIndex >20.3)
                    || (babyAge == 9 && massBodyIndex >20.1)
                    || (babyAge == 10 && massBodyIndex >19.9)
                    || (babyAge == 11 && massBodyIndex >19.8)
                    || (babyAge == 12 && massBodyIndex >19.6)) {
                nutritionQuestionnaire.setCodeMassBodyIndex(CodeBodyMassIndex.CODE_VERY_THICK);
            }
        }
    }

    private void generateTextMassBodyIndex(NutritionQuestionnaire nutritionQuestionnaire){
        switch (nutritionQuestionnaire.getCodeMassBodyIndex()) {
            case CodeBodyMassIndex.CODE_NORMAL -> nutritionQuestionnaire.setTextMassBodyIndex(CodeBodyMassIndex.TEXT_NORMAL);
            case CodeBodyMassIndex.CODE_VERY_THIN -> nutritionQuestionnaire.setTextMassBodyIndex(CodeBodyMassIndex.TEXT_VERY_THIN);
            case CodeBodyMassIndex.CODE_THIN -> nutritionQuestionnaire.setTextMassBodyIndex(CodeBodyMassIndex.TEXT_THIN);
            case CodeBodyMassIndex.CODE_THICK -> nutritionQuestionnaire.setTextMassBodyIndex(CodeBodyMassIndex.TEXT_THICK);
            case CodeBodyMassIndex.CODE_VERY_THICK -> nutritionQuestionnaire.setTextMassBodyIndex(CodeBodyMassIndex.TEXT_VERY_THICK);
        }
    }

    public void menuText(NutritionQuestionnaire nutritionQuestionnaire){
        int illness =  nutritionQuestionnaire.getIllness();
        int codeIMT = nutritionQuestionnaire.getCodeMassBodyIndex();
        int babyAge = Integer.parseInt(nutritionQuestionnaire.getBabyAge());
        switch (codeIMT) {
            case CodeBodyMassIndex.CODE_NORMAL:
                if (illness == Illness.NO_ILLNESS) {
                    switch (babyAge) {
                        case 1, 2, 3, 4:
                            nutritionQuestionnaire.setMenu(TextNormal.MENU_FOURTH);
                            break;
                        case 5:
                            nutritionQuestionnaire.setMenu(TextNormal.MENU_FIFTH_HEALTHY);
                            nutritionQuestionnaire.setDescription(TextNormal.DESCRIPTION_FIFTH_HEALTHY);
                            break;
                        case 6:
                            nutritionQuestionnaire.setMenu(TextNormal.MENU_SIXTH_HEALTHY);
                            nutritionQuestionnaire.setDescription(TextNormal.DESCRIPTION_SIXTH_HEALTHY);
                            break;
                        case 7:
                            nutritionQuestionnaire.setMenu(TextNormal.MENU_SEVENTH_HEALTHY);
                            nutritionQuestionnaire.setDescription(TextNormal.DESCRIPTION_SEVENTH_HEALTHY);
                            break;
                        case 8:
                            nutritionQuestionnaire.setMenu(TextNormal.MENU_EIGHTH_HEALTHY);
                            nutritionQuestionnaire.setDescription(TextNormal.DESCRIPTION_EIGHTH_HEALTHY);
                            break;
                        case 9, 10, 11, 12:
                            nutritionQuestionnaire.setMenu(TextNormal.MENU_NINTH_TO_TWELFTH_HEALTHY);
                            nutritionQuestionnaire.setDescription(TextNormal.DESCRIPTION_NINTH_TO_TWELFTH_HEALTHY);
                            break;
                        default:
                            nutritionQuestionnaire.setMenu(null);
                            nutritionQuestionnaire.setDescription(null);
                            break;
                    };
                } else if (illness == Illness.CONSTIPATION) {
                    switch (babyAge) {
                        case 1, 2, 3, 4:
                            nutritionQuestionnaire.setMenu(TextNormalConstipation.MENU_FOURTH);
                            nutritionQuestionnaire.setDescription(TextNormalConstipation.DESCRIPTION_FOURTH);
                            break;
                        case 5:
                            nutritionQuestionnaire.setMenu(TextNormalConstipation.MENU_FIFTH);
                            nutritionQuestionnaire.setDescription(TextNormalConstipation.DESCRIPTION_FIFTH);
                            break;
                        case 6:
                            nutritionQuestionnaire.setMenu(TextNormalConstipation.MENU_SIXTH);
                            nutritionQuestionnaire.setDescription(TextNormalConstipation.DESCRIPTION_SIXTH);
                            break;
                        case 7:
                            nutritionQuestionnaire.setMenu(TextNormalConstipation.MENU_SEVENTH);
                            nutritionQuestionnaire.setDescription(TextNormalConstipation.DESCRIPTION_SEVENTH);
                            break;
                        case 8:
                            nutritionQuestionnaire.setMenu(TextNormalConstipation.MENU_EIGHTH);
                            nutritionQuestionnaire.setDescription(TextNormalConstipation.DESCRIPTION_EIGHTH);
                            break;
                        case 9, 10, 11, 12:
                            nutritionQuestionnaire.setMenu(TextNormalConstipation.MENU_NINTH_TO_TWELFTH);
                            nutritionQuestionnaire.setDescription(TextNormalConstipation.DESCRIPTION_NINTH_TO_TWELFTH);
                            break;
                        default:
                            nutritionQuestionnaire.setMenu(null);
                            nutritionQuestionnaire.setDescription(null);
                            break;
                    };
                } else if (illness == Illness.REGURGITATION) {
                    switch (babyAge) {
                        case 1, 2, 3, 4:
                            nutritionQuestionnaire.setMenu(TextNormalRegurgitation.MENU_FOURTH);
                            nutritionQuestionnaire.setDescription(TextNormalRegurgitation.DESCRIPTION_FOURTH);
                            break;
                        case 5:
                            nutritionQuestionnaire.setMenu(TextNormalRegurgitation.MENU_FIFTH);
                            nutritionQuestionnaire.setDescription(TextNormalRegurgitation.DESCRIPTION_FIFTH);
                            break;
                        case 6:
                            nutritionQuestionnaire.setMenu(TextNormal.MENU_SIXTH_HEALTHY);
                            nutritionQuestionnaire.setDescription(TextNormal.DESCRIPTION_SIXTH_HEALTHY);
                            break;
                        case 7:
                            nutritionQuestionnaire.setMenu(TextNormal.MENU_SEVENTH_HEALTHY);
                            nutritionQuestionnaire.setDescription(TextNormal.DESCRIPTION_SEVENTH_HEALTHY);
                            break;
                        case 8:
                            nutritionQuestionnaire.setMenu(TextNormal.MENU_EIGHTH_HEALTHY);
                            nutritionQuestionnaire.setDescription(TextNormal.DESCRIPTION_EIGHTH_HEALTHY);
                            break;
                        case 9, 10, 11, 12:
                            nutritionQuestionnaire.setMenu(TextNormal.MENU_NINTH_TO_TWELFTH_HEALTHY);
                            nutritionQuestionnaire.setDescription(TextNormal.DESCRIPTION_NINTH_TO_TWELFTH_HEALTHY);
                            break;
                        default:
                            nutritionQuestionnaire.setMenu(null);
                            nutritionQuestionnaire.setDescription(null);
                            break;
                    };
                }
                break;
            case CodeBodyMassIndex.CODE_VERY_THIN:
                switch (babyAge) {
                    case 1, 2, 3, 4:
                        nutritionQuestionnaire.setMenu(TextThin.MENU_FOURTH_THIN);
                        nutritionQuestionnaire.setDescription(TextThin.DESCRIPTION_FOURTH_THIN);
                        break;
                    case 5:
                        nutritionQuestionnaire.setMenu(TextThin.MENU_FIFTH_THIN);
                        nutritionQuestionnaire.setDescription(TextThin.DESCRIPTION_FIFTH_THIN);
                        break;
                    case 6:
                        nutritionQuestionnaire.setMenu(TextThin.MENU_SIXTH_THIN);
                        nutritionQuestionnaire.setDescription(TextThin.DESCRIPTION_SIXTH_THIN);
                        break;
                    case 7:
                        nutritionQuestionnaire.setMenu(TextThin.MENU_SEVENTH_THIN);
                        nutritionQuestionnaire.setDescription(TextThin.DESCRIPTION_SEVENTH_THIN);
                        break;
                    case 8:
                        nutritionQuestionnaire.setMenu(TextThin.MENU_EIGHTH_THIN);
                        nutritionQuestionnaire.setDescription(TextThin.DESCRIPTION_EIGHTH_THIN);
                        break;
                    case 9, 10, 11, 12:
                        nutritionQuestionnaire.setMenu(TextThin.MENU_NINTH_TO_TWELFTH_THIN);
                        nutritionQuestionnaire.setDescription(TextThin.DESCRIPTION_NINTH_TO_TWELFTH_THIN);
                        break;
                    default:
                        nutritionQuestionnaire.setMenu(null);
                        nutritionQuestionnaire.setDescription(null);
                        break;
                };
                break;
            case CodeBodyMassIndex.CODE_THIN:
                switch (babyAge) {
                    case 1, 2, 3, 4:
                        nutritionQuestionnaire.setMenu(TextThin.MENU_FOURTH_THIN);
                        nutritionQuestionnaire.setDescription(TextThin.DESCRIPTION_FOURTH_THIN);
                        break;
                    case 5:
                        nutritionQuestionnaire.setMenu(TextThin.MENU_FIFTH_THIN);
                        nutritionQuestionnaire.setDescription(TextThin.DESCRIPTION_FIFTH_THIN);
                        break;
                    case 6:
                        nutritionQuestionnaire.setMenu(TextThin.MENU_SIXTH_THIN);
                        nutritionQuestionnaire.setDescription(TextThin.DESCRIPTION_SIXTH_THIN);
                        break;
                    case 7:
                        nutritionQuestionnaire.setMenu(TextThin.MENU_SEVENTH_THIN);
                        nutritionQuestionnaire.setDescription(TextThin.DESCRIPTION_SEVENTH_THIN);
                        break;
                    case 8:
                        nutritionQuestionnaire.setMenu(TextThin.MENU_EIGHTH_THIN);
                        nutritionQuestionnaire.setDescription(TextThin.DESCRIPTION_EIGHTH_THIN);
                        break;
                    case 9, 10, 11, 12:
                        nutritionQuestionnaire.setMenu(TextThin.MENU_NINTH_TO_TWELFTH_THIN);
                        nutritionQuestionnaire.setDescription(TextThin.DESCRIPTION_NINTH_TO_TWELFTH_THIN);
                        break;
                    default:
                        nutritionQuestionnaire.setMenu(null);
                        nutritionQuestionnaire.setDescription(null);
                        break;
                };
                break;
            case CodeBodyMassIndex.CODE_THICK:
                switch (babyAge) {
                    case 1, 2, 3, 4:
                        nutritionQuestionnaire.setMenu(TextThick.MENU_FOURTH_THICK);
                        nutritionQuestionnaire.setDescription(TextThick.DESCRIPTION_FOURTH_THICK);
                        break;
                    case 5:
                        nutritionQuestionnaire.setMenu(TextThick.MENU_FIFTH_THICK);
                        nutritionQuestionnaire.setDescription(TextThick.DESCRIPTION_FIFTH_THICK);
                        break;
                    case 6:
                        nutritionQuestionnaire.setMenu(TextThick.MENU_SIXTH_THICK);
                        nutritionQuestionnaire.setDescription(TextThick.DESCRIPTION_SIXTH_THICK);
                        break;
                    case 7:
                        nutritionQuestionnaire.setMenu(TextThick.MENU_SEVENTH_THICK);
                        nutritionQuestionnaire.setDescription(TextThick.DESCRIPTION_SEVENTH_THICK);
                        break;
                    case 8:
                        nutritionQuestionnaire.setMenu(TextThick.MENU_EIGHTH_THICK);
                        nutritionQuestionnaire.setDescription(TextThick.DESCRIPTION_EIGHTH_THICK);
                        break;
                    case 9, 10, 11, 12:
                        nutritionQuestionnaire.setMenu(TextThick.MENU_NINTH_TO_TWELFTH_THICK);
                        nutritionQuestionnaire.setDescription(TextThick.DESCRIPTION_NINTH_TO_TWELFTH_THICK);
                        break;
                    default:
                        nutritionQuestionnaire.setMenu(null);
                        nutritionQuestionnaire.setDescription(null);
                        break;
                };
                break;
            case CodeBodyMassIndex.CODE_VERY_THICK:
                switch (babyAge) {
                    case 1, 2, 3, 4:
                        nutritionQuestionnaire.setMenu(TextVeryThick.MENU_FOURTH_VERY_THICK);
                        nutritionQuestionnaire.setDescription(TextVeryThick.DESCRIPTION_FOURTH_VERY_THICK);
                        break;
                    case 5:
                        nutritionQuestionnaire.setMenu(TextVeryThick.MENU_FIFTH_VERY_THICK);
                        nutritionQuestionnaire.setDescription(TextVeryThick.DESCRIPTION_FIFTH_VERY_THICK);
                        break;
                    case 6:
                        nutritionQuestionnaire.setMenu(TextVeryThick.MENU_SIXTH_VERY_THICK);
                        nutritionQuestionnaire.setDescription(TextVeryThick.DESCRIPTION_SIXTH_VERY_THICK);
                        break;
                    case 7:
                        nutritionQuestionnaire.setMenu(TextVeryThick.MENU_SEVENTH_VERY_THICK);
                        nutritionQuestionnaire.setDescription(TextVeryThick.DESCRIPTION_SEVENTH_VERY_THICK);
                        break;
                    case 8:
                        nutritionQuestionnaire.setMenu(TextVeryThick.MENU_EIGHTH_VERY_THICK);
                        nutritionQuestionnaire.setDescription(TextVeryThick.DESCRIPTION_EIGHTH_VERY_THICK);
                        break;
                    case 9, 10, 11, 12:
                        nutritionQuestionnaire.setMenu(TextVeryThick.MENU_NINTH_TO_TWELFTH_VERY_THICK);
                        nutritionQuestionnaire.setDescription(TextVeryThick.DESCRIPTION_NINTH_TO_TWELFTH_VERY_THICK);
                        break;
                    default:
                        nutritionQuestionnaire.setMenu(null);
                        nutritionQuestionnaire.setDescription(null);
                        break;
                };
                break;
        }
    }
}
