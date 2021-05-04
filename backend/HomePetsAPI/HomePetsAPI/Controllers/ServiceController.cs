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
    public class ServiceController : ControllerBase
    {
        // GET api/values
        [HttpGet]
        public ActionResult<IEnumerable<ServiceModel>> Get()
        {
            return new ServiceModel[] {
                new ServiceModel(){ email = "email.com", id = 1, nomeServico = "Serviço 1", preco = 1.00, tipoPreco = 1 },
                new ServiceModel(){ email = "email.com", id = 1, nomeServico = "Serviço 2", preco = 2.00, tipoPreco = 1 },
                new ServiceModel(){ email = "email.com", id = 1, nomeServico = "Serviço 3", preco = 3.00, tipoPreco = 1 },
                new ServiceModel(){ email = "email.com", id = 1, nomeServico = "Serviço 4", preco = 4.00, tipoPreco = 1 },
                new ServiceModel(){ email = "email.com", id = 1, nomeServico = "Serviço 5", preco = 5.00, tipoPreco = 1 },
            };
        }

        // GET api/values/5
        [HttpGet("{id}")]
        public ActionResult<ServiceModel> Get(int id)
        {
            return new ServiceModel() { email = "email.com", id = 1, nomeServico = "Serviço 1", preco = 1.00, tipoPreco = 1 };
        }

        // POST api/values
        [HttpPost]
        public void Post([FromBody] ServiceModel value)
        {

        }

        // PUT api/values/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody] ServiceModel value)
        {
        }

        // DELETE api/values/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {

        }
    }
}
