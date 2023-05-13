package data.dao.mysql


import data.dao.InternalItemDao
import data.model.TaxLevel
import data.util.DataBaseUtil
import data.util.DbCredentials
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import model.FoodType
import model.InternalItem
import model.IternalID
import java.io.File
import java.sql.ResultSet
import java.util.*

class MySqlInternalItem : InternalItemDao {
    val file = File("src/jvmMain/kotlin/data/util/config.json")
    val dbCredentials = Json.decodeFromString<DbCredentials>(file.readText())

    private val connection = DataBaseUtil.getConnection(dbCredentials) // assuming you have this class

    private fun mapDataToObject(rs: ResultSet): InternalItem {

        var internalID : IternalID;
        when (rs.getString("id")){
            "8942" -> internalID = IternalID.BANANA
            "2746" -> internalID = IternalID.PEAR
            "0233" -> internalID = IternalID.TOMATO
            "2542" -> internalID = IternalID.CHICKEN
            "4281" -> internalID = IternalID.BAGETTE
            else -> internalID = IternalID.OTHER
        }
        var department : FoodType;
        when (rs.getString("department")){
            "FRUIT" -> department = FoodType.FRUIT
            "VEGETABLE" -> department = FoodType.VEGETABLE
            "MEAT" -> department = FoodType.MEAT
            "BREAD" -> department = FoodType.BREAD
            else -> department = FoodType.OTHER
        }

        val name = rs.getString("name")
        val taxLevel = TaxLevel.valueOf(rs.getString("tax_level"))
        val weight = rs.getDouble("weight")
        val price = rs.getDouble("price")
        val internCode = rs.getString("id")
        val itemId = department.value
        val checkNumber = rs.getString("check_number")
        val code = rs.getString("code")
        val id: UUID = UUID.fromString(rs.getString("uuid"))

        return InternalItem(internalID, department, name, taxLevel, weight, price, internCode, itemId, checkNumber, code, id)
    }

    override fun getById(uuid: UUID): InternalItem? {
        val query = "SELECT * FROM internal_item WHERE uuid = ?"
        val preparedStatement = connection?.prepareStatement(query)
        preparedStatement?.setString(1, uuid.toString())

        val resultSet = preparedStatement?.executeQuery()
        return if (resultSet?.next()!!) mapDataToObject(resultSet) else null
    }

    override fun getByName(name: String): InternalItem? {
        val query = "SELECT * FROM internal_item WHERE name = ?"
        val preparedStatement = connection?.prepareStatement(query)
        preparedStatement?.setString(1, name)

        val resultSet = preparedStatement?.executeQuery()
        return if (resultSet!!.next()) mapDataToObject(resultSet) else null
    }

    override fun getByCode(code: String): InternalItem? {
        val query = "SELECT * FROM internal_item WHERE code = ?"
        val preparedStatement = connection?.prepareStatement(query)
        preparedStatement?.setString(1, code)

        val resultSet = preparedStatement?.executeQuery()
        return if (resultSet!!.next()) mapDataToObject(resultSet) else null
    }

    override fun getAll(): List<InternalItem> {
        val query = "SELECT * FROM internal_item"
        val preparedStatement = connection?.prepareStatement(query)
        val resultSet = preparedStatement?.executeQuery()

        val internalItems = mutableListOf<InternalItem>()
        while (resultSet!!.next()) {
            internalItems.add(mapDataToObject(resultSet))
        }
        return internalItems
    }

    override fun insert(obj: InternalItem): Boolean {
        val query = """
            INSERT INTO internal_item (id, department, name, tax_level, price, weight, check_number, code, uuid, created, modified)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())
        """
        val preparedStatement = connection?.prepareStatement(query)
        preparedStatement?.setString(1, obj.internalId.value)
        preparedStatement?.setString(2, obj.department.name)
        preparedStatement?.setString(3, obj.name)
        preparedStatement?.setString(4, obj.taxLevel.name)
        preparedStatement?.setDouble(5, obj.price)
        preparedStatement?.setDouble(6, obj.weight ?: 0.0)
        preparedStatement?.setString(7, obj.checkNumber)
        preparedStatement?.setString(8, obj.code)
        preparedStatement?.setString(9, obj.id.toString())

        return preparedStatement!!.executeUpdate() > 0
    }

    override fun update(obj: InternalItem): Boolean {
        val query = """
            UPDATE internal_item
            SET internal_id = ?, department = ?, name = ?, tax_level = ?, price = ?, intern_code = ?, weight = ?, check_number = ?, code = ?
            WHERE uuid = ?
        """
        val preparedStatement = connection?.prepareStatement(query)
        preparedStatement?.setString(1, obj.internalId.name)
        preparedStatement?.setString(2, obj.department.name)
        preparedStatement?.setString(3, obj.name)
        preparedStatement?.setString(4, obj.taxLevel.name)
        preparedStatement?.setDouble(5, obj.price)
        preparedStatement?.setString(6, obj.internCode)
        preparedStatement?.setDouble(7, obj.weight ?: 0.0)
        preparedStatement?.setString(8, obj.checkNumber)
        preparedStatement?.setString(9, obj.code)
        preparedStatement?.setString(10, obj.id.toString())

        return preparedStatement!!.executeUpdate() > 0
    }

    override fun delete(obj: InternalItem): Boolean {
        val query = "DELETE FROM internal_item WHERE uuid = ?"
        val preparedStatement = connection?.prepareStatement(query)
        preparedStatement?.setString(1, obj.id.toString())

        return preparedStatement!!.executeUpdate() > 0
    }
}

