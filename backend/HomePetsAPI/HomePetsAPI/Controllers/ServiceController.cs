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
    public class ServiceController : ControllerBase
    {
        // GET api/values
        [HttpGet]
        public ActionResult<IEnumerable<ServiceModel>> Get()
        {
            var userDataClaim = User.Claims.Where(c => c.Type == System.Security.Claims.ClaimTypes.UserData).Select(c => c.Value).SingleOrDefault();
            var UsuarioLogadoId = Int32.Parse(userDataClaim);


            return new ServiceModel[] {
                new ServiceModel(){ id = 1, nomeServico = "Serviço 1", preco = 1.00, tipoPreco = 1 },
                new ServiceModel(){ id = 2, nomeServico = "Serviço 2", preco = 2.00, tipoPreco = 1 },
                new ServiceModel(){ id = 3, nomeServico = "Serviço 3", preco = 3.00, tipoPreco = 1 },
                new ServiceModel(){ id = 4, nomeServico = "Serviço 4", preco = 4.00, tipoPreco = 1 },
                new ServiceModel(){ id = 5, nomeServico = "Serviço 5", preco = 5.00, tipoPreco = 1 },
            };
        }

        // GET api/values/5
        [HttpGet("{id}")]
        public ActionResult<ServiceModel> Get(int id)
        {
            return new ServiceModel() { id = id, nomeServico = "Serviço 1", preco = 1.00, tipoPreco = 1 };
        }

        // POST api/values
        [HttpPost]
        public void Post([FromBody] ServiceModel value)
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

        // DELETE api/values/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
            var userDataClaim = User.Claims.Where(c => c.Type == System.Security.Claims.ClaimTypes.UserData).Select(c => c.Value).SingleOrDefault();
            var UsuarioLogadoId = Int32.Parse(userDataClaim);



        }
    }
}
