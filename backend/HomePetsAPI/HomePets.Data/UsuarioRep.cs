using HomePets.Domain;
using Microsoft.EntityFrameworkCore;
using System;
using System.Linq;

namespace HomePets.Data
{
    public class UsuarioRep : Repositorio<Usuario>, IUsuarioRep
    {
        public UsuarioRep(EFContext context) : base(context)
        {

        }


        public Usuario ObterUsuarioPeloId(int Id)
        {
            return AsQueryable().Where(p => !p.Deleted && p.Id == Id)
                .Select(s => new
                {
                    s,
                }).SingleOrDefault()?.s;
        }


        public Usuario ObterUsuarioPeloEmail(string Email)
        {
            return AsQueryable().Where(p => !p.Deleted && p.Email == Email)
                .Select(s => new
                {
                    s,
                }).SingleOrDefault()?.s;
        }

        public bool ExisteEmail(int Id, string Email)
        {
            return AsQueryable().Where(p => !p.Deleted && p.Email == Email && p.Id != Id).Any();
        }
    }
}
