package data.model

import Searchable
import java.time.LocalDateTime
import java.util.UUID
import kotlin.random.Random

enum class TaxLevel {
    A, B, C
}

fun generateRandomCode(): String {
    val random = Random.Default
    val builder = StringBuilder(13)
    repeat(13) {
        builder.append(random.nextInt(10))
    }
    return builder.toString()
}

class Item(

    var name: String,
    var price: Double,
    var taxLevel: TaxLevel,
    var code: String = generateRandomCode(),
    val id: UUID = UUID.randomUUID()

) : Searchable {
    override fun search(word: String): Boolean {
        return name.contains(word, true) || code.contains(word, true) || id.toString()
            .contains(word, true) || price.toString().contains(word, true) || taxLevel.toString().contains(word, true)
    }

    var created: LocalDateTime = LocalDateTime.now()
    var modified: LocalDateTime = created
    fun update(name: String, price: Double, taxLevel: TaxLevel) {
        this.name = name
        this.price = price
        this.taxLevel = taxLevel
        modified = LocalDateTime.now()
    }

}

