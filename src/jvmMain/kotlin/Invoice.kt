
package data.model

import java.util.*
import java.util.UUID

import java.util.Date
import java.util.LinkedHashMap


class Invoice(private var id: UUID, private var date: Date, private var items: LinkedHashMap<Item, Int>, private var code: UUID) {
    constructor(date: Date,items: LinkedHashMap<Item, Int>)
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
        //za vsak izdelek izračunamo davke

        for ((item, quantity) in items) {
            if (item.taxLevel == 1) {
                tax += item.price / 22 * quantity
            } else if (item.taxLevel == 2) {
                tax += item.price / 9.5 * quantity
            } else if (item.taxLevel == 3) {
                tax += item.price / 100 * quantity
            }
        }
        return tax
    }




    var finalPrice = items.getTotalPrice();
    val finalPriceFormatted = String.format("%.2f", finalPrice)
    val taxFormatted = String.format("%.2f", getTax())
    var netoPrice = finalPrice - getTax()
    val netoPriceFormatted = String.format("%.2f", netoPrice)

    //izpis
    fun print() {
        println("Invoice #${id} - ${date}")
        println("Items:")
        for ((item, quantity) in items) {
            println("- ${item.name}: ${item.price} x ${quantity} = ${item.price * quantity}")
        }
        println("Final Price: ${finalPriceFormatted}")
        println("Taxes: ${taxFormatted}")
        println("Neto price: ${netoPriceFormatted}")
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

