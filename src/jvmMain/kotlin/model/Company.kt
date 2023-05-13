package model

class Company(
    var name: String,
    var address: String,        //Dunajska cesta 107
    var taxNumber: String,//SI58665765...bank account
    var email: String,              //mercator@gmail.com
    var phone: String,              //051285686
    var registrationNumber: String,//6261752000
    var website: String,           //www.mercator.si
    var taxpayer: Boolean
) : Searchable {
    override fun search(word: String): Boolean {
        return name.contains(word, true) || address.contains(word, true) || taxNumber.contains(
            word,
            true
        ) || email.contains(word, true) || phone.contains(word, true) || registrationNumber.contains(
            word,
            true
        ) || website.contains(word, true) || taxpayer.toString().contains(word, true)
    }
}
