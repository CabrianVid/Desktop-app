package data.dao
import model.Company

interface CompanyDao : DaoCrud<Company> {
    fun getByName(name: String): Company?

    fun getByTaxNumber(name: String): Company?

}
