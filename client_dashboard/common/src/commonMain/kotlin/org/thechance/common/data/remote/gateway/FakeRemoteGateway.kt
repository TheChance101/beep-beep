package org.thechance.common.data.remote.gateway


import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.thechance.common.data.local.gateway.LocalGateway
import org.thechance.common.data.remote.mapper.toEntity
import org.thechance.common.data.remote.model.DataWrapperDto
import org.thechance.common.data.remote.model.RestaurantDto
import org.thechance.common.data.remote.model.TaxiDto
import org.thechance.common.data.remote.model.toEntity
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.NewRestaurantInfo
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.entity.Time
import org.thechance.common.domain.getway.IRemoteGateway
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil
import kotlin.math.floor

class FakeRemoteGateway(
    private val localGateway: LocalGateway,
) : IRemoteGateway {

    private val restaurants = mutableListOf<RestaurantDto>()
    private val taxis = mutableListOf<TaxiDto>()
    private val cuisines = mutableListOf<String>()

    init {
        taxis.addAll(
            listOf(
                TaxiDto(
                    id = "1",
                    plateNumber = "ABC123",
                    type = "Sedan",
                    color = 1,
                    seats = 4,
                    status = 1,
                    username = "john_doe",
                    trips = "10"
                ),
                TaxiDto(
                    id = "2",
                    plateNumber = "XYZ789",
                    color = 2,
                    type = "SUV",
                    seats = 6,
                    username = "jane_doe",
                    status = 1,
                    trips = "5"
                ),
                TaxiDto(
                    id = "3",
                    plateNumber = "DEF456",
                    color = 3,
                    type = "Hatchback",
                    seats = 4,
                    username = "james_smith",
                    status = 0,
                    trips = "2"
                ),
                TaxiDto(
                    id = "4",
                    plateNumber = "GHI789",
                    color = 4,
                    type = "Minivan",
                    seats = 6,
                    username = "mary_johnson",
                    status = 1,
                    trips = "15"
                ),
                TaxiDto(
                    id = "5",
                    plateNumber = "JKL012",
                    color = 1,
                    type = "Convertible",
                    seats = 4,
                    username = "robert_white",
                    status = 0,
                    trips = "3"
                ),
                TaxiDto(
                    id = "6",
                    plateNumber = "MNO345",
                    color = 2,
                    type = "Sedan",
                    seats = 4,
                    username = "linda_miller",
                    status = 0,
                    trips = "7"
                ),
                TaxiDto(
                    id = "7",
                    plateNumber = "PQR678",
                    color = 3,
                    type = "Hatchback",
                    seats = 4,
                    username = "david_jones",
                    status = 1,
                    trips = "12"
                ),
                TaxiDto(
                    id = "8",
                    plateNumber = "STU901",
                    color = 4,
                    type = "Minivan",
                    seats = 2,
                    username = "susan_anderson",
                    status = 0,
                    trips = "9"
                ),
                TaxiDto(
                    id = "9",
                    plateNumber = "ABC123",
                    type = "Sedan",
                    color = 1,
                    seats = 4,
                    status = 1,
                    username = "Asia",
                    trips = "10"
                ),
                TaxiDto(
                    id = "10",
                    plateNumber = "ABC123",
                    type = "Sedan",
                    color = 1,
                    seats = 4,
                    status = 1,
                    username = "Safi",
                    trips = "10"
                ),
                TaxiDto(
                    id = "11",
                    plateNumber = "ABC123",
                    type = "Sedan",
                    color = 1,
                    seats = 4,
                    status = 1,
                    username = "Mujtaba",
                    trips = "10"
                ),
                TaxiDto(
                    id = "12",
                    plateNumber = "ABC123",
                    type = "Sedan",
                    color = 1,
                    seats = 4,
                    status = 1,
                    username = "Krrar",
                    trips = "10"
                ),
                TaxiDto(
                    id = "13",
                    plateNumber = "ABC123",
                    type = "Sedan",
                    color = 1,
                    seats = 4,
                    status = 1,
                    username = "Aya",
                    trips = "10"
                ),
                TaxiDto(
                    id = "14",
                    plateNumber = "ABC123",
                    type = "Sedan",
                    color = 1,
                    seats = 4,
                    status = 1,
                    username = "Kamel",
                    trips = "10"
                ),
                TaxiDto(
                    id = "16",
                    plateNumber = "ABC123",
                    type = "Sedan",
                    color = 2,
                    seats = 4,
                    status = 1,
                    username = "Kamel",
                    trips = "10"
                ),
                TaxiDto(
                    id = "17",
                    plateNumber = "ABC123",
                    type = "Sedan",
                    color = 2,
                    seats = 5,
                    status = 1,
                    username = "Kamel",
                    trips = "10"
                ),
                TaxiDto(
                    id = "18",
                    plateNumber = "ABC123",
                    type = "Sedan",
                    color = 2,
                    seats = 4,
                    status = 1,
                    username = "Kamel",
                    trips = "10"
                ),
                TaxiDto(
                    id = "19",
                    plateNumber = "ABC123",
                    type = "Sedan",
                    color = 2,
                    seats = 4,
                    status = 1,
                    username = "Kamel",
                    trips = "10"
                ),
                TaxiDto(
                    id = "20",
                    plateNumber = "ABC123",
                    type = "Sedan",
                    color = 5,
                    seats = 5,
                    status = 1,
                    username = "Kamel",
                    trips = "10"
                ),
                TaxiDto(
                    id = "21",
                    plateNumber = "ABC123",
                    type = "Sedan",
                    color = 4,
                    seats = 4,
                    status = 1,
                    username = "Kamel",
                    trips = "10"
                ),
                TaxiDto(
                    id = "22",
                    plateNumber = "ABC123",
                    type = "Sedan",
                    color = 5,
                    seats = 4,
                    status = 1,
                    username = "Kamel",
                    trips = "10"
                ),
            )
        )
        restaurants.addAll(
            listOf(
                RestaurantDto(
                    id = "8c90c4c6-1e69-47f3-aa59-2edcd6f0057b",
                    name = "Mujtaba Restaurant",
                    ownerUsername = "mujtaba",
                    phoneNumber = "0532465722",
                    rating = 0.4,
                    priceLevel = 1,
                    workingHours = "06:30 - 22:30"
                ),
                RestaurantDto(
                    id = "6e21s4f-aw32-fs3e-fe43-aw56g4yr324",
                    name = "Karrar Restaurant",
                    ownerUsername = "karrar",
                    phoneNumber = "0535232154",
                    rating = 3.5,
                    priceLevel = 1,
                    workingHours = "12:00 - 23:00"
                ),
                RestaurantDto(
                    id = "7a33sax-aw32-fs3e-12df-42ad6x352zse",
                    name = "Saif Restaurant",
                    ownerUsername = "saif",
                    phoneNumber = "0554627893",
                    rating = 4.0,
                    priceLevel = 3,
                    workingHours = "09:00 - 23:00"
                ),
                RestaurantDto(
                    id = "7y1z47c-s2df-76de-dwe2-42ad6x352zse",
                    name = "Nada Restaurant",
                    ownerUsername = "nada",
                    phoneNumber = "0524242766",
                    rating = 3.4,
                    priceLevel = 2,
                    workingHours = "01:00 - 23:00"
                ),
                RestaurantDto(
                    id = "3e1f5d4a-8317-4f13-aa89-2c094652e6a3",
                    name = "Asia Restaurant",
                    ownerUsername = "asia",
                    phoneNumber = "0528242165",
                    rating = 2.9,
                    priceLevel = 1,
                    workingHours = "09:30 - 21:30"
                ),
                RestaurantDto(
                    id = "7a1bfe39-4b2c-4f76-bde0-82da2eaf9e99",
                    name = "Kamel Restaurant",
                    ownerUsername = "kamel",
                    phoneNumber = "0528242235",
                    rating = 4.9,
                    priceLevel = 3,
                    workingHours = "06:30 - 22:30"
                ),
            )
        )
        cuisines.addAll(
            listOf(
                "Angolan cuisine",
                "Cameroonian cuisine",
                "Chadian cuisine",
                "Congolese cuisine",
                "Centrafrican cuisine",
                "Equatorial Guinea cuisine",
                "Gabonese cuisine",
                "Santomean cuisine",
                "Burundian cuisine",
                "Djiboutian cuisine",
                "Eritrean cuisine",
                "Ethiopian cuisine",
                "Kenyan cuisine",
                "Maasai cuisine",
                "Rwandan cuisine",
                "Somali cuisine",
                "South Sudanese cuisine",
                "Tanzanian cuisine",
                "Zanzibari cuisine",
                "Ugandan cuisine",
            )
        )
    }
    override suspend fun getUserData() = "asia"

    override suspend fun getPdfTaxiReport() {
        val taxiReportFile = createTaxiPDFReport()
        localGateway.saveTaxiReport(taxiReportFile)
    }

    override suspend fun getRestaurants(
        pageNumber: Int,
        numberOfRestaurantsInPage: Int,
        restaurantName: String,
        rating: Double?,
        priceLevel: Int?,
    ): DataWrapper<Restaurant> {
        var restaurants = restaurants.toEntity()
        if (restaurantName.isNotEmpty()) {
            restaurants = restaurants.filter {
                it.name.startsWith(
                    restaurantName,
                    true
                )
            }
        }
        if (rating != null && priceLevel != null) {
            restaurants = restaurants.filter {
                it.priceLevel == priceLevel &&
                        when {
                            rating.rem(1) > 0.89 || rating.rem(1) == 0.0 || rating.rem(1) > 0.5
                            -> it.rating in floor(rating) - 0.1..0.49 + floor(rating)

                            else -> it.rating in 0.5 + floor(rating)..0.89 + floor(rating)
                        }
            }
        }
        val startIndex = (pageNumber - 1) * numberOfRestaurantsInPage
        val endIndex = startIndex + numberOfRestaurantsInPage
        val numberOfPages = ceil(restaurants.size / (numberOfRestaurantsInPage * 1.0)).toInt()
        return try {
            DataWrapperDto(
                totalPages = numberOfPages,
                result = restaurants.subList(startIndex, endIndex.coerceAtMost(restaurants.size)),
                totalResult = restaurants.size
            ).toEntity()
        } catch (e: Exception) {
            DataWrapperDto(
                totalPages = numberOfPages,
                result = restaurants,
                totalResult = restaurants.size
            ).toEntity()
        }
    }

    override suspend fun loginUser(username: String, password: String): Pair<String, String> {
        return Pair("token", "refreshToken")
    }

    override suspend fun createRestaurant(restaurant: NewRestaurantInfo): Restaurant {
        return Restaurant(
            id = "7",
            name = restaurant.name,
            ownerUsername = restaurant.ownerUsername,
            phoneNumber = restaurant.phoneNumber,
            workingHours = restaurant.workingHours,
            rating = 3.0,
            priceLevel = 1
        )
    }

    override suspend fun getCurrentLocation(): String {
        return "30.044420,31.235712"
    }

    override suspend fun deleteRestaurants(restaurant: Restaurant): Restaurant {
        restaurants.remove(
            RestaurantDto(
                id = restaurant.id,
                name = restaurant.name,
                ownerUsername = restaurant.ownerUsername,
                phoneNumber = restaurant.phoneNumber,
                rating = restaurant.rating,
                priceLevel = restaurant.priceLevel,
                workingHours = if (restaurant.workingHours == Pair(
                        Time(0, 0), Time(0, 0)
                    )
                ) null
                else "${restaurant.workingHours.first} - ${restaurant.workingHours.second}",
            )
        )
        return restaurant
    }

    override suspend fun getCuisines(): List<String> {
        return cuisines
    }

    override suspend fun createCuisine(cuisineName: String): String? {
        return if (cuisineName !in cuisines && cuisineName.isNotBlank()) {
            cuisines.add(0, cuisineName)
            cuisineName
        } else null
    }

    override suspend fun deleteCuisine(cuisineName: String): String {
        cuisines.remove(cuisineName)
        return cuisineName
    }

    private fun createTaxiPDFReport(): File {
        val title = "Taxi Details Report"
        val columnNames = listOf(
            "Taxi ID",
            "Username",
            "Plate Number",
            "Model",
            "Color",
            "Seats",
            "Status",
            "Trips"
        )
        val columnWidth = listOf(50f, 80f, 80f, 80f, 80f, 80f, 80f, 50f)

        val file = createPDFReport(title, taxis, columnNames, columnWidth) { taxi ->
            listOf(
                taxi.id.toString(), taxi.username, taxi.plateNumber, taxi.type, taxi.color,
                taxi.seats, taxi.status.toString(), taxi.trips.toString()
            ).map { it ?: "" }
        }
        return file
    }

    private fun <T> createPDFReport(
        title: String,
        dataList: List<T>,
        columnNames: List<String>,
        colWidths: List<Float>,
        dataExtractor: (T) -> List<Any>,
    ): File {
        try {
            //region Create PDF document
            val document = PDDocument()
            val page = PDPage(PDRectangle.A4)
            document.addPage(page)
            val contentStream = PDPageContentStream(document, page)

            val pageWidth = page.trimBox.width
            val pageHeight = page.trimBox.height

            val startX = 10f
            val cellHeight = 30f
            //endregion
            val titleY = titleContent(contentStream, title, pageWidth, pageHeight)
            val dateTimeY = dateContent(contentStream, pageWidth, titleY)
            val (currentX, currentY) = headerContent(
                contentStream,
                startX,
                dateTimeY,
                cellHeight,
                columnNames,
                colWidths
            )
            tableContent(
                currentY,
                contentStream,
                dataList,
                cellHeight,
                currentX,
                startX,
                colWidths,
                dataExtractor
            )
            contentStream.close()
            val pdfFilePath = "${System.getProperty("user.home")}/Downloads/$title.pdf"
            val pdfFile = File(pdfFilePath)
            document.save(pdfFile)
            document.close()
            return pdfFile
        } catch (e: Exception) {
            e.printStackTrace()
            return File("")
        }
    }

    private fun <T> tableContent(
        currentY: Float,
        contentStream: PDPageContentStream,
        dataList: List<T>,
        cellHeight: Float,
        currentX: Float,
        startX: Float,
        colWidths: List<Float>,
        dataExtractor: (T) -> List<Any>,
    ) {
        // Draw table content
        // Add space between header and table content
        var currentY = currentY
        var currentX = currentX
        val contentTopMargin = 30f // Add the desired space
        currentY -= contentTopMargin
        contentStream.setFont(PDType1Font.HELVETICA, 12f)
        for ((rowIndex, item) in dataList.withIndex()) {
            currentY -= cellHeight // Move down for the next row
            currentX = startX

            val rowData = dataExtractor(item)

            for ((index, cellData) in rowData.withIndex()) {
                contentStream.beginText()
                val textWidth =
                    PDType1Font.HELVETICA.getStringWidth(cellData.toString()) / 1000 * 12f
                val textX = currentX + (colWidths[index] - textWidth) / 2
                val textY = currentY + 20f
                contentStream.newLineAtOffset(textX, textY)
                contentStream.showText(cellData.toString())
                contentStream.endText()

                currentX += colWidths[index]
            }
        }
    }

    private fun titleContent(
        contentStream: PDPageContentStream,
        title: String,
        pageWidth: Float,
        pageHeight: Float,
    ): Float {
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16f)
        contentStream.beginText()
        val titleWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(title) / 1000 * 16f
        val titleX = pageWidth / 2 - titleWidth / 2
        val titleY = pageHeight - 30f
        contentStream.newLineAtOffset(titleX, titleY)
        contentStream.showText(title)
        contentStream.endText()
        return titleY
    }

    private fun dateContent(
        contentStream: PDPageContentStream,
        pageWidth: Float,
        titleY: Float,
    ): Float {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val dateTime = dateFormat.format(Date())
        contentStream.setFont(PDType1Font.HELVETICA, 11f)

        contentStream.beginText()
        val dateTimeWidth = PDType1Font.HELVETICA.getStringWidth(dateTime) / 1000 * 12f
        val pageMidX = pageWidth / 2 // Place the text in the center of the page
        val dateTimeX = pageMidX - dateTimeWidth / 2 // Place the text in the center horizontally
        val dateTimeTopMargin = 10f // Add space between date/time and title
        val dateTimeY = titleY - 20f - dateTimeTopMargin // Adjust the value to add more space
        contentStream.newLineAtOffset(dateTimeX, dateTimeY)
        contentStream.showText(dateTime)
        contentStream.endText()
        return dateTimeY
    }

    private fun headerContent(
        contentStream: PDPageContentStream,
        startX: Float,
        dateTimeY: Float,
        cellHeight: Float,
        columnNames: List<String>,
        colWidths: List<Float>,
    ): Pair<Float, Float> {
        // region Draw header row
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 11f)
        var currentX = startX
        val headerTopMargin = 20f // Add space between title/date
        val headerY = dateTimeY - headerTopMargin - cellHeight
        var currentY = headerY
        val columnWidth = columnNames.size
        for ((index, columnName) in columnNames.withIndex()) {
            contentStream.beginText()
            val textWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(columnName) / 1000 * 12f
            val textX = currentX + (colWidths[index] - textWidth) / 2
            val textY = currentY - cellHeight + 20f
            contentStream.newLineAtOffset(textX, textY)
            contentStream.showText(columnName)
            contentStream.endText()
            currentX += colWidths[index]
        }
        return Pair(currentX, currentY)
    }

}