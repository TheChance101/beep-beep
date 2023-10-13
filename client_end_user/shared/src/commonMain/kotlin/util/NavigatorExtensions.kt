package util

import cafe.adriel.voyager.navigator.Navigator

val Navigator.root: Navigator?
    get() {
        var root: Navigator? = null
        repeat(level) {
            root = root?.parent ?: parent
        }
        return root
    }