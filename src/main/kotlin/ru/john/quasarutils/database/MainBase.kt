package ru.john.quasarutils.database

import com.zaxxer.hikari.HikariDataSource
import org.bukkit.Bukkit
import ru.john.quasarutils.util.PlayerVirus
import java.sql.*
import java.util.concurrent.CompletableFuture

class MainBase(
    private val hikariDataSource: HikariDataSource
) {
    fun createCharacterTable() {
        val connection = getConnection()

        val sql = """
            CREATE TABLE IF NOT EXISTS Player (
                UUID TEXT,
                Name TEXT,
                Surname TEXT,
                PRIMARY KEY (UUID)
            );
        """

        val statement = connection.createStatement()
        statement.execute(sql)

        statement.close()
        connection.close()
    }
    fun createVirusTable() {
        val connection = getConnection()

        val sql = """
            CREATE TABLE IF NOT EXISTS Infection (
                UUID TEXT,
                InfectionTime LONG,
                PRIMARY KEY (UUID)
            );
        """

        val statement = connection.createStatement()
        statement.execute(sql)

        statement.close()
        connection.close()
    }

    fun insertInfectionData(uuid: String, infectionTime: Long) {
        val connection = getConnection()

        val sql = """
        INSERT INTO Infection (UUID, InfectionTime)
        VALUES (?, ?)
        ON CONFLICT (UUID) DO UPDATE SET InfectionTime = ?;
    """

        val preparedStatement: PreparedStatement = connection.prepareStatement(sql)
        preparedStatement.setString(1, uuid)
        preparedStatement.setLong(2, infectionTime)
        preparedStatement.setLong(3, infectionTime)

        preparedStatement.executeUpdate()

        preparedStatement.close()
        connection.close()
    }

    fun deleteInfectionDataByUUID(uuid: String) {
        val connection = getConnection()

        val sql = "DELETE FROM Infection WHERE UUID = ?;"

        val preparedStatement: PreparedStatement = connection.prepareStatement(sql)
        preparedStatement.setString(1, uuid)

        preparedStatement.executeUpdate()

        preparedStatement.close()
        connection.close()
    }

    fun getPlayerVirus(uuid: String): PlayerVirus? {
        val connection = getConnection()

        try {

            val sql = "SELECT * FROM Infection WHERE UUID = ?"
            val preparedStatement = connection.prepareStatement(sql)
            preparedStatement.setString(1, uuid)

            val resultSet = preparedStatement.executeQuery()

            if (resultSet.next()) {
                val ownerUUID = resultSet.getString("UUID")
                val infectionTime = resultSet.getLong("InfectionTime")

                val player = Bukkit.getOfflinePlayer(ownerUUID)


                return PlayerVirus(
                    player,
                    infectionTime
                )
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            try {
                connection.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

        return null
    }

    private fun deletePlayerByUUID(uuid: String) {
        val connection = getConnection()

        val sql = "DELETE FROM Player WHERE UUID = ?"

        val preparedStatement = connection.prepareStatement(sql)
        preparedStatement.setString(1, uuid)
        preparedStatement.executeUpdate()

        preparedStatement.close()
        connection.close()
    }

    fun deletePlayerData(uuid: String) {
        val thread = Thread {
            deletePlayerByUUID(uuid)
        }

        thread.start()
    }

    private fun insertPlayer(uuid: String, name: String, surname: String) {
        val connection = getConnection()
        val sql = "INSERT INTO Player (UUID, Name, Surname) VALUES (?, ?, ?)"

        try {
            val preparedStatement: PreparedStatement = connection.prepareStatement(sql)
            preparedStatement.setString(1, uuid)
            preparedStatement.setString(2, name)
            preparedStatement.setString(3, surname)

            preparedStatement.executeUpdate()
            preparedStatement.close()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection.close()
        }
    }

    fun addPlayer (uuid: String, name: String, surname: String) {
        val thread = Thread {
            insertPlayer(uuid, name, surname)
        }

        thread.start()
    }

    private fun getPlayerInfo(uuid: String): Pair<String, String> {
        val connection = getConnection()
        val sql = "SELECT Name, Surname FROM Player WHERE UUID = ?"

        try {
            val preparedStatement: PreparedStatement = connection.prepareStatement(sql)
            preparedStatement.setString(1, uuid)

            val resultSet: ResultSet = preparedStatement.executeQuery()
            if (resultSet.next()) {
                val name = resultSet.getString("Name")
                val surname = resultSet.getString("Surname")
                preparedStatement.close()
                return Pair(name, surname)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection.close()
        }

        return Pair("", "")
    }

    fun getNameAndSurname(uuid: String): Pair<String, String> {

        val future = CompletableFuture.supplyAsync {
            getPlayerInfo(uuid)
        }
        val result = future.join()

        val (name, surname) = result

        return Pair(name, surname)
    }


    private fun getConnection(): Connection {
        return hikariDataSource.getConnection()
    }
}