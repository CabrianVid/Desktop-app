package data
import data.model.Item
import data.model.Items
import data.model.Invoice
import data.model.TaxLevel
import java.time.LocalDateTime


/*
popravi:
1.Date      opravljeno
2.konstruktore      opravljeno
3.da dejansko prikrijes metodi put in remove(al kak ze)...    opreavljeno
4.da samo enkrat zracunas skupno ceno     opravljeno
5.ce je level 1,2,3 spremeni v enum class   opravljeno
6.pri item naredi da ma funkcija update neko uporabnost   opravljeno
7.kodo pri itemu naredi boljso da je lahko tudi 0xxxxxxx...   opravljeno
8.create and modify pri invoice    opravljeno
9. items update    opravljeno

 */


fun main() {


    var item1 = Item("krompir", 4.3, TaxLevel.B)
    var item2 = Item("spageti", 7.00, TaxLevel.A)
    var item3 = Item("sir", 1.99,TaxLevel.A)
    var item4 = Item("meso", 12.00,TaxLevel.C)
    item2.update("jogurt", 99.00, TaxLevel.C)


    val items = Items()

    items.put(item1,1)
    items.put(item1,1)
    items.put(item1,1)
    items.put(item2,1)
    items.put(item3,3)
    items.put(item4, 1)

    items.remove(item1)
    items.remove(item3)
    items.updateItem(item3, item4)
    //create a new invoice
    val invoice = Invoice(LocalDateTime.now(), items)
    invoice.print()



}