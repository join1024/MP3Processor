package com.join.tools.mp3.processor.tools;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @author ：Join
 * @date ：Created in 2019/7/11 11:00
 * @modified By：
 */
@Slf4j
public class FileTool {

	public static void copyFile(String resourceFileName,String destFilePath,String ffmpegDir,String destDir){
		String stringData=processFile(resourceFileName,ffmpegDir,destDir);
		log.info("\n====================== file content is : \n{}",stringData);
		writeToFile(stringData,destFilePath);
	}

	public static void writeToFile(String stringData,String destFilePath){
		try(FileOutputStream fileOutputStream=new FileOutputStream(destFilePath)){
			fileOutputStream.write(stringData.getBytes());
			fileOutputStream.flush();
		}catch (Exception e){
			log.error("写文件异常：file path = "+destFilePath,e);
		}
	}

	public static String processFile(String resourceFileName,String ffmpegDir,String destDir){

		byte[] bytes=new byte[1024];
		int length=0;
		StringBuilder stringBuilder=new StringBuilder();
		try(InputStream inputStream=FileTool.class.getResourceAsStream(resourceFileName)){
			do{
				length=inputStream.read(bytes);
				if(length>0){
					stringBuilder.append(new String(bytes,0, length));
				}
			}while (length>0);

		}catch (Exception e){
			log.error("文件读取异常：resource file path = "+resourceFileName,e);
		}

		//得到盘符，如D盘：“D:”
		String fileDrive=null;
		int idx=ffmpegDir.indexOf(":");
		if(idx>0){
			fileDrive=ffmpegDir.substring(0,idx+1);
		}

		String stringData=stringBuilder.toString();
		if(fileDrive!=null){
			//盘符替换
			stringData=stringData.replace("${file.drive}",fileDrive);
		}
		//变量替换为实际路径
		stringData=stringData.replace("${ffmpeg.dir}",ffmpegDir);
		stringData=stringData.replace("${mp3.destDir}",destDir);

		return stringData;
	}

	public static void createDirs(String dirPath){
		if(dirPath==null){
			return ;
		}
		File file=new File(dirPath);
		if(!file.exists()){
			log.info("\n============== delete dir :{}",dirPath);
			file.mkdirs();
		}
	}

	public static void deleteFile(String filePath){
		if(filePath==null){
			return ;
		}
		File file=new File(filePath);
		if(file.exists()){
			log.info("\n============== delete file :{}",filePath);
			file.delete();
		}
	}

	public static void copyFile(String resourceFileName,String destFilePath){

		byte[] bytes=new byte[1024];
		int length=0;

		try(
				InputStream inputStream=FileTool.class.getResourceAsStream(resourceFileName);
				FileOutputStream fileos=new FileOutputStream(destFilePath)
		){
			do{
				length=inputStream.read(bytes);
				if(length>0){
					fileos.write(bytes,0,length);
				}
			}while (length>0);

		}catch (Exception e){
			log.error("文件读取异常：file path = "+destFilePath,e);
		}

	}
}
