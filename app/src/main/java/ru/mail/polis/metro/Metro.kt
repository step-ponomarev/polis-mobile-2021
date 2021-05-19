package ru.mail.polis.metro

import ru.mail.polis.R

enum class Metro(val stationName: String, val branchColor: Int) {
    AVTOVO("Автово", R.color.metro_red),
    ADMIRALTEYSKAYA("Адмиралтейская", R.color.metro_purple),
    ACADEMIC("Академическая", R.color.metro_red),
    BALTIC("Балтийская", R.color.metro_red),
    BUCHAREST("Бухарестская", R.color.metro_purple),
    VASILEOSTROVSKAYA("Василеостровская", R.color.metro_green),
    VLADIMIRSKAYA("Владимирская", R.color.metro_green),
    VOLKOVSKYAYA("Волковская", R.color.metro_purple),
    VYBORGSKAYA("Выборгская", R.color.metro_red),
    GORKOVSKAYA("Горьковская", R.color.metro_blue),
    GOSTINY_DVOR("Гостиный двор", R.color.metro_green),
    CIVIL_PROSPECT("Гражданский проспект", R.color.metro_red),
    DEVYATKINO("Девяткино", R.color.metro_red),
    DOSTOEVSKAYA("Достоевская", R.color.metro_orange),
    ELIZAROVSKAYA("Елизаровская", R.color.metro_green),
    STAR("Звёздная", R.color.metro_blue),
    ZVENIGORODSKAYA("Звенигородская", R.color.metro_purple),
    KIROVSKY_ZAVOD("Кировский завод", R.color.metro_red),
    COMMANDANT_PROSPECT("Комендантский проспект", R.color.metro_purple),
    KRESTOVSKY_ISLAND("Крестовский остров", R.color.metro_purple),
    KUPCHINO("Купчино", R.color.metro_blue),
    LADOGA("Ладожская", R.color.metro_orange),
    LENINSKY_PROSPECT("Ленинский проспект", R.color.metro_red),
    LESNAYA("Лесная", R.color.metro_red),
    LIGOVSKY_PROSPECT("Лиговский проспект", R.color.metro_orange),
    LOMONOSOVSKAYA("Ломоносовская", R.color.metro_green),
    MAYAKOVSKAYA("Маяковская", R.color.metro_green),
    INTERNATIONAL("Международная", R.color.metro_purple),
    MOSCOW("Московская", R.color.metro_blue),
    MOSCOW_GATES("Московские ворота", R.color.metro_blue),
    NARVA("Нарвская", R.color.metro_red),
    NEVSKY_PROSPECT("Невский проспект", R.color.metro_blue),
    NOVOCHERSSKAYA("Новочеркасская", R.color.metro_orange),
    OBVODNYI_CHANNEL("Обводный канал", R.color.metro_purple),
    OBUKHOVO("Обухово", R.color.metro_green),
    OZERKI("Озерки", R.color.metro_blue),
    VICTORY_PARK("Парк Победы", R.color.metro_blue),
    PARNASSUS("Парнас", R.color.metro_blue),
    PETROGRADSKAYA("Петроградская", R.color.metro_blue),
    PIONERSKAYA("Пионерская", R.color.metro_blue),
    ALEXANDER_NEVSKY_PLOSHCHAD_ONE("Площадь Александра Невского 1", R.color.metro_green),
    ALEXANDER_NEVSKY_PLOSHCHAD_TWO("Площадь Александра Невского 2", R.color.metro_orange),
    VOSSTANIYA_PLOSHCHAD("Площадь Восстания", R.color.metro_red),
    LENIN_PLOSHCHAD("Площадь Ленина", R.color.metro_red),
    COURAGE_PLOSHCHAD("Площадь Мужества", R.color.metro_red),
    POLYTECHNIC("Политехническая", R.color.metro_red),
    PRIMORSKAYA("Приморская", R.color.metro_green),
    PROLETARIAN("Пролетарская", R.color.metro_green),
    BOLSHEVIKS_PROSPECT("Проспект Большевиков", R.color.metro_orange),
    VETERANS_PROSPECT("Проспект Ветеранов", R.color.metro_red),
    EDUCATION_PROSPECT("Проспект Просвещения", R.color.metro_blue),
    PUSHKINSKAYA("Пушкинская", R.color.metro_green),
    RYBATSKOE("Рыбацкое", R.color.metro_green),
    SADOVAYA("Садовая", R.color.metro_purple),
    SENNAYA_PLOSHCHAD("Сенная площадь", R.color.metro_blue),
    SPASSKAYA("Спасская", R.color.metro_orange),
    SPORTIVNAYA("Спортивная", R.color.metro_purple),
    OLD_VILLAGE("Старая Деревня", R.color.metro_purple),
    TECHNOLOGY_INSTITYTE_ONE("Технологический институт 1", R.color.metro_red),
    TECHNOLOGY_INSTITYTE_TWO("Технологический институт 2", R.color.metro_blue),
    UDELNAYA("Удельная", R.color.metro_blue),
    DYBENKO_STREE("Улица Дыбенко", R.color.metro_orange),
    FRUNZENSKAYA("Фрунзенская", R.color.metro_blue),
    BLACK_RIVER("Чёрная речка", R.color.metro_blue),
    CHERNYSHEVSKAYA("Чернышевская", R.color.metro_red),
    CHKALOVSKAYA("Чкаловская", R.color.metro_purple),
    ELECTROSILA("Электросила", R.color.metro_blue);

    companion object {
        fun from(stationName: String): Metro {
            values().forEach {
                if (it.stationName.compareTo(stationName) == 0) {
                    return it
                }
            }
            throw IllegalArgumentException("No enum constant with name $stationName")
        }
    }
}
