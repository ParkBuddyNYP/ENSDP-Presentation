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
    public class CarparkGraphDatasController : ControllerBase
    {
        private readonly ParkBuddiesContext _context;

        public CarparkGraphDatasController(ParkBuddiesContext context)
        {
            _context = context;
        }
        [Authorize(Roles = Role.CompanyAdmin + "," + Role.ParkBuddyAdmin)]
        // GET: api/CarparkGraphDatas
        [HttpGet]
        public IEnumerable<CarparkGraphData> GetCarparkGraphData()
        {
            return _context.CarparkGraphData;
        }

        [Authorize(Roles = Role.CompanyAdmin + "," + Role.ParkBuddyAdmin)]
        // GET: api/CarparkGraphDatas/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetCarparkGraphData([FromRoute] string id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var carparkGraphData = await _context.CarparkGraphData.SingleOrDefaultAsync(m => m.CarparkGraphDataID== id);

            if (carparkGraphData == null)
            {
                return NotFound();
            }

            return Ok(carparkGraphData);
        }

        // PUT: api/CarparkGraphDatas/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutCarparkGraphData([FromRoute] string id, [FromBody] CarparkGraphData carparkGraphData)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != carparkGraphData.CarparkGraphDataID)
            {
                return BadRequest();
            }

            _context.Entry(carparkGraphData).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!CarparkGraphDataExists(id))
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

        [AllowAnonymous]
        // POST: api/CarparkGraphDatas
        [HttpPost]
        public async Task<IActionResult> PostCarparkGraphData([FromBody] CarparkGraphData carparkGraphData)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            _context.CarparkGraphData.Add(carparkGraphData);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetCarparkGraphData", new { id = carparkGraphData.CarparkGraphDataID }, carparkGraphData);
        }

        // DELETE: api/CarparkGraphDatas/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteCarparkGraphData([FromRoute] string id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var carparkGraphData = await _context.CarparkGraphData.SingleOrDefaultAsync(m => m.CarparkGraphDataID == id);
            if (carparkGraphData == null)
            {
                return NotFound();
            }

            _context.CarparkGraphData.Remove(carparkGraphData);
            await _context.SaveChangesAsync();

            return Ok(carparkGraphData);
        }

        private bool CarparkGraphDataExists(string id)
        {
            return _context.CarparkGraphData.Any(e => e.CarparkGraphDataID == id);
        }
    }
}