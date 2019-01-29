using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace ParkBuddies.Models
{
    public class CarparkCompanies
    {
        [Key]
        public string CompanyID { get; set; }
        
        public string CompanyName { get; set; }

        public List<Carpark> Carparks { get; set; }
    }
}
