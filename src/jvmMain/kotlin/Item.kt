
package data.model
import java.util.UUID
import java.util.Date
import kotlin.random.Random

class Item(public var id: UUID, public var name: String, public var price: Double, public var quantity: Int, public var taxLevel: Int,  public var code: Long) {
    constructor(name: String, price: Double, quantity: Int, taxLevel: Int)
            : this(UUID.randomUUID(), name, price, quantity, taxLevel,Random.nextLong(1_000_000_000_000, 10_000_000_000_000))

    var created: Date = Date()
    var modified: Date = created
    fun update() {
        modified = Date()
    }

}
