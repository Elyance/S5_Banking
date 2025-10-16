using Microsoft.EntityFrameworkCore;
using CompteDepotService.Data;
using CompteDepotService.Services;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

// Configuration CORS pour autoriser l'app Java
builder.Services.AddCors(options =>
{
    options.AddPolicy("AllowJavaClient", policy =>
    {
        policy.WithOrigins("http://localhost:6060/centralisateur-app") // URL de votre app Java
              .AllowAnyHeader()
              .AllowAnyMethod();
    });
});

// Configuration de la base de données PostgreSQL
builder.Services.AddDbContext<AppDbContext>(options =>
    options.UseNpgsql(builder.Configuration.GetConnectionString("DefaultConnection")));

// Injection des services
builder.Services.AddScoped<CompteDepotService.Services.CompteDepotService>();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseCors("AllowJavaClient");
app.UseAuthorization();
app.MapControllers();

app.Run();