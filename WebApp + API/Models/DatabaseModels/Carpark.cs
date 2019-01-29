using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace ParkBuddies.Models
{
    public class Carpark
    {
        [Key]
        public string CarparkID { get; set; }
        public string CarparkName { get; set; }
        public string CarparkLat { get; set; }
        public string CarparkLng { get; set; }
        public string CarparkStatus { get; set; } //full,almost full etc
        public string CompanyName { get; set; }
        public int NumberOfLotsAvailable { get; set; }
        public int TotalNumberOfLots { get; set; }

        //In this table
        public CarparkCompanies Company { get; set; }
        //For other table
        public List<CarparkLots> CarparkLots { get; set; }
        public List<UserReport> UserReports { get; set; }
        public List<User> Users { get; set; }
        //public CarparkGraphData CarParkGraphData { get; set; }
    }
}
