using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Video
{
    public class BabyEntity
    {
        public BabyEntity(string name, string statu,string shopurl) {
            this.name = name;
            this.statu = statu;
            this.shopurl = shopurl;
        }
        public string name { get; set; }
        public string statu { get; set; }

        public string shopurl { get; set; }
    }
}
