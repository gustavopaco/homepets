using HomePets.Data;
using HomePets.Domain;
using System;
using System.Transactions;
using System.Linq;

namespace HomePets.App
{
    public class UsuarioApp
    {
        private readonly IUoW _uow;

        public UsuarioApp(IUoW uow)
        {
            _uow = uow;
        }

        public void SalvarUsuario(int Id, String email, string nome, string senha, TipoUsuario tipo)
        {

            var validacaoEmail = _uow.Usuarios.ExisteEmail(Id, email);
            if (validacaoEmail)
                throw new Exception("Email já cadastrado.");

            using (var scope = new TransactionScope(TransactionScopeOption.Required, new TransactionOptions() { IsolationLevel = IsolationLevel.ReadCommitted }))
            {
                Usuario oUsuario = _uow.Usuarios.ObterUsuarioPeloId(Id);

                if (oUsuario == null)
                {
                    oUsuario = new Usuario();
                    _uow.Usuarios.Add(oUsuario);
                }


                oUsuario.Email = email;
                oUsuario.Nome = nome;
                oUsuario.Tipo = tipo;
                oUsuario.Senha = senha;


                _uow.SaveChanges();

                scope.Complete();
            }
        }
    }
}
