package data

import kotlinx.coroutines.flow.StateFlow
import model.ReceiptDetails
import java.lang.Exception

interface ReceiptDetailsRepository {

    suspend fun getReceiptDetails(): StateFlow<ReceiptDetailsState>

    data class ReceiptDetailsState(
        val receiptDetailsList: ReceiptDetails?,
        val loading: Boolean,
        val lastException: Exception?
    )
}