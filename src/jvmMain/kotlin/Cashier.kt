import kotlin.random.Random


fun generateRandomCode(): String {
    val random = Random.Default
    val builder = StringBuilder(8)
    repeat(8) {
        builder.append(random.nextInt(10))
    }
    return builder.toString()
}

class Cashier(
    var name: String,
    var surname: String,
    var idNumber: String = generateRandomCode(),
) : Searchable {
    override fun search(word: String): Boolean {
        return name.contains(word, true) || surname.contains(word, true) || idNumber.contains(word, true)
    }
    fun specifyCode(numbers: String,food : InternalItem): String {

        return idNumber
    }


}
