package network

import data.model.ReceiptDetailsDTOItem
import retrofit2.http.GET

interface ReceiptDetailsApi {

    @GET("qr-scanner-codes/alpha-qr-gFpwhsQ8fkY1")
    suspend fun getReceiptDetails(): List<ReceiptDetailsDTOItem>
}