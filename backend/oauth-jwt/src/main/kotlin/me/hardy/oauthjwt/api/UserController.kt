package me.hardy.oauthjwt.api

import me.hardy.oauthjwt.dto.ResponseDTO
import me.hardy.oauthjwt.dto.UserInfoDTO
import me.hardy.oauthjwt.security.principal.UserPrincipal
import me.hardy.oauthjwt.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user-info")
class UserController(
    private val userService: UserService
) {

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    fun getUserInfo(@AuthenticationPrincipal userPrincipal: UserPrincipal): ResponseEntity<ResponseDTO<UserInfoDTO>> {
        val userInfo = userService.getUserInfo(userPrincipal.getUserId()!!)
        val response = ResponseDTO(userInfo)
        return ResponseEntity.ok(response)
    }
}