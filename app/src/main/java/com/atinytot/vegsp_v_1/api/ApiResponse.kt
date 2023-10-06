package com.atinytot.vegsp_v_1.api

data class ApiResponse(
    val statusCode: Int,
    val responseData: Any?,
    val responseMessage: String?
)