package data.model

import java.time.LocalDateTime

class Items : LinkedHashMap<Item, Int>() {

    var created: LocalDateTime = LocalDateTime.now()
    var modified: LocalDateTime = created

    fun getQuantity(item: Item): Int? {
        return this[item]
    }

    override fun remove(item: Item): Int? {
        //check if item is even in items
        if (getQuantity(item) == null) throw IllegalStateException("Item does not exist in the list")
        else if (getQuantity(item) == 1) {
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
        if (item.weight != null) {
            if (item.weight < 0) throw IllegalArgumentException("Weight must be greater than 0")
            else {
                item.created = LocalDateTime.now()
                item.modified = item.created
                modified = item.created
                val put = super.put(item, 1)
                return put
            }
        } else {
            if (quantity < 1) throw IllegalArgumentException("Quantity must be greater than 0")
            else {
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
        }

    }

    fun updateItem(oldItem: Item, updatedItem: Item) {
        if (containsKey(oldItem) && updatedItem != null) {

            remove(oldItem)
            if (updatedItem.weight != null) {
                put(updatedItem, 1)
            } else {
                val quantity = getQuantity(updatedItem)!!
                put(updatedItem, quantity)
            }
            updatedItem.modified = LocalDateTime.now()
            modified = LocalDateTime.now()
        } else {
            throw IllegalStateException("Item does not exist in the list")
        }
    }


}

fun LinkedHashMap<Item, Int>.getTotalPrice(): Double {
    var totalPrice = 0.0
    for ((item, quantity) in this) {
        if (item.weight != null) {
            totalPrice += item.price * quantity
        }
        totalPrice += item.price * quantity
    }
    return totalPrice
}

fun LinkedHashMap<Item, Int>.getTax(): Double {
    var tax = 0.0
    //za vsak izdelek izračunamo davke

    for ((item, quantity) in this) {
        if (item.weight != null) {
            if (item.taxLevel == TaxLevel.A) {
                tax += item.price / 22 * quantity * item.weight
            } else if (item.taxLevel == TaxLevel.B) {
                tax += item.price / 9.5 * quantity * item.weight
            } else if (item.taxLevel == TaxLevel.C) {
                tax += item.price / 5 * quantity * item.weight
            }
        } else {
            if (item.taxLevel == TaxLevel.A) {
                tax += item.price / 22 * quantity
            } else if (item.taxLevel == TaxLevel.B) {
                tax += item.price / 9.5 * quantity
            } else if (item.taxLevel == TaxLevel.C) {
                tax += item.price / 5 * quantity
            }
        }
    }
    return tax
}