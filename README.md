

Comando para gerar uma banco de dados postgres via Docker:

```sh
docker run --name gerenciador-tarefas-db -e POSTGRES_USER=gerenciador-tarefas -e POSTGRES_PASSWORD=gerenciador-tarefas -e POSTGRES_DB=gerenciador-tarefas -p 5665:5432 -d postgres
```