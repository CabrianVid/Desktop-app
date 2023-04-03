package data.model


import Searchable
import data.util.BarcodeUtil
import java.time.LocalDateTime
import java.util.*

enum class TaxLevel {
    A, B, C
}
fun generateCode(internCode: String, itemId: String, price: Double): String{

    var temp = price.toString()
    var priceString = temp.replace(".", "")

    if (priceString.length < 5) {
        priceString = priceString.padStart(5, '0')
    }

    if(priceString.length>5){
        priceString = String.format("%.5f", price)
        priceString = priceString.replace(".", "")
    }




    var barcodeNum = internCode + itemId + priceString
    return barcodeNum
}

fun generateCheckNumber(internCode: String, itemId: String, price: Double): String {
    var temp = price.toString()
    var priceString = temp.replace(".", "")

    if (priceString.length < 5) {
        priceString = priceString.padStart(5, '0')
    }
    if(priceString.length>5) {
        priceString = String.format("%.5f", price)
        priceString = priceString.replace(".", "")

    }
    var sum = 0
    var barcodeNum = internCode + itemId + priceString

    for (i in 0..11) {
        if (i % 2 == 0) {
            sum += barcodeNum[i].toString().toInt() * 1
        } else {
            sum += barcodeNum[i].toString().toInt() * 3
        }
    }
    var lastDigit = 10 - (sum % 10)
    if(lastDigit == 10) {
        lastDigit = 0
    }

    barcodeNum += lastDigit.toString()

    return lastDigit.toString()
}


open class Item(

    var name: String,
    var taxLevel: TaxLevel,
    var price: Double,
    var internCode: String,
    var itemId: String,
    val weight: Double? = null,
    val checkNumber: String = generateCheckNumber(internCode, itemId, price),
    var code: String = generateCode(internCode, itemId, price) + checkNumber,
    val id: UUID = UUID.randomUUID()

) : Searchable {
    override fun search(word: String): Boolean {
        return name.contains(word, true) || code.contains(word, true) || id.toString()
            .contains(word, true) || price.toString().contains(word, true) || taxLevel.toString().contains(word, true)
    }
    init {
        if (!BarcodeUtil.isValidBarcode(code)) {
            throw IllegalArgumentException("Invalid barcode: $code")
        }
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
