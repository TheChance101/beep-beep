package org.thechance.api_gateway.data.localizedMessages.languages

import org.koin.core.annotation.Single

@Single
class PalestinianArabicLocalizedMessages(
) : LocalizedMessages {

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
    override val alreadyInFavorite: String= "بالفعل موجود في المفضله"

    // endregion

    // region taxi
    override val taxiCreatedSuccessfully: String = "اعمل حفلة بقى عندك تكسي يا صاحب الحظ 🎉"
    override val taxiUpdateSuccessfully: String = "تم تحديث التاكسي بنجاح"
    override val taxiDeleteSuccessfully: String = "تم حذف التاكسي بنجاح"
    override val invalidId: String = "هاد التاكسي مش عنده رقم صحيح!"
    override val invalidPlate: String = "هاد التاكسي مش عنده رقم لوحة صحيح!"
    override val invalidColor: String = "هاد التاكسي مش عنده لون صحيح!"
    override val invalidCarType: String = "هاد التاكسي مش عنده نوع سيارة صحيح!"
    override val seatOutOfRange: String = "هاد التاكسي مش عنده عدد كراسي صحيح!"
    override val invalidLocation: String = "المكان اللي دخلته مش صح!"
    override val invalidRate: String = "التقييم اللي دخلته مش صح!"
    override val invalidDate: String = "التاريخ اللي دخلته مش صح!"
    override val invalidPrice: String = "السعر اللي دخلته مش صح!"
    override val alreadyExist: String = "هاد التاكسي موجود من قبل يا زلمة!"
    override val requiredQuery: String = "في معلومات مفقودة يا زلمة!"

    // endregion

    //region restaurant
    override val restaurantCreatedSuccessfully: String = "صار عندك مطعم جديد"
    override val restaurantUpdateSuccessfully: String = "حدثنالك بيانات المطعم"
    override val restaurantDeleteSuccessfully: String = "المطعم انحذف"
    override val restaurantInvalidId: String = "الآي دي هاد مش صح"
    override val restaurantInvalidName: String = "الاسم مش صح"
    override val restaurantInvalidLocation: String = "العنوان مش صح"
    override val restaurantInvalidDescription: String = "الوصف لهاد المطعم مش صح"
    override val restaurantInvalidPriceLevel: String = "تسعير المطعم مش مناسب"
    override val restaurantInvalidRate: String = "تقييم المطعم مش مناسب"
    override val restaurantInvalidPhone: String = "رقم الموبايل مش صح"
    override val restaurantInvalidTime: String = "الوقت يلي دخلته مش صح"
    override val restaurantInvalidPage: String = "رقم الصفحة مش مناسب"
    override val restaurantInvalidPageLimit: String = "عدد الصفحات مش مناسب"
    override val restaurantInvalidOneOrMoreIds: String = "آي دي واحد أو أكثر غلط"
    override val restaurantInvalidPermissionUpdateLocation: String = "إذن تحديث الموقع مش صالح"
    override val restaurantInvalidUpdateParameter: String = "بيانات التحديث هادي ما مناسبة"
    override val restaurantInvalidPropertyRights: String = "ما عندك حقوق ملكية"
    override val restaurantInvalidPrice: String = "السعر مش مناسب"
    override val restaurantInvalidCuisineLimit: String = "عدد المطاعم مش مناسب"
    override val restaurantInvalidAddress: String = "العنوان مش مناسب"
    override val restaurantInvalidEmail: String = "ايميلك مش صح"
    override val restaurantInvalidRequestParameter: String = "بيانات طلبك مش صح"
    override val restaurantErrorAdd: String = "حصل ايرور في الإضافة"
    override val restaurantClosed: String = "المطعم مقفول"
    override val restaurantInsertOrderError: String = "حصل مشكلة في إضافة الأوردر"
    override val restaurantInvalidReceivedOrders: String = "الأوردر اللي وصلنا مش صالح"
    override val restaurantNotFound: String = "ما لقينا هالمطعم يا صاحب!!"
    override val deletedSuccessfully: String="انحذف يخوو"
    override val cuisineNameAlreadyExisted: String = "المطبخ موجود قبل كدا يا حبيبى"
    //endregion
}
