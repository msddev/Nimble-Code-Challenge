cmake_minimum_required(VERSION 3.22.1)

project("ClientKeysNdk")

add_library(
        ClientKeysNdk
        SHARED
        ClientKeysNdk.cpp
)

target_link_libraries(
        ClientKeysNdk
        android
        log
)