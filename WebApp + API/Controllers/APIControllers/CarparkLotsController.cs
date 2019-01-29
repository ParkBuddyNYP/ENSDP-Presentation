using System;
using System.Collections.Generic;
using System.Linq;
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
    public class CarparkLotsController : ControllerBase
    {
        private readonly ParkBuddiesContext _context;

        public CarparkLotsController(ParkBuddiesContext context)
        {
            _context = context;
        }

        // GET: api/CarparkLots
        [HttpGet]
        public IEnumerable<CarparkLots> GetCarparkLots()
        {
            return _context.CarparkLots;
        }

        // GET: api/CarparkLots/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetCarparkLots([FromRoute] string id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var carparkLots = await _context.CarparkLots.SingleOrDefaultAsync(m => m.CarparkName == id);

            if (carparkLots == null)
            {
                return NotFound();
            }

            return Ok(carparkLots);
        }

        // PUT: api/CarparkLots/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutCarparkLots([FromRoute] string id, [FromBody] CarparkLots carparkLots)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != carparkLots.LotID)
            {
                return BadRequest();
            }

            _context.Entry(carparkLots).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!CarparkLotsExists(id))
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

        // POST: api/CarparkLots
        [HttpPost]
        public async Task<IActionResult> PostCarparkLots([FromBody] CarparkLots carparkLots)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            var existingCarpark = _context.Carpark.Where(s => s.CarparkName == carparkLots.CarparkName)
                                                    .FirstOrDefault<Carpark>();

            if (existingCarpark != null)
            {
                existingCarpark.TotalNumberOfLots = carparkLots.TotalNumberOfLots;
                existingCarpark.NumberOfLotsAvailable = carparkLots.NumberOfLotsAvailable;

            }

            _context.CarparkLots.Add(carparkLots);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetCarparkLots", new { id = carparkLots.LotID }, carparkLots);
        }

        [Authorize(Roles = Role.CompanyAdmin + "," + Role.ParkBuddyAdmin)]
        // DELETE: api/CarparkLots/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteCarparkLots([FromRoute] string id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var carparkLots = await _context.CarparkLots.SingleOrDefaultAsync(m => m.LotID == id);
            if (carparkLots == null)
            {
                return NotFound();
            }

            _context.CarparkLots.Remove(carparkLots);
            await _context.SaveChangesAsync();

            return Ok(carparkLots);
        }

        private bool CarparkLotsExists(string id)
        {
            return _context.CarparkLots.Any(e => e.LotID == id);
        }
    }
}