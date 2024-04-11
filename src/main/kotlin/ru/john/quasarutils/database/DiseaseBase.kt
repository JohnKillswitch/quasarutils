package ru.john.quasarutils.database

import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection

class DiseaseBase(
    private val hikariDataSource: HikariDataSource
) {

    fun createTable() {
        val connection = getConnection()

        val sql = """
            CREATE TABLE IF NOT EXISTS PlayerDiseases (
                uuid TEXT,
                disease_name TEXT,
                infection_time INTEGER
                PRIMARY KEY (UUID)
            );
        """

        val statement = connection.createStatement()
        statement.execute(sql)

        statement.close()
        connection.close()
    }

    fun getPlayerDiseases(uuid: String): List<String> {
        val connection = getConnection()
        val diseases = mutableListOf<String>()

        val sql = """
        SELECT disease_name
        FROM PlayerDiseases
        WHERE uuid = ?
        """

        val preparedStatement = connection.prepareStatement(sql)
        preparedStatement.setString(1, uuid)
        val resultSet = preparedStatement.executeQuery()

        while (resultSet.next()) {
            val diseaseName = resultSet.getString("disease_name")
            diseases.add(diseaseName)
        }

        resultSet.close()
        preparedStatement.close()
        connection.close()

        return diseases
    }

    fun addPlayerDisease(uuid: String, diseaseName: String) {
        val connection = getConnection()

        val sql = """
        INSERT INTO PlayerDiseases (uuid, disease_name, infection_time)
        VALUES (?, ?, CURRENT_TIMESTAMP)
        """

        val preparedStatement = connection.prepareStatement(sql)
        preparedStatement.setString(1, uuid)
        preparedStatement.setString(2, diseaseName)
        preparedStatement.executeUpdate()

        preparedStatement.close()
        connection.close()
    }

    private fun getConnection(): Connection {
        return hikariDataSource.getConnection()
    }

}