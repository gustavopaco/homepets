using HomePets.Data;
using HomePets.Domain;
using System;
using System.Transactions;
using System.Linq;

namespace HomePets.App
{
    public class PetApp
    {
        private readonly EFContext _context;

        public PetApp(EFContext context)
        {
            _context = context;
        }

        public int SalvarPet(String nome)
        {

            //var validacaoEmail = _context.Pets.Where(p => !p.Deleted && p.Email == email).Any();
            //if (validacaoEmail)
            //    throw new Exception("Email já cadastrado.");


            var Id = 0;

            using (var scope = new TransactionScope(TransactionScopeOption.Required, new TransactionOptions() { IsolationLevel = IsolationLevel.ReadCommitted }))
            {
                var oPet = new Pet()
                {
                    Nome = nome,


                };

                _context.Pets.Add(oPet);
                _context.SaveChanges();

                scope.Complete();
            }
            return Id;
        }

    }
}
