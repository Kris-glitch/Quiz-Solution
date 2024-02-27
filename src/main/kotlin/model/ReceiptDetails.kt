package model

import java.io.Serializable

data class ReceiptDetails (
    val receiptDetailsList: List<ReceiptDetailsItem>
): Serializable