using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace ParkBuddies.Models
{
    public class CarparkLots
    {
        [Key]
        public string LotID { get; set; }
        public int LotNumber { get; set; }
        public string LotStatus { get; set; }
        public int NumberOfLotsAvailable { get; set; }
        public int TotalNumberOfLots { get; set; }
        public string CarparkName { get; set; }

        public Carpark Carpark { get; set; }
    }
}
