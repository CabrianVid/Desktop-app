package data

import Cashier
import Company
import Issuer
import Searchable
import data.model.Item
import data.model.Items
import data.model.Invoice
import data.model.TaxLevel
import java.time.LocalDateTime
import java.util.*

fun main() {

    //create Issuer
    val issuer =
        Issuer("Mercator", "Dunajska cesta 107", "SI4588595", "mercator@gmail.com", "051285686", "www.mercator.si")
    val cashier = Cashier("Janez", "Novak")
    val customer = Company("HENKEL MARIBOR d.o.o.", "SI58665765", "6261752000", true)

    var item1 = Item("krompir", 4.3, TaxLevel.B)
    var item2 = Item("spageti", 7.00, TaxLevel.A)
    var item3 = Item("sir", 1.99, TaxLevel.A)
    var item4 = Item("meso", 12.00, TaxLevel.C)
    item2.update("jogurt", 99.00, TaxLevel.C)


    val items = Items()

    items.put(item1, 1)
    items.put(item1, 1)
    items.put(item1, 1)
    items.put(item2, 1)
    items.put(item3, 3)
    items.put(item4, 1)

    items.remove(item1)
    items.remove(item3)
    items.updateItem(item3, item4)
    //create a new invoice
    val invoice = Invoice(LocalDateTime.now(), items, issuer, cashier)

    invoice.print()
    val findRE : Boolean = invoice.search("anez")
    if(findRE){
        println("true")
    }else{
        println("false")
    }
    //invoice.print(invoice.search("anez"))
}