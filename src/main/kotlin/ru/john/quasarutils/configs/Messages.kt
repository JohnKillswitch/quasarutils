package ru.john.quasarutils.configs

import space.arim.dazzleconf.annote.*

@ConfHeader("Plugin messages.")
interface Messages {
    @ConfDefault.DefaultString("Неправильный ввод команды. /char create [имя] [фамилия]")
    @ConfKey("wrong-usage")
    fun wrongUsage(): String
    @ConfDefault.DefaultString("Команда доступна только игроку!")
    @ConfKey("players-only")
    fun playersOnly(): String
    @ConfDefault.DefaultString("У тебя уже есть персонаж!")
    @ConfKey("already-created")
    fun alreadyCreated(): String
    @ConfDefault.DefaultString("Максимальная длина имени 10 букв!")
    @ConfKey("max-lenght-name")
    fun maxLenghtName(): String
    @ConfDefault.DefaultString("Имя должно состоять только из букв кириллицы!")
    @ConfKey("cyrillic-only-name")
    fun cyrrilicOnlyName(): String
    @ConfDefault.DefaultString("Максимальная длина фамилии 10 букв!")
    @ConfKey("max-lenght-surname")
    fun maxLenghtSurname(): String
    @ConfDefault.DefaultString("Фамилия должно состоять только из букв кириллицы!")
    @ConfKey("cyrillic-only-surname")
    fun cyrrilicOnlySurname(): String
    @ConfDefault.DefaultString("Нет прав на эту команду")
    @ConfKey("no-perm")
    fun noPerm(): String
}