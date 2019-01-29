using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace ParkBuddies.Models
{
    public class UserReport
    {
        [Key]
        public string ReportID { get; set; }
        public string ReportStatus { get; set; }
        public string ReportDate { get; set; }
        public string ReportDescription { get; set; }
        public string ReportType { get; set; }
        public string ReportImage { get; set; }
        public byte[] ReportImagebyte { get; set; }

        //foreign
        public string UserID { get; set; }

        public User User { get; set; }

        //foreign
        public string CarparkName { get; set; }
        public Carpark Carpark { get; set; }
    }
}
