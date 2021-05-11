using HomePets.Data;
using HomePets.Domain;
using System;
using System.Transactions;
using System.Linq;

namespace HomePets.App
{
    public class ServicoApp
    {
        private readonly EFContext _context;

        public ServicoApp(EFContext context)
        {
            _context = context;
        }

        public int SalvarServico(String nome)
        {

            //var validacaoEmail = _context.Pets.Where(p => !p.Deleted && p.Email == email).Any();
            //if (validacaoEmail)
            //    throw new Exception("Email já cadastrado.");


            var Id = 0;

            using (var scope = new TransactionScope(TransactionScopeOption.Required, new TransactionOptions() { IsolationLevel = IsolationLevel.ReadCommitted }))
            {
                var oServico = new Servico()
                {
                    Nome = nome,


                };

                _context.Servicos.Add(oServico);
                _context.SaveChanges();

                scope.Complete();
            }
            return Id;
        }

    }
}
