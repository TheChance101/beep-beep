package org.thechance.common.data.remote.gateway


import org.thechance.common.data.local.LocalGateway
import org.thechance.common.data.remote.mapper.toDto
import org.thechance.common.data.remote.mapper.toEntity
import org.thechance.common.data.remote.model.AdminDto
import org.thechance.common.data.remote.model.DataWrapperDto
import org.thechance.common.data.remote.model.TaxiDto
import org.thechance.common.data.remote.model.UserDto
import org.thechance.common.data.remote.model.toEntity
import org.thechance.common.data.service.IFakeService
import org.thechance.common.domain.entity.AddRestaurant
import org.thechance.common.domain.entity.AddTaxi
import org.thechance.common.domain.entity.Admin
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.Location
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.entity.User
import org.thechance.common.domain.entity.UserTokens
import org.thechance.common.domain.getway.IRemoteGateway
import java.util.UUID
import kotlin.math.ceil
import kotlin.math.floor

class FakeRemoteGateway(
    private val fakeService: IFakeService,
    private val localGateway: LocalGateway
) : IRemoteGateway {
    override fun getUserData(): Admin =
        AdminDto(fullName = "asia").toEntity()

    override fun getUsers(page: Int, numberOfUsers: Int): DataWrapper<User> {
        val users = listOf(
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
                id = "d8e9f0a1-b2c3-d4e5-f6a7-b8c9d0e1f2a",
                fullName = "Layla Hussein",
                username = "layla_hussein_789",
                email = "layla_hussein@example.com",
                country = "Syria",
                permissions = listOf(
                    UserDto.PermissionDto(id = 1, permission = "END_USER"),
                    UserDto.PermissionDto(id = 2, permission = "DRIVER")
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
        ).toEntity()
        val startIndex = (page - 1) * numberOfUsers
        val endIndex = startIndex + numberOfUsers
        val numberOfPages = ceil(users.size / (numberOfUsers * 1.0)).toInt()
        return try {
            DataWrapperDto(
                totalPages = numberOfPages,
                result = users.subList(startIndex, endIndex.coerceAtMost(users.size)),
                totalResult = users.size
            ).toEntity()
        } catch (e: Exception) {
            DataWrapperDto(
                totalPages = numberOfPages,
                result = users,
                totalResult = users.size
            ).toEntity()
        }
    }

    private val taxis = mutableListOf<TaxiDto>()

    init {
        taxis.addAll(
            listOf(
                TaxiDto(
                    id = "1",
                    plateNumber = "ABC123",
                    color = 1,
                    type = "Sedan",
                    seats = 4,
                    username = "john_doe",
                    status = 1,
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
                )
            )
        )
    }

    override suspend fun getTaxis(): List<Taxi> {
        return fakeService.getTaxis().toEntity()
    }


    override suspend fun createTaxi(taxi: AddTaxi): Taxi {
        val taxiDto = taxi.toDto()
       return fakeService.addTaxi(TaxiDto(
                id = UUID.randomUUID().toString(),
                plateNumber = taxiDto.plateNumber,
                color = taxiDto.color,
                type = taxiDto.type,
                seats = taxiDto.seats,
                username = taxiDto.username,
            )
        ).toEntity()
    }

    override suspend fun findTaxiByUsername(username: String): List<Taxi> {
        return fakeService.findTaxisByUsername(username).toEntity()
    }

    override suspend fun getPdfTaxiReport() {
        val taxiReportFile = fakeService.getTaxiPDFReport()
        localGateway.saveTaxiReport(taxiReportFile)
    }

    override suspend fun getRestaurants(): List<Restaurant> {
        return fakeService.getRestaurants().toEntity()
    }

    override suspend fun searchRestaurantsByRestaurantName(restaurantName: String): List<Restaurant> {
        return fakeService.searchRestaurantsByRestaurantName(restaurantName).toEntity()
    }

    override suspend fun filterRestaurants(rating: Double, priceLevel: Int): List<Restaurant> {
        return filterRestaurants(getRestaurants(), rating, priceLevel)
    }

    override suspend fun searchFilterRestaurants(
        restaurantName: String,
        rating: Double,
        priceLevel: Int
    ): List<Restaurant> {
        return filterRestaurants(
            searchRestaurantsByRestaurantName(restaurantName),
            rating,
            priceLevel
        )
    }

    override suspend fun loginUser(username: String, password: String): UserTokens {
        return UserTokens("", "")
    }

    private fun filterRestaurants(
        restaurants: List<Restaurant>,
        rating: Double,
        priceLevel: Int
    ): List<Restaurant> {
        return restaurants.filter {
            it.priceLevel == priceLevel &&
                    when {
                        rating.rem(1) > 0.89 || rating.rem(1) == 0.0 || rating.rem(1) > 0.5
                        -> it.rating in floor(rating) - 0.1..0.49 + floor(rating)

                        else -> it.rating in 0.5 + floor(rating)..0.89 + floor(rating)
                    }
        }
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
}