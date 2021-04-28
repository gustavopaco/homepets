using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace HomePets.Data.Migrations
{
    public partial class CreateUsuario : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "Login",
                table: "Usuarios",
                newName: "Email");

            migrationBuilder.AddColumn<bool>(
                name: "Deleted",
                table: "Usuarios",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<byte[]>(
                name: "Timestamp",
                table: "Usuarios",
                rowVersion: true,
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "Tipo",
                table: "Usuarios",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "Deleted",
                table: "Usuarios");

            migrationBuilder.DropColumn(
                name: "Timestamp",
                table: "Usuarios");

            migrationBuilder.DropColumn(
                name: "Tipo",
                table: "Usuarios");

            migrationBuilder.RenameColumn(
                name: "Email",
                table: "Usuarios",
                newName: "Login");
        }
    }
}
