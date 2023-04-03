import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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

data class elements(val name: String, val icon: ImageVector)

// variables
val tabs = listOf(
    elements(
        "Invoices",
        Icons.Rounded.List
    ),
    elements(
        "About",
        Icons.Rounded.Info
    )
)


@Composable
fun Invoices() {

        Column(
            modifier = Modifier.fillMaxWidth().padding(200.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = "INVOICES CONTENT"
            )
        }
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
                text = "You are viewing the $currTab tab",
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun NavigationBar() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(
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
            0 -> Invoices()
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
//            Company(
//                "Mercator",
//                "Dunajska cesta 107",
//                "SI4588595",
//                "mercator@gmail.com",
//                "051285686",
//                "75943695",
//                "www.mercator.si",
//                true
//            )
//        val cashier = Cashier("Janez", "Novak")
//        val customer = Company(
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
//        val internalItem1 = InternalItem(IternalID.BANANA, FoodType.FRUIT, "Banana", TaxLevel.B, 3.20)
//        println("test2")
//        val interalItem2 = InternalItem(IternalID.PEAR, FoodType.FRUIT, "Apple", TaxLevel.B, 6.50)
//        val internalItem3 = InternalItem(IternalID.TOMATO, FoodType.VEGETABLE, "Tomato", TaxLevel.B, 0.5)
//        val internalItem4 = InternalItem(IternalID.BAGETTE, FoodType.BREAD, "Bagette", TaxLevel.B, 1.2)
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
