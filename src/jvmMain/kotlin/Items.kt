
package data.model

import java.util.*

class Items(public val itemList: MutableList<Item>) {

    var created: Date = Date()
    var modified: Date = created


    // dodajanje novih elementov
    fun addItem(item: Item) {
        var alreadyExists: Boolean = false

        for(el in itemList){
            if(item.name == el.name){
                el.quantity+=item.quantity
                alreadyExists = true;
                //nastavimo da je bil spremenjen
                item.modified = Date()
                modified = Date()
            }
        }
        if(alreadyExists == false){
            itemList.add(item)
            //nastavimo modified in created
            item.created = Date()
            item.modified = item.created
            modified = item.created
        }

    }


    // odstrani izdelek iz lista
    fun deleteItem(item: Item) {

        for(el in itemList){
            if(item.name == el.name){
                if(el.quantity>1){
                    el.quantity--;
                    //nastavimo da je spremenjen
                    item.modified = Date()
                    modified = Date()
                }else{
                    itemList.remove(el)
                    //nastavimo da je bil spremenjen
                    modified = Date()
                }
            }
        }





    }

    // izdelek spremenimo glede na index
    fun updateItem(itemIndex: Int, updatedItem: Item) {

        if (itemIndex >= 0) {
            itemList[itemIndex] = updatedItem

            itemList[itemIndex].modified = Date()
            modified = Date()
        }
    }

    //ceno celotnega lista
    fun getTotalPrice(): Double {
        var totalPrice = 0.0
        for (item in itemList) {
            totalPrice += item.price * item.quantity
        }
        return totalPrice
    }

}