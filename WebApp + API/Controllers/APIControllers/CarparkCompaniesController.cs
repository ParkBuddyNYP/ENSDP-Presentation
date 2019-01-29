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
    public class CarparkCompaniesController : ControllerBase
    {
        private readonly ParkBuddiesContext _context;

        public CarparkCompaniesController(ParkBuddiesContext context)
        {
            _context = context;
        }
        [Authorize(Roles = Role.CompanyAdmin + "," + Role.ParkBuddyAdmin)]
        // GET: api/CarparkCompanies
        [HttpGet]
        public IEnumerable<CarparkCompanies> GetCarparkCompany()
        {
            return _context.CarparkCompany;
        }
        [Authorize(Roles = Role.CompanyAdmin + "," + Role.ParkBuddyAdmin)]
        // GET: api/CarparkCompanies/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetCarparkCompanies([FromRoute] string id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var carparkCompanies = await _context.CarparkCompany.SingleOrDefaultAsync(m => m.CompanyID == id);

            if (carparkCompanies == null)
            {
                return NotFound();
            }

            return Ok(carparkCompanies);
        }

        // PUT: api/CarparkCompanies/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutCarparkCompanies([FromRoute] string id, [FromBody] CarparkCompanies carparkCompanies)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != carparkCompanies.CompanyID)
            {
                return BadRequest();
            }

            _context.Entry(carparkCompanies).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!CarparkCompaniesExists(id))
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
        // POST: api/CarparkCompanies
        [HttpPost]
        public async Task<IActionResult> PostCarparkCompanies([FromBody] CarparkCompanies carparkCompanies)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            _context.CarparkCompany.Add(carparkCompanies);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetCarparkCompanies", new { id = carparkCompanies.CompanyID }, carparkCompanies);
        }
        [Authorize(Roles = Role.CompanyAdmin + "," + Role.ParkBuddyAdmin)]
        // DELETE: api/CarparkCompanies/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteCarparkCompanies([FromRoute] string id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var carparkCompanies = await _context.CarparkCompany.SingleOrDefaultAsync(m => m.CompanyID == id);
            if (carparkCompanies == null)
            {
                return NotFound();
            }

            _context.CarparkCompany.Remove(carparkCompanies);
            await _context.SaveChangesAsync();

            return Ok(carparkCompanies);
        }

        private bool CarparkCompaniesExists(string id)
        {
            return _context.CarparkCompany.Any(e => e.CompanyID == id);
        }
    }
}