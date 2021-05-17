using HomePets.App;
using HomePets.Data;
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

        private readonly IUoW UoW;
        UsuarioApp UsuarioApp = null;


        public LoginController(IUoW uow)
        {
            UoW = uow;
            UsuarioApp = new UsuarioApp(UoW);
        }


        [Route("register")]
        [HttpPost]
        public ActionResult<TokenModel> Register(RegisterModel model)
        {
            try
            {
                //1- Cliente
                //2- Dono Hotel
                //3- Freelancer
                UsuarioApp.CriarUsuario(model.email, model.nome, model.senha, (TipoUsuario)model.stats);

                var usuario = UoW.Usuarios.ObterUsuarioPeloEmail(model.email);

                var token = TokenService.GenerateToken(usuario.Id.ToString(), usuario.Nome, ((int)usuario.Tipo).ToString());

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
                //obter usuario via Rep
                var usuario = UoW.Usuarios.ObterUsuarioPeloEmail(model.email);

                if (usuario.Senha != model.senha)
                    return Unauthorized();

                //Criar token
                var token = TokenService.GenerateToken(usuario.Id.ToString(), usuario.Nome, ((int)usuario.Tipo).ToString());


                return new TokenModel()
                {
                    token = token,
                    nome = usuario.Nome,
                    stats = (int)usuario.Tipo,
                };

            }
            catch (Exception ex)
            {
                return Unauthorized();
            }
        }

    }
}
