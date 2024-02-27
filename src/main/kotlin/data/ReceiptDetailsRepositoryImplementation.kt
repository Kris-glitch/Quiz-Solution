package data

import data.model.ReceiptDetailsDTOItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import model.ReceiptDetails
import model.ReceiptDetailsItem
import network.ReceiptDetailsApi
import java.lang.Exception

class ReceiptDetailsRepositoryImplementation(
    private val receiptDetailsApi: ReceiptDetailsApi
) : ReceiptDetailsRepository {

    private var _receiptDetailFlow =
        MutableStateFlow(
            ReceiptDetailsRepository.ReceiptDetailsState(
                receiptDetailsList = null,
                loading = true,
                lastException = null
            )
        )
    private val receiptDetailFlow: StateFlow<ReceiptDetailsRepository.ReceiptDetailsState> =
        _receiptDetailFlow

    override suspend fun getReceiptDetails(): StateFlow<ReceiptDetailsRepository.ReceiptDetailsState>{
        _receiptDetailFlow.update {
            it.copy(receiptDetailsList = null, loading = true, lastException = null)
        }

        try {
            val response = receiptDetailsApi.getReceiptDetails()
            val receiptDetails = mapReceiptDetailsDTO(response)

            _receiptDetailFlow.update {
                it.copy(receiptDetailsList = receiptDetails, loading = false)
            }
        } catch (e: Exception) {
            _receiptDetailFlow.update {
                it.copy(loading = false, lastException = e)
            }
        }
        return receiptDetailFlow
    }

    private fun mapReceiptDetailsDTO(responseList: List<ReceiptDetailsDTOItem>): ReceiptDetails {
        val receiptDetailsList = mutableListOf<ReceiptDetailsItem>()

        for (receiptItem in responseList) {

            val receiptDetailsItem = ReceiptDetailsItem(
                description = receiptItem.description,
                domestic = receiptItem.domestic,
                name = receiptItem.name,
                price = receiptItem.price,
                weight = receiptItem.weight
            )
            receiptDetailsList.add(receiptDetailsItem)
        }
        return ReceiptDetails(receiptDetailsList.toList())
    }

}