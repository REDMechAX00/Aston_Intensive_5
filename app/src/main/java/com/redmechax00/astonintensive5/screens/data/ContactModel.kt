package com.redmechax00.astonintensive5.screens.data

data class ContactModel(
    val itemId: Int = -1,
    val itemName: String = "",
    val itemSurname: String = "",
    var itemPhone: String = ""
) {
    override fun equals(other: Any?): Boolean {
        other as ContactModel
        if (itemId != other.itemId) return false
        if (itemName != other.itemName) return false
        if (itemSurname != other.itemSurname) return false
        if (itemPhone != other.itemPhone) return false
        return true
    }

    override fun hashCode(): Int {
        var result = itemId.hashCode()
        result = 31 * result + itemName.hashCode()
        result = 31 * result + itemSurname.hashCode()
        result = 31 * result + itemPhone.hashCode()
        return result
    }

    override fun toString(): String {
        return "$itemName $itemSurname"
    }
}

