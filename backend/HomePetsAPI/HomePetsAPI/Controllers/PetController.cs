using HomePets.Domain;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace HomePetsAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class PetController : ControllerBase
    {
        // GET api/values
        [HttpGet]
        public ActionResult<IEnumerable<PetModel>> Get()
        {
            return new PetModel[] {
                new PetModel(){ email = "email.com", nome = "Nome 1", raca = "Raça 1", sexo = "M", tipoPet = 0, },
                new PetModel(){ email = "email.com", nome = "Nome 2", raca = "Raça 1", sexo = "M", tipoPet = 0, },
                new PetModel(){ email = "email.com", nome = "Nome 3", raca = "Raça 1", sexo = "M", tipoPet = 0, },
                new PetModel(){ email = "email.com", nome = "Nome 4", raca = "Raça 1", sexo = "M", tipoPet = 0, },
            };
        }

        // GET api/values/5
        [HttpGet("{id}")]
        public ActionResult<PetModel> Get(int id)
        {
            return new PetModel() { email = "email.com", nome = "Nome 1", raca = "Raça 1", sexo = "M", tipoPet = 0, };
        }

        // POST api/values
        [HttpPost]
        public void Post([FromBody] PetModel value)
        {

        }

        // PUT api/values/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody] PetModel value)
        {
        }

        // DELETE api/values/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {

        }
    }
}
