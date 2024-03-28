package com.telegramBot.nutricion.dictionaries;

public interface TextThick {

    String DESCRIPTION_FOURTH_THICK =
            """
            %s
            📌 Каждый прием пищи, должен укладываться в разовый объем: %.0f мл
                   
            🥦 Можно ввести овощные пюре. Продукт вводится постепенно с  увеличением объема (10 мл ➡️ 150  мл) в течении 7 дней, целесообразно давать в два кормления.
            Важно начинать с пюре из одного вида овощей, следующий вид вводится только после того, как пюре из первого овоща достигло объема 50 мл.
            Низкокалорийные овощные пюре: капуста, кабачки.
                   
            ❗️ Детские безмолочные каши без сахара промышленного выпуска рекомендуется ввести не раньше, чем в  5,5 месяцев.
            """;

    String MENU_FOURTH_THICK =
            """
            📖 Пример меню:
            
            1 неделя:
            7:00 – ГМ/АМС %.0f мл
            10:30 – Овощное пюре 5 мл
                    ГМ/АМС %.0f мл
            14:00 – ГМ/АМС %.0f мл
            17:30 – Овощное пюре 5 мл
                    ГМ/АМС %.0f мл
            21:00 – ГМ/АМС %.0f мл
            00:00 – ГМ/АМС %.0f мл
            🌙 Ночное кормление
            
            2–4 недели:
            7:00 – ГМ/АМС %.0f мл
            10:30 – Овощное пюре 75 мл
                    ГМ/АМС %.0f мл
            14:00 – ГМ/АМС %.0f мл
            17:30 – Овощное пюре 75 мл
                    ГМ/АМС %.0f мл
            21:00 – ГМ/АМС %.0f мл
            00:00 – ГМ/АМС %.0f мл
            🌙 Ночное кормление
            
            *ГМ – грудное молоко
            *АМС – адаптированная молочная смесь
            """;

    String DESCRIPTION_FIFTH_THICK =
            """
            %s
            📌 Каждый прием пищи, должен укладываться в разовый объем: %.0f мл
            
            Продукты следует вводить в рацион последовательно, внимательно отслеживая реакцию ребенка на каждый продукт.
            Обратите внимание на овощные пюре, введенные в 4 месяца. При избыточной массе тела их важно давать ребенку в 2 кормления по 70–100 мл, уменьшая объем грудного молока  (раздел «Календарь и правила введения прикорма»
            
            1 неделя 🍏
            Ребенку следует предложить ребенку фруктовое пюре промышленного выпуска, достигая максимального суточного объема  (5 мл ➡️ 50 мл) в течении 7 дней.
            Важно начинать с пюре из одного вида фруктов, следующий вид вводится только после того, как пюре из первого фрукта достигло объема 25 мл.
            
            3 неделя (5,5 месяцев) 🥣
            Вводятся детские безмолочные каши без сахара промышленного выпуска, разведенные водой, без добавления масла. Постепенно увеличивая объем до максимального суточного объема (10 мл ➡️ 150 мл) в течении 5–7 дней.
            """;

    String MENU_FIFTH_THICK =
            """
            📖Пример меню:
            
            1,2 недели:
            7:00 – ГМ/АМС %.0f мл
            10:30 – Фруктовое пюре 5 мл
                    ГМ/АМС %.0f мл
            14:00 – Овощное пюре 75 мл
                    Растительное масло 1 мл
                    ГМ/АМС %.0f мл
            17:30 – Фруктовое пюре 5 мл
                    ГМ/ АМС %.0f мл
            21:00 – Овощное пюре 75 мл
                    ГМ/АМС %.0f мл
            00:00 – ГМ/АМС %.0f мл
            🌙 Ночное кормление
            
            3 неделя:
            7:00 – ГМ/АМС %.0f мл
            10:30 – Безмолочная каша на воде 10 мл
                    Фруктовое пюре 40 мл
                    ГМ/ АМС %.0f мл
            14:00 – Овощное пюре 75 мл
                    Растительное масло 2 мл
                    ГМ/АМС %.0f мл
            17:30 – Фруктовое пюре 20 мл
                    ГМ/АМС %.0f мл
            21:00 – Овощное пюре 75 мл
                    ГМ/АМС %.0f мл
            00:00 – ГМ/АМС %.0f мл
            🌙 Ночное кормление
            
            4 неделя:
            7:00 – ГМ/АМС %.0f
            10:30 – Безмолочная каша на воде 70 мл
                    Фруктовое пюре 40 мл
                    ГМ/ АМС %.0f мл
            14:00 – Овощное пюре 75 мл
                    Растительное масло 2 мл
                    ГМ/АМС %.0f мл
            17:30 – Фруктовое пюре 20 мл
                    ГМ/АМС %.0f мл
            21:00 – Овощное пюре 75 мл
                    ГМ/АМС %.0f мл
            00:00 – ГМ/АМС %.0f мл
            🌙 Ночное кормление
            
            *ГМ – грудное молоко
            *АМС – адаптированная молочная смесь
            """;

    String DESCRIPTION_SIXTH_THICK =
            """
            %s
            📌 Каждый прием пищи, должен укладываться в разовый объем: %.0f мл
            
            Обратите внимание на продукты, которые должны были быть введены ранее (раздел «Календарь и правила введения прикорма»).
            
            🥩 На 1 неделе вводим мясное пюре. Продукт вводится постепенно с увеличением объема (5 мл ➡️ 30 мл) в течении 5–7 дней.
            Либо отварное мясо (3 мл ➡️ 15 мл)
            """;

    String MENU_SIXTH_THICK =
            """
            📖Пример меню:
            
            1 неделя:
            7:00 – ГМ/АМС %.0f мл
            11:00 – Овощное пюре 100 мл
                    Растительное масло 3 мл
                    Мясное пюре 5 мл
                    ГМ/АМС %.0f мл
            15:00 – Безмолочная каша, разведенная водой 80 мл
                    Фруктовое пюре 50 мл
                    ГМ/АМС %.0f мл
            19:00 – Овощное пюре 50 мл
                    ГМ/АМС %.0f мл
            23:00 – АМС/ГМ %.0f мл
            🌙 Ночное кормление
            
            2–4 недели:
            7:00 – ГМ/АМС %.0f мл
            11:00 – Безмолочная каша, разведенная водой  80 мл
                    Фруктовое пюре 50 мл
                    ГМ/АМС %.0f мл
            15:00 – Овощное пюре 70 мл
                    Растительное масло 3 мл
                    Мясное пюре 30 мл
                    ГМ/АМС %.0f мл
            19:00 – Овощное пюре 80 мл
                    ГМ/АМС %.0f мл
            23:00 – АМС/ГМ %.0f мл
            🌙 Ночное кормление
            
            *ГМ – грудное молоко
            *АМС – адаптированная молочная смесь
            """;

    String DESCRIPTION_SEVENTH_THICK =
            """
            %s
            📌 Каждый прием пищи, должен укладываться в разовый объем: %.0f мл
            
            🥚 Вводим отварной яичный желток. 1/4 желтка, добавляем в кашу.
            
            Обратите внимание на продукты, которые уже должны были быть введены ранее (раздел «Календарь и правила введения прикорма»).
            """;

    String MENU_SEVENTH_THICK =
            """
            📖Пример меню:
            7:00 – ГМ/АМС %.0f мл
            11:00 – Безмолочная каша, разведенная водой  75 мл
                    Желток вареного куриного яйца ¼ (добавить в кашу)
                    Фруктовое пюре 60 мл
                    ГМ/АМС %.0f мл
            15:00 – Овощное пюре 70 мл
                    Растительное масло 3 мл
                    Мясное пюре 50 мл
                    ГМ/АМС %.0f мл
            19:00 – Овощное пюре 80 мл
                    ГМ/АМС %.0f мл
            23:00 – АМС/ГМ %.0f мл
            🌙 Ночное кормление
            
            * ГМ – грудное молоко
            * АМС – адаптированная молочная смесь
            """;

    String DESCRIPTION_EIGHTH_THICK =
            """
            %s
            📌 Каждый прием пищи, должен укладываться в разовый объем: %.0f мл
            Обратите внимание на продукты, которые  должны были быть введены ранее (раздел «Календарь и правила введения прикорма» ➡️ Суточный объем прикормов).
            
            Прикормы следует вводить в рацион последовательно, внимательно отслеживая реакцию ребенка на каждый продукт.
            
            1 неделя 🥛
            Ребенку следует предложить кисломолочные продукты (творожки, кефир), достигая максимального суточного объема (творог: 10 мл ➡️ 40 мл, кефир: 200 мл) в течении 5–7 дней.
            
            2 неделя 🐟
            Вводится рыбное пюре. Продукт вводится также постепенно с  увеличением объема (5 мл ➡️ 30 мл) в течении 7 дней. Давать не реже 2 раз в неделю, заменяя мясного пюре.
            
            3 неделя 🍞
            Необходимо ввести в рацион хлебобулочные изделия: хлеб пшеничный/сухари 5 г.
            """;

    String MENU_EIGHTH_THICK =
            """
            📖 Пример меню:
            
            1 неделя:
            7:00 – ГМ/АМС %.0f мл
            11:00 – Творог 10 мл
                    Фруктовое пюре 80 мл
                    ГМ/АМС %.0f мл
            15:00 – Безмолочная каша, разведенная водой 130 мл
                    Желток вареного куриного яйца 1/2 (добавить в кашу)
                    ГМ/АМС %.0f мл
            19:00 – Овощное пюре 90  мл
                    Растительное масло 5 мл
                    Мясное пюре 45 мл
                    ГМ/АМС %.0f мл
            23:00 – ГМ/АМС %.0f мл
            🌙 Ночное кормление
            
            2 неделя:
            7:00 – ГМ/АМС %.0f мл
            11:00 – Овощное пюре 130 мл
                    Растительное масло 5 мл
                    Рыбное пюре 5 мл
                    ГМ/АМС %.0f мл
            15:00 – Безмолочная каша, на воде 110 мл
                    Желток вареного куриного яйца 1/2 (добавить в кашу)
                    Фруктовое пюре 20 мл
                    ГМ/АМС %.0f мл
            19:00 – Творог 30 мл
                    Фруктовое пюре 60 мл
                    ГМ/АМС %.0f мл
            23:00 –  Кефир 200 мл
            🌙 Ночное кормление
            
            3 неделя:
            7:00 – ГМ/АМС %.0f мл
            11:00 – Безмолочная каша, на воде 100 мл
                    Желток вареного куриного яйца 1/2 (добавить в кашу)
                    Фруктовое пюре 30 мл
                    ГМ/АМС %.0f мл
            15:00 – Овощное пюре 95 мл
                    Растительное масло 6 мл
                    Рыбное пюре 30 мл
                    Хлеб пшеничный 5 г
                    ГМ/АМС %.0f мл
            19:00 – Творог 40 мл
                    Фруктовое пюре 50 мл
                    ГМ/АМС %.0f мл
            23:00 – Кефир 200 мл
            🌙 Ночное кормление
            
            4 неделя:
            7:00 – ГМ/АМС %.0f мл
            11:00 – Безмолочная каша на воде 100 мл
                    Желток вареного куриного яйца 1/2 (добавить в кашу)
                    Фруктовое пюре 30 мл
                    ГМ/АМС %.0f мл
            15:00 – Овощное пюре 100 мл
                    Растительное масло 5 мл
                    Рыбное пюре 30 мл
                    Хлеб пшеничный 5 г
                    ГМ/АМС %.0f мл
            19:00 – Творог 40 мл
                    Фруктовое пюре 50 мл
                    ГМ/АМС %.0f мл
            23:00 –  Кефир 200 мл
            🌙 Ночное кормление
            
            * ГМ – грудное молоко
            * АМС – адаптированная молочная смесь
            """;

    String DESCRIPTION_NINTH_TO_TWELFTH_THICK =
            """
            %s
            📌 Каждый прием пищи, должен укладываться в разовый объем: %.0f мл
            
            ❗️ В этом возрасте должна меняться консистенция продуктов от гомогенизированной переходим к мелко– и крупноизмельченной.
            
            Обратите внимание на продукты, которые должны были быть введены ранее (раздел «Календарь и правила введения прикорма»).
            """;

    String MENU_NINTH_TO_TWELFTH_THICK =
            """
            📖 Пример меню:
            7:00  – ГМ/АМС %.0f мл
            11:00 – Безмолочная каша на воде 100 мл
                    Желток вареного куриного яйца 1/2 (добавить в кашу)
                    Фруктовое пюре 30 мл
                    ГМ/АМС %.0f мл
            15:00 – Овощное пюре 100 мл
                    Растительное масло 5 мл
                    Отварная курица 30 г
                    Хлеб пшеничный 5 г
                    ГМ/АМС %.0f мл
            19:00 – Творог 40 мл
                    Фруктовое пюре 70 мл
                    ГМ/АМС %.0f мл
            23:00 – Кефир 200 мл
            🌙 Ночное кормление
            
            *ГМ – грудное молоко
            *АМС – адаптированная молочная смесь
            """;
}
