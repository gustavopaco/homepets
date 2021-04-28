using HomePets.Data;
using HomePets.Domain;
using System;
using System.Transactions;
using System.Linq;

namespace HomePets.App
{
    public class UsuarioApp
    {
        private readonly EFContext _context;

        public UsuarioApp(EFContext context)
        {
            _context = context;
        }

        public int SalvarUsuario(String email)
        {

            var validacaoEmail = _context.Usuarios.Where(p => !p.Deleted && p.Email == email).Any();
            if (validacaoEmail)
                throw new Exception("Email já cadastrado.");


            var Id = 0;

            using (var scope = new TransactionScope(TransactionScopeOption.Required, new TransactionOptions() { IsolationLevel = IsolationLevel.ReadCommitted }))
            {
                var oUsuario = new Usuario()
                {
                    Email = email,
                                       

                };

                _context.Usuarios.Add(oUsuario);
                _context.SaveChanges();

                scope.Complete();
            }
            return Id;
        }

    }
}
