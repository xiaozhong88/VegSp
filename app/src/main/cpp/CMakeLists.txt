cmake_minimum_required(VERSION 3.22.1)

project("VegSp_V_1")

find_library(log-lib log)

# 设置包含的目录
include_directories(include)

add_library(
        native
        SHARED
        native-lib.cpp
        OpenGLRender.cpp
        utils/GLUtil.cpp
)

target_link_libraries(
        native
        GLESv3
        android
        ${log-lib}
)