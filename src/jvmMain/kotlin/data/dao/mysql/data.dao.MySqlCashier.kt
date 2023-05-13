package data.dao.mysql

import data.dao.CashierDao
import data.util.DataBaseUtil
import data.util.DbCredentials
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import model.Cashier
import java.io.File
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*

@Serializable
data class DbCredentials(val url: String, val username: String, val password: String)

class MySqlCashier : CashierDao {
    override fun getById(id: UUID): Cashier? {
        TODO("Not yet implemented")
    }

    private fun mapDataToObject(rs: ResultSet): Cashier {
        val name = rs.getString("name")
        val surname = rs.getString("surname")
        val idNumber = rs.getString("id")

        return Cashier(name, surname, idNumber)
    }

    override fun getByIdNumber(id: String): Cashier? {
        val file = File("src/jvmMain/kotlin/data/util/config.json")
        val dbCredentials = Json.decodeFromString<DbCredentials>(file.readText())
        val conn = DataBaseUtil.getConnection(dbCredentials) ?: return null
        conn.use {
            try {
                val select = it.prepareStatement("SELECT * FROM cashier WHERE id = ? LIMIT 1")
                select.setString(1, id)
                val rs = select.executeQuery()
                if (!rs.first()) return null
                return mapDataToObject(rs)
            } catch (ex: SQLException) {
                println(ex.message)
            }
        }
        return null
    }

    override fun getByName(name: String): Cashier? {
        val file = File("src/jvmMain/kotlin/data/util/config.json")
        val dbCredentials = Json.decodeFromString<DbCredentials>(file.readText())
        val conn = DataBaseUtil.getConnection(dbCredentials) ?: return null
        conn.use {
            try {
                val select = it.prepareStatement("SELECT * FROM cashier WHERE name = ? LIMIT 1", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)
                select.setString(1, name)
                val rs = select.executeQuery()
                if (!rs.first()) return null
                return mapDataToObject(rs)
            } catch (ex: SQLException) {
                println(ex.message)
            }
        }
        return null
    }

    override fun getAll(): List<Cashier> {
        val cashiers = mutableListOf<Cashier>()
        val file = File("src/jvmMain/kotlin/data/util/config.json")
        val dbCredentials = Json.decodeFromString<DbCredentials>(file.readText())
        val conn = DataBaseUtil.getConnection(dbCredentials) ?: return cashiers
        conn.use {
            try {
                val select = it.prepareStatement("SELECT * FROM  cashier")
                val rs = select.executeQuery()
                while (rs.next()) {
                    val cashier = mapDataToObject(rs)
                    cashiers.add(cashier)
                }
            } catch (ex: SQLException) {
                println(ex.message)
            }
        }
        return cashiers
    }

    override fun insert(obj: Cashier): Boolean {
        val file = File("src/jvmMain/kotlin/data/util/config.json")
        val dbCredentials = Json.decodeFromString<DbCredentials>(file.readText())
        val conn = DataBaseUtil.getConnection(dbCredentials) ?: return false
        conn.use {
            try {
                val insert = it.prepareStatement("INSERT INTO cashier (id, name, surname) VALUES (?, ?, ?)")
                insert.setString(1, obj.idNumber)
                insert.setString(2, obj.name)
                insert.setString(3, obj.surname)

                val rowsAffected = insert.executeUpdate()
                return rowsAffected > 0
            } catch (ex: SQLException) {
                println(ex.message)
            }
        }
        return false
    }

    override fun update(obj: Cashier): Boolean {
        val file = File("src/jvmMain/kotlin/data/util/config.json")
        val dbCredentials = Json.decodeFromString<DbCredentials>(file.readText())
        val conn = DataBaseUtil.getConnection(dbCredentials) ?: return false
        conn.use {
            try {
                val update = it.prepareStatement("UPDATE cashier SET id = ?, name = ?, surname = ? WHERE id = ?")
                update.setString(1, obj.idNumber)
                update.setString(2, obj.name)
                update.setString(3, obj.surname)
                update.setString(4, obj.idNumber)
                val rowsAffected = update.executeUpdate()
                return rowsAffected > 0
            } catch (ex: SQLException) {
                println(ex.message)
            }
        }
        return false
    }

    override fun delete(obj: Cashier): Boolean {
        val file = File("src/jvmMain/kotlin/data/util/config.json")
        val dbCredentials = Json.decodeFromString<DbCredentials>(file.readText())
        val conn = DataBaseUtil.getConnection(dbCredentials) ?: return false
        conn.use {
            try {
                val delete = it.prepareStatement("DELETE FROM cashier WHERE name = ? AND surname = ?")
                delete.setString(1, obj.name)
                delete.setString(2, obj.surname)
                val rowsAffected = delete.executeUpdate()
                return rowsAffected > 0
            } catch (ex: SQLException) {
                println(ex.message)
            }
        }
        return false
    }
}
