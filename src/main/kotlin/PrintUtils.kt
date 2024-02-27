import model.ReceiptDetailsItem
import java.text.DecimalFormat

object PrintUtils {

    fun formatDomesticDetails(domesticList: List<ReceiptDetailsItem>): String {
        var domesticString = ". Domestic"

        for (produce in domesticList) {
            val weightString = produce.weight?.let { "$it" + "g" } ?: "N/A"

            domesticString += "\n" +
                    "... ${produce.name}\nPrice: $${formatPrice(produce.price)}\n${
                produce.description.substring(
                    0,
                    minOf(produce.description.length, 10)
                )
            }...\nWeight: $weightString"
        }
        return domesticString
    }

    fun formatImportedDetails(importedList: List<ReceiptDetailsItem>): String {
        var importedString = ". Imported"

        for (produce in importedList) {
            val weightString = produce.weight?.let { "$it" + "g" } ?: "N/A"

            importedString += "\n" +
                    "... ${produce.name}\nPrice: $${formatPrice(produce.price)}\n${
                        produce.description.substring(
                            0,
                            minOf(produce.description.length, 10)
                        )
                    }...\nWeight: $weightString"
        }
        return importedString
    }

    fun formatPrice(price: Double): String {
        val decimalFormat = DecimalFormat("#,##0.00")
        val symbols = decimalFormat.decimalFormatSymbols
        symbols.decimalSeparator = ','
        symbols.groupingSeparator = '.'
        decimalFormat.decimalFormatSymbols = symbols
        return decimalFormat.format(price)
    }
}