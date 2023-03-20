
import data.model.Item
import data.model.TaxLevel
import data.model.generateCheckNumber
import data.model.generateCode
import java.util.*

enum class IternalID(val value: String, val pricePerKilo: Double) {
    BANANA("8942", 7.55),
    PEAR("2746", 8.99),
    TOMATO("0233", 3.99),
    CHICKEN("2542",9.99),
    BAGETTE("4281", 1.99)
}
enum class FoodType(val value: String) {//department
    FRUIT("211"),
    VEGETABLE("233"),
    MEAT("242"),
    BREAD("276")
}

class InternalItem(
    val internalId: IternalID,
    val department: FoodType,
    name: String,
    taxLevel: TaxLevel,
    weight: Double? = null,
    price : Double = internalId.pricePerKilo * weight!!,
    internCode: String = internalId.value,
    itemId: String = department.value,
    checkNumber: String = generateCheckNumber(internCode, itemId, price),
    code: String = generateCode(internCode, itemId, price) + checkNumber,
    id:UUID= UUID.randomUUID(),
) : Item(name, taxLevel, price, internCode, itemId, weight, checkNumber, code, id) {

}