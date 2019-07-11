package com.join.tools.mp3.processor.tools;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * cmd命令工具类
 */
@Slf4j
public class CMDTool {

	/**
	 * 执行一个cmd命令
	 * @param cmdCommand cmd命令
	 * @return 命令执行结果字符串，如出现异常返回null
	 */
	public static String excuteCommand(String cmdCommand){
		return excute(cmdCommand);
	}

	/**
	 * 执行bat批处理文件
	 * @param file bat路径
	 */
	public static String excuteCmdBatch(String file){
		return excuteCmdBatch(file,true);
	}

	/**
	 * 执行bat批处理文件
	 * @param batFilePath bat路径
	 * @param isCloseWindow 执行完毕后是否关闭cmd窗口
	 */
	public static String excuteCmdBatch(String batFilePath, boolean isCloseWindow){
		log.info("\n===============开始执行脚本：{} =================",batFilePath);
		String cmdCommand ;
		if(isCloseWindow) {
			cmdCommand = "cmd.exe /c start "+batFilePath;
		}else {
			cmdCommand = "cmd.exe /k start "+batFilePath;
		}
		return excute(cmdCommand);
	}

	private static String excute(String cmdCommand) {

		StringBuilder stringBuilder = new StringBuilder();
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(cmdCommand);
			try(
					InputStream inputStream=process.getInputStream();
					InputStreamReader inputStreamReader=new InputStreamReader(inputStream, "GBK");
					BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			){
				String line = null;
				while((line=bufferedReader.readLine()) != null)
				{
					stringBuilder.append(line).append("\n");
				}
			}

			return stringBuilder.toString();

		} catch (Exception e) {
			log.error(e.getLocalizedMessage(),e);
			return null;
		}

	}
}
