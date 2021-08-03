package com.ccicnet.gd.customer.dto.config;

import com.ccicnet.gd.common.dto.Dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoteApiDto extends Dto {
	private static final long serialVersionUID = 1L;
	private String id; //API ID，交易码
	private String url; //远程接口的 URL
	private boolean logRequest=true;//是否记录请求日志
	private boolean logResponse=true;//是否记录返回日志
//    private boolean idempotent = false; //远程的接口是否支持幂等，即同样的流水号可以重发
}
