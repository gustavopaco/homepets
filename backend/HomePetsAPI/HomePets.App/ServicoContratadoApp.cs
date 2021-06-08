using HomePets.Data;
using HomePets.Domain;
using System;
using System.Transactions;
using System.Linq;

namespace HomePets.App
{
    public class ServicoContratadoApp
    {
        private readonly IUoW _uow;

        public ServicoContratadoApp(IUoW uow)
        {
            _uow = uow;
        }

        public void SalvarServicoContratado(int Id, DateTime data, double valorTotal, int servicoId, int usuarioDonoPetId, int petId)
        {
            using (var scope = new TransactionScope(TransactionScopeOption.Required, new TransactionOptions() { IsolationLevel = IsolationLevel.ReadCommitted }))
            {
                ServicoContratado oServicoContratado = _uow.ServicosContratados.ObterServicoContratado(Id);

                if (oServicoContratado == null)
                {
                    oServicoContratado = new ServicoContratado();
                    _uow.ServicosContratados.Add(oServicoContratado);
                }

                oServicoContratado.DataExecucao = data;
                oServicoContratado.ValorTotal = valorTotal;
                oServicoContratado.ServicoId = servicoId;
                oServicoContratado.PetId = petId;
                oServicoContratado.UsuarioDonoPetId = usuarioDonoPetId;


                _uow.SaveChanges();

                scope.Complete();
            }
        }

        public void ApagarServicoContratado(int Id, int usuarioId)
        {
            using (var scope = new TransactionScope(TransactionScopeOption.Required, new TransactionOptions() { IsolationLevel = IsolationLevel.ReadCommitted }))
            {
                ServicoContratado oServicoContratado = _uow.ServicosContratados.ObterServicoContratado(Id);

                if (oServicoContratado == null)
                    throw new RoleException("Serviço não encontrado.");

                if (oServicoContratado.UsuarioDonoPetId != usuarioId)
                    throw new RoleException("Usuário não tem permissão.");

                oServicoContratado.Deleted = true;

                _uow.SaveChanges();

                scope.Complete();
            }
        }
    }
}
