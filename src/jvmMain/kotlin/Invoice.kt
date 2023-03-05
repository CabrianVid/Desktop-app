
package data.model
import java.util.UUID

import java.util.Date

class Invoice(private var id: UUID, private var date: Date, public var items: Items, private var code: UUID) {
    constructor(date: Date,  items: Items)
            : this( UUID.randomUUID(), date, items,  UUID.randomUUID()){

        var created: Date = Date()
        var modified: Date = created
    }

//22% če na oblačila, toaletne stvari, alkoholne pijače in cigarete
//9% za hrano
//5°% za knjige in druge "paprinate medije"

    //izračun davka glede na to v katerem razredu so izdelki
    fun getTax(): Double {
        var tax = 0.0
        for (item in items.itemList) {
            if (item.taxLevel == 1) {
                tax += item.price / 22 * item.quantity
            } else if (item.taxLevel == 2) {
                tax += item.price / 9.5 * item.quantity
            } else if (item.taxLevel == 3) {
                tax += item.price / 100 * item.quantity
            }
        }
        return tax
    }



    var finalPrice = items.getTotalPrice();
    val taxFormatted = String.format("%.2f", getTax())
    var netoPrice = finalPrice - getTax()
    val netoPriceFormatted = String.format("%.2f", netoPrice)

    //izpis
    fun print() {
        println("Invoice #${id} - ${date}")
        println("Items:")
        for (item in items.itemList) {
            println("- ${item.name}: ${item.price} x ${item.quantity} = ${item.price * item.quantity}")
        }
        println("Final Price: ${finalPrice}")
        println("Taxes: ${taxFormatted}")
        println("Neto price: ${netoPriceFormatted}")
        println("Invoice code: ${code}")

    }



}