package data.gateway.fake

import data.remote.mapper.toEntity
import data.remote.model.CartDto
import data.remote.model.CartMealDto
import domain.entity.Cart
import domain.gateway.ICartGateway

class FakeCartGateway : ICartGateway {

    override suspend fun getAllCartMeals(): Cart {
        return CartDto(
            meals = mealsCart,
            totalPrice = 255.0,
            currency = "$"
        ).toEntity()
    }

    private val mealsCart = listOf(
        CartMealDto(
            id = "64f372095fecc11e6d917656",
            price = 85.0,
            count = 1,
            restaurantName = "Burger king",
            name = "Double cheese burger",
            currency = "$",
            image = "https://s3-alpha-sig.figma.com/img/87f1/a16c/e905155ab7f153570d77f67ecb3bf1f6?Expires=1696204800&Signature=DmxCysc92O0ScX-GO5hhNt2AmsVGk0-U9she4GbHwfhL8qwvl386HN-kur-Wmw-XGE4OptyvEvDLqhMhhaeypR3Bd8lh2SIk6-BxI2z1D1z35QHeHN-ZEVc6Lq1Ai23jtzRAXVfU3nLVLNKOKnupSdhIWQ1JYMaP8TbRokOQFy7cQR70bSEuhROd2urzy5UDq3zJFaqyt6OTleWf9jwJNv5frK0v351I6bdHR0ekSR6flLcCamfvz3skYu7VZ6VE8QLr-dLzX3KIeeYkiZrgEE4YyR054o5TcE~BJALxaLbbHbvKJnOZTp93Lga7Ki5cSFMx3GAYC87Okpz0GHScQA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        ),
        CartMealDto(
            id = "64f372095fecc11e6d917656",
            price = 85.0,
            count = 1,
            restaurantName = "Burger king",
            name = "Double cheese burger",
            currency = "$",
            image = "https://s3-alpha-sig.figma.com/img/87f1/a16c/e905155ab7f153570d77f67ecb3bf1f6?Expires=1696204800&Signature=DmxCysc92O0ScX-GO5hhNt2AmsVGk0-U9she4GbHwfhL8qwvl386HN-kur-Wmw-XGE4OptyvEvDLqhMhhaeypR3Bd8lh2SIk6-BxI2z1D1z35QHeHN-ZEVc6Lq1Ai23jtzRAXVfU3nLVLNKOKnupSdhIWQ1JYMaP8TbRokOQFy7cQR70bSEuhROd2urzy5UDq3zJFaqyt6OTleWf9jwJNv5frK0v351I6bdHR0ekSR6flLcCamfvz3skYu7VZ6VE8QLr-dLzX3KIeeYkiZrgEE4YyR054o5TcE~BJALxaLbbHbvKJnOZTp93Lga7Ki5cSFMx3GAYC87Okpz0GHScQA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        ),
        CartMealDto(
            id = "64f372095fecc11e6d917656",
            price = 85.0,
            count = 1,
            restaurantName = "Burger king",
            name = "Double cheese burger",
            currency = "$",
            image = "https://s3-alpha-sig.figma.com/img/87f1/a16c/e905155ab7f153570d77f67ecb3bf1f6?Expires=1696204800&Signature=DmxCysc92O0ScX-GO5hhNt2AmsVGk0-U9she4GbHwfhL8qwvl386HN-kur-Wmw-XGE4OptyvEvDLqhMhhaeypR3Bd8lh2SIk6-BxI2z1D1z35QHeHN-ZEVc6Lq1Ai23jtzRAXVfU3nLVLNKOKnupSdhIWQ1JYMaP8TbRokOQFy7cQR70bSEuhROd2urzy5UDq3zJFaqyt6OTleWf9jwJNv5frK0v351I6bdHR0ekSR6flLcCamfvz3skYu7VZ6VE8QLr-dLzX3KIeeYkiZrgEE4YyR054o5TcE~BJALxaLbbHbvKJnOZTp93Lga7Ki5cSFMx3GAYC87Okpz0GHScQA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        ),
        CartMealDto(
            id = "64f372095fecc11e6d917656",
            price = 85.0,
            count = 1,
            restaurantName = "Burger king",
            name = "Double cheese burger",
            currency = "$",
            image = "https://s3-alpha-sig.figma.com/img/87f1/a16c/e905155ab7f153570d77f67ecb3bf1f6?Expires=1696204800&Signature=DmxCysc92O0ScX-GO5hhNt2AmsVGk0-U9she4GbHwfhL8qwvl386HN-kur-Wmw-XGE4OptyvEvDLqhMhhaeypR3Bd8lh2SIk6-BxI2z1D1z35QHeHN-ZEVc6Lq1Ai23jtzRAXVfU3nLVLNKOKnupSdhIWQ1JYMaP8TbRokOQFy7cQR70bSEuhROd2urzy5UDq3zJFaqyt6OTleWf9jwJNv5frK0v351I6bdHR0ekSR6flLcCamfvz3skYu7VZ6VE8QLr-dLzX3KIeeYkiZrgEE4YyR054o5TcE~BJALxaLbbHbvKJnOZTp93Lga7Ki5cSFMx3GAYC87Okpz0GHScQA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        ),
        CartMealDto(
            id = "64f372095fecc11e6d917656",
            price = 85.0,
            count = 1,
            restaurantName = "Burger king",
            name = "Double cheese burger",
            currency = "$",
            image = "https://s3-alpha-sig.figma.com/img/87f1/a16c/e905155ab7f153570d77f67ecb3bf1f6?Expires=1696204800&Signature=DmxCysc92O0ScX-GO5hhNt2AmsVGk0-U9she4GbHwfhL8qwvl386HN-kur-Wmw-XGE4OptyvEvDLqhMhhaeypR3Bd8lh2SIk6-BxI2z1D1z35QHeHN-ZEVc6Lq1Ai23jtzRAXVfU3nLVLNKOKnupSdhIWQ1JYMaP8TbRokOQFy7cQR70bSEuhROd2urzy5UDq3zJFaqyt6OTleWf9jwJNv5frK0v351I6bdHR0ekSR6flLcCamfvz3skYu7VZ6VE8QLr-dLzX3KIeeYkiZrgEE4YyR054o5TcE~BJALxaLbbHbvKJnOZTp93Lga7Ki5cSFMx3GAYC87Okpz0GHScQA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        ),
        CartMealDto(
            id = "64f372095fecc11e6d917656",
            price = 85.0,
            count = 1,
            restaurantName = "Burger king",
            name = "Double cheese burger",
            currency = "$",
            image = "https://s3-alpha-sig.figma.com/img/87f1/a16c/e905155ab7f153570d77f67ecb3bf1f6?Expires=1696204800&Signature=DmxCysc92O0ScX-GO5hhNt2AmsVGk0-U9she4GbHwfhL8qwvl386HN-kur-Wmw-XGE4OptyvEvDLqhMhhaeypR3Bd8lh2SIk6-BxI2z1D1z35QHeHN-ZEVc6Lq1Ai23jtzRAXVfU3nLVLNKOKnupSdhIWQ1JYMaP8TbRokOQFy7cQR70bSEuhROd2urzy5UDq3zJFaqyt6OTleWf9jwJNv5frK0v351I6bdHR0ekSR6flLcCamfvz3skYu7VZ6VE8QLr-dLzX3KIeeYkiZrgEE4YyR054o5TcE~BJALxaLbbHbvKJnOZTp93Lga7Ki5cSFMx3GAYC87Okpz0GHScQA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        ),
        CartMealDto(
            id = "64f372095fecc11e6d917656",
            price = 85.0,
            count = 1,
            restaurantName = "Burger king",
            name = "Double cheese burger",
            currency = "$",
            image = "https://s3-alpha-sig.figma.com/img/87f1/a16c/e905155ab7f153570d77f67ecb3bf1f6?Expires=1696204800&Signature=DmxCysc92O0ScX-GO5hhNt2AmsVGk0-U9she4GbHwfhL8qwvl386HN-kur-Wmw-XGE4OptyvEvDLqhMhhaeypR3Bd8lh2SIk6-BxI2z1D1z35QHeHN-ZEVc6Lq1Ai23jtzRAXVfU3nLVLNKOKnupSdhIWQ1JYMaP8TbRokOQFy7cQR70bSEuhROd2urzy5UDq3zJFaqyt6OTleWf9jwJNv5frK0v351I6bdHR0ekSR6flLcCamfvz3skYu7VZ6VE8QLr-dLzX3KIeeYkiZrgEE4YyR054o5TcE~BJALxaLbbHbvKJnOZTp93Lga7Ki5cSFMx3GAYC87Okpz0GHScQA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        ),
        CartMealDto(
            id = "64f372095fecc11e6d917656",
            price = 85.0,
            count = 1,
            restaurantName = "Burger king",
            name = "Double cheese burger",
            currency = "$",
            image = "https://s3-alpha-sig.figma.com/img/87f1/a16c/e905155ab7f153570d77f67ecb3bf1f6?Expires=1696204800&Signature=DmxCysc92O0ScX-GO5hhNt2AmsVGk0-U9she4GbHwfhL8qwvl386HN-kur-Wmw-XGE4OptyvEvDLqhMhhaeypR3Bd8lh2SIk6-BxI2z1D1z35QHeHN-ZEVc6Lq1Ai23jtzRAXVfU3nLVLNKOKnupSdhIWQ1JYMaP8TbRokOQFy7cQR70bSEuhROd2urzy5UDq3zJFaqyt6OTleWf9jwJNv5frK0v351I6bdHR0ekSR6flLcCamfvz3skYu7VZ6VE8QLr-dLzX3KIeeYkiZrgEE4YyR054o5TcE~BJALxaLbbHbvKJnOZTp93Lga7Ki5cSFMx3GAYC87Okpz0GHScQA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        ),
        CartMealDto(
            id = "64f372095fecc11e6d917656",
            price = 85.0,
            count = 1,
            restaurantName = "Burger king",
            name = "Double cheese burger",
            currency = "$",
            image = "https://s3-alpha-sig.figma.com/img/87f1/a16c/e905155ab7f153570d77f67ecb3bf1f6?Expires=1696204800&Signature=DmxCysc92O0ScX-GO5hhNt2AmsVGk0-U9she4GbHwfhL8qwvl386HN-kur-Wmw-XGE4OptyvEvDLqhMhhaeypR3Bd8lh2SIk6-BxI2z1D1z35QHeHN-ZEVc6Lq1Ai23jtzRAXVfU3nLVLNKOKnupSdhIWQ1JYMaP8TbRokOQFy7cQR70bSEuhROd2urzy5UDq3zJFaqyt6OTleWf9jwJNv5frK0v351I6bdHR0ekSR6flLcCamfvz3skYu7VZ6VE8QLr-dLzX3KIeeYkiZrgEE4YyR054o5TcE~BJALxaLbbHbvKJnOZTp93Lga7Ki5cSFMx3GAYC87Okpz0GHScQA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        ),
        CartMealDto(
            id = "64f372095fecc11e6d917656",
            price = 85.0,
            count = 1,
            restaurantName = "Burger king",
            name = "Double cheese burger",
            currency = "$",
            image = "https://s3-alpha-sig.figma.com/img/87f1/a16c/e905155ab7f153570d77f67ecb3bf1f6?Expires=1696204800&Signature=DmxCysc92O0ScX-GO5hhNt2AmsVGk0-U9she4GbHwfhL8qwvl386HN-kur-Wmw-XGE4OptyvEvDLqhMhhaeypR3Bd8lh2SIk6-BxI2z1D1z35QHeHN-ZEVc6Lq1Ai23jtzRAXVfU3nLVLNKOKnupSdhIWQ1JYMaP8TbRokOQFy7cQR70bSEuhROd2urzy5UDq3zJFaqyt6OTleWf9jwJNv5frK0v351I6bdHR0ekSR6flLcCamfvz3skYu7VZ6VE8QLr-dLzX3KIeeYkiZrgEE4YyR054o5TcE~BJALxaLbbHbvKJnOZTp93Lga7Ki5cSFMx3GAYC87Okpz0GHScQA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        ),
    )
}