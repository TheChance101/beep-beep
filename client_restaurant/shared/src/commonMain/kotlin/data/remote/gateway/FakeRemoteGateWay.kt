package data.remote.gateway

import data.remote.model.AddressDto
import data.remote.model.CuisineDto
import data.remote.model.MealDto
import data.remote.model.OrderDto
import data.remote.model.OrderMealDto
import data.remote.model.RestaurantDto
import data.remote.model.toEntity
import data.remote.model.toOrderEntity
import domain.entity.Category
import domain.entity.Cuisine
import domain.entity.Meal
import domain.entity.Order
import domain.entity.Restaurant
import domain.gateway.IRemoteGateWay
import presentation.base.RequestException

class FakeRemoteGateWay : IRemoteGateWay {


    private val orders = mutableListOf(
        OrderDto(
            id = "f5c8b31e-5c4d-4c8a-babc-0e9463daad20",
            userId = "550e8400-e29b-41d4-a716-446655440989",
            restaurantId = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            meals = listOf(
                OrderMealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://takethemameal.com/files_images_v2/stam.jpg",
                    quantity = 2,
                    mealName = "Pasta"
                ),
                OrderMealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://img.taste.com.au/k1UFa9O6/w720-h480-cfill-q80/taste/2022/12/one-pan-piri-piri-chicken-183821-2.jpg",
                    quantity = 2,
                    mealName = "Breakfast burrito"
                )
            ),
            totalPrice = 22.74,
            createdAt = "2022-05-16T08:23",
            orderState = 0
        ),
        OrderDto(
            id = "4d7bdc9b-6233-44ef-80a0-6a09ef856862",
            userId = "8a430be2-15b7-47f5-9e06-3f236f8c25ec",
            restaurantId = "7c3d631e-6d49-48c9-9f91-9426ec559eb1",
            meals = listOf(
                OrderMealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://takethemameal.com/files_images_v2/stam.jpg",
                    quantity = 4,
                    mealName = "Pancake stack"
                ),
            ),
            totalPrice = 28.48,
            createdAt = "2022-05-16T08:23",
            orderState = 2
        ),
        OrderDto(
            id = "891ecf91-62bf-4d91-96bf-8d4cc8271a81",
            userId = "f26dab15-7193-4e8d-bf6e-f4d2ae8799af",
            restaurantId = "91c2ae1f-8495-4c0c-bc47-7bf7ef77d907",
            meals = listOf(
                OrderMealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://takethemameal.com/files_images_v2/stam.jpg",
                    quantity = 1,
                    mealName = "Lunch salad"
                ),
                OrderMealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://takethemameal.com/files_images_v2/stam.jpg",
                    quantity = 1,
                    mealName = "Afternoon tea sandwich"
                ),
                OrderMealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://takethemameal.com/files_images_v2/stam.jpg",
                    quantity = 3,
                    mealName = "Dinner party"
                )
            ),
            totalPrice = 100.49,
            createdAt = "2022-05-16T08:23",
            orderState = 3
        ),
        OrderDto(
            id = "f5c8b31e-5c4d-4c8a-babc-0e9463daad20",
            userId = "550e8400-e29b-41d4-a716-446655440989",
            restaurantId = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            meals = listOf(
                OrderMealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://takethemameal.com/files_images_v2/stam.jpg",
                    quantity = 3,
                    mealName = "Supper club"
                ),
            ),
            totalPrice = 22.74,
            createdAt = "2022-05-16T08:23",
            orderState = 1
        ),
        OrderDto(
            id = "4d7bdc9b-6233-44ef-80a0-6a09ef856862",
            userId = "8a430be2-15b7-47f5-9e06-3f236f8c25ec",
            restaurantId = "7c3d631e-6d49-48c9-9f91-9426ec559eb1",
            meals = listOf(
                OrderMealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://img.taste.com.au/k1UFa9O6/w720-h480-cfill-q80/taste/2022/12/one-pan-piri-piri-chicken-183821-2.jpg",
                    quantity = 3,
                    mealName = "Light snack"
                ),
                OrderMealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://takethemameal.com/files_images_v2/stam.jpg",
                    quantity = 1,
                    mealName = "Hors d'oeuvre platter"
                ),
                OrderMealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://images.immediate.co.uk/production/volatile/sites/2/2017/09/OLI1017-Healthy_ChimmiChurriChicken_014545.jpg?quality=90&resize=700,466",
                    quantity = 3,
                    mealName = "Appetizer sampler"
                ),
                OrderMealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://ichef.bbci.co.uk/food/ic/food_16x9_832/recipes/sausage_and_lentil_stew_90967_16x9.jpg",
                    quantity = 4,
                    mealName = "Pasta"
                )
            ),
            totalPrice = 300.5,
            createdAt = "2022-05-16T08:23",
            orderState = 1
        ),
        OrderDto(
            id = "891ecf91-62bf-4d91-96bf-8d4cc8271a81",
            userId = "f26dab15-7193-4e8d-bf6e-f4d2ae8799af",
            restaurantId = "91c2ae1f-8495-4c0c-bc47-7bf7ef77d907",
            meals = listOf(
                OrderMealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://takethemameal.com/files_images_v2/stam.jpg",
                    quantity = 2,
                    mealName = "Pasta"
                ),
                OrderMealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://img.taste.com.au/k1UFa9O6/w720-h480-cfill-q80/taste/2022/12/one-pan-piri-piri-chicken-183821-2.jpg",
                    quantity = 2,
                    mealName = "Main course dish"
                )
            ),
            totalPrice = 26.49,
            createdAt = "2022-05-16T08:23",
            orderState = 1
        ),
        OrderDto(
            id = "d59b00c3-923c-4cf4-bd0e-3a4c997a3156",
            userId = "550e8400-e29b-41d4-a716-446655440989",
            restaurantId = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            meals = listOf(
                OrderMealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://takethemameal.com/files_images_v2/stam.jpg",
                    quantity = 2,
                    mealName = "Dessert pizza"
                ),
                OrderMealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://takethemameal.com/files_images_v2/stam.jpg",
                    quantity = 2,
                    mealName = "Pasta"
                )
            ),
            totalPrice = 20.73,
            createdAt = "2022-05-16T08:23",
            orderState = 2
        ),
        OrderDto(
            id = "9e94fdd9-9cbf-4b7e-a97e-8ea31c4876b2",
            userId = "8a430be2-15b7-47f5-9e06-3f236f8c25ec",
            restaurantId = "7c3d631e-6d49-48c9-9f91-9426ec559eb1",
            meals = listOf(
                OrderMealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://thecarefreekitchen.com/wp-content/uploads/2021/10/Buffalo-Chicken-Taquitos-1024x1024.jpg",
                    quantity = 2,
                    mealName = "Digestif cocktail"
                ),
                OrderMealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://www.freshnlean.com/wp-content/uploads/2021/03/Meal-Plan-plate-protein.png",
                    quantity = 1,
                    mealName = "Pasta"
                )
            ),
            totalPrice = 41.96,
            createdAt = "2022-05-16T08:23",
            orderState = 1
        ),
        OrderDto(
            id = "c07d45e5-4c5d-4847-a518-8f21c66620f9",
            userId = "f26dab15-7193-4e8d-bf6e-f4d2ae8799af",
            restaurantId = "91c2ae1f-8495-4c0c-bc47-7bf7ef77d907",
            meals = listOf(
                OrderMealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://takethemameal.com/files_images_v2/stam.jpg",
                    quantity = 2,
                    mealName = "Dinner party"
                )
            ),
            totalPrice = 35.98,
            createdAt = "2022-05-16T08:23",
            orderState = 0
        ),
        OrderDto(
            id = "d59b00c3-923c-4cf4-bd0e-3a4c997a3156",
            userId = "550e8400-e29b-41d4-a716-446655440989",
            restaurantId = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            meals = listOf(
                OrderMealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://www.foodandwine.com/thmb/bRz199ONebY-5h5gcvpOcHRxAkA=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/Web_4000-Trifecta-Chicken-Breast-Sweet-Potato-Mixed-Vegetable_04-72a24aaee5584c06a26451603daec5c9.jpg",
                    quantity = 2,
                    mealName = "Pasta"
                ),
                OrderMealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://takethemameal.com/files_images_v2/stam.jpg",
                    quantity = 2,
                    mealName = "Dinner party"
                )
            ),
            totalPrice = 20.73,
            createdAt = "2022-05-16T08:23",
            orderState = 0
        ),
        OrderDto(
            id = "9e94fdd9-9cbf-4b7e-a97e-8ea31c4876b2",
            userId = "8a430be2-15b7-47f5-9e06-3f236f8c25ec",
            restaurantId = "7c3d631e-6d49-48c9-9f91-9426ec559eb1",
            meals = listOf(
                OrderMealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://cdn.apartmenttherapy.info/image/upload/v1558635037/k/archive/388e39a6ca67e257cb7bef6bde6a98aef1bcd434.jpg",
                    quantity = 1,
                    mealName = "Supper club"
                ),
                OrderMealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://images.immediate.co.uk/production/volatile/sites/30/2022/07/Fajita-style-pasta-f792c52.jpg?quality=90&resize=440,400",
                    quantity = 2,
                    mealName = "Pasta"
                ),
                OrderMealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://www.thelazydish.com/wp-content/uploads/2022/05/cheap-easy-dinner-recipes-for-family-with-kids-from-the-lazy-dish.jpg",
                    quantity = 3,
                    mealName = "Appetizer sampler"
                )
            ),
            totalPrice = 41.96,
            createdAt = "2022-05-16T08:23",
            orderState = 1
        ),
        OrderDto(
            id = "c07d45e5-4c5d-4847-a518-8f21c66620f9",
            userId = "f26dab15-7193-4e8d-bf6e-f4d2ae8799af",
            restaurantId = "91c2ae1f-8495-4c0c-bc47-7bf7ef77d907",
            meals = listOf(
                OrderMealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://ichef.bbci.co.uk/food/ic/food_16x9_832/recipes/roast_chicken_for_one_41998_16x9.jpg",
                    quantity = 2,
                    mealName = "Pasta"
                ),
                OrderMealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://takethemameal.com/files_images_v2/stam.jpg",
                    quantity = 2,
                    mealName = "Pasta"
                )
            ),
            totalPrice = 35.98,
            createdAt = "2022-05-16T08:23",
            orderState = 1
        ),
        OrderDto(
            id = "1a2b3c4d-5e6f-7a8b-9c0d-e1f2g3h4i5j6",
            userId = "550e8400-e29b-41d4-a716-446655440989",
            restaurantId = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            meals = listOf(
                OrderMealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://takethemameal.com/files_images_v2/stam.jpg",
                    quantity = 2,
                    mealName = "Pasta"
                ),
                OrderMealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://ichef.bbci.co.uk/food/ic/food_16x9_832/recipes/roast_chicken_for_one_41998_16x9.jpg",
                    quantity = 2,
                    mealName = "Digestif cocktail"
                )
            ),
            totalPrice = 28.45,
            createdAt = "2022-05-16T08:23",
            orderState = 0
        ),
        OrderDto(
            id = "2b3c4d5e-6f7a-8b9c-0d1e-2f3g4h5i6j7",
            userId = "8a430be2-15b7-47f5-9e06-3f236f8c25ec",
            restaurantId = "7c3d631e-6d49-48c9-9f91-9426ec559eb1",
            meals = listOf(
                OrderMealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://takethemameal.com/files_images_v2/stam.jpg",
                    quantity = 2,
                    mealName = "Pasta"
                ),
                OrderMealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://www.deliciouslycleaneats.com.au/wp-content/uploads/2018/08/Meal-Plan-Spread1.jpg",
                    quantity = 2,
                    mealName = "Pasta"
                )
            ),
            totalPrice = 23.99,
            createdAt = "2022-05-16T08:23",
            orderState = 4
        ),
    )

    private val meals = mutableListOf(
        MealDto(
            id = "e5b1a329-6f3a-4d63-bb7f-895f1e1c2f9a",
            restaurantId = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            name = "Hummus Platter",
            imageUrl = "https://www.deliciouslycleaneats.com.au/wp-content/uploads/2018/08/Meal-Plan-Spread1.jpg",
            description = "A delicious platter of hummus served with pita bread.",
            price = 8.99,
            cuisines = listOf(
                "e5b1a329-6f3a-4d63-bb7f-895f1e1c2f9a",
                "ba9b9700-6d24-434b-8d67-daf9e45e1063",
                "ba9b9700-6d24-434b-8d67-daf9e45e1065",
                "6ab493b4-4b8d-410a-a13e-780346243f3a"
            )

        ),
        MealDto(
            id = "f8d21b6d-49d1-43eb-932c-5a30a5914d78",
            restaurantId = "7c3d631e-6d49-48c9-9f91-9426ec559eb1",
            name = "Spicy Tuna Roll",
            imageUrl = "https://ichef.bbci.co.uk/food/ic/food_16x9_832/recipes/roast_chicken_for_one_41998_16x9.jpg",
            description = "A spicy roll made with fresh tuna and spicy mayo.",
            price = 12.49,
            cuisines = listOf(
                "e5b1a329-6f3a-4d63-bb7f-895f1e1c2f9a",
                "ba9b9700-6d24-434b-8d67-daf9e45e1064",
                "ba9b9700-6d24-434b-8d67-daf9e45e1066",
                "6ab493b4-4b8d-410a-a13e-780346243f3a"
            )
        ),
        MealDto(
            id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
            restaurantId = "91c2ae1f-8495-4c0c-bc47-7bf7ef77d907",
            name = "Beignet Sampler",
            imageUrl = "https://realfood.tesco.com/media/images/472x310-Teriyaki-glazed-sausages-6c3c4a03-b353-49c9-85a3-978f326ba592-0-472x310.jpg",
            description = "A delightful sampler of beignets with different toppings.",
            price = 9.99,
            cuisines = listOf(
                "e5b1a329-6f3a-4d63-bb7f-895f1e1c2f9a",
                "ba9b9700-6d24-434b-8d67-daf9e45e1063",
                "ba9b9700-6d24-434b-8d67-daf9e45e1065",
                "ba9b9700-6d24-434b-8d67-daf9e45e1066"
            )
        ),
        MealDto(
            id = "4d1c8f5e-7f24-4df3-9835-06f0d63f8eb1",
            restaurantId = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            name = "Falafel Wrap",
            imageUrl = "https://img.taste.com.au/k1UFa9O6/w720-h480-cfill-q80/taste/2022/12/one-pan-piri-piri-chicken-183821-2.jpg",
            description = "A wrap filled with crispy falafel balls and fresh veggies.",
            price = 7.95,
            cuisines = listOf(
                "e5b1a329-6f3a-4d63-bb7f-895f1e1c2f9a",
                "ba9b9700-6d24-434b-8d67-daf9e45e1064",
                "ba9b9700-6d24-434b-8d67-daf9e45e1065",
                "6ab493b4-4b8d-410a-a13e-780346243f3a"
            )
        ),
        MealDto(
            id = "8a2a4387-1cc2-4b68-9df1-3f497ddc94a2",
            restaurantId = "7c3d631e-6d49-48c9-9f91-9426ec559eb1",
            name = "Salmon Nigiri",
            imageUrl = "https://i.pinimg.com/1200x/b8/7b/c7/b87bc72f970fe00c115e3d1471956c4d.jpg",
            description = "Fresh salmon slices on bite-sized beds of seasoned rice.",
            price = 10.99,
            cuisines = listOf(
                "e5b1a329-6f3a-4d63-bb7f-895f1e1c2f9a",
                "ba9b9700-6d24-434b-8d67-daf9e45e1063",
                "6ab493b4-4b8d-410a-a13e-780346243f3c",
                "ba9b9700-6d24-434b-8d67-daf9e45e1066"
            )
        ),
        MealDto(
            id = "a1ebe83d-617a-4e14-9e27-4d0367c4e0d2",
            restaurantId = "91c2ae1f-8495-4c0c-bc47-7bf7ef77d907",
            name = "Gumbo",
            imageUrl = "https://www.southernliving.com/thmb/iL2CEgCAMqC4cpp6taRqwYQI1gs=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/27162_FGFsuperbowl_0359_16x9-2000-5dd253dc23044ee78aacd9673f5befbc.jpg",
            description = "A hearty stew with a mix of meats, seafood, and vegetables.",
            price = 14.75,
            cuisines = listOf(
                "e5b1a329-6f3a-4d63-bb7f-895f1e1c2f9a",
                "ba9b9700-6d24-434b-8d67-daf9e45e1064",
                "6ab493b4-4b8d-410a-a13e-780346243f3c",
                "ba9b9700-6d24-434b-8d67-daf9e45e1065",
                "ba9b9700-6d24-434b-8d67-daf9e45e1066"
            )
        ),
        MealDto(
            id = "ba9b9700-6d24-434b-8d67-daf9e45e1063",
            restaurantId = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            name = "Shawarma Plate",
            imageUrl = "https://media.theeverygirl.com/wp-content/uploads/2022/05/healthy-meal-prep-dinners-teg-new-gallery.jpeg",
            description = "A plate of mouthwatering shawarma served with garlic sauce.",
            price = 13.75,
            cuisines = listOf(
                "e5b1a329-6f3a-4d63-bb7f-895f1e1c2f9a",
                "ba9b9700-6d24-434b-8d67-daf9e45e1063",
                "6ab493b4-4b8d-410a-a13e-780346243f3c",
                "ba9b9700-6d24-434b-8d67-daf9e45e1065",
                "ba9b9700-6d24-434b-8d67-daf9e45e1066"
            )
        ),
        MealDto(
            id = "2d5bbf8a-4854-49c6-99ed-ef09899c2d8e",
            restaurantId = "7c3d631e-6d49-48c9-9f91-9426ec559eb1",
            name = "Dragon Roll",
            imageUrl = "https://www.foodandwine.com/thmb/bRz199ONebY-5h5gcvpOcHRxAkA=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/Web_4000-Trifecta-Chicken-Breast-Sweet-Potato-Mixed-Vegetable_04-72a24aaee5584c06a26451603daec5c9.jpg",
            description = "An exquisite roll with eel, avocado, and a sweet soy glaze.",
            price = 15.99,
            cuisines = listOf(
                "e5b1a329-6f3a-4d63-bb7f-895f1e1c2f9a",
                "ba9b9700-6d24-434b-8d67-daf9e45e1064",
                "ba9b9700-6d24-434b-8d67-daf9e45e1066"
            )
        ),
        MealDto(
            id = "e772ad66-0251-412f-99a1-4a10435f9a07",
            restaurantId = "91c2ae1f-8495-4c0c-bc47-7bf7ef77d907",
            name = "Jambalaya",
            imageUrl = "https://takethemameal.com/files_images_v2/stam.jpg",
            description = "A spicy Cajun dish with rice, sausage, chicken, and shrimp.",
            price = 16.50,
            cuisines = listOf(
                "e5b1a329-6f3a-4d63-bb7f-895f1e1c2f9a",
                "6ab493b4-4b8d-410a-a13e-780346243f3c",
                "ba9b9700-6d24-434b-8d67-daf9e45e1063",
                "ba9b9700-6d24-434b-8d67-daf9e45e1065"
            )
        )
    )

    private val restaurants = listOf(
        RestaurantDto(
            id = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            ownerId = "550e8400-e29b-41d4-a716-446655440989",
            ownerUsername = "@Zeko",
            name = "Zeko Tlawoth",
            description = "It's a restaurant that is famous for preparing Arabian Foods",
            rate = 4.5,
            phone = "+9641324221423",
            openingTime = "09:00",
            closingTime = "22:00",
            priceLevel = "$$$",
            address = AddressDto(latitude = 31.0285807, longitude = 38.2588888)
        ),
        RestaurantDto(
            id = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            ownerId = "550e8400-e29b-41d4-a716-446655440989",
            name = "Zeko Tlawoth",
            description = "It's a restaurant that is famous for preparing Arabian Foods",
            rate = 4.5,
            phone = "+9641324221423",
            openingTime = "09:00",
            closingTime = "22:00",
            address = AddressDto(latitude = 31.0285807, longitude = 38.2588888)
        ), RestaurantDto(
            id = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            ownerId = "550e8400-e29b-41d4-a716-446655440989",
            name = "Zeko Tlawoth",
            description = "It's a restaurant that is famous for preparing Arabian Foods",
            rate = 4.5,
            phone = "+9641324221423",
            openingTime = "09:00",
            closingTime = "22:00",
            address = AddressDto(latitude = 31.0285807, longitude = 38.2588888)
        ),
        RestaurantDto(
            id = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            ownerId = "550e8400-e29b-41d4-a716-446655440989",
            name = "Zeko Tlawoth",
            description = "It's a restaurant that is famous for preparing Arabian Foods",
            rate = 4.5,
            phone = "+9641324221423",
            openingTime = "09:00",
            closingTime = "22:00",
            address = AddressDto(latitude = 31.0285807, longitude = 38.2588888)
        ), RestaurantDto(
            id = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            ownerId = "550e8400-e29b-41d4-a716-446655440989",
            name = "Zeko Tlawoth",
            description = "It's a restaurant that is famous for preparing Arabian Foods",
            rate = 4.5,
            phone = "+9641324221423",
            openingTime = "09:00",
            closingTime = "22:00",
            address = AddressDto(latitude = 31.0285807, longitude = 38.2588888)
        ), RestaurantDto(
            id = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            ownerId = "550e8400-e29b-41d4-a716-446655440989",
            name = "Zeko Tlawoth",
            description = "It's a restaurant that is famous for preparing Arabian Foods",
            rate = 4.5,
            phone = "+9641324221423",
            openingTime = "09:00",
            closingTime = "22:00",
            address = AddressDto(latitude = 31.0285807, longitude = 38.2588888)
        ), RestaurantDto(
            id = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            ownerId = "550e8400-e29b-41d4-a716-446655440989",
            name = "Zeko Tlawoth",
            description = "It's a restaurant that is famous for preparing Arabian Foods",
            rate = 4.5,
            phone = "+9641324221423",
            openingTime = "09:00",
            closingTime = "22:00",
            address = AddressDto(latitude = 31.0285807, longitude = 38.2588888)
        ), RestaurantDto(
            id = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            ownerId = "550e8400-e29b-41d4-a716-446655440989",
            name = "Zeko Tlawoth",
            description = "It's a restaurant that is famous for preparing Arabian Foods",
            rate = 4.5,
            phone = "+9641324221423",
            openingTime = "09:00",
            closingTime = "22:00",
            address = AddressDto(latitude = 31.0285807, longitude = 38.2588888)
        ),
        RestaurantDto(
            id = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            ownerId = "550e8400-e29b-41d4-a716-446655440989",
            name = "Zeko Tlawoth",
            description = "It's a restaurant that is famous for preparing Arabian Foods",
            rate = 4.5,
            phone = "+9641324221423",
            openingTime = "09:00",
            closingTime = "22:00",
            address = AddressDto(latitude = 31.0285807, longitude = 38.2588888)
        ),
        RestaurantDto(
            id = "7c3d631e-6d49-48c9-9f91-9426ec559eb1",
            ownerId = "8a430be2-15b7-47f5-9e06-3f236f8c25ec",
            ownerUsername = "@Uzumaki_Naruto",
            name = "Sushi Haven",
            description = "A trendy sushi restaurant offering a variety of fresh sushi rolls and sashimi.",
            rate = 3.5,
            phone = "+1234567890",
            openingTime = "11:30",
            closingTime = "21:30",
            address = AddressDto(latitude = 40.712776, longitude = -74.005974),
            priceLevel = "$$"
        ),
        RestaurantDto(
            id = "91c2ae1f-8495-4c0c-bc47-7bf7ef77d907",
            ownerId = "f26dab15-7193-4e8d-bf6e-f4d2ae8799af",
            ownerUsername = "@Victoria_Delacroix",
            name = "Café du Monde",
            description = "A historic café known for its beignets, coffee, and vibrant atmosphere.",
            rate = 2.5,
            phone = "+18005551234",
            openingTime = "07:00",
            closingTime = "23:00",
            address = AddressDto(latitude = 29.9583507, longitude = -90.0656312),
            priceLevel = "$$"
        )
    )


    private val cuisines = listOf(
        CuisineDto(id = "e5b1a329-6f3a-4d63-bb7f-895f1e1c2f9a", name = "All",),
        CuisineDto(id = "ba9b9700-6d24-434b-8d67-daf9e45e1063", name = "Main Course"),
        CuisineDto(id = "6ab493b4-4b8d-410a-a13e-780346243f3a", name = "Keto"),
        CuisineDto(id = "ba9b9700-6d24-434b-8d67-daf9e45e1065", name = "Pizza"),
        CuisineDto(id = "ba9b9700-6d24-434b-8d67-daf9e45e1066", name = "Dinner"),
        CuisineDto(id = "6ab493b4-4b8d-410a-a13e-780346243f3c", name = "Chinese"),
        CuisineDto(id = "6ab493b4-4b8d-410a-a13e-780346243f3d", name = "Italian"),
        CuisineDto(id = "6ab493b4-4b8d-410a-a13e-780346243f3e", name = "Middle-East"),
        CuisineDto(id = "6ab493b4-4b8d-410a-a13e-780346243f3f", name = "Indian"),
        CuisineDto(id = "6ab493b4-4b8d-410a-a13e-780346243f3g", name = "French"),
        CuisineDto(id = "6ab493b4-4b8d-410a-a13e-780346243f3h", name = "Japanese"),
        CuisineDto(id = "6ab493b4-4b8d-410a-a13e-780346243f3i", name = "Spanish"),
        CuisineDto(id = "6ab493b4-4b8d-410a-a13e-780346243f3j", name = "Greek"),
        CuisineDto(id ="ba9b9700-6d24-434b-8d67-daf9e45e1064", name = "Burrito"),

    )

    //region restaurant
    override suspend fun getRestaurantsByOwnerId(ownerId: String): List<Restaurant> {
        return restaurants.toEntity()
    }

    override suspend fun updateRestaurantInfo(restaurant: Restaurant): Restaurant? {
        // Just Kda W Kda 😉
        return restaurant
    }

    override suspend fun getRestaurantInfo(restaurantId: String): Restaurant? {
        return getRestaurantsByOwnerId("7bf7ef77d907").find { it.id == restaurantId }
    }
    //endregion restaurant

    //region meal
    override suspend fun getMealsByRestaurantId(restaurantId: String): List<Meal> {
        return meals.toEntity()
    }

    override suspend fun getMealById(mealId: String): Meal? {
        return getMealsByRestaurantId("ef77d90").find { it.id == mealId }
    }

    override suspend fun addMeal(meal: Meal): Meal {
        return meal
    }

    override suspend fun updateMeal(meal: Meal): Meal {
        // Just Kda W Kda 😉
        return meal
    }
    //endregion meal

    //region order
    override suspend fun getCurrentOrders(restaurantId: String): List<Order> {
        return orders.toOrderEntity()
//        return orders.filter {
//            it.orderState == OrderState.PENDING.statusCode &&
//                    it.orderState == OrderState.IN_COOKING.statusCode
//        }.toOrderEntity()
    }

    override suspend fun getOrdersHistory(restaurantId: String): List<Order> {
        return orders.filter { it.orderState == 2 || it.orderState == 3 }.toOrderEntity()
    }

    override suspend fun updateOrderState(orderId: String, orderState: Int): Order {
        val order = orders.find { it.id == orderId }
        return order?.toEntity() ?: throw RequestException()
    }

    override suspend fun getOrderById(orderId: String): Order? {
        return orders.find { it.id == orderId }?.toEntity()
    }
    //endregion order

    //region category
    override suspend fun getCategoriesByRestaurantId(restaurantId: String): Category {
        return Category(id = "8a-4854-49c6-99ed-ef09899c2", name = "Sea Food")
    }
    //endregion category

    //region cuisines
    override suspend fun getCuisines(): List<Cuisine> {
        return cuisines.toEntity()
    }

    override suspend fun getCuisinesInMeal(mealId: String): List<Cuisine> {
        return cuisines.subList(0, 3).toEntity()
    }

    override suspend fun getCuisine(restaurantId: String): List<Cuisine> {
        return cuisines.toEntity()
    }

    override suspend fun getMealsByCuisineId(id: String): List<Meal> {
        return meals.filter { meal ->
            meal.cuisines?.find { it == id } != null
        }.toEntity()
    }


    //endregion cuisines

}
