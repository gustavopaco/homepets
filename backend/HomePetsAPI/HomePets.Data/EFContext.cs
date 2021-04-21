using HomePets.Domain;
using Microsoft.EntityFrameworkCore;
using System;


namespace HomePets.Data
{
    public class EFContext : DbContext
    {
        private const string connectionStringName = "Name=MS_TableConnectionString";

        public EFContext(DbContextOptions options) : base(options)
        {

        }

        DbSet<Usuario> Usuarios { get; set; }

    }
}
