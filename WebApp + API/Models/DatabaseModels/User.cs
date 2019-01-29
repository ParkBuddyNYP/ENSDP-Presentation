using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace ParkBuddies.Models
{
    public class User
    {
        //NormalUser,AdminUser,ParkBuddyAdmin
        [Key]
        public string UserID { get; set; }
        public string UserName { get; set; }
        public string Name { get; set; }
        public byte[] PasswordHash { get; set; }
        public byte[] PasswordSalt { get; set; }
        public string UserProfileImage { get; set; }
        public string UserRole { get; set; }
        public string UserToken { get; set; }
        public string UserEmail { get; set; }
           

        //NormalUser
        public string CarparkName { get; set; }

        public Carpark Carpark { get; set; }
        public List<UserReport> UserReports { get; set; }

        //AdminUser
        public string AdminRFID { get; set; }
        public string CompanyName { get; set; }

        public List<CarparkCompanies> Companies { get; set; }
    }
}
