package com.nadhif.hayazee.model.dummies

import com.nadhif.hayazee.common.util.GsonUtil
import com.nadhif.hayazee.model.response.LoginResponse
import com.nadhif.hayazee.model.response.RegisterResponse

object AuthDummies {

    const val loginErrorBody = "{\n" +
            "  \"error\": true,\n" +
            "  \"message\": \"Invalid password\"\n" +
            "}"

    const val registerErrorBody = "{\n" +
            "  \"error\": true,\n" +
            "  \"message\": \"Email is already taken\"\n" +
            "}"

    fun getSuccessLoginDummy(): LoginResponse? {

        val json = "{\n" +
                "  \"error\": false,\n" +
                "  \"message\": \"success\",\n" +
                "  \"loginResult\": {\n" +
                "    \"userId\": \"user-uat5fPmYJRQyDJG7\",\n" +
                "    \"name\": \"fulan\",\n" +
                "    \"token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXVhdDVmUG1ZSlJReURKRzciLCJpYXQiOjE2NjcxOTg2MDB9.Ofh1nizErmZ7O8GQmqXPiVZ8l8C2FlAAq48U3z-AAg8\"\n" +
                "  }\n" +
                "}"
        return GsonUtil.fromJson(json, LoginResponse::class.java)
    }

    fun getErrorLoginDummy(): LoginResponse? {
        return GsonUtil.fromJson(loginErrorBody, LoginResponse::class.java)
    }

    fun getSuccessRegisterDummy(): RegisterResponse? {
        val json = "{\n" +
                "  \"error\": false,\n" +
                "  \"message\": \"User created\"\n" +
                "}"

        return GsonUtil.fromJson(json, RegisterResponse::class.java)
    }

    fun getErrorRegisterDummy(): RegisterResponse? {
        return GsonUtil.fromJson(registerErrorBody, RegisterResponse::class.java)
    }

}