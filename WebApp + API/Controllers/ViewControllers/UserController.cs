using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using ParkBuddies.Models;

namespace ParkBuddies.Controllers
{
    public class UserController : Controller
    {
        public IActionResult Index()
        {
            var viewModel = new UserDto
            {
                UserName = "Jon Hilton",
                UserPassword = "me@jonhilton.net",
                CarparkName = "Dayum"
            };

            return View(viewModel);
        }

        // GET: User/Create
        public IActionResult Create()
        {
            return View();
        }
    }
}