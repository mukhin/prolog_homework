# Домашнее задание на Prolog
Среда запуска: https://swish.swi-prolog.org/

Составить программу, т.е. модель предметной области – базу знаний, объединив в ней информацию – знания:
«Телефонный справочник»: Фамилия, №тел, Адрес – структура (Город, Улица, №дома, №кв),
«Автомобили»: Фамилия_владельца, Марка, Цвет, Стоимость, и др.,
«Вкладчики банков»: Фамилия, Банк, счет, сумма, др.

Владелец может иметь несколько телефонов, автомобилей, вкладов (факты).
Используя правила, обеспечить возможность поиска:
а) По № телефона найти: Фамилию, Марку автомобиля, Стоимость автомобиля (может быть несколько);
в) Используя сформированное в пункте а) правило, по № телефона найти: только Марку автомобиля (автомобилей может быть несколько);
с) Используя простой, не составной вопрос: по Фамилии (уникальна в городе, но в разных городах есть однофамильцы) и Городу проживания найти:  Улицу проживания, Банки, в которых есть вклады и №телефона.

#### Каталоги:
- src
    prolog_homework.txt
    
Запуск программы:    
а) phone_auto(A,_,C,_,E). -- По № телефона найти: Фамилию, Марку автомобиля, Стоимость автомобиля (может быть несколько);
в) phone_auto(_,_,_,D,_). -- Используя сформированное в пункте а) правило, по № телефона найти: только Марку автомобиля (автомобилей может быть несколько);
с) name_bank(_,B,_,D,E). -- Используя простой, не составной вопрос: по Фамилии (уникальна в городе, но в разных городах есть однофамильцы) и Городу проживания найти:  Улицу проживания, Банки, в которых есть вклады и №телефона.
