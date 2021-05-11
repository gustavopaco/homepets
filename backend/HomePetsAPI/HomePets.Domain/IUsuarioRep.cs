using System;

namespace HomePets.Domain
{
    public interface IUsuarioRep : IRepositorio<Usuario>
    {
        Usuario ObterUsuarioPeloId(int Id);

        Usuario ObterUsuarioPeloEmail(string Email);

        bool ExisteEmail(string Email);
    }
}