package org.thechance.common.data.remote.gateway

import org.thechance.common.data.remote.model.AdminDto
import org.thechance.common.data.remote.model.UserDto
import org.thechance.common.data.remote.model.toEntity
import org.thechance.common.domain.entity.Admin
import org.thechance.common.domain.entity.User
import org.thechance.common.domain.getway.IRemoteGateway


class FakeRemoteGateway : IRemoteGateway {
    override fun getUserData(): Admin {
        return AdminDto(
            fullName = "asia",
        ).toEntity()
    }

    override fun getUsers(): List<User> {
        return listOf(
            UserDto(
                id = "c4425a0e-9f0a-4df1-bcc1-6dd96322a990",
                fullName = "John Smith",
                username = "john_smith_123",
                email = "john_smith@example.com",
                country = "USA",
                permissions = listOf(
                    UserDto.PermissionDto(id = 1, permission = "RESTAURANT"),
                    UserDto.PermissionDto(id = 2, permission = "DRIVER"),
                    UserDto.PermissionDto(id = 3, permission = "END_USER")
                )
            ),
            UserDto(
                id = "f7b087da-8c02-417b-a3db-54c82b5ff5b4",
                fullName = "Emily Johnson",
                username = "emily_johnson_456",
                email = "emily_johnson@example.com",
                country = "Canada",
                permissions = listOf(
                    UserDto.PermissionDto(id = 1, permission = "SUPPORT"),
                    UserDto.PermissionDto(id = 2, permission = "DELIVERY")
                )
            ),
            UserDto(
                id = "3e1f5d4a-8317-4f13-aa89-2c094652e6a3",
                fullName = "Michael Williams",
                username = "michael_williams_789",
                email = "michael_williams@example.com",
                country = "UK",
                permissions = listOf(
                    UserDto.PermissionDto(id = 1, permission = "ADMIN")
                )
            ),
            UserDto(
                id = "c3d8fe2b-6d36-47ea-964a-57d45e780bce",
                fullName = "Sarah Brown",
                username = "sarah_brown_246",
                email = "sarah_brown@example.com",
                country = "Australia",
                permissions = listOf(
                    UserDto.PermissionDto(id = 1, permission = "END_USER"),
                    UserDto.PermissionDto(id = 2, permission = "RESTAURANT")
                )
            ),
            UserDto(
                id = "7a1bfe39-4b2c-4f76-bde0-82da2eaf9e99",
                fullName = "David Jones",
                username = "david_jones_567",
                email = "david_jones@example.com",
                country = "USA",
                permissions = listOf(
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
                country = "Canada",
                permissions = listOf(
                    UserDto.PermissionDto(id = 1, permission = "END_USER"),
                    UserDto.PermissionDto(id = 2, permission = "DRIVER")
                )
            ),
        ).toEntity()
    }
}