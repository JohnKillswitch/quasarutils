package ru.john.quasarutils.configs

import space.arim.dazzleconf.annote.*
import space.arim.dazzleconf.annote.ConfDefault.DefaultString


@ConfHeader("Main configuration file.")
interface Config {
        @ConfComments("Блок, на которых игрок будет ускоряться. КРОМЕ DIRT_PATH")
        @ConfDefault.DefaultStrings(
            "STONE",
            "DIRT_PATH",
            "DIRT"
        )
        @ConfKey("types")
        fun blockTypes(): List<String>

        @ConfComments("Длительность эффекта в секундах")
        @ConfDefault.DefaultDouble(0.5)
        @ConfKey("duration")
        fun duration(): Double



}