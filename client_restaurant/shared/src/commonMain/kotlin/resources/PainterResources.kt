package resources

data class PainterResources(
    val bpIcon: String,
    val bpLogo: String,
    val arrowLeft: String,
    val moonStars: String,
    val filledStar: String,
    val halfFilledStar: String,
    val sort: String,
    val sun: String,
    val galleryAdd: String,
    val edit: String,
    val flag: String,
    val backgroundPattern: String,
    val logout: String
)

val BpPainterLightResources = PainterResources(
    bpIcon = "ic_beep_beep_icon.xml",
    bpLogo = "ic_beep_beep_logo.xml",
    arrowLeft = "arrow_left.xml",
    moonStars = "moon_stars.xml",
    filledStar = "ic_filled_star_light.xml",
    halfFilledStar = "ic_half_filled_star_light.xml",
    sort = "sort.xml",
    sun = "sun.xml",
    arrowDown = "arrowdown.svg",
    logout = "ic_logout.xml",
    backgroundPattern = "background_pattern.png"
    galleryAdd = "gallery_add.xml",
    edit = "edit.xml",
    flag = "flag.xml",
    logout = "ic_logout.xml"
)

val BpPainterDarkResources = PainterResources(
    bpIcon = "ic_beep_beep_icon.xml",
    bpLogo = "ic_beep_beep_logo.xml",
    arrowLeft = "arrow_left.xml",
    moonStars = "moon_stars.xml",
    filledStar = "ic_filled_star_dark.xml",
    halfFilledStar = "ic_half_filled_star_dark.xml",
    sort = "sort.xml",
    sun = "sun.xml",
    arrowDown = "arrowdown.svg",
    backgroundPattern = "background_pattern.png",
    galleryAdd = "gallery_add.xml",
    edit = "edit.xml",
    flag = "flag.xml",
    logout = "ic_logout.xml"
)
