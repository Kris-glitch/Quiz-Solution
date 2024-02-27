package data

import ReceiptDetailsViewModel
import org.koin.dsl.module

object DataModule {

    val dataModule = module {
        single<ReceiptDetailsRepository> { ReceiptDetailsRepositoryImplementation(get()) }
        single { ReceiptDetailsViewModel(get()) }
    }

}