package org.thechance.api_gateway.data.localizedMessages.languages

import org.koin.core.annotation.Single

@Single
class IraqiArabicLocalizedMessages : LocalizedMessages {

    // region identity
    override val invalidRequestParameter: String = "بيانات الطلب كلش غلط"
    override val invalidAddressLocation: String = "مكان العنوان مو صحيح"
    override val userAlreadyExist: String = "المستخدم موجود بالفعل"
    override val invalidInformation: String = "المعلومات كلش غلط"
    override val invalidFullName: String = "الإسم الكامل مو صحيح"
    override val invalidUsername: String = "اسم المستخدم مو صحيح"
    override val passwordCannotBeLessThan8Characters: String = "الرقم السري ما يقل عن ٨ حروف"
    override val usernameCannotBeBlank: String = "اسم المستخدم ما يقدر يكون فاضي"
    override val passwordCannotBeBlank: String = "الرقم السري ما يقدر يكون فاضي"
    override val invalidEmail: String = "الإيميل مو صحيح"
    override val notFound: String = "ما لقيناه"
    override val invalidCredentials: String = "بيانات الاعتماد كلش غلط"
    override val userCreatedSuccessfully: String = "المستخدم صار عندنا بنجاح 🎉"
    override val unknownError: String = "خطأ مو معروف `¯\\_(ツ)_/¯`"
    override val userNotFound: String = "المستخدم ما لقيناه"
    override val invalidPermission: String = "صلاحية كلش غلط"
    // endregion

    // region taxi
    override val taxiCreatedSuccessfully: String = "سيارة التاكسي صارت عندنا بنجاح 🎉"
    override val taxiUpdateSuccessfully: String = "سيارة التاكسي صارت محدثة بنجاح 🎉"
    override val taxiDeleteSuccessfully: String = "سيارة التاكسي صارت محذوفة بنجاح 🎉"
    // endregion

    //region restaurant
    override val restaurantNotFound: String = "عذرًا، ما لقينا هالمطعم"
    //endregion
}
