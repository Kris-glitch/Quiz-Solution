import data.ReceiptDetailsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import model.ReceiptDetails
import model.ReceiptDetailsItem


class ReceiptDetailsViewModel(
    private val receiptDetailsRepository: ReceiptDetailsRepository
) {

    private var _receiptDetailsViewModelState: MutableStateFlow<ReceiptDetailsViewModelState> =
        MutableStateFlow(ReceiptDetailsViewModelState.Loading)
    val receiptDetailsViewModelState: StateFlow<ReceiptDetailsViewModelState> = _receiptDetailsViewModelState

    fun getReceiptDetails() {
        CoroutineScope(Dispatchers.IO).launch {
            receiptDetailsRepository.getReceiptDetails().collect { state ->
                when {
                    state.loading -> {
                        _receiptDetailsViewModelState.value = ReceiptDetailsViewModelState.Loading
                    }

                    state.lastException != null -> {
                        _receiptDetailsViewModelState.value =
                            ReceiptDetailsViewModelState.Error(state.lastException.message)
                    }

                    state.receiptDetailsList != null -> {
                        val domesticList = filerDomesticProduct(state.receiptDetailsList)
                        val importedList = filterImportedProduct(state.receiptDetailsList)
                            _receiptDetailsViewModelState.value =
                                ReceiptDetailsViewModelState.Loaded(domesticList = domesticList,importedList = importedList)
                    }
                }
            }
        }
    }

    private fun filerDomesticProduct(receiptDetailsList: ReceiptDetails): List<ReceiptDetailsItem> {
        return receiptDetailsList.receiptDetailsList.filter { it.domestic }.sortedBy { it.name }
    }

    private fun filterImportedProduct(receiptDetailsList: ReceiptDetails): List<ReceiptDetailsItem> {
        return receiptDetailsList.receiptDetailsList.filter { !it.domestic }.sortedBy { it.name }
    }

    sealed interface ReceiptDetailsViewModelState {
        data class Loaded(val domesticList: List<ReceiptDetailsItem>, val importedList: List<ReceiptDetailsItem>) :
            ReceiptDetailsViewModelState
        data object Loading : ReceiptDetailsViewModelState
        data class Error(val message: String?) : ReceiptDetailsViewModelState
    }

}