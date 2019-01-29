using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace ParkBuddies.Models
{
    public class CarparkGraphData
    {
        [Key]
        public string CarparkGraphDataID { get; set; }
        [DataType(DataType.Date)]
        public DateTime TimeRecorded { get; set; }
        public string TotalNumberOfLots { get; set; }
        public string LotsAvailable { get; set; }
       // public string LotID { get; set; }
        //public string UserInLotId { get; set; }
        //[DataType(DataType.Date)]
        //public DateTime LotUsedStartTime { get; set; }
        //public DateTime LotUsedEndTime { get; set; }
        public string CarparkName { get; set; }

        public Carpark Carpark { get; set; }
    }
}
