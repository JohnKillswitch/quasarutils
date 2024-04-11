package ru.john.quasarutils.configs

import space.arim.dazzleconf.annote.ConfComments
import space.arim.dazzleconf.annote.ConfDefault
import space.arim.dazzleconf.annote.ConfHeader
import space.arim.dazzleconf.annote.ConfKey

@ConfHeader("Configuration file for player tasks.")
interface PlayerTasksConfig {

    @ConfComments("Типы материалов, которые ломаются под тяжелой броней игрока")
    @ConfDefault.DefaultStrings(
        "BLUE_ICE",
        "FROSTED_ICE",
        "ICE"
    )
    @ConfKey("notAllowedForWeightArmor")
    fun notAllowedForWeightArmor(): List<String>
}