//解码状态定义
// Created by 凯驰 on 2021/2/24.
//

#ifndef OKTEST_DECODE_STATE_H
#define OKTEST_DECODE_STATE_H

enum DecodeState {
    STOP,
    PREPARE,
    START,
    DECODING,
    PAUSE,
    FINISH
};

#endif //OKTEST_DECODE_STATE_H
