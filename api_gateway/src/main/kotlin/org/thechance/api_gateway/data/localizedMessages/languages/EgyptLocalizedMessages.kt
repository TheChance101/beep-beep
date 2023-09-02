package org.thechance.api_gateway.data.localizedMessages.languages

import org.koin.core.annotation.Single

@Single
class EgyptianArabicLocalizedMessages() : LocalizedMessages {

    // region identity
    override val invalidRequestParameter: String = "مدخل بيانات غلط يا باشا"
    override val invalidAddressLocation: String = "مش عارف عنوانك يا زميل"
    override val userAlreadyExist: String = "الاسم ده موجود قبل كده، شوفلك غيره"
    override val invalidInformation: String = "امسك حرامي"
    override val invalidFullName: String = "الاسم اللي دخلته مينفعش يا صاحبي"
    override val invalidUsername: String = "انت شفاف يا باشا؟"
    override val passwordCannotBeLessThan8Characters: String = "بالذمة شفت باسورد أقل من 8 حروف يا كبير"
    override val usernameCannotBeBlank: String = "في اي يسطا"
    override val passwordCannotBeBlank: String = "فين الباسورد!! هنخم؟"
    override val invalidEmail: String = "الايميل يسطا!!"
    override val notFound: String = "مش موجود ياعم"
    override val invalidCredentials: String = "امسك حرامي"
    override val userCreatedSuccessfully: String = "اعمل حفلة بقى عندك حساب يا ابن المحظوظة 🎉"
    override val unknownError: String = "حصل ايرور لو كانت عارفه كنت هقولك"
    override val userNotFound: String = "مش لاقينك ياعم"
    override val invalidPermission: String = "مش عندك الصلاحية ياعم"
    // endregion

    // region taxi
    override val taxiCreatedSuccessfully: String= "اعمل حفلة بقى عندك تكسي يا ابن المحظوظة 🎉"
    override val taxiUpdateSuccessfully: String= "حدثنالك التاكسي يا عم 🎉"
    override val taxiDeleteSuccessfully: String= "مسحنالك التاكسي مع الاسف"
    override val invalidId: String = "مش لاقيين التاكسي ده!!"
    override val invalidPlate: String = "الرقم اللي دخلته مش مظبوط يعم"
    override val invalidColor: String = "مش مظبوط اللون اللي دخلته يعم"
    override val invalidCarType: String = "مش مظبوط نوع العربية اللي دخلته يعم"
    override val seatOutOfRange: String = "مش مظبوط عدد المقاعد اللي دخلته يعم"
    override val invalidLocation: String = "مش مظبوط المكان اللي دخلته يعم"
    override val invalidRate: String = "مش مظبوط التقييم اللي دخلته  يعم"
    override val invalidDate: String = "مش مظبوط التاريخ اللي دخلته يعم"
    override val invalidPrice: String = "مش مظبوط السعر اللي دخلته يعم"
    override val alreadyExist: String = "التاكسي ده موجود قبل كده ياعم"
    override val requiredQuery: String = " في حاجات ناقصة يا معلم"
    // endregion

    //region restaurant
    override val restaurantNotFound: String = "مش لاقيين المطعم ده!!"
    //endregion
}
