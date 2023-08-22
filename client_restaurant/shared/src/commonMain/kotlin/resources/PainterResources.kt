package resources

data class PainterResources(
    val bpIcon: String,
    val bpLogo: String,
    val arrowLeft: String,
    val arrowDown:String,
    val moonStars: String,
    val sort: String,
    val sun: String,
    val test:String
)

val BpPainterLightResources = PainterResources(
    bpIcon = "ic_beep_beep_icon.xml",
    bpLogo = "ic_beep_beep_logo.xml",
    arrowLeft = "arrow_left.xml",
    moonStars = "moon_stars.xml",
    sort = "sort.xml",
    sun = "sun.xml",
    arrowDown= "arrowdown.svg",
    test = "test.jpg"
)

val BpPainterDarkResources = PainterResources(
    bpIcon = "ic_beep_beep_icon.xml",
    bpLogo = "ic_beep_beep_logo.xml",
    arrowLeft = "arrow_left.xml",
    moonStars = "moon_stars.xml",
    sort = "sort.xml",
    sun = "sun.xml",
    arrowDown= "arrowdown.svg",
    test = "test.jpg"
)