package data.util

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileNotFoundException
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

@Serializable
data class DbCredentials(val url: String, val username: String, val password: String)
object DataBaseUtil{
    @JvmStatic
    fun getConnection(dbCredentials: DbCredentials): Connection? {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance()
            return DriverManager.getConnection(dbCredentials.url, dbCredentials.username, dbCredentials.password)
        } catch (ex: SQLException){
            println("${ex.javaClass.simpleName}: ${ex.message}")

        } catch (ex: Exception){
            println("${ex.javaClass.simpleName}: ${ex.message}")
        }
        return null
    }
    @JvmStatic
    fun testConnection(){
        val query = "SELECT * FROM item" //zamenjal city z item
        val file = File("src/jvmMain/kotlin/data/util/config.json")
        val dbCredentials = Json.decodeFromString<DbCredentials>(file.readText())

        try {

            val conn = getConnection(dbCredentials) ?: return
            conn.use {
                try {
                    val select = it.prepareStatement(query)
                    val rs = select.executeQuery()
                    while (rs.next()) {

                        val attribute = rs.getString("name")//zamenjal city z name
                        println("$attribute")
                    }
                } catch (ex: SQLException) {
                    println(ex.message)
                }
            }

        }catch (ex: FileNotFoundException){
            println(ex.message)
        }

    }

}
