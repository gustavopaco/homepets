using System;

namespace HomePets.App
{
    public class UsuarioApp
    {
        private readonly EFContext _context;

        public UsuarioApp(EFContext context)
        {
            _context = context;
        }
        public int SalvarUsuario( String email)
        {
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
