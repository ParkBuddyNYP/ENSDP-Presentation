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
    public class CarparksController : ControllerBase
    {
        private readonly ParkBuddiesContext _context;

        public CarparksController(ParkBuddiesContext context)
        {
            _context = context;
        }

        // GET: api/Carparks
        [HttpGet]
        public IEnumerable<Carpark> GetCarpark()
        {
            return _context.Carpark;
            
        }

        
        // GET: api/Carparks/5
        [HttpGet("{id}")]
        public IEnumerable<Carpark> GetCarpark([FromRoute] string id)
        {


            var carpark = _context.Carpark.Where(t => t.CompanyName == id).ToList();

            //var carpark = await _context.Carpark.SingleOrDefaultAsync(m => m.CompanyName == id);


            return carpark;
        }

        // PUT: api/Carparks/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutCarpark([FromRoute] string id, [FromBody] Carpark carpark)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != carpark.CarparkID)
            {
                return BadRequest();
            }

            _context.Entry(carpark).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!CarparkExists(id))
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

        [Authorize(Roles = Role.CompanyAdmin + "," + Role.ParkBuddyAdmin)]
        // POST: api/Carparks                        ([FromBody] Carpark carpark)
        [HttpPost]

        public async Task<IActionResult> PostCarpark([FromBody] Carpark carpark)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            _context.Carpark.Add(carpark);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetCarpark", new { id = carpark.CarparkID }, carpark);
        }

        [Authorize(Roles = Role.CompanyAdmin + "," + Role.ParkBuddyAdmin)]
        // DELETE: api/Carparks/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteCarpark([FromRoute] string id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var carpark = await _context.Carpark.SingleOrDefaultAsync(m => m.CarparkID == id);
            if (carpark == null)
            {
                return NotFound();
            }

            _context.Carpark.Remove(carpark);
            await _context.SaveChangesAsync();

            return Ok(carpark);
        }

        private bool CarparkExists(string id)
        {
            return _context.Carpark.Any(e => e.CarparkID == id);
        }
    }
}