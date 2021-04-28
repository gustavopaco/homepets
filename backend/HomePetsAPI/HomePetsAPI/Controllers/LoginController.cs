using HomePets.Domain;
using HomePetsAPI.Code;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace HomePetsAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class LoginController : ControllerBase
    {


        public LoginController()
        {

        }


        [Route("register")]
        [HttpPost]
        public ActionResult<TokenModel> Register(RegisterModel model)
        {
            try
            {
                //TODO : registrar usuario

                var UsuarioApp = new UsuarioApp(null);

                var id = UsuarioApp.SalvarUsuario(model.email);
                
                var token = TokenService.GenerateToken(id.ToString(), model.nome, "ROLE"); //TODO: definir qual será a ROLE baseado no perfil do usuario : cliente, freela, dono de hotel


                return new TokenModel() { token = token };

            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }

        }

        [Route("login")]
        [HttpPost]
        public ActionResult<TokenModel> Post(AuthModel model)
        {
            try
            {
                //TODO : obter usuario via Rep

                //TODO:  validar senha
                //return Unauthorized();

                //Criar token
                var id = 0;
                var nome = "";
                var token = TokenService.GenerateToken(id.ToString(), nome, "ROLE"); //TODO: definir qual será a ROLE baseado no perfil do usuario : cliente, freela, dono de hotel

                return new TokenModel()
                {
                    token = token,
                    //completeRegistration = bool true, false/// ?
                };

            }
            catch (Exception ex)
            {
                return Unauthorized();
            }
        }

    }
}
