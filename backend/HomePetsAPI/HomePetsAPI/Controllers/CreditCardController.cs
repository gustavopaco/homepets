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
    public class CreditCardController : ControllerBase
    {
        private readonly IUoW UoW;
        private readonly CartaoCreditoApp CartaoCreditoApp = null;

        public CreditCardController(IUoW uow)
        {
            UoW = uow;
            CartaoCreditoApp = new CartaoCreditoApp(UoW);
        }



        // GET api/values
        [HttpGet]
        public ActionResult<IEnumerable<CreditCardModel>> Get()
        {
            var userDataClaim = User.Claims.Where(c => c.Type == System.Security.Claims.ClaimTypes.UserData).Select(c => c.Value).SingleOrDefault();
            var UsuarioLogadoId = Int32.Parse(userDataClaim);

            return UoW.CartoesCredito.ObterCartoesCredito(UsuarioLogadoId)
                .Select(s => new CreditCardModel()
                {
                    id = s.Id,
                    codigoValidacao = s.CodigoValidacao,
                    nomeTitular = s.NomeTitular,
                    numero = s.Numero,
                    validadeMesAno = s.ValidadeMesAno,

                }).ToList();
        }

        // GET api/values/5
        [HttpGet("{id}")]
        public ActionResult<CreditCardModel> Get(int id)
        {
            var s = UoW.CartoesCredito.ObterCartaoCredito(id);
            if (s == null)
                return NotFound("Cartão de Crédito não encontrado.");


            return new CreditCardModel()
            {
                id = s.Id,
                codigoValidacao = s.CodigoValidacao,
                nomeTitular = s.NomeTitular,
                numero = s.Numero,
                validadeMesAno = s.ValidadeMesAno,
            };
        }

        // POST api/values
        [HttpPost]
        public void Post([FromBody] CreditCardModel value)
        {
            var userDataClaim = User.Claims.Where(c => c.Type == System.Security.Claims.ClaimTypes.UserData).Select(c => c.Value).SingleOrDefault();
            var UsuarioLogadoId = Int32.Parse(userDataClaim);

            CartaoCreditoApp.SalvarCartaoCredito(value.id, value.codigoValidacao, value.nomeTitular, value.numero, value.validadeMesAno, UsuarioLogadoId);
        }

        // DELETE api/values/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
            var userDataClaim = User.Claims.Where(c => c.Type == System.Security.Claims.ClaimTypes.UserData).Select(c => c.Value).SingleOrDefault();
            var UsuarioLogadoId = Int32.Parse(userDataClaim);


            CartaoCreditoApp.ApagarCartaoCredito(id, UsuarioLogadoId);
        }
    }
}
