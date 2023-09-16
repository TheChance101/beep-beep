package org.thechance.common.data.remote.fakegateway

import org.thechance.common.data.remote.mapper.toEntity
import org.thechance.common.data.remote.model.DataWrapperDto
import org.thechance.common.data.remote.model.UserDto
import org.thechance.common.data.remote.model.toEntity
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.Permission
import org.thechance.common.domain.entity.User
import org.thechance.common.domain.getway.IUsersGateway
import kotlin.math.ceil

class UsersFakeGateway : IUsersGateway {

    override suspend fun getUserData(): String = "aaaa"

    override suspend fun getUsers(
        query: String?,
        byPermissions: List<Permission>,
        byCountries: List<String>,
        page: Int,
        numberOfUsers: Int
    ): DataWrapper<User> {
        var filteredUsers = fakeUsers
        query?.let { searchQuery ->
            if (searchQuery.isNotEmpty()) {
                filteredUsers = fakeUsers.filter {
                    it.fullName.startsWith(searchQuery, true) || it.username.startsWith(query, true)
                }
            }
        }
        filteredUsers = if (byPermissions.isNotEmpty()) filteredUsers.filter { user ->
            user.permission.containsAll(byPermissions)
        } else filteredUsers

        filteredUsers = if (byCountries.isNotEmpty()) filteredUsers.filter { user ->
            byCountries.any { it == user.country }
        } else filteredUsers

        val startIndex = (page - 1) * numberOfUsers
        val endIndex = startIndex + numberOfUsers
        val numberOfPages = ceil(filteredUsers.size / (numberOfUsers * 1.0)).toInt()
        return try {
            DataWrapperDto(
                totalPages = numberOfPages,
                result = filteredUsers.subList(
                    startIndex,
                    endIndex.coerceAtMost(filteredUsers.size)
                ),
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

    override suspend fun loginUser(username: String, password: String): Pair<String, String> {
        return Pair("token", "refreshToken")
    }

    override suspend fun deleteUser(userId: String): Boolean {
        return true
    }

    override suspend fun getLastRegisteredUsers(limit : Int): List<User> {
        return  fakeUsers.subList(0, limit)
    }

    private val fakeUsers = listOf(
        UserDto(
            id = "c4425a0e-9f0a-4df1-bcc1-6dd96322a990",
            fullName = "mohammed sayed",
            username = "mohammed_sayed",
            email = "elzamalk@example.com",
            country = "Egypt",
            permission = 1,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "f7b087da-8c02-417b-a3db-54c82b5ff5b4",
            fullName = "asia",
            username = "asia",
            email = "asia@example.com",
            country = "Iraq",
            permission = 1,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "3e1f5d4a-8317-4f13-aa89-2c094652e6a3",
            fullName = "ali",
            username = "ali_jamal",
            email = "ali_jamal@example.com",
            country = "Iraq",
            permission = 3,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "c3d8fe2b-6d36-47ea-964a-57d45e780bce",
            fullName = "mustafa",
            username = "mustafa_246",
            email = "mustafa_246@example.com",
            country = "Syria",
            permission = 4,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "7a1bfe39-4b2c-4f76-bde0-82da2eaf9e99",
            fullName = "sarah ali",
            username = "sarah_ali_567",
            email = "sarah_ali_567@example.com",
            country = "Palestine",
            permission = 5,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "8c90c4c6-1e69-47f3-aa59-2edcd6f0057b",
            fullName = "Jane Davis",
            username = "jane_davis_890",
            email = "jane_davis@example.com",
            country = "Other",
            permission = 3,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "8c90c4c6-1e69-47f3-aa59-2edcd6f0057b",
            fullName = "Ahmed mohsen",
            username = "ahmed_mohsen_890",
            email = "ahmed_mohsen@example.com",
            country = "Palestine",
            permission = 6,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "8c90c4c6-1e69-47f3-aa59-2edcd6f0057b",
            fullName = "cristiano alberto",
            username = "cristiano_real_600",
            email = "cristiano_real@example.com",
            country = "Other",
            permission = 3,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "c4425a0e-9f0a-4df1-bcc1-6dd96322a990",
            fullName = "Ahmed Hassan",
            username = "ahmed_hassan_123",
            email = "ahmed_hassan@example.com",
            country = "Egypt",
            permission = 8,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "f7b087da-8c02-417b-a3db-54c82b5ff5b4",
            fullName = "Zainab Ali",
            username = "zainab_ali_456",
            email = "zainab_ali@example.com",
            country = "Iraq",
            permission = 7,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "3e1f5d4a-8317-4f13-aa89-2c094652e6a3",
            fullName = "Mohammed Khalid",
            username = "mohammed_khalid_789",
            email = "mohammed_khalid@example.com",
            country = "Syria",
            permission = 9,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "c3d8fe2b-6d36-47ea-964a-57d45e780bce",
            fullName = "Leila Ahmed",
            username = "leila_ahmed_246",
            email = "leila_ahmed@example.com",
            country = "Palestine",
            permission = 2,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "7a1bfe39-4b2c-4f76-bde0-82da2eaf9e99",
            fullName = "Youssef Salem",
            username = "youssef_salem_567",
            email = "youssef_salem@example.com",
            country = "Egypt",
            permission = 8,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "8c90c4c6-1e69-47f3-aa59-2edcd6f0057b",
            fullName = "Sana Ali",
            username = "sana_ali_890",
            email = "sana_ali@example.com",
            country = "Iraq",
            permission = 5,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "c28e9e91-3c3b-4e46-9f53-13b380e2a4d5",
            fullName = "Layla Mohamed",
            username = "layla_mohamed_123",
            email = "layla_mohamed@example.com",
            country = "Syria",
            permission = 7,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "f5d5b4a1-6a1f-4c8d-8b1e-7a2e90e38d3c",
            fullName = "Ali Abdullah",
            username = "ali_abdullah_456",
            email = "ali_abdullah@example.com",
            country = "Palestine",
            permission = 4,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "f5d5b4a1-6a1f-4c8d-8b1e-7a2e90e38d3c",
            fullName = "Omar Khalid",
            username = "omar_khalid_123",
            email = "omar_khalid@example.com",
            country = "Syria",
            permission = 8,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "d9f6c4e2-4a12-4c8f-bb8a-21e4d8f9c3a0",
            fullName = "Nadia Ali",
            username = "nadia_ali_456",
            email = "nadia_ali@example.com",
            country = "Palestine",
            permission = 3,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "a1b2c3d4-5e6f-7a8b-9c0d-1e2f3a4b5c6d",
            fullName = "Lina Mahmoud",
            username = "lina_mahmoud_123",
            email = "lina_mahmoud@example.com",
            country = "Egypt",
            permission = 2,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "b5c6d7e8-9f0a-1b2c-3d4e-5f6a7b8c9d0",
            fullName = "Ali Khalid",
            username = "ali_khalid_456",
            email = "ali_khalid@example.com",
            country = "Iraq",
            permission = 1,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "c9d0e1f2-a3b4-c5d6-e7f8-a9b0c1d2e3f",
            fullName = "Sami Ahmed",
            username = "sami_ahmed_789",
            email = "sami_ahmed@example.com",
            country = "Syria",
            permission = 6,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "d2e3f4a5-b6c7-d8e9-f0a1-b2c3d4e5f6a",
            fullName = "Layla Abbas",
            username = "layla_abbas_246",
            email = "layla_abbas@example.com",
            country = "Palestine",
            permission = 5,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "f0a1b2c3-d4e5-f6a7-b8c9-d0e1f2a3b4c",
            fullName = "Yara Hassan",
            username = "yara_hassan_123",
            email = "yara_hassan@example.com",
            country = "Egypt",
            permission = 4,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "b4c5d6e7-f8a9-b0c1-d2e3-f4a5b6c7d8e",
            fullName = "Amir Ali",
            username = "amir_ali_456",
            email = "amir_ali@example.com",
            country = "Iraq",
            permission = 3,
        ),
        UserDto(
            id = "d8e9f0a1-b2c3-d4e5-f6a7-b8c9d0e1f2a",
            fullName = "Layla Hussein",
            username = "layla_hussein_789",
            email = "layla_hussein@example.com",
            country = "Syria",
            permission = 2,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "b3c4d5e6-f7a8-b9c0-d1e2-f3a4b5c6d7e",
            fullName = "Khaled Abbas",
            username = "khaled_abbas_246",
            email = "khaled_abbas@example.com",
            country = "Palestine",
            permission = 1,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "e2f3a4b5-c6d7e8f9-a0b1c2d3-e4f5a6b7c8d",
            fullName = "Nour Hamdi",
            username = "nour_hamdi_123",
            email = "nour_hamdi@example.com",
            country = "Egypt",
            permission = 6,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "d3e4f5a6-b7c8d9e0-f1a2b3c4-d5e6f7a8b9c",
            fullName = "Rami Mustafa",
            username = "rami_mustafa_456",
            email = "rami_mustafa@example.com",
            country = "Iraq",
            permission = 5,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "c4d5e6f7-a8b9c0d1-e2f3a4b5-c6d7e8f9a0b",
            fullName = "Layla Nasser",
            username = "layla_nasser_789",
            email = "layla_nasser@example.com",
            country = "Syria",
            permission = 4,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "b5c6d7e8-f9a0b1c2-d3e4f5a6-b7c8d9e0f1a",
            fullName = "Kareem Omar",
            username = "kareem_omar_246",
            email = "kareem_omar@example.com",
            country = "Palestine",
            permission = 3,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "a0b1c2d3-e4f5a6b7-c8d9e0f1-a2b3c4d5e6f",
            fullName = "Karim Hamza",
            username = "karim_hamza_123",
            email = "karim_hamza@example.com",
            country = "Egypt",
            permission = 2,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "c2d3e4f5-a6b7c8d9-e0f1a2b3-c4d5e6f7a8b",
            fullName = "Hanaa Ali",
            username = "hanaa_ali_456",
            email = "hanaa_ali@example.com",
            country = "Iraq",
            permission = 1,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "e6f7a8b9-c0d1e2f3-a4b5c6d7-e8f9a0b1c2d",
            fullName = "Omar Nader",
            username = "omar_nader_789",
            email = "omar_nader@example.com",
            country = "Syria",
            permission = 6,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "a4b5c6d7-e8f9a0b1-c2d3e4f5-a6b7c8d9e0f",
            fullName = "Laila Hamed",
            username = "laila_hamed_246",
            email = "laila_hamed@example.com",
            country = "Palestine",
            permission = 5,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "e8f9a0b1-c2d3e4f5-a6b7c8d9-e0f1a2b3c4d",
            fullName = "Yasmine Hussein",
            username = "yasmine_hussein_123",
            email = "yasmine_hussein@example.com",
            country = "Egypt",
            permission = 4,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "a2b3c4d5-e6f7a8b9-c0d1e2f3-a4b5c6d7e8f",
            fullName = "Hassan Ali",
            username = "hassan_ali_456",
            email = "hassan_ali@example.com",
            country = "Iraq",
            permission = 7,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "c6d7e8f9-a0b1c2d3-e4f5a6b7-c8d9e0f1a2b",
            fullName = "Leila Nasser",
            username = "leila_nasser_789",
            email = "leila_nasser@example.com",
            country = "Syria",
            permission = 8,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "e0f1a2b3-c4d5e6f7-a8b9c0d1-e2f3a4b5c6d",
            fullName = "Khaled Abbas",
            username = "khaled_abbas_246",
            email = "khaled_abbas@example.com",
            country = "Palestine",
            permission = 5,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "c4d5e6f7-a8b9c0d1-e2f3a4b5-c6d7e8f9a0b",
            fullName = "Mona Ahmed",
            username = "mona_ahmed_123",
            email = "mona_ahmed@example.com",
            country = "Egypt",
            permission = 2,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "e2f3a4b5-c6d7e8f9-a0b1c2d3-e4f5a6b7c8d",
            fullName = "Nasser Ali",
            username = "nasser_ali_456",
            email = "nasser_ali@example.com",
            country = "Iraq",
            permission = 1,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "a0b1c2d3-e4f5a6b7-c8d9e0f1-a2b3c4d5e6f",
            fullName = "Yasmin Nader",
            username = "yasmin_nader_789",
            email = "yasmin_nader@example.com",
            country = "Syria",
            permission = 7,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "b1c2d3e4-f5a6b7c8-d9e0f1a2-b3c4d5e6f7a8",
            fullName = "Sara Mahmoud",
            username = "sara_mahmoud_123",
            email = "sara_mahmoud@example.com",
            country = "Egypt",
            permission = 3,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "b7c8d9e0-f1a2b3c4-d5e6f7a8-b9c0d1e2f3a4",
            fullName = "Ahmad Abbas",
            username = "ahmad_abbas_246",
            email = "ahmad_abbas@example.com",
            country = "Palestine",
            permission = 2,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "f9a0b1c2-d3e4f5a6-b7c8d9e0-f1a2b3c4d5e6",
            fullName = "Ahmed Ibrahim",
            username = "ahmed_ibrahim_123",
            email = "ahmed_ibrahim@example.com",
            country = "Egypt",
            permission = 9,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "c4d5e6f7-a8b9c0d1-e2f3a4b5-c6d7e8f9a0b",
            fullName = "Samar Ali",
            username = "samar_ali_456",
            email = "samar_ali@example.com",
            country = "Iraq",
            permission = 4,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "e8f9a0b1-c2d3e4f5-a6b7c8d9-e0f1a2b3c4d5",
            fullName = "Layla Hussein",
            username = "layla_hussein_789",
            email = "layla_hussein@example.com",
            country = "Syria",
            permission = 3,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "b3c4d5e6-f7a8b9c0-d1e2f3a4-b5c6d7e8f9a0",
            fullName = "Rami Nasser",
            username = "rami_nasser_246",
            email = "rami_nasser@example.com",
            country = "Palestine",
            permission = 2,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "11111111-2222-3333-4444-555555555555",
            fullName = "Sarah Johnson",
            username = "sarah_johnson_789",
            email = "sarah_johnson@example.com",
            country = "United States",
            permission = 6,
            imageUrl = "dummy_img.png"
        ),
        UserDto(
            id = "22222222-3333-4444-5555-666666666666",
            fullName = "Samuel Lee",
            username = "samuel_lee_123",
            email = "samuel_lee@example.com",
            country = "Canada",
            permission = 4,
            imageUrl = "dummy_img.png"
        ),

        UserDto(
            id = "33333333-4444-5555-6666-777777777777",
            fullName = "Sophia Martinez",
            username = "sophia_martinez_456",
            email = "sophia_martinez@example.com",
            country = "Spain",
            permission = 5,
            imageUrl = "dummy_img.png"
        ),

        UserDto(
            id = "44444444-5555-6666-7777-888888888888",
            fullName = "Scott Anderson",
            username = "scott_anderson_234",
            email = "scott_anderson@example.com",
            country = "Australia",
            permission = 4,
            imageUrl = "dummy_img.png"
        ),

        UserDto(
            id = "55555555-6666-7777-8888-999999999999",
            fullName = "Samantha Wilson",
            username = "samantha_wilson_567",
            email = "samantha_wilson@example.com",
            country = "United Kingdom",
            permission = 4,
            imageUrl = "dummy_img.png"
        ),

        UserDto(
            id = "66666666-7777-8888-9999-000000000000",
            fullName = "Sebastian Brown",
            username = "sebastian_brown_890",
            email = "sebastian_brown@example.com",
            country = "Germany",
            permission = 4,
            imageUrl = "dummy_img.png"
        ),

        UserDto(
            id = "77777777-8888-9999-0000-111111111111",
            fullName = "Stella Davis",
            username = "stella_davis_345",
            email = "stella_davis@example.com",
            country = "France",
            permission = 3,
            imageUrl = "dummy_img.png"
        ),

        UserDto(
            id = "88888888-9999-0000-1111-222222222222",
            fullName = "Stephen Smith",
            username = "stephen_smith_678",
            email = "stephen_smith@example.com",
            country = "New Zealand",
            permission = 2,
            imageUrl = "dummy_img.png"
        ),

        UserDto(
            id = "99999999-0000-1111-2222-333333333333",
            fullName = "Selena White",
            username = "selena_white_901",
            email = "selena_white@example.com",
            country = "Brazil",
            permission = 1,
            imageUrl = "dummy_img.png"
        ),

        UserDto(
            id = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee",
            fullName = "Sylvester Green",
            username = "sylvester_green_234",
            email = "sylvester_green@example.com",
            country = "South Africa",
            permission = 2,
            imageUrl = "dummy_img.png"
        )
    ).toEntity()

}