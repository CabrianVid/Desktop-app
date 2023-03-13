package data.model

import java.time.LocalDateTime
import java.util.*

class Items : LinkedHashMap<Item, Int>() {

    var created: LocalDateTime = LocalDateTime.now()
    var modified: LocalDateTime = created

    fun getQuantity(item: Item): Int? {
        return this[item]
    }
    override fun remove(item: Item): Int? {
        if (getQuantity(item) == 1) {
            val removed = super.remove(item)
            modified = LocalDateTime.now()
            return removed
        } else if (getQuantity(item)!! > 1) {
            val newQuantity = getQuantity(item)!! - 1
            val put = super.put(item, newQuantity)
            modified = LocalDateTime.now()
            item.modified = LocalDateTime.now()
            return put
        } else {
            throw IllegalStateException("Item does not exist in the list")
        }
    }
    override fun put(item: Item, quantity: Int): Int? {
        if (containsKey(item)) {
            val newQuantity = getQuantity(item)!! + quantity
            val put = super.put(item, newQuantity)
            modified = LocalDateTime.now()
            return put
        } else {
            item.created = LocalDateTime.now()
            item.modified = item.created
            modified = item.created
            val put = super.put(item, quantity)
            return put
        }
    }
    fun updateItem(oldItem: Item, updatedItem: Item) {
        if (containsKey(oldItem)) {
            val quantity = getQuantity(updatedItem)!!
            remove(oldItem)
            put(updatedItem, quantity)
            updatedItem.modified = LocalDateTime.now()
            modified = LocalDateTime.now()
        }
        else {
            throw IllegalStateException("Item does not exist in the list")
        }
    }
}
