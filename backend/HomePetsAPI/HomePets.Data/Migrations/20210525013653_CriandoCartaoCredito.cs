using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace HomePets.Data.Migrations
{
    public partial class CriandoCartaoCredito : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "CartoesCredito",
                columns: table => new
                {
                    Deleted = table.Column<bool>(nullable: false),
                    Timestamp = table.Column<byte[]>(rowVersion: true, nullable: true),
                    CartaoCreditoId = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn),
                    NomeTitular = table.Column<string>(nullable: true),
                    Numero = table.Column<string>(nullable: true),
                    CodigoValidacao = table.Column<string>(nullable: true),
                    ValidadeMesAno = table.Column<string>(nullable: true),
                    UsuarioId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_CartoesCredito", x => x.CartaoCreditoId);
                    table.ForeignKey(
                        name: "FK_CartoesCredito_Usuarios_UsuarioId",
                        column: x => x.UsuarioId,
                        principalTable: "Usuarios",
                        principalColumn: "UsuarioId",
                        onDelete: ReferentialAction.NoAction);
                });

            migrationBuilder.CreateIndex(
                name: "IX_CartoesCredito_UsuarioId",
                table: "CartoesCredito",
                column: "UsuarioId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "CartoesCredito");
        }
    }
}
