package model

object BarcodeUtil {

    fun isLastDigitValid(barcode: String): Boolean {
        var sum = 0
        for (i in 0..11) {
            if (i % 2 == 0) {
                sum += barcode[i].toString().toInt() * 1
            } else {
                sum += barcode[i].toString().toInt() * 3
            }
        }
        var lastDigit = 10 - (sum % 10)
        if (lastDigit == 10) {
            lastDigit = 0
        }

        return lastDigit == barcode[12].toString().toInt()
    }

    fun isValidBarcode(barcode: String): Boolean {

        if (barcode.length != 13) {
            return false
        }

        if (!barcode.all { it.isDigit() }) {
            return false
        }
        if (!isLastDigitValid(barcode)) {
            return false
        }

        // The barcode is valid
        return true
    }
}


