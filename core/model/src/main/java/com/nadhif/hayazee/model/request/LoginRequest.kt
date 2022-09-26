package com.nadhif.hayazee.model.request


data class LoginRequest(override val email: String, override val password: String) : AuthRequest
