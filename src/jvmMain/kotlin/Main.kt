package data
import data.model.Item
import data.model.Items
import data.model.Invoice
import java.util.*


fun main() {
    var item1 = Item("krompir", 4.3, 1,2)
    var item2 = Item("spageti", 7.00, 5,2)
    var item3 = Item("sir", 1.99, 2,2)
    var item4 = Item("meso", 12.00, 5,2)

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










}
