package data
import data.model.Item
import data.model.Items
import data.model.Invoice
import java.util.*


fun main() {
    var item1 = Item("krompir", 4.3,2)
    var item2 = Item("spageti", 7.00,2)
    var item3 = Item("sir", 1.99,2)
    var item4 = Item("meso", 12.00,2)

    //create a new Items hashmap
    val items = Items()
    items.addItem(item1,1)
    items.addItem(item2,1)
    items.addItem(item3,1)
    items.addItem(item4,1)
    items.removeItem(item1,1)
    //create a new invoice
    val invoice = Invoice(Date(), items)
    invoice.print();



    /*

    var items = Items(mutableListOf(item1, item2, item3, item4))
    val currentDate = Date()

    var invoice = Invoice(currentDate, items)
    //dodaj item
    invoice.items.addItem(Item("kafa", 2.99, 1,2))
    invoice.items.addItem(Item("kafa", 2.99, 1,2))
    invoice.items.addItem(Item("kafa", 2.99, 1,2))

    //odstrani item
    invoice.items.deleteItem(Item("kafa", 2.99, 1,2))
    //posodobii item
    invoice.items.updateItem( 0, Item("puding", 12.20, 1,2))
    //izpiši račun
    invoice.print();
*/









}