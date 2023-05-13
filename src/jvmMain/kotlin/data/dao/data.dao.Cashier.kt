package data.dao
import model.Cashier

interface CashierDao : DaoCrud<Cashier> {
    fun getByIdNumber(id: String): Cashier?
    fun getByName(name: String): Cashier?
}
