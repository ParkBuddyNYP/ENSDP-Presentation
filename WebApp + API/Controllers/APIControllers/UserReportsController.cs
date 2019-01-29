using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using ParkBuddies.Models;

namespace ParkBuddies.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UserReportsController : ControllerBase
    {
        private readonly ParkBuddiesContext _context;

        public UserReportsController(ParkBuddiesContext context)
        {
            _context = context;
        }
        [Authorize(Roles = Role.CompanyAdmin + "," + Role.ParkBuddyAdmin)]
        // GET: api/UserReports
        [HttpGet]
        public IEnumerable<UserReport> GetUserReport()
        {
            var userReport = _context.UserReport.ToList();
            for (int i = 0; i < userReport.Count(); i++)
            {
                userReport[i].ReportImage = "data:image/png;base64," + Convert.ToBase64String(userReport[i].ReportImagebyte);
                Array.Clear(userReport[i].ReportImagebyte, 0, userReport[i].ReportImagebyte.Length);
            }

            return _context.UserReport;
        }

        [Authorize(Roles = Role.CompanyAdmin + "," + Role.ParkBuddyAdmin)]
        // GET: api/UserReports/5
        [HttpGet("{id}")]
        public IEnumerable<UserReport> GetUserReport([FromRoute] string id)
        {

            var userReport = _context.UserReport.Where(m => m.ReportType == id).ToList();
            for (int i = 0; i < userReport.Count(); i++)
            {
                userReport[i].ReportImage = "data:image/png;base64," + Convert.ToBase64String(userReport[i].ReportImagebyte);
                Array.Clear(userReport[i].ReportImagebyte, 0, userReport[i].ReportImagebyte.Length);
            }



            return userReport;
        }

        // PUT: api/UserReports/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutUserReport([FromRoute] string id, [FromBody] UserReport userReport)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != userReport.ReportID)
            {
                return BadRequest();
            }

            _context.Entry(userReport).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!UserReportExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        // POST: api/UserReports
        [HttpPost]
        public async Task<IActionResult> PostUserReport([FromBody] UserReport userReport)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            string base64 = userReport.ReportImage.Replace("data:image/png;base64,", "");
            Byte[] bitmapData = new Byte[base64.Length];
            bitmapData = Convert.FromBase64String(FixBase64ForImage(base64));
            userReport.ReportImage = "";


            userReport.ReportImagebyte = bitmapData;

            _context.UserReport.Add(userReport);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetUserReport", new { id = userReport.ReportID }, userReport);
        }
        [Authorize(Roles = Role.CompanyAdmin + "," + Role.ParkBuddyAdmin)]
        // DELETE: api/UserReports/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteUserReport([FromRoute] string id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var userReport = await _context.UserReport.SingleOrDefaultAsync(m => m.ReportID == id);
            if (userReport == null)
            {
                return NotFound();
            }

            _context.UserReport.Remove(userReport);
            await _context.SaveChangesAsync();

            return Ok(userReport);
        }

        private bool UserReportExists(string id)
        {
            return _context.UserReport.Any(e => e.ReportID == id);
        }

        public static string FixBase64ForImage(string image)
        {
            StringBuilder sbText = new StringBuilder(image, image.Length);
            sbText.Replace("\r\n", String.Empty);
            sbText.Replace(" ", String.Empty);
            return sbText.ToString();
        }
    }
}