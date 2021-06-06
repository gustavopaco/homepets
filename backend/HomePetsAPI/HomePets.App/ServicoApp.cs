using HomePets.Data;
using HomePets.Domain;
using System;
using System.Transactions;
using System.Linq;

namespace HomePets.App
{
    public class ServicoApp
    {
        private readonly IUoW _uow;

        public ServicoApp(IUoW uow)
        {
            _uow = uow;
        }

        public void SalvarServico(int Id, String nome, double preco, int tipoPreco, int usuarioId)
        {
            using (var scope = new TransactionScope(TransactionScopeOption.Required, new TransactionOptions() { IsolationLevel = IsolationLevel.ReadCommitted }))
            {
                Servico oServico = _uow.Servicos.ObterServico(Id);

                if (oServico == null)
                {
                    oServico = new Servico();
                    _uow.Servicos.Add(oServico);
                }

                oServico.Nome = nome;
                oServico.Preco = preco;
                oServico.TipoPreco = tipoPreco;
                oServico.UsuarioId = usuarioId;

                _uow.SaveChanges();

                scope.Complete();
            }
        }

        public void ApagarServico(int Id, int usuarioId)
        {
            using (var scope = new TransactionScope(TransactionScopeOption.Required, new TransactionOptions() { IsolationLevel = IsolationLevel.ReadCommitted }))
            {
                Servico oServico = _uow.Servicos.ObterServico(Id);

                if (oServico == null)
                    throw new RoleException("Serviço não encontrado.");

                if (oServico.UsuarioId != usuarioId)
                    throw new RoleException("Usuário não tem permissão.");

                oServico.Deleted = true;

                _uow.SaveChanges();

                scope.Complete();
            }
        }
    }
}
