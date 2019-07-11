package com.join.tools.mp3.processor;

import com.join.tools.mp3.processor.tools.CMDTool;
import com.join.tools.mp3.processor.tools.FileTool;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v1Tag;
import com.mpatric.mp3agic.Mp3File;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;

/**
 * @author join
 */
@Slf4j
@SpringBootApplication
public class Mp3ProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(Mp3ProcessorApplication.class, args);
	}

	@Value("${mp3.srcDir}")
	private String mp3SrcDir;

	@Value("${mp3.destDir}")
	private String mp3DestDir;

	@Value("${ffmpeg.dir}")
	private String ffmpegDir;

	private String tempCmdFile;


	@PostConstruct
	private void process(){
		FileTool.createDirs(mp3DestDir);

		File parent=new File(mp3SrcDir);
		File[] files=parent.listFiles();

		int totalCount=0;
		int errorCount=0;

		log.info("\n ====== begin process ... \n");
		for (File file : files) {
			if(file.getName().endsWith(".mp3")){
				try {
					Mp3File mp3file = new Mp3File(file);
					ID3v1 id3v1Tag;

					if (mp3file.hasId3v2Tag()) {
						id3v1Tag =  mp3file.getId3v2Tag();
					} else if(mp3file.hasId3v1Tag()){
						id3v1Tag =  mp3file.getId3v1Tag();
					} else {
						// mp3 does not have an ID3v1 tag, let's create one..
						id3v1Tag = new ID3v1Tag();
						mp3file.setId3v1Tag(id3v1Tag);
					}
					id3v1Tag.setArtist("纪涵邦");
					//把mp3标题修改成与文件名一致,在音乐播放器列表中显示的是title,而不是文件名，可以根据自己的需求修改title
					id3v1Tag.setTitle("汉朝那些事_"+file.getName());
					id3v1Tag.setAlbum("汉朝那些事");
					id3v1Tag.setYear("2019");
					id3v1Tag.setGenre(12);
					id3v1Tag.setComment("modify by mp3agic");
					mp3file.save(mp3DestDir +file.getName());

					totalCount++;
				} catch (Exception e) {
					errorCount++;
					log.error("\n !!!!!!!!!!!!! {} process fail: {} !!!!!!!!!!!!! \n",file.getName(),e.getLocalizedMessage());
				}
			}
		}

		log.info("\n ====== process complete .  \n");
		log.info("\n ===== mp3 total count is {} , process success count is {} , process failed count is {} \n",totalCount,(totalCount-errorCount),errorCount);


		exeCommandBatch();
	}

	private void exeCommandBatch() {
		String path=System.getProperty("user.dir");
		tempCmdFile=path+File.separator+"temp_mp3Cutter.bat";
		FileTool.copyFile("/mp3Cutter.bat",tempCmdFile,ffmpegDir,mp3DestDir);
		CMDTool.excuteCmdBatch(tempCmdFile);

	}

	/*@PreDestroy
	private void destory(){
		FileTool.deleteFile(tempCmdFile);
	}*/

}
