class Issuer(
    var name: String,       //npr. Mercator
    var address: String,        //Dunajska cesta 107
    var bankAccount: String,      //SI4588595
    var email: String,              //mercator@gmail.com
    var phone: String,              //051285686
    var website: String             //www.mercator.si
) : Searchable {
    override fun search(word: String): Boolean {
        return name.contains(word, true) || address.contains(word, true) || bankAccount.contains(
            word,
            true
        ) || email.contains(word, true) || phone.contains(word, true) || website.contains(word, true)
    }
}