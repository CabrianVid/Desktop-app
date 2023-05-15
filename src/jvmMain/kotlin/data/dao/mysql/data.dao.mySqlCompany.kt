package data.dao.mysql


import data.dao.CompanyDao
import data.util.DataBaseUtil
import data.util.DbCredentials
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import model.Company
import java.io.File
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*

class MySqlCompany : CompanyDao {
    private fun mapDataToCompany(rs: ResultSet): Company {
        val name = rs.getString("name")
        val address = rs.getString("address")
        val taxNumber = rs.getString("tax_number")
        val email = rs.getString("email")
        val phone = rs.getString("phone")
        val registrationNumber = rs.getString("id")
        val website = rs.getString("website")
        val taxpayer = rs.getBoolean("taxpayer")

        return Company(name, address, taxNumber, email, phone, registrationNumber, website, taxpayer)
    }
    override fun getById(id: UUID): Company? {
        return getCompany("SELECT * FROM company WHERE id = ? LIMIT 1", id.toString())

    }

    override fun getByName(name: String): Company? {
        return getCompany("SELECT * FROM company WHERE name = ? LIMIT 1", name)
    }

    override fun getByTaxNumber(taxNumber: String): Company? {
        return getCompany("SELECT * FROM company WHERE tax_number = ? LIMIT 1", taxNumber)
    }

    private fun getCompany(sql: String, param: String): Company? {
        val file = File("src/jvmMain/kotlin/data/util/config.json")
        val dbCredentials = Json.decodeFromString<DbCredentials>(file.readText())
        val conn = DataBaseUtil.getConnection(dbCredentials) ?: return null
        conn.use {
            try {
                val select = it.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)
                select.setString(1, param)
                val rs = select.executeQuery()
                if (!rs.first()) return null
                return mapDataToCompany(rs)
            } catch (ex: SQLException) {
                println(ex.message)
            }
        }
        return null
    }

    override fun getAll(): List<Company> {
        val companies = mutableListOf<Company>()
        val file = File("src/jvmMain/kotlin/data/util/config.json")
        val dbCredentials = Json.decodeFromString<DbCredentials>(file.readText())
        val conn = DataBaseUtil.getConnection(dbCredentials) ?: return companies
        conn.use {
            try {
                val select = it.prepareStatement("SELECT * FROM company", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)
                val rs = select.executeQuery()
                while (rs.next()) {
                    val company = mapDataToCompany(rs)
                    companies.add(company)
                }
            } catch (ex: SQLException) {
                println(ex.message)
            }
        }
        return companies
    }

    override fun insert(obj: Company): Boolean {
        val file = File("src/jvmMain/kotlin/data/util/config.json")
        val dbCredentials = Json.decodeFromString<DbCredentials>(file.readText())
        val conn = DataBaseUtil.getConnection(dbCredentials) ?: return false
        conn.use {
            try {
                val insert = it.prepareStatement(
                    "INSERT INTO company (id, name, address, tax_number, email, phone, website, taxpayer, created, modified) VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())"
                )
                insert.setString(1,obj.registrationNumber)
                insert.setString(2, obj.name)
                insert.setString(3, obj.address)
                insert.setString(4, obj.taxNumber)
                insert.setString(5, obj.email)
                insert.setString(6, obj.phone)
                insert.setString(7, obj.website)
                insert.setBoolean(8, obj.taxpayer)
                val rowsAffected = insert.executeUpdate()
                return rowsAffected > 0
            } catch (ex: SQLException) {
                println(ex.message)
            }
        }
        return false
    }

    override fun update(obj: Company): Boolean {
        val file = File("src/jvmMain/kotlin/data/util/config.json")
        val dbCredentials = Json.decodeFromString<DbCredentials>(file.readText())
        val conn = DataBaseUtil.getConnection(dbCredentials) ?: return false
        conn.use {
            try {
                val update = it.prepareStatement(
                    "UPDATE company SET id = ?, name = ?, address = ?, tax_number = ?, email = ?, phone = ?, website = ?, taxpayer = ?, modified = NOW() WHERE id = ?"
                )
                update.setString(1, obj.registrationNumber)
                update.setString(2, obj.name)
                update.setString(3, obj.address)
                update.setString(4, obj.taxNumber)
                update.setString(5, obj.email)
                update.setString(6, obj.phone)
                update.setString(7, obj.website)
                update.setBoolean(8, obj.taxpayer)
                update.setString(9, obj.registrationNumber)
                val rowsAffected = update.executeUpdate()
                return rowsAffected > 0
            } catch (ex: SQLException) {
                println(ex.message)
            }
        }
        return false
    }

    override fun delete(obj: Company): Boolean {
        val file = File("src/jvmMain/kotlin/data/util/config.json")
        val dbCredentials = Json.decodeFromString<DbCredentials>(file.readText())
        val conn = DataBaseUtil.getConnection(dbCredentials) ?: return false
        conn.use {
            try {
                val delete = it.prepareStatement("DELETE FROM company WHERE id = ?")
                delete.setString(1, obj.registrationNumber)
                val rowsAffected = delete.executeUpdate()
                return rowsAffected > 0
            } catch (ex: SQLException) {
                println(ex.message)
            }
        }
        return false
    }
}

