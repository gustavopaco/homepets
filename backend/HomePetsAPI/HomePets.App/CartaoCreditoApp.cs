using HomePets.Data;
using HomePets.Domain;
using System;
using System.Transactions;
using System.Linq;

namespace HomePets.App
{
    public class CartaoCreditoApp
    {
        private readonly IUoW _uow;

        public CartaoCreditoApp(IUoW uow)
        {
            _uow = uow;
        }

        public void SalvarCartaoCredito(int Id, string codigoValidacao, string nomeTitular, string numero, string validadeMesAno, int usuarioId)
        {
            using (var scope = new TransactionScope(TransactionScopeOption.Required, new TransactionOptions() { IsolationLevel = IsolationLevel.ReadCommitted }))
            {
                CartaoCredito oCartaoCredito = _uow.CartoesCredito.ObterCartaoCredito(Id);

                if (oCartaoCredito == null)
                {
                    oCartaoCredito = new CartaoCredito();
                    _uow.CartoesCredito.Add(oCartaoCredito);
                }

                oCartaoCredito.CodigoValidacao = codigoValidacao;
                oCartaoCredito.NomeTitular = nomeTitular;
                oCartaoCredito.Numero = numero;
                oCartaoCredito.ValidadeMesAno = validadeMesAno;

                oCartaoCredito.UsuarioId = usuarioId;

                _uow.SaveChanges();

                scope.Complete();
            }
        }

        public void ApagarCartaoCredito(int Id, int usuarioId)
        {
            using (var scope = new TransactionScope(TransactionScopeOption.Required, new TransactionOptions() { IsolationLevel = IsolationLevel.ReadCommitted }))
            {
                CartaoCredito oCartaoCredito = _uow.CartoesCredito.ObterCartaoCredito(Id);

                if (oCartaoCredito == null)
                    throw new RoleException("Cartão de Crédito não encontrado.");

                if (oCartaoCredito.UsuarioId != usuarioId)
                    throw new RoleException("Usuário não tem permissão.");

                oCartaoCredito.Deleted = true;

                _uow.SaveChanges();

                scope.Complete();
            }
        }
    }
}
