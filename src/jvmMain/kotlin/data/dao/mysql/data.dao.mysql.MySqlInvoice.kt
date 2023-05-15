package data.dao.mysql
import data.dao.CashierDao
import data.dao.CompanyDao
import data.dao.InvoiceDao
import data.dao.ItemDao
import data.model.Item
import data.util.DataBaseUtil
import data.util.DbCredentials
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import model.Invoice
import java.io.File
import java.nio.ByteBuffer
import java.sql.ResultSet
import java.sql.SQLException
import java.time.LocalDateTime
import java.util.*

class MySqlInvoice : InvoiceDao {
    private val cashierDao: CashierDao = MySqlCashier()
    private val companyDao: CompanyDao = MySqlCompany()
    // Assuming there's a class named MySqlItem for ItemDao
    private val itemDao: ItemDao = MySqlItem()
    private fun mapDataToInvoice(rs: ResultSet): Invoice {
        val id = UUID.fromString(rs.getString("uuid"))
        val date = rs.getTimestamp("date").toLocalDateTime()
        val code = UUID.fromString(rs.getString("code"))
        val finalPrice = rs.getDouble("final_price")
        val netoPrice = rs.getDouble("neto_price")
        val created = rs.getTimestamp("created").toLocalDateTime()
        val modified = rs.getTimestamp("modified").toLocalDateTime()
        val cashierId = UUID.fromString(rs.getString("cashier_id"))
        val companyId = UUID.fromString(rs.getString("company_id"))

        // Get referenced entities from other tables
        val cashier = cashierDao.getById(cashierId)
        val company = companyDao.getById(companyId)
        val items = getInvoiceItems(id)

        return Invoice(date, items, company, cashier, finalPrice = finalPrice,
            netoPrice = netoPrice, id = id, code = code, created = created, modified = modified)
    }

    fun getInvoiceItems(invoiceId: UUID): LinkedHashMap<Item, Int> {

        TODO("Not yet implemented")
    }


    override fun getById(id: UUID): Invoice? {
        val dbCredentials = getDbCredentials()
        val conn = DataBaseUtil.getConnection(dbCredentials) ?: return null
        conn.use {
            try {
                val select = it.prepareStatement("SELECT * FROM invoice WHERE id = ? LIMIT 1")
                select.setString(1, id.toString())
                val rs = select.executeQuery()
                if (!rs.first()) return null
                return mapDataToInvoice(rs)
            } catch (ex: SQLException) {
                println(ex.message)
            }
        }
        return null
    }

    override fun getAll(): List<Invoice> {
        TODO("Not yet implemented")
    }

    override fun insert(invoice: Invoice): Boolean {
        val file = File("src/jvmMain/kotlin/data/util/config.json")
        val dbCredentials = Json.decodeFromString<DbCredentials>(file.readText())
        val conn = DataBaseUtil.getConnection(dbCredentials) ?: return false
        conn.use {
            try {
                val insert = it.prepareStatement("INSERT INTO invoice (id, date, code, final_price, neto_price, created, modified, uuid) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")
                insert.setBytes(1, invoice.id.asBytes())
                insert.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now()))
                insert.setString(3, invoice.code.toString())
                insert.setDouble(4, invoice.finalPrice)
                insert.setDouble(5, invoice.netoPrice)
                insert.setTimestamp(6, java.sql.Timestamp.valueOf(LocalDateTime.now()))
                insert.setTimestamp(7, java.sql.Timestamp.valueOf(LocalDateTime.now()))
                insert.setString(8, invoice.id.toString())

                //insert.setString(9, invoice.customer?.registrationNumber.toString())
//                insert.setString(10, invoice.cashier?.idNumber.toString())
                val rowsAffected = insert.executeUpdate()
                return rowsAffected > 0
            } catch (ex: SQLException) {
                println(ex.message)
            }
        }
        return false
    }

    override fun update(invoice: Invoice): Boolean {
        TODO("Not yet implemented")
    }
    override fun delete(invoice: Invoice): Boolean {
        TODO("Not yet implemented")
    }


//    private fun getInvoiceItems(invoiceId: UUID): LinkedHashMap<Item, Int> {
//        val file = File("src/jvmMain/kotlin/data/util/config.json")
//        val dbCredentials = Json.decodeFromString<DbCredentials>(file.readText())
//        val connection = DataBaseUtil.getConnection(dbCredentials) ?: return null
//
//        val items = LinkedHashMap<Item, Int>()
//        val statement: PreparedStatement =
//            connection.prepareStatement("SELECT * FROM items WHERE invoice_id = ?")
//
//        statement.setString(1, invoiceId.toString())
//
//        val resultSet = statement.executeQuery()
//
//        while (resultSet.next()) {
//            val itemId = UUID.fromString(resultSet.getString("item_id"))
//            val item = MySqlItem(connection).getById(itemId)
//            if (item != null) {
//                items[item] = resultSet.getInt("quantity")
//            }
//        }
//
//        return items
//    }

    // ... other CRUD operations: getAll(), insert(), update(), delete()

    override fun getByPrice(price: Double): Invoice? {
//        val file = File("src/jvmMain/kotlin/data/util/config.json")
//        val dbCredentials = Json.decodeFromString<DbCredentials>(file.readText())
//        val connection = DataBaseUtil.getConnection(dbCredentials) ?: return null
//
//        val statement: PreparedStatement =
//            connection.prepareStatement("SELECT * FROM invoice WHERE final_price = ?")
//
//        statement.setDouble(1, price)
//
//        val resultSet = statement.executeQuery()
//
//        return if (resultSet.next()) {
//            mapDataToObject(resultSet)
//        } else {
//            null
//        }
        return null
    }
    private fun getDbCredentials(): DbCredentials {
        val file = File("src/jvmMain/kotlin/data/util/config.json")
        return Json.decodeFromString(file.readText())
    }
    fun UUID.asBytes(): ByteArray{
        val byteBuffer = ByteBuffer.wrap(ByteArray(16))
        byteBuffer.putLong(mostSignificantBits)
        byteBuffer.putLong(leastSignificantBits)
        return byteBuffer.array()
    }
    fun ByteArray.asUuid(): UUID{
        val byteBuffer = ByteBuffer.wrap(this)
        val high = byteBuffer.long
        val low = byteBuffer.long
        return UUID(high, low)

    }

}
