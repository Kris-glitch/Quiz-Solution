package data.model

import java.io.Serializable

data class ReceiptDetailsDTOItem(
    val description: String,
    val domestic: Boolean,
    val name: String,
    val price: Double,
    val weight: Int?
): Serializable