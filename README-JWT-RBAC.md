
# JWT + RBAC + Swagger + ApiResponse Handlers

- Stateless JWT auth with RBAC roles: OWNER, TENANT, ADMIN
- Swagger UI open (no auth) and supports JWT Authorization
- Consistent ApiResponse for 401/403 via custom handlers

## Run
export JWT_SECRET="super-long-random-secret"
export JWT_EXPIRATION_MS=86400000
mvn spring-boot:run

## Swagger
- UI: http://localhost:8080/swagger-ui/index.html
- Docs: /v3/api-docs

### Use JWT in Swagger
1) POST /api/auth/register or /api/auth/login
2) Copy the `token`
3) Click **Authorize** (bearerAuth) and paste token (raw or with `Bearer `)
4) Call protected endpoints

## Sample curl
curl -s -X POST http://localhost:8080/api/auth/register -H "Content-Type: application/json" -d '{"username":"alice","email":"alice@example.com","password":"Passw0rd!","roles":["OWNER"]}'

TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d '{"usernameOrEmail":"alice","password":"Passw0rd!"}' | jq -r .token)

curl -H "Authorization: Bearer $TOKEN" http://localhost:8080/api/me/owner
