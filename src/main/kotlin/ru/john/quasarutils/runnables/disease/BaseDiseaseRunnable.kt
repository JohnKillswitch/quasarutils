package ru.john.quasarutils.runnables.disease

import org.bukkit.entity.Player
import ru.john.quasarutils.diseases.types.BaseDisease
import ru.john.quasarutils.runnables.DiseaseRunnable

class BaseDiseaseRunnable(player: Player, disease: BaseDisease) : DiseaseRunnable(player, disease, 0L, 20L) {
    override fun run() {

    }

}