package org.thechance.common.data.remote.gateway


import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.thechance.common.data.local.gateway.LocalGateway
import org.thechance.common.data.remote.mapper.toDto
import org.thechance.common.data.remote.mapper.toEntity
import org.thechance.common.data.remote.model.*
import org.thechance.common.domain.entity.*
import org.thechance.common.domain.getway.IRemoteGateway
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil
import kotlin.math.floor

class FakeRemoteGateway(
    private val localGateway: LocalGateway
) : IRemoteGateway {

    private val restaurant = mutableListOf<RestaurantDto>()
    private val taxis = mutableListOf<TaxiDto>()
    private val fakeUsers = listOf(
        UserDto(
            id = "c4425a0e-9f0a-4df1-bcc1-6dd96322a990",
            fullName = "mohammed sayed",
            username = "mohammed_sayed",
            email = "elzamalk@example.com",
            country = "Egypt",
            permissions = listOf(
                UserDto.PermissionDto(id = 3, permission = "END_USER"),
                UserDto.PermissionDto(id = 1, permission = "RESTAURANT"),
                UserDto.PermissionDto(id = 2, permission = "DRIVER"),
                UserDto.PermissionDto(id = 1, permission = "SUPPORT"),
                UserDto.PermissionDto(id = 2, permission = "ADMIN")
            )
        ),
        UserDto(
            id = "f7b087da-8c02-417b-a3db-54c82b5ff5b4",
            fullName = "asia",
            username = "asia",
            email = "asia@example.com",
            country = "Iraq",
            permissions = listOf(
                UserDto.PermissionDto(id = 3, permission = "END_USER"),
                UserDto.PermissionDto(id = 1, permission = "SUPPORT"),
                UserDto.PermissionDto(id = 2, permission = "ADMIN"),
            )
        ),
        UserDto(
            id = "3e1f5d4a-8317-4f13-aa89-2c094652e6a3",
            fullName = "ali",
            username = "ali_jamal",
            email = "ali_jamal@example.com",
            country = "Iraq",
            permissions = listOf(
                UserDto.PermissionDto(id = 3, permission = "END_USER"),
                UserDto.PermissionDto(id = 1, permission = "ADMIN")
            )
        ),
        UserDto(
            id = "c3d8fe2b-6d36-47ea-964a-57d45e780bce",
            fullName = "mustafa",
            username = "mustafa_246",
            email = "mustafa_246@example.com",
            country = "Syria",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "END_USER"),
                UserDto.PermissionDto(id = 2, permission = "RESTAURANT")
            )
        ),
        UserDto(
            id = "7a1bfe39-4b2c-4f76-bde0-82da2eaf9e99",
            fullName = "sarah ali",
            username = "sarah_ali_567",
            email = "sarah_ali_567@example.com",
            country = "Palestine",
            permissions = listOf(
                UserDto.PermissionDto(id = 3, permission = "END_USER"),
                UserDto.PermissionDto(id = 1, permission = "SUPPORT"),
                UserDto.PermissionDto(id = 2, permission = "ADMIN"),
                UserDto.PermissionDto(id = 3, permission = "DELIVERY")
            )
        ),
        UserDto(
            id = "8c90c4c6-1e69-47f3-aa59-2edcd6f0057b",
            fullName = "Jane Davis",
            username = "jane_davis_890",
            email = "jane_davis@example.com",
            country = "Other",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "END_USER"),
                UserDto.PermissionDto(id = 2, permission = "DRIVER")
            )
        ),
        UserDto(
            id = "8c90c4c6-1e69-47f3-aa59-2edcd6f0057b",
            fullName = "Ahmed mohsen",
            username = "ahmed_mohsen_890",
            email = "ahmed_mohsen@example.com",
            country = "Palestine",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "END_USER"),
                UserDto.PermissionDto(id = 2, permission = "DRIVER")
            )
        ),
        UserDto(
            id = "8c90c4c6-1e69-47f3-aa59-2edcd6f0057b",
            fullName = "cristiano alberto",
            username = "cristiano_real_600",
            email = "cristiano_real@example.com",
            country = "Other",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "END_USER"),
                UserDto.PermissionDto(id = 2, permission = "DRIVER")
            )
        ),
        UserDto(
            id = "c4425a0e-9f0a-4df1-bcc1-6dd96322a990",
            fullName = "Ahmed Hassan",
            username = "ahmed_hassan_123",
            email = "ahmed_hassan@example.com",
            country = "Egypt",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "RESTAURANT"),
                UserDto.PermissionDto(id = 2, permission = "DRIVER"),
                UserDto.PermissionDto(id = 3, permission = "END_USER")
            )
        ),
        UserDto(
            id = "f7b087da-8c02-417b-a3db-54c82b5ff5b4",
            fullName = "Zainab Ali",
            username = "zainab_ali_456",
            email = "zainab_ali@example.com",
            country = "Iraq",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "SUPPORT"),
                UserDto.PermissionDto(id = 2, permission = "DELIVERY")
            )
        ),
        UserDto(
            id = "3e1f5d4a-8317-4f13-aa89-2c094652e6a3",
            fullName = "Mohammed Khalid",
            username = "mohammed_khalid_789",
            email = "mohammed_khalid@example.com",
            country = "Syria",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "ADMIN")
            )
        ),
        UserDto(
            id = "c3d8fe2b-6d36-47ea-964a-57d45e780bce",
            fullName = "Leila Ahmed",
            username = "leila_ahmed_246",
            email = "leila_ahmed@example.com",
            country = "Palestine",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "END_USER"),
                UserDto.PermissionDto(id = 2, permission = "RESTAURANT")
            )
        ),
        UserDto(
            id = "7a1bfe39-4b2c-4f76-bde0-82da2eaf9e99",
            fullName = "Youssef Salem",
            username = "youssef_salem_567",
            email = "youssef_salem@example.com",
            country = "Egypt",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "SUPPORT"),
                UserDto.PermissionDto(id = 2, permission = "ADMIN"),
                UserDto.PermissionDto(id = 3, permission = "DELIVERY")
            )
        ),
        UserDto(
            id = "8c90c4c6-1e69-47f3-aa59-2edcd6f0057b",
            fullName = "Sana Ali",
            username = "sana_ali_890",
            email = "sana_ali@example.com",
            country = "Iraq",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "END_USER"),
                UserDto.PermissionDto(id = 2, permission = "DRIVER")
            )
        ),
        UserDto(
            id = "c28e9e91-3c3b-4e46-9f53-13b380e2a4d5",
            fullName = "Layla Mohamed",
            username = "layla_mohamed_123",
            email = "layla_mohamed@example.com",
            country = "Syria",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "RESTAURANT"),
                UserDto.PermissionDto(id = 2, permission = "END_USER")
            )
        ),
        UserDto(
            id = "f5d5b4a1-6a1f-4c8d-8b1e-7a2e90e38d3c",
            fullName = "Ali Abdullah",
            username = "ali_abdullah_456",
            email = "ali_abdullah@example.com",
            country = "Palestine",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "DELIVERY")
            )
        ),
        UserDto(
            id = "f5d5b4a1-6a1f-4c8d-8b1e-7a2e90e38d3c",
            fullName = "Omar Khalid",
            username = "omar_khalid_123",
            email = "omar_khalid@example.com",
            country = "Syria",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "ADMIN"),
                UserDto.PermissionDto(id = 2, permission = "END_USER")
            )
        ),
        UserDto(
            id = "d9f6c4e2-4a12-4c8f-bb8a-21e4d8f9c3a0",
            fullName = "Nadia Ali",
            username = "nadia_ali_456",
            email = "nadia_ali@example.com",
            country = "Palestine",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "DELIVERY"),
                UserDto.PermissionDto(id = 2, permission = "DRIVER")
            )
        ),
        UserDto(
            id = "a1b2c3d4-5e6f-7a8b-9c0d-1e2f3a4b5c6d",
            fullName = "Lina Mahmoud",
            username = "lina_mahmoud_123",
            email = "lina_mahmoud@example.com",
            country = "Egypt",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "END_USER"),
                UserDto.PermissionDto(id = 2, permission = "DELIVERY")
            )
        ),
        UserDto(
            id = "b5c6d7e8-9f0a-1b2c-3d4e-5f6a7b8c9d0",
            fullName = "Ali Khalid",
            username = "ali_khalid_456",
            email = "ali_khalid@example.com",
            country = "Iraq",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "RESTAURANT"),
                UserDto.PermissionDto(id = 2, permission = "DRIVER"),
                UserDto.PermissionDto(id = 3, permission = "ADMIN")
            )
        ),
        UserDto(
            id = "c9d0e1f2-a3b4-c5d6-e7f8-a9b0c1d2e3f",
            fullName = "Sami Ahmed",
            username = "sami_ahmed_789",
            email = "sami_ahmed@example.com",
            country = "Syria",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "SUPPORT")
            )
        ),
        UserDto(
            id = "d2e3f4a5-b6c7-d8e9-f0a1-b2c3d4e5f6a",
            fullName = "Layla Abbas",
            username = "layla_abbas_246",
            email = "layla_abbas@example.com",
            country = "Palestine",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "END_USER"),
                UserDto.PermissionDto(id = 2, permission = "DRIVER"),
                UserDto.PermissionDto(id = 3, permission = "DELIVERY")
            )
        ),
        UserDto(
            id = "f0a1b2c3-d4e5-f6a7-b8c9-d0e1f2a3b4c",
            fullName = "Yara Hassan",
            username = "yara_hassan_123",
            email = "yara_hassan@example.com",
            country = "Egypt",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "RESTAURANT"),
                UserDto.PermissionDto(id = 2, permission = "SUPPORT")
            )
        ),
        UserDto(
            id = "b4c5d6e7-f8a9-b0c1-d2e3-f4a5b6c7d8e",
            fullName = "Amir Ali",
            username = "amir_ali_456",
            email = "amir_ali@example.com",
            country = "Iraq",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "DELIVERY"),
                UserDto.PermissionDto(id = 2, permission = "ADMIN")
            )
        ),
        UserDto(
            id = "b3c4d5e6-f7a8-b9c0-d1e2-f3a4b5c6d7e",
            fullName = "Khaled Abbas",
            username = "khaled_abbas_246",
            email = "khaled_abbas@example.com",
            country = "Palestine",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "SUPPORT"),
                UserDto.PermissionDto(id = 2, permission = "DELIVERY"),
                UserDto.PermissionDto(id = 3, permission = "DRIVER")
            )
        ),
        UserDto(
            id = "e2f3a4b5-c6d7e8f9-a0b1c2d3-e4f5a6b7c8d",
            fullName = "Nour Hamdi",
            username = "nour_hamdi_123",
            email = "nour_hamdi@example.com",
            country = "Egypt",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "END_USER"),
                UserDto.PermissionDto(id = 2, permission = "DELIVERY")
            )
        ),
        UserDto(
            id = "d3e4f5a6-b7c8d9e0-f1a2b3c4-d5e6f7a8b9c",
            fullName = "Rami Mustafa",
            username = "rami_mustafa_456",
            email = "rami_mustafa@example.com",
            country = "Iraq",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "RESTAURANT"),
                UserDto.PermissionDto(id = 2, permission = "DRIVER")
            )
        ),
        UserDto(
            id = "c4d5e6f7-a8b9c0d1-e2f3a4b5-c6d7e8f9a0b",
            fullName = "Layla Nasser",
            username = "layla_nasser_789",
            email = "layla_nasser@example.com",
            country = "Syria",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "SUPPORT"),
                UserDto.PermissionDto(id = 2, permission = "DELIVERY"),
                UserDto.PermissionDto(id = 3, permission = "ADMIN")
            )
        ),
        UserDto(
            id = "b5c6d7e8-f9a0b1c2-d3e4f5a6-b7c8d9e0f1a",
            fullName = "Kareem Omar",
            username = "kareem_omar_246",
            email = "kareem_omar@example.com",
            country = "Palestine",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "DRIVER"),
                UserDto.PermissionDto(id = 2, permission = "SUPPORT")
            )
        ),
        UserDto(
            id = "a0b1c2d3-e4f5a6b7-c8d9e0f1-a2b3c4d5e6f",
            fullName = "Karim Hamza",
            username = "karim_hamza_123",
            email = "karim_hamza@example.com",
            country = "Egypt",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "DELIVERY"),
                UserDto.PermissionDto(id = 2, permission = "ADMIN")
            )
        ),
        UserDto(
            id = "c2d3e4f5-a6b7c8d9-e0f1a2b3-c4d5e6f7a8b",
            fullName = "Hanaa Ali",
            username = "hanaa_ali_456",
            email = "hanaa_ali@example.com",
            country = "Iraq",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "SUPPORT"),
                UserDto.PermissionDto(id = 2, permission = "DRIVER")
            )
        ),
        UserDto(
            id = "e6f7a8b9-c0d1e2f3-a4b5c6d7-e8f9a0b1c2d",
            fullName = "Omar Nader",
            username = "omar_nader_789",
            email = "omar_nader@example.com",
            country = "Syria",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "END_USER"),
                UserDto.PermissionDto(id = 2, permission = "DELIVERY"),
                UserDto.PermissionDto(id = 3, permission = "ADMIN")
            )
        ),
        UserDto(
            id = "a4b5c6d7-e8f9a0b1-c2d3e4f5-a6b7c8d9e0f",
            fullName = "Laila Hamed",
            username = "laila_hamed_246",
            email = "laila_hamed@example.com",
            country = "Palestine",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "SUPPORT"),
                UserDto.PermissionDto(id = 2, permission = "DRIVER"),
                UserDto.PermissionDto(id = 3, permission = "END_USER")
            )
        ),
        UserDto(
            id = "e8f9a0b1-c2d3e4f5-a6b7c8d9-e0f1a2b3c4d",
            fullName = "Yasmine Hussein",
            username = "yasmine_hussein_123",
            email = "yasmine_hussein@example.com",
            country = "Egypt",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "RESTAURANT"),
                UserDto.PermissionDto(id = 2, permission = "END_USER")
            )
        ),
        UserDto(
            id = "a2b3c4d5-e6f7a8b9-c0d1e2f3-a4b5c6d7e8f",
            fullName = "Hassan Ali",
            username = "hassan_ali_456",
            email = "hassan_ali@example.com",
            country = "Iraq",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "DRIVER"),
                UserDto.PermissionDto(id = 2, permission = "ADMIN")
            )
        ),
        UserDto(
            id = "c6d7e8f9-a0b1c2d3-e4f5a6b7-c8d9e0f1a2b",
            fullName = "Leila Nasser",
            username = "leila_nasser_789",
            email = "leila_nasser@example.com",
            country = "Syria",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "SUPPORT"),
                UserDto.PermissionDto(id = 2, permission = "DELIVERY")
            )
        ),
        UserDto(
            id = "e0f1a2b3-c4d5e6f7-a8b9c0d1-e2f3a4b5c6d",
            fullName = "Khaled Abbas",
            username = "khaled_abbas_246",
            email = "khaled_abbas@example.com",
            country = "Palestine",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "DELIVERY"),
                UserDto.PermissionDto(id = 2, permission = "SUPPORT"),
                UserDto.PermissionDto(id = 3, permission = "ADMIN")
            )
        ),
        UserDto(
            id = "c4d5e6f7-a8b9c0d1-e2f3a4b5-c6d7e8f9a0b",
            fullName = "Mona Ahmed",
            username = "mona_ahmed_123",
            email = "mona_ahmed@example.com",
            country = "Egypt",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "SUPPORT"),
                UserDto.PermissionDto(id = 2, permission = "DRIVER")
            )
        ),
        UserDto(
            id = "e2f3a4b5-c6d7e8f9-a0b1c2d3-e4f5a6b7c8d",
            fullName = "Nasser Ali",
            username = "nasser_ali_456",
            email = "nasser_ali@example.com",
            country = "Iraq",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "DELIVERY"),
                UserDto.PermissionDto(id = 2, permission = "ADMIN")
            )
        ),
        UserDto(
            id = "a0b1c2d3-e4f5a6b7-c8d9e0f1-a2b3c4d5e6f",
            fullName = "Yasmin Nader",
            username = "yasmin_nader_789",
            email = "yasmin_nader@example.com",
            country = "Syria",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "END_USER"),
                UserDto.PermissionDto(id = 2, permission = "RESTAURANT"),
                UserDto.PermissionDto(id = 3, permission = "DELIVERY")
            )
        ),
        UserDto(
            id = "b1c2d3e4-f5a6b7c8-d9e0f1a2-b3c4d5e6f7a8",
            fullName = "Sara Mahmoud",
            username = "sara_mahmoud_123",
            email = "sara_mahmoud@example.com",
            country = "Egypt",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "END_USER"),
                UserDto.PermissionDto(id = 2, permission = "ADMIN")
            )
        ),
        UserDto(
            id = "b7c8d9e0-f1a2b3c4-d5e6f7a8-b9c0d1e2f3a4",
            fullName = "Ahmad Abbas",
            username = "ahmad_abbas_246",
            email = "ahmad_abbas@example.com",
            country = "Palestine",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "RESTAURANT"),
                UserDto.PermissionDto(id = 2, permission = "SUPPORT")
            )
        ),
        UserDto(
            id = "f9a0b1c2-d3e4f5a6-b7c8d9e0-f1a2b3c4d5e6",
            fullName = "Ahmed Ibrahim",
            username = "ahmed_ibrahim_123",
            email = "ahmed_ibrahim@example.com",
            country = "Egypt",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "DELIVERY"),
                UserDto.PermissionDto(id = 2, permission = "END_USER")
            )
        ),
        UserDto(
            id = "c4d5e6f7-a8b9c0d1-e2f3a4b5-c6d7e8f9a0b",
            fullName = "Samar Ali",
            username = "samar_ali_456",
            email = "samar_ali@example.com",
            country = "Iraq",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "SUPPORT"),
                UserDto.PermissionDto(id = 2, permission = "ADMIN"),
                UserDto.PermissionDto(id = 3, permission = "RESTAURANT")
            )
        ),
        UserDto(
            id = "e8f9a0b1-c2d3e4f5-a6b7c8d9-e0f1a2b3c4d5",
            fullName = "Layla Hussein",
            username = "layla_hussein_789",
            email = "layla_hussein@example.com",
            country = "Syria",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "DRIVER"),
                UserDto.PermissionDto(id = 2, permission = "DELIVERY")
            )
        ),
        UserDto(
            id = "b3c4d5e6-f7a8b9c0-d1e2f3a4-b5c6d7e8f9a0",
            fullName = "Rami Nasser",
            username = "rami_nasser_246",
            email = "rami_nasser@example.com",
            country = "Palestine",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "SUPPORT"),
                UserDto.PermissionDto(id = 2, permission = "END_USER")
            )
        ),
        UserDto(
            id = "11111111-2222-3333-4444-555555555555",
            fullName = "Sarah Johnson",
            username = "sarah_johnson_789",
            email = "sarah_johnson@example.com",
            country = "United States",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "SUPPORT"),
                UserDto.PermissionDto(id = 2, permission = "END_USER")
            )
        ),
        UserDto(
            id = "22222222-3333-4444-5555-666666666666",
            fullName = "Samuel Lee",
            username = "samuel_lee_123",
            email = "samuel_lee@example.com",
            country = "Canada",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "SUPPORT"),
                UserDto.PermissionDto(id = 2, permission = "END_USER")
            )
        ),

        UserDto(
            id = "33333333-4444-5555-6666-777777777777",
            fullName = "Sophia Martinez",
            username = "sophia_martinez_456",
            email = "sophia_martinez@example.com",
            country = "Spain",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "SUPPORT"),
                UserDto.PermissionDto(id = 2, permission = "END_USER")
            )
        ),

        UserDto(
            id = "44444444-5555-6666-7777-888888888888",
            fullName = "Scott Anderson",
            username = "scott_anderson_234",
            email = "scott_anderson@example.com",
            country = "Australia",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "SUPPORT"),
                UserDto.PermissionDto(id = 2, permission = "END_USER")
            )
        ),

        UserDto(
            id = "55555555-6666-7777-8888-999999999999",
            fullName = "Samantha Wilson",
            username = "samantha_wilson_567",
            email = "samantha_wilson@example.com",
            country = "United Kingdom",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "SUPPORT"),
                UserDto.PermissionDto(id = 2, permission = "END_USER")
            )
        ),

        UserDto(
            id = "66666666-7777-8888-9999-000000000000",
            fullName = "Sebastian Brown",
            username = "sebastian_brown_890",
            email = "sebastian_brown@example.com",
            country = "Germany",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "SUPPORT"),
                UserDto.PermissionDto(id = 2, permission = "END_USER")
            )
        ),

        UserDto(
            id = "77777777-8888-9999-0000-111111111111",
            fullName = "Stella Davis",
            username = "stella_davis_345",
            email = "stella_davis@example.com",
            country = "France",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "SUPPORT"),
                UserDto.PermissionDto(id = 2, permission = "END_USER")
            )
        ),

        UserDto(
            id = "88888888-9999-0000-1111-222222222222",
            fullName = "Stephen Smith",
            username = "stephen_smith_678",
            email = "stephen_smith@example.com",
            country = "New Zealand",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "SUPPORT"),
                UserDto.PermissionDto(id = 2, permission = "END_USER")
            )
        ),

        UserDto(
            id = "99999999-0000-1111-2222-333333333333",
            fullName = "Selena White",
            username = "selena_white_901",
            email = "selena_white@example.com",
            country = "Brazil",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "SUPPORT"),
                UserDto.PermissionDto(id = 2, permission = "END_USER")
            )
        ),

        UserDto(
            id = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee",
            fullName = "Sylvester Green",
            username = "sylvester_green_234",
            email = "sylvester_green@example.com",
            country = "South Africa",
            permissions = listOf(
                UserDto.PermissionDto(id = 1, permission = "SUPPORT"),
                UserDto.PermissionDto(id = 2, permission = "END_USER")
            )
        )
    ).toEntity()

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
                    status = 2,
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
                    status = 2,
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
                    status = 2,
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
            )
        )
        restaurant.addAll(
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
    }
    override suspend fun  getUserData() = "asia"

    override suspend fun getUsers(
        byPermissions: List<Permission>,
        byCountries: List<String>,
        page: Int,
        numberOfUsers: Int
    ): DataWrapper<User> {
        val filteredUsers = fakeUsers.filter { user ->
            (byPermissions.isEmpty() || user.permission.any { it in byPermissions })
                    && (byCountries.isEmpty() || user.country in byCountries)
        }

        val totalUsers = filteredUsers.size
        val numberOfPages = ceil(totalUsers.toDouble() / numberOfUsers).toInt()
        val startIndex = (page - 1) * numberOfUsers
        val endIndex = startIndex + numberOfUsers

        val paginatedUsers = filteredUsers.subList(startIndex, endIndex.coerceAtMost(totalUsers))

        return DataWrapperDto(
            totalPages = numberOfPages,
            result = paginatedUsers,
            totalResult = totalUsers
        ).toEntity()
    }

    override suspend fun getTaxis(page: Int, numberOfTaxis: Int): DataWrapper<Taxi> {
        val taxis = taxis.toEntity()
        val startIndex = (page - 1) * numberOfTaxis
        val endIndex = startIndex + numberOfTaxis
        val numberOfPages = ceil(taxis.size / (numberOfTaxis * 1.0)).toInt()
        return try {
            DataWrapperDto(
                totalPages = numberOfPages,
                result = taxis.subList(startIndex, endIndex.coerceAtMost(taxis.size)),
                totalResult = taxis.size
            ).toEntity()
        } catch (e: Exception) {
            DataWrapperDto(
                totalPages = numberOfPages,
                result = taxis,
                totalResult = taxis.size
            ).toEntity()
        }
    }

    override suspend fun createTaxi(taxi: NewTaxiInfo): Taxi {
        val taxiDto = taxi.toDto()
        taxis.add(
            TaxiDto(
                id = UUID.randomUUID().toString(),
                plateNumber = taxiDto.plateNumber,
                color = taxiDto.color,
                type = taxiDto.type,
                seats = taxiDto.seats,
                username = taxiDto.username,
                status = null,
                trips = null
            )
        )
        return taxis.last().toEntity()
    }

    override suspend fun searchTaxisByUsername(username: String, page: Int, numberOfTaxis: Int): DataWrapper<Taxi> {
        val taxis = taxis.filter {
            it.username?.startsWith(username, true) ?: false
        }.toEntity()
        val startIndex = (page - 1) * numberOfTaxis
        val endIndex = startIndex + numberOfTaxis
        val numberOfPages = ceil(taxis.size / (numberOfTaxis * 1.0)).toInt()
        return try {
            DataWrapperDto(
                totalPages = numberOfPages,
                result = taxis.subList(startIndex, endIndex.coerceAtMost(taxis.size)),
                totalResult = taxis.size
            ).toEntity()
        } catch (e: Exception) {
            DataWrapperDto(
                totalPages = numberOfPages,
                result = taxis,
                totalResult = taxis.size
            ).toEntity()
        }
    }

    override suspend fun filterTaxis(
        taxi: TaxiFiltration,
        page: Int,
        numberOfTaxis: Int
    ): DataWrapper<Taxi> {
        val taxiDto = taxi.toDto()
        val taxisFiltered = taxis.filter {
            it.color == taxiDto.color && it.seats == taxiDto.seats && it.status == taxiDto.status
        }.toEntity()

        val startIndex = (page - 1) * numberOfTaxis
        val endIndex = startIndex + numberOfTaxis
        val numberOfPages = ceil(taxisFiltered.size / (numberOfTaxis * 1.0)).toInt()
        return try {
            DataWrapperDto(
                totalPages = numberOfPages,
                result = taxisFiltered.subList(
                    startIndex,
                    endIndex.coerceAtMost(taxisFiltered.size)
                ),
                totalResult = taxisFiltered.size
            ).toEntity()
        } catch (e: Exception) {
            DataWrapperDto(
                totalPages = numberOfPages, result = taxisFiltered, totalResult = taxisFiltered.size
            ).toEntity()
        }
    }

    override suspend fun getPdfTaxiReport() {
        val taxiReportFile = createTaxiPDFReport()
        localGateway.saveTaxiReport(taxiReportFile)
    }

    override suspend fun getRestaurants(
        pageNumber: Int,
        numberOfRestaurantsInPage: Int,
        restaurantName: String,
        rating: Double?,
        priceLevel: Int?
    ): DataWrapper<Restaurant> {
        var restaurants = restaurant.toEntity()
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

    override suspend fun createRestaurant(restaurant: AddRestaurant): Restaurant {
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

    override suspend fun getCurrentLocation(): Location {
        return Location(location = "30.044420,31.235712")
    }

    override suspend fun searchUsers(
        query: String,
        byPermissions: List<Permission>,
        byCountries: List<String>,
        page: Int,
        numberOfUsers: Int
    ): DataWrapper<User> {
        val filteredUsers = fakeUsers.filter {
            it.fullName.startsWith(query, true) || it.username.startsWith(query, true)
        }.toMutableList()

        if (byPermissions.isNotEmpty()) filteredUsers.retainAll { user ->
            user.permission.any { permission -> byPermissions.contains(permission) }
        }

        if (byCountries.isNotEmpty()) filteredUsers.retainAll { user ->
            byCountries.any { it == user.country }
        }

        val startIndex = (page - 1) * numberOfUsers
        val endIndex = startIndex + numberOfUsers
        val numberOfPages = ceil(filteredUsers.size / (numberOfUsers * 1.0)).toInt()
        return try {
            DataWrapperDto(
                totalPages = numberOfPages,
                result = filteredUsers.subList(startIndex, endIndex.coerceAtMost(filteredUsers.size)),
                totalResult = filteredUsers.size
            ).toEntity()
        } catch (e: Exception) {
            DataWrapperDto(
                totalPages = numberOfPages,
                result = filteredUsers,
                totalResult = filteredUsers.size
            ).toEntity()
        }
    }

    override suspend fun filterUsers(
        permissions: List<Permission>,
        countries: List<String>,
        page: Int,
        numberOfUsers: Int,
    ): DataWrapper<User> {
        val filteredUsers = fakeUsers.filter { user ->
            user.permission.containsAll(permissions) && countries.any { it == user.country }
        }
//        val filteredUsers = fakeUsers.filter { user ->
//            user.permission.any { permission -> permissions.any { it.name == permission.name } } && countries.any { it == user.country }
//        }
        val startIndex = (page - 1) * numberOfUsers
        val endIndex = (page - 1) * numberOfUsers
        val numberOfPages = ceil(filteredUsers.size / (numberOfUsers * 1.0)).toInt()
        return try {
            DataWrapperDto(
                totalPages = numberOfPages,
                result = filteredUsers.subList(startIndex, endIndex.coerceAtMost(filteredUsers.size)),
                totalResult = filteredUsers.size
            ).toEntity()
        } catch (e: Exception) {
            DataWrapperDto(
                totalPages = numberOfPages,
                result = filteredUsers,
                totalResult = filteredUsers.size
            ).toEntity()
        }
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
        dataExtractor: (T) -> List<Any>
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
        dataExtractor: (T) -> List<Any>
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
        pageHeight: Float
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
        titleY: Float
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