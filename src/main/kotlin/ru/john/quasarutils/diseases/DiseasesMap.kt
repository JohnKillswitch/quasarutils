package ru.john.quasarutils.diseases

import ru.john.quasarutils.diseases.disease.BaseDisease

class DiseasesMap(

) {

    companion object {
        val disease: HashMap<String, List<BaseDisease>> = HashMap<String, List<BaseDisease>>()
    }

    fun getDiseases(uuid: String) = disease[uuid]

    fun setDiseases(uuid: String) {
        
    }
}