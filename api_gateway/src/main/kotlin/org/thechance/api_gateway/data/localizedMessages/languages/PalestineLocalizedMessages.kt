package org.thechance.api_gateway.data.localizedMessages.languages

import org.koin.core.annotation.Single

@Single
class PalestineLocalizedMessages : LocalizedMessages {
    override val invalidFullName: String = "اكتب اسمك كامل افتخر بنفسك"
    override val invalidUsername: String = "فين اسمك يا 56"
    override val passwordCannotBeLessThan8Characters: String = "خلي الباسوورد اكبر من 8 عشان متتسرقش وتعيط"
    override val usernameCannotBeBlank: String = "اييشش هااضض"
    override val passwordCannotBeBlank: String = "هتخلي الباسوورد شفاف كمان؟"
    override val invalidEmail: String = "دا مش ايميل يا 56"
    override val invalidCredentials: String = "امسك 56"
    override val notFound: String = "شفاف 404"
    override val invalidRequestParameter: String = "الباراميتر دا مش موجود يا 56"
    override val invalidAddressLocation: String = "اللوكيشن في مثلث برمودا"
    override val userAlreadyExist: String = "اليوزر دا موجود من قبل يا 56"
    override val invalidInformation: String = "البيانات دي مش صح يا 56"
    override val unknownError: String = "حدث خطأ غير معروف"
    override val userNotFound: String = "اليوزر دا مش موجود يا 56"
    override val userCreatedSuccessfully: String = "شرفتنا يا 56"
    override val invalidPermission: String = "مش عندك الصلاحية"
    override val taxiCreatedSuccessfully: String = "   شرفتنا يا صاحبي"
    override val taxiUpdateSuccessfully: String = "   عدلنا معلومات التاكسي يا صاحبي"
    override val taxiDeleteSuccessfully: String = "   حذفنا التاكسي يا صاحبي"

    //region restaurant
    override val restaurantNotFound: String = "نعتذر لم نستطيع ايجاد هذا المطعم!"
    //endregion
}