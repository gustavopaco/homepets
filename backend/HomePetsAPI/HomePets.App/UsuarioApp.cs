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

        public void CriarUsuario(String email, string nome, string senha, TipoUsuario tipo)
        {

            var validacaoEmail = _uow.Usuarios.ExisteEmail(email);
            if (validacaoEmail)
                throw new RoleException("Email já cadastrado.");

            using (var scope = new TransactionScope(TransactionScopeOption.Required, new TransactionOptions() { IsolationLevel = IsolationLevel.ReadCommitted }))
            {
                var oUsuario = new Usuario();
                _uow.Usuarios.Add(oUsuario);


                oUsuario.Email = email;
                oUsuario.Nome = nome;
                oUsuario.Tipo = tipo;
                oUsuario.Senha = senha;


                _uow.SaveChanges();

                scope.Complete();
            }
        }

        public void AlterarDados(int Id, string nome, string senha)
        {
            using (var scope = new TransactionScope(TransactionScopeOption.Required, new TransactionOptions() { IsolationLevel = IsolationLevel.ReadCommitted }))
            {
                Usuario oUsuario = _uow.Usuarios.ObterUsuarioPeloId(Id);

                if (oUsuario == null)
                    throw new RoleException("Usuário não encontrado");


                oUsuario.Nome = nome;
                oUsuario.Senha = senha;


                _uow.SaveChanges();

                scope.Complete();
            }
        }
    }
}
