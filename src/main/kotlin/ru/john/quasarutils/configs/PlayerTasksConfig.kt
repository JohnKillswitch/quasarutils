package ru.john.quasarutils.configs

import space.arim.dazzleconf.annote.ConfComments
import space.arim.dazzleconf.annote.ConfDefault
import space.arim.dazzleconf.annote.ConfHeader
import space.arim.dazzleconf.annote.ConfKey

@ConfHeader("Configuration file for player tasks.")
interface PlayerTasksConfig {

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

}