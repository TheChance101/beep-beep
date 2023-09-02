package org.thechance.api_gateway.data.localizedMessages.languages

import org.koin.core.annotation.Single

@Single
class IraqiArabicLocalizedMessages() : LocalizedMessages {

    // region identity
    override val invalidRequestParameter: String = "بيانات الطلب كلش غلط"
    override val invalidAddressLocation: String = "مكان العنوان مو صحيح"
    override val userAlreadyExist: String = "المستخدم موجود بالفعل"
    override val invalidInformation: String = "المعلومات كلش غلط"
    override val invalidFullName: String = "الإسم الكامل مو صحيح"
    override val invalidUsername: String = "اسم المستخدم مو صحيح"
    override val passwordCannotBeLessThan8Characters: String = "الرقم السري ما يقل عن ٨ حروف"
    override val usernameCannotBeBlank: String = "اسم المستخدم ميصير يكون فارغ"
    override val passwordCannotBeBlank: String = "الرقم السري ميصير يكون فارغ"
    override val invalidEmail: String = "الإيميل مو صحيح"
    override val notFound: String = "ما موجود"
    override val invalidCredentials: String = "بيانات الاعتماد كلش غلط"
    override val userCreatedSuccessfully: String = "المستخدم صار عندنا بنجاح 🎉"
    override val unknownError: String = "خطأ مو معروف `¯\\_(ツ)_/¯`"
    override val userNotFound: String = "المستخدم  ما موجود"
    override val invalidPermission: String = "صلاحية كلش غلط"
    // endregion

    // region taxi
    override val taxiCreatedSuccessfully: String = "سيارة التاكسي صارت عندنا بنجاح 🎉"
    override val taxiUpdateSuccessfully: String = "سيارة التاكسي صارت محدثة بنجاح 🎉"
    override val taxiDeleteSuccessfully: String = "سيارة التاكسي صارت محذوفة بنجاح 🎉"
    override val invalidId: String = "الايدي مالتك مو صحيح"
    override val invalidPlate: String = "رقم اللوحة مو صحيح"
    override val invalidColor: String = "لون السيارة مو صحيح"
    override val invalidCarType: String = "نوع السيارة مو صحيح"
    override val seatOutOfRange: String = "عدد المقاعد مو صحيح"
    override val invalidLocation: String = "الموقع   مو صحيح"
    override val invalidRate: String = "التقييم مو صحيح"
    override val invalidDate: String = "التاريخ مو صحيح"
    override val invalidPrice: String = "السعر مو صحيح"
    override val alreadyExist: String = " موجود اصلا"
    override val requiredQuery: String= " عندك نقص بالمعلومات حجي"
    // endregion

    //region restaurant
    override val restaurantNotFound: String = "عيني مطعم لتريده ما موجود"
    //endregion
}
