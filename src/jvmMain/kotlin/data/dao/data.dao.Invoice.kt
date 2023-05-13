package data.dao
import model.Invoice
import java.util.*

interface InvoiceDao : DaoCrud<Invoice> {
    override fun getById(id : UUID): Invoice?

    fun getByPrice (price: Double): Invoice?

}
