using HomePets.Data;
using HomePets.Domain;
using System;
using System.Transactions;
using System.Linq;

namespace HomePets.App
{
    public class PetApp
    {
        private readonly IUoW _uow;

        public PetApp(IUoW uow)
        {
            _uow = uow;
        }

        public void SalvarPet(int Id, string nome, string raca, string sexo, int tipo, int usuarioId)
        {
            using (var scope = new TransactionScope(TransactionScopeOption.Required, new TransactionOptions() { IsolationLevel = IsolationLevel.ReadCommitted }))
            {
                Pet oPet = _uow.Pets.ObterPet(Id);

                if (oPet == null)
                {
                    oPet = new Pet();
                    _uow.Pets.Add(oPet);
                }

                oPet.Nome = nome;
                oPet.Raca = raca;
                oPet.Sexo = sexo;
                oPet.Tipo = tipo;
                oPet.UsuarioId = usuarioId;

                _uow.SaveChanges();

                scope.Complete();
            }
        }

        public void ApagarPet(int Id, int usuarioId)
        {
            using (var scope = new TransactionScope(TransactionScopeOption.Required, new TransactionOptions() { IsolationLevel = IsolationLevel.ReadCommitted }))
            {
                Pet oPet = _uow.Pets.ObterPet(Id);

                if (oPet == null)
                    throw new Exception("Pet não encontrado.");

                if (oPet.UsuarioId != usuarioId)
                    throw new Exception("Usuário não tem permissão.");

                oPet.Deleted = true;

                _uow.SaveChanges();

                scope.Complete();
            }
        }
    }
}
