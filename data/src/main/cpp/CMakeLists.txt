cmake_minimum_required(VERSION 3.22.1)

project("clientkeysndk")

add_library(
        clientkeysndk
        SHARED
        clientkeysndk.cpp
)

target_link_libraries(
        clientkeysndk
        android
        log
)