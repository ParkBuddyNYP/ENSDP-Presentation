using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;

namespace ParkBuddies.Models
{
    public class ParkBuddiesContext : DbContext
    {
        public ParkBuddiesContext(DbContextOptions<ParkBuddiesContext> options)
            : base(options)
        {
        }

        public DbSet<ParkBuddies.Models.Carpark> Carpark { get; set; }
        public DbSet<ParkBuddies.Models.CarparkCompanies> CarparkCompany { get; set; }
        public DbSet<ParkBuddies.Models.User> User { get; set; }
        //public DbSet<ParkBuddies.Models.UserDto> UserDto { get; set; }
        //public DbSet<ParkBuddies.Models.Role> Role { get; set; }
        public DbSet<ParkBuddies.Models.CarparkLots> CarparkLots { get; set; }
        public DbSet<ParkBuddies.Models.UserReport> UserReport { get; set; }
        public DbSet<ParkBuddies.Models.CarparkGraphData> CarparkGraphData { get; set; }
        
    }
}
