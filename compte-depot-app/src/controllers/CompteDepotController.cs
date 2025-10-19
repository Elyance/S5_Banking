
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

        [HttpGet("list")]
        public ActionResult<IEnumerable<CompteDepot>> GetAllComptesDepot()
        {
            Console.WriteLine("Fetching all comptes depot...");
            var comptes = _service.GetAllComptesDepot();
            return Ok(comptes);
        }

        [HttpPost]
        public ActionResult<CompteDepot> CreateCompteDepot([FromBody] CompteDepot compteDepot)
        {
            var created = _service.CreateCompteDepot(compteDepot);
            return CreatedAtAction(nameof(CreateCompteDepot), new { id = created.Id }, created);
        }
        // DTO pour le dépôt
        public class DepotRequest
        {
            public decimal Montant { get; set; }
            public DateTime DateDepot { get; set; }
            public long CompteDepotId { get; set; }
        }

        [HttpPost("depot")]
        public ActionResult<Transaction> Deposer([FromBody] DepotRequest request)
        {
            try
            {
                var transaction = _service.Deposer(request.Montant, request.DateDepot, request.CompteDepotId);
                // Retourner un DTO pour éviter les cycles
                var dto = new {
                    transaction.Id,
                    transaction.TypeOperationId,
                    transaction.CompteDepotId,
                    transaction.Montant,
                    transaction.SoldeAvant,
                    transaction.SoldeApres,
                    transaction.DateTransaction
                };
                return Ok(dto);
            }
            catch (Exception ex)
            {
                return BadRequest(new { error = ex.Message });
            }
        }

        [HttpPost("taux")]
        public ActionResult<TauxInteret> CreateTaux([FromBody] TauxInteret taux)
        {
            try
            {
                var created = _service.CreateTauxInteret(taux);
                return CreatedAtAction(nameof(GetTaux), new { id = created.Id }, created);
            }
            catch (Exception ex)
            {
                return BadRequest(new { error = ex.Message });
            }
        }

        [HttpGet("taux/{id}")]
        public ActionResult<TauxInteret> GetTaux(long id)
        {
            var taux = _service.GetTauxById(id);
            if (taux == null) return NotFound();
            return Ok(taux);
        }

        [HttpGet("taux/latest")]
        public ActionResult<TauxInteret> GetLatestTaux()
        {
            var taux = _service.GetLatestTaux();
            if (taux == null) return NotFound();
            return Ok(taux);
        }
    }
}
