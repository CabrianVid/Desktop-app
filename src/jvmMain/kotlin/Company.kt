class Company(
    var name: String,
    var taxNumber: String,//SI58665765
    var registrationNumber: String,//6261752000
    var taxpayer: Boolean
) : Searchable {
    override fun search(word: String): Boolean {
        return name.contains(word, true) || taxNumber.contains(word, true) || registrationNumber.contains(word, true)
    }
}
