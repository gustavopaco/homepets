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
    public class ServiceContractController : ControllerBase
    {

        private readonly IUoW UoW;
        private readonly ServicoContratadoApp ServicoContratadoApp = null;

        public ServiceContractController(IUoW uow)
        {
            UoW = uow;
            ServicoContratadoApp = new ServicoContratadoApp(UoW);
        }




        // GET api/values
        [HttpGet]
        public ActionResult<IEnumerable<ServiceContractModel>> Get()
        {
            var userDataClaim = User.Claims.Where(c => c.Type == System.Security.Claims.ClaimTypes.UserData).Select(c => c.Value).SingleOrDefault();
            var UsuarioLogadoId = Int32.Parse(userDataClaim);

            return UoW.ServicosContratados.ObterServicosRelacionados(UsuarioLogadoId)
                .Select(s => new ServiceContractModel()
                {
                    id = s.Id,
                    idPet = s.PetId,
                    idPrestador = s.Servico.UsuarioId,
                    idServico = s.ServicoId,
                    idTomador = s.UsuarioDonoPetId,
                    nomePet = s.Pet.Nome,

                    nomePrestador = s.Servico.Usuario.Nome,
                    nomeServico = s.Servico.Nome,
                    nomeTomador = s.UsuarioDonoPet.Nome,

                    tipoPreco = s.Servico.TipoPreco,
                    preco = s.Servico.Preco,
                    valorTotal = s.ValorTotal,
                    dataExecucao = s.DataExecucao,

                }).ToList();
        }

        // POST api/values
        [HttpPost]
        public void Post([FromBody] ServiceContractModel value)
        {
            var userDataClaim = User.Claims.Where(c => c.Type == System.Security.Claims.ClaimTypes.UserData).Select(c => c.Value).SingleOrDefault();
            var UsuarioLogadoId = Int32.Parse(userDataClaim);

            ServicoContratadoApp.SalvarServicoContratado(value.id, value.dataExecucao, value.valorTotal, value.idServico, UsuarioLogadoId, value.idPet);
        }

        // DELETE api/values/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
            var userDataClaim = User.Claims.Where(c => c.Type == System.Security.Claims.ClaimTypes.UserData).Select(c => c.Value).SingleOrDefault();
            var UsuarioLogadoId = Int32.Parse(userDataClaim);


            ServicoContratadoApp.ApagarServicoContratado(id, UsuarioLogadoId);
        }
    }
}
