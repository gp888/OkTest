//解码器定义
// Created by 凯驰 on 2021/2/24.
//一个纯虚类，类似 Java 的 interface
//

#ifndef OKTEST_I_DECODER_H
#define OKTEST_I_DECODER_H

class IDecoder {
public:
    virtual void GoOn() = 0;
    virtual void Pause() = 0;
    virtual void Stop() = 0;
    virtual bool IsRunning() = 0;
    virtual long GetDuration() = 0;
    virtual long GetCurPos() = 0;
};

#endif //OKTEST_I_DECODER_H
