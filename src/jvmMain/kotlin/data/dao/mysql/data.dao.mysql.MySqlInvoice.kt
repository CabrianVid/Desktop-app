package data.dao.mysql
import data.dao.InvoiceDao
import data.util.DataBaseUtil
import data.util.DbCredentials
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import model.*
import java.io.File
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.time.LocalDateTime
import java.util.*

class MySqlInvoice : InvoiceDao {

    private fun mapDataToObject(rs: ResultSet): Invoice? {

        val file = File("src/jvmMain/kotlin/data/util/config.json")
        val dbCredentials = Json.decodeFromString<DbCredentials>(file.readText())
        val connection = DataBaseUtil.getConnection(dbCredentials) ?: return null

        val date = rs.getTimestamp("date").toLocalDateTime()
        val code = UUID.fromString(rs.getString("code"))
        val finalPrice = rs.getDouble("final_price")
        val netoPrice = rs.getDouble("neto_price")
        val created = rs.getTimestamp("created").toLocalDateTime()
        val modified = rs.getTimestamp("modified").toLocalDateTime()
        val cashierId = UUID.fromString(rs.getString("cashier_id"))
        val companyId = UUID.fromString(rs.getString("company_id"))

        val cashier = MySqlCashier.getById(cashierId)
        val company = MySqlCompany(connection).getById(companyId)
        val items = getInvoiceItems(id)

        return Invoice(date, items, company, cashier, finalPrice, netoPrice, id, code, created, modified)
    }

    override fun getById(id: UUID): Invoice? {
        val file = File("src/jvmMain/kotlin/data/util/config.json")
        val dbCredentials = Json.decodeFromString<DbCredentials>(file.readText())
        val connection = DataBaseUtil.getConnection(dbCredentials) ?: return null

        val statement: PreparedStatement =
            connection.prepareStatement("SELECT * FROM invoice WHERE id = ?")

        statement.setString(1, id.toString())

        val resultSet = statement.executeQuery()

        return if (resultSet.next()) {
            mapDataToObject(resultSet)
        } else {
            return null
        }
    }

    override fun getAll(): List<Invoice> {
        TODO("Not yet implemented")
    }
    override fun insert(obj: Invoice): Boolean {
        TODO("Not yet implemented")
    }

    override fun update(obj: Invoice): Boolean {
        TODO("Not yet implemented")
    }

    override fun delete(obj: Invoice): Boolean {
        TODO("Not yet implemented")
    }


    private fun getInvoiceItems(invoiceId: UUID): LinkedHashMap<Item, Int> {
        val file = File("src/jvmMain/kotlin/data/util/config.json")
        val dbCredentials = Json.decodeFromString<DbCredentials>(file.readText())
        val connection = DataBaseUtil.getConnection(dbCredentials) ?: return null

        val items = LinkedHashMap<Item, Int>()
        val statement: PreparedStatement =
            connection.prepareStatement("SELECT * FROM items WHERE invoice_id = ?")

        statement.setString(1, invoiceId.toString())

        val resultSet = statement.executeQuery()

        while (resultSet.next()) {
            val itemId = UUID.fromString(resultSet.getString("item_id"))
            val item = MySqlItem(connection).getById(itemId)
            if (item != null) {
                items[item] = resultSet.getInt("quantity")
            }
        }

        return items
    }

    // ... other CRUD operations: getAll(), insert(), update(), delete()

    override fun getByPrice(price: Double): Invoice? {
        val file = File("src/jvmMain/kotlin/data/util/config.json")
        val dbCredentials = Json.decodeFromString<DbCredentials>(file.readText())
        val connection = DataBaseUtil.getConnection(dbCredentials) ?: return null

        val statement: PreparedStatement =
            connection.prepareStatement("SELECT * FROM invoice WHERE final_price = ?")

        statement.setDouble(1, price)

        val resultSet = statement.executeQuery()

        return if (resultSet.next()) {
            mapDataToObject(resultSet)
        } else {
            null
        }
    }
}
