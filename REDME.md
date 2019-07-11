# mp3标签处理和裁剪    
> 批量修改mp3的标题tag属性, 然后批量剪切前25秒的广告 

1. 下载 ffmpeg  
https://ffmpeg.zeranoe.com/builds/  

2. 用maven编译后得到程序包  
 MP3Processor-0.0.1-SNAPSHOT.jar  
 
3. 运行程序  
 ```shell
java -jar MP3Processor-0.0.1-SNAPSHOT.jar  --mp3.srcDir=C:\\Users\\xxx\\Downloads\\ --mp3.destDir=C:\\Users\\xxx\\Downloads\\dest\\ --ffmpeg.dir=D:\\ProgramDev\\ffmpeg-20190705-a514244-win64-static\\bin
```
> 参数说明：  
> `mp3.srcDir` mp3原文件路径  
> `mp3.destDir` mp3处理后的文件存储路径  
> `ffmpeg.dir` ffmpeg程序的bin目录 
 
