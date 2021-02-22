# cmake 最低版本
cmake_minimum_required(VERSION 3.4.1)

# 支持gnu++11
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=gnu++11")

# 1. 定义so库和头文件所在目录，方面后面使用
# CMAKE_SOURCE_DIR 为系统变量，指向 CMakeLists.txt 所在目录。 ANDROID_ABI 也是系统变量，指向 so 对应的 CPU 框架目录：armeabi、armeabi-v7a、x86 ...
set(ffmpeg_lib_dir ${CMAKE_SOURCE_DIR}/../jniLibs/${ANDROID_ABI})
set(ffmpeg_head_dir ${CMAKE_SOURCE_DIR}/ffmpeg)

# 2. 添加头文件目录
include_directories(${ffmpeg_head_dir}/include)

# 3. 添加ffmpeg相关的so库
add_library( avutil
        SHARED
        IMPORTED )
set_target_properties( avutil
        PROPERTIES IMPORTED_LOCATION
        ${ffmpeg_lib_dir}/libavutil.so )

add_library( swresample
        SHARED
        IMPORTED )
set_target_properties( swresample
        PROPERTIES IMPORTED_LOCATION
        ${ffmpeg_lib_dir}/libswresample.so )

add_library( avcodec
        SHARED
        IMPORTED )
set_target_properties( avcodec
        PROPERTIES IMPORTED_LOCATION
        ${ffmpeg_lib_dir}/libavcodec.so )

add_library( avfilter
        SHARED
        IMPORTED)
set_target_properties( avfilter
        PROPERTIES IMPORTED_LOCATION
        ${ffmpeg_lib_dir}/libavfilter.so )

add_library( swscale
        SHARED
        IMPORTED)
set_target_properties( swscale
        PROPERTIES IMPORTED_LOCATION
        ${ffmpeg_lib_dir}/libswscale.so )

add_library( avformat
        SHARED
        IMPORTED)
set_target_properties( avformat
        PROPERTIES IMPORTED_LOCATION
        ${ffmpeg_lib_dir}/libavformat.so )

add_library( avdevice
        SHARED
        IMPORTED)
set_target_properties( avdevice
        PROPERTIES IMPORTED_LOCATION
        ${ffmpeg_lib_dir}/libavdevice.so )

# 查找代码中使用到的系统库
find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log )

# 配置目标so库编译信息
add_library(
        # Sets the name of the library.
        # 输出so库的名称
        native-lib

        # Sets the library as a shared library.
        # 设置生成库的方式，默认为SHARE动态库
        SHARED

        # Provides a relative path to your source file(s).
        # 列出参与编译的所有源文件
        native-lib.cpp
        )

# 指定编译目标库时，cmake要链接的库
target_link_libraries(

        # 指定目标库，native-lib 是在上面 add_library 中配置的目标库
        native-lib

        # 4. 连接 FFmpeg 相关的库
        avutil
        swresample
        avcodec
        avfilter
        swscale
        avformat
        avdevice

        # Links the target library to the log library
        # included in the NDK.
        ${log-lib} )