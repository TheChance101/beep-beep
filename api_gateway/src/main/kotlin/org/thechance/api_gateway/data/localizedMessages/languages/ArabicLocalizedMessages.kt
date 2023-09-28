package org.thechance.api_gateway.data.localizedMessages.languages

import org.koin.core.annotation.Single

@Single
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
    override val alreadyInFavorite: String = "يوجد بالفعل في المفضله"
    // endregion

    // region taxi
    override val taxiCreatedSuccessfully: String= "تم انشاء التاكسي بنجاح"
    override val tripCreatedSuccessfully: String = "تم انشاء الرحلة"
    override val tripApproved: String = "تم قبول الرحلة"
    override val tripCanceled: String = "تم الغاء الرحلة"
    override val tripFinished: String = "تم إنهاء الرحلة بنجاح"
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
    override val restaurantCreatedSuccessfully: String = "تم إنشاء المطعم بنجاح 🥳"
    override val restaurantUpdateSuccessfully: String = "تم تحديث المطعم بنجاح 🥳"
    override val restaurantDeleteSuccessfully: String = "تم حذف المطعم بنجاح"
    override val restaurantInvalidId: String = "معرف المستخدم غير صالح"
    override val restaurantInvalidName: String = "الاسم غير صالح"
    override val restaurantInvalidLocation: String = "الموقع غير صالح"
    override val restaurantInvalidDescription: String = "الوصف غير صالح"
    override val restaurantInvalidPriceLevel: String = "مستوى التسعير غير صالح"
    override val restaurantInvalidRate: String = "التقييم غير صالح"
    override val restaurantInvalidPhone: String = "رقم الهاتف غير صالح"
    override val restaurantInvalidTime: String = "الوقت غير صالح"
    override val restaurantInvalidPage: String = "رقم الصفحة غير صالح"
    override val restaurantInvalidPageLimit: String = "اقصى عدد للصفحات غير صالح"
    override val restaurantInvalidOneOrMoreIds: String = "معرف مستخدم واحد أو أكثر غير صالح"
    override val restaurantInvalidPermissionUpdateLocation: String = "إذن تحديث الموقع غير صالح"
    override val restaurantInvalidUpdateParameter: String = "معلومات التحديث غير صالحة"
    override val restaurantInvalidPropertyRights: String = "حقوق الملكية غير صالحة"
    override val restaurantInvalidPrice: String = "السعر غير صالح"
    override val restaurantInvalidCuisineLimit: String = "اقصى عدد للمطابخ غير صالح"
    override val restaurantInvalidAddress: String = "العنوان غير صالح"
    override val restaurantInvalidEmail: String = "البريد إلكتروني غير صالح"
    override val restaurantInvalidRequestParameter: String = "معلومات الطلب غير صالحة"
    override val restaurantErrorAdd: String = "حدث خطأ في الإضافة"
    override val restaurantClosed: String = "المطعم مغلق"
    override val restaurantInsertOrderError: String = "خطأ في إضافة الطلب"
    override val restaurantInvalidReceivedOrders: String = "الطلبات الواردة غير صالحة"
    override val restaurantNotFound: String = "عذرًا، لم نتمكن من العثور على هذا المطعم"
    override val deletedSuccessfully: String = "تم الحذف بنجاح"
    override val cuisineNameAlreadyExisted: String = "هذا المطبخ موجود بالفعل "

    override val missingParameter: String = "معلومات مفقودة"
    override val tokensNotFound: String = "رمز الجهاز غير موجود"
    override val tokenNotRegister: String = "رمز الجهاز غير مسجل"
    //endregion
}