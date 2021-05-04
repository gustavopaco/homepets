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
        // GET api/values
        [HttpGet]
        public ActionResult<IEnumerable<PetModel>> Get()
        {

            var userDataClaim = User.Claims.Where(c => c.Type == System.Security.Claims.ClaimTypes.UserData).Select(c => c.Value).SingleOrDefault();
            var UsuarioLogadoId = Int32.Parse(userDataClaim);



            return new PetModel[] {
                new PetModel(){ id= 1, nome = "Nome 1", raca = "Raça 1", sexo = "M", tipoPet = 0, },
                new PetModel(){ id= 2, nome = "Nome 2", raca = "Raça 1", sexo = "M", tipoPet = 0, },
                new PetModel(){ id= 3, nome = "Nome 3", raca = "Raça 1", sexo = "M", tipoPet = 0, },
                new PetModel(){ id= 4, nome = "Nome 4", raca = "Raça 1", sexo = "M", tipoPet = 0, },
            };
        }

        // GET api/values/5
        [HttpGet("{id}")]
        public ActionResult<PetModel> Get(int id)
        {
            return new PetModel() { id = id, nome = "Nome 1", raca = "Raça 1", sexo = "M", tipoPet = 0, };
        }

        // POST api/values
        [HttpPost]
        public void Post([FromBody] PetModel value)
        {
            var userDataClaim = User.Claims.Where(c => c.Type == System.Security.Claims.ClaimTypes.UserData).Select(c => c.Value).SingleOrDefault();
            var UsuarioLogadoId = Int32.Parse(userDataClaim);

            if (value.id == 0)
            {
                //inclusao
            }
            else
            {
                //alteracao
            }

        }

        //// PUT api/values/5
        //[HttpPut("{id}")]
        //public void Put(int id, [FromBody] PetModel value)
        //{
        //}

        // DELETE api/values/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
            var userDataClaim = User.Claims.Where(c => c.Type == System.Security.Claims.ClaimTypes.UserData).Select(c => c.Value).SingleOrDefault();
            var UsuarioLogadoId = Int32.Parse(userDataClaim);


        }
    }
}
