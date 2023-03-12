package data.model
import java.time.LocalDateTime
import java.util.UUID
import java.util.LinkedHashMap


class Invoice(
    private var date: LocalDateTime,
    private var items: LinkedHashMap<Item, Int>,
    private var id: UUID = UUID.randomUUID(),
    private var code: UUID = UUID.randomUUID(),
    private var finalPrice: Double = items.getTotalPrice(),

    val created: LocalDateTime = LocalDateTime.now(),
    var modified: LocalDateTime = LocalDateTime.now()
) {


//22% če na oblačila, toaletne stvari, alkoholne pijače in cigarete
//9% za hrano
//5°% za knjige in druge "paprinate medije"

    //izračun davka glede na to v katerem razredu so izdelki
    fun getTax(): Double {
        var tax = 0.0
        //za vsak izdelek izračunamo davke

        for ((item, quantity) in items) {
            if (item.taxLevel == TaxLevel.A) {
                tax += item.price / 22 * quantity
            } else if (item.taxLevel == TaxLevel.B) {
                tax += item.price / 9.5 * quantity
            } else if (item.taxLevel == TaxLevel.C) {
                tax += item.price / 5 * quantity
            }
        }
        return tax
    }

    var netoPrice = finalPrice - getTax()


    //izpis
    fun print() {
        println("Invoice #${id} - ${date}")
        println("Items:")
        for ((item, quantity) in items) {
            println(
                "- ${item.name}: ${item.price} x ${quantity} ......... ${
                    String.format(
                        "%.2f",
                        item.price * quantity
                    )
                }"
            )
        }
        println("Final Price: ${String.format("%.2f", finalPrice)}")
        println("Taxes: ${String.format("%.2f", getTax())}")
        println("Neto price: ${String.format("%.2f", netoPrice)}")
        println("Invoice code: ${code}")

    }
}
fun LinkedHashMap<Item, Int>.getTotalPrice(): Double {
    var totalPrice = 0.0
    for ((item, quantity) in this) {
        totalPrice += item.price * quantity
    }
    return totalPrice
}

