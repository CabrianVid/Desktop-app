package data

import Cashier
import Company
import FoodType
import InternalItem
import IternalID
import data.model.Invoice
import data.model.Item
import data.model.Items
import data.model.TaxLevel
import java.time.LocalDateTime


//daj funkcije za generiranje v barcode utility

fun main() {
    try {
        val issuer =
            Company(
                "Mercator",
                "Dunajska cesta 107",
                "SI4588595",
                "mercator@gmail.com",
                "051285686",
                "75943695",
                "www.mercator.si",
                true
            )
        val cashier = Cashier("Janez", "Novak")
        val customer = Company(
            "HENKEL MARIBOR d.o.o.",
            "Presernova 10",
            "SI6261752000",
            "henkel@gmail.com",
            "02 234 56 78",
            "75943695",
            "www.henkel.si",
            false
        )


        val item1 = Item("krompir", TaxLevel.B, 890.12, "123", "4567")
        println("test")
        println(item1.code)
        val internalItem1 = InternalItem(IternalID.BANANA, FoodType.FRUIT, "Banana", TaxLevel.B, 3.20)
        println("test2")
        val interalItem2 = InternalItem(IternalID.PEAR, FoodType.FRUIT, "Apple", TaxLevel.B, 6.50)
        val internalItem3 = InternalItem(IternalID.TOMATO, FoodType.VEGETABLE, "Tomato", TaxLevel.B, 0.5)
        val internalItem4 = InternalItem(IternalID.BAGETTE, FoodType.BREAD, "Bagette", TaxLevel.B, 1.2)
        val items = Items()
        items.put(item1, 6)
        items.put(internalItem1, 23)
        //items.updateItem(internalItem1,interalItem2)
        items.put(interalItem2, 1)
        items.put(internalItem3, 1)
        items.put(internalItem4, 1)
        items.remove(internalItem4)

        val invoice = Invoice(LocalDateTime.now(), items, issuer, cashier, customer)
        invoice.print()

        val findRE: Boolean = invoice.search("anez")
        if (findRE) {
            println("true")
        } else {
            println("false")
        }


    } catch (e: Exception) {
        println(e.message)
    }

}