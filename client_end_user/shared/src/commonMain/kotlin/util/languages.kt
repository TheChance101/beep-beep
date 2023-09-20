package util

import presentation.pickLanguage.LanguageUIState

val langs: List<LanguageUIState> = listOf(

    LanguageUIState(
        name = "عربي",
        code = LanguageCode.AR.value,
        image = "ar.jpg"
    ),
    LanguageUIState(
        name = "English",
        code = LanguageCode.EN.value,
        image = "en.jpg"
    ),
    LanguageUIState(
        name = "فلسطيني",
        code = LanguageCode.PS.value,
        image = "ps.jpg"
    ),
    LanguageUIState(
        name = "عراقي",
        code = LanguageCode.IQ.value,
        image = "iq.jpg"
    ),
    LanguageUIState(
        name = "سوري",
        code = LanguageCode.SY.value,
        image = "sy.jpg"
    ),
    LanguageUIState(
        name = "مصري",
        code = LanguageCode.EG.value,
        image = "eg.jpg"
    ),
)
