package org.thechance.api_gateway.data.localizedMessages.languages

import org.koin.core.annotation.Single

@Single
class PalestinianArabicLocalizedMessages : LocalizedMessages {

    // region identity
    override val invalidRequestParameter: String = "بيانات الطلب مش صحيحة"
    override val invalidAddressLocation: String = "ما عندك عنوان يا صاحبي"
    override val userAlreadyExist: String = "الاسم هاد موجود قبل هيك، جرب غيره"
    override val invalidInformation: String = "امسك حرامي"
    override val invalidFullName: String = "الاسم اللي دخلته مش صحيح"
    override val invalidUsername: String = "مش فاهمك، شو الاسم ده؟"
    override val passwordCannotBeLessThan8Characters: String = "الرقم السري ما يقل عن ٨ حروف"
    override val usernameCannotBeBlank: String = "شو هالاسم الفارغ؟"
    override val passwordCannotBeBlank: String = "ما عندك رقم سري؟"
    override val invalidEmail: String = "الإيميل مش صحيح"
    override val notFound: String = "ما لقيناه"
    override val invalidCredentials: String = "امسك حرامي"
    override val userCreatedSuccessfully: String = "اعمل حفلة بقى عندك حساب يا صاحب الحظ 🎉"
    override val unknownError: String = "حصل ايرور وما عرفتش شو عملت"
    override val userNotFound: String = "مش لاقينك يا صاحب"
    override val invalidPermission: String = "مش عندك الصلاحية يا صاحب"
    // endregion

    // region taxi
    override val taxiCreatedSuccessfully: String= "اعمل حفلة بقى عندك تكسي يا صاحب الحظ 🎉"
    override val taxiUpdateSuccessfully: String= "تم تحديث التاكسي بنجاح"
    override val taxiDeleteSuccessfully: String= "تم حذف التاكسي بنجاح"
    // endregion

    //region restaurant
    override val restaurantNotFound: String = "ما لقينا هالمطعم يا صاحب!!"
    //endregion
}
