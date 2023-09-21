package util

fun String.capitalizeFirstLetter(): String {
    return lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}