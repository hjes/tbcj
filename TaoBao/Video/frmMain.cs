using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using HttpCodeLib;
using System.IO;
using System.Net;
using CCWin;

namespace Video
{
    public partial class frmMain : Skin_DevExpress
    {
        public frmMain()
        {
            InitializeComponent();
        }
        Util util = new Util();
        XJHTTP http = new XJHTTP();
        private void btndownload_Click(object sender, EventArgs e)
        { 
            string result=util.execute(txtItemUrl.Text,"自定义");
            string[] results=result.Split('&');
            int index = this.dgvTask.Rows.Add();
            this.dgvTask.Rows[index].Cells[0].Value = results[0].Trim();
            this.dgvTask.Rows[index].Cells[1].Value = results[1];
        }
        //店铺采集
        private void btnshopcj_Click(object sender, EventArgs e)
        {
            
            ShopService shop = new ShopService(txtShopUrl.Text, txtHtml,webBrowser1,toolTip, dgvShopTask);
            shop.getList();
            toolTip.Text = "店铺商品解析完成共" + dgvShopTask.Rows.Count+"件宝贝";
        }

        private void frmMain_FormClosed(object sender, FormClosedEventArgs e)
        {
            Application.Exit();
        }

        private void frmMain_FormClosing(object sender, FormClosingEventArgs e)
        {
            Application.Exit();
        }

        private void aToolStripMenuItem_Click(object sender, EventArgs e)
        {
            dgvShopTask.Rows[dgvShopTask.CurrentRow.Index].Cells[2].Value = "正在下载";
            string shopurl=dgvShopTask.Rows[dgvShopTask.CurrentRow.Index].Cells[1].Value.ToString();
             string shopname=dgvShopTask.Rows[dgvShopTask.CurrentRow.Index].Cells[0].Value.ToString();
             string result = util.execute("http://123.184.41.233:81/s/ajax?method=check&uid=" + txtAccount.Text + "&shopurl=" + shopurl, shopname);
            string[] results = result.Split('&');
            dgvShopTask.Rows[dgvShopTask.CurrentRow.Index].Cells[2].Value = results[1];
        }
        private void frmMain_Load(object sender, EventArgs e)
        {
            txtHtml.Text = http.GetHtml("http://123.184.41.233:81/s/ajax?method=getAd").Html;
        }

        private void 下载全部ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            int row = dgvShopTask.Rows.Count;//得到总行数    
            for (int i = 0; i < row; i++)//得到总行数并在之内循环    
            {
                string state=this.dgvShopTask.Rows[i].Cells[2].Value.ToString();
                if (state == "未开始")
                {
                    dgvShopTask.Rows[i].Cells[2].Value = "正在下载";
                    string shopurl = dgvShopTask.Rows[i].Cells[1].Value.ToString();
                    string shopname = dgvShopTask.Rows[i].Cells[0].Value.ToString();
                    string result = util.execute("http://123.184.41.233:81/s/ajax?method=check&uid=" + txtAccount.Text.Trim() + "&shopurl=" + shopurl,shopname);
                    string[] results = result.Split('&');
                    dgvShopTask.Rows[i].Cells[2].Value = results[1];
                    if (results[0] == "500") break;
                    Delay(2);
                }
            }   
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
