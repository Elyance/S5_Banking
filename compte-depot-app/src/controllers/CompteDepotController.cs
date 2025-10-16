using Microsoft.AspNetCore.Mvc;
using CompteDepotService.Models;
using CompteDepotService.Services;

namespace CompteDepotService.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class CompteDepotController : ControllerBase
    {
		private readonly Services.CompteDepotService _service;

		public CompteDepotController(Services.CompteDepotService service)
		{
			_service = service;
		}

        [HttpPost]
        public ActionResult<CompteDepot> CreateCompteDepot([FromBody] CompteDepot compteDepot)
        {
            var created = _service.CreateCompteDepot(compteDepot);
            return CreatedAtAction(nameof(CreateCompteDepot), new { id = created.Id }, created);
        }
    }
}
