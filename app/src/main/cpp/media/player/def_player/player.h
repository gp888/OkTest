//
// Created by 凯驰 on 2021/4/9.
//

#ifndef OKTEST_PLAYER_H
#define OKTEST_PLAYER_H


#include "../../decoder/video/v_decoder.h"
//#include "../../decoder/audio/a_decoder.h"
//#include "../../../opengl/drawer/video_drawer.h"
//#include "../../../opengl/drawer/proxy/drawer_proxy.h"
//#include "../../render/video/opengl_render/opengl_render.h"

class Player {
private:
    VideoDecoder *m_v_decoder;
    VideoRender *m_v_render;

//    AudioDecoder *m_a_decoder;
//    AudioRender *m_a_render;

public:
    Player(JNIEnv *jniEnv, jstring path, jobject surface);
    ~Player();

    void play();
    void pause();
};


#endif //OKTEST_PLAYER_H
