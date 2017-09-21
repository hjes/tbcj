using HttpCodeLib;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Windows.Forms;

namespace Video
{
    public  class Util
    {
        XJHTTP http = new XJHTTP();
        private string uid = "";
        private string vid = "";
        private string videourl = "";
        private string shopname = "";
        //解析宝贝url并下载视频
        public string execute(string url)
        {
            try
            {
                HttpResults req = http.GetHtml(url);
                string html = req.Html;
                shopname = http.GetStringMid(html, "<title>", "</title>");
                if (html.IndexOf("videoId") == -1) return shopname+"&该商品没有小视频";
                uid = http.GetStringMid(html, "userid:", ";");
                vid = http.GetStringMid(html, "videoId\":\"", "\"");
                string fileurl = "http://cloud.video.taobao.com/videoapi/info.php?vid=" + vid + "&uid=" + uid + "&p=1&t=6";
                html = http.GetHtml(fileurl).Html;
                videourl = http.GetStringMid(html, "video_url>", "</video_");
                CopyFileByUrl(videourl);
            }
            catch (Exception)
            {
                return shopname + "&发生错误";
            }
            return shopname + "&下载成功";
        }

        //下载文件
        public bool CopyFileByUrl(string url)
        {
            string name = shopname+url.Substring(url.LastIndexOf('/') + 1);//获取名字
            string fileFolder = Application.StartupPath + "/video";
            string filePath = Path.Combine(fileFolder, name);//存放地址就是本地的upload下的同名的文件
            if (!Directory.Exists(fileFolder))
                Directory.CreateDirectory(fileFolder);
            HttpWebRequest request = HttpWebRequest.Create(url) as HttpWebRequest;
            request.Method = "GET";
            request.ProtocolVersion = new Version(1, 1);
            HttpWebResponse response = request.GetResponse() as HttpWebResponse;
            if (response.StatusCode == HttpStatusCode.NotFound)
            {
                return false;//找不到则直接返回null
            }
            // 转换为byte类型
            System.IO.Stream stream = response.GetResponseStream();

            //创建本地文件写入流
            Stream fs = new FileStream(filePath, FileMode.Create);
            byte[] bArr = new byte[1024];
            int size = stream.Read(bArr, 0, (int)bArr.Length);
            while (size > 0)
            {
                fs.Write(bArr, 0, size);
                size = stream.Read(bArr, 0, (int)bArr.Length);
            }
            fs.Close();
            stream.Close();
            return true;
        }
    }
}
