package com.example.ecommerceapp.models

enum class OrderStatus(val statusName: String, val statusCode: Int) {
    Pending("Pending", 0),
    Processing("Processing", 1),
    ReadyForShipment("Ready for Shipment", 2),
    PartiallyReady("Partially Ready", 3),
    OrderDispatched("Order Dispatched", 4),
    PartiallyDelivered("Partially Delivered", 5),
    Delivered("Delivered", 6),
    Cancelled("Cancelled", 7);

    companion object {
        // Convert the status string to the corresponding enum
        fun fromString(value: String): OrderStatus {
            return values().firstOrNull { it.statusName == value } ?: Pending
        }

        // Convert the status code (Int) to the enum
        fun fromInt(value: Int): OrderStatus {
            return values().getOrElse(value) { Pending }
        }
    }
}
