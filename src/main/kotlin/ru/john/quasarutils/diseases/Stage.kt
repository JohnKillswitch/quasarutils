package ru.john.quasarutils.diseases

abstract class Stage(
    private val onlyOnline: Boolean,
    private val seconds: Int,
    private val spreadTypes: ArrayList<SpreadType>
) {

}