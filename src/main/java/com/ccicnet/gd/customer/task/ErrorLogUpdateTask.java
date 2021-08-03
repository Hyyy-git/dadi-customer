package com.ccicnet.gd.customer.task;

import com.ccicnet.gd.common.dto.Dto;
import com.ccicnet.gd.customer.constant.common.KafkaTopic;
import com.ccicnet.gd.customer.event.EventProducer;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 该类用于处理错误日志文件，根据错误线程号获取对应的请求报文，重新发kafka消息
 * @author wqwang 2020/12/24
 */
@Slf4j
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ErrorLogUpdateTask extends AbstractTask {
	@Resource
	private EventProducer eventProducer;
	
	private TaskParam taskParam;
	
	@Getter
    @Setter
    private static class TaskParam extends Dto {
        private String path;
    }

	@Override
	protected void execute(Long reqId, String param) {
		log.info("------------------------------errorUpdateStart--------------------");
		try {
			this.taskParam = Dto.fromJson(param, TaskParam.class);
			execute();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		log.info("-------------------------------errorUpdateEnd--------------------");
	}
	
	
	private void execute() throws IOException {
		//error和info文件所在文件夹路径
		String filePath = taskParam.path;
		// 通过文件夹获取该文件夹下所有的文件集合
		Map<String, List<String>> urlMap = getErrorAndInfoFile(filePath);
		// 获取所有error文件路径集合
		List<String> errorFileList = urlMap.get("error");
		// 获取所有info文件路径集合
		List<String> infoFileList = urlMap.get("info");

		// 遍历error文件集合和info文件集合
		for (String errorSt : errorFileList) {
			// 根据error文件名称获取当日所有的info文件名称集合
			List<String> todayAllInfoFile = byErrorGetInfo(errorSt, infoFileList);
			// 获取当前error日志文件中的所有的线程编号集合
			List<String> errorThreads = getThreadNumbers(filePath + errorSt);

			// 遍历当日所有的info日志文件集合
			for (String infoName : todayAllInfoFile) {
				// 获取当前文件中错误线程对应的请求报文信息
				Map<String, String> thrAndReqMap = getTheReqData(errorThreads, filePath + infoName);

				for (Map.Entry<String, String> mapEnt : thrAndReqMap.entrySet()) {
					// 发送更新接口
					eventProducer.send(KafkaTopic.BUSI_CUSTOMER_UPDATE, mapEnt.getValue(), mapEnt.getKey());
					// 从当前线程集合中移除该线程元素
					errorThreads.remove(mapEnt.getKey());
				}
			}

			// 检查当前error文件线程集合是否已全部执行
			if (errorThreads != null && errorThreads.size() != 0) {
				for (String sq : errorThreads) {
					log.warn("error日志文件:{}中的线程编号:{},未找到对应的请求信息，请检查！",errorSt,sq);
				}
			}else {
				log.info("--------------------------日志文件:"+errorSt+"已完成处理！----------------------------");
			}
		}
	}

	/**
	 * 根据error文件名获取当日所有的info文件名
	 * @param errorFileName 错误日志文件名
	 * @param allInfoList 全部info文件集合
	 * @return infoList error对应当日所有info文件集合
	 */
	public List<String> byErrorGetInfo(String errorFileName, List<String> allInfoList) {
		List<String> infoList = new ArrayList<>();
		for (String infoStr : allInfoList) {
			if (errorFileName != null && errorFileName.contains(infoStr.substring(0, 23))) {
				infoList.add(infoStr);
			}
		}
		Collections.sort(infoList);
		return infoList;
	}

	/**
	 * 解析当前文件夹，获取所有的error文件和info文件 将其放入map中
	 * @param urlFileStr 文件夹名称
	 * @return map error和info文件集合
	 * error ：错误日志文件集合key 
	 * info ：正常日志文件集合key
	 */
	public Map<String, List<String>> getErrorAndInfoFile(String urlFileStr) {
		File file = new File(urlFileStr);
		Map<String, List<String>> map = new HashMap<>();
		// error日志文件
		List<String> errorList = new ArrayList<>();
		// info日志文件
		List<String> infoList = new ArrayList<>();

		if (file.isDirectory()) {
			String[] listArr = file.list();
			if(listArr == null || listArr.length == 0){
				return map;
			}
			for (String url : listArr) {
				if (url != null && url.contains("error")) {
					errorList.add(url);
				}
				if (url != null && !url.contains("error")) {
					infoList.add(url);
				}
			}
			//对list文件进行升序排列
			Collections.sort(errorList);
			Collections.sort(infoList);
			map.put("error", errorList);
			map.put("info", infoList);
		}

		return map;
	}
	

	/**
	 * 获取线程编号集合
	 * @return threadNumbers:当前error文件线程集合
	 * @throws IOException 文件解析异常
	 */
	public List<String> getThreadNumbers(String errorLogFileUrl) throws IOException {
		File infile = new File(errorLogFileUrl);
		// 定义获取的文件中的线程编号集合
		List<String> threadNumbers = new ArrayList<>();
		if (!infile.exists()) {
			log.info("文件不存在-------" + infile);
			return threadNumbers;
		}

		try (LineIterator iterator = FileUtils.lineIterator(infile, "UTF-8")) {
			while (iterator.hasNext()) {
				String lineText = iterator.nextLine();
				if (lineText != null && lineText.length() > 25 && lineText.indexOf("[") == 24 && lineText.contains("][E]")) {
					String tmpStr = lineText.substring(25, lineText.indexOf("][E]"));
					tmpStr = StringUtils.right(tmpStr, 8);
					if (!threadNumbers.contains(tmpStr)) {
						threadNumbers.add(tmpStr);
					}
				}
			}
		}

		return threadNumbers;
	}
	

	/**
	 * 根据线程编号获取对应的请求报文信息
	 * @param thrNumbers 线程编号集合
	 * @param reqFileUrl info文件名称
	 * @return threadNoAndReq 线程编号对应的请求报文map
	 * @throws IOException 问价解析异常
	 */
	public Map<String, String> getTheReqData(List<String> thrNumbers, String reqFileUrl) throws IOException {
		// 创建Map集合，用于存储请求报文线程编号和请求报文信息
		Map<String, String> threadNoAndReq = new LinkedHashMap<>();
		// 解析请求报文文件，获取报文信息
		File reqFile = new File(reqFileUrl);
		if (!reqFile.exists()) {
			log.warn("info文件不存在-----" + reqFile);
			return threadNoAndReq;
		}

		final String mqlineHeader = "c.c.gd.customer.event.EventConsumer:30 - received:";
		try (LineIterator lineIte = FileUtils.lineIterator(reqFile, "UTF-8")) {
			while (lineIte.hasNext()) {
				String lineText = lineIte.nextLine();
				if (lineText != null && lineText.length() > 24 && lineText.charAt(24) == '[' && lineText.contains(mqlineHeader)) {
					for (String thr : thrNumbers) {
						if (lineText.contains("@" + thr + "][")) {
							// 处理当前行数据，获取报文体信息
							lineText = lineText.substring(lineText.indexOf(mqlineHeader) + mqlineHeader.length());
							// 将线程编号和请求报文体进行关联
							threadNoAndReq.put(thr, lineText);
						}
					}
				}
			}
		}

		return threadNoAndReq;
	}
}
