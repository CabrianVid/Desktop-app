
package data.dao.mysql
import data.dao.ItemDao
import data.model.Item
import data.model.TaxLevel
import data.util.DataBaseUtil
import data.util.DbCredentials
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import java.sql.ResultSet
import java.sql.SQLException
import java.time.LocalDate
import java.util.*

data class MyObject(val id: Int, val name: String, val age: Int)


fun mapDataToObject(rs: ResultSet): Item {
    var taxLevel: TaxLevel;
    when (rs.getString("tax_level")) {
        "A" -> taxLevel = TaxLevel.A
        "B" -> taxLevel = TaxLevel.B
        "C" -> taxLevel = TaxLevel.C
        else -> taxLevel = TaxLevel.A
    }

    val name = rs.getString("name")
    val price = rs.getDouble("price")
    val internCode = ""
    val itemId = ""
    val weight = null
    val checkNumber = rs.getString("check_number")
    val code = rs.getString("code")
    val uuid: UUID = UUID.fromString(rs.getString("uuid"))


    return Item(name, taxLevel, price, internCode, itemId, weight, checkNumber, code, uuid)
}

class MySqlItem : ItemDao {//ostal sem pri tem da sem tu odstano to da je abstrakten razred

    override fun getById(uuid: UUID): Item? {
        val file = File("src/jvmMain/kotlin/data/util/config.json")
        val dbCredentials = Json.decodeFromString<DbCredentials>(file.readText())
        val conn = DataBaseUtil.getConnection(dbCredentials) ?: return null
        conn.use {
            try{
                val select = it.prepareStatement("SELECT * FROM item WHERE uuid = ? LIMIT 1", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)
                select.setString(1, uuid.toString())
                val rs = select.executeQuery()
                if(!rs.first()) return null
                return mapDataToObject(rs)
            } catch (ex: SQLException){
                println(ex.message)
            }
        }
        return null
    }
    override fun getByBarcode(barcode: String): Item? {
        val file = File("src/jvmMain/kotlin/data/util/config.json")
        val dbCredentials = Json.decodeFromString<DbCredentials>(file.readText())
        val conn = DataBaseUtil.getConnection(dbCredentials) ?: return null
        conn.use {
            try{
                val select = it.prepareStatement("SELECT * FROM item WHERE code = ? LIMIT 1", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)
                select.setString(1, barcode)
                val rs = select.executeQuery()
                if(!rs.first()) return null
                return mapDataToObject(rs)
            } catch (ex: SQLException){
                println(ex.message)
            }
        }
        return null
    }

    override fun getByName(name: String): Item? {
        val file = File("src/jvmMain/kotlin/data/util/config.json")
        val dbCredentials = Json.decodeFromString<DbCredentials>(file.readText())
        val conn = DataBaseUtil.getConnection(dbCredentials) ?: return null
        conn.use {
            try {
                val select = it.prepareStatement("SELECT * FROM item WHERE name = ? LIMIT 1", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)
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

    override fun getAll(): List<Item> {
        val items = mutableListOf<Item>()
        val file = File("src/jvmMain/kotlin/data/util/config.json")
        val dbCredentials = Json.decodeFromString<DbCredentials>(file.readText())
        val conn = DataBaseUtil.getConnection(dbCredentials) ?: return items
        conn.use {
            try {
                val select = it.prepareStatement("SELECT * FROM  item")
                val rs = select.executeQuery()
                while (rs.next()) {
                    val item = mapDataToObject(rs)
                    items.add(item)
                }
            } catch (ex: SQLException) {
                println(ex.message)
            }
        }
        return items
    }

    override fun insert(obj: Item): Boolean {
        val file = File("src/jvmMain/kotlin/data/util/config.json")
        val dbCredentials = Json.decodeFromString<DbCredentials>(file.readText())
        val conn = DataBaseUtil.getConnection(dbCredentials) ?: return false
        conn.use {
            try {
                val insert = it.prepareStatement("INSERT INTO item (id, name, tax_level, price, check_number, code, uuid, created, modified) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")
                insert.setString(1, obj.itemId)
                insert.setString(2, obj.name)
                insert.setString(3, obj.taxLevel.toString())
                insert.setDouble(4, obj.price)
                insert.setString(5, obj.checkNumber)
                insert.setString(6, obj.code)
                insert.setString(7, obj.id.toString())
                insert.setString(8,  LocalDate.now().toString())
                insert.setString(9,  LocalDate.now().toString())

                val rowsAffected = insert.executeUpdate()
                return rowsAffected > 0
            } catch (ex: SQLException) {
                println(ex.message)
            }
        }
        return false
    }




    override fun update(item: Item): Boolean {
        val file = File("src/jvmMain/kotlin/data/util/config.json")
        val dbCredentials = Json.decodeFromString<DbCredentials>(file.readText())
        val conn = DataBaseUtil.getConnection(dbCredentials) ?: return false
        conn.use {
            try {
                val update = it.prepareStatement("UPDATE item SET id = ?, name = ?, tax_level = ?, price = ?, check_number = ?, code = ?, uuid = ?, created = ?, modified = ? WHERE name = ?")
                update.setString(1, item.itemId)
                update.setString(2, item.name)
                update.setString(3, item.taxLevel.toString())
                update.setDouble(4, item.price)
                update.setString(5, item.checkNumber)
                update.setString(6, item.code)
                update.setString(7, item.id.toString())
                update.setString(8,  LocalDate.now().toString())
                update.setString(9,  LocalDate.now().toString())
                update.setString(10, item.name)
                val rowsAffected = update.executeUpdate()
                return rowsAffected > 0
            } catch (ex: SQLException) {
                println(ex.message)
            }
        }
        return false
    }


    override fun delete(obj: Item): Boolean {
        val file = File("src/jvmMain/kotlin/data/util/config.json")
        val dbCredentials = Json.decodeFromString<DbCredentials>(file.readText())
        val conn = DataBaseUtil.getConnection(dbCredentials) ?: return false
        conn.use {
            try {
                val delete = it.prepareStatement("DELETE FROM item WHERE uuid = ?")
                delete.setString(1, obj.id.toString())
                val rowsAffected = delete.executeUpdate()
                return rowsAffected > 0
            } catch (ex: SQLException) {
                println(ex.message)
            }
        }
        return false
    }
}


