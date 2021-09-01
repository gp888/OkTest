//
// Created by 凯驰 on 2021/4/9.
//

#ifndef OKTEST_VIDEORENDER_H
#define OKTEST_VIDEORENDER_H


#include <stdint.h>
#include <jni.h>

#include "../../one_frame.h"

class VideoRender {
public:
    virtual void InitRender(JNIEnv *env, int video_width, int video_height, int *dst_size) = 0;
    virtual void Render(OneFrame *one_frame) = 0;
    virtual void ReleaseRender() = 0;
};



#endif //OKTEST_VIDEO_RENDER_H
