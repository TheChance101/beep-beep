package org.thechance.common.data.remote.fakegateway

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.thechance.common.data.remote.mapper.toDto
import org.thechance.common.data.remote.mapper.toEntity
import org.thechance.common.data.remote.model.DataWrapperDto
import org.thechance.common.data.remote.model.TaxiDto
import org.thechance.common.data.remote.model.toEntity
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.NewTaxiInfo
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.entity.TaxiFiltration
import org.thechance.common.domain.getway.ITaxisGateway
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil

class TaxisFakeGateway : ITaxisGateway {

    override suspend fun getPdfTaxiReport() {
        createTaxiPDFReport()
    }

    override suspend fun getTaxis(
        username: String?,
        taxiFiltration: TaxiFiltration,
        page: Int,
        limit: Int
    ): DataWrapper<Taxi> {
        var filteredTaxis = taxis.toEntity()

        username?.let { name ->
            if (name.isNotEmpty()) {
                filteredTaxis = filteredTaxis.filter {
                    it.username.startsWith(name, ignoreCase = true)
                }
            }
        }

        if (taxiFiltration.status != null || taxiFiltration.carColor != null || taxiFiltration.seats != -1) {
            filteredTaxis = filteredTaxis.filter {
                it.status == taxiFiltration.status &&
                        it.color == taxiFiltration.carColor &&
                        it.seats == taxiFiltration.seats
            }
        }

        val startIndex = (page - 1) * limit
        val endIndex = startIndex + limit
        val numberOfPages = ceil(taxis.size / (limit * 1.0)).toInt()
        return try {
            DataWrapperDto(
                totalPages = numberOfPages,
                result = filteredTaxis.subList(startIndex, endIndex.coerceAtMost(taxis.size)),
                totalResult = filteredTaxis.size
            ).toEntity()
        } catch (e: Exception) {
            DataWrapperDto(
                totalPages = numberOfPages,
                result = filteredTaxis,
                totalResult = filteredTaxis.size
            ).toEntity()
        }
    }

    override suspend fun createTaxi(taxi: NewTaxiInfo): Taxi {
        val taxiDto = taxi.toDto()
        taxis.add(
            TaxiDto(
                id = UUID.randomUUID().toString(),
                plateNumber = taxiDto.plateNumber,
                color = taxiDto.color.toLong(),
                seats = taxiDto.seats,
                type = taxiDto.type,
                driverUsername = taxiDto.driverUsername,
                isAvailable = false,
                trips = "0",
            )
        )
        return taxis.last().toEntity()
    }

    override suspend fun updateTaxi(taxi: NewTaxiInfo): Taxi {
        val indexToUpdate = taxis.indexOfFirst { it.driverId == taxi.driverUserName }
        val newTaxi = taxi.toDto()
        return if (indexToUpdate != -1) {
            val oldTaxi = taxis[indexToUpdate]
            val updatedTaxi = TaxiDto(
                id = UUID.randomUUID().toString(),
                plateNumber = newTaxi.plateNumber,
                color = newTaxi.color.toLong(),
                type = newTaxi.type,
                seats = newTaxi.seats,
                driverUsername = newTaxi.driverUsername,
                trips = oldTaxi.trips,
                isAvailable = oldTaxi.isAvailable,
                driverId = oldTaxi.driverId
            )
            taxis.removeAt(indexToUpdate)
            taxis.add(index = indexToUpdate, element = updatedTaxi)
            updatedTaxi.toEntity()
        } else {
            throw Exception("Taxi not found")
        }
    }

    override suspend fun deleteTaxi(taxiId: String): Boolean {
        val indexToUpdate = taxis.indexOfFirst { it.id == taxiId }
        return if (indexToUpdate != -1) {
            taxis.removeAt(indexToUpdate)
            true
        } else {
            throw Exception("Taxi not found")
        }
    }

    override suspend fun getTaxiById(taxiId: String): Taxi {
        return taxis.first { it.id == taxiId }.toEntity()
    }

    private val taxis = mutableListOf(
        TaxiDto(
            id = "1",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 1,
            seats = 4,
            isAvailable = true,
            driverUsername = "john_doe",
            trips = "10",
            driverId = "DRIVER_ID",
        ),
        TaxiDto(
            id = "2",
            plateNumber = "XYZ789",
            color = 2,
            type = "SUV",
            seats = 6,
            driverUsername = "jane_doe",
            isAvailable = true,
            trips = "5",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "3",
            plateNumber = "DEF456",
            color = 3,
            type = "Hatchback",
            seats = 4,
            driverUsername = "james_smith",
            isAvailable = true,
            trips = "2",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "4",
            plateNumber = "GHI789",
            color = 4,
            type = "Minivan",
            seats = 6,
            driverUsername = "mary_johnson",
            isAvailable = true,
            trips = "15",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "5",
            plateNumber = "JKL012",
            color = 1,
            type = "Convertible",
            seats = 4,
            driverUsername = "robert_white",
            isAvailable = true,
            trips = "3",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "6",
            plateNumber = "MNO345",
            color = 2,
            type = "Sedan",
            seats = 4,
            driverUsername = "linda_miller",
            isAvailable = true,
            trips = "7",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "7",
            plateNumber = "PQR678",
            color = 3,
            type = "Hatchback",
            seats = 4,
            driverUsername = "david_jones",
            isAvailable = true,
            trips = "12",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "8",
            plateNumber = "STU901",
            color = 4,
            type = "Minivan",
            seats = 2,
            driverUsername = "susan_anderson",
            isAvailable = true,
            trips = "9",
            driverId = " DRIVER_ID"
        ),
        TaxiDto(
            id = "9",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 1,
            seats = 4,
            isAvailable = false,
            driverUsername = "Asia",
            trips = "10",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "10",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 1,
            seats = 4,
            isAvailable = false,
            driverUsername = "Safi",
            trips = "10",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "11",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 1,
            seats = 4,
            isAvailable = false,
            driverUsername = "Mujtaba",
            trips = "10",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "12",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 1,
            seats = 4,
            isAvailable = false,
            driverUsername = "Krrar",
            trips = "10",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "13",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 1,
            seats = 4,
            isAvailable = false,
            driverUsername = "Aya",
            trips = "10",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "14",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 1,
            seats = 4,
            isAvailable = false,
            driverUsername = "Kamel",
            trips = "10",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "16",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 2,
            seats = 4,
            isAvailable = false,
            driverUsername = "Kamel",
            trips = "10",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "17",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 2,
            seats = 5,
            isAvailable = false,
            driverUsername = "Kamel",
            trips = "10",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "18",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 2,
            seats = 4,
            isAvailable = false,
            driverUsername = "Kamel",
            trips = "10",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "19",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 2,
            seats = 4,
            isAvailable = true,
            driverUsername = "Kamel",
            trips = "10",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "20",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 5,
            seats = 5,
            isAvailable = true,
            driverUsername = "Kamel",
            trips = "10",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "21",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 4,
            seats = 4,
            isAvailable = true,
            driverUsername = "Kamel",
            trips = "10",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "22",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 5,
            seats = 4,
            isAvailable = true,
            driverUsername = "Kamel",
            trips = "10",
            driverId = "DRIVER_ID"
        ),
    )

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
                    taxi.id.toString(), taxi.driverUsername, taxi.plateNumber, taxi.type, taxi.color,
                    taxi.seats, taxi.isAvailable.toString(), taxi.trips.toString()
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