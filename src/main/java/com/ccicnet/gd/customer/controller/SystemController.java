package com.ccicnet.gd.customer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统接口
 */
@Slf4j
@RestController
@RequestMapping("/sys")
public class SystemController implements ApplicationRunner {
	private final static String OK = "ok"; //F5 要求的HTTP状态回应

	@Override
	public void run(ApplicationArguments args) {
		log.info("application run ok");
		System.setProperty("status", OK);
	}

	/**
	 * 修改 status 以便在停机前响应心跳检测
	 */
	@GetMapping("/setStatus")
	public String setStatus(String status) {
		log.warn("System status change to:{}", status);
		return System.setProperty("status", status);
	}

	/**
	 * 监控程序心跳检测
	 */
	@GetMapping("/status")
	public String status() {
		String status = System.getProperty("status");
		if (!OK.equals(status)) {
			log.warn("System echoed fail status");//打印出这一条日志后，证明F5已经检测到程序已经状态异常，就可以shutdown进程，发版了。
		}
		return status;
	}
}
