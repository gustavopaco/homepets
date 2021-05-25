using HomePets.App;
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

        private readonly IUoW UoW;
        ServicoApp ServicoApp = null;

        public ServiceController(IUoW uow)
        {
            UoW = uow;
            ServicoApp = new ServicoApp(UoW);
        }




        // GET api/values
        [HttpGet]
        public ActionResult<IEnumerable<ServiceModel>> Get()
        {
            var userDataClaim = User.Claims.Where(c => c.Type == System.Security.Claims.ClaimTypes.UserData).Select(c => c.Value).SingleOrDefault();
            var UsuarioLogadoId = Int32.Parse(userDataClaim);

            return UoW.Servicos.ObterServicos(UsuarioLogadoId)
                .Select(s => new ServiceModel()
                {
                    id = s.Id,
                    nomeServico = s.Nome,
                    preco = s.Preco,
                    tipoPreco = s.TipoPreco,
                }).ToList();
        }


        // GET api/values
        [HttpGet]
        public ActionResult<IEnumerable<ServiceDescriptionModel>> Get(string query)
        {
            var userDataClaim = User.Claims.Where(c => c.Type == System.Security.Claims.ClaimTypes.UserData).Select(c => c.Value).SingleOrDefault();
            var UsuarioLogadoId = Int32.Parse(userDataClaim);

            return UoW.Servicos.ObterServicosDisponiveis(query)
                .Select(s => new ServiceDescriptionModel()
                {
                    id = s.Id,
                    nomeServico = s.Nome,
                    preco = s.Preco,
                    tipoPreco = s.TipoPreco,

                    nomePrestador = s.Usuario.Nome,
                }).ToList();
        }

        // GET api/values/5
        [HttpGet("{id}")]
        public ActionResult<ServiceModel> Get(int id)
        {
            var s = UoW.Servicos.ObterServico(id);
            if (s == null)
                return NotFound("Serviço não encontrado.");


            return new ServiceModel()
            {
                id = s.Id,
                nomeServico = s.Nome,
                preco = s.Preco,
                tipoPreco = s.TipoPreco,
            };
        }

        // POST api/values
        [HttpPost]
        public void Post([FromBody] ServiceModel value)
        {
            var userDataClaim = User.Claims.Where(c => c.Type == System.Security.Claims.ClaimTypes.UserData).Select(c => c.Value).SingleOrDefault();
            var UsuarioLogadoId = Int32.Parse(userDataClaim);

            ServicoApp.SalvarServico(value.id, value.nomeServico, value.preco, value.tipoPreco, UsuarioLogadoId);
        }

        // DELETE api/values/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
            var userDataClaim = User.Claims.Where(c => c.Type == System.Security.Claims.ClaimTypes.UserData).Select(c => c.Value).SingleOrDefault();
            var UsuarioLogadoId = Int32.Parse(userDataClaim);


            ServicoApp.ApagarServico(id, UsuarioLogadoId);
        }
    }
}
