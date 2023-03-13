package data.model

import Issuer
import Cashier
import Company
import Searchable
import java.time.LocalDateTime
import java.util.UUID
import java.util.LinkedHashMap

//
//• issuer (izdajatelj),
//• customer (stranka),
//• cashier (blagajnik)


class Invoice(
    private var date: LocalDateTime,
    private var items: LinkedHashMap<Item, Int>,

    private var issuer: Issuer,
    private var cashier: Cashier,
    private var customer: Company? = null, //pomeni da je lahko null in takrat ni firme
    private var id: UUID = UUID.randomUUID(),
    private var code: UUID = UUID.randomUUID(),
    private var finalPrice: Double = items.getTotalPrice(),
    private var netoPrice: Double = finalPrice - items.getTax(),

    val created: LocalDateTime = LocalDateTime.now(),
    var modified: LocalDateTime = LocalDateTime.now()
) : Searchable {
    override fun search(word: String): Boolean {
        return date.toString().contains(word, true) || items.toString()
            .contains(word, true) || issuer.search(word) || cashier.search(word) || id.toString()
            .contains(word, true) || code.toString().contains(word, true) || finalPrice.toString()
            .contains(word, true) || netoPrice.toString().contains(word, true) || created.toString()
            .contains(word, true) || modified.toString().contains(word, true)
    }

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


    //izpis
    fun print() {
        println("Invoice #${id} - ${date}")
        println("Issuer name: ${issuer.name}")
        println("Issuer address: ${issuer.address}")
        println("Issuer bank account: ${issuer.bankAccount}")
        println("Issuer email: ${issuer.email}")
        println("Issuer phone: ${issuer.phone}")
        println("Issuer website: ${issuer.website}")

        println("Customer name: ${customer?.name}")
        println("Customer tax number: ${customer?.taxNumber}")
        println("Customer bank account: ${customer?.registrationNumber}")
        if (customer?.taxpayer == true) {
            println("Customer tax payer: yes")
        } else {
            println("Customer tax payer: no")
        }


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


        println("Cashier name: ${cashier.name}")
        println("Cashier surname: ${cashier.surname}")
        println("Cashier email: ${cashier.idNumber}")

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

fun LinkedHashMap<Item, Int>.getTax(): Double {
    var tax = 0.0
    //za vsak izdelek izračunamo davke

    for ((item, quantity) in this) {
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
