package org.thechance.api_gateway.data.localizedMessages.languages

class ArabicLocalizedMessages() : LocalizedMessages {

    // region identity
    override val invalidRequestParameter: String = "مدخلات خاطئة"
    override val invalidAddressLocation: String = "عنوان غير صالح"
    override val userAlreadyExist: String = "هذا المستخدم موجود بالفعل"
    override val invalidInformation: String = "البيانات خاطئة"
    override val invalidFullName: String = "\"اسم غير صالح بالمره )"
    override val invalidUsername: String = "اسم المستخدم غير صالح"
    override val passwordCannotBeLessThan8Characters: String = "كلمه المرور يجب ان تكون اكثر من 8 حروف"
    override val usernameCannotBeBlank: String = "اسم المستخدم لا يجب ان يكون فارغ"
    override val passwordCannotBeBlank: String = "كلمه المرور لا يجب ان تكون فارغة"
    override val invalidEmail: String = "ايميل غير صالح"
    override val notFound: String = "غير موجود"
    override val invalidCredentials: String = "البيانات خاطئة"
    override val userCreatedSuccessfully: String = ""
    override val unknownError: String = "حصل خطأ زي حياتك يا 56"
    override val userNotFound: String = "المستخدم غير موجود"
    override val invalidPermission: String = "ليس لديك الصلاحية للقيام بهذا الامر"
    // endregion

    // region taxi
    override val taxiCreatedSuccessfully: String= "تم انشاء التاكسي بنجاح"
    override val taxiUpdateSuccessfully: String= "تم تحديث التاكسي بنجاح"
    override val taxiDeleteSuccessfully: String= "تم حذف التاكسي بنجاح"
    override val invalidId: String = "رقم بطاقة غير صالح"
    override val invalidPlate: String = "رقم لوحة غير صالح"
    override val invalidColor: String = "لون غير صالح"
    override val invalidCarType: String = "نوع السيارة غير صالح"
    override val seatOutOfRange: String = "عدد المقاعد غير صالح"
    override val invalidLocation: String = "موقع غير صالح"
    override val invalidRate: String = "تقييم غير صالح"
    override val invalidDate: String = "تاريخ غير صالح"
    override val invalidPrice: String = "سعر غير صالح"
    override val alreadyExist: String = "موجود بالفعل"
    override val requiredQuery: String = "المدخل مطلوب "
    // endregion

    //region restaurant
    override val restaurantNotFound: String = "نعتذر لم نستطيع ايجاد هذا المطعم!"
    //endregion
}