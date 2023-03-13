package data

import Cashier
import Company
import data.model.Item
import data.model.Items
import data.model.Invoice
import data.model.TaxLevel
import java.time.LocalDateTime

// try and catch   opravljeno
//da ce je customer null ne izpises      opravljeno
// Company in Issuer zdruzi       opravljeno
// funkciji v Invoice prestavi v Items      opravljeno

fun main() {

    //create Issuer
    val issuer =
        Company("Mercator", "Dunajska cesta 107", "SI4588595", "mercator@gmail.com", "051285686", "75943695", "www.mercator.si", true)
    val cashier = Cashier("Janez", "Novak")
    val customer = Company("HENKEL MARIBOR d.o.o.", "Presernova 10", "SI6261752000", "henkel@gmail.com", "02 234 56 78", "75943695", "www.henkel.si", false)

    var item1 = Item("krompir", 4.3, TaxLevel.B)
    var item2 = Item("spageti", 7.00, TaxLevel.A)
    var item3 = Item("sir", 1.99, TaxLevel.A)
    var item4 = Item("meso", 12.00, TaxLevel.C)
    var item5 = Item("sladoled", 1.99, TaxLevel.C)
    item2.update("jogurt", 99.00, TaxLevel.C)


    val items = Items()
    try{
    items.put(item1, 1)
    items.put(item1, 1)
    items.put(item1, 1)
    items.put(item2, 1)
    items.put(item3, 3)
    items.put(item4, 1)
    items.put(item4, 1)
    items.put(item5, -1)

        items.remove(item5)
        items.remove(item3)
        items.updateItem(item3, item5)
    }catch (e: Exception){
        println(e.message)}


    val invoice = Invoice(LocalDateTime.now(), items, issuer, cashier, customer)
    invoice.print()
    val findRE : Boolean = invoice.search("anez")
    if(findRE){
        println("true")
    }else{
        println("false")
    }
    //invoice.print(invoice.search("anez"))
}