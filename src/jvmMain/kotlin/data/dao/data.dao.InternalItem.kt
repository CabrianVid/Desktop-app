package data.dao
import model.InternalItem


interface InternalItemDao : DaoCrud<InternalItem> {
    fun getByName(name: String): InternalItem?

    fun getByCode(name: String): InternalItem?

}
