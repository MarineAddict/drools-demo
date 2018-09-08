package com.oyo.droolsdemo.entity.request.test;

import com.oyo.droolsdemo.entity.model.risk.RiskLevelEnum;
import lombok.Data;

/**
 * 基本的请求体，请求时间记录,以及请求的风险
 * @author: create by xuqie
 * @description:
 * @date:2018/9/8
 */
@Data
public class BaseRequest {

    /**
     * 请求时间
     */
    private Long requestTime;

    /**
     * 请求的风险级别
     */
    RiskLevelEnum riskLevel;

}
