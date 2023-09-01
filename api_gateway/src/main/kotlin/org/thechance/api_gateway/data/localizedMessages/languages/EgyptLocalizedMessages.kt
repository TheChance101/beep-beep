package org.thechance.api_gateway.data.localizedMessages.languages

class EgyptLocalizedMessages : LocalizedMessages {
    override val invalidRequestParameter: String = "مدخل بيانات غلط )\""
    override val invalidAddressLocation: String = "مش عارف عنوانك !!"
    override val userAlreadyExist: String = "الاسم ده موجود قبل كده, شوفلك غيره"
    override val invalidInformation: String = "امسك حرامي"
    override val invalidFullName: String = " الاسم اللي دخلته مينفعش مينفعش"
    override val invalidUsername: String = "انت شفاف؟"
    override val passwordCannotBeLessThan8Characters: String = "بالذمه شوفت باسورد اقل من 8 حروف !!"
    override val usernameCannotBeBlank: String = "في اي يسطا"
    override val passwordCannotBeBlank: String = "فين الباسورد!! هنخم؟"
    override val invalidEmail: String = "الايميل يابشمهندس !!"
    override val notFound: String = "مش موجود ياعم"
    override val invalidCredentials: String = "امسك حرامي"
    override val userCreatedSuccessfully: String = "اعمل حفلة بقى عندك حساب يا ابن المحظوظة"
    override val unknownError: String = "حصل ايرور لو كانت عارفه كنت هقولك"
    override val userNotFound: String = "مش لاقينك ياعم"
    override val invalidPermission: String = "مش عندك الصلاحية ياعم"
    override val taxiCreatedSuccessfully: String= "اعمل حفلة بقى عندك تكسي يا ابن المحظوظة"
    override val taxiUpdateSuccessfully: String= "تم تحديث التاكسي بنجاح"
    override val taxiDeleteSuccessfully: String= "تم حذف التاكسي بنجاح"

    //region restaurant
    override val restaurantNotFound: String = "مش لاقيين المطعم ده!!"
    //endregion
}