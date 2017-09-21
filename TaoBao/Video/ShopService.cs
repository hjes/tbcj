using HtmlAgilityPack;
using HttpCodeLib;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Windows.Forms;
using CCWin;
using CCWin.SkinControl;
 

namespace Video
{
    public class ShopService
    {
        public ShopService(string shopurl,SkinTextBox txtbtn,WebBrowser webclient, ToolStripLabel tip,SkinDataGridView dgvTask)
        {
            this.tip = tip;
            this.webclient = webclient;
            this.dgvTask = dgvTask;
            this.text = txtbtn;
            printLog("初始化软件设置");
            this.shopurl = shopurl;
            initUrl(shopurl + allitemUrlwithNew + 1);
            printLog("解析店铺链接中");
            Delay(6);
            int i = 1;
            this.html = webclient.Document.Body.OuterHtml;
            while (this.html.IndexOf("page-info") == -1)
            {
                printLog("解析店铺链接中"+(i*20)+"%");
                this.html = webclient.Document.Body.OuterHtml;
                Delay(2);
                i++;
                if(i>5)
                {
                    break;
                }
            }
           
            printLog("获取宝贝总数中");
            Delay(1);
            this.count=getPageCount(html);
          
        }
        private void initUrl(string url)
        {
            webclient.Navigate(url);
        }

        private string allitemUrlwithNew = "/search.htm?orderType=newOn_desc&pageNum=";//按照新品排列url

        public  SkinTextBox text = null;
        WebBrowser webclient = null;
        private XJHTTP http = new XJHTTP();
     
        private string contenturl = String.Empty;
        private string shopurl = String.Empty;
        private int count = 0;
        private string html = String.Empty;
        private ToolStripLabel tip = null;
        private SkinDataGridView dgvTask = null;
        Util util = new Util();
        public void getList()
        {
          
            for (int i = 1; i <=count; i++)
            {
                initUrl(shopurl + allitemUrlwithNew + i);
                printLog("采集第"+i+"页宝贝中");
                Delay(6);
                int j = 1;
                this.html = webclient.Document.Body.OuterHtml;
                while (this.html.IndexOf("page-info") == -1)
                {
                    this.html = webclient.Document.Body.OuterHtml;
                    Delay(2);
                    j++;
                    if (j > 5)
                    {
                        break;
                    }
                }
                HtmlAgilityPack.HtmlDocument doc = new HtmlAgilityPack.HtmlDocument();
                doc.LoadHtml(html);
                HtmlNodeCollection shops = doc.DocumentNode.SelectNodes("//*[@class='item-name' or contains(@class,' item-name ') or starts-with(@class,'item-name ') or substring(@class,string-length(@class)-3)=' item-name']");
                if (shops == null)
                {
                    MessageBox.Show("采集过于频繁!请等待或重启路由器！");
                    printLog("采集过于频繁!请等待或重启路由器！");
                    Delay(6);
                    break;
                } 
                foreach (HtmlNode  item in shops)
                {
                    BabyEntity entity = new BabyEntity(item.InnerText, "未开始", "https:" + item.GetAttributeValue("href", "未开始"));
                    printLog(entity.name+entity.statu);
                    additem(entity);
                }
            }
           
        }

        //获取总页数
        public int getPageCount(string html)
        {
            int count = 0;
            string[] cous = http.GetStringMid(html, "page-info", "<").Split('/');
            if (cous.Length==2)
            {
                count = int.Parse(cous[1]);
            }
            else
            {
                System.Windows.Forms.MessageBox.Show("采集过于频繁!请等待或重启路由器！");
            }
            return count;
        }

        private void printLog(string str)
        {
            tip.Text = str;
        }

        private void additem(BabyEntity item)
        {
            int index = this.dgvTask.Rows.Add();
            this.dgvTask.Rows[index].Cells[0].Value = item.name.Trim();
            this.dgvTask.Rows[index].Cells[1].Value = item.shopurl.Trim();
            this.dgvTask.Rows[index].Cells[2].Value = item.statu.Trim();
        }

        public static bool Delay(int delayTime)
        {
            DateTime now = DateTime.Now;
            int s;
            do
            {
                TimeSpan spand = DateTime.Now - now;
                s = spand.Seconds;
                Application.DoEvents();
            }
            while (s < delayTime);
            return true;
        }
    }
}
