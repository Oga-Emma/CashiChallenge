package com.example.cashichallenge.util

object DataFactory {

    val validPaymentResponse = """
        {
            "message": "Payment request received and transaction initiated.",
            "success": true,
            "data": {
                "id": "8a05a3d5-c73d-4b1f-8e79-f674828606e1",
                "senderId": "user-123",
                "recipientEmail": "jane.doe@example.com",
                "amount": 99.99,
                "currency": "USD",
                "timestamp": 1761653484001,
                "status": "PENDING"
            }
        }
    """.trimIndent()
}