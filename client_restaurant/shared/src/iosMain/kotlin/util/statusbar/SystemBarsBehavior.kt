package util.statusbar

actual class SystemBarsBehavior {
    actual companion object {
        private val instance = SystemBarsBehavior()


        actual val Default: SystemBarsBehavior = instance


        actual val Immersive: SystemBarsBehavior = instance
    }
}