# Sistema de Aluguel - Módulo `solo`

Este README rápido explica como subir a aplicação, acessar a documentação Swagger e exemplos JSON prontos para postar um veículo e um cliente.

## 1- Rode a aplicaçao

## 2- LINK Swagger
```
http://localhost:8080/swagger-ui/index.html
```
## 3- JSONs prontos para colar no Swagger (POST)

POST `/api/veiculos`:

```json
{
  "matricula": "AAA1222",
  "ano": 2025,
  "marca": "Ferrari",
  "modelo": "Veneno",
  "placa": "SLA-4444"
}
```

```json
{
  "matricula": "BBB1222",
  "ano": 2022,
  "marca": "Teste2",
  "modelo": "Corola",
  "placa": "TET-4444"
}
```


POST `/api/clientes`:

```json
{
  "nome": "Pedro Silva",
  "rg": "99.999.999-9",
  "cpf": "888.888.888-88",
  "endereco": "Rua da puc, 44",
  "profissao": "Programador",
  "empregadores": ["Empresa A", "Empresa B"]
}
```

```json
{
  "nome": "Lucas Silva",
  "rg": "77.999.999-9",
  "cpf": "222.888.888-88",
  "endereco": "Rua do pic, 55",
  "profissao": "Sapateiro",
  "empregadores": ["Empresa A", "Empresa B"]
}
```


