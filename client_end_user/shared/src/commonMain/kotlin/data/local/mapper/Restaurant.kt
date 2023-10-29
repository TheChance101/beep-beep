package data.local.mapper

import data.local.model.RestaurantCollection
import domain.entity.Location
import domain.entity.PriceLevel
import domain.entity.Restaurant
import domain.entity.Time

fun RestaurantCollection.toFavoriteRestaurant(): Restaurant {
    return Restaurant(
        id = this.id,
        name = this.name,
        rate = this.rate,
        priceLevel = PriceLevel.getPriceLevel(priceLevel) ?: PriceLevel.MEDIUM,
        image = this.imageUrl,
        description = "", // never used
        phone = "", // never used
        openingTime = Time(12,0), // never used
        closingTime = Time(23,0), // never used
        location = Location(0.0,0.0), // never used
        address = "", // never used
        ownerId = "", // never used
        ownerUsername = "" // never used
    )
}

fun Restaurant.toRestaurantCollection(): RestaurantCollection {
    return RestaurantCollection().apply {
        id = this@toRestaurantCollection.id
        name = this@toRestaurantCollection.name
        rate = this@toRestaurantCollection.rate
        priceLevel = this@toRestaurantCollection.priceLevel.priceLevel
        imageUrl = this@toRestaurantCollection.image
    }
}
