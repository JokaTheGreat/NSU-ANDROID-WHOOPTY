package com.example.whoopty.utils

class StringFormater {
    companion object {
        private const val MAX_DESCR_LENGTH = 520

        fun formatDescription(description: String): String {
            if (description.length < MAX_DESCR_LENGTH) {
                return description
            } else if (description.indexOf("\r\n") in 1 until MAX_DESCR_LENGTH) {
                return description.substring(0, description.indexOf("\r\n"))
            } else if (description.indexOf(".") in 1 until MAX_DESCR_LENGTH) {
                return description.substring(0, description.indexOf(".") + 1)
            }

            return description.substring(0, MAX_DESCR_LENGTH) + "..."
        }
    }
}