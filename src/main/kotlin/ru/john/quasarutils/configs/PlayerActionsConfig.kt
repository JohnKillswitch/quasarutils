package ru.john.quasarutils.configs

import space.arim.dazzleconf.annote.ConfComments
import space.arim.dazzleconf.annote.ConfDefault
import space.arim.dazzleconf.annote.ConfHeader
import space.arim.dazzleconf.annote.ConfKey

@ConfHeader("Configuration file for player tasks.")
interface PlayerActionsConfig {

    @ConfComments("Типы материалов, которые ломаются под тяжелой броней игрока")
    @ConfDefault.DefaultStrings(
        "BLUE_ICE:BLOCK_ICE_DESTROY",
        "FROSTED_ICE:BLOCK_ICE_DESTROY",
        "ICE:BLOCK_ICE_DESTROY",
        "PACKED_ICE:BLOCK_ICE_DESTROY"
    )
    @ConfKey("notAllowedForWeightArmor")
    fun notAllowedForWeightArmor(): List<String>

    @ConfComments("Шанс, с которым блок ломается под игроком")
    @ConfDefault.DefaultDouble(0.4)
    @ConfKey("blockBreakChance")
    fun blockBreakChance(): Double

    @ConfComments("Время (в секундах), за которое уменьшается единица жажды")
    @ConfDefault.DefaultInteger(10)
    @ConfKey("minusThirstPerValue")
    fun minusThirstPerValue(): Int

    @ConfComments("Модификатор уменьшения времени уменьшения жажды в пустыне")
    @ConfDefault.DefaultDouble(2.0)
    @ConfKey("desertThirstModificator")
    fun desertThirstModificator(): Double

    @ConfComments("Модификатор уменьшения времени уменьшения жажды при беге")
    @ConfDefault.DefaultDouble(1.5)
    @ConfKey("runThirstModificator")
    fun runThirstModificator(): Double

    @ConfComments("Модификатор уменьшения времени уменьшения жажды при поедании еды")
    @ConfDefault.DefaultDouble(1.2)
    @ConfKey("eatThirstModificator")
    fun eatThirstModificator(): Double

    @ConfComments("Звук питья воды из источника")
    @ConfDefault.DefaultString("ENTITY_GENERIC_DRINK")
    @ConfKey("handDrinkWaterSound")
    fun handDrinkWaterSound(): String

    @ConfComments("Максимальное количество жажды")
    @ConfDefault.DefaultInteger(20)
    @ConfKey("thirstMaxValue")
    fun thirstMaxValue(): Int

    @ConfComments("Количество восполняемой жажды за источник воды")
    @ConfDefault.DefaultInteger(1)
    @ConfKey("blockThirstAdding")
    fun blockThirstAdding(): Int

    @ConfComments("Количество восполняемой жажды за предметы")
    @ConfDefault.DefaultStrings(
        "POTION:1",
        "MILK_BUCKET:1"
    )
    @ConfKey("itemsThirstAdding")
    fun itemsThirstAdding(): List<String>

    @ConfComments("Количество блоков, которые игрок достает рукой, чтобы выпить из источника")
    @ConfDefault.DefaultInteger(3)
    @ConfKey("blockThirstAddMaxDistance")
    fun blockThirstAddMaxDistance(): Int

    @ConfComments("Задержка между глотками воды из источника(в тиках)")
    @ConfDefault.DefaultInteger(10)
    @ConfKey("handDrinkWaterCooldown")
    fun handDrinkWaterCooldown(): Int

    @ConfComments("Максимальное значение стамины")
    @ConfDefault.DefaultInteger(20)
    @ConfKey("maxStaminaValue")
    fun maxStaminaValue(): Int

    @ConfComments("Насколько уменьшается значение стамины при беге")
    @ConfDefault.DefaultInteger(1)
    @ConfKey("staminaPerRun")
    fun staminaPerRun(): Int
}