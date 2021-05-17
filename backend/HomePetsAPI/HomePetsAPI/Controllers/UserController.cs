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
    public class UserController : ControllerBase
    {

        private readonly IUoW UoW;
        UsuarioApp UsuarioApp = null;


        public UserController(IUoW uow)
        {
            UoW = uow;
            UsuarioApp = new UsuarioApp(UoW);
        }


        // POST api/values
        [HttpPost]
        public void Post([FromBody] UserModel value)
        {
            var userDataClaim = User.Claims.Where(c => c.Type == System.Security.Claims.ClaimTypes.UserData).Select(c => c.Value).SingleOrDefault();
            var UsuarioLogadoId = Int32.Parse(userDataClaim);


            //Alterar dados do usuario.
            UsuarioApp.AlterarDados(UsuarioLogadoId, value.nome, value.senha);
        }
    }
}
