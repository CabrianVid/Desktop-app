

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.List
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import data.dao.mysql.MySqlItem
import data.model.Item

//ERR model spremeni da ne bo skup v items item in internal item


//
data class elements(val name: String, val icon: ImageVector)

// variables
val tabs = listOf(
    elements(
        "Items",
        Icons.Rounded.List
    ),
    elements(
        "About",
        Icons.Rounded.Info
    )
)
@Composable
fun Items() {
    val items: List<Item> by remember { mutableStateOf(MySqlItem().getAll()) }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(items) { index, item ->
            Text(text = "${item.name}, ${item.price}€")
        }
    }
//
//        Column(
//            modifier = Modifier.fillMaxWidth().padding(200.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                textAlign = TextAlign.Center,
//                text = "ITEM CONTENT"
//            )
//        }
    }


@Composable
fun AboutApp() {
        Column(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(15.dp),
                text = "About application",
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Principles of programming languages\nAuthor: Matej Moravec",
                textAlign = TextAlign.Center
            )
        }

}

@Composable
fun Footer(index: Int) {
    val currTab = tabs[index].name
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Cyan)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                text = "You are viewing the \"$currTab\" tab",
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun NavigationBar() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow( //row
            selectedTabIndex = selectedTabIndex,
            backgroundColor = Color.Cyan
        ) {
            tabs.forEachIndexed { index, item ->
                Tab(
                    text = { Text(text = item.name) },
                    selected = selectedTabIndex == index,
                    icon = { Icon(item.icon, "") },
                    onClick = {
                        selectedTabIndex = index
                    }
                )
            }
        }
        when (selectedTabIndex) {
            0 -> Items()
            1 -> AboutApp()
        }
        Footer(selectedTabIndex)
    }
}

fun main() {


    androidx.compose.ui.window.singleWindowApplication(
        title = "Compose Desktop",
        state = WindowState(width = 800.dp, height = 600.dp)
    ) {
        NavigationBar()
    }
}
//    DataBaseUtil.testConnection()
//    //__________________________________6 naloga_______________________________________________________
//
//
//    val mySqlItem = MySqlItem()
//
//// Insert
//    val newItem = Item(
//        name = "Apple",
//        taxLevel = TaxLevel.A,
//        price = 99.99,
//        internCode = "IC123",
//        itemId = "4",
//        weight = 1.0,
//        checkNumber = "1",
//        code = "1234567891231"
//    )
//    println(if (mySqlItem.insert(newItem)) "The item was inserted successfully." else "Failed to insert the item.")
//
//// Get by name and get all
//    val allItems = mySqlItem.getAll()
//    allItems.forEach { println("All items: ${it.name}") }
//    println("Item found: ${mySqlItem.getByName("Banana")?.name ?: "Item not found."}")
//
//// Update item
//    val itemToUpdate = mySqlItem.getByName("Banana")?.apply {
//        price = 199.99
//        taxLevel = TaxLevel.B
//    }
//    println(if (itemToUpdate != null && mySqlItem.update(itemToUpdate)) "The item was updated successfully." else "Failed to update the item.")
//
//// Get by barcode
//    val barcode = "1234567891231"
//    val itemByBarcode = mySqlItem.getByBarcode(barcode)
//    println(itemByBarcode?.let { "Item found: Name: ${it.name} Price: ${it.price} Tax Level: ${it.taxLevel}" } ?: "No item found with barcode: $barcode")
//
//// Get by id
//    val uuidSearch : UUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000")
//    val itemById = mySqlItem.getById(uuidSearch)
//    println(itemById?.let { "Item found: Name: ${it.name} Price: ${it.price} Tax Level: ${it.taxLevel}" } ?: "No item found with id: $uuidSearch")
//
//// Delete item
//    val itemName = "Apple"
//    val itemToDelete = mySqlItem.getByName(itemName)
//    println(if (itemToDelete != null && mySqlItem.delete(itemToDelete)) "The item was deleted successfully." else "Failed to delete the item.")
//
//    //for Cashier
//    println("Cashier__________________________")
//    val mySqlCashier = MySqlCashier()
//
//    val newCashier = Cashier(
//        name = "John",
//        surname = "Doe"
//    )
//
//    val wasInserted = mySqlCashier.insert(newCashier)
//
//    if (wasInserted) {
//        println("The cashier was inserted successfully.")
//    } else {
//        println("Failed to insert the cashier.")
//    }
//
//    val mySqlCashier2 = MySqlCashier()
//    val cashier = mySqlCashier2.getByName("John")
//
//    if (cashier != null) {
//        println("Cashier found:")
//        println(cashier.name)
//    } else {
//        println("Cashier not found.")
//    }
//
//    val mySqlCashier3 = MySqlCashier()
//    val cashierToUpdate = mySqlCashier3.getByName("John")
//
//    if (cashierToUpdate != null) {
//        cashierToUpdate.surname = "Smith"
//
//        val wasUpdated = mySqlCashier3.update(cashierToUpdate)
//
//        if (wasUpdated) {
//            println("The cashier was updated successfully.")
//        } else {
//            println("Failed to update the cashier.")
//        }
//    } else {
//        println("Cashier not found.")
//    }
//
//    val mySqlCashier4 = MySqlCashier()
//    val cashierToDelete = mySqlCashier4.getByName("John")
//
//    if (cashierToDelete != null) {
//        val wasDeleted = mySqlCashier4.delete(cashierToDelete)
//
//        if (wasDeleted) {
//            println("The cashier was deleted successfully.")
//        } else {
//            println("Failed to delete the cashier.")
//        }
//    } else {
//        println("No cashier found with name: ${cashierToDelete?.name}")
//    }
//
//    println("Company__________________________")
//    val mySqlCompanyDao = MySqlCompany()
//
//    val newCompany = Company("Mercator", "123 X St.", "12345", "contact@companyx.com", "1234567890", "91820", "www.companyx.com", true)
//    val insertSuccess = mySqlCompanyDao.insert(newCompany)
//
//    if (insertSuccess) {
//        println("Company inserted successfully.")
//    } else {
//        println("Failed to insert company.")
//        return
//    }
//
//    val companyFromDb = mySqlCompanyDao.getByName("Mercator")
//
//    if (companyFromDb != null) {
//        println("Retrieved company from database.")
//        println("Company name: ${companyFromDb.name}")
//        println("Company address: ${companyFromDb.address}")
//        // ...
//    } else {
//        println("Failed to retrieve company from database.")
//        return
//    }
//
//    // Update the company
//    companyFromDb.email = "newemail@companyx.com"
//    val updateSuccess = mySqlCompanyDao.update(companyFromDb)
//
//    if (updateSuccess) {
//        println("Company updated successfully.")
//    } else {
//        println("Failed to update company.")
//        return
//    }
//
//    // Get all companies
//    val allCompanies = mySqlCompanyDao.getAll()
//    if (allCompanies.isNotEmpty()) {
//        println("Retrieved all companies from database.")
//        allCompanies.forEach { company ->
//            println("Company name: ${company.name}")
//        }
//    } else {
//        println("No companies found in the database.")
//    }
//
//    // Delete the company
//    val deleteSuccess = mySqlCompanyDao.delete(companyFromDb)
//    if (deleteSuccess) {
//        println("Company deleted successfully.")
//    } else {
//        println("Failed to delete company.")
//    }
//
//    println("InternalItem__________________________")
//
//        val mySqlInternalItemDao = MySqlInternalItem()
//
//        // Create a new InternalItem
//        val internalItem = InternalItem(
//            internalId = IternalID.CHICKEN,
//            department = FoodType.MEAT,
//            name = "Nuggets",
//            taxLevel = TaxLevel.A,
//            weight = 2.0,
//        )
////    name = "Apple",
////    taxLevel = TaxLevel.A,
////    price = 99.99,
////    internCode = "IC123",
////    itemId = "4",
////    weight = 1.0,
////    checkNumber = "1",
////    code = "1234567891231"
//
//        // Insert the InternalItem to the database
//        val insertStatus = mySqlInternalItemDao.insert(internalItem)
//        println("Insert operation status: $insertStatus")
//
//        // Fetch the InternalItem by name
//        val itemByName = mySqlInternalItemDao.getByName("Banana")
//        println("InternalItem fetched by name: ${itemByName?.name}")
//
//
//    println("INVOICE__________________________________________")
//
//
//    val item1 = Item(
//        name = "pasteta",
//        taxLevel = TaxLevel.A,
//        price = 99.99,
//        internCode = "IC123",
//        itemId = "5",
//        weight = 1.0,
//        checkNumber = "1",
//        code = "1234567891231"
//    )
//    val item2 = Item(
//        name= "Orange",
//        taxLevel = TaxLevel.B,
//        price = 99.99,
//        internCode = "IC123",
//        itemId = "6",
//        weight = 1.0,
//        checkNumber = "1",
//        code = "1234567891231"
//    )
//
//    val items = LinkedHashMap<Item, Int>()
//    items[item1] = 5
//    items[item2] = 3
//
//
//    val company = Company("Example Company", "123 Example St", "tax123", "example@company.com", "1234567890", "reg123", "www.example.com", true)
//    val cashier1 = Cashier("John", "Doe")
//
//    val invoice = Invoice(LocalDateTime.now(), items, company, cashier1, company)
//
//    val invoiceDao = MySqlInvoice()
//
//    val wasInserted1 = invoiceDao.insert(invoice)
//
//    if (wasInserted1) {
//        println("Invoice was successfully inserted.")
//    } else {
//        println("Failed to insert the invoice.")
//    }
//    } //KONEC 6 NALOGE








//-----------------------------------------------------------------------------------------------

//fun main() {
//    androidx.compose.ui.window.singleWindowApplication(
//        title = "Compose Desktop",
//        state = WindowState(width = 400.dp, height = 400.dp)
//    ) {
//        Nav()
//    }
//}
//fun main() {
//    val frame = JFrame("My App")
//    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
//    frame.setSize(400, 400)
//    frame.isVisible = true
//
//    val panel = JPanel(BorderLayout())
//    frame.add(panel)
//
//    val textField = JTextField("Hello World")
//    textField.preferredSize = Dimension(100, 30)
//    panel.add(textField, BorderLayout.CENTER)
//}
//@Preview
//@Composable
//fun TopBar() {
//    val (isFirstSelected, setIsFirstSelected) = remember { mutableStateOf(true) }
//
//    Column(modifier = Modifier.fillMaxWidth()) {
//        Row(
//            modifier = Modifier.fillMaxWidth().height(56.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            val firstText = "First Text"
//            val secondText = "Second Text"
//
//            ClickableText(
//                text = buildAnnotatedString {
//                    withStyle(
//                        style = SpanStyle(
//                            color = if (isFirstSelected) MaterialTheme.colors.primary else Color.Gray
//                        )
//                    ) {
//                        append(firstText)
//                    }
//                },
//                onClick = {
//                    setIsFirstSelected(true)
//                }
//            )
//
//            Text(text = " | ")
//
//            ClickableText(
//                text = buildAnnotatedString {
//                    withStyle(
//                        style = SpanStyle(
//                            color = if (!isFirstSelected) MaterialTheme.colors.primary else Color.Gray
//                        )
//                    ) {
//                        append(secondText)
//                    }
//                },
//                onClick = {
//                    setIsFirstSelected(false)
//                }
//            )
//        }
//
//        // Show different content based on which text is selected
//        if (isFirstSelected) {
//            Text(text = "First Text Content")
//        } else {
//            Text(text = "Second Text Content")
//        }
//    }
//}
//_________________________________________________________________________________________________

//    try {
//        val issuer =
//            model.Company(
//                "Mercator",
//                "Dunajska cesta 107",
//                "SI4588595",
//                "mercator@gmail.com",
//                "051285686",
//                "75943695",
//                "www.mercator.si",
//                true
//            )
//        val cashier = model.Cashier("Janez", "Novak")
//        val customer = model.Company(
//            "HENKEL MARIBOR d.o.o.",
//            "Presernova 10",
//            "SI6261752000",
//            "henkel@gmail.com",
//            "02 234 56 78",
//            "75943695",
//            "www.henkel.si",
//            false
//        )
//
//
//        val item1 = Item("Coca Cola", TaxLevel.B, 890.12, "123", "4567")
//        println("test")
//        println(item1.code)
//        val internalItem1 = model.InternalItem(model.IternalID.BANANA, model.FoodType.FRUIT, "Banana", TaxLevel.B, 3.20)
//        println("test2")
//        val interalItem2 = model.InternalItem(model.IternalID.PEAR, model.FoodType.FRUIT, "Apple", TaxLevel.B, 6.50)
//        val internalItem3 = model.InternalItem(model.IternalID.TOMATO, model.FoodType.VEGETABLE, "Tomato", TaxLevel.B, 0.5)
//        val internalItem4 = model.InternalItem(model.IternalID.BAGETTE, model.FoodType.BREAD, "Bagette", TaxLevel.B, 1.2)
//        val items = Items()
//        items.put(item1, 6)
//        items.put(internalItem1, 23)
//        //items.updateItem(internalItem1,interalItem2)
//        items.put(interalItem2, 1)
//        items.put(internalItem3, 1)
//        items.put(internalItem4, 1)
//        items.remove(internalItem4)
//
//        val invoice = Invoice(LocalDateTime.now(), items, issuer, cashier, customer)
//        invoice.print()
//
//        val findRE: Boolean = invoice.search("anez")
//        if (findRE) {
//            println("true")
//        } else {
//            println("false")
//        }
//
//
//    } catch (e: Exception) {
//        println(e.message)
//    }
