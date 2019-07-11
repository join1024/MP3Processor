@echo off
echo ############# process begin ########################
${file.drive}
cd ${ffmpeg.dir}
for %%s in (${mp3.destDir}*.mp3) do (
	rem 得到mp3文件名，包含路径
	echo %%s
	rem 拼接得到将要输出的mp3文件名，包含路径
	echo %%s.dest.mp3
	rem 从25秒开始剪切去掉前面25秒的广告
	ffmpeg -i %%s -ss 00:00:25 -acodec copy %%s.dest.mp3

)
echo ... ...
echo ... ...
echo ############# process complete , please close the window. ########################
echo ... ...
echo ... ...



