package ru.john.quasarutils.services

/*
QuasarService - сервис, который способен выполнять
различную рутинную работу в разные моменты времени.
Каждый сервис отличается по названию. Все сервисы
хранятся в ServiceLoader.kt

Существуют 2 вида сервисов - STATIC и DYNAMIC,
STATIC - запускаются при запуске сервера, могут быть в 1 экземпляре.
DYNAMIC - запускаются не при запуске сервера, может быть несколько.

Примеры:
EventService - статичный сервис
PlayerRunnablesService - динамичный сервис
 */
abstract class QuasarService(
    var name: String
)