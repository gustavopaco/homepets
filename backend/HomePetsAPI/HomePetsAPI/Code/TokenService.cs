using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using Microsoft.IdentityModel.Tokens;

namespace HomePetsAPI.Code
{
    public static class TokenService
    {
        public static string GenerateToken(string UsuarioId, string UsuarioNome, string Role)
        {
            var tokenHandler = new JwtSecurityTokenHandler();
            var key = Encoding.ASCII.GetBytes("fedaf7d8863b48e197b9287d492b708e");
            var tokenDescriptor = new SecurityTokenDescriptor
            {
                Subject = new ClaimsIdentity(new Claim[]
                {
                    new Claim(ClaimTypes.UserData, UsuarioId),
                    new Claim(ClaimTypes.NameIdentifier, UsuarioNome),
                    new Claim("http://schemas.microsoft.com/accesscontrolservice/2010/07/claims/identityprovider", "HomePets", "http://www.w3.org/2001/XMLSchema#string"),
                    new Claim(ClaimTypes.Name, UsuarioNome),
                    new Claim(ClaimTypes.GivenName, UsuarioNome),
                    new Claim(ClaimTypes.Role, Role),
                }),
                Expires = DateTime.UtcNow.AddHours(24),
                SigningCredentials = new SigningCredentials(new SymmetricSecurityKey(key), SecurityAlgorithms.HmacSha256Signature)
            };
            var token = tokenHandler.CreateToken(tokenDescriptor);
            return tokenHandler.WriteToken(token);
        }
    }
}
