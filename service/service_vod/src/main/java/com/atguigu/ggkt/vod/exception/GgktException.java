package com.atguigu.ggkt.vod.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GgktException extends RuntimeException{
    private Integer code;
    private  String msg;
}
