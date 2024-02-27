import data.DataModule.dataModule
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import network.NetworkModule.networkModule
import org.koin.core.context.GlobalContext.startKoin
import org.koin.java.KoinJavaComponent.inject


fun main(args: Array<String>) {

    startKoin {
        modules(networkModule, dataModule)
    }

    val viewModel: ReceiptDetailsViewModel by inject(ReceiptDetailsViewModel::class.java)

    viewModel.getReceiptDetails()

    runBlocking {

        delay(1000)
        viewModel.receiptDetailsViewModelState.collect{uiState->
            when (uiState) {
                is ReceiptDetailsViewModel.ReceiptDetailsViewModelState.Loaded -> {
                    println(PrintUtils.formatDomesticDetails(uiState.domesticList))
                    println(PrintUtils.formatImportedDetails(uiState.importedList))
                    println("Domestic cost: $${PrintUtils.formatPrice(uiState.domesticList.sumOf { it.price })}")
                    println("Imported cost: $${PrintUtils.formatPrice(uiState.importedList.sumOf { it.price })}")
                    println("Domestic count: ${uiState.domesticList.size}")
                    println("Imported count: ${uiState.importedList.size}")
                }
                is ReceiptDetailsViewModel.ReceiptDetailsViewModelState.Loading -> {
                    //optional
                    //println("Loading")
                }
                is ReceiptDetailsViewModel.ReceiptDetailsViewModelState.Error -> {
                    //optional
                    //println("Error: ${uiState.message}")
                }
            }
        }
    }

}