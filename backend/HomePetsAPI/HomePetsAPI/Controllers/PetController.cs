using HomePets.App;
using HomePets.Data;
using HomePets.Domain;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace HomePetsAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    [Authorize]
    public class PetController : ControllerBase
    {
        private readonly IUoW UoW;
        PetApp PetApp = null;

        public PetController(IUoW uow)
        {
            UoW = uow;
            PetApp = new PetApp(UoW);
        }





        // GET api/values
        [HttpGet]
        public ActionResult<IEnumerable<PetModel>> Get()
        {
            var userDataClaim = User.Claims.Where(c => c.Type == System.Security.Claims.ClaimTypes.UserData).Select(c => c.Value).SingleOrDefault();
            var UsuarioLogadoId = Int32.Parse(userDataClaim);

            return UoW.Pets.ObterPets(UsuarioLogadoId)
                .Select(s => new PetModel()
                {
                    id = s.Id,
                    nome = s.Nome,
                    raca = s.Raca,
                    sexo = s.Sexo,
                    tipoPet = s.Tipo,
                }).ToList();
        }

        // GET api/values/5
        [HttpGet("{id}")]
        public ActionResult<PetModel> Get(int id)
        {
            var s = UoW.Pets.ObterPet(id);
            if (s == null)
                return NotFound("Pet não encontrado.");


            return new PetModel()
            {
                id = s.Id,
                nome = s.Nome,
                raca = s.Raca,
                sexo = s.Sexo,
                tipoPet = s.Tipo,
            };
        }

        // POST api/values
        [HttpPost]
        public void Post([FromBody] PetModel value)
        {
            var userDataClaim = User.Claims.Where(c => c.Type == System.Security.Claims.ClaimTypes.UserData).Select(c => c.Value).SingleOrDefault();
            var UsuarioLogadoId = Int32.Parse(userDataClaim);

            PetApp.SalvarPet(value.id, value.nome, value.raca, value.sexo, value.tipoPet, UsuarioLogadoId);
        }

        // DELETE api/values/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
            var userDataClaim = User.Claims.Where(c => c.Type == System.Security.Claims.ClaimTypes.UserData).Select(c => c.Value).SingleOrDefault();
            var UsuarioLogadoId = Int32.Parse(userDataClaim);


            PetApp.ApagarPet(id, UsuarioLogadoId);
        }
    }
}
